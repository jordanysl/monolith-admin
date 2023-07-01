package com.begcode.monolith.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The RegionCodeLevel enumeration.
 */
public enum RegionCodeLevel {
    PROVINCE("PROVINCE", "PROVINCE"),
    CITY("CITY", "CITY"),
    COUNTY("COUNTY", "COUNTY"),
    TOWN("TOWN", "TOWN"),
    VILLAGE("VILLAGE", "VILLAGE");

    @JsonValue
    private String value;

    private String desc;

    RegionCodeLevel(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static RegionCodeLevel getByValue(String value) {
        for (RegionCodeLevel enumRegionCodeLevel : RegionCodeLevel.values()) {
            if (enumRegionCodeLevel.getValue().equals(value)) {
                return enumRegionCodeLevel;
            }
        }
        return null;
    }

    public static RegionCodeLevel getByDesc(String desc) {
        for (RegionCodeLevel enumRegionCodeLevel : RegionCodeLevel.values()) {
            if (enumRegionCodeLevel.getDesc().equals(desc)) {
                return enumRegionCodeLevel;
            }
        }
        return null;
    }
}
