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
        Path archivoLog = null;
        Process process = null;
        
        try {
            Path inputPath = Paths.get(inputFile).toAbsolutePath().normalize();
            Path outputDirPath = Paths.get(outputDir).toAbsolutePath().normalize();
            
            // Validar el archivo antes de procesar
            verificarArchivoOffice(inputPath);
            
            archivoLog = outputDirPath.resolve("conversion_log_" + System.nanoTime() + ".txt");
            
            ProcessBuilder processBuilder = new ProcessBuilder(
                "libreoffice",
                "--headless",
                "--convert-to",
                "pdf",
                inputPath.toString(),
                "--outdir",
                outputDirPath.toString()
            );
            
            processBuilder.redirectErrorStream(true);
            processBuilder.redirectOutput(ProcessBuilder.Redirect.to(archivoLog.toFile()));
            
            process = processBuilder.start();
            
            if (!process.waitFor(30, TimeUnit.SECONDS)) {
                process.destroyForcibly();
                throw new TimeoutException("Tiempo de conversión excedido");
            }
            
            if (process.exitValue() != 0) {
                String errorDetails = leerLogError(archivoLog);
                throw new IOException("Error en conversión (código " + process.exitValue() + ")\n" + errorDetails);
            }
            
            successCount.incrementAndGet();
            
        } catch (Exception e) {
            failureCount.incrementAndGet();
            manejarError(e, inputFile, archivoLog);
        } finally {
            limpiarRecursos(process, archivoLog);
        }
    }
    
    private String leerLogError(Path archivoLog) {
        try {
            return new String(Files.readAllBytes(archivoLog), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "No se pudo leer el archivo de log: " + e.getMessage();
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
    
    private void limpiarRecursos(Process process, Path archivoLog) {
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

    private boolean esArchivoOfficeValido(byte[] cabecera) {
        return esDocValido(cabecera) || esDocxValido(cabecera) || 
               esPptValido(cabecera) || esXlsValido(cabecera) ||
               esPptxValido(cabecera) || esXlsxValido(cabecera);
    }

    private boolean esDocValido(byte[] cabecera) {
        return cabecera.length >= 8 && 
               cabecera[0] == (byte) 0xD0 && 
               cabecera[1] == (byte) 0xCF &&
               cabecera[2] == (byte) 0x11 && 
               cabecera[3] == (byte) 0xE0;
    }

    private boolean esDocxValido(byte[] cabecera) {
        return cabecera.length >= 4 && 
               cabecera[0] == (byte) 0x50 && 
               cabecera[1] == (byte) 0x4B &&
               cabecera[2] == (byte) 0x03 && 
               cabecera[3] == (byte) 0x04;
    }

    private boolean esPptValido(byte[] cabecera) {
        return esDocValido(cabecera);
    }

    private boolean esXlsValido(byte[] cabecera) {
        return esDocValido(cabecera);
    }

    private boolean esPptxValido(byte[] cabecera) {
        return esDocxValido(cabecera);
    }

    private boolean esXlsxValido(byte[] cabecera) {
        return esDocxValido(cabecera);
    }

    private void verificarArchivoOffice(Path rutaArchivo) throws IOException {
        byte[] cabecera = new byte[8];
        try (InputStream is = Files.newInputStream(rutaArchivo)) {
            int bytesLeidos = is.read(cabecera);
            if (bytesLeidos < 8 || !esArchivoOfficeValido(cabecera)) {
                throw new IOException("El archivo no parece ser un documento de Office válido");
            }
        }
    }
}