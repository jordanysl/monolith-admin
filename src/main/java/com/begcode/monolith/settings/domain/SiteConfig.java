package com.begcode.monolith.settings.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.begcode.monolith.domain.AbstractAuditingEntity;
import com.diboot.core.binding.annotation.BindEntity;
import com.diboot.core.binding.annotation.BindEntityList;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

/**
 * 网站配置
 */
@TableName(value = "site_config")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SiteConfig extends AbstractAuditingEntity<Long, SiteConfig> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @TableField(value = "id")
    private Long id;

    /**
     * 分类名称
     */
    @TableField(value = "category_name")
    private String categoryName;

    /**
     * 分类Key
     */
    @TableField(value = "category_key")
    private String categoryKey;

    /**
     * 是否禁用
     */
    @TableField(value = "disabled")
    private Boolean disabled;

    /**
     * 排序
     */
    @TableField(value = "sort_value")
    private Integer sortValue;

    /**
     * 是否内置
     */
    @TableField(value = "built_in")
    private Boolean builtIn;

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

    /**
     * 配置项列表
     */
    @TableField(exist = false)
    @BindEntityList(
        entity = CommonFieldData.class,
        condition = "id=common_field_data.owner_entity_id AND common_field_data.owner_entity_name = 'SiteConfig' "
    )
    private List<CommonFieldData> items = new ArrayList<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public SiteConfig id(Long id) {
        this.id = id;
        return this;
    }

    public SiteConfig categoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public SiteConfig categoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
        return this;
    }

    public SiteConfig disabled(Boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    public SiteConfig sortValue(Integer sortValue) {
        this.sortValue = sortValue;
        return this;
    }

    public SiteConfig builtIn(Boolean builtIn) {
        this.builtIn = builtIn;
        return this;
    }

    public SiteConfig delFlag(Boolean delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public SiteConfig deletedTime(ZonedDateTime deletedTime) {
        this.deletedTime = deletedTime;
        return this;
    }

    public SiteConfig items(List<CommonFieldData> commonFieldData) {
        this.items = commonFieldData;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SiteConfig)) {
            return false;
        }
        return id != null && id.equals(((SiteConfig) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
