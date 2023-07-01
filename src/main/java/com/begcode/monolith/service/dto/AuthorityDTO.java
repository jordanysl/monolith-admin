package com.begcode.monolith.service.dto;

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

 * {@link com.begcode.monolith.domain.Authority}的DTO。
 */
@Schema(description = "角色")
@Data
@ToString
@EqualsAndHashCode
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AuthorityDTO implements Serializable {

    private Long id;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String name;

    /**
     * 角色代号
     */
    @Schema(description = "角色代号")
    private String code;

    /**
     * 信息
     */
    @Schema(description = "信息")
    private String info;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer order;

    /**
     * 展示
     */
    @Schema(description = "展示")
    private Boolean display;

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

    private List<AuthorityDTO> children = new ArrayList<>();

    private List<ViewPermissionDTO> viewPermissions = new ArrayList<>();

    private List<ApiPermissionDTO> apiPermissions = new ArrayList<>();

    private AuthorityDTO parent;
    private Long parentId;

    // jhipster-needle-dto-add-field - JHipster will add fields here, do not remove

    public AuthorityDTO id(Long id) {
        this.id = id;
        return this;
    }

    public AuthorityDTO name(String name) {
        this.name = name;
        return this;
    }

    public AuthorityDTO code(String code) {
        this.code = code;
        return this;
    }

    public AuthorityDTO info(String info) {
        this.info = info;
        return this;
    }

    public AuthorityDTO order(Integer order) {
        this.order = order;
        return this;
    }

    public AuthorityDTO display(Boolean display) {
        this.display = display;
        return this;
    }

    public AuthorityDTO delFlag(Boolean delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public AuthorityDTO deletedTime(ZonedDateTime deletedTime) {
        this.deletedTime = deletedTime;
        return this;
    }

    public AuthorityDTO children(List<AuthorityDTO> children) {
        this.children = children;
        return this;
    }

    public AuthorityDTO viewPermissions(List<ViewPermissionDTO> viewPermissions) {
        this.viewPermissions = viewPermissions;
        return this;
    }

    public AuthorityDTO apiPermissions(List<ApiPermissionDTO> apiPermissions) {
        this.apiPermissions = apiPermissions;
        return this;
    }

    public AuthorityDTO parent(AuthorityDTO parent) {
        this.parent = parent;
        return this;
    }
    // jhipster-needle-dto-add-getters-setters - JHipster will add getters and setters here, do not remove

}
