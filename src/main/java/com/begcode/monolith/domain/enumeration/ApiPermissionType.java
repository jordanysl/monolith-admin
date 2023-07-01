package com.begcode.monolith.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Api权限类型
 */
public enum ApiPermissionType {
    /**
     * 业务
     */
    BUSINESS("BUSINESS", "BUSINESS"),
    /**
     * API接口
     */
    API("API", "API"),
    /**
     * 实体
     */
    ENTITY("ENTITY", "ENTITY"),
    /**
     * 微服务
     */
    MICRO_SERVICE("MICRO_SERVICE", "MICRO_SERVICE");

    @JsonValue
    private String value;

    private String desc;

    ApiPermissionType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static ApiPermissionType getByValue(String value) {
        for (ApiPermissionType enumApiPermissionType : ApiPermissionType.values()) {
            if (enumApiPermissionType.getValue().equals(value)) {
                return enumApiPermissionType;
            }
        }
        return null;
    }

    public static ApiPermissionType getByDesc(String desc) {
        for (ApiPermissionType enumApiPermissionType : ApiPermissionType.values()) {
            if (enumApiPermissionType.getDesc().equals(desc)) {
                return enumApiPermissionType;
            }
        }
        return null;
    }
}
