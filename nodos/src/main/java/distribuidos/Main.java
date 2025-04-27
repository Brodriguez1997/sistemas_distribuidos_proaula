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

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

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
                String[] urls = {request.getUrl()};
                int[] threadCounts = {4};
                
                // Configurar directorio de salida temporal
                String tempDir = System.getProperty("java.io.tmpdir") + "pdf_output/";
                new File(tempDir).mkdirs();
                
                UrlPdf processor = new UrlPdf(urls, threadCounts, tempDir);
                processor.processUrls();
                
                // Leer el PDF generado y convertirlo a base64
                String pdfName = request.getNombre() + ".pdf";
                String pdfPath = tempDir + pdfName;
                byte[] pdfBytes = Files.readAllBytes(Paths.get(pdfPath));
                String pdfBase64 = Base64.getEncoder().encodeToString(pdfBytes);
                
                // Eliminar archivo temporal
                Files.deleteIfExists(Paths.get(pdfPath));
                
                responseObserver.onNext(
                    ConvertirUrlsResponse.newBuilder()
                    .addResultados(pdfBase64) // Envía el PDF en base64
                    .build()
                );
                responseObserver.onCompleted();
            } catch (Exception e) {
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
                // Decodificar el base64 a archivo temporal
                byte[] fileBytes = Base64.getDecoder().decode(request.getArchivo());
                String tempDir = System.getProperty("java.io.tmpdir");
                String tempFilePath = tempDir + request.getNombre();
                Path path = Paths.get(tempFilePath);
                Files.write(path, fileBytes);

                // Procesar el archivo
                String[] files = {tempFilePath};
                int threads = 4;
                String outputDir = tempDir + "pdf_output/";
                new File(outputDir).mkdirs(); // Crear directorio si no existe
                
                OfficePdf processor = new OfficePdf(files, threads, outputDir);
                processor.processFiles();
                
                // Leer el PDF generado y convertirlo a base64
                String pdfPath = outputDir + request.getNombre().replaceFirst("[.][^.]+$", "") + ".pdf";
                byte[] pdfBytes = Files.readAllBytes(Paths.get(pdfPath));
                String pdfBase64 = Base64.getEncoder().encodeToString(pdfBytes);
                
                // Limpieza: eliminar archivos temporales
                Files.deleteIfExists(path);
                Files.deleteIfExists(Paths.get(pdfPath));
                
                responseObserver.onNext(
                    ConvertirArchivosResponse.newBuilder()
                    .addResultados(pdfBase64) // Envía el PDF en base64
                    .build()
                );
                responseObserver.onCompleted();
                 
            long duration = System.currentTimeMillis() - startTime;
            long memoryUsed = memoryBean.getHeapMemoryUsage().getUsed() / (1024 * 1024); // MB
            
            System.out.printf(
                "gRPC Request: %s, Tiempo: %dms, Memoria: %dMB%n",
                request.getNombre(), duration, memoryUsed
            );
            
            responseObserver.onNext(ConvertirArchivosResponse.newBuilder()
                .addResultados(pdfBase64)
                .build());
            responseObserver.onCompleted();

            } catch (Exception e) {
                System.err.printf("Error en gRPC: %s%n", e.getMessage());
                responseObserver.onError(e);
            }
        
        }

        @Override
        public void saludar(SaludarRequest request,
                          StreamObserver<SaludarResponse> responseObserver) {
            // Nueva implementación del método Saludar
            String mensajeRecibido = request.getMensaje();
            String respuesta = "Hola desde el servidor! Recibí tu mensaje: " + mensajeRecibido;
            System.out.println(request);
            
            responseObserver.onNext(
                SaludarResponse.newBuilder()
                    .setRespuesta(respuesta)
                    .build()
            );
            responseObserver.onCompleted();
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