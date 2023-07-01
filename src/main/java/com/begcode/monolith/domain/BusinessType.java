package com.begcode.monolith.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.diboot.core.binding.annotation.BindEntity;
import com.diboot.core.binding.annotation.BindEntityList;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.*;

/**
 * 业务类型
 */
@TableName(value = "business_type")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BusinessType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @TableField(value = "id")
    private Long id;

    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 代码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 软删除标志
     */
    @TableField(value = "del_flag")
    @TableLogic
    private Boolean delFlag = false;

    /**
     * 软删除时间
     */
    @TableField(value = "deleted_time")
    private ZonedDateTime deletedTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public BusinessType id(Long id) {
        this.id = id;
        return this;
    }

    public BusinessType name(String name) {
        this.name = name;
        return this;
    }

    public BusinessType code(String code) {
        this.code = code;
        return this;
    }

    public BusinessType description(String description) {
        this.description = description;
        return this;
    }

    public BusinessType icon(String icon) {
        this.icon = icon;
        return this;
    }

    public BusinessType delFlag(Boolean delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public BusinessType deletedTime(ZonedDateTime deletedTime) {
        this.deletedTime = deletedTime;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessType)) {
            return false;
        }
        return id != null && id.equals(((BusinessType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
