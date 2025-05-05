package officepdf;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.atomic.AtomicLong;

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
            // Generar nombre único para el PDF de salida
            String baseName = inputFile.substring(inputFile.lastIndexOf('/') + 1)
                                .replaceFirst("[.][^.]+$", "");
            String outputName = "officepdf_" + System.nanoTime() + "_" + 
                            Thread.currentThread().getId() + "_" + baseName + ".pdf";
            String outputPath = outputDir + outputName;

            ProcessBuilder processBuilder = new ProcessBuilder(
                "C:/Program Files/LibreOffice/program/soffice.exe",
                "--headless",
                "--convert-to",
                "pdf",
                inputFile,
                "--outdir",
                outputDir,
                "--writer",
                "--infilter=\"Microsoft Word 2007-2013 XML\""
            );

            processBuilder.redirectErrorStream(true);
            
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            long endTime = System.currentTimeMillis();
            long cpuEnd = threadBean.getCurrentThreadCpuTime();
            
            long duration = endTime - startTime;
            long cpuUsage = (cpuEnd - cpuStart) / 1_000_000;

            totalConversionTime.addAndGet(duration);
            if (exitCode == 0) {
                successCount.incrementAndGet();
                System.out.printf(
                    "Conversión exitosa: %s -> %s (Tiempo: %dms, CPU: %dms)%n",
                    inputFile, outputName, duration, cpuUsage
                );
            } else {
                failureCount.incrementAndGet();
                System.err.printf(
                    "Error en conversión: %s (Código: %d)%n",
                    inputFile, exitCode
                );
            }
        } catch (IOException | InterruptedException e) {
            failureCount.incrementAndGet();
            System.err.println("Error en Processing.run(): " + e.getMessage());
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
