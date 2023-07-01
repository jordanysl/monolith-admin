package com.begcode.monolith.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The AnnoBusinessType enumeration.
 */
public enum AnnoBusinessType {
    EMAIL("EMAIL", "EMAIL"),
    WORKFLOW("WORKFLOW", "WORKFLOW");

    @JsonValue
    private String value;

    private String desc;

    AnnoBusinessType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static AnnoBusinessType getByValue(String value) {
        for (AnnoBusinessType enumAnnoBusinessType : AnnoBusinessType.values()) {
            if (enumAnnoBusinessType.getValue().equals(value)) {
                return enumAnnoBusinessType;
            }
        }
        return null;
    }

    public static AnnoBusinessType getByDesc(String desc) {
        for (AnnoBusinessType enumAnnoBusinessType : AnnoBusinessType.values()) {
            if (enumAnnoBusinessType.getDesc().equals(desc)) {
                return enumAnnoBusinessType;
            }
        }
        return null;
    }
}
