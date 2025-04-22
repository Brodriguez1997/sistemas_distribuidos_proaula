package officepdf;

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
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        for (String file : files) {
            String outputPdfPath = "./archivosSalida/";
            executor.submit(new Processing(file, outputPdfPath));
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    }
}