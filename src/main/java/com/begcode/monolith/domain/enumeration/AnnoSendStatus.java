package com.begcode.monolith.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The AnnoSendStatus enumeration.
 */
public enum AnnoSendStatus {
    NOT_RELEASE("NOT_RELEASE", "NOT_RELEASE"),
    RELEASED("RELEASED", "RELEASED"),
    CANCELED("CANCELED", "CANCELED");

    @JsonValue
    private String value;

    private String desc;

    AnnoSendStatus(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static AnnoSendStatus getByValue(String value) {
        for (AnnoSendStatus enumAnnoSendStatus : AnnoSendStatus.values()) {
            if (enumAnnoSendStatus.getValue().equals(value)) {
                return enumAnnoSendStatus;
            }
        }
        return null;
    }

    public static AnnoSendStatus getByDesc(String desc) {
        for (AnnoSendStatus enumAnnoSendStatus : AnnoSendStatus.values()) {
            if (enumAnnoSendStatus.getDesc().equals(desc)) {
                return enumAnnoSendStatus;
            }
        }
        return null;
    }
}
