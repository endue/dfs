package com.simon.dfs.namenode.rpc.service;

import io.grpc.stub.ClientCalls;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.3.0)",
    comments = "Source: NameNodeRpcServer.proto")
public final class NameNodeServiceGrpc {

  private NameNodeServiceGrpc() {}

  public static final String SERVICE_NAME = "com.simon.dfs.namenode.rpc.NameNodeService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.simon.dfs.namenode.rpc.model.RegisterRequest,
      com.simon.dfs.namenode.rpc.model.RegisterResponse> METHOD_REGISTER =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "com.simon.dfs.namenode.rpc.NameNodeService", "register"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.simon.dfs.namenode.rpc.model.RegisterRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.simon.dfs.namenode.rpc.model.RegisterResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.simon.dfs.namenode.rpc.model.HeartbeatRequest,
      com.simon.dfs.namenode.rpc.model.HeartbeatResponse> METHOD_HEARTBEAT =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "com.simon.dfs.namenode.rpc.NameNodeService", "heartbeat"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.simon.dfs.namenode.rpc.model.HeartbeatRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.simon.dfs.namenode.rpc.model.HeartbeatResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.simon.dfs.namenode.rpc.model.MkdirRequest,
      com.simon.dfs.namenode.rpc.model.MkdirResponse> METHOD_MKDIR =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "com.simon.dfs.namenode.rpc.NameNodeService", "mkdir"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.simon.dfs.namenode.rpc.model.MkdirRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.simon.dfs.namenode.rpc.model.MkdirResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.simon.dfs.namenode.rpc.model.ShutdownRequest,
      com.simon.dfs.namenode.rpc.model.ShutdownResponse> METHOD_SHUTDOWN =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "com.simon.dfs.namenode.rpc.NameNodeService", "shutdown"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.simon.dfs.namenode.rpc.model.ShutdownRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.simon.dfs.namenode.rpc.model.ShutdownResponse.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static NameNodeServiceStub newStub(io.grpc.Channel channel) {
    return new NameNodeServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static NameNodeServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new NameNodeServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static NameNodeServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new NameNodeServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class NameNodeServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void register(com.simon.dfs.namenode.rpc.model.RegisterRequest request,
        io.grpc.stub.StreamObserver<com.simon.dfs.namenode.rpc.model.RegisterResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_REGISTER, responseObserver);
    }

