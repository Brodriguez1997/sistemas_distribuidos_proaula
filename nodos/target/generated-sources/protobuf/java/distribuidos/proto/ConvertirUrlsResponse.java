// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: servicio.proto

package distribuidos.proto;

/**
 * Protobuf type {@code distribuidos.ConvertirUrlsResponse}
 */
public final class ConvertirUrlsResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:distribuidos.ConvertirUrlsResponse)
    ConvertirUrlsResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ConvertirUrlsResponse.newBuilder() to construct.
  private ConvertirUrlsResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ConvertirUrlsResponse() {
    resultados_ = com.google.protobuf.LazyStringArrayList.EMPTY;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new ConvertirUrlsResponse();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return distribuidos.proto.ServiceProto.internal_static_distribuidos_ConvertirUrlsResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return distribuidos.proto.ServiceProto.internal_static_distribuidos_ConvertirUrlsResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            distribuidos.proto.ConvertirUrlsResponse.class, distribuidos.proto.ConvertirUrlsResponse.Builder.class);
  }

  public static final int RESULTADOS_FIELD_NUMBER = 1;
  private com.google.protobuf.LazyStringList resultados_;
  /**
   * <code>repeated string resultados = 1;</code>
   * @return A list containing the resultados.
   */
  public com.google.protobuf.ProtocolStringList
      getResultadosList() {
    return resultados_;
  }
  /**
   * <code>repeated string resultados = 1;</code>
   * @return The count of resultados.
   */
  public int getResultadosCount() {
    return resultados_.size();
  }
  /**
   * <code>repeated string resultados = 1;</code>
   * @param index The index of the element to return.
   * @return The resultados at the given index.
   */
  public java.lang.String getResultados(int index) {
    return resultados_.get(index);
  }
  /**
   * <code>repeated string resultados = 1;</code>
   * @param index The index of the value to return.
   * @return The bytes of the resultados at the given index.
   */
  public com.google.protobuf.ByteString
      getResultadosBytes(int index) {
    return resultados_.getByteString(index);
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    for (int i = 0; i < resultados_.size(); i++) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, resultados_.getRaw(i));
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    {
      int dataSize = 0;
      for (int i = 0; i < resultados_.size(); i++) {
        dataSize += computeStringSizeNoTag(resultados_.getRaw(i));
      }
      size += dataSize;
      size += 1 * getResultadosList().size();
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof distribuidos.proto.ConvertirUrlsResponse)) {
      return super.equals(obj);
    }
    distribuidos.proto.ConvertirUrlsResponse other = (distribuidos.proto.ConvertirUrlsResponse) obj;

    if (!getResultadosList()
        .equals(other.getResultadosList())) return false;
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (getResultadosCount() > 0) {
      hash = (37 * hash) + RESULTADOS_FIELD_NUMBER;
      hash = (53 * hash) + getResultadosList().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static distribuidos.proto.ConvertirUrlsResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static distribuidos.proto.ConvertirUrlsResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static distribuidos.proto.ConvertirUrlsResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static distribuidos.proto.ConvertirUrlsResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static distribuidos.proto.ConvertirUrlsResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static distribuidos.proto.ConvertirUrlsResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static distribuidos.proto.ConvertirUrlsResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static distribuidos.proto.ConvertirUrlsResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static distribuidos.proto.ConvertirUrlsResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static distribuidos.proto.ConvertirUrlsResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static distribuidos.proto.ConvertirUrlsResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static distribuidos.proto.ConvertirUrlsResponse parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(distribuidos.proto.ConvertirUrlsResponse prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code distribuidos.ConvertirUrlsResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:distribuidos.ConvertirUrlsResponse)
      distribuidos.proto.ConvertirUrlsResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return distribuidos.proto.ServiceProto.internal_static_distribuidos_ConvertirUrlsResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return distribuidos.proto.ServiceProto.internal_static_distribuidos_ConvertirUrlsResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              distribuidos.proto.ConvertirUrlsResponse.class, distribuidos.proto.ConvertirUrlsResponse.Builder.class);
    }

    // Construct using distribuidos.proto.ConvertirUrlsResponse.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      resultados_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      bitField0_ = (bitField0_ & ~0x00000001);
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return distribuidos.proto.ServiceProto.internal_static_distribuidos_ConvertirUrlsResponse_descriptor;
    }

    @java.lang.Override
    public distribuidos.proto.ConvertirUrlsResponse getDefaultInstanceForType() {
      return distribuidos.proto.ConvertirUrlsResponse.getDefaultInstance();
    }

    @java.lang.Override
    public distribuidos.proto.ConvertirUrlsResponse build() {
      distribuidos.proto.ConvertirUrlsResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public distribuidos.proto.ConvertirUrlsResponse buildPartial() {
      distribuidos.proto.ConvertirUrlsResponse result = new distribuidos.proto.ConvertirUrlsResponse(this);
      int from_bitField0_ = bitField0_;
      if (((bitField0_ & 0x00000001) != 0)) {
        resultados_ = resultados_.getUnmodifiableView();
        bitField0_ = (bitField0_ & ~0x00000001);
      }
      result.resultados_ = resultados_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof distribuidos.proto.ConvertirUrlsResponse) {
        return mergeFrom((distribuidos.proto.ConvertirUrlsResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(distribuidos.proto.ConvertirUrlsResponse other) {
      if (other == distribuidos.proto.ConvertirUrlsResponse.getDefaultInstance()) return this;
      if (!other.resultados_.isEmpty()) {
        if (resultados_.isEmpty()) {
          resultados_ = other.resultados_;
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          ensureResultadosIsMutable();
          resultados_.addAll(other.resultados_);
        }
        onChanged();
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              java.lang.String s = input.readStringRequireUtf8();
              ensureResultadosIsMutable();
              resultados_.add(s);
              break;
            } // case 10
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }
    private int bitField0_;

    private com.google.protobuf.LazyStringList resultados_ = com.google.protobuf.LazyStringArrayList.EMPTY;
    private void ensureResultadosIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        resultados_ = new com.google.protobuf.LazyStringArrayList(resultados_);
        bitField0_ |= 0x00000001;
       }
    }
    /**
     * <code>repeated string resultados = 1;</code>
     * @return A list containing the resultados.
     */
    public com.google.protobuf.ProtocolStringList
        getResultadosList() {
      return resultados_.getUnmodifiableView();
    }
    /**
     * <code>repeated string resultados = 1;</code>
     * @return The count of resultados.
     */
    public int getResultadosCount() {
      return resultados_.size();
    }
    /**
     * <code>repeated string resultados = 1;</code>
     * @param index The index of the element to return.
     * @return The resultados at the given index.
     */
    public java.lang.String getResultados(int index) {
      return resultados_.get(index);
    }
    /**
     * <code>repeated string resultados = 1;</code>
     * @param index The index of the value to return.
     * @return The bytes of the resultados at the given index.
     */
    public com.google.protobuf.ByteString
        getResultadosBytes(int index) {
      return resultados_.getByteString(index);
    }
    /**
     * <code>repeated string resultados = 1;</code>
     * @param index The index to set the value at.
     * @param value The resultados to set.
     * @return This builder for chaining.
     */
    public Builder setResultados(
        int index, java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  ensureResultadosIsMutable();
      resultados_.set(index, value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string resultados = 1;</code>
     * @param value The resultados to add.
     * @return This builder for chaining.
     */
    public Builder addResultados(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  ensureResultadosIsMutable();
      resultados_.add(value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string resultados = 1;</code>
     * @param values The resultados to add.
     * @return This builder for chaining.
     */
    public Builder addAllResultados(
        java.lang.Iterable<java.lang.String> values) {
      ensureResultadosIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
          values, resultados_);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string resultados = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearResultados() {
      resultados_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string resultados = 1;</code>
     * @param value The bytes of the resultados to add.
     * @return This builder for chaining.
     */
    public Builder addResultadosBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      ensureResultadosIsMutable();
      resultados_.add(value);
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:distribuidos.ConvertirUrlsResponse)
  }

  // @@protoc_insertion_point(class_scope:distribuidos.ConvertirUrlsResponse)
  private static final distribuidos.proto.ConvertirUrlsResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new distribuidos.proto.ConvertirUrlsResponse();
  }

  public static distribuidos.proto.ConvertirUrlsResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ConvertirUrlsResponse>
      PARSER = new com.google.protobuf.AbstractParser<ConvertirUrlsResponse>() {
    @java.lang.Override
    public ConvertirUrlsResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<ConvertirUrlsResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ConvertirUrlsResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public distribuidos.proto.ConvertirUrlsResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

