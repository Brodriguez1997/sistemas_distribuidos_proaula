// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: servicio.proto

package distribuidos.proto;

/**
 * Protobuf type {@code distribuidos.DescargarArchivoRequest}
 */
public final class DescargarArchivoRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:distribuidos.DescargarArchivoRequest)
    DescargarArchivoRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use DescargarArchivoRequest.newBuilder() to construct.
  private DescargarArchivoRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private DescargarArchivoRequest() {
    nombre_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new DescargarArchivoRequest();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return distribuidos.proto.ServiceProto.internal_static_distribuidos_DescargarArchivoRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return distribuidos.proto.ServiceProto.internal_static_distribuidos_DescargarArchivoRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            distribuidos.proto.DescargarArchivoRequest.class, distribuidos.proto.DescargarArchivoRequest.Builder.class);
  }

  public static final int NOMBRE_FIELD_NUMBER = 1;
  private volatile java.lang.Object nombre_;
  /**
   * <code>string nombre = 1;</code>
   * @return The nombre.
   */
  @java.lang.Override
  public java.lang.String getNombre() {
    java.lang.Object ref = nombre_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      nombre_ = s;
      return s;
    }
  }
  /**
   * <code>string nombre = 1;</code>
   * @return The bytes for nombre.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getNombreBytes() {
    java.lang.Object ref = nombre_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      nombre_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
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
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(nombre_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, nombre_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(nombre_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, nombre_);
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
    if (!(obj instanceof distribuidos.proto.DescargarArchivoRequest)) {
      return super.equals(obj);
    }
    distribuidos.proto.DescargarArchivoRequest other = (distribuidos.proto.DescargarArchivoRequest) obj;

    if (!getNombre()
        .equals(other.getNombre())) return false;
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
    hash = (37 * hash) + NOMBRE_FIELD_NUMBER;
    hash = (53 * hash) + getNombre().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static distribuidos.proto.DescargarArchivoRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static distribuidos.proto.DescargarArchivoRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static distribuidos.proto.DescargarArchivoRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static distribuidos.proto.DescargarArchivoRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static distribuidos.proto.DescargarArchivoRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static distribuidos.proto.DescargarArchivoRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static distribuidos.proto.DescargarArchivoRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static distribuidos.proto.DescargarArchivoRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static distribuidos.proto.DescargarArchivoRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static distribuidos.proto.DescargarArchivoRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static distribuidos.proto.DescargarArchivoRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static distribuidos.proto.DescargarArchivoRequest parseFrom(
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
  public static Builder newBuilder(distribuidos.proto.DescargarArchivoRequest prototype) {
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
   * Protobuf type {@code distribuidos.DescargarArchivoRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:distribuidos.DescargarArchivoRequest)
      distribuidos.proto.DescargarArchivoRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return distribuidos.proto.ServiceProto.internal_static_distribuidos_DescargarArchivoRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return distribuidos.proto.ServiceProto.internal_static_distribuidos_DescargarArchivoRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              distribuidos.proto.DescargarArchivoRequest.class, distribuidos.proto.DescargarArchivoRequest.Builder.class);
    }

    // Construct using distribuidos.proto.DescargarArchivoRequest.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      nombre_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return distribuidos.proto.ServiceProto.internal_static_distribuidos_DescargarArchivoRequest_descriptor;
    }

    @java.lang.Override
    public distribuidos.proto.DescargarArchivoRequest getDefaultInstanceForType() {
      return distribuidos.proto.DescargarArchivoRequest.getDefaultInstance();
    }

    @java.lang.Override
    public distribuidos.proto.DescargarArchivoRequest build() {
      distribuidos.proto.DescargarArchivoRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public distribuidos.proto.DescargarArchivoRequest buildPartial() {
      distribuidos.proto.DescargarArchivoRequest result = new distribuidos.proto.DescargarArchivoRequest(this);
      result.nombre_ = nombre_;
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
      if (other instanceof distribuidos.proto.DescargarArchivoRequest) {
        return mergeFrom((distribuidos.proto.DescargarArchivoRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(distribuidos.proto.DescargarArchivoRequest other) {
      if (other == distribuidos.proto.DescargarArchivoRequest.getDefaultInstance()) return this;
      if (!other.getNombre().isEmpty()) {
        nombre_ = other.nombre_;
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
              nombre_ = input.readStringRequireUtf8();

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

    private java.lang.Object nombre_ = "";
    /**
     * <code>string nombre = 1;</code>
     * @return The nombre.
     */
    public java.lang.String getNombre() {
      java.lang.Object ref = nombre_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        nombre_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string nombre = 1;</code>
     * @return The bytes for nombre.
     */
    public com.google.protobuf.ByteString
        getNombreBytes() {
      java.lang.Object ref = nombre_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        nombre_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string nombre = 1;</code>
     * @param value The nombre to set.
     * @return This builder for chaining.
     */
    public Builder setNombre(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      nombre_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string nombre = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearNombre() {
      
      nombre_ = getDefaultInstance().getNombre();
      onChanged();
      return this;
    }
    /**
     * <code>string nombre = 1;</code>
     * @param value The bytes for nombre to set.
     * @return This builder for chaining.
     */
    public Builder setNombreBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      nombre_ = value;
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


    // @@protoc_insertion_point(builder_scope:distribuidos.DescargarArchivoRequest)
  }

  // @@protoc_insertion_point(class_scope:distribuidos.DescargarArchivoRequest)
  private static final distribuidos.proto.DescargarArchivoRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new distribuidos.proto.DescargarArchivoRequest();
  }

  public static distribuidos.proto.DescargarArchivoRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DescargarArchivoRequest>
      PARSER = new com.google.protobuf.AbstractParser<DescargarArchivoRequest>() {
    @java.lang.Override
    public DescargarArchivoRequest parsePartialFrom(
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

  public static com.google.protobuf.Parser<DescargarArchivoRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DescargarArchivoRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public distribuidos.proto.DescargarArchivoRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

