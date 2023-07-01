package com.begcode.monolith.system.service;

import static tech.jhipster.service.mybatis.AggregateUtil.buildAggregate;
import static tech.jhipster.service.mybatis.AggregateUtil.buildGroupBy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.system.domain.*; // for static metamodels
import com.begcode.monolith.system.domain.DataPermissionRule;
import com.begcode.monolith.system.repository.DataPermissionRuleRepository;
import com.begcode.monolith.system.service.criteria.DataPermissionRuleCriteria;
import com.begcode.monolith.system.service.dto.DataPermissionRuleDTO;
import com.begcode.monolith.system.service.mapper.DataPermissionRuleMapper;
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
 * 用于对数据库中的{@link DataPermissionRule}实体执行复杂查询的Service。
 * 主要输入是一个{@link DataPermissionRuleCriteria}，它被转换为{@link QueryWrapper}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link DataPermissionRuleDTO}列表{@link List} 或 {@link DataPermissionRuleDTO} 的分页列表 {@link Page}。
 */
@Service
public class DataPermissionRuleQueryService implements QueryService<DataPermissionRule> {

    private final Logger log = LoggerFactory.getLogger(DataPermissionRuleQueryService.class);

    protected final DataPermissionRuleRepository dataPermissionRuleRepository;

    protected final DataPermissionRuleMapper dataPermissionRuleMapper;

    public DataPermissionRuleQueryService(
        DataPermissionRuleRepository dataPermissionRuleRepository,
        DataPermissionRuleMapper dataPermissionRuleMapper
    ) {
        this.dataPermissionRuleRepository = dataPermissionRuleRepository;
        this.dataPermissionRuleMapper = dataPermissionRuleMapper;
    }

    /**
     * Return a {@link List} of {@link DataPermissionRuleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */

    public List<DataPermissionRuleDTO> findByCriteria(DataPermissionRuleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final QueryWrapper<DataPermissionRule> queryWrapper = createQueryWrapper(criteria);
        return dataPermissionRuleMapper.toDto(dataPermissionRuleRepository.selectList(queryWrapper));
    }

    /**
     * Return a {@link IPage} of {@link DataPermissionRuleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */

    public IPage<DataPermissionRuleDTO> findByCriteria(DataPermissionRuleCriteria criteria, Page<DataPermissionRule> page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final QueryWrapper<DataPermissionRule> queryWrapper = createQueryWrapper(criteria);
        return dataPermissionRuleRepository.selectPage(page, queryWrapper).convert(dataPermissionRuleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */

    public long countByCriteria(DataPermissionRuleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final QueryWrapper<DataPermissionRule> queryWrapper = createQueryWrapper(criteria);
        return dataPermissionRuleRepository.selectCount(queryWrapper);
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, DataPermissionRuleCriteria criteria) {
        return (List<T>) dataPermissionRuleRepository.selectObjs(createQueryWrapper(criteria).select(fieldName));
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, DataPermissionRuleCriteria criteria) {
        return dataPermissionRuleRepository.selectCount(createQueryWrapper(criteria));
    }

    /**
     * Function to convert {@link DataPermissionRuleCriteria} to a {@link QueryWrapper}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link QueryWrapper} of the entity.
     */
    protected QueryWrapper<DataPermissionRule> createQueryWrapper(DataPermissionRuleCriteria criteria) {
        QueryWrapper<DataPermissionRule> queryWrapper = new DynamicJoinQueryWrapper<>(DataPermissionRule.class, null);
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
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "permission_id"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "name"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "column"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "conditions"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "value"));
                });
            }
        }
        return queryWrapper;
    }

    private QueryWrapper<DataPermissionRule> createQueryWrapper(
        QueryWrapper<DataPermissionRule> queryWrapper,
        Boolean useOr,
        DataPermissionRuleCriteria criteria
    ) {
        if (criteria != null) {
            if (useOr == null) {
                useOr = false;
            }
            if (criteria.getId() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getId(), "id", useOr));
            }
            if (criteria.getPermissionId() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getPermissionId(), "permission_id", useOr));
            }
            if (criteria.getName() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getName(), "name", useOr));
            }
            if (criteria.getColumn() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getColumn(), "column", useOr));
            }
            if (criteria.getConditions() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getConditions(), "conditions", useOr));
            }
            if (criteria.getValue() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getValue(), "value", useOr));
            }
            if (criteria.getDisabled() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getDisabled(), "disabled", useOr));
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
     * Return a {@link IPage} of {@link DataPermissionRuleDTO} which matches the criteria from the database.
     * @param queryWrapper The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    public IPage<DataPermissionRuleDTO> findByQueryWrapper(QueryWrapper<DataPermissionRule> queryWrapper, Page<DataPermissionRule> page) {
        log.debug("find by criteria : {}, page: {}", queryWrapper, page);
        return dataPermissionRuleRepository.selectPage(page, queryWrapper).convert(dataPermissionRuleMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(DataPermissionRuleCriteria criteria) {
        QueryWrapper<DataPermissionRule> queryWrapper = createQueryWrapper(criteria);
        List<String> selectFields = new ArrayList<>();
        List<String> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields);
        }
        if (criteria.getPermissionId() != null) {
            getAggregateAndGroupBy(criteria.getPermissionId(), "permission_id", selectFields, groupByFields);
        }
        if (criteria.getName() != null) {
            getAggregateAndGroupBy(criteria.getName(), "name", selectFields, groupByFields);
        }
        if (criteria.getColumn() != null) {
            getAggregateAndGroupBy(criteria.getColumn(), "column", selectFields, groupByFields);
        }
        if (criteria.getConditions() != null) {
            getAggregateAndGroupBy(criteria.getConditions(), "conditions", selectFields, groupByFields);
        }
        if (criteria.getValue() != null) {
            getAggregateAndGroupBy(criteria.getValue(), "value", selectFields, groupByFields);
        }
        if (criteria.getDisabled() != null) {
            getAggregateAndGroupBy(criteria.getDisabled(), "disabled", selectFields, groupByFields);
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
            return dataPermissionRuleRepository.selectMaps(queryWrapper);
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
