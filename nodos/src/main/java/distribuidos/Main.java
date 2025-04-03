package distribuidos;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
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
            responseObserver.onNext(
                ConvertirUrlsResponse.newBuilder()
                    .addResultados("Success")
                    .build()
            );
            responseObserver.onCompleted();
        }
    }

    static class OfficeConverterService extends ConvertidorOfficeGrpc.ConvertidorOfficeImplBase {
        @Override
        public void convertirArchivos(ConvertirArchivosRequest request, 
                                    StreamObserver<ConvertirArchivosResponse> responseObserver) {
            responseObserver.onNext(
                ConvertirArchivosResponse.newBuilder()
                    .addResultados("Success")
                    .build()
            );
            responseObserver.onCompleted();
        }
    }
}