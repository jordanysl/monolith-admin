package com.begcode.monolith.system.service.dto;

import com.begcode.monolith.domain.AbstractAuditingEntity;
import com.begcode.monolith.domain.enumeration.AnnoBusinessType;
import com.begcode.monolith.domain.enumeration.AnnoCategory;
import com.begcode.monolith.domain.enumeration.AnnoOpenType;
import com.begcode.monolith.domain.enumeration.AnnoSendStatus;
import com.begcode.monolith.domain.enumeration.PriorityLevel;
import com.begcode.monolith.domain.enumeration.ReceiverType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.ZonedDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**

 * {@link com.begcode.monolith.system.domain.Announcement}的DTO。
 */
@Schema(description = "系统通告")
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnnouncementDTO extends AbstractAuditingEntity<Long, AnnouncementDTO> {

    private Long id;

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;

    /**
     * 内容
     */
    @Schema(description = "内容")
    private String content;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private ZonedDateTime startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private ZonedDateTime endTime;

    /**
     * 发布人Id
     */
    @Schema(description = "发布人Id")
    private Long senderId;

    /**
     * 优先级\n（L低，M中，H高）
     */
    @Schema(description = "优先级\n（L低，M中，H高）")
    private PriorityLevel priority;

    /**
     * 消息类型\n通知公告,系统消息
     */
    @Schema(description = "消息类型\n通知公告,系统消息")
    private AnnoCategory category;

    /**
     * 通告对象类型\n（USER:指定用户，ALL:全体用户）
     */
    @Schema(description = "通告对象类型\n（USER:指定用户，ALL:全体用户）")
    private ReceiverType receiverType;

    /**
     * 发布状态\n
     */
    @Schema(description = "发布状态\n")
    private AnnoSendStatus sendStatus;

    /**
     * 发布时间
     */
    @Schema(description = "发布时间")
    private ZonedDateTime sendTime;

    /**
     * 撤销时间
     */
    @Schema(description = "撤销时间")
    private ZonedDateTime cancelTime;

    /**
     * 业务类型\n(email:邮件 bpm:流程)
     */
    @Schema(description = "业务类型\n(email:邮件 bpm:流程)")
    private AnnoBusinessType businessType;

    /**
     * 业务id
     */
    @Schema(description = "业务id")
    private Long businessId;

    /**
     * 打开方式
     */
    @Schema(description = "打开方式")
    private AnnoOpenType openType;

    /**
     * 组件/路由 地址
     */
    @Schema(description = "组件/路由 地址")
    private String openPage;

    /**
     * 指定接收者id
     */
    @Schema(description = "指定接收者id")
    private String receiverIds;

    /**
     * 摘要
     */
    @Schema(description = "摘要")
    private String summary;

    /**
     * 创建者Id
     */
    @Schema(description = "创建者Id")
    private Long createdBy;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private ZonedDateTime createdDate;

    /**
     * 修改者Id
     */
    @Schema(description = "修改者Id")
    private Long lastModifiedBy;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private ZonedDateTime lastModifiedDate;

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

    public AnnouncementDTO id(Long id) {
        this.id = id;
        return this;
    }

    public AnnouncementDTO title(String title) {
        this.title = title;
        return this;
    }

    public AnnouncementDTO content(String content) {
        this.content = content;
        return this;
    }

    public AnnouncementDTO startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public AnnouncementDTO endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public AnnouncementDTO senderId(Long senderId) {
        this.senderId = senderId;
        return this;
    }

    public AnnouncementDTO priority(PriorityLevel priority) {
        this.priority = priority;
        return this;
    }

    public AnnouncementDTO category(AnnoCategory category) {
        this.category = category;
        return this;
    }

    public AnnouncementDTO receiverType(ReceiverType receiverType) {
        this.receiverType = receiverType;
        return this;
    }

    public AnnouncementDTO sendStatus(AnnoSendStatus sendStatus) {
        this.sendStatus = sendStatus;
        return this;
    }

    public AnnouncementDTO sendTime(ZonedDateTime sendTime) {
        this.sendTime = sendTime;
        return this;
    }

    public AnnouncementDTO cancelTime(ZonedDateTime cancelTime) {
        this.cancelTime = cancelTime;
        return this;
    }

    public AnnouncementDTO businessType(AnnoBusinessType businessType) {
        this.businessType = businessType;
        return this;
    }

    public AnnouncementDTO businessId(Long businessId) {
        this.businessId = businessId;
        return this;
    }

    public AnnouncementDTO openType(AnnoOpenType openType) {
        this.openType = openType;
        return this;
    }

    public AnnouncementDTO openPage(String openPage) {
        this.openPage = openPage;
        return this;
    }

    public AnnouncementDTO receiverIds(String receiverIds) {
        this.receiverIds = receiverIds;
        return this;
    }

    public AnnouncementDTO summary(String summary) {
        this.summary = summary;
        return this;
    }

    public AnnouncementDTO createdBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public AnnouncementDTO createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public AnnouncementDTO lastModifiedBy(Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public AnnouncementDTO lastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public AnnouncementDTO delFlag(Boolean delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public AnnouncementDTO deletedTime(ZonedDateTime deletedTime) {
        this.deletedTime = deletedTime;
        return this;
    }
    // jhipster-needle-dto-add-getters-setters - JHipster will add getters and setters here, do not remove

}
