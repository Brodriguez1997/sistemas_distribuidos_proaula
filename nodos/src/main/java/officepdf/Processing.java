package officepdf;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.io.IOException;
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
        try {
            // 1. Normalización de rutas y nombres
            Path inputPath = Paths.get(inputFile).toAbsolutePath().normalize();
            Path outputDirPath = Paths.get(outputDir).toAbsolutePath().normalize();
            
            // 2. Validación de archivo de entrada
            if (!Files.exists(inputPath)) {
                throw new IOException("Archivo de entrada no existe: " + inputPath);
            }
            if (Files.size(inputPath) == 0) {
                throw new IOException("Archivo de entrada está vacío");
            }

            // 3. Crear nombre de salida consistente
            String baseName = inputPath.getFileName().toString()
                .replaceFirst("[.][^.]+$", "")
                .toLowerCase()
                .replaceAll("[^a-z0-9]", "_");
            
            String outputName = "officepdf_" + System.nanoTime() + "_" + 
                            Thread.currentThread().getId() + "_" + baseName + ".pdf";
            Path outputPath = outputDirPath.resolve(outputName);

            // 4. Configuración robusta de LibreOffice
            ProcessBuilder processBuilder = new ProcessBuilder(
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

            // 5. Redirección de logs detallada
            Path logFile = outputDirPath.resolve("libreoffice_log_" + System.nanoTime() + ".log");
            processBuilder.redirectErrorStream(true);
            processBuilder.redirectOutput(ProcessBuilder.Redirect.to(logFile.toFile()));

            // 6. Ejecución con timeout dinámico
            Process process = processBuilder.start();
            long timeout = calculateTimeout(Files.size(inputPath));
            boolean finished = process.waitFor(timeout, TimeUnit.SECONDS);

            if (!finished) {
                process.destroyForcibly();
                throw new TimeoutException("Tiempo de conversión excedido (" + timeout + " segundos)");
            }

            // 7. Validación estricta del resultado
            if (process.exitValue() == 0) {
                if (!Files.exists(outputPath)) {
                    // Buscar cualquier PDF recién creado como fallback
                    Optional<Path> generatedPdf = Files.list(outputDirPath)
                        .filter(p -> p.toString().endsWith(".pdf"))
                        .filter(p -> {
                            try {
                                return Files.getLastModifiedTime(p).toMillis() > startTime;
                            } catch (IOException e) {
                                return false;
                            }
                        })
                        .findFirst();
                    
                    if (generatedPdf.isPresent()) {
                        try {
                            Files.move(generatedPdf.get(), outputPath, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            throw new IOException("No se pudo mover el PDF generado a la ubicación esperada", e);
                        }
                    } else {
                        throw new IOException("LibreOffice reportó éxito pero no generó PDF");
                    }
                }
                successCount.incrementAndGet();
            } else {
                throw new IOException("Error en conversión (código " + process.exitValue() + ")");
            }
        } catch (Exception e) {
            failureCount.incrementAndGet();
            System.err.println("Error en el procesamiento del archivo: " + inputFile);
            System.err.println("Tipo de error: " + e.getClass().getSimpleName());
            System.err.println("Mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private long calculateTimeout(long fileSize) {
        // 30 segundos base + 1 segundo por cada 100KB
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