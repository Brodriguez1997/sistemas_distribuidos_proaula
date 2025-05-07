package distribuidos;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.Status;
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
            private static final String FILE_SEPARATOR = File.separator;
            
            // Configurar directorios
            String tempDir = System.getProperty("java.io.tmpdir");
            if (tempDir.endsWith(FILE_SEPARATOR)) {
                tempDir = tempDir.substring(0, tempDir.length() - 1);
            }
            String outputDir = tempDir + FILE_SEPARATOR + "pdf_output" + File.separator;
            List<String> filePaths = new ArrayList<>();
            List<String> nombres = new ArrayList<>();
            
            try {
                // 1. Preparar directorio de salida
                System.out.println("Directorio de salida: " + outputDir);
                Stream Files.createDirectories(Paths.get(outputDir));
                
                // 2. Procesar cada archivo recibido
                for (ArchivoItem item : request.getArchivosList()) {
                    // Limpiar nombre de archivo (eliminar caracteres especiales y espacios)
                    String nombreLimpio = item.getNombre()
                        .replaceAll("[^a-zA-Z0-9.-]", "_")
                        .replace(" ", "_");
                    
                    String tempFilePath = tempDir + File.separator + "office_" + 
                                        System.nanoTime() + "_" + nombreLimpio;
                    
                    // Escribir archivo temporal
                    byte[] fileBytes = Base64.getDecoder().decode(item.getContenidoBase64());
                    Files.write(Paths.get(tempFilePath), fileBytes);
                    
                    // Verificar que se creó correctamente
                    if (!Files.exists(Paths.get(tempFilePath))) {
                        throw new IOException("No se pudo crear el archivo temporal: " + tempFilePath);
                    }
                    
                    filePaths.add(tempFilePath);
                    nombres.add(nombreLimpio);
                }

                System.out.println("tempDir: " + tempDir);
                System.out.println("outputDir: " + outputDir);

                // 3. Procesar archivos con hilos
                String[] files = filePaths.toArray(new String[0]);
                int threads = Math.min(4, Runtime.getRuntime().availableProcessors());
                OfficePdf processor = new OfficePdf(files, threads, outputDir);
                processor.processFiles();
                
                // 4. Preparar respuesta
                ConvertirArchivosResponse.Builder responseBuilder = ConvertirArchivosResponse.newBuilder();
                
                // Buscar PDFs generados
                List<Path> pdfsGenerados = Files.list(Paths.get(outputDir))
                                .filter(path -> path.toString().endsWith(".pdf") && 
                                            path.toString().contains("officepdf_"))
                                .sorted(Comparator.comparing(path -> {
                                    String nombre = path.getFileName().toString();
                                    return Long.parseLong(nombre.split("_")[1]);
                                }))
                                .collect(Collectors.toList());

                // 5. Agregar resultados a la respuesta
                for (int i = 0; i < Math.min(pdfsGenerados.size(), nombres.size()); i++) {
                    try {
                        byte[] pdfBytes = Files.readAllBytes(pdfsGenerados.get(i));
                        String pdfBase64 = Base64.getEncoder().encodeToString(pdfBytes);
                        responseBuilder.addResultados(pdfBase64);
                    } catch (IOException e) {
                        System.err.println("Error leyendo PDF generado: " + pdfsGenerados.get(i));
                        responseBuilder.addResultados(""); // Resultado vacío para conversiones fallidas
                    }
                }
                
                // 6. Enviar respuesta
                responseObserver.onNext(responseBuilder.build());
                responseObserver.onCompleted();
                
            } catch (Exception e) {
                // 7. Manejar errores adecuadamente
                String mensajeError = "Error al procesar archivos: " + e.getMessage();
                System.err.println(mensajeError);
                e.printStackTrace();
                
                responseObserver.onError(Status.INTERNAL
                    .withDescription(mensajeError)
                    .withCause(e)
                    .asRuntimeException());
            } finally {
                // 8. Limpieza de archivos temporales
                limpiarArchivosTemporales(filePaths, outputDir);
                
                // 9. Registrar métricas
                registrarMetricas(startTime, memoryBean);
            }
        }

        private void limpiarArchivosTemporales(List<String> archivosTemporales, String directorioSalida) {
            // Limpiar archivos de entrada
            for (String archivo : archivosTemporales) {
                try {
                    Files.deleteIfExists(Paths.get(archivo));
                } catch (IOException e) {
                    System.err.println("No se pudo eliminar archivo temporal: " + archivo);
                }
            }
            
            // Limpiar PDFs generados
            try {
                Files.list(Paths.get(directorioSalida))
                    .filter(path -> path.toString().contains("officepdf_"))
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                            System.err.println("No se pudo eliminar PDF temporal: " + path);
                        }
                    });
            } catch (IOException e) {
                System.err.println("Error limpiando directorio de salida: " + e.getMessage());
            }
        }

        // Método auxiliar para métricas
        private void registrarMetricas(long inicio, MemoryMXBean memoria) {
            long duracion = System.currentTimeMillis() - inicio;
            long memoriaUsada = memoria.getHeapMemoryUsage().getUsed() / (1024 * 1024);
            
            System.out.println("\n=== Métricas del Servicio ===");
            System.out.println("Total de peticiones: " + totalRequests.get());
            System.out.printf("Última conversión - Tiempo: %dms | Memoria usada: %dMB\n",
                duracion, memoriaUsada);
            System.out.printf("Estadísticas: %d éxitos, %d fallos\n",
                Processing.getSuccessCount(), Processing.getFailureCount());
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