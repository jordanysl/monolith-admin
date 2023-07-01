package com.begcode.monolith.settings.service;

import static tech.jhipster.service.mybatis.AggregateUtil.buildAggregate;
import static tech.jhipster.service.mybatis.AggregateUtil.buildGroupBy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.settings.domain.*; // for static metamodels
import com.begcode.monolith.settings.domain.SysFillRule;
import com.begcode.monolith.settings.repository.SysFillRuleRepository;
import com.begcode.monolith.settings.service.criteria.SysFillRuleCriteria;
import com.begcode.monolith.settings.service.dto.SysFillRuleDTO;
import com.begcode.monolith.settings.service.mapper.SysFillRuleMapper;
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
 * 用于对数据库中的{@link SysFillRule}实体执行复杂查询的Service。
 * 主要输入是一个{@link SysFillRuleCriteria}，它被转换为{@link QueryWrapper}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link SysFillRuleDTO}列表{@link List} 或 {@link SysFillRuleDTO} 的分页列表 {@link Page}。
 */
@Service
public class SysFillRuleQueryService implements QueryService<SysFillRule> {

    private final Logger log = LoggerFactory.getLogger(SysFillRuleQueryService.class);

    protected final SysFillRuleRepository sysFillRuleRepository;

    protected final SysFillRuleMapper sysFillRuleMapper;

    public SysFillRuleQueryService(SysFillRuleRepository sysFillRuleRepository, SysFillRuleMapper sysFillRuleMapper) {
        this.sysFillRuleRepository = sysFillRuleRepository;
        this.sysFillRuleMapper = sysFillRuleMapper;
    }

    /**
     * Return a {@link List} of {@link SysFillRuleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */

    public List<SysFillRuleDTO> findByCriteria(SysFillRuleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final QueryWrapper<SysFillRule> queryWrapper = createQueryWrapper(criteria);
        return sysFillRuleMapper.toDto(sysFillRuleRepository.selectList(queryWrapper));
    }

    /**
     * Return a {@link IPage} of {@link SysFillRuleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */

