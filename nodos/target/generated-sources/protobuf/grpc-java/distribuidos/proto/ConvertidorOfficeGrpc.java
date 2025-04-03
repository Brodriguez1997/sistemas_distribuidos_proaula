package distribuidos.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.45.1)",
    comments = "Source: servicio.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ConvertidorOfficeGrpc {

  private ConvertidorOfficeGrpc() {}

  public static final String SERVICE_NAME = "distribuidos.ConvertidorOffice";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<distribuidos.proto.ConvertirArchivosRequest,
      distribuidos.proto.ConvertirArchivosResponse> getConvertirArchivosMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ConvertirArchivos",
      requestType = distribuidos.proto.ConvertirArchivosRequest.class,
      responseType = distribuidos.proto.ConvertirArchivosResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<distribuidos.proto.ConvertirArchivosRequest,
      distribuidos.proto.ConvertirArchivosResponse> getConvertirArchivosMethod() {
    io.grpc.MethodDescriptor<distribuidos.proto.ConvertirArchivosRequest, distribuidos.proto.ConvertirArchivosResponse> getConvertirArchivosMethod;
    if ((getConvertirArchivosMethod = ConvertidorOfficeGrpc.getConvertirArchivosMethod) == null) {
      synchronized (ConvertidorOfficeGrpc.class) {
        if ((getConvertirArchivosMethod = ConvertidorOfficeGrpc.getConvertirArchivosMethod) == null) {
          ConvertidorOfficeGrpc.getConvertirArchivosMethod = getConvertirArchivosMethod =
              io.grpc.MethodDescriptor.<distribuidos.proto.ConvertirArchivosRequest, distribuidos.proto.ConvertirArchivosResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ConvertirArchivos"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  distribuidos.proto.ConvertirArchivosRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  distribuidos.proto.ConvertirArchivosResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ConvertidorOfficeMethodDescriptorSupplier("ConvertirArchivos"))
              .build();
        }
      }
    }
    return getConvertirArchivosMethod;
  }

  private static volatile io.grpc.MethodDescriptor<distribuidos.proto.DescargarArchivoRequest,
      distribuidos.proto.DescargarArchivoResponse> getDescargarArchivoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DescargarArchivo",
      requestType = distribuidos.proto.DescargarArchivoRequest.class,
      responseType = distribuidos.proto.DescargarArchivoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<distribuidos.proto.DescargarArchivoRequest,
      distribuidos.proto.DescargarArchivoResponse> getDescargarArchivoMethod() {
    io.grpc.MethodDescriptor<distribuidos.proto.DescargarArchivoRequest, distribuidos.proto.DescargarArchivoResponse> getDescargarArchivoMethod;
    if ((getDescargarArchivoMethod = ConvertidorOfficeGrpc.getDescargarArchivoMethod) == null) {
      synchronized (ConvertidorOfficeGrpc.class) {
        if ((getDescargarArchivoMethod = ConvertidorOfficeGrpc.getDescargarArchivoMethod) == null) {
          ConvertidorOfficeGrpc.getDescargarArchivoMethod = getDescargarArchivoMethod =
              io.grpc.MethodDescriptor.<distribuidos.proto.DescargarArchivoRequest, distribuidos.proto.DescargarArchivoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DescargarArchivo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  distribuidos.proto.DescargarArchivoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  distribuidos.proto.DescargarArchivoResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ConvertidorOfficeMethodDescriptorSupplier("DescargarArchivo"))
              .build();
        }
      }
    }
    return getDescargarArchivoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<distribuidos.proto.SaludarRequest,
      distribuidos.proto.SaludarResponse> getSaludarMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Saludar",
      requestType = distribuidos.proto.SaludarRequest.class,
      responseType = distribuidos.proto.SaludarResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<distribuidos.proto.SaludarRequest,
      distribuidos.proto.SaludarResponse> getSaludarMethod() {
    io.grpc.MethodDescriptor<distribuidos.proto.SaludarRequest, distribuidos.proto.SaludarResponse> getSaludarMethod;
    if ((getSaludarMethod = ConvertidorOfficeGrpc.getSaludarMethod) == null) {
      synchronized (ConvertidorOfficeGrpc.class) {
        if ((getSaludarMethod = ConvertidorOfficeGrpc.getSaludarMethod) == null) {
          ConvertidorOfficeGrpc.getSaludarMethod = getSaludarMethod =
              io.grpc.MethodDescriptor.<distribuidos.proto.SaludarRequest, distribuidos.proto.SaludarResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Saludar"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  distribuidos.proto.SaludarRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  distribuidos.proto.SaludarResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ConvertidorOfficeMethodDescriptorSupplier("Saludar"))
              .build();
        }
      }
    }
    return getSaludarMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ConvertidorOfficeStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ConvertidorOfficeStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ConvertidorOfficeStub>() {
        @java.lang.Override
        public ConvertidorOfficeStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ConvertidorOfficeStub(channel, callOptions);
        }
      };
    return ConvertidorOfficeStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ConvertidorOfficeBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ConvertidorOfficeBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ConvertidorOfficeBlockingStub>() {
        @java.lang.Override
        public ConvertidorOfficeBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ConvertidorOfficeBlockingStub(channel, callOptions);
        }
      };
    return ConvertidorOfficeBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ConvertidorOfficeFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ConvertidorOfficeFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ConvertidorOfficeFutureStub>() {
        @java.lang.Override
        public ConvertidorOfficeFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ConvertidorOfficeFutureStub(channel, callOptions);
        }
      };
    return ConvertidorOfficeFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ConvertidorOfficeImplBase implements io.grpc.BindableService {

    /**
     */
    public void convertirArchivos(distribuidos.proto.ConvertirArchivosRequest request,
        io.grpc.stub.StreamObserver<distribuidos.proto.ConvertirArchivosResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getConvertirArchivosMethod(), responseObserver);
    }

    /**
     */
    public void descargarArchivo(distribuidos.proto.DescargarArchivoRequest request,
        io.grpc.stub.StreamObserver<distribuidos.proto.DescargarArchivoResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDescargarArchivoMethod(), responseObserver);
    }

    /**
     */
    public void saludar(distribuidos.proto.SaludarRequest request,
        io.grpc.stub.StreamObserver<distribuidos.proto.SaludarResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaludarMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getConvertirArchivosMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                distribuidos.proto.ConvertirArchivosRequest,
                distribuidos.proto.ConvertirArchivosResponse>(
                  this, METHODID_CONVERTIR_ARCHIVOS)))
          .addMethod(
            getDescargarArchivoMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                distribuidos.proto.DescargarArchivoRequest,
                distribuidos.proto.DescargarArchivoResponse>(
                  this, METHODID_DESCARGAR_ARCHIVO)))
          .addMethod(
            getSaludarMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                distribuidos.proto.SaludarRequest,
                distribuidos.proto.SaludarResponse>(
                  this, METHODID_SALUDAR)))
          .build();
    }
  }

  /**
   */
  public static final class ConvertidorOfficeStub extends io.grpc.stub.AbstractAsyncStub<ConvertidorOfficeStub> {
    private ConvertidorOfficeStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConvertidorOfficeStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ConvertidorOfficeStub(channel, callOptions);
    }

    /**
     */
    public void convertirArchivos(distribuidos.proto.ConvertirArchivosRequest request,
        io.grpc.stub.StreamObserver<distribuidos.proto.ConvertirArchivosResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getConvertirArchivosMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void descargarArchivo(distribuidos.proto.DescargarArchivoRequest request,
        io.grpc.stub.StreamObserver<distribuidos.proto.DescargarArchivoResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDescargarArchivoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void saludar(distribuidos.proto.SaludarRequest request,
        io.grpc.stub.StreamObserver<distribuidos.proto.SaludarResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaludarMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ConvertidorOfficeBlockingStub extends io.grpc.stub.AbstractBlockingStub<ConvertidorOfficeBlockingStub> {
    private ConvertidorOfficeBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConvertidorOfficeBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ConvertidorOfficeBlockingStub(channel, callOptions);
    }

    /**
     */
    public distribuidos.proto.ConvertirArchivosResponse convertirArchivos(distribuidos.proto.ConvertirArchivosRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getConvertirArchivosMethod(), getCallOptions(), request);
    }

    /**
     */
    public distribuidos.proto.DescargarArchivoResponse descargarArchivo(distribuidos.proto.DescargarArchivoRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDescargarArchivoMethod(), getCallOptions(), request);
    }

    /**
     */
    public distribuidos.proto.SaludarResponse saludar(distribuidos.proto.SaludarRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaludarMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ConvertidorOfficeFutureStub extends io.grpc.stub.AbstractFutureStub<ConvertidorOfficeFutureStub> {
    private ConvertidorOfficeFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConvertidorOfficeFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ConvertidorOfficeFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<distribuidos.proto.ConvertirArchivosResponse> convertirArchivos(
        distribuidos.proto.ConvertirArchivosRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getConvertirArchivosMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<distribuidos.proto.DescargarArchivoResponse> descargarArchivo(
        distribuidos.proto.DescargarArchivoRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDescargarArchivoMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<distribuidos.proto.SaludarResponse> saludar(
        distribuidos.proto.SaludarRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaludarMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CONVERTIR_ARCHIVOS = 0;
  private static final int METHODID_DESCARGAR_ARCHIVO = 1;
  private static final int METHODID_SALUDAR = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ConvertidorOfficeImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ConvertidorOfficeImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CONVERTIR_ARCHIVOS:
          serviceImpl.convertirArchivos((distribuidos.proto.ConvertirArchivosRequest) request,
              (io.grpc.stub.StreamObserver<distribuidos.proto.ConvertirArchivosResponse>) responseObserver);
          break;
        case METHODID_DESCARGAR_ARCHIVO:
          serviceImpl.descargarArchivo((distribuidos.proto.DescargarArchivoRequest) request,
              (io.grpc.stub.StreamObserver<distribuidos.proto.DescargarArchivoResponse>) responseObserver);
          break;
        case METHODID_SALUDAR:
          serviceImpl.saludar((distribuidos.proto.SaludarRequest) request,
              (io.grpc.stub.StreamObserver<distribuidos.proto.SaludarResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ConvertidorOfficeBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ConvertidorOfficeBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return distribuidos.proto.ServiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ConvertidorOffice");
    }
  }

  private static final class ConvertidorOfficeFileDescriptorSupplier
      extends ConvertidorOfficeBaseDescriptorSupplier {
    ConvertidorOfficeFileDescriptorSupplier() {}
  }

  private static final class ConvertidorOfficeMethodDescriptorSupplier
      extends ConvertidorOfficeBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ConvertidorOfficeMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ConvertidorOfficeGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ConvertidorOfficeFileDescriptorSupplier())
              .addMethod(getConvertirArchivosMethod())
              .addMethod(getDescargarArchivoMethod())
              .addMethod(getSaludarMethod())
              .build();
        }
      }
    }
    return result;
  }
}
