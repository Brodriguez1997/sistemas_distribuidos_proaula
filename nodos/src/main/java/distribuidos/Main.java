package distribuidos;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import officepdf.officepdf;
import urlpdf.urlpdf;

import java.io.IOException;
import distribuidos.proto.*;

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
                 // Crear el array de URLs con la URL recibida en la solicitud
                String[] urls = {request.getUrl()};
                 int[] threadCounts = {4}; // Puedes hacer esto configurable si lo necesitas
                                    
                                    // Crear y ejecutar el procesador de URLs
                 urlpdf processor = new urlpdf (urls, threadCounts);
                         processor.processUrls();
                                    
                        responseObserver.onNext(
                         ConvertirUrlsResponse.newBuilder()
                     .addResultados("Success")
                    .build()
                    );
                  responseObserver.onCompleted();
                } catch (Exception e) {
                 responseObserver.onError(e);
            }
         }
    }

    static class OfficeConverterService extends ConvertidorOfficeGrpc.ConvertidorOfficeImplBase {
        @Override
        public void convertirArchivos(ConvertirArchivosRequest request, 
                                    StreamObserver<ConvertirArchivosResponse> responseObserver) {
          try {
                // Crear el array de archivos con el archivo recibido en la solicitud
            String[] files = {request.getArchivo()};
            int threads = 4; // Puedes hacer esto configurable
                                            
                                            // Crear y ejecutar el procesador de archivos
            officepdf processor = new officepdf(files, threads);
            processor.processFiles();
                                            
                responseObserver.onNext(
                     ConvertirArchivosResponse.newBuilder()
                     .addResultados("Success - Archivo convertido: " + request.getNombre())
                     .build()
                 );
             responseObserver.onCompleted();
            } catch (Exception e) {
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
}