package com.begcode.monolith.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.begcode.monolith.domain.enumeration.SmsProvider;
import com.diboot.core.binding.annotation.BindEntity;
import com.diboot.core.binding.annotation.BindEntityList;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.*;

/**
 * 短信服务配置
 */
@TableName(value = "sms_supplier")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SmsSupplier implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @TableField(value = "id")
    private Long id;

    /**
     * 提供商
     */
    @TableField(value = "provider")
    private SmsProvider provider;

    /**
     * 配置数据
     */
    @TableField(value = "config_data")
    private String configData;

    /**
     * 短信签名
     */
    @TableField(value = "sign_name")
    private String signName;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 启用
     */
    @TableField(value = "enabled")
    private Boolean enabled;

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

    public SmsSupplier id(Long id) {
        this.id = id;
        return this;
    }

    public SmsSupplier provider(SmsProvider provider) {
        this.provider = provider;
        return this;
    }

    public SmsSupplier configData(String configData) {
        this.configData = configData;
        return this;
    }

    public SmsSupplier signName(String signName) {
        this.signName = signName;
        return this;
    }

    public SmsSupplier remark(String remark) {
        this.remark = remark;
        return this;
    }

    public SmsSupplier enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public SmsSupplier delFlag(Boolean delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public SmsSupplier deletedTime(ZonedDateTime deletedTime) {
        this.deletedTime = deletedTime;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SmsSupplier)) {
            return false;
        }
        return id != null && id.equals(((SmsSupplier) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
