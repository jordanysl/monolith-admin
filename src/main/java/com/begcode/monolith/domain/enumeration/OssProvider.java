package com.begcode.monolith.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * OSS提供商
 */
public enum OssProvider {
    /**
     * 本地
     */
    LOCAL("LOCAL", "LOCAL"),
    /**
     * MINIO
     */
    MINIO("MINIO", "MINIO"),
    /**
     * 七牛云
     */
    QINIU("QINIU", "QINIU"),
    /**
     * 阿里云
     */
    ALI("ALI", "ALI"),
    /**
     * 腾讯云
     */
    TENCENT("TENCENT", "TENCENT");

    @JsonValue
    private String value;

    private String desc;

    OssProvider(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static OssProvider getByValue(String value) {
        for (OssProvider enumOssProvider : OssProvider.values()) {
            if (enumOssProvider.getValue().equals(value)) {
                return enumOssProvider;
            }
        }
        return null;
    }

    public static OssProvider getByDesc(String desc) {
        for (OssProvider enumOssProvider : OssProvider.values()) {
            if (enumOssProvider.getDesc().equals(desc)) {
                return enumOssProvider;
            }
        }
        return null;
    }
}
