package com.begcode.monolith.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The OperateType enumeration.
 */
public enum OperateType {
    /**
     * 列表
     */
    LIST("LIST", "LIST"),
    /**
     * 新增
     */
    ADD("ADD", "ADD"),
    /**
     * 编辑
     */
    EDIT("EDIT", "EDIT"),
    /**
     * 删除
     */
    DELETE("DELETE", "DELETE"),
    /**
     * 导入
     */
    IMPORT("IMPORT", "IMPORT"),
    /**
     * 导出
     */
    EXPORT("EXPORT", "EXPORT"),
    /**
     * 其他
     */
    OTHER("OTHER", "OTHER"),
    /**
     * 登录
     */
    LOGIN("LOGIN", "LOGIN"),
    /**
     * 统计
     */
    STATS("STATS", "STATS"),
    /**
     * 查看
     */
    VIEW("VIEW", "VIEW"),
    /**
     * 审核
     */
    AUDIT("AUDIT", "AUDIT"),
    /**
     * 工作流
     */
    WORK_FLOW("WORK_FLOW", "WORK_FLOW");

    @JsonValue
    private String value;

    private String desc;

    OperateType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static OperateType getByValue(String value) {
        for (OperateType enumOperateType : OperateType.values()) {
            if (enumOperateType.getValue().equals(value)) {
                return enumOperateType;
            }
        }
        return null;
    }

    public static OperateType getByDesc(String desc) {
        for (OperateType enumOperateType : OperateType.values()) {
            if (enumOperateType.getDesc().equals(desc)) {
                return enumOperateType;
            }
        }
        return null;
    }
}
