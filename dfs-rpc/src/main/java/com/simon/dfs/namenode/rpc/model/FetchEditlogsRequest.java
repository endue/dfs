// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: NameNodeRpcModel.proto

package com.simon.dfs.namenode.rpc.model;

/**
 * Protobuf type {@code com.simon.dfs.namenode.rpc.FetchEditlogsRequest}
 */
public  final class FetchEditlogsRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:com.simon.dfs.namenode.rpc.FetchEditlogsRequest)
    FetchEditlogsRequestOrBuilder {
  // Use FetchEditlogsRequest.newBuilder() to construct.
  private FetchEditlogsRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private FetchEditlogsRequest() {
    code_ = 0;
    fetchedMaxTxid_ = 0L;
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private FetchEditlogsRequest(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 8: {

            code_ = input.readInt32();
            break;
          }
          case 16: {

            fetchedMaxTxid_ = input.readInt64();
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.simon.dfs.namenode.rpc.model.NameNodeRpcModel.internal_static_com_simon_dfs_namenode_rpc_FetchEditlogsRequest_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.simon.dfs.namenode.rpc.model.NameNodeRpcModel.internal_static_com_simon_dfs_namenode_rpc_FetchEditlogsRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest.class, com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest.Builder.class);
  }

  public static final int CODE_FIELD_NUMBER = 1;
  private int code_;
  /**
   * <code>optional int32 code = 1;</code>
   */
  public int getCode() {
    return code_;
  }

  public static final int FETCHEDMAXTXID_FIELD_NUMBER = 2;
  private long fetchedMaxTxid_;
  /**
   * <code>optional int64 fetchedMaxTxid = 2;</code>
   */
  public long getFetchedMaxTxid() {
    return fetchedMaxTxid_;
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (code_ != 0) {
      output.writeInt32(1, code_);
    }
    if (fetchedMaxTxid_ != 0L) {
      output.writeInt64(2, fetchedMaxTxid_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (code_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, code_);
    }
    if (fetchedMaxTxid_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(2, fetchedMaxTxid_);
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest)) {
      return super.equals(obj);
    }
    com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest other = (com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest) obj;

    boolean result = true;
    result = result && (getCode()
        == other.getCode());
    result = result && (getFetchedMaxTxid()
        == other.getFetchedMaxTxid());
    return result;
  }

  @Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptorForType().hashCode();
    hash = (37 * hash) + CODE_FIELD_NUMBER;
    hash = (53 * hash) + getCode();
    hash = (37 * hash) + FETCHEDMAXTXID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getFetchedMaxTxid());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code com.simon.dfs.namenode.rpc.FetchEditlogsRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:com.simon.dfs.namenode.rpc.FetchEditlogsRequest)
      com.simon.dfs.namenode.rpc.model.FetchEditlogsRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.simon.dfs.namenode.rpc.model.NameNodeRpcModel.internal_static_com_simon_dfs_namenode_rpc_FetchEditlogsRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.simon.dfs.namenode.rpc.model.NameNodeRpcModel.internal_static_com_simon_dfs_namenode_rpc_FetchEditlogsRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest.class, com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest.Builder.class);
    }

    // Construct using com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      code_ = 0;

      fetchedMaxTxid_ = 0L;

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.simon.dfs.namenode.rpc.model.NameNodeRpcModel.internal_static_com_simon_dfs_namenode_rpc_FetchEditlogsRequest_descriptor;
    }

    public com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest getDefaultInstanceForType() {
      return com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest.getDefaultInstance();
    }

    public com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest build() {
      com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest buildPartial() {
      com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest result = new com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest(this);
      result.code_ = code_;
      result.fetchedMaxTxid_ = fetchedMaxTxid_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest) {
        return mergeFrom((com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest other) {
      if (other == com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest.getDefaultInstance()) return this;
      if (other.getCode() != 0) {
        setCode(other.getCode());
      }
      if (other.getFetchedMaxTxid() != 0L) {
        setFetchedMaxTxid(other.getFetchedMaxTxid());
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int code_ ;
    /**
     * <code>optional int32 code = 1;</code>
     */
    public int getCode() {
      return code_;
    }
    /**
     * <code>optional int32 code = 1;</code>
     */
    public Builder setCode(int value) {

      code_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional int32 code = 1;</code>
     */
    public Builder clearCode() {

      code_ = 0;
      onChanged();
      return this;
    }

    private long fetchedMaxTxid_ ;
    /**
     * <code>optional int64 fetchedMaxTxid = 2;</code>
     */
    public long getFetchedMaxTxid() {
      return fetchedMaxTxid_;
    }
    /**
     * <code>optional int64 fetchedMaxTxid = 2;</code>
     */
    public Builder setFetchedMaxTxid(long value) {

      fetchedMaxTxid_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional int64 fetchedMaxTxid = 2;</code>
     */
    public Builder clearFetchedMaxTxid() {

      fetchedMaxTxid_ = 0L;
      onChanged();
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:com.simon.dfs.namenode.rpc.FetchEditlogsRequest)
  }

  // @@protoc_insertion_point(class_scope:com.simon.dfs.namenode.rpc.FetchEditlogsRequest)
  private static final com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest();
  }

  public static com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<FetchEditlogsRequest>
      PARSER = new com.google.protobuf.AbstractParser<FetchEditlogsRequest>() {
    public FetchEditlogsRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new FetchEditlogsRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<FetchEditlogsRequest> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<FetchEditlogsRequest> getParserForType() {
    return PARSER;
  }

  public com.simon.dfs.namenode.rpc.model.FetchEditlogsRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
