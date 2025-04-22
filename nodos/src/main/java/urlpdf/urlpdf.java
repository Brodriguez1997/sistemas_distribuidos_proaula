package urlpdf;

import java.io.File;
import java.util.Scanner;

public class urlpdf {
    private String[] urls;
    private int[] threadCounts;

    public urlpdf(String[] urls, int[] threadCounts) {
        this.urls = urls;
        this.threadCounts = threadCounts;
    }

    public void processUrls() throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            for (int numThreads : threadCounts) {
                long inicio = System.currentTimeMillis();

                ProcessingManager processingManager = new ProcessingManager(numThreads);
                processingManager.processURLs(urls);

                long fin = System.currentTimeMillis();
                long totalTime = fin - inicio;
                System.out.println("Tiempo con " + numThreads + " hilos: " + totalTime / 1000 + " segundos");

                cleanPdfFolder();
            }
        }
    }

    private void cleanPdfFolder() {
        File folder = new File("C:/Users/brayan antonio/eclipse-workspace/pdfs/pdfFolder/");
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }
}