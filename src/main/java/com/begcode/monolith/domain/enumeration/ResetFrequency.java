package com.begcode.monolith.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 重置频率
 */
public enum ResetFrequency {
    NONE("不重置", "不重置"),
    DAILY("每天重置", "每天重置"),
    WEEKLY("每周重置", "每周重置"),
    MONTHLY("每月重置", "每月重置"),
    YEARLY("每年重置", "每年重置"),
    CUSTOM("自定义重置", "自定义重置");

    @JsonValue
    private final String value;

    private final String desc;

    ResetFrequency(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static ResetFrequency getByValue(String value) {
        for (ResetFrequency enumResetFrequency : ResetFrequency.values()) {
            if (enumResetFrequency.getValue().equals(value)) {
                return enumResetFrequency;
            }
        }
        return null;
    }

    public static ResetFrequency getByDesc(String desc) {
        for (ResetFrequency enumResetFrequency : ResetFrequency.values()) {
            if (enumResetFrequency.getDesc().equals(desc)) {
                return enumResetFrequency;
            }
        }
        return null;
    }
}
