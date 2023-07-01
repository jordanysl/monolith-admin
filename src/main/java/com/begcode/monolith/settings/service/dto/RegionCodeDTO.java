package com.begcode.monolith.settings.service.dto;

import com.begcode.monolith.domain.enumeration.RegionCodeLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**

 * {@link com.begcode.monolith.settings.domain.RegionCode}的DTO。
 */
@Schema(description = "行政区划码")
@Data
@ToString
@EqualsAndHashCode
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RegionCodeDTO implements Serializable {

    private Long id;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 地区代码
     */
    @Schema(description = "地区代码")
    private String areaCode;

    /**
     * 城市代码
     */
    @Schema(description = "城市代码")
    private String cityCode;

    /**
     * 全名
     */
    @Schema(description = "全名")
    private String mergerName;

    /**
     * 短名称
     */
    @Schema(description = "短名称")
    private String shortName;

    /**
     * 邮政编码
     */
    @Schema(description = "邮政编码")
    private String zipCode;

    /**
     * 等级
     */
    @Schema(description = "等级")
    private RegionCodeLevel level;

    /**
     * 经度
     */
    @Schema(description = "经度")
    private Double lng;

    /**
     * 纬度
     */
    @Schema(description = "纬度")
    private Double lat;

    /**
     * 软删除标志
     */
    @Schema(description = "软删除标志")
    private Boolean delFlag;

    /**
     * 软删除时间
     */
    @Schema(description = "软删除时间")
    private ZonedDateTime deletedTime;

    private List<RegionCodeDTO> children = new ArrayList<>();

    private RegionCodeDTO parent;
    private Long parentId;

    // jhipster-needle-dto-add-field - JHipster will add fields here, do not remove

    public RegionCodeDTO id(Long id) {
        this.id = id;
        return this;
    }

    public RegionCodeDTO name(String name) {
        this.name = name;
        return this;
    }

    public RegionCodeDTO areaCode(String areaCode) {
        this.areaCode = areaCode;
        return this;
    }

    public RegionCodeDTO cityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }

    public RegionCodeDTO mergerName(String mergerName) {
        this.mergerName = mergerName;
        return this;
    }

    public RegionCodeDTO shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public RegionCodeDTO zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public RegionCodeDTO level(RegionCodeLevel level) {
        this.level = level;
        return this;
    }

    public RegionCodeDTO lng(Double lng) {
        this.lng = lng;
        return this;
    }

    public RegionCodeDTO lat(Double lat) {
        this.lat = lat;
        return this;
    }

    public RegionCodeDTO delFlag(Boolean delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public RegionCodeDTO deletedTime(ZonedDateTime deletedTime) {
        this.deletedTime = deletedTime;
        return this;
    }

    public RegionCodeDTO children(List<RegionCodeDTO> children) {
        this.children = children;
        return this;
    }

    public RegionCodeDTO parent(RegionCodeDTO parent) {
        this.parent = parent;
        return this;
    }
    // jhipster-needle-dto-add-getters-setters - JHipster will add getters and setters here, do not remove

}
