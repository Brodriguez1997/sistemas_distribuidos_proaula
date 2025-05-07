package officepdf;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

public class Processing implements Runnable {
    private final String inputFile;
    private final String outputDir;
    private static final AtomicLong successCount = new AtomicLong(0);
    private static final AtomicLong failureCount = new AtomicLong(0);

    public Processing(String inputFile, String outputDir) {
        this.inputFile = inputFile;
        this.outputDir = outputDir;
    }

    @Override
    public void run() {
        Path logFile = null;
        Process process = null;
        
        try {
            // 1. Validaciones iniciales
            Path inputPath = Paths.get(inputFile).toAbsolutePath().normalize();
            Path outputDirPath = Paths.get(outputDir).toAbsolutePath().normalize();
            
            validateFile(inputPath);
            checkLibreOfficeEnvironment();
            checkLibreOfficeComponents();
            
            // 2. Configurar archivo de log
            logFile = outputDirPath.resolve("libreoffice_conv_" + System.nanoTime() + ".log");
            
            // 3. Configurar y ejecutar proceso de conversión
            ProcessBuilder pb = createProcessBuilder(inputPath, outputDirPath, logFile);
            process = pb.start();
            
            // 4. Esperar con timeout dinámico
            long timeout = calculateTimeout(Files.size(inputPath));
            if (!process.waitFor(timeout, TimeUnit.SECONDS)) {
                process.destroyForcibly();
                throw new TimeoutException("Tiempo de conversión excedido (" + timeout + "s)");
            }
            
            // 5. Verificar resultado
            if (process.exitValue() != 0) {
                String errorDetails = Files.readString(logFile);
                throw new IOException("Error en conversión (código " + process.exitValue() + ")\n" +
                                    "Detalles LibreOffice:\n" + errorDetails);
            }
            
            successCount.incrementAndGet();
            
        } catch (Exception e) {
            failureCount.incrementAndGet();
            handleConversionError(e, inputFile, logFile);
        } finally {
            cleanResources(process, logFile);
        }
    }

    private ProcessBuilder createProcessBuilder(Path inputPath, Path outputDirPath, Path logFile) {
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
            "--writer",
            "--infilter=\"writer8\"",
            inputPath.toString(),
            "--outdir",
            outputDirPath.toString()
        );
        
        pb.redirectErrorStream(true);
        pb.redirectOutput(ProcessBuilder.Redirect.to(logFile.toFile()));
        return pb;
    }

    private void validateFile(Path filePath) throws IOException {
        // Verificar existencia y tamaño
        if (!Files.exists(filePath)) {
            throw new IOException("El archivo no existe: " + filePath);
        }
        
        long fileSize = Files.size(filePath);
        if (fileSize < 100) {
            throw new IOException("Archivo demasiado pequeño (" + fileSize + " bytes). Posible archivo corrupto.");
        }
        
        // Verificar cabecera del archivo
        byte[] header = new byte[8];
        try (InputStream is = Files.newInputStream(filePath)) {
            int bytesRead = is.read(header);
            if (bytesRead < 4) {
                throw new IOException("No se pudo leer la cabecera del archivo");
            }
            
            // Detección de formatos comunes (ZIP para ODT/DOCX o cabeceras binarias)
            boolean isZipFormat = header[0] == 0x50 && header[1] == 0x4B && 
                                header[2] == 0x03 && header[3] == 0x04;
            boolean isOfficeFormat = header[0] == (byte)0xD0 && header[1] == (byte)0xCF &&
                                   header[2] == (byte)0x11 && header[3] == (byte)0xE0;
            
            if (!isZipFormat && !isOfficeFormat) {
                throw new IOException("El archivo no parece ser un documento de Office válido");
            }
        }
    }

    private void checkLibreOfficeEnvironment() throws IOException {
        try {
            // Verificar instalación básica
            Process p = new ProcessBuilder("libreoffice", "--version").start();
            if (!p.waitFor(5, TimeUnit.SECONDS) || p.exitValue() != 0) {
                throw new IOException("LibreOffice no está instalado correctamente");
            }
            
            // Verificar directorio temporal
            Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"));
            if (!Files.isWritable(tempDir)) {
                throw new IOException("No se puede escribir en el directorio temporal: " + tempDir);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Verificación interrumpida", e);
        }
    }

    private void checkLibreOfficeComponents() throws IOException {
        try {
            Process process = new ProcessBuilder(
                "libreoffice",
                "--headless",
                "--cat",
                "macro:///Standard.Module1.ComponentExist"
            ).start();
            
            if (!process.waitFor(5, TimeUnit.SECONDS) || process.exitValue() != 0) {
                throw new IOException("Faltan componentes esenciales en LibreOffice");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Verificación interrumpida", e);
        }
    }

    private void handleConversionError(Exception e, String inputFile, Path logFile) {
        System.err.println("\n=== ERROR DE CONVERSIÓN ===");
        System.err.println("Archivo: " + inputFile);
        System.err.println("Tipo: " + e.getClass().getSimpleName());
        System.err.println("Mensaje: " + e.getMessage());
        
        // Mostrar log de LibreOffice si existe
        if (logFile != null && Files.exists(logFile)) {
            try {
                System.err.println("\nLOG DE LIBREOFFICE:");
                System.err.println(Files.readString(logFile));
            } catch (IOException ioEx) {
                System.err.println("No se pudo leer el archivo de log: " + ioEx.getMessage());
            }
        }
        
        // Consejos específicos para errores comunes
        if (e.getMessage() != null && e.getMessage().contains("código 1")) {
            System.err.println("\nPOSIBLES SOLUCIONES:");
            System.err.println("1. Verificar que el archivo no esté corrupto");
            System.err.println("2. Instalar componentes faltantes: sudo apt-get install libreoffice-writer");
            System.err.println("3. Verificar permisos del archivo");
        }
        
        System.err.println("\nStack Trace:");
        e.printStackTrace();
        System.err.println("=== FIN DEL ERROR ===\n");
    }

    private void cleanResources(Process process, Path logFile) {
        try {
            if (process != null && process.isAlive()) {
                process.destroyForcibly();
            }
        } catch (Exception e) {
            System.err.println("Error al detener el proceso: " + e.getMessage());
        }
        
        try {
            if (logFile != null && Files.exists(logFile)) {
                Files.deleteIfExists(logFile);
            }
        } catch (IOException e) {
            System.err.println("Error al limpiar archivo de log: " + e.getMessage());
        }
    }

    private long calculateTimeout(long fileSize) {
        // Tiempo base de 30 segundos + 1 segundo por cada 100KB
        return 30 + (fileSize / (100 * 1024));
    }

    public static long getSuccessCount() {
        return successCount.get();
    }

    public static long getFailureCount() {
        return failureCount.get();
    }
}