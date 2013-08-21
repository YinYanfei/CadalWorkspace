/**
 * Autogenerated by Thrift Compiler (0.8.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package cn.cadal.rec.ol.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Book implements org.apache.thrift.TBase<Book, Book._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Book");

  private static final org.apache.thrift.protocol.TField BOOK_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("bookId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField BOOK_TITLE_FIELD_DESC = new org.apache.thrift.protocol.TField("bookTitle", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField BOOK_PUBLISHER_FIELD_DESC = new org.apache.thrift.protocol.TField("bookPublisher", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField BOOK_AUTHOR_FIELD_DESC = new org.apache.thrift.protocol.TField("bookAuthor", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField BOOK_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("bookType", org.apache.thrift.protocol.TType.STRING, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new BookStandardSchemeFactory());
    schemes.put(TupleScheme.class, new BookTupleSchemeFactory());
  }

  public String bookId; // required
  public String bookTitle; // required
  public String bookPublisher; // required
  public String bookAuthor; // required
  public String bookType; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    BOOK_ID((short)1, "bookId"),
    BOOK_TITLE((short)2, "bookTitle"),
    BOOK_PUBLISHER((short)3, "bookPublisher"),
    BOOK_AUTHOR((short)4, "bookAuthor"),
    BOOK_TYPE((short)5, "bookType");

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
        case 1: // BOOK_ID
          return BOOK_ID;
        case 2: // BOOK_TITLE
          return BOOK_TITLE;
        case 3: // BOOK_PUBLISHER
          return BOOK_PUBLISHER;
        case 4: // BOOK_AUTHOR
          return BOOK_AUTHOR;
        case 5: // BOOK_TYPE
          return BOOK_TYPE;
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
    tmpMap.put(_Fields.BOOK_ID, new org.apache.thrift.meta_data.FieldMetaData("bookId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.BOOK_TITLE, new org.apache.thrift.meta_data.FieldMetaData("bookTitle", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.BOOK_PUBLISHER, new org.apache.thrift.meta_data.FieldMetaData("bookPublisher", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.BOOK_AUTHOR, new org.apache.thrift.meta_data.FieldMetaData("bookAuthor", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.BOOK_TYPE, new org.apache.thrift.meta_data.FieldMetaData("bookType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Book.class, metaDataMap);
  }

  public Book() {
  }

  public Book(
    String bookId,
    String bookTitle,
    String bookPublisher,
    String bookAuthor,
    String bookType)
  {
    this();
    this.bookId = bookId;
    this.bookTitle = bookTitle;
    this.bookPublisher = bookPublisher;
    this.bookAuthor = bookAuthor;
    this.bookType = bookType;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Book(Book other) {
    if (other.isSetBookId()) {
      this.bookId = other.bookId;
    }
    if (other.isSetBookTitle()) {
      this.bookTitle = other.bookTitle;
    }
    if (other.isSetBookPublisher()) {
      this.bookPublisher = other.bookPublisher;
    }
    if (other.isSetBookAuthor()) {
      this.bookAuthor = other.bookAuthor;
    }
    if (other.isSetBookType()) {
      this.bookType = other.bookType;
    }
  }

  public Book deepCopy() {
    return new Book(this);
  }

  @Override
  public void clear() {
    this.bookId = null;
    this.bookTitle = null;
    this.bookPublisher = null;
    this.bookAuthor = null;
    this.bookType = null;
  }

  public String getBookId() {
    return this.bookId;
  }

  public Book setBookId(String bookId) {
    this.bookId = bookId;
    return this;
  }

  public void unsetBookId() {
    this.bookId = null;
  }

  /** Returns true if field bookId is set (has been assigned a value) and false otherwise */
  public boolean isSetBookId() {
    return this.bookId != null;
  }

  public void setBookIdIsSet(boolean value) {
    if (!value) {
      this.bookId = null;
    }
  }

  public String getBookTitle() {
    return this.bookTitle;
  }

  public Book setBookTitle(String bookTitle) {
    this.bookTitle = bookTitle;
    return this;
  }

  public void unsetBookTitle() {
    this.bookTitle = null;
  }

  /** Returns true if field bookTitle is set (has been assigned a value) and false otherwise */
  public boolean isSetBookTitle() {
    return this.bookTitle != null;
  }

  public void setBookTitleIsSet(boolean value) {
    if (!value) {
      this.bookTitle = null;
    }
  }

  public String getBookPublisher() {
    return this.bookPublisher;
  }

  public Book setBookPublisher(String bookPublisher) {
    this.bookPublisher = bookPublisher;
    return this;
  }

  public void unsetBookPublisher() {
    this.bookPublisher = null;
  }

  /** Returns true if field bookPublisher is set (has been assigned a value) and false otherwise */
  public boolean isSetBookPublisher() {
    return this.bookPublisher != null;
  }

  public void setBookPublisherIsSet(boolean value) {
    if (!value) {
      this.bookPublisher = null;
    }
  }

  public String getBookAuthor() {
    return this.bookAuthor;
  }

  public Book setBookAuthor(String bookAuthor) {
    this.bookAuthor = bookAuthor;
    return this;
  }

  public void unsetBookAuthor() {
    this.bookAuthor = null;
  }

  /** Returns true if field bookAuthor is set (has been assigned a value) and false otherwise */
  public boolean isSetBookAuthor() {
    return this.bookAuthor != null;
  }

  public void setBookAuthorIsSet(boolean value) {
    if (!value) {
      this.bookAuthor = null;
    }
  }

  public String getBookType() {
    return this.bookType;
  }

  public Book setBookType(String bookType) {
    this.bookType = bookType;
    return this;
  }

  public void unsetBookType() {
    this.bookType = null;
  }

  /** Returns true if field bookType is set (has been assigned a value) and false otherwise */
  public boolean isSetBookType() {
    return this.bookType != null;
  }

  public void setBookTypeIsSet(boolean value) {
    if (!value) {
      this.bookType = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case BOOK_ID:
      if (value == null) {
        unsetBookId();
      } else {
        setBookId((String)value);
      }
      break;

    case BOOK_TITLE:
      if (value == null) {
        unsetBookTitle();
      } else {
        setBookTitle((String)value);
      }
      break;

    case BOOK_PUBLISHER:
      if (value == null) {
        unsetBookPublisher();
      } else {
        setBookPublisher((String)value);
      }
      break;

    case BOOK_AUTHOR:
      if (value == null) {
        unsetBookAuthor();
      } else {
        setBookAuthor((String)value);
      }
      break;

    case BOOK_TYPE:
      if (value == null) {
        unsetBookType();
      } else {
        setBookType((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case BOOK_ID:
      return getBookId();

    case BOOK_TITLE:
      return getBookTitle();

    case BOOK_PUBLISHER:
      return getBookPublisher();

    case BOOK_AUTHOR:
      return getBookAuthor();

    case BOOK_TYPE:
      return getBookType();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case BOOK_ID:
      return isSetBookId();
    case BOOK_TITLE:
      return isSetBookTitle();
    case BOOK_PUBLISHER:
      return isSetBookPublisher();
    case BOOK_AUTHOR:
      return isSetBookAuthor();
    case BOOK_TYPE:
      return isSetBookType();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Book)
      return this.equals((Book)that);
    return false;
  }

  public boolean equals(Book that) {
    if (that == null)
      return false;

    boolean this_present_bookId = true && this.isSetBookId();
    boolean that_present_bookId = true && that.isSetBookId();
    if (this_present_bookId || that_present_bookId) {
      if (!(this_present_bookId && that_present_bookId))
        return false;
      if (!this.bookId.equals(that.bookId))
        return false;
    }

    boolean this_present_bookTitle = true && this.isSetBookTitle();
    boolean that_present_bookTitle = true && that.isSetBookTitle();
    if (this_present_bookTitle || that_present_bookTitle) {
      if (!(this_present_bookTitle && that_present_bookTitle))
        return false;
      if (!this.bookTitle.equals(that.bookTitle))
        return false;
    }

    boolean this_present_bookPublisher = true && this.isSetBookPublisher();
    boolean that_present_bookPublisher = true && that.isSetBookPublisher();
    if (this_present_bookPublisher || that_present_bookPublisher) {
      if (!(this_present_bookPublisher && that_present_bookPublisher))
        return false;
      if (!this.bookPublisher.equals(that.bookPublisher))
        return false;
    }

    boolean this_present_bookAuthor = true && this.isSetBookAuthor();
    boolean that_present_bookAuthor = true && that.isSetBookAuthor();
    if (this_present_bookAuthor || that_present_bookAuthor) {
      if (!(this_present_bookAuthor && that_present_bookAuthor))
        return false;
      if (!this.bookAuthor.equals(that.bookAuthor))
        return false;
    }

    boolean this_present_bookType = true && this.isSetBookType();
    boolean that_present_bookType = true && that.isSetBookType();
    if (this_present_bookType || that_present_bookType) {
      if (!(this_present_bookType && that_present_bookType))
        return false;
      if (!this.bookType.equals(that.bookType))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(Book other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    Book typedOther = (Book)other;

    lastComparison = Boolean.valueOf(isSetBookId()).compareTo(typedOther.isSetBookId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBookId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.bookId, typedOther.bookId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBookTitle()).compareTo(typedOther.isSetBookTitle());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBookTitle()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.bookTitle, typedOther.bookTitle);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBookPublisher()).compareTo(typedOther.isSetBookPublisher());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBookPublisher()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.bookPublisher, typedOther.bookPublisher);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBookAuthor()).compareTo(typedOther.isSetBookAuthor());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBookAuthor()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.bookAuthor, typedOther.bookAuthor);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBookType()).compareTo(typedOther.isSetBookType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBookType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.bookType, typedOther.bookType);
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
    StringBuilder sb = new StringBuilder("Book(");
    boolean first = true;

    sb.append("bookId:");
    if (this.bookId == null) {
      sb.append("null");
    } else {
      sb.append(this.bookId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("bookTitle:");
    if (this.bookTitle == null) {
      sb.append("null");
    } else {
      sb.append(this.bookTitle);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("bookPublisher:");
    if (this.bookPublisher == null) {
      sb.append("null");
    } else {
      sb.append(this.bookPublisher);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("bookAuthor:");
    if (this.bookAuthor == null) {
      sb.append("null");
    } else {
      sb.append(this.bookAuthor);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("bookType:");
    if (this.bookType == null) {
      sb.append("null");
    } else {
      sb.append(this.bookType);
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

  private static class BookStandardSchemeFactory implements SchemeFactory {
    public BookStandardScheme getScheme() {
      return new BookStandardScheme();
    }
  }

  private static class BookStandardScheme extends StandardScheme<Book> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Book struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // BOOK_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.bookId = iprot.readString();
              struct.setBookIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // BOOK_TITLE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.bookTitle = iprot.readString();
              struct.setBookTitleIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // BOOK_PUBLISHER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.bookPublisher = iprot.readString();
              struct.setBookPublisherIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // BOOK_AUTHOR
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.bookAuthor = iprot.readString();
              struct.setBookAuthorIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // BOOK_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.bookType = iprot.readString();
              struct.setBookTypeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Book struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.bookId != null) {
        oprot.writeFieldBegin(BOOK_ID_FIELD_DESC);
        oprot.writeString(struct.bookId);
        oprot.writeFieldEnd();
      }
      if (struct.bookTitle != null) {
        oprot.writeFieldBegin(BOOK_TITLE_FIELD_DESC);
        oprot.writeString(struct.bookTitle);
        oprot.writeFieldEnd();
      }
      if (struct.bookPublisher != null) {
        oprot.writeFieldBegin(BOOK_PUBLISHER_FIELD_DESC);
        oprot.writeString(struct.bookPublisher);
        oprot.writeFieldEnd();
      }
      if (struct.bookAuthor != null) {
        oprot.writeFieldBegin(BOOK_AUTHOR_FIELD_DESC);
        oprot.writeString(struct.bookAuthor);
        oprot.writeFieldEnd();
      }
      if (struct.bookType != null) {
        oprot.writeFieldBegin(BOOK_TYPE_FIELD_DESC);
        oprot.writeString(struct.bookType);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class BookTupleSchemeFactory implements SchemeFactory {
    public BookTupleScheme getScheme() {
      return new BookTupleScheme();
    }
  }

  private static class BookTupleScheme extends TupleScheme<Book> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Book struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetBookId()) {
        optionals.set(0);
      }
      if (struct.isSetBookTitle()) {
        optionals.set(1);
      }
      if (struct.isSetBookPublisher()) {
        optionals.set(2);
      }
      if (struct.isSetBookAuthor()) {
        optionals.set(3);
      }
      if (struct.isSetBookType()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals);
      if (struct.isSetBookId()) {
        oprot.writeString(struct.bookId);
      }
      if (struct.isSetBookTitle()) {
        oprot.writeString(struct.bookTitle);
      }
      if (struct.isSetBookPublisher()) {
        oprot.writeString(struct.bookPublisher);
      }
      if (struct.isSetBookAuthor()) {
        oprot.writeString(struct.bookAuthor);
      }
      if (struct.isSetBookType()) {
        oprot.writeString(struct.bookType);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Book struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.bookId = iprot.readString();
        struct.setBookIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.bookTitle = iprot.readString();
        struct.setBookTitleIsSet(true);
      }
      if (incoming.get(2)) {
        struct.bookPublisher = iprot.readString();
        struct.setBookPublisherIsSet(true);
      }
      if (incoming.get(3)) {
        struct.bookAuthor = iprot.readString();
        struct.setBookAuthorIsSet(true);
      }
      if (incoming.get(4)) {
        struct.bookType = iprot.readString();
        struct.setBookTypeIsSet(true);
      }
    }
  }

}

