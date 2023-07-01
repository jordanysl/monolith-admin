package com.begcode.monolith.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**

 * {@link com.begcode.monolith.domain.ResourceCategory}的DTO。
 */
@Schema(description = "资源分类")
@Data
@ToString
@EqualsAndHashCode
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResourceCategoryDTO implements Serializable {

    private Long id;

    /**
     * 标题
     */
    @Size(max = 40)
    @Schema(description = "标题")
    private String title;

    /**
     * 代码
     */
    @Size(max = 20)
    @Schema(description = "代码")
    private String code;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer orderNumber;

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

    private List<ResourceCategoryDTO> children = new ArrayList<>();

    private ResourceCategoryDTO parent;
    private Long parentId;

    // jhipster-needle-dto-add-field - JHipster will add fields here, do not remove

    public ResourceCategoryDTO id(Long id) {
        this.id = id;
        return this;
    }

    public ResourceCategoryDTO title(String title) {
        this.title = title;
        return this;
    }

    public ResourceCategoryDTO code(String code) {
        this.code = code;
        return this;
    }

    public ResourceCategoryDTO orderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public ResourceCategoryDTO delFlag(Boolean delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public ResourceCategoryDTO deletedTime(ZonedDateTime deletedTime) {
        this.deletedTime = deletedTime;
        return this;
    }

    public ResourceCategoryDTO children(List<ResourceCategoryDTO> children) {
        this.children = children;
        return this;
    }

    public ResourceCategoryDTO parent(ResourceCategoryDTO parent) {
        this.parent = parent;
        return this;
    }
    // jhipster-needle-dto-add-getters-setters - JHipster will add getters and setters here, do not remove

}
