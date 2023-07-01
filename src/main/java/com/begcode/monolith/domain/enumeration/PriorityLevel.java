package com.begcode.monolith.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The PriorityLevel enumeration.
 */
public enum PriorityLevel {
    HIGH("HIGH", "HIGH"),
    MEDIUM("MEDIUM", "MEDIUM"),
    LOW("LOW", "LOW");

    @JsonValue
    private String value;

    private String desc;

    PriorityLevel(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static PriorityLevel getByValue(String value) {
        for (PriorityLevel enumPriorityLevel : PriorityLevel.values()) {
            if (enumPriorityLevel.getValue().equals(value)) {
                return enumPriorityLevel;
            }
        }
        return null;
    }

    public static PriorityLevel getByDesc(String desc) {
        for (PriorityLevel enumPriorityLevel : PriorityLevel.values()) {
            if (enumPriorityLevel.getDesc().equals(desc)) {
                return enumPriorityLevel;
            }
        }
        return null;
    }
}
