package com.begcode.monolith.system.service.dto;

import com.begcode.monolith.domain.AbstractAuditingEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.ZonedDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**

 * {@link com.begcode.monolith.system.domain.DataPermissionRule}的DTO。
 */
@Schema(description = "数据权限规则")
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DataPermissionRuleDTO extends AbstractAuditingEntity<Long, DataPermissionRuleDTO> {

    private Long id;

    /**
     * 菜单ID
     */
    @Schema(description = "菜单ID")
    private String permissionId;

    /**
     * 规则名称
     */
    @Schema(description = "规则名称")
    private String name;

    /**
     * 字段
     */
    @Schema(description = "字段")
    private String column;

    /**
     * 条件
     */
    @Schema(description = "条件")
    private String conditions;

    /**
     * 规则值
     */
    @Schema(description = "规则值")
    private String value;

    /**
     * 禁用状态
     */
    @Schema(description = "禁用状态")
    private Boolean disabled;

    /**
     * 创建者Id
     */
    @Schema(description = "创建者Id")
    private Long createdBy;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private ZonedDateTime createdDate;

    /**
     * 修改者Id
     */
    @Schema(description = "修改者Id")
    private Long lastModifiedBy;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private ZonedDateTime lastModifiedDate;

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

    // jhipster-needle-dto-add-field - JHipster will add fields here, do not remove

    public DataPermissionRuleDTO id(Long id) {
        this.id = id;
        return this;
    }

    public DataPermissionRuleDTO permissionId(String permissionId) {
        this.permissionId = permissionId;
        return this;
    }

    public DataPermissionRuleDTO name(String name) {
        this.name = name;
        return this;
    }

    public DataPermissionRuleDTO column(String column) {
        this.column = column;
        return this;
    }

    public DataPermissionRuleDTO conditions(String conditions) {
        this.conditions = conditions;
        return this;
    }

    public DataPermissionRuleDTO value(String value) {
        this.value = value;
        return this;
    }

    public DataPermissionRuleDTO disabled(Boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    public DataPermissionRuleDTO createdBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public DataPermissionRuleDTO createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public DataPermissionRuleDTO lastModifiedBy(Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public DataPermissionRuleDTO lastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public DataPermissionRuleDTO delFlag(Boolean delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public DataPermissionRuleDTO deletedTime(ZonedDateTime deletedTime) {
        this.deletedTime = deletedTime;
        return this;
    }
    // jhipster-needle-dto-add-getters-setters - JHipster will add getters and setters here, do not remove

}
