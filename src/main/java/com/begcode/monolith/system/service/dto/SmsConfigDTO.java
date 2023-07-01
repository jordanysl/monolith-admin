package com.begcode.monolith.system.service.dto;

import com.begcode.monolith.domain.enumeration.SmsProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**

 * {@link com.begcode.monolith.system.domain.SmsConfig}的DTO。
 */
@Schema(description = "短信配置")
@Data
@ToString
@EqualsAndHashCode
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SmsConfigDTO implements Serializable {

    private Long id;

    /**
     * 提供商
     */
    @Schema(description = "提供商")
    private SmsProvider provider;

    /**
     * 资源编号
     */
    @Schema(description = "资源编号")
    private String smsCode;

    /**
     * 模板ID
     */
    @Schema(description = "模板ID")
    private String templateId;

    /**
     * accessKey
     */
    @Schema(description = "accessKey")
    private String accessKey;

    /**
     * secretKey
     */
    @Schema(description = "secretKey")
    private String secretKey;

    /**
     * regionId
     */
    @Schema(description = "regionId")
    private String regionId;

    /**
     * 短信签名
     */
    @Schema(description = "短信签名")
    private String signName;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 启用
     */
    @Schema(description = "启用")
    private Boolean enabled;

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

    public SmsConfigDTO id(Long id) {
        this.id = id;
        return this;
    }

    public SmsConfigDTO provider(SmsProvider provider) {
        this.provider = provider;
        return this;
    }

    public SmsConfigDTO smsCode(String smsCode) {
        this.smsCode = smsCode;
        return this;
    }

    public SmsConfigDTO templateId(String templateId) {
        this.templateId = templateId;
        return this;
    }

    public SmsConfigDTO accessKey(String accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    public SmsConfigDTO secretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    public SmsConfigDTO regionId(String regionId) {
        this.regionId = regionId;
        return this;
    }

    public SmsConfigDTO signName(String signName) {
        this.signName = signName;
        return this;
    }

    public SmsConfigDTO remark(String remark) {
        this.remark = remark;
        return this;
    }

    public SmsConfigDTO enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public SmsConfigDTO delFlag(Boolean delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public SmsConfigDTO deletedTime(ZonedDateTime deletedTime) {
        this.deletedTime = deletedTime;
        return this;
    }
    // jhipster-needle-dto-add-getters-setters - JHipster will add getters and setters here, do not remove

}
