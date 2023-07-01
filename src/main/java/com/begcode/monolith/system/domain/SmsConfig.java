package com.begcode.monolith.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.begcode.monolith.domain.enumeration.SmsProvider;
import com.diboot.core.binding.annotation.BindEntity;
import com.diboot.core.binding.annotation.BindEntityList;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.*;

/**
 * 短信配置
 */
@TableName(value = "sms_config")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SmsConfig implements Serializable {

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
     * 资源编号
     */
    @TableField(value = "sms_code")
    private String smsCode;

    /**
     * 模板ID
     */
    @TableField(value = "template_id")
    private String templateId;

    /**
     * accessKey
     */
    @TableField(value = "access_key")
    private String accessKey;

    /**
     * secretKey
     */
    @TableField(value = "secret_key")
    private String secretKey;

    /**
     * regionId
     */
    @TableField(value = "region_id")
    private String regionId;

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

    public SmsConfig id(Long id) {
        this.id = id;
        return this;
    }

    public SmsConfig provider(SmsProvider provider) {
        this.provider = provider;
        return this;
    }

    public SmsConfig smsCode(String smsCode) {
        this.smsCode = smsCode;
        return this;
    }

    public SmsConfig templateId(String templateId) {
        this.templateId = templateId;
        return this;
    }

    public SmsConfig accessKey(String accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    public SmsConfig secretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    public SmsConfig regionId(String regionId) {
        this.regionId = regionId;
        return this;
    }

    public SmsConfig signName(String signName) {
        this.signName = signName;
        return this;
    }

    public SmsConfig remark(String remark) {
        this.remark = remark;
        return this;
    }

    public SmsConfig enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public SmsConfig delFlag(Boolean delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public SmsConfig deletedTime(ZonedDateTime deletedTime) {
        this.deletedTime = deletedTime;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SmsConfig)) {
            return false;
        }
        return id != null && id.equals(((SmsConfig) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
