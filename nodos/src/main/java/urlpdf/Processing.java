package urlpdf;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Processing implements Runnable {
    private final String url;
    private final String outputDir;

    public Processing(String url, String outputDir) {
        this.url = url;
        this.outputDir = outputDir;
    }

    @Override
    public void run() {
        try {
            String fileName = "pdf_" + System.nanoTime() + "_" + 
                            Thread.currentThread().getId() + ".pdf";
            String outputPath = outputDir + fileName;
            
            System.out.println("Generando PDF desde URL: " + url);
            System.out.println("Ruta de salida: " + outputPath);

            // Comando para Linux
            ProcessBuilder processBuilder = new ProcessBuilder(
                "google-chrome",
                "--headless",
                "--disable-gpu",
                "--print-to-pdf=" + outputPath,
                "--no-sandbox",
                "--disable-dev-shm-usage",
                url
            );

            processBuilder.redirectErrorStream(true);
            
            Process process = processBuilder.start();
            boolean finished = process.waitFor(30, TimeUnit.SECONDS);
            
            if (!finished) {
                process.destroy();
                throw new RuntimeException("Timeout al generar PDF");
            }

            if (process.exitValue() != 0) {
                throw new RuntimeException("Chrome falló con código: " + process.exitValue());
            }

            System.out.println("PDF generado exitosamente: " + outputPath);
        } catch (Exception e) {
            System.err.println("Error en Processing.run(): " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}