package com.begcode.monolith.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 打开方式(组件：component 路由：url 弹窗显示:MODAL_DETAIL)
 */
public enum AnnoOpenType {
    URL("URL", "URL"),
    COMPONENT("COMPONENT", "COMPONENT"),
    MODAL_DETAIL("MODAL_DETAIL", "MODAL_DETAIL");

    @JsonValue
    private String value;

    private String desc;

    AnnoOpenType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static AnnoOpenType getByValue(String value) {
        for (AnnoOpenType enumAnnoOpenType : AnnoOpenType.values()) {
            if (enumAnnoOpenType.getValue().equals(value)) {
                return enumAnnoOpenType;
            }
        }
        return null;
    }

    public static AnnoOpenType getByDesc(String desc) {
        for (AnnoOpenType enumAnnoOpenType : AnnoOpenType.values()) {
            if (enumAnnoOpenType.getDesc().equals(desc)) {
                return enumAnnoOpenType;
            }
        }
        return null;
    }
}
