package com.begcode.monolith.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The AnnoCategory enumeration.
 */
public enum AnnoCategory {
    SYSTEM_INFO("SYSTEM_INFO", "SYSTEM_INFO"),
    NOTICE("NOTICE", "NOTICE"),
    TODO("TODO", "TODO");

    @JsonValue
    private String value;

    private String desc;

    AnnoCategory(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static AnnoCategory getByValue(String value) {
        for (AnnoCategory enumAnnoCategory : AnnoCategory.values()) {
            if (enumAnnoCategory.getValue().equals(value)) {
                return enumAnnoCategory;
            }
        }
        return null;
    }

    public static AnnoCategory getByDesc(String desc) {
        for (AnnoCategory enumAnnoCategory : AnnoCategory.values()) {
            if (enumAnnoCategory.getDesc().equals(desc)) {
                return enumAnnoCategory;
            }
        }
        return null;
    }
}
