package cn.cadal.storm.demo.thriftClient;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecServer {

  public interface Iface {

    public String GetRecInfo(String cluster, String resultNum) throws org.apache.thrift.TException;

  }

  public interface AsyncIface {

    public void GetRecInfo(String cluster, String resultNum, org.apache.thrift.async.AsyncMethodCallback<AsyncClient.GetRecInfo_call> resultHandler) throws org.apache.thrift.TException;

  }

  public static class Client extends org.apache.thrift.TServiceClient implements Iface {
    public static class Factory implements org.apache.thrift.TServiceClientFactory<Client> {
      public Factory() {}
      public Client getClient(org.apache.thrift.protocol.TProtocol prot) {
        return new Client(prot);
      }
      public Client getClient(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
        return new Client(iprot, oprot);
      }
    }

    public Client(org.apache.thrift.protocol.TProtocol prot)
    {
      super(prot, prot);
    }

    public Client(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
      super(iprot, oprot);
    }

    public String GetRecInfo(String cluster, String resultNum) throws org.apache.thrift.TException
    {
      send_GetRecInfo(cluster, resultNum);
      return recv_GetRecInfo();
    }

    public void send_GetRecInfo(String cluster, String resultNum) throws org.apache.thrift.TException
    {
      GetRecInfo_args args = new GetRecInfo_args();
      args.setCluster(cluster);
      args.setResultNum(resultNum);
      sendBase("GetRecInfo", args);
    }

    public String recv_GetRecInfo() throws org.apache.thrift.TException
    {
      GetRecInfo_result result = new GetRecInfo_result();
      receiveBase(result, "GetRecInfo");
      if (result.isSetSuccess()) {
        return result.success;
      }
      throw new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.MISSING_RESULT, "GetRecInfo failed: unknown result");
    }

  }
  public static class AsyncClient extends org.apache.thrift.async.TAsyncClient implements AsyncIface {
    public static class Factory implements org.apache.thrift.async.TAsyncClientFactory<AsyncClient> {
      private org.apache.thrift.async.TAsyncClientManager clientManager;
      private org.apache.thrift.protocol.TProtocolFactory protocolFactory;
      public Factory(org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.protocol.TProtocolFactory protocolFactory) {
        this.clientManager = clientManager;
        this.protocolFactory = protocolFactory;
      }
      public AsyncClient getAsyncClient(org.apache.thrift.transport.TNonblockingTransport transport) {
        return new AsyncClient(protocolFactory, clientManager, transport);
      }
    }

    public AsyncClient(org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.transport.TNonblockingTransport transport) {
      super(protocolFactory, clientManager, transport);
    }

    public void GetRecInfo(String cluster, String resultNum, org.apache.thrift.async.AsyncMethodCallback<GetRecInfo_call> resultHandler) throws org.apache.thrift.TException {
      checkReady();
      GetRecInfo_call method_call = new GetRecInfo_call(cluster, resultNum, resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class GetRecInfo_call extends org.apache.thrift.async.TAsyncMethodCall {
      private String cluster;
      private String resultNum;
      public GetRecInfo_call(String cluster, String resultNum, org.apache.thrift.async.AsyncMethodCallback<GetRecInfo_call> resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.cluster = cluster;
        this.resultNum = resultNum;
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("GetRecInfo", org.apache.thrift.protocol.TMessageType.CALL, 0));
        GetRecInfo_args args = new GetRecInfo_args();
        args.setCluster(cluster);
        args.setResultNum(resultNum);
        args.write(prot);
        prot.writeMessageEnd();
      }

      public String getResult() throws org.apache.thrift.TException {
        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        return (new Client(prot)).recv_GetRecInfo();
      }
    }

  }

  public static class Processor<I extends Iface> extends org.apache.thrift.TBaseProcessor<I> implements org.apache.thrift.TProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class.getName());
    public Processor(I iface) {
      super(iface, getProcessMap(new HashMap<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>>()));
    }

    protected Processor(I iface, Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends Iface> Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> getProcessMap(Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      processMap.put("GetRecInfo", new GetRecInfo());
      return processMap;
    }

    private static class GetRecInfo<I extends Iface> extends org.apache.thrift.ProcessFunction<I, GetRecInfo_args> {
      public GetRecInfo() {
        super("GetRecInfo");
      }

      protected GetRecInfo_args getEmptyArgsInstance() {
        return new GetRecInfo_args();
      }

      protected GetRecInfo_result getResult(I iface, GetRecInfo_args args) throws org.apache.thrift.TException {
        GetRecInfo_result result = new GetRecInfo_result();
        result.success = iface.GetRecInfo(args.cluster, args.resultNum);
        return result;
      }
    }

  }

  public static class GetRecInfo_args implements org.apache.thrift.TBase<GetRecInfo_args, GetRecInfo_args._Fields>, java.io.Serializable, Cloneable   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetRecInfo_args");

    private static final org.apache.thrift.protocol.TField CLUSTER_FIELD_DESC = new org.apache.thrift.protocol.TField("cluster", org.apache.thrift.protocol.TType.STRING, (short)1);
    private static final org.apache.thrift.protocol.TField RESULT_NUM_FIELD_DESC = new org.apache.thrift.protocol.TField("resultNum", org.apache.thrift.protocol.TType.STRING, (short)2);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new GetRecInfo_argsStandardSchemeFactory());
      schemes.put(TupleScheme.class, new GetRecInfo_argsTupleSchemeFactory());
    }

    public String cluster; // required
    public String resultNum; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      CLUSTER((short)1, "cluster"),
      RESULT_NUM((short)2, "resultNum");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // CLUSTER
            return CLUSTER;
          case 2: // RESULT_NUM
            return RESULT_NUM;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.CLUSTER, new org.apache.thrift.meta_data.FieldMetaData("cluster", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
      tmpMap.put(_Fields.RESULT_NUM, new org.apache.thrift.meta_data.FieldMetaData("resultNum", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetRecInfo_args.class, metaDataMap);
    }

    public GetRecInfo_args() {
    }

    public GetRecInfo_args(
      String cluster,
      String resultNum)
    {
      this();
      this.cluster = cluster;
      this.resultNum = resultNum;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public GetRecInfo_args(GetRecInfo_args other) {
      if (other.isSetCluster()) {
        this.cluster = other.cluster;
      }
      if (other.isSetResultNum()) {
        this.resultNum = other.resultNum;
      }
    }

    public GetRecInfo_args deepCopy() {
      return new GetRecInfo_args(this);
    }

    public void clear() {
      this.cluster = null;
      this.resultNum = null;
    }

    public String getCluster() {
      return this.cluster;
    }

    public GetRecInfo_args setCluster(String cluster) {
      this.cluster = cluster;
      return this;
    }

    public void unsetCluster() {
      this.cluster = null;
    }

    /** Returns true if field cluster is set (has been assigned a value) and false otherwise */
    public boolean isSetCluster() {
      return this.cluster != null;
    }

    public void setClusterIsSet(boolean value) {
      if (!value) {
        this.cluster = null;
      }
    }

    public String getResultNum() {
      return this.resultNum;
    }

    public GetRecInfo_args setResultNum(String resultNum) {
      this.resultNum = resultNum;
      return this;
    }

    public void unsetResultNum() {
      this.resultNum = null;
    }

    /** Returns true if field resultNum is set (has been assigned a value) and false otherwise */
    public boolean isSetResultNum() {
      return this.resultNum != null;
    }

    public void setResultNumIsSet(boolean value) {
      if (!value) {
        this.resultNum = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case CLUSTER:
        if (value == null) {
          unsetCluster();
        } else {
          setCluster((String)value);
        }
        break;

      case RESULT_NUM:
        if (value == null) {
          unsetResultNum();
        } else {
          setResultNum((String)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case CLUSTER:
        return getCluster();

      case RESULT_NUM:
        return getResultNum();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case CLUSTER:
        return isSetCluster();
      case RESULT_NUM:
        return isSetResultNum();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof GetRecInfo_args)
        return this.equals((GetRecInfo_args)that);
      return false;
    }

    public boolean equals(GetRecInfo_args that) {
      if (that == null)
        return false;

      boolean this_present_cluster = true && this.isSetCluster();
      boolean that_present_cluster = true && that.isSetCluster();
      if (this_present_cluster || that_present_cluster) {
        if (!(this_present_cluster && that_present_cluster))
          return false;
        if (!this.cluster.equals(that.cluster))
          return false;
      }

      boolean this_present_resultNum = true && this.isSetResultNum();
      boolean that_present_resultNum = true && that.isSetResultNum();
      if (this_present_resultNum || that_present_resultNum) {
        if (!(this_present_resultNum && that_present_resultNum))
          return false;
        if (!this.resultNum.equals(that.resultNum))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public int compareTo(GetRecInfo_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;
      GetRecInfo_args typedOther = (GetRecInfo_args)other;

      lastComparison = Boolean.valueOf(isSetCluster()).compareTo(typedOther.isSetCluster());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetCluster()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.cluster, typedOther.cluster);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetResultNum()).compareTo(typedOther.isSetResultNum());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetResultNum()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.resultNum, typedOther.resultNum);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("GetRecInfo_args(");
      boolean first = true;

      sb.append("cluster:");
      if (this.cluster == null) {
        sb.append("null");
      } else {
        sb.append(this.cluster);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("resultNum:");
      if (this.resultNum == null) {
        sb.append("null");
      } else {
        sb.append(this.resultNum);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class GetRecInfo_argsStandardSchemeFactory implements SchemeFactory {
      public GetRecInfo_argsStandardScheme getScheme() {
        return new GetRecInfo_argsStandardScheme();
      }
    }

    private static class GetRecInfo_argsStandardScheme extends StandardScheme<GetRecInfo_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, GetRecInfo_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 1: // CLUSTER
              if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                struct.cluster = iprot.readString();
                struct.setClusterIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // RESULT_NUM
              if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                struct.resultNum = iprot.readString();
                struct.setResultNumIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, GetRecInfo_args struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.cluster != null) {
          oprot.writeFieldBegin(CLUSTER_FIELD_DESC);
          oprot.writeString(struct.cluster);
          oprot.writeFieldEnd();
        }
        if (struct.resultNum != null) {
          oprot.writeFieldBegin(RESULT_NUM_FIELD_DESC);
          oprot.writeString(struct.resultNum);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class GetRecInfo_argsTupleSchemeFactory implements SchemeFactory {
      public GetRecInfo_argsTupleScheme getScheme() {
        return new GetRecInfo_argsTupleScheme();
      }
    }

    private static class GetRecInfo_argsTupleScheme extends TupleScheme<GetRecInfo_args> {

      public void write(org.apache.thrift.protocol.TProtocol prot, GetRecInfo_args struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetCluster()) {
          optionals.set(0);
        }
        if (struct.isSetResultNum()) {
          optionals.set(1);
        }
        oprot.writeBitSet(optionals);
        if (struct.isSetCluster()) {
          oprot.writeString(struct.cluster);
        }
        if (struct.isSetResultNum()) {
          oprot.writeString(struct.resultNum);
        }
      }

      public void read(org.apache.thrift.protocol.TProtocol prot, GetRecInfo_args struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(2);
        if (incoming.get(0)) {
          struct.cluster = iprot.readString();
          struct.setClusterIsSet(true);
        }
        if (incoming.get(1)) {
          struct.resultNum = iprot.readString();
          struct.setResultNumIsSet(true);
        }
      }
    }

  }

  public static class GetRecInfo_result implements org.apache.thrift.TBase<GetRecInfo_result, GetRecInfo_result._Fields>, java.io.Serializable, Cloneable   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetRecInfo_result");

    private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.STRING, (short)0);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new GetRecInfo_resultStandardSchemeFactory());
      schemes.put(TupleScheme.class, new GetRecInfo_resultTupleSchemeFactory());
    }

    public String success; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      SUCCESS((short)0, "success");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 0: // SUCCESS
            return SUCCESS;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetRecInfo_result.class, metaDataMap);
    }

    public GetRecInfo_result() {
    }

    public GetRecInfo_result(
      String success)
    {
      this();
      this.success = success;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public GetRecInfo_result(GetRecInfo_result other) {
      if (other.isSetSuccess()) {
        this.success = other.success;
      }
    }

    public GetRecInfo_result deepCopy() {
      return new GetRecInfo_result(this);
    }

    public void clear() {
      this.success = null;
    }

    public String getSuccess() {
      return this.success;
    }

    public GetRecInfo_result setSuccess(String success) {
      this.success = success;
      return this;
    }

    public void unsetSuccess() {
      this.success = null;
    }

    /** Returns true if field success is set (has been assigned a value) and false otherwise */
    public boolean isSetSuccess() {
      return this.success != null;
    }

    public void setSuccessIsSet(boolean value) {
      if (!value) {
        this.success = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case SUCCESS:
        if (value == null) {
          unsetSuccess();
        } else {
          setSuccess((String)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case SUCCESS:
        return getSuccess();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case SUCCESS:
        return isSetSuccess();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof GetRecInfo_result)
        return this.equals((GetRecInfo_result)that);
      return false;
    }

    public boolean equals(GetRecInfo_result that) {
      if (that == null)
        return false;

      boolean this_present_success = true && this.isSetSuccess();
      boolean that_present_success = true && that.isSetSuccess();
      if (this_present_success || that_present_success) {
        if (!(this_present_success && that_present_success))
          return false;
        if (!this.success.equals(that.success))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public int compareTo(GetRecInfo_result other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;
      GetRecInfo_result typedOther = (GetRecInfo_result)other;

      lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(typedOther.isSetSuccess());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetSuccess()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, typedOther.success);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
      }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("GetRecInfo_result(");
      boolean first = true;

      sb.append("success:");
      if (this.success == null) {
        sb.append("null");
      } else {
        sb.append(this.success);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class GetRecInfo_resultStandardSchemeFactory implements SchemeFactory {
      public GetRecInfo_resultStandardScheme getScheme() {
        return new GetRecInfo_resultStandardScheme();
      }
    }

    private static class GetRecInfo_resultStandardScheme extends StandardScheme<GetRecInfo_result> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, GetRecInfo_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 0: // SUCCESS
              if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                struct.success = iprot.readString();
                struct.setSuccessIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, GetRecInfo_result struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.success != null) {
          oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
          oprot.writeString(struct.success);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class GetRecInfo_resultTupleSchemeFactory implements SchemeFactory {
      public GetRecInfo_resultTupleScheme getScheme() {
        return new GetRecInfo_resultTupleScheme();
      }
    }

    private static class GetRecInfo_resultTupleScheme extends TupleScheme<GetRecInfo_result> {

      public void write(org.apache.thrift.protocol.TProtocol prot, GetRecInfo_result struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetSuccess()) {
          optionals.set(0);
        }
        oprot.writeBitSet(optionals);
        if (struct.isSetSuccess()) {
          oprot.writeString(struct.success);
        }
      }

      public void read(org.apache.thrift.protocol.TProtocol prot, GetRecInfo_result struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(1);
        if (incoming.get(0)) {
          struct.success = iprot.readString();
          struct.setSuccessIsSet(true);
        }
      }
    }

  }

}
