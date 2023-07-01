package com.begcode.monolith.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.begcode.monolith.domain.ResourceCategory} entity. This class is used
 * in {@link com.begcode.monolith.web.rest.ResourceCategoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /resource-categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResourceCategoryCriteria implements Serializable, Criteria {

    private String jhiCommonSearchKeywords;

    private Boolean useOr = false;

    private ResourceCategoryCriteria and;

    private ResourceCategoryCriteria or;

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter code;

    private IntegerFilter orderNumber;

    private LongFilter filesId;

    private StringFilter filesUrl;

    private LongFilter childrenId;

    private StringFilter childrenTitle;

    private LongFilter imagesId;

    private StringFilter imagesUrl;

    private LongFilter parentId;

    private StringFilter parentTitle;

    private Boolean distinct;

    public ResourceCategoryCriteria() {}

    public ResourceCategoryCriteria(ResourceCategoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.orderNumber = other.orderNumber == null ? null : other.orderNumber.copy();
        this.filesId = other.filesId == null ? null : other.filesId.copy();
        this.filesUrl = other.filesUrl == null ? null : other.filesUrl.copy();
        this.childrenId = other.childrenId == null ? null : other.childrenId.copy();
        this.childrenTitle = other.childrenTitle == null ? null : other.childrenTitle.copy();
        this.imagesId = other.imagesId == null ? null : other.imagesId.copy();
        this.imagesUrl = other.imagesUrl == null ? null : other.imagesUrl.copy();
        this.parentId = other.parentId == null ? null : other.parentId.copy();
        this.parentTitle = other.parentTitle == null ? null : other.parentTitle.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ResourceCategoryCriteria copy() {
        return new ResourceCategoryCriteria(this);
    }

    public void setAnd(ResourceCategoryCriteria and) {
        this.and = and;
    }

    public ResourceCategoryCriteria getAnd() {
        return and;
    }

    public ResourceCategoryCriteria and() {
        if (and == null) {
            and = new ResourceCategoryCriteria();
        }
        return and;
    }

    public void setOr(ResourceCategoryCriteria or) {
        this.or = or;
    }

    public ResourceCategoryCriteria getOr() {
        return or;
    }

    public ResourceCategoryCriteria or() {
        if (or == null) {
            or = new ResourceCategoryCriteria();
        }
        return or;
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getCode() {
        return code;
    }

    public StringFilter code() {
        if (code == null) {
            code = new StringFilter();
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public IntegerFilter getOrderNumber() {
        return orderNumber;
    }

    public IntegerFilter orderNumber() {
        if (orderNumber == null) {
            orderNumber = new IntegerFilter();
        }
        return orderNumber;
    }

    public void setOrderNumber(IntegerFilter orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LongFilter getFilesId() {
        return filesId;
    }

    public LongFilter filesId() {
        if (filesId == null) {
            filesId = new LongFilter();
        }
        return filesId;
    }

    public void setFilesId(LongFilter filesId) {
        this.filesId = filesId;
    }

    public StringFilter getFilesUrl() {
        return filesUrl;
    }

    public StringFilter filesUrl() {
        if (filesUrl == null) {
            filesUrl = new StringFilter();
        }
        return filesUrl;
    }

    public void setFilesUrl(StringFilter filesUrl) {
        this.filesUrl = filesUrl;
    }

    public LongFilter getChildrenId() {
        return childrenId;
    }

    public LongFilter childrenId() {
        if (childrenId == null) {
            childrenId = new LongFilter();
        }
        return childrenId;
    }

    public void setChildrenId(LongFilter childrenId) {
        this.childrenId = childrenId;
    }

    public StringFilter getChildrenTitle() {
        return childrenTitle;
    }

    public StringFilter childrenTitle() {
        if (childrenTitle == null) {
            childrenTitle = new StringFilter();
        }
        return childrenTitle;
    }

    public void setChildrenTitle(StringFilter childrenTitle) {
        this.childrenTitle = childrenTitle;
    }

    public LongFilter getImagesId() {
        return imagesId;
    }

    public LongFilter imagesId() {
        if (imagesId == null) {
            imagesId = new LongFilter();
        }
        return imagesId;
    }

    public void setImagesId(LongFilter imagesId) {
        this.imagesId = imagesId;
    }

    public StringFilter getImagesUrl() {
        return imagesUrl;
    }

    public StringFilter imagesUrl() {
        if (imagesUrl == null) {
            imagesUrl = new StringFilter();
        }
        return imagesUrl;
    }

    public void setImagesUrl(StringFilter imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    public LongFilter getParentId() {
        return parentId;
    }

    public LongFilter parentId() {
        if (parentId == null) {
            parentId = new LongFilter();
        }
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
    }

    public StringFilter getParentTitle() {
        return parentTitle;
    }

    public StringFilter parentTitle() {
        if (parentTitle == null) {
            parentTitle = new StringFilter();
        }
        return parentTitle;
    }

    public void setParentTitle(StringFilter parentTitle) {
        this.parentTitle = parentTitle;
    }

    public String getJhiCommonSearchKeywords() {
        return jhiCommonSearchKeywords;
    }

    public void setJhiCommonSearchKeywords(String jhiCommonSearchKeywords) {
        this.jhiCommonSearchKeywords = jhiCommonSearchKeywords;
    }

    public Boolean getUseOr() {
        return useOr;
    }

    public void setUseOr(Boolean useOr) {
        this.useOr = useOr;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ResourceCategoryCriteria that = (ResourceCategoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(code, that.code) &&
            Objects.equals(orderNumber, that.orderNumber) &&
            Objects.equals(filesId, that.filesId) &&
            Objects.equals(filesUrl, that.filesUrl) &&
            Objects.equals(childrenId, that.childrenId) &&
            Objects.equals(childrenTitle, that.childrenTitle) &&
            Objects.equals(imagesId, that.imagesId) &&
            Objects.equals(imagesUrl, that.imagesUrl) &&
            Objects.equals(parentId, that.parentId) &&
            Objects.equals(parentTitle, that.parentTitle) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            title,
            code,
            orderNumber,
            filesId,
            filesUrl,
            childrenId,
            childrenTitle,
            imagesId,
            imagesUrl,
            parentId,
            parentTitle,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResourceCategoryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (orderNumber != null ? "orderNumber=" + orderNumber + ", " : "") +
            (filesId != null ? "filesId=" + filesId + ", " : "") +
            (filesUrl != null ? "filesUrl=" + filesUrl + ", " : "") +
            (childrenId != null ? "childrenId=" + childrenId + ", " : "") +
            (childrenTitle != null ? "childrenTitle=" + childrenTitle + ", " : "") +
            (imagesId != null ? "imagesId=" + imagesId + ", " : "") +
            (imagesUrl != null ? "imagesUrl=" + imagesUrl + ", " : "") +
            (parentId != null ? "parentId=" + parentId + ", " : "") +
            (parentTitle != null ? "parentTitle=" + parentTitle + ", " : "") +
            (jhiCommonSearchKeywords != null ? "jhiCommonSearchKeywords=" + jhiCommonSearchKeywords + ", " : "") +
            "useOr=" + useOr +
            (and != null ? "and=" + and + ", " : "") +
            (or != null ? "or=" + or + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
