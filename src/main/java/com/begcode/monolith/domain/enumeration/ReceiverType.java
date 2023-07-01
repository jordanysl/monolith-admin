package com.begcode.monolith.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The ReceiverType enumeration.
 */
public enum ReceiverType {
    USER("USER", "USER"),
    ALL("ALL", "ALL"),
    DEPARTMENT("DEPARTMENT", "DEPARTMENT"),
    AUTHORITY("AUTHORITY", "AUTHORITY"),
    POSITION("POSITION", "POSITION");

    @JsonValue
    private String value;

    private String desc;

    ReceiverType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static ReceiverType getByValue(String value) {
        for (ReceiverType enumReceiverType : ReceiverType.values()) {
            if (enumReceiverType.getValue().equals(value)) {
                return enumReceiverType;
            }
        }
        return null;
    }

    public static ReceiverType getByDesc(String desc) {
        for (ReceiverType enumReceiverType : ReceiverType.values()) {
            if (enumReceiverType.getDesc().equals(desc)) {
                return enumReceiverType;
            }
        }
        return null;
    }
}
