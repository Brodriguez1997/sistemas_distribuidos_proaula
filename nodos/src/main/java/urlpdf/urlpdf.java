package urlpdf;

public class UrlPdf {
    private String[] urls;
    private int[] threadCounts;
    private String outputDir;

    public UrlPdf(String[] urls, int[] threadCounts, String outputDir) {
        this.urls = urls;
        this.threadCounts = threadCounts;
        // Asegurar que el directorio termine con /
        this.outputDir = outputDir.endsWith("/") ? outputDir : outputDir + "/";
    }

    public void processUrls() throws Exception {
        System.out.println("Iniciando procesamiento de URLs...");
        for (int numThreads : threadCounts) {
            System.out.println("Usando " + numThreads + " hilos");
            ProcessingManager processingManager = new ProcessingManager(numThreads, outputDir);
            processingManager.processURLs(urls);
        }
    }
}