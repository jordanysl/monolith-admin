package com.begcode.monolith.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 推送状态\nWAITING未推送 SUCCESS推送成功 FAILURE推送失败 NOT_TRY失败不再发送
 */
public enum SendStatus {
    /**
     * 未推送
     */
    WAITING("WAITING", "WAITING"),
    /**
     * 推送成功
     */
    SUCCESS("SUCCESS", "SUCCESS"),
    /**
     * 推送失败
     */
    FAILURE("FAILURE", "FAILURE"),
    /**
     * 失败不再发送
     */
    NOT_TRY("NOT_TRY", "NOT_TRY");

    @JsonValue
    private String value;

    private String desc;

    SendStatus(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static SendStatus getByValue(String value) {
        for (SendStatus enumSendStatus : SendStatus.values()) {
            if (enumSendStatus.getValue().equals(value)) {
                return enumSendStatus;
            }
        }
        return null;
    }

    public static SendStatus getByDesc(String desc) {
        for (SendStatus enumSendStatus : SendStatus.values()) {
            if (enumSendStatus.getDesc().equals(desc)) {
                return enumSendStatus;
            }
        }
        return null;
    }
}
