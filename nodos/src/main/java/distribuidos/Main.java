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
import java.nio.file.attribute.PosixFilePermissions;
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
        private static final String FILE_SEPARATOR = File.separator;

        @Override
        public void convertirArchivos(ConvertirArchivosRequest request,
                                    StreamObserver<ConvertirArchivosResponse> responseObserver) {
            totalRequests.incrementAndGet();
            long startTime = System.currentTimeMillis();
            MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
            
            // Configurar directorios
            Path tempDir = Paths.get(System.getProperty("java.io.tmpdir")).normalize();
            Path outputDir = tempDir.resolve("pdf_output");
            List<String> filePaths = new ArrayList<>();
            List<String> nombres = new ArrayList<>();

            try {
                // 1. Verificar instalación de LibreOffice
                verifyLibreOfficeInstallation();
                
                // 2. Preparar directorio de salida
                prepareOutputDirectory(outputDir);
                
                System.out.println("Directorio temporal: " + tempDir);
                System.out.println("Directorio de salida: " + outputDir);

                // 3. Procesar cada archivo recibido
                for (ArchivoItem item : request.getArchivosList()) {
                    String nombreLimpio = sanitizeFilename(item.getNombre());
                    Path tempFilePath = tempDir.resolve("office_" + System.nanoTime() + "_" + nombreLimpio);
                    
                    // Escribir archivo temporal
                    byte[] fileBytes = Base64.getDecoder().decode(item.getContenidoBase64());
                    Files.write(tempFilePath, fileBytes);
                    
                    // Validar archivo temporal
                    validateTempFile(tempFilePath);
                    
                    filePaths.add(tempFilePath.toString());
                    nombres.add(nombreLimpio);
                }

                // 4. Procesar archivos con hilos
                String[] files = filePaths.toArray(new String[0]);
                int threads = calculateOptimalThreadCount();
                OfficePdf processor = new OfficePdf(files, threads, outputDir.toString());
                processor.processFiles();

                // 5. Preparar respuesta
                ConvertirArchivosResponse.Builder responseBuilder = ConvertirArchivosResponse.newBuilder();
                List<Path> pdfsGenerados = findGeneratedPdfs(outputDir, startTime);

                // 6. Agregar resultados a la respuesta
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

                // 7. Enviar respuesta
                responseObserver.onNext(responseBuilder.build());
                responseObserver.onCompleted();

            } catch (Exception e) {
                handleConversionError(e, responseObserver);
            } finally {
                // 8. Limpieza y métricas
                cleanTempFiles(filePaths, outputDir);
                logMetrics(startTime, memoryBean);
            }
        }

        private void verifyLibreOfficeInstallation() throws IOException {
            try {
                Process process = new ProcessBuilder("libreoffice", "--version").start();
                if (!process.waitFor(10, TimeUnit.SECONDS) || process.exitValue() != 0) {
                    throw new IOException("LibreOffice no está instalado correctamente o no es accesible");
                }
            } catch (Exception e) {
                throw new IOException("No se pudo verificar la instalación de LibreOffice", e);
            }
        }

        private void prepareOutputDirectory(Path outputDir) throws IOException {
            if (!Files.exists(outputDir)) {
                Files.createDirectories(outputDir);
                try {
                    // Solo establecer permisos POSIX si el sistema lo soporta
                    if (!System.getProperty("os.name").toLowerCase().contains("win")) {
                        Files.setPosixFilePermissions(outputDir, 
                            PosixFilePermissions.fromString("rwxrwxrwx"));
                    }
                } catch (UnsupportedOperationException e) {
                    // Ignorar en sistemas que no soportan POSIX
                }
            }
            
            if (!Files.isWritable(outputDir)) {
                throw new IOException("No se puede escribir en el directorio de salida: " + outputDir);
            }
        }

        private String sanitizeFilename(String filename) {
            return filename.toLowerCase()
                    .replaceAll("[^a-z0-9.-]", "_")
                    .replaceAll("_{2,}", "_")
                    .replaceAll("^_|_$", "");
        }

        private void validateTempFile(Path tempFilePath) throws IOException {
            if (!Files.exists(tempFilePath)) {
                throw new IOException("No se pudo crear el archivo temporal: " + tempFilePath);
            }
            if (Files.size(tempFilePath) == 0) {
                Files.deleteIfExists(tempFilePath);
                throw new IOException("El archivo temporal está vacío: " + tempFilePath);
            }
        }

        private int calculateOptimalThreadCount() {
            int availableProcessors = Runtime.getRuntime().availableProcessors();
            return Math.min(4, Math.max(1, availableProcessors - 1));
        }

        private List<Path> findGeneratedPdfs(Path outputDir, long startTime) throws IOException {
            return Files.list(outputDir)
                    .filter(path -> path.toString().endsWith(".pdf"))
                    .filter(path -> path.getFileName().toString().startsWith("officepdf_"))
                    .filter(path -> {
                        try {
                            return Files.getLastModifiedTime(path).toMillis() > startTime;
                        } catch (IOException e) {
                            return false;
                        }
                    })
                    .sorted(Comparator.comparing(path -> {
                        String name = path.getFileName().toString();
                        return Long.parseLong(name.split("_")[1]);
                    })) // <-- Aquí faltaba cerrar este paréntesis
                    .collect(Collectors.toList());
        }

        private void handleConversionError(Exception e, 
                                        StreamObserver<ConvertirArchivosResponse> responseObserver) {
            String errorMsg = "Error al procesar archivos: " + e.getMessage();
            System.err.println(errorMsg);
            e.printStackTrace();

            Status status = Status.INTERNAL;
            if (e instanceof IOException) {
                status = Status.FAILED_PRECONDITION.withDescription("Error de configuración: " + e.getMessage());
            }

            responseObserver.onError(status
                    .withCause(e)
                    .asRuntimeException());
        }

        private void cleanTempFiles(List<String> filePaths, Path outputDir) {
            // Limpiar archivos de entrada temporales
            filePaths.forEach(path -> {
                try {
                    Files.deleteIfExists(Paths.get(path));
                } catch (IOException e) {
                    System.err.println("No se pudo eliminar archivo temporal: " + path);
                }
            });

            // Limpiar PDFs temporales antiguos (más de 1 hora)
            try {
                long cutoff = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(1);
                Files.list(outputDir)
                        .filter(path -> path.toString().endsWith(".pdf"))
                        .filter(path -> path.getFileName().toString().startsWith("officepdf_"))
                        .filter(path -> {
                            try {
                                return Files.getLastModifiedTime(path).toMillis() < cutoff;
                            } catch (IOException e) {
                                return false;
                            }
                        })
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

        private void logMetrics(long startTime, MemoryMXBean memoryBean) {
            long duration = System.currentTimeMillis() - startTime;
            long memoryUsed = memoryBean.getHeapMemoryUsage().getUsed() / (1024 * 1024);

            System.out.println("\n=== Métricas del Servicio ===");
            System.out.println("Total de peticiones: " + totalRequests.get());
            System.out.printf("Última conversión - Tiempo: %dms | Memoria usada: %dMB\n",
                    duration, memoryUsed);
            System.out.printf("Estadísticas: %d éxitos, %d fallos\n",
                    Processing.getSuccessCount(), Processing.getFailureCount());
        }
    }


    public static class OfficePdf2 { // Corregir: Hacer la clase OfficePdf2 static
        private String[] files;
        private int threads;
        private String outputPdfPath;

        public OfficePdf2(String[] files, int threads, String outputPdfPath) { // Agregar constructor
            this.files = files;
            this.threads = threads;
            this.outputPdfPath = outputPdfPath;
        }

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