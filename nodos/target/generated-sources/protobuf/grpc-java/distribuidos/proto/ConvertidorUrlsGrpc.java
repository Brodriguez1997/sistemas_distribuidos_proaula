package distribuidos.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.45.1)",
    comments = "Source: servicio.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ConvertidorUrlsGrpc {

  private ConvertidorUrlsGrpc() {}

  public static final String SERVICE_NAME = "distribuidos.ConvertidorUrls";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<distribuidos.proto.ConvertirUrlsRequest,
      distribuidos.proto.ConvertirUrlsResponse> getConvertirUrlsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ConvertirUrls",
      requestType = distribuidos.proto.ConvertirUrlsRequest.class,
      responseType = distribuidos.proto.ConvertirUrlsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<distribuidos.proto.ConvertirUrlsRequest,
      distribuidos.proto.ConvertirUrlsResponse> getConvertirUrlsMethod() {
    io.grpc.MethodDescriptor<distribuidos.proto.ConvertirUrlsRequest, distribuidos.proto.ConvertirUrlsResponse> getConvertirUrlsMethod;
    if ((getConvertirUrlsMethod = ConvertidorUrlsGrpc.getConvertirUrlsMethod) == null) {
      synchronized (ConvertidorUrlsGrpc.class) {
        if ((getConvertirUrlsMethod = ConvertidorUrlsGrpc.getConvertirUrlsMethod) == null) {
          ConvertidorUrlsGrpc.getConvertirUrlsMethod = getConvertirUrlsMethod =
              io.grpc.MethodDescriptor.<distribuidos.proto.ConvertirUrlsRequest, distribuidos.proto.ConvertirUrlsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ConvertirUrls"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  distribuidos.proto.ConvertirUrlsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  distribuidos.proto.ConvertirUrlsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ConvertidorUrlsMethodDescriptorSupplier("ConvertirUrls"))
              .build();
        }
      }
    }
    return getConvertirUrlsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<distribuidos.proto.DescargarUrlsRequest,
      distribuidos.proto.DescargarUrlsResponse> getDescargarUrlsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DescargarUrls",
      requestType = distribuidos.proto.DescargarUrlsRequest.class,
      responseType = distribuidos.proto.DescargarUrlsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<distribuidos.proto.DescargarUrlsRequest,
      distribuidos.proto.DescargarUrlsResponse> getDescargarUrlsMethod() {
    io.grpc.MethodDescriptor<distribuidos.proto.DescargarUrlsRequest, distribuidos.proto.DescargarUrlsResponse> getDescargarUrlsMethod;
    if ((getDescargarUrlsMethod = ConvertidorUrlsGrpc.getDescargarUrlsMethod) == null) {
      synchronized (ConvertidorUrlsGrpc.class) {
        if ((getDescargarUrlsMethod = ConvertidorUrlsGrpc.getDescargarUrlsMethod) == null) {
          ConvertidorUrlsGrpc.getDescargarUrlsMethod = getDescargarUrlsMethod =
              io.grpc.MethodDescriptor.<distribuidos.proto.DescargarUrlsRequest, distribuidos.proto.DescargarUrlsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DescargarUrls"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  distribuidos.proto.DescargarUrlsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  distribuidos.proto.DescargarUrlsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ConvertidorUrlsMethodDescriptorSupplier("DescargarUrls"))
              .build();
        }
      }
    }
    return getDescargarUrlsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ConvertidorUrlsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ConvertidorUrlsStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ConvertidorUrlsStub>() {
        @java.lang.Override
        public ConvertidorUrlsStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ConvertidorUrlsStub(channel, callOptions);
        }
      };
    return ConvertidorUrlsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ConvertidorUrlsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ConvertidorUrlsBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ConvertidorUrlsBlockingStub>() {
        @java.lang.Override
        public ConvertidorUrlsBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ConvertidorUrlsBlockingStub(channel, callOptions);
        }
      };
    return ConvertidorUrlsBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ConvertidorUrlsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ConvertidorUrlsFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ConvertidorUrlsFutureStub>() {
        @java.lang.Override
        public ConvertidorUrlsFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ConvertidorUrlsFutureStub(channel, callOptions);
        }
      };
    return ConvertidorUrlsFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ConvertidorUrlsImplBase implements io.grpc.BindableService {

    /**
     */
    public void convertirUrls(distribuidos.proto.ConvertirUrlsRequest request,
        io.grpc.stub.StreamObserver<distribuidos.proto.ConvertirUrlsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getConvertirUrlsMethod(), responseObserver);
    }

    /**
     */
    public void descargarUrls(distribuidos.proto.DescargarUrlsRequest request,
        io.grpc.stub.StreamObserver<distribuidos.proto.DescargarUrlsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDescargarUrlsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getConvertirUrlsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                distribuidos.proto.ConvertirUrlsRequest,
                distribuidos.proto.ConvertirUrlsResponse>(
                  this, METHODID_CONVERTIR_URLS)))
          .addMethod(
            getDescargarUrlsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                distribuidos.proto.DescargarUrlsRequest,
                distribuidos.proto.DescargarUrlsResponse>(
                  this, METHODID_DESCARGAR_URLS)))
          .build();
    }
  }

  /**
   */
  public static final class ConvertidorUrlsStub extends io.grpc.stub.AbstractAsyncStub<ConvertidorUrlsStub> {
    private ConvertidorUrlsStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConvertidorUrlsStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ConvertidorUrlsStub(channel, callOptions);
    }

    /**
     */
    public void convertirUrls(distribuidos.proto.ConvertirUrlsRequest request,
        io.grpc.stub.StreamObserver<distribuidos.proto.ConvertirUrlsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getConvertirUrlsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void descargarUrls(distribuidos.proto.DescargarUrlsRequest request,
        io.grpc.stub.StreamObserver<distribuidos.proto.DescargarUrlsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDescargarUrlsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ConvertidorUrlsBlockingStub extends io.grpc.stub.AbstractBlockingStub<ConvertidorUrlsBlockingStub> {
    private ConvertidorUrlsBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConvertidorUrlsBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ConvertidorUrlsBlockingStub(channel, callOptions);
    }

    /**
     */
    public distribuidos.proto.ConvertirUrlsResponse convertirUrls(distribuidos.proto.ConvertirUrlsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getConvertirUrlsMethod(), getCallOptions(), request);
    }

    /**
     */
    public distribuidos.proto.DescargarUrlsResponse descargarUrls(distribuidos.proto.DescargarUrlsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDescargarUrlsMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ConvertidorUrlsFutureStub extends io.grpc.stub.AbstractFutureStub<ConvertidorUrlsFutureStub> {
    private ConvertidorUrlsFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConvertidorUrlsFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ConvertidorUrlsFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<distribuidos.proto.ConvertirUrlsResponse> convertirUrls(
        distribuidos.proto.ConvertirUrlsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getConvertirUrlsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<distribuidos.proto.DescargarUrlsResponse> descargarUrls(
        distribuidos.proto.DescargarUrlsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDescargarUrlsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CONVERTIR_URLS = 0;
  private static final int METHODID_DESCARGAR_URLS = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ConvertidorUrlsImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ConvertidorUrlsImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CONVERTIR_URLS:
          serviceImpl.convertirUrls((distribuidos.proto.ConvertirUrlsRequest) request,
              (io.grpc.stub.StreamObserver<distribuidos.proto.ConvertirUrlsResponse>) responseObserver);
          break;
        case METHODID_DESCARGAR_URLS:
          serviceImpl.descargarUrls((distribuidos.proto.DescargarUrlsRequest) request,
              (io.grpc.stub.StreamObserver<distribuidos.proto.DescargarUrlsResponse>) responseObserver);
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

  private static abstract class ConvertidorUrlsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ConvertidorUrlsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return distribuidos.proto.ServiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ConvertidorUrls");
    }
  }

  private static final class ConvertidorUrlsFileDescriptorSupplier
      extends ConvertidorUrlsBaseDescriptorSupplier {
    ConvertidorUrlsFileDescriptorSupplier() {}
  }

  private static final class ConvertidorUrlsMethodDescriptorSupplier
      extends ConvertidorUrlsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ConvertidorUrlsMethodDescriptorSupplier(String methodName) {
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
      synchronized (ConvertidorUrlsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ConvertidorUrlsFileDescriptorSupplier())
              .addMethod(getConvertirUrlsMethod())
              .addMethod(getDescargarUrlsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
