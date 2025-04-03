package urlpdf;

import java.io.IOException;

public class Processing implements Runnable {
    private final String url;

    public Processing(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        try {
            String path = "C:/Users/brayan antonio/eclipse-workspace/pdfs/pdfFolder/" + url.substring(url.lastIndexOf("/") + 1) + ".pdf"; // Generate unique output path
            Process process = new ProcessBuilder(
                    "C:/Program Files/Google/Chrome/Application/chrome.exe", "--headless", "--disable-gpu", "--print-to-pdf=" + path, url
            ).start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
