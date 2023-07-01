package com.begcode.monolith.taskjob.service;

import static tech.jhipster.service.mybatis.AggregateUtil.buildAggregate;
import static tech.jhipster.service.mybatis.AggregateUtil.buildGroupBy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.taskjob.domain.*; // for static metamodels
import com.begcode.monolith.taskjob.domain.TaskJobConfig;
import com.begcode.monolith.taskjob.repository.TaskJobConfigRepository;
import com.begcode.monolith.taskjob.service.criteria.TaskJobConfigCriteria;
import com.begcode.monolith.taskjob.service.dto.TaskJobConfigDTO;
import com.begcode.monolith.taskjob.service.mapper.TaskJobConfigMapper;
import com.diboot.core.binding.query.dynamic.DynamicJoinQueryWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tech.jhipster.service.aggregate.*;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.service.filter.ZonedDateTimeFilter;
import tech.jhipster.service.mybatis.CriteriaUtil;
import tech.jhipster.service.mybatis.QueryService;

/**
 * 用于对数据库中的{@link TaskJobConfig}实体执行复杂查询的Service。
 * 主要输入是一个{@link TaskJobConfigCriteria}，它被转换为{@link QueryWrapper}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link TaskJobConfigDTO}列表{@link List} 或 {@link TaskJobConfigDTO} 的分页列表 {@link Page}。
 */
@Service
public class TaskJobConfigQueryService implements QueryService<TaskJobConfig> {

    private final Logger log = LoggerFactory.getLogger(TaskJobConfigQueryService.class);

    protected final TaskJobConfigRepository taskJobConfigRepository;

    protected final TaskJobConfigMapper taskJobConfigMapper;

    public TaskJobConfigQueryService(TaskJobConfigRepository taskJobConfigRepository, TaskJobConfigMapper taskJobConfigMapper) {
        this.taskJobConfigRepository = taskJobConfigRepository;
        this.taskJobConfigMapper = taskJobConfigMapper;
    }

    /**
     * Return a {@link List} of {@link TaskJobConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */

    public List<TaskJobConfigDTO> findByCriteria(TaskJobConfigCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final QueryWrapper<TaskJobConfig> queryWrapper = createQueryWrapper(criteria);
        return taskJobConfigMapper.toDto(taskJobConfigRepository.selectList(queryWrapper));
    }

    /**
     * Return a {@link IPage} of {@link TaskJobConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */

