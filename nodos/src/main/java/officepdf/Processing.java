package officepdf;

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
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        long cpuStart = threadBean.getCurrentThreadCpuTime();
        
        try {
            // 1. Preparación del nombre de archivo
            String baseName = new File(inputFile).getName().replaceFirst("[.][^.]+$", "");
            String outputName = String.format("officepdf_%d_%d_%s.pdf", 
                System.nanoTime(), 
                Thread.currentThread().getId(), 
                baseName);
            String outputPath = outputDir + File.separator + outputName;

            // 2. Configuración del proceso
            ProcessBuilder processBuilder = new ProcessBuilder(
                "libreoffice",
                "--headless",
                "--convert-to",
                "pdf:writer_pdf_Export",
                "--nologo",
                "--norestore",
                "--nodefault",
                "--nolockcheck",
                "--invisible",
                inputFile,
                "--outdir",
                outputDir
            );

            // 3. Redirección de errores y salida
            File logFile = new File(outputDir + File.separator + "libreoffice_log_" + System.nanoTime() + ".txt");
            processBuilder.redirectErrorStream(true);
            processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(logFile));

            // 4. Variables para capturar salida
            StringBuilder processOutput = new StringBuilder();
            
            // 5. Ejecución del proceso
            Process process = processBuilder.start();
            
            // Leer la salida del proceso en un hilo separado
            Thread outputReader = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        processOutput.append(line).append("\n");
                    }
                } catch (IOException e) {
                    System.err.println("Error leyendo salida del proceso: " + e.getMessage());
                }
            });
            outputReader.start();

            // 6. Esperar por la finalización
            int exitCode = process.waitFor();
            outputReader.join(); // Asegurar que hemos leído toda la salida
            
            long endTime = System.currentTimeMillis();
            long cpuEnd = threadBean.getCurrentThreadCpuTime();
            long duration = endTime - startTime;
            long cpuUsage = (cpuEnd - cpuStart) / 1_000_000;

            // 7. Manejo de resultados
            if (exitCode == 0) {
                successCount.incrementAndGet();
                System.out.printf(
                    "Conversión exitosa: %s -> %s (Tiempo: %dms, CPU: %dms)%n",
                    inputFile, outputName, duration, cpuUsage
                );
                
                // Verificar que realmente se creó el PDF
                if (!Files.exists(Paths.get(outputPath))) {
                    throw new IOException("El archivo PDF de salida no se creó: " + outputPath);
                }
            } else {
                failureCount.incrementAndGet();
                System.err.printf(
                    "Error en conversión: %s (Código: %d)%nDetalles:%n%s%n",
                    inputFile, exitCode, processOutput.toString()
                );
                
                // Guardar el archivo problemático para diagnóstico
                Path problematicFile = Paths.get(outputDir + File.separator + "problematic_" + baseName);
                Files.copy(Paths.get(inputFile), problematicFile, StandardCopyOption.REPLACE_EXISTING);
                System.err.println("Se guardó copia del archivo problemático en: " + problematicFile);
            }
        } catch (Exception e) {
            failureCount.incrementAndGet();
            System.err.println("\n=== ERROR CRÍTICO ===");
            System.err.println("Archivo: " + inputFile);
            System.err.println("Tipo: " + e.getClass().getSimpleName());
            System.err.println("Mensaje: " + e.getMessage());
            System.err.println("Stack Trace:");
            e.printStackTrace();
            System.err.println("====================\n");
            
            // Intenta limpiar archivos temporales
            try {
                Files.deleteIfExists(Paths.get(inputFile));
            } catch (IOException ioEx) {
                System.err.println("No se pudo limpiar archivo temporal: " + ioEx.getMessage());
            }
        } finally {
            // 8. Limpieza de recursos
            try {
                Files.deleteIfExists(Paths.get(inputFile));
            } catch (IOException e) {
                System.err.println("Advertencia: No se pudo eliminar el archivo temporal " + inputFile);
            }
        }
    }
    // Métodos estáticos para acceder a métricas globales
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
