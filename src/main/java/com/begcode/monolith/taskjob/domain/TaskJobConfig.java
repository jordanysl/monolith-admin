package com.begcode.monolith.taskjob.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.begcode.monolith.domain.AbstractAuditingEntity;
import com.begcode.monolith.domain.enumeration.JobStatus;
import com.diboot.core.binding.annotation.BindEntity;
import com.diboot.core.binding.annotation.BindEntityList;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.*;

/**
 * 定时任务
 */
@TableName(value = "task_job_config")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TaskJobConfig extends AbstractAuditingEntity<Long, TaskJobConfig> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @TableField(value = "id")
    private Long id;

    /**
     * 任务名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 任务类名
     */
    @TableField(value = "job_class_name")
    private String jobClassName;

    /**
     * cron表达式
     */
    @TableField(value = "cron_expression")
    private String cronExpression;

    /**
     * 参数
     */
    @TableField(value = "parameter")
    private String parameter;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 任务状态
     */
    @TableField(value = "job_status")
    private JobStatus jobStatus;

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

    public TaskJobConfig id(Long id) {
        this.id = id;
        return this;
    }

    public TaskJobConfig name(String name) {
        this.name = name;
        return this;
    }

    public TaskJobConfig jobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
        return this;
    }

    public TaskJobConfig cronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
        return this;
    }

    public TaskJobConfig parameter(String parameter) {
        this.parameter = parameter;
        return this;
    }

    public TaskJobConfig description(String description) {
        this.description = description;
        return this;
    }

    public TaskJobConfig jobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
        return this;
    }

    public TaskJobConfig delFlag(Boolean delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public TaskJobConfig deletedTime(ZonedDateTime deletedTime) {
        this.deletedTime = deletedTime;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskJobConfig)) {
            return false;
        }
        return id != null && id.equals(((TaskJobConfig) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
