package com.begcode.monolith.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 链接目标
 */
public enum TargetType {
    /**
     * 新窗口
     */
    BLANK("BLANK", "BLANK"),
    /**
     * 当前窗口
     */
    SELF("SELF", "SELF"),
    /**
     * 父窗口
     */
    PARENT("PARENT", "PARENT"),
    /**
     * 顶层窗口
     */
    TOP("TOP", "TOP");

    @JsonValue
    private String value;

    private String desc;

    TargetType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static TargetType getByValue(String value) {
        for (TargetType enumTargetType : TargetType.values()) {
            if (enumTargetType.getValue().equals(value)) {
                return enumTargetType;
            }
        }
        return null;
    }

    public static TargetType getByDesc(String desc) {
        for (TargetType enumTargetType : TargetType.values()) {
            if (enumTargetType.getDesc().equals(desc)) {
                return enumTargetType;
            }
        }
        return null;
    }
}
