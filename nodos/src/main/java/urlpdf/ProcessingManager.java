package urlpdf;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProcessingManager {

    private final int numThreads;

    public ProcessingManager(int numThreads) {
        this.numThreads = numThreads;
    }

    public void processURLs(String[] urls) {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (String url : urls) {
            executor.submit(new Processing(url));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS); // Wait for completion
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
