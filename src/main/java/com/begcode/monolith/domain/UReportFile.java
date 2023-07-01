package com.begcode.monolith.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.diboot.core.binding.annotation.BindEntity;
import com.diboot.core.binding.annotation.BindEntityList;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.*;

/**
 * 报表存储
 */
@TableName(value = "u_report_file")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UReportFile implements Serializable {

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
     * 内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 创建时间
     */
    @TableField(value = "create_at")
    private ZonedDateTime createAt;

    /**
     * 更新时间
     */
    @TableField(value = "update_at")
    private ZonedDateTime updateAt;

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

    public UReportFile id(Long id) {
        this.id = id;
        return this;
    }

    public UReportFile name(String name) {
        this.name = name;
        return this;
    }

    public UReportFile content(String content) {
        this.content = content;
        return this;
    }

    public UReportFile createAt(ZonedDateTime createAt) {
        this.createAt = createAt;
        return this;
    }

    public UReportFile updateAt(ZonedDateTime updateAt) {
        this.updateAt = updateAt;
        return this;
    }

    public UReportFile delFlag(Boolean delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public UReportFile deletedTime(ZonedDateTime deletedTime) {
        this.deletedTime = deletedTime;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UReportFile)) {
            return false;
        }
        return id != null && id.equals(((UReportFile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
