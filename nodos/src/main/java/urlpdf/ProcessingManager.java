package urlpdf;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProcessingManager {
    private final int numThreads;
    private final String outputDir;

    public ProcessingManager(int numThreads, String outputDir) {
        this.numThreads = numThreads;
        this.outputDir = outputDir;
    }

    public void processURLs(String[] urls) {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (String url : urls) {
            executor.submit(new Processing(url, outputDir));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
