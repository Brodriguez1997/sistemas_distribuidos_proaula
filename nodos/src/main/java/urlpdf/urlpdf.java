package urlpdf;

import java.util.Scanner;

public class UrlPdf {
    private String[] urls;
    private int[] threadCounts;
    private String outputDir;

    public UrlPdf(String[] urls, int[] threadCounts, String outputDir) {
        this.urls = urls;
        this.threadCounts = threadCounts;
        this.outputDir = outputDir;
    }

    public void processUrls() throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            for (int numThreads : threadCounts) {
                ProcessingManager processingManager = new ProcessingManager(numThreads,outputDir);
                processingManager.processURLs(urls);
            }
        }
    }
}