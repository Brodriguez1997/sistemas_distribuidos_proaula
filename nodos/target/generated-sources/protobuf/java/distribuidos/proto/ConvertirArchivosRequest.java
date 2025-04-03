// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: servicio.proto

package distribuidos.proto;

/**
 * Protobuf type {@code distribuidos.ConvertirArchivosRequest}
 */
public final class ConvertirArchivosRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:distribuidos.ConvertirArchivosRequest)
    ConvertirArchivosRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ConvertirArchivosRequest.newBuilder() to construct.
  private ConvertirArchivosRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ConvertirArchivosRequest() {
    archivo_ = "";
    nombre_ = "";
    tipo_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new ConvertirArchivosRequest();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return distribuidos.proto.ServiceProto.internal_static_distribuidos_ConvertirArchivosRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return distribuidos.proto.ServiceProto.internal_static_distribuidos_ConvertirArchivosRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            distribuidos.proto.ConvertirArchivosRequest.class, distribuidos.proto.ConvertirArchivosRequest.Builder.class);
  }

  public static final int ARCHIVO_FIELD_NUMBER = 1;
  private volatile java.lang.Object archivo_;
  /**
   * <code>string archivo = 1;</code>
   * @return The archivo.
   */
  @java.lang.Override
  public java.lang.String getArchivo() {
    java.lang.Object ref = archivo_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      archivo_ = s;
      return s;
    }
  }
  /**
   * <code>string archivo = 1;</code>
   * @return The bytes for archivo.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getArchivoBytes() {
    java.lang.Object ref = archivo_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      archivo_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int NOMBRE_FIELD_NUMBER = 2;
  private volatile java.lang.Object nombre_;
  /**
   * <code>string nombre = 2;</code>
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
   * <code>string nombre = 2;</code>
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

  public static final int TIPO_FIELD_NUMBER = 3;
  private volatile java.lang.Object tipo_;
  /**
   * <code>string tipo = 3;</code>
   * @return The tipo.
   */
  @java.lang.Override
  public java.lang.String getTipo() {
    java.lang.Object ref = tipo_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      tipo_ = s;
      return s;
    }
  }
  /**
   * <code>string tipo = 3;</code>
   * @return The bytes for tipo.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getTipoBytes() {
    java.lang.Object ref = tipo_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      tipo_ = b;
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
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(archivo_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, archivo_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(nombre_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, nombre_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(tipo_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, tipo_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(archivo_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, archivo_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(nombre_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, nombre_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(tipo_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, tipo_);
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
    if (!(obj instanceof distribuidos.proto.ConvertirArchivosRequest)) {
      return super.equals(obj);
    }
    distribuidos.proto.ConvertirArchivosRequest other = (distribuidos.proto.ConvertirArchivosRequest) obj;

    if (!getArchivo()
        .equals(other.getArchivo())) return false;
    if (!getNombre()
        .equals(other.getNombre())) return false;
    if (!getTipo()
        .equals(other.getTipo())) return false;
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
    hash = (37 * hash) + ARCHIVO_FIELD_NUMBER;
    hash = (53 * hash) + getArchivo().hashCode();
    hash = (37 * hash) + NOMBRE_FIELD_NUMBER;
    hash = (53 * hash) + getNombre().hashCode();
    hash = (37 * hash) + TIPO_FIELD_NUMBER;
    hash = (53 * hash) + getTipo().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static distribuidos.proto.ConvertirArchivosRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static distribuidos.proto.ConvertirArchivosRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static distribuidos.proto.ConvertirArchivosRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static distribuidos.proto.ConvertirArchivosRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static distribuidos.proto.ConvertirArchivosRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static distribuidos.proto.ConvertirArchivosRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static distribuidos.proto.ConvertirArchivosRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static distribuidos.proto.ConvertirArchivosRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static distribuidos.proto.ConvertirArchivosRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static distribuidos.proto.ConvertirArchivosRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static distribuidos.proto.ConvertirArchivosRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static distribuidos.proto.ConvertirArchivosRequest parseFrom(
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
  public static Builder newBuilder(distribuidos.proto.ConvertirArchivosRequest prototype) {
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
   * Protobuf type {@code distribuidos.ConvertirArchivosRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:distribuidos.ConvertirArchivosRequest)
      distribuidos.proto.ConvertirArchivosRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return distribuidos.proto.ServiceProto.internal_static_distribuidos_ConvertirArchivosRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return distribuidos.proto.ServiceProto.internal_static_distribuidos_ConvertirArchivosRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              distribuidos.proto.ConvertirArchivosRequest.class, distribuidos.proto.ConvertirArchivosRequest.Builder.class);
    }

    // Construct using distribuidos.proto.ConvertirArchivosRequest.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      archivo_ = "";

      nombre_ = "";

      tipo_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return distribuidos.proto.ServiceProto.internal_static_distribuidos_ConvertirArchivosRequest_descriptor;
    }

    @java.lang.Override
    public distribuidos.proto.ConvertirArchivosRequest getDefaultInstanceForType() {
      return distribuidos.proto.ConvertirArchivosRequest.getDefaultInstance();
    }

    @java.lang.Override
    public distribuidos.proto.ConvertirArchivosRequest build() {
      distribuidos.proto.ConvertirArchivosRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public distribuidos.proto.ConvertirArchivosRequest buildPartial() {
      distribuidos.proto.ConvertirArchivosRequest result = new distribuidos.proto.ConvertirArchivosRequest(this);
      result.archivo_ = archivo_;
      result.nombre_ = nombre_;
      result.tipo_ = tipo_;
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
      if (other instanceof distribuidos.proto.ConvertirArchivosRequest) {
        return mergeFrom((distribuidos.proto.ConvertirArchivosRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(distribuidos.proto.ConvertirArchivosRequest other) {
      if (other == distribuidos.proto.ConvertirArchivosRequest.getDefaultInstance()) return this;
      if (!other.getArchivo().isEmpty()) {
        archivo_ = other.archivo_;
        onChanged();
      }
      if (!other.getNombre().isEmpty()) {
        nombre_ = other.nombre_;
        onChanged();
      }
      if (!other.getTipo().isEmpty()) {
        tipo_ = other.tipo_;
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
              archivo_ = input.readStringRequireUtf8();

              break;
            } // case 10
            case 18: {
              nombre_ = input.readStringRequireUtf8();

              break;
            } // case 18
            case 26: {
              tipo_ = input.readStringRequireUtf8();

              break;
            } // case 26
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

    private java.lang.Object archivo_ = "";
    /**
     * <code>string archivo = 1;</code>
     * @return The archivo.
     */
    public java.lang.String getArchivo() {
      java.lang.Object ref = archivo_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        archivo_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string archivo = 1;</code>
     * @return The bytes for archivo.
     */
    public com.google.protobuf.ByteString
        getArchivoBytes() {
      java.lang.Object ref = archivo_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        archivo_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string archivo = 1;</code>
     * @param value The archivo to set.
     * @return This builder for chaining.
     */
    public Builder setArchivo(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      archivo_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string archivo = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearArchivo() {
      
      archivo_ = getDefaultInstance().getArchivo();
      onChanged();
      return this;
    }
    /**
     * <code>string archivo = 1;</code>
     * @param value The bytes for archivo to set.
     * @return This builder for chaining.
     */
    public Builder setArchivoBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      archivo_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object nombre_ = "";
    /**
     * <code>string nombre = 2;</code>
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
     * <code>string nombre = 2;</code>
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
     * <code>string nombre = 2;</code>
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
     * <code>string nombre = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearNombre() {
      
      nombre_ = getDefaultInstance().getNombre();
      onChanged();
      return this;
    }
    /**
     * <code>string nombre = 2;</code>
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

    private java.lang.Object tipo_ = "";
    /**
     * <code>string tipo = 3;</code>
     * @return The tipo.
     */
    public java.lang.String getTipo() {
      java.lang.Object ref = tipo_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        tipo_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string tipo = 3;</code>
     * @return The bytes for tipo.
     */
    public com.google.protobuf.ByteString
        getTipoBytes() {
      java.lang.Object ref = tipo_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        tipo_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string tipo = 3;</code>
     * @param value The tipo to set.
     * @return This builder for chaining.
     */
    public Builder setTipo(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      tipo_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string tipo = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearTipo() {
      
      tipo_ = getDefaultInstance().getTipo();
      onChanged();
      return this;
    }
    /**
     * <code>string tipo = 3;</code>
     * @param value The bytes for tipo to set.
     * @return This builder for chaining.
     */
    public Builder setTipoBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      tipo_ = value;
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


    // @@protoc_insertion_point(builder_scope:distribuidos.ConvertirArchivosRequest)
  }

  // @@protoc_insertion_point(class_scope:distribuidos.ConvertirArchivosRequest)
  private static final distribuidos.proto.ConvertirArchivosRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new distribuidos.proto.ConvertirArchivosRequest();
  }

  public static distribuidos.proto.ConvertirArchivosRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ConvertirArchivosRequest>
      PARSER = new com.google.protobuf.AbstractParser<ConvertirArchivosRequest>() {
    @java.lang.Override
    public ConvertirArchivosRequest parsePartialFrom(
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

  public static com.google.protobuf.Parser<ConvertirArchivosRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ConvertirArchivosRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public distribuidos.proto.ConvertirArchivosRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

