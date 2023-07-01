package com.begcode.monolith.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Api权限状态
 */
public enum ApiPermissionState {
    /**
     * 可配置
     */
    CONFIGURABLE("CONFIGURABLE", "CONFIGURABLE"),
    /**
     * 允许所有
     */
    PERMIT_ALL("PERMIT_ALL", "PERMIT_ALL"),
    /**
     * 不可达
     */
    UNREACHABLE("UNREACHABLE", "UNREACHABLE"),
    /**
     * 认证
     */
    AUTHENTICATE("AUTHENTICATE", "AUTHENTICATE");

    @JsonValue
    private String value;

    private String desc;

    ApiPermissionState(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static ApiPermissionState getByValue(String value) {
        for (ApiPermissionState enumApiPermissionState : ApiPermissionState.values()) {
            if (enumApiPermissionState.getValue().equals(value)) {
                return enumApiPermissionState;
            }
        }
        return null;
    }

    public static ApiPermissionState getByDesc(String desc) {
        for (ApiPermissionState enumApiPermissionState : ApiPermissionState.values()) {
            if (enumApiPermissionState.getDesc().equals(desc)) {
                return enumApiPermissionState;
            }
        }
        return null;
    }
}