    public IPage<SysFillRuleDTO> findByCriteria(SysFillRuleCriteria criteria, Page<SysFillRule> page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final QueryWrapper<SysFillRule> queryWrapper = createQueryWrapper(criteria);
        return sysFillRuleRepository.selectPage(page, queryWrapper).convert(sysFillRuleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */

    public long countByCriteria(SysFillRuleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final QueryWrapper<SysFillRule> queryWrapper = createQueryWrapper(criteria);
        return sysFillRuleRepository.selectCount(queryWrapper);
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, SysFillRuleCriteria criteria) {
        return (List<T>) sysFillRuleRepository.selectObjs(createQueryWrapper(criteria).select(fieldName));
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, SysFillRuleCriteria criteria) {
        return sysFillRuleRepository.selectCount(createQueryWrapper(criteria));
    }

    /**
     * Function to convert {@link SysFillRuleCriteria} to a {@link QueryWrapper}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link QueryWrapper} of the entity.
     */
    protected QueryWrapper<SysFillRule> createQueryWrapper(SysFillRuleCriteria criteria) {
        QueryWrapper<SysFillRule> queryWrapper = new DynamicJoinQueryWrapper<>(SysFillRule.class, null);
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
                            "seq_value"
                        )
                    );
                });
            } else {
                queryWrapper.and(q -> {
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "name"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "code"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "desc"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "fill_value"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "impl_class"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "params"));
                });
            }
        }
        return queryWrapper;
    }

    private QueryWrapper<SysFillRule> createQueryWrapper(
        QueryWrapper<SysFillRule> queryWrapper,
        Boolean useOr,
        SysFillRuleCriteria criteria
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
            if (criteria.getCode() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getCode(), "code", useOr));
            }
            if (criteria.getDesc() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getDesc(), "desc", useOr));
            }
            if (criteria.getEnabled() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getEnabled(), "enabled", useOr));
            }
            if (criteria.getResetFrequency() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getResetFrequency(), "reset_frequency", useOr));
            }
            if (criteria.getSeqValue() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getSeqValue(), "seq_value", useOr));
            }
            if (criteria.getFillValue() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getFillValue(), "fill_value", useOr));
            }
            if (criteria.getImplClass() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getImplClass(), "impl_class", useOr));
            }
            if (criteria.getParams() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getParams(), "params", useOr));
            }
            if (criteria.getResetStartTime() != null) {
                queryWrapper =
                    CriteriaUtil.build(
                        useOr,
                        queryWrapper,
                        buildRangeSpecification(criteria.getResetStartTime(), "reset_start_time", useOr)
                    );
            }
            if (criteria.getResetEndTime() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getResetEndTime(), "reset_end_time", useOr));
            }
            if (criteria.getResetTime() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getResetTime(), "reset_time", useOr));
            }
            if (criteria.getRuleItemsId() != null) {
                // todo 未实现
            }
            if (criteria.getRuleItemsDatePattern() != null) {
                // todo 未实现 one-to-many;[object Object];datePattern
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
     * Return a {@link IPage} of {@link SysFillRuleDTO} which matches the criteria from the database.
     * @param queryWrapper The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    public IPage<SysFillRuleDTO> findByQueryWrapper(QueryWrapper<SysFillRule> queryWrapper, Page<SysFillRule> page) {
        log.debug("find by criteria : {}, page: {}", queryWrapper, page);
        return sysFillRuleRepository.selectPage(page, queryWrapper).convert(sysFillRuleMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(SysFillRuleCriteria criteria) {
        QueryWrapper<SysFillRule> queryWrapper = createQueryWrapper(criteria);
        List<String> selectFields = new ArrayList<>();
        List<String> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields);
        }
        if (criteria.getName() != null) {
            getAggregateAndGroupBy(criteria.getName(), "name", selectFields, groupByFields);
        }
        if (criteria.getCode() != null) {
            getAggregateAndGroupBy(criteria.getCode(), "code", selectFields, groupByFields);
        }
        if (criteria.getDesc() != null) {
            getAggregateAndGroupBy(criteria.getDesc(), "desc", selectFields, groupByFields);
        }
        if (criteria.getEnabled() != null) {
            getAggregateAndGroupBy(criteria.getEnabled(), "enabled", selectFields, groupByFields);
        }
        if (criteria.getResetFrequency() != null) {
            getAggregateAndGroupBy(criteria.getResetFrequency(), "reset_frequency", selectFields, groupByFields);
        }
        if (criteria.getSeqValue() != null) {
            getAggregateAndGroupBy(criteria.getSeqValue(), "seq_value", selectFields, groupByFields);
        }
        if (criteria.getFillValue() != null) {
            getAggregateAndGroupBy(criteria.getFillValue(), "fill_value", selectFields, groupByFields);
        }
        if (criteria.getImplClass() != null) {
            getAggregateAndGroupBy(criteria.getImplClass(), "impl_class", selectFields, groupByFields);
        }
        if (criteria.getParams() != null) {
            getAggregateAndGroupBy(criteria.getParams(), "params", selectFields, groupByFields);
        }
        if (criteria.getResetStartTime() != null) {
            getAggregateAndGroupBy(criteria.getResetStartTime(), "reset_start_time", selectFields, groupByFields);
        }
        if (criteria.getResetEndTime() != null) {
            getAggregateAndGroupBy(criteria.getResetEndTime(), "reset_end_time", selectFields, groupByFields);
        }
        if (criteria.getResetTime() != null) {
            getAggregateAndGroupBy(criteria.getResetTime(), "reset_time", selectFields, groupByFields);
        }
        if (CollectionUtils.isNotEmpty(selectFields)) {
            queryWrapper.select(selectFields.toArray(new String[0])).groupBy(CollectionUtils.isNotEmpty(groupByFields), groupByFields);
            return sysFillRuleRepository.selectMaps(queryWrapper);
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