    public IPage<TaskJobConfigDTO> findByCriteria(TaskJobConfigCriteria criteria, Page<TaskJobConfig> page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final QueryWrapper<TaskJobConfig> queryWrapper = createQueryWrapper(criteria);
        return taskJobConfigRepository.selectPage(page, queryWrapper).convert(taskJobConfigMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */

    public long countByCriteria(TaskJobConfigCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final QueryWrapper<TaskJobConfig> queryWrapper = createQueryWrapper(criteria);
        return taskJobConfigRepository.selectCount(queryWrapper);
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, TaskJobConfigCriteria criteria) {
        return (List<T>) taskJobConfigRepository.selectObjs(createQueryWrapper(criteria).select(fieldName));
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, TaskJobConfigCriteria criteria) {
        return taskJobConfigRepository.selectCount(createQueryWrapper(criteria));
    }

    /**
     * Function to convert {@link TaskJobConfigCriteria} to a {@link QueryWrapper}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link QueryWrapper} of the entity.
     */
    protected QueryWrapper<TaskJobConfig> createQueryWrapper(TaskJobConfigCriteria criteria) {
        QueryWrapper<TaskJobConfig> queryWrapper = new DynamicJoinQueryWrapper<>(TaskJobConfig.class, null);
        queryWrapper = createQueryWrapper(queryWrapper, criteria.getUseOr(), criteria);
        if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
            if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                queryWrapper.and(q -> {
                    q.or(buildSpecification(new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())), "id"));
                    q.or(
                        buildRangeSpecification(
                            (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                            "id"
                        )
                    );
                    q.or(
                        buildRangeSpecification(
                            (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                            "created_by"
                        )
                    );
                    q.or(
                        buildRangeSpecification(
                            (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                            "last_modified_by"
                        )
                    );
                });
            } else {
                queryWrapper.and(q -> {
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "name"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "job_class_name"));
                    q.or(
                        buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "cron_expression")
                    );
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "parameter"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "description"));
                });
            }
        }
        return queryWrapper;
    }

    private QueryWrapper<TaskJobConfig> createQueryWrapper(
        QueryWrapper<TaskJobConfig> queryWrapper,
        Boolean useOr,
        TaskJobConfigCriteria criteria
    ) {
        if (criteria != null) {
            if (useOr == null) {
                useOr = false;
            }
            if (criteria.getId() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getId(), "id", useOr));
            }
            if (criteria.getName() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getName(), "name", useOr));
            }
            if (criteria.getJobClassName() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getJobClassName(), "job_class_name", useOr));
            }
            if (criteria.getCronExpression() != null) {
                queryWrapper =
                    CriteriaUtil.build(
                        useOr,
                        queryWrapper,
                        buildStringSpecification(criteria.getCronExpression(), "cron_expression", useOr)
                    );
            }
            if (criteria.getParameter() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getParameter(), "parameter", useOr));
            }
            if (criteria.getDescription() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getDescription(), "description", useOr));
            }
            if (criteria.getJobStatus() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getJobStatus(), "job_status", useOr));
            }
            if (criteria.getCreatedBy() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getCreatedBy(), "created_by", useOr));
            }
            if (criteria.getCreatedDate() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getCreatedDate(), "created_date", useOr));
            }
            if (criteria.getLastModifiedBy() != null) {
                queryWrapper =
                    CriteriaUtil.build(
                        useOr,
                        queryWrapper,
                        buildRangeSpecification(criteria.getLastModifiedBy(), "last_modified_by", useOr)
                    );
            }
            if (criteria.getLastModifiedDate() != null) {
                queryWrapper =
                    CriteriaUtil.build(
                        useOr,
                        queryWrapper,
                        buildRangeSpecification(criteria.getLastModifiedDate(), "last_modified_date", useOr)
                    );
            }
            if (criteria.getAnd() != null) {
                queryWrapper.and(q -> createQueryWrapper(q, criteria.getAnd().getUseOr(), criteria.getAnd()));
            } else {
                if (criteria.getOr() != null) {
                    queryWrapper.or(q -> createQueryWrapper(q, criteria.getOr().getUseOr(), criteria.getOr()));
                }
            }
        }
        return queryWrapper;
    }

    /**
     * Return a {@link IPage} of {@link TaskJobConfigDTO} which matches the criteria from the database.
     * @param queryWrapper The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    public IPage<TaskJobConfigDTO> findByQueryWrapper(QueryWrapper<TaskJobConfig> queryWrapper, Page<TaskJobConfig> page) {
        log.debug("find by criteria : {}, page: {}", queryWrapper, page);
        return taskJobConfigRepository.selectPage(page, queryWrapper).convert(taskJobConfigMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(TaskJobConfigCriteria criteria) {
        QueryWrapper<TaskJobConfig> queryWrapper = createQueryWrapper(criteria);
        List<String> selectFields = new ArrayList<>();
        List<String> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields);
        }
        if (criteria.getName() != null) {
            getAggregateAndGroupBy(criteria.getName(), "name", selectFields, groupByFields);
        }
        if (criteria.getJobClassName() != null) {
            getAggregateAndGroupBy(criteria.getJobClassName(), "job_class_name", selectFields, groupByFields);
        }
        if (criteria.getCronExpression() != null) {
            getAggregateAndGroupBy(criteria.getCronExpression(), "cron_expression", selectFields, groupByFields);
        }
        if (criteria.getParameter() != null) {
            getAggregateAndGroupBy(criteria.getParameter(), "parameter", selectFields, groupByFields);
        }
        if (criteria.getDescription() != null) {
            getAggregateAndGroupBy(criteria.getDescription(), "description", selectFields, groupByFields);
        }
        if (criteria.getJobStatus() != null) {
            getAggregateAndGroupBy(criteria.getJobStatus(), "job_status", selectFields, groupByFields);
        }
        if (criteria.getCreatedBy() != null) {
            getAggregateAndGroupBy(criteria.getCreatedBy(), "created_by", selectFields, groupByFields);
        }
        if (criteria.getCreatedDate() != null) {
            getAggregateAndGroupBy(criteria.getCreatedDate(), "created_date", selectFields, groupByFields);
        }
        if (criteria.getLastModifiedBy() != null) {
            getAggregateAndGroupBy(criteria.getLastModifiedBy(), "last_modified_by", selectFields, groupByFields);
        }
        if (criteria.getLastModifiedDate() != null) {
            getAggregateAndGroupBy(criteria.getLastModifiedDate(), "last_modified_date", selectFields, groupByFields);
        }
        if (CollectionUtils.isNotEmpty(selectFields)) {
            queryWrapper.select(selectFields.toArray(new String[0])).groupBy(CollectionUtils.isNotEmpty(groupByFields), groupByFields);
            return taskJobConfigRepository.selectMaps(queryWrapper);
        }
        return Collections.emptyList();
    }

    private void getAggregateAndGroupBy(Filter<?> filter, String fieldName, List<String> selects, List<String> groupBys) {
        if (filter.getAggregate() != null) {
            if (filter.getAggregate() instanceof NumberAggregate) {
                buildAggregate((NumberAggregate) filter.getAggregate(), fieldName, selects);
            } else {
                buildAggregate(filter.getAggregate(), fieldName, selects);
            }
        }
        if (filter.getGroupBy() != null) {
            if (filter.getGroupBy() instanceof DateTimeGroupBy) {
                buildGroupBy((DateTimeGroupBy) filter.getGroupBy(), fieldName, groupBys, selects);
            } else {
                buildGroupBy(filter.getGroupBy(), fieldName, groupBys, selects);
            }
        }
    }
}