    /**
     */
    public void heartbeat(com.simon.dfs.namenode.rpc.model.HeartbeatRequest request,
        io.grpc.stub.StreamObserver<com.simon.dfs.namenode.rpc.model.HeartbeatResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_HEARTBEAT, responseObserver);
    }

    /**
     */
    public void mkdir(com.simon.dfs.namenode.rpc.model.MkdirRequest request,
        io.grpc.stub.StreamObserver<com.simon.dfs.namenode.rpc.model.MkdirResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_MKDIR, responseObserver);
    }

    /**
     */
    public void shutdown(com.simon.dfs.namenode.rpc.model.ShutdownRequest request,
        io.grpc.stub.StreamObserver<com.simon.dfs.namenode.rpc.model.ShutdownResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SHUTDOWN, responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_REGISTER,
            asyncUnaryCall(
              new MethodHandlers<
                com.simon.dfs.namenode.rpc.model.RegisterRequest,
                com.simon.dfs.namenode.rpc.model.RegisterResponse>(
                  this, METHODID_REGISTER)))
          .addMethod(
            METHOD_HEARTBEAT,
            asyncUnaryCall(
              new MethodHandlers<
                com.simon.dfs.namenode.rpc.model.HeartbeatRequest,
                com.simon.dfs.namenode.rpc.model.HeartbeatResponse>(
                  this, METHODID_HEARTBEAT)))
          .addMethod(
            METHOD_MKDIR,
            asyncUnaryCall(
              new MethodHandlers<
                com.simon.dfs.namenode.rpc.model.MkdirRequest,
                com.simon.dfs.namenode.rpc.model.MkdirResponse>(
                  this, METHODID_MKDIR)))
          .addMethod(
            METHOD_SHUTDOWN,
            asyncUnaryCall(
              new MethodHandlers<
                com.simon.dfs.namenode.rpc.model.ShutdownRequest,
                com.simon.dfs.namenode.rpc.model.ShutdownResponse>(
                  this, METHODID_SHUTDOWN)))
          .build();
    }
  }

  /**
   */
  public static final class NameNodeServiceStub extends io.grpc.stub.AbstractStub<NameNodeServiceStub> {
    private NameNodeServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private NameNodeServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected NameNodeServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new NameNodeServiceStub(channel, callOptions);
    }

    /**
     */
    public void register(com.simon.dfs.namenode.rpc.model.RegisterRequest request,
        io.grpc.stub.StreamObserver<com.simon.dfs.namenode.rpc.model.RegisterResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
          getChannel().newCall(METHOD_REGISTER, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void heartbeat(com.simon.dfs.namenode.rpc.model.HeartbeatRequest request,
        io.grpc.stub.StreamObserver<com.simon.dfs.namenode.rpc.model.HeartbeatResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
          getChannel().newCall(METHOD_HEARTBEAT, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void mkdir(com.simon.dfs.namenode.rpc.model.MkdirRequest request,
        io.grpc.stub.StreamObserver<com.simon.dfs.namenode.rpc.model.MkdirResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
          getChannel().newCall(METHOD_MKDIR, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void shutdown(com.simon.dfs.namenode.rpc.model.ShutdownRequest request,
        io.grpc.stub.StreamObserver<com.simon.dfs.namenode.rpc.model.ShutdownResponse> responseObserver) {
      ClientCalls.asyncUnaryCall(
          getChannel().newCall(METHOD_SHUTDOWN, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class NameNodeServiceBlockingStub extends io.grpc.stub.AbstractStub<NameNodeServiceBlockingStub> {
    private NameNodeServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private NameNodeServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected NameNodeServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new NameNodeServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.simon.dfs.namenode.rpc.model.RegisterResponse register(com.simon.dfs.namenode.rpc.model.RegisterRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_REGISTER, getCallOptions(), request);
    }

    /**
     */
    public com.simon.dfs.namenode.rpc.model.HeartbeatResponse heartbeat(com.simon.dfs.namenode.rpc.model.HeartbeatRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_HEARTBEAT, getCallOptions(), request);
    }

    /**
     */
    public com.simon.dfs.namenode.rpc.model.MkdirResponse mkdir(com.simon.dfs.namenode.rpc.model.MkdirRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_MKDIR, getCallOptions(), request);
    }

    /**
     */
    public com.simon.dfs.namenode.rpc.model.ShutdownResponse shutdown(com.simon.dfs.namenode.rpc.model.ShutdownRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SHUTDOWN, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class NameNodeServiceFutureStub extends io.grpc.stub.AbstractStub<NameNodeServiceFutureStub> {
    private NameNodeServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private NameNodeServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected NameNodeServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new NameNodeServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.simon.dfs.namenode.rpc.model.RegisterResponse> register(
        com.simon.dfs.namenode.rpc.model.RegisterRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_REGISTER, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.simon.dfs.namenode.rpc.model.HeartbeatResponse> heartbeat(
        com.simon.dfs.namenode.rpc.model.HeartbeatRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_HEARTBEAT, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.simon.dfs.namenode.rpc.model.MkdirResponse> mkdir(
        com.simon.dfs.namenode.rpc.model.MkdirRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_MKDIR, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.simon.dfs.namenode.rpc.model.ShutdownResponse> shutdown(
        com.simon.dfs.namenode.rpc.model.ShutdownRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SHUTDOWN, getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER = 0;
  private static final int METHODID_HEARTBEAT = 1;
  private static final int METHODID_MKDIR = 2;
  private static final int METHODID_SHUTDOWN = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final NameNodeServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(NameNodeServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REGISTER:
          serviceImpl.register((com.simon.dfs.namenode.rpc.model.RegisterRequest) request,
              (io.grpc.stub.StreamObserver<com.simon.dfs.namenode.rpc.model.RegisterResponse>) responseObserver);
          break;
        case METHODID_HEARTBEAT:
          serviceImpl.heartbeat((com.simon.dfs.namenode.rpc.model.HeartbeatRequest) request,
              (io.grpc.stub.StreamObserver<com.simon.dfs.namenode.rpc.model.HeartbeatResponse>) responseObserver);
          break;
        case METHODID_MKDIR:
          serviceImpl.mkdir((com.simon.dfs.namenode.rpc.model.MkdirRequest) request,
              (io.grpc.stub.StreamObserver<com.simon.dfs.namenode.rpc.model.MkdirResponse>) responseObserver);
          break;
        case METHODID_SHUTDOWN:
          serviceImpl.shutdown((com.simon.dfs.namenode.rpc.model.ShutdownRequest) request,
              (io.grpc.stub.StreamObserver<com.simon.dfs.namenode.rpc.model.ShutdownResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class NameNodeServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.simon.dfs.namenode.rpc.service.NameNodeServer.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (NameNodeServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new NameNodeServiceDescriptorSupplier())
              .addMethod(METHOD_REGISTER)
              .addMethod(METHOD_HEARTBEAT)
              .addMethod(METHOD_MKDIR)
              .addMethod(METHOD_SHUTDOWN)
              .build();
        }
      }
    }
    return result;
  }
}
