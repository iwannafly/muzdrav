/**
 * Autogenerated by Thrift Compiler (0.9.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package ru.nkz.ivcgzo.thriftCommon.classifier;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum ClassifierSortFields implements org.apache.thrift.TEnum {
  pcod(1),
  name(2),
  pcodName(3);

  private final int value;

  private ClassifierSortFields(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static ClassifierSortFields findByValue(int value) { 
    switch (value) {
      case 1:
        return pcod;
      case 2:
        return name;
      case 3:
        return pcodName;
      default:
        return null;
    }
  }
}