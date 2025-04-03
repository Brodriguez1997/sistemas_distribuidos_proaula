package officepdf;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class officepdf {

    public static void main(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {

            String[] myfiles = {
                
            };

          
            int threads= 4;
           
                long inicio = System.currentTimeMillis();

                ExecutorService executor = Executors.newFixedThreadPool(threads);

                for (String file : myfiles) {
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
        }
    

    private static void cleanOutputFolder() {
        File folder = new File("C:/Users/brayan antonio/eclipse-workspace/pdfs2/archivosSalida/");
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }
}
