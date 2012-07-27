/**
 * Autogenerated by Thrift Compiler (0.8.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package ru.nkz.ivcgzo.thriftServerVrachInfo;

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

public class ShablonText implements org.apache.thrift.TBase<ShablonText, ShablonText._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ShablonText");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField ID_RAZD_FIELD_DESC = new org.apache.thrift.protocol.TField("id_razd", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField ID_POK_FIELD_DESC = new org.apache.thrift.protocol.TField("id_pok", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField PCOD_S00_FIELD_DESC = new org.apache.thrift.protocol.TField("pcod_s00", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField TEXT_FIELD_DESC = new org.apache.thrift.protocol.TField("text", org.apache.thrift.protocol.TType.STRING, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ShablonTextStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ShablonTextTupleSchemeFactory());
  }

  public int id; // required
  public int id_razd; // required
  public int id_pok; // required
  public String pcod_s00; // required
  public String text; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    ID_RAZD((short)2, "id_razd"),
    ID_POK((short)3, "id_pok"),
    PCOD_S00((short)4, "pcod_s00"),
    TEXT((short)5, "text");

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
        case 1: // ID
          return ID;
        case 2: // ID_RAZD
          return ID_RAZD;
        case 3: // ID_POK
          return ID_POK;
        case 4: // PCOD_S00
          return PCOD_S00;
        case 5: // TEXT
          return TEXT;
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
  private static final int __ID_ISSET_ID = 0;
  private static final int __ID_RAZD_ISSET_ID = 1;
  private static final int __ID_POK_ISSET_ID = 2;
  private BitSet __isset_bit_vector = new BitSet(3);
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ID_RAZD, new org.apache.thrift.meta_data.FieldMetaData("id_razd", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ID_POK, new org.apache.thrift.meta_data.FieldMetaData("id_pok", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PCOD_S00, new org.apache.thrift.meta_data.FieldMetaData("pcod_s00", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TEXT, new org.apache.thrift.meta_data.FieldMetaData("text", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ShablonText.class, metaDataMap);
  }

  public ShablonText() {
  }

  public ShablonText(
    int id,
    int id_razd,
    int id_pok,
    String pcod_s00,
    String text)
  {
    this();
    this.id = id;
    setIdIsSet(true);
    this.id_razd = id_razd;
    setId_razdIsSet(true);
    this.id_pok = id_pok;
    setId_pokIsSet(true);
    this.pcod_s00 = pcod_s00;
    this.text = text;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ShablonText(ShablonText other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    this.id = other.id;
    this.id_razd = other.id_razd;
    this.id_pok = other.id_pok;
    if (other.isSetPcod_s00()) {
      this.pcod_s00 = other.pcod_s00;
    }
    if (other.isSetText()) {
      this.text = other.text;
    }
  }

  public ShablonText deepCopy() {
    return new ShablonText(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    setId_razdIsSet(false);
    this.id_razd = 0;
    setId_pokIsSet(false);
    this.id_pok = 0;
    this.pcod_s00 = null;
    this.text = null;
  }

  public int getId() {
    return this.id;
  }

  public ShablonText setId(int id) {
    this.id = id;
    setIdIsSet(true);
    return this;
  }

  public void unsetId() {
    __isset_bit_vector.clear(__ID_ISSET_ID);
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return __isset_bit_vector.get(__ID_ISSET_ID);
  }

  public void setIdIsSet(boolean value) {
    __isset_bit_vector.set(__ID_ISSET_ID, value);
  }

  public int getId_razd() {
    return this.id_razd;
  }

  public ShablonText setId_razd(int id_razd) {
    this.id_razd = id_razd;
    setId_razdIsSet(true);
    return this;
  }

  public void unsetId_razd() {
    __isset_bit_vector.clear(__ID_RAZD_ISSET_ID);
  }

  /** Returns true if field id_razd is set (has been assigned a value) and false otherwise */
  public boolean isSetId_razd() {
    return __isset_bit_vector.get(__ID_RAZD_ISSET_ID);
  }

  public void setId_razdIsSet(boolean value) {
    __isset_bit_vector.set(__ID_RAZD_ISSET_ID, value);
  }

  public int getId_pok() {
    return this.id_pok;
  }

  public ShablonText setId_pok(int id_pok) {
    this.id_pok = id_pok;
    setId_pokIsSet(true);
    return this;
  }

  public void unsetId_pok() {
    __isset_bit_vector.clear(__ID_POK_ISSET_ID);
  }

  /** Returns true if field id_pok is set (has been assigned a value) and false otherwise */
  public boolean isSetId_pok() {
    return __isset_bit_vector.get(__ID_POK_ISSET_ID);
  }

  public void setId_pokIsSet(boolean value) {
    __isset_bit_vector.set(__ID_POK_ISSET_ID, value);
  }

  public String getPcod_s00() {
    return this.pcod_s00;
  }

  public ShablonText setPcod_s00(String pcod_s00) {
    this.pcod_s00 = pcod_s00;
    return this;
  }

  public void unsetPcod_s00() {
    this.pcod_s00 = null;
  }

  /** Returns true if field pcod_s00 is set (has been assigned a value) and false otherwise */
  public boolean isSetPcod_s00() {
    return this.pcod_s00 != null;
  }

  public void setPcod_s00IsSet(boolean value) {
    if (!value) {
      this.pcod_s00 = null;
    }
  }

  public String getText() {
    return this.text;
  }

  public ShablonText setText(String text) {
    this.text = text;
    return this;
  }

  public void unsetText() {
    this.text = null;
  }

  /** Returns true if field text is set (has been assigned a value) and false otherwise */
  public boolean isSetText() {
    return this.text != null;
  }

  public void setTextIsSet(boolean value) {
    if (!value) {
      this.text = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((Integer)value);
      }
      break;

    case ID_RAZD:
      if (value == null) {
        unsetId_razd();
      } else {
        setId_razd((Integer)value);
      }
      break;

    case ID_POK:
      if (value == null) {
        unsetId_pok();
      } else {
        setId_pok((Integer)value);
      }
      break;

    case PCOD_S00:
      if (value == null) {
        unsetPcod_s00();
      } else {
        setPcod_s00((String)value);
      }
      break;

    case TEXT:
      if (value == null) {
        unsetText();
      } else {
        setText((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return Integer.valueOf(getId());

    case ID_RAZD:
      return Integer.valueOf(getId_razd());

    case ID_POK:
      return Integer.valueOf(getId_pok());

    case PCOD_S00:
      return getPcod_s00();

    case TEXT:
      return getText();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case ID_RAZD:
      return isSetId_razd();
    case ID_POK:
      return isSetId_pok();
    case PCOD_S00:
      return isSetPcod_s00();
    case TEXT:
      return isSetText();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ShablonText)
      return this.equals((ShablonText)that);
    return false;
  }

  public boolean equals(ShablonText that) {
    if (that == null)
      return false;

    boolean this_present_id = true;
    boolean that_present_id = true;
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
        return false;
    }

    boolean this_present_id_razd = true;
    boolean that_present_id_razd = true;
    if (this_present_id_razd || that_present_id_razd) {
      if (!(this_present_id_razd && that_present_id_razd))
        return false;
      if (this.id_razd != that.id_razd)
        return false;
    }

    boolean this_present_id_pok = true;
    boolean that_present_id_pok = true;
    if (this_present_id_pok || that_present_id_pok) {
      if (!(this_present_id_pok && that_present_id_pok))
        return false;
      if (this.id_pok != that.id_pok)
        return false;
    }

    boolean this_present_pcod_s00 = true && this.isSetPcod_s00();
    boolean that_present_pcod_s00 = true && that.isSetPcod_s00();
    if (this_present_pcod_s00 || that_present_pcod_s00) {
      if (!(this_present_pcod_s00 && that_present_pcod_s00))
        return false;
      if (!this.pcod_s00.equals(that.pcod_s00))
        return false;
    }

    boolean this_present_text = true && this.isSetText();
    boolean that_present_text = true && that.isSetText();
    if (this_present_text || that_present_text) {
      if (!(this_present_text && that_present_text))
        return false;
      if (!this.text.equals(that.text))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(ShablonText other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    ShablonText typedOther = (ShablonText)other;

    lastComparison = Boolean.valueOf(isSetId()).compareTo(typedOther.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, typedOther.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetId_razd()).compareTo(typedOther.isSetId_razd());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId_razd()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id_razd, typedOther.id_razd);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetId_pok()).compareTo(typedOther.isSetId_pok());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId_pok()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id_pok, typedOther.id_pok);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPcod_s00()).compareTo(typedOther.isSetPcod_s00());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPcod_s00()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pcod_s00, typedOther.pcod_s00);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetText()).compareTo(typedOther.isSetText());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetText()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.text, typedOther.text);
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
    StringBuilder sb = new StringBuilder("ShablonText(");
    boolean first = true;

    sb.append("id:");
    sb.append(this.id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("id_razd:");
    sb.append(this.id_razd);
    first = false;
    if (!first) sb.append(", ");
    sb.append("id_pok:");
    sb.append(this.id_pok);
    first = false;
    if (!first) sb.append(", ");
    sb.append("pcod_s00:");
    if (this.pcod_s00 == null) {
      sb.append("null");
    } else {
      sb.append(this.pcod_s00);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("text:");
    if (this.text == null) {
      sb.append("null");
    } else {
      sb.append(this.text);
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bit_vector = new BitSet(1);
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ShablonTextStandardSchemeFactory implements SchemeFactory {
    public ShablonTextStandardScheme getScheme() {
      return new ShablonTextStandardScheme();
    }
  }

  private static class ShablonTextStandardScheme extends StandardScheme<ShablonText> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ShablonText struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.id = iprot.readI32();
              struct.setIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ID_RAZD
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.id_razd = iprot.readI32();
              struct.setId_razdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ID_POK
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.id_pok = iprot.readI32();
              struct.setId_pokIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // PCOD_S00
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.pcod_s00 = iprot.readString();
              struct.setPcod_s00IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // TEXT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.text = iprot.readString();
              struct.setTextIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ShablonText struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ID_FIELD_DESC);
      oprot.writeI32(struct.id);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(ID_RAZD_FIELD_DESC);
      oprot.writeI32(struct.id_razd);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(ID_POK_FIELD_DESC);
      oprot.writeI32(struct.id_pok);
      oprot.writeFieldEnd();
      if (struct.pcod_s00 != null) {
        oprot.writeFieldBegin(PCOD_S00_FIELD_DESC);
        oprot.writeString(struct.pcod_s00);
        oprot.writeFieldEnd();
      }
      if (struct.text != null) {
        oprot.writeFieldBegin(TEXT_FIELD_DESC);
        oprot.writeString(struct.text);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ShablonTextTupleSchemeFactory implements SchemeFactory {
    public ShablonTextTupleScheme getScheme() {
      return new ShablonTextTupleScheme();
    }
  }

  private static class ShablonTextTupleScheme extends TupleScheme<ShablonText> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ShablonText struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetId_razd()) {
        optionals.set(1);
      }
      if (struct.isSetId_pok()) {
        optionals.set(2);
      }
      if (struct.isSetPcod_s00()) {
        optionals.set(3);
      }
      if (struct.isSetText()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetId_razd()) {
        oprot.writeI32(struct.id_razd);
      }
      if (struct.isSetId_pok()) {
        oprot.writeI32(struct.id_pok);
      }
      if (struct.isSetPcod_s00()) {
        oprot.writeString(struct.pcod_s00);
      }
      if (struct.isSetText()) {
        oprot.writeString(struct.text);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ShablonText struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.id_razd = iprot.readI32();
        struct.setId_razdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.id_pok = iprot.readI32();
        struct.setId_pokIsSet(true);
      }
      if (incoming.get(3)) {
        struct.pcod_s00 = iprot.readString();
        struct.setPcod_s00IsSet(true);
      }
      if (incoming.get(4)) {
        struct.text = iprot.readString();
        struct.setTextIsSet(true);
      }
    }
  }

}

