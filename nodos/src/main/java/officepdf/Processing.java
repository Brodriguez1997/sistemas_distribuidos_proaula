package officepdf;

import java.io.IOException;

public class Processing implements Runnable {
    private final String inputFile;
    private final String outputPdfPath;

    public Processing(String inputFile, String outputPdfPath) {
        this.inputFile = inputFile;
        this.outputPdfPath = outputPdfPath;
    }

    @Override
    public void run() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "C:/Program Files/LibreOffice/program/soffice.exe", "--headless", "--convert-to", "pdf", inputFile, "--outdir", outputPdfPath
            );
            Process process = processBuilder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
