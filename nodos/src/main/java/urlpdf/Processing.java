package urlpdf;

import java.io.IOException;

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
            // Extraer nombre del archivo de la URL
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            
            // Limpiar el nombre de archivo para evitar caracteres inválidos
            fileName = fileName.replaceAll("[^a-zA-Z0-9.-]", "_") + ".pdf";
            
            // Ruta completa de salida
            String outputPath = outputDir + fileName;
            
            // Ejecutar Chrome en modo headless para generar el PDF
            Process process = new ProcessBuilder(
                "C:/Program Files/Google/Chrome/Application/chrome.exe",
                "--headless",
                "--disable-gpu",
                "--print-to-pdf=" + outputPath,
                url
            ).start();
            
            // Esperar a que termine el proceso
            int exitCode = process.waitFor();
            
            if (exitCode != 0) {
                System.err.println("Error al generar PDF para URL: " + url);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}