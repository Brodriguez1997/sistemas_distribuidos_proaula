package officepdf;

import java.io.IOException;

public class Processing implements Runnable {
    private final String inputFile;
    private final String outputDir;

    public Processing(String inputFile, String outputDir) {
        this.inputFile = inputFile;
        this.outputDir = outputDir;
    }

    @Override
    public void run() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                "C:/Program Files/LibreOffice/program/soffice.exe",
                "--headless",
                "--convert-to",
                "pdf",
                inputFile,
                "--outdir",
                outputDir
            );
            
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            
            if (exitCode != 0) {
                System.err.println("Error al convertir archivo: " + inputFile);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
