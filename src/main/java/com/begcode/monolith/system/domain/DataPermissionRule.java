package com.begcode.monolith.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.begcode.monolith.domain.AbstractAuditingEntity;
import com.diboot.core.binding.annotation.BindEntity;
import com.diboot.core.binding.annotation.BindEntityList;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.*;

/**
 * 数据权限规则
 */
@TableName(value = "data_permission_rule")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DataPermissionRule extends AbstractAuditingEntity<Long, DataPermissionRule> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @TableField(value = "id")
    private Long id;

    /**
     * 菜单ID
     */
    @TableField(value = "permission_id")
    private String permissionId;

    /**
     * 规则名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 字段
     */
    @TableField(value = "`column`")
    private String column;

    /**
     * 条件
     */
    @TableField(value = "conditions")
    private String conditions;

    /**
     * 规则值
     */
    @TableField(value = "value")
    private String value;

    /**
     * 禁用状态
     */
    @TableField(value = "disabled")
    private Boolean disabled;

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

    public DataPermissionRule id(Long id) {
        this.id = id;
        return this;
    }

    public DataPermissionRule permissionId(String permissionId) {
        this.permissionId = permissionId;
        return this;
    }

    public DataPermissionRule name(String name) {
        this.name = name;
        return this;
    }

    public DataPermissionRule column(String column) {
        this.column = column;
        return this;
    }

    public DataPermissionRule conditions(String conditions) {
        this.conditions = conditions;
        return this;
    }

    public DataPermissionRule value(String value) {
        this.value = value;
        return this;
    }

    public DataPermissionRule disabled(Boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    public DataPermissionRule delFlag(Boolean delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public DataPermissionRule deletedTime(ZonedDateTime deletedTime) {
        this.deletedTime = deletedTime;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataPermissionRule)) {
            return false;
        }
        return id != null && id.equals(((DataPermissionRule) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
