package com.begcode.monolith.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 日志类型（1登录日志，2操作日志）
 */
public enum LogType {
    /**
     * 登录日志
     */
    LOGIN("LOGIN", "LOGIN"),
    /**
     * 操作日志
     */
    OPERATE("OPERATE", "OPERATE");

    @JsonValue
    private String value;

    private String desc;

    LogType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static LogType getByValue(String value) {
        for (LogType enumLogType : LogType.values()) {
            if (enumLogType.getValue().equals(value)) {
                return enumLogType;
            }
        }
        return null;
    }

    public static LogType getByDesc(String desc) {
        for (LogType enumLogType : LogType.values()) {
            if (enumLogType.getDesc().equals(desc)) {
                return enumLogType;
            }
        }
        return null;
    }
}
