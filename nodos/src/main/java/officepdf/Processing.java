package officepdf;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            Path inputPath = Paths.get(inputFile);
            Path outputDirPath = Paths.get(outputDir);
            
            // Verificaciones previas
            verifySystemEnvironment();
            
            // Configurar log
            logFile = outputDirPath.resolve("libreoffice_conv_" + System.nanoTime() + ".log");
            
            // Proceso de conversión
            ProcessBuilder pb = new ProcessBuilder(
                "libreoffice",
                "--headless",
                "--convert-to",
                "pdf",
                "--nologo",
                "--norestore",
                "--nodefault",
                "--nolockcheck",
                "--invisible",
                inputPath.toString(),
                "--outdir",
                outputDirPath.toString()
            );
            
            pb.redirectErrorStream(true);
            pb.redirectOutput(ProcessBuilder.Redirect.to(logFile.toFile()));
            
            process = pb.start();
            
            if (!process.waitFor(2, TimeUnit.MINUTES)) {
                process.destroyForcibly();
                throw new TimeoutException("Tiempo de conversión excedido");
            }
            
            if (process.exitValue() != 0) {
                throw new IOException("Error en conversión (código " + process.exitValue() + ")");
            }
            
            successCount.incrementAndGet();
            
        } catch (Exception e) {
            failureCount.incrementAndGet();
            handleError(e, logFile);
        } finally {
            cleanResources(process, logFile);
        }
    }

    private void verifySystemEnvironment() throws IOException {
        // 1. Verificar instalación básica
        checkCommandSuccess("libreoffice --version", "LibreOffice no está instalado correctamente");
        
        // 2. Verificar componentes
        checkCommandSuccess("libreoffice --headless --cat macro:///Standard.Module1.WriterCheck", 
                          "Componente Writer faltante");
        checkCommandSuccess("libreoffice --headless --cat macro:///Standard.Module1.CalcCheck", 
                          "Componente Calc faltante");
        
        // 3. Verificar directorios
        if (!Files.isWritable(Paths.get(outputDir))) {
            throw new IOException("No se puede escribir en el directorio de salida: " + outputDir);
        }
    }

    private void checkCommandSuccess(String command, String errorMessage) throws IOException {
        try {
            Process p = Runtime.getRuntime().exec(command);
            if (!p.waitFor(10, TimeUnit.SECONDS) || p.exitValue() != 0) {
                throw new IOException(errorMessage);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Verificación interrumpida: " + command, e);
        }
    }

    private void handleError(Exception e, Path logFile) {
        System.err.println("\n=== ERROR DETALLADO ===");
        System.err.println("Archivo: " + inputFile);
        System.err.println("Tipo: " + e.getClass().getSimpleName());
        System.err.println("Mensaje: " + e.getMessage());
        
        // Mostrar log si existe
        if (logFile != null && Files.exists(logFile)) {
            try {
                System.err.println("\nLOG DE LIBREOFFICE:");
                System.err.println(Files.readString(logFile));
            } catch (IOException ioEx) {
                System.err.println("Error leyendo log: " + ioEx.getMessage());
            }
        }
        
        // Consejos específicos
        if (e.getMessage() != null && e.getMessage().contains("faltante")) {
            System.err.println("\nSOLUCIÓN RECOMENDADA:");
            System.err.println("Ejecute estos comandos como administrador:");
            System.err.println("sudo apt-get update");
            System.err.println("sudo apt-get install libreoffice-writer libreoffice-calc libreoffice-impress");
        }
        
        e.printStackTrace();
    }

    private void cleanResources(Process process, Path logFile) {
        try {
            if (process != null && process.isAlive()) {
                process.destroyForcibly();
            }
        } catch (Exception e) {
            System.err.println("Error limpiando proceso: " + e.getMessage());
        }
        
        try {
            if (logFile != null && Files.exists(logFile)) {
                Files.deleteIfExists(logFile);
            }
        } catch (IOException e) {
            System.err.println("Error limpiando archivo de log: " + e.getMessage());
        }
    }

    public static long getSuccessCount() {
        return successCount.get();
    }

    public static long getFailureCount() {
        return failureCount.get();
    }
}