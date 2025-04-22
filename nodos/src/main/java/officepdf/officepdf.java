package officepdf;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OfficePdf {
    private String[] files;
    private int threads;
    private String outputPdfPath;

    public OfficePdf(String[] files, int threads, String outputPdfPath) {
        this.files = files;
        this.threads = threads;
        this.outputPdfPath = outputPdfPath;
    }

    public void processFiles() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        for (String file : files) {
            executor.submit(new Processing(file, outputPdfPath));
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    }
}