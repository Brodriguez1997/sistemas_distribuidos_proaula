package officepdf;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.atomic.AtomicLong;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Processing implements Runnable {
    private final String inputFile;
    private final String outputDir;
    private static final AtomicLong totalConversionTime = new AtomicLong(0);
    private static final AtomicLong successCount = new AtomicLong(0);
    private static final AtomicLong failureCount = new AtomicLong(0);

    public Processing(String inputFile, String outputDir) {
        this.inputFile = inputFile;
        this.outputDir = outputDir;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        Path logFile = null;
        Process process = null;
        
        try {
            // 1. Verificaciones iniciales
            Path inputPath = Paths.get(inputFile).toAbsolutePath().normalize();
            Path outputDirPath = Paths.get(outputDir).toAbsolutePath().normalize();
            
            validateInputFile(inputPath);
            checkLibreOfficeEnvironment();
            
            // 2. Configurar proceso
            logFile = outputDirPath.resolve("lo_conv_" + System.nanoTime() + ".log");
            
            ProcessBuilder pb = new ProcessBuilder(
                "libreoffice",
                "--headless",
                "--convert-to",
                "pdf:writer_pdf_Export",
                "--nologo",
                "--norestore",
                "--nodefault",
                "--nolockcheck",
                "--invisible",
                "--nofirststartwizard",
                inputPath.toString(),
                "--outdir",
                outputDirPath.toString()
            );
            
            pb.redirectErrorStream(true);
            pb.redirectOutput(ProcessBuilder.Redirect.to(logFile.toFile()));
            
            // 3. Ejecutar conversión
            process = pb.start();
            long timeout = calculateTimeout(Files.size(inputPath));
            boolean finished = process.waitFor(timeout, TimeUnit.SECONDS);
            
            if (!finished) {
                process.destroyForcibly();
                throw new TimeoutException("Timeout después de " + timeout + " segundos");
            }
            
            // 4. Verificar resultado
            if (process.exitValue() != 0) {
                String errorDetails = Files.readString(logFile);
                throw new IOException("Error en conversión (código " + process.exitValue() + ")\n" +
                                "Detalles:\n" + errorDetails);
            }
            
            successCount.incrementAndGet();
            
        } catch (Exception e) {
            failureCount.incrementAndGet();
            manejarError(e, inputFile, logFile); // Método renombrado para consistencia
        } finally {
            cleanResources(process, logFile);
        }
    }
    
    private void validateInputFile(Path filePath) throws IOException {
        if (Files.size(filePath) < 50) {
            throw new IOException("El archivo es demasiado pequeño para ser válido");
        }
        
        if (!Files.isReadable(filePath)) {
            throw new IOException("No se tienen permisos para leer el archivo");
        }
        
        byte[] header = new byte[8];
        try (InputStream is = Files.newInputStream(filePath)) {
            is.read(header);
            if (header[0] == 0 && header[1] == 0 && header[2] == 0) {
                throw new IOException("El archivo parece estar corrupto o vacío");
            }
        }
    }

    private void checkLibreOfficeEnvironment() throws IOException {
        try {
            Process p = new ProcessBuilder("libreoffice", "--version").start();
            if (p.waitFor(5, TimeUnit.SECONDS) && p.exitValue() != 0) {
                throw new IOException("LibreOffice no está instalado correctamente");
            }
            
            Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"));
            if (!Files.isWritable(tempDir)) {
                throw new IOException("No se puede escribir en el directorio temporal: " + tempDir);
            }
            
            long freeMem = Runtime.getRuntime().freeMemory() / (1024 * 1024);
            if (freeMem < 100) {
                throw new IOException("Memoria insuficiente: " + freeMem + "MB libres");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Verificación interrumpida", e);
        }
    }
    
    private void manejarError(Exception e, String inputFile, Path archivoLog) {
        System.err.println("\n=== ERROR DE CONVERSIÓN ===");
        System.err.println("Archivo: " + inputFile);
        System.err.println("Tipo: " + e.getClass().getSimpleName());
        System.err.println("Mensaje: " + e.getMessage());
        
        if (archivoLog != null && Files.exists(archivoLog)) {
            System.err.println("\nDetalles del error (log LibreOffice):");
            try {
                Files.lines(archivoLog).forEach(System.err::println);
            } catch (IOException ioEx) {
                System.err.println("No se pudo leer el log: " + ioEx.getMessage());
            }
        }
        
        System.err.println("Stack Trace:");
        e.printStackTrace();
        System.err.println("=== FIN DEL ERROR ===\n");
    }
    
    private void cleanResources(Process process, Path archivoLog) {
        try {
            if (process != null && process.isAlive()) {
                process.destroyForcibly();
            }
        } catch (Exception e) {
            System.err.println("Error al detener el proceso: " + e.getMessage());
        }
        
        try {
            if (archivoLog != null && Files.exists(archivoLog)) {
                Files.deleteIfExists(archivoLog);
            }
        } catch (IOException e) {
            System.err.println("Error al limpiar archivo de log: " + e.getMessage());
        }
    }

    private long calculateTimeout(long fileSize) {
        return 30 + (fileSize / (100 * 1024));
    }

    public static long getTotalConversionTime() {
        return totalConversionTime.get();
    }

    public static long getSuccessCount() {
        return successCount.get();
    }

    public static long getFailureCount() {
        return failureCount.get();
    }
}