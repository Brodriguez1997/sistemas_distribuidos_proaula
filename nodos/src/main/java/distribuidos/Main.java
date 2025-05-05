package distribuidos;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import officepdf.OfficePdf;
import officepdf.Processing;
import urlpdf.UrlPdf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import distribuidos.proto.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Comparator;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private Server server;

    public static void main(String[] args) throws IOException, InterruptedException {
        final Main server = new Main();
        server.start();
        server.blockUntilShutdown();
    }

    private void start() throws IOException {
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new UrlConverterService())
                .addService(new OfficeConverterService())
                .build()
                .start();
        System.out.println("Server started on port " + port);
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    static class UrlConverterService extends ConvertidorUrlsGrpc.ConvertidorUrlsImplBase {
        @Override
        public void convertirUrls(ConvertirUrlsRequest request, 
                StreamObserver<ConvertirUrlsResponse> responseObserver) {
            try {
                // Preparar URLs para procesamiento
                List<String> urlList = new ArrayList<>();
                List<String> nombres = new ArrayList<>();
                
                for (UrlItem item : request.getUrlsList()) {
                    urlList.add(item.getUrl());
                    nombres.add(item.getNombre());
                }
                
                String[] urls = urlList.toArray(new String[0]);
                int[] threadCounts = {4};
                
                // Configurar directorio de salida temporal
                String tempDir = System.getProperty("java.io.tmpdir") + "/pdf_output/";
                new File(tempDir).mkdirs();
                
                // Limpiar directorio temporal antes de empezar
                Files.list(Paths.get(tempDir))
                    .filter(path -> path.toString().endsWith(".pdf"))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            System.err.println("Error al limpiar archivo temporal: " + path);
                        }
                    });
                
                UrlPdf processor = new UrlPdf(urls, threadCounts, tempDir);
                processor.processUrls();
                
                // Preparar respuesta
                ConvertirUrlsResponse.Builder responseBuilder = ConvertirUrlsResponse.newBuilder();
                
                // Buscar TODOS los PDFs generados en el directorio temporal
                List<Path> generatedPdfs = Files.list(Paths.get(tempDir))
                                        .filter(path -> path.toString().startsWith(tempDir + "pdf_") && 
                                                    path.toString().endsWith(".pdf"))
                                        .sorted(Comparator.comparingLong(p -> {
                                            String name = p.getFileName().toString();
                                            return Long.parseLong(name.split("_")[1]);
                                        }))
                                        .collect(Collectors.toList());

                // Procesar los PDFs generados
                for (int i = 0; i < Math.min(generatedPdfs.size(), nombres.size()); i++) {
                    Path pdfPath = generatedPdfs.get(i);
                    byte[] pdfBytes = Files.readAllBytes(pdfPath);
                    String pdfBase64 = Base64.getEncoder().encodeToString(pdfBytes);
                    responseBuilder.addResultados(pdfBase64);
                    Files.delete(pdfPath);
                }
                
                responseObserver.onNext(responseBuilder.build());
                responseObserver.onCompleted();
            } catch (Exception e) {
                System.err.println("Error en convertirUrls: " + e.getMessage());
                responseObserver.onError(e);
            }
        }
    }

    static class OfficeConverterService extends ConvertidorOfficeGrpc.ConvertidorOfficeImplBase {
        private static final AtomicInteger totalRequests = new AtomicInteger(0);
        
        @Override
        public void convertirArchivos(ConvertirArchivosRequest request, 
            StreamObserver<ConvertirArchivosResponse> responseObserver) {
            totalRequests.incrementAndGet();
            long startTime = System.currentTimeMillis();
            MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
            
            try {
                String tempDir = System.getProperty("java.io.tmpdir");
                String outputDir = tempDir + "/pdf_output/";
                new File(outputDir).mkdirs();
                
                // Preparar archivos para procesamiento
                List<String> filePaths = new ArrayList<>();
                List<String> nombres = new ArrayList<>();
                
                for (ArchivoItem item : request.getArchivosList()) {
                    byte[] fileBytes = Base64.getDecoder().decode(item.getContenidoBase64());
                    String tempFilePath = tempDir + "office_" + System.nanoTime() + "_" + item.getNombre();
                    Files.write(Paths.get(tempFilePath), fileBytes);
                    filePaths.add(tempFilePath);
                    nombres.add(item.getNombre());
                }

                // Procesar archivos
                String[] files = filePaths.toArray(new String[0]);
                int threads = 4;
                OfficePdf processor = new OfficePdf(files, threads, outputDir);
                processor.processFiles();
                
                // Preparar respuesta
                ConvertirArchivosResponse.Builder responseBuilder = ConvertirArchivosResponse.newBuilder();
                
                // Buscar todos los PDFs generados recientemente
                List<Path> generatedPdfs = Files.list(Paths.get(outputDir))
                                        .filter(path -> path.toString().endsWith(".pdf") && 
                                                    path.toString().contains("officepdf_"))
                                        .sorted(Comparator.comparing(path -> {
                                            String name = path.getFileName().toString();
                                            return Long.parseLong(name.split("_")[1]);
                                        }))
                                        .collect(Collectors.toList());

                // Asociar cada PDF generado con su archivo original
                for (int i = 0; i < Math.min(generatedPdfs.size(), nombres.size()); i++) {
                    Path pdfPath = generatedPdfs.get(i);
                    byte[] pdfBytes = Files.readAllBytes(pdfPath);
                    String pdfBase64 = Base64.getEncoder().encodeToString(pdfBytes);
                    responseBuilder.addResultados(pdfBase64);
                    
                    // Limpieza
                    Files.deleteIfExists(Paths.get(filePaths.get(i)));
                    Files.deleteIfExists(pdfPath);
                }
                
                // Registrar métricas
                long duration = System.currentTimeMillis() - startTime;
                long memoryUsed = memoryBean.getHeapMemoryUsage().getUsed() / (1024 * 1024);
                
                System.out.println("\n=== Métricas del Servicio ===");
                System.out.println("Total de peticiones: " + totalRequests.get());
                System.out.printf("Última conversión - Tiempo: %dms | Memoria usada: %dMB\n",
                    duration, memoryUsed);
                System.out.printf("Estadísticas de conversión: %d éxitos, %d fallos\n",
                    Processing.getSuccessCount(), Processing.getFailureCount());
                
                responseObserver.onNext(responseBuilder.build());
                responseObserver.onCompleted();
            } catch (Exception e) {
                System.err.printf("Error en gRPC: %s%n", e.getMessage());
                responseObserver.onError(e);
            }
        }
    }


    public class OfficePdf2 {
        private String[] files;
        private int threads;
        private String outputPdfPath;

        public void processFiles() throws Exception {
            long startTime = System.currentTimeMillis();
            ExecutorService executor = Executors.newFixedThreadPool(threads);

            for (String file : files) {
                executor.submit(new Processing(file, outputPdfPath));
            }

            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            
            long totalTime = System.currentTimeMillis() - startTime;
            System.out.printf(
                "Resumen: %d archivos, %d hilos, Tiempo total: %dms%n",
                files.length, threads, totalTime
            );
        }
    }
}