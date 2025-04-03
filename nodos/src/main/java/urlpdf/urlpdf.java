package urlpdf;

import java.util.Scanner;
import java.io.File;

public class urlpdf {

    public static void main(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {

            String[] myurls = {
               
            };
            int[] threadCounts = {4};

            for (int numThreads : threadCounts) {
                long inicio = System.currentTimeMillis();

                ProcessingManager processingManager = new ProcessingManager(numThreads);
                processingManager.processURLs(myurls);

                long fin = System.currentTimeMillis();
                long totalTime = fin - inicio;
                System.out.println("Tiempo con " + numThreads + " hilos: " + totalTime / 1000 + " segundos");

                cleanPdfFolder();
            }
        }
    }

    private static void cleanPdfFolder() {
        File folder = new File("C:/Users/brayan antonio/eclipse-workspace/pdfs/pdfFolder/");
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }
}