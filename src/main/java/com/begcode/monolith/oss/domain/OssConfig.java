package com.begcode.monolith.oss.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.begcode.monolith.domain.enumeration.OssProvider;
import com.diboot.core.binding.annotation.BindEntity;
import com.diboot.core.binding.annotation.BindEntityList;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.*;

/**
 * 对象存储配置
 */
@TableName(value = "oss_config")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OssConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @TableField(value = "id")
    private Long id;

    /**
     * 提供商
     */
    @TableField(value = "provider")
    private OssProvider provider;

    /**
     * 平台
     */
    @TableField(value = "platform")
    private String platform;

    /**
     * 启用
     */
    @TableField(value = "enabled")
    private Boolean enabled;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 配置数据
     */
    @TableField(value = "config_data")
    private String configData;

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

    public OssConfig id(Long id) {
        this.id = id;
        return this;
    }

    public OssConfig provider(OssProvider provider) {
        this.provider = provider;
        return this;
    }

    public OssConfig platform(String platform) {
        this.platform = platform;
        return this;
    }

    public OssConfig enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public OssConfig remark(String remark) {
        this.remark = remark;
        return this;
    }

    public OssConfig configData(String configData) {
        this.configData = configData;
        return this;
    }

    public OssConfig delFlag(Boolean delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public OssConfig deletedTime(ZonedDateTime deletedTime) {
        this.deletedTime = deletedTime;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OssConfig)) {
            return false;
        }
        return id != null && id.equals(((OssConfig) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
