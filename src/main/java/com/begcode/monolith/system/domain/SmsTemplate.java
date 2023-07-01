package com.begcode.monolith.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.begcode.monolith.domain.AbstractAuditingEntity;
import com.begcode.monolith.domain.enumeration.MessageSendType;
import com.diboot.core.binding.annotation.BindEntity;
import com.diboot.core.binding.annotation.BindEntityList;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.*;

/**
 * 消息模板
 */
@TableName(value = "sms_template")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SmsTemplate extends AbstractAuditingEntity<Long, SmsTemplate> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @TableField(value = "id")
    private Long id;

    /**
     * 模板标题
     */
    @TableField(value = "name")
    private String name;

    /**
     * 模板CODE
     */
    @TableField(value = "code")
    private String code;

    /**
     * 模板类型
     */
    @TableField(value = "type")
    private MessageSendType type;

    /**
     * 模板内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 模板测试json
     */
    @TableField(value = "test_json")
    private String testJson;

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

    public SmsTemplate id(Long id) {
        this.id = id;
        return this;
    }

    public SmsTemplate name(String name) {
        this.name = name;
        return this;
    }

    public SmsTemplate code(String code) {
        this.code = code;
        return this;
    }

    public SmsTemplate type(MessageSendType type) {
        this.type = type;
        return this;
    }

    public SmsTemplate content(String content) {
        this.content = content;
        return this;
    }

    public SmsTemplate testJson(String testJson) {
        this.testJson = testJson;
        return this;
    }

    public SmsTemplate delFlag(Boolean delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public SmsTemplate deletedTime(ZonedDateTime deletedTime) {
        this.deletedTime = deletedTime;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SmsTemplate)) {
            return false;
        }
        return id != null && id.equals(((SmsTemplate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
