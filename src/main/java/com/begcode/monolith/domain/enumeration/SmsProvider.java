package com.begcode.monolith.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 短信厂商
 */
public enum SmsProvider {
    /**
     * 阿里云
     */
    ALIBABA("ALIBABA", "ALIBABA"),
    /**
     * 华为云
     */
    HUAWEI("HUAWEI", "HUAWEI"),
    /**
     * 云片
     */
    YUNPIAN("YUNPIAN", "YUNPIAN"),
    /**
     * 腾讯云
     */
    TENCENT("TENCENT", "TENCENT"),
    /**
     * 合一
     */
    UNI_SMS("UNI_SMS", "UNI_SMS"),
    /**
     * 京东云
     */
    JD_CLOUD("JD_CLOUD", "JD_CLOUD"),
    /**
     * 容联云
     */
    CLOOPEN("CLOOPEN", "CLOOPEN"),
    /**
     * 亿美软通
     */
    EMAY("EMAY", "EMAY");

    @JsonValue
    private String value;

    private String desc;

    SmsProvider(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static SmsProvider getByValue(String value) {
        for (SmsProvider enumSmsProvider : SmsProvider.values()) {
            if (enumSmsProvider.getValue().equals(value)) {
                return enumSmsProvider;
            }
        }
        return null;
    }

    public static SmsProvider getByDesc(String desc) {
        for (SmsProvider enumSmsProvider : SmsProvider.values()) {
            if (enumSmsProvider.getDesc().equals(desc)) {
                return enumSmsProvider;
            }
        }
        return null;
    }
}
