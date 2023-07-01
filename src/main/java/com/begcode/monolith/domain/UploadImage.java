package com.begcode.monolith.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.begcode.monolith.domain.AbstractAuditingEntity;
import com.diboot.core.binding.annotation.BindEntity;
import com.diboot.core.binding.annotation.BindEntityList;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.*;

/**
 * 上传图片
 */
@TableName(value = "upload_image")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UploadImage extends AbstractAuditingEntity<Long, UploadImage> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @TableField(value = "id")
    private Long id;

    /**
     * 完整文件名\n不含路径
     */
    @TableField(value = "full_name")
    private String fullName;

    /**
     * 文件名\n不含扩展名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 扩展名
     */
    @TableField(value = "ext")
    private String ext;

    /**
     * 文件类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * Web Url地址
     */
    @TableField(value = "url")
    private String url;

    /**
     * 本地路径
     */
    @TableField(value = "path")
    private String path;

    /**
     * 本地存储目录
     */
    @TableField(value = "folder")
    private String folder;

    /**
     * 使用实体名称
     */
    @TableField(value = "owner_entity_name")
    private String ownerEntityName;

    /**
     * 使用实体ID
     */
    @TableField(value = "owner_entity_id")
    private String ownerEntityId;

    /**
     * 业务标题
     */
    @TableField(value = "business_title")
    private String businessTitle;

    /**
     * 业务自定义描述内容
     */
    @TableField(value = "business_desc")
    private String businessDesc;

    /**
     * 业务状态
     */
    @TableField(value = "business_status")
    private String businessStatus;

    /**
     * 创建时间
     */
    @TableField(value = "create_at")
    private ZonedDateTime createAt;

    /**
     * 文件大小
     */
    @TableField(value = "file_size")
    private Long fileSize;

    /**
     * 小图Url
     */
    @TableField(value = "smart_url")
    private String smartUrl;

    /**
     * 中等图Url
     */
    @TableField(value = "medium_url")
    private String mediumUrl;

    /**
     * 文件被引用次数
     */
    @TableField(value = "reference_count")
    private Long referenceCount;

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
     * 所属分类
     */
    @TableField(exist = false)
    @BindEntity(entity = ResourceCategory.class, condition = "this.category_id=id")
    @JsonIgnoreProperties(value = { "files", "children", "images", "parent" }, allowSetters = true)
    private ResourceCategory category;

    @TableField(value = "category_id")
    private Long categoryId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UploadImage id(Long id) {
        this.id = id;
        return this;
    }

    public UploadImage fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public UploadImage name(String name) {
        this.name = name;
        return this;
    }

    public UploadImage ext(String ext) {
        this.ext = ext;
        return this;
    }

    public UploadImage type(String type) {
        this.type = type;
        return this;
    }

    public UploadImage url(String url) {
        this.url = url;
        return this;
    }

    public UploadImage path(String path) {
        this.path = path;
        return this;
    }

    public UploadImage folder(String folder) {
        this.folder = folder;
        return this;
    }

    public UploadImage ownerEntityName(String ownerEntityName) {
        this.ownerEntityName = ownerEntityName;
        return this;
    }

    public UploadImage ownerEntityId(String ownerEntityId) {
        this.ownerEntityId = ownerEntityId;
        return this;
    }

    public UploadImage businessTitle(String businessTitle) {
        this.businessTitle = businessTitle;
        return this;
    }

    public UploadImage businessDesc(String businessDesc) {
        this.businessDesc = businessDesc;
        return this;
    }

    public UploadImage businessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
        return this;
    }

    public UploadImage createAt(ZonedDateTime createAt) {
        this.createAt = createAt;
        return this;
    }

    public UploadImage fileSize(Long fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public UploadImage smartUrl(String smartUrl) {
        this.smartUrl = smartUrl;
        return this;
    }

    public UploadImage mediumUrl(String mediumUrl) {
        this.mediumUrl = mediumUrl;
        return this;
    }

    public UploadImage referenceCount(Long referenceCount) {
        this.referenceCount = referenceCount;
        return this;
    }

    public UploadImage delFlag(Boolean delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public UploadImage deletedTime(ZonedDateTime deletedTime) {
        this.deletedTime = deletedTime;
        return this;
    }

    public UploadImage category(ResourceCategory resourceCategory) {
        this.category = resourceCategory;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UploadImage)) {
            return false;
        }
        return id != null && id.equals(((UploadImage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
