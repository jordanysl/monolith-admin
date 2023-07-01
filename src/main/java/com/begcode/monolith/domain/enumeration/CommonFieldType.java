package com.begcode.monolith.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 字段类型\n
 */
public enum CommonFieldType {
    /**
     * 整数
     */
    INTEGER("INTEGER", "INTEGER"),
    /**
     * 长整数
     */
    LONG("LONG", "LONG"),
    /**
     * 布尔
     */
    BOOLEAN("BOOLEAN", "BOOLEAN"),
    /**
     * 字符串
     */
    STRING("STRING", "STRING"),
    /**
     * 单精度
     */
    FLOAT("FLOAT", "FLOAT"),
    /**
     * 双精度
     */
    DOUBLE("DOUBLE", "DOUBLE"),
    /**
     * 日期时间
     */
    ZONED_DATE_TIME("ZONED_DATE_TIME", "ZONED_DATE_TIME"),
    /**
     * 本地日期
     */
    LOCATE_DATE("LOCATE_DATE", "LOCATE_DATE"),
    /**
     * 大数字
     */
    BIG_DECIMAL("BIG_DECIMAL", "BIG_DECIMAL"),
    /**
     * 大文本
     */
    TEXTBLOB("TEXTBLOB", "TEXTBLOB"),
    /**
     * 大图片
     */
    IMAGEBLOB("IMAGEBLOB", "IMAGEBLOB"),
    /**
     * 数组
     */
    ARRAY("ARRAY", "ARRAY"),
    /**
     * 枚举
     */
    ENUM("ENUM", "ENUM"),
    /**
     * 上传图片
     */
    UPLOAD_IMAGE("UPLOAD_IMAGE", "UPLOAD_IMAGE"),
    /**
     * 上传文件
     */
    UPLOAD_FILE("UPLOAD_FILE", "UPLOAD_FILE"),
    /**
     * 实体
     */
    ENTITY("ENTITY", "ENTITY"),
    /**
     * 单选
     */
    RADIO("RADIO", "RADIO"),
    /**
     * 多选
     */
    MULTI_SELECT("MULTI_SELECT", "MULTI_SELECT"),
    /**
     * 数据字典
     */
    DATA_DICTIONARY("DATA_DICTIONARY", "DATA_DICTIONARY"),
    /**
     * UUID
     */
    UUID("UUID", "UUID"),
    /**
     * 时间戳
     */
    INSTANT("INSTANT", "INSTANT");

    @JsonValue
    private String value;

    private String desc;

    CommonFieldType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static CommonFieldType getByValue(String value) {
        for (CommonFieldType enumCommonFieldType : CommonFieldType.values()) {
            if (enumCommonFieldType.getValue().equals(value)) {
                return enumCommonFieldType;
            }
        }
        return null;
    }

    public static CommonFieldType getByDesc(String desc) {
        for (CommonFieldType enumCommonFieldType : CommonFieldType.values()) {
            if (enumCommonFieldType.getDesc().equals(desc)) {
                return enumCommonFieldType;
            }
        }
        return null;
    }
}
