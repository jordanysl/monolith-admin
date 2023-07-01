package com.begcode.monolith.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 任务状态
 */
public enum JobStatus {
    /**
     * 正常
     */
    NORMAL("NORMAL", "NORMAL"),
    /**
     * 暂停
     */
    PAUSED("PAUSED", "PAUSED"),
    /**
     * 完成
     */
    COMPLETE("COMPLETE", "COMPLETE"),
    /**
     * 错误
     */
    ERROR("ERROR", "ERROR"),
    /**
     * 阻塞
     */
    BLOCKED("BLOCKED", "BLOCKED");

    @JsonValue
    private String value;

    private String desc;

    JobStatus(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static JobStatus getByValue(String value) {
        for (JobStatus enumJobStatus : JobStatus.values()) {
            if (enumJobStatus.getValue().equals(value)) {
                return enumJobStatus;
            }
        }
        return null;
    }

    public static JobStatus getByDesc(String desc) {
        for (JobStatus enumJobStatus : JobStatus.values()) {
            if (enumJobStatus.getDesc().equals(desc)) {
                return enumJobStatus;
            }
        }
        return null;
    }
}
