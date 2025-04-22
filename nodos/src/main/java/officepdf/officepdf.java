package officepdf;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class officepdf {
    private String[] files;
    private int threads;

    public officepdf(String[] files, int threads) {
        this.files = files;
        this.threads = threads;
    }

    public void processFiles() throws Exception {
        long inicio = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        for (String file : files) {
            String inputFile = file;
            String outputPdfPath = "./archivosSalida/";
            executor.submit(new Processing(inputFile, outputPdfPath));
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

        long fin = System.currentTimeMillis();
        long totalTime = fin - inicio;
        System.out.println("Tiempo: " + totalTime / 1000 + " Segundos");

        cleanOutputFolder();
    }

    private void cleanOutputFolder() {
        File folder = new File("C:/Users/brayan antonio/eclipse-workspace/pdfs2/archivosSalida/");
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }
}