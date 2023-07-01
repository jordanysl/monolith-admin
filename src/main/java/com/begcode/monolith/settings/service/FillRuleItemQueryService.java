package com.begcode.monolith.settings.service;

import static tech.jhipster.service.mybatis.AggregateUtil.buildAggregate;
import static tech.jhipster.service.mybatis.AggregateUtil.buildGroupBy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.settings.domain.*; // for static metamodels
import com.begcode.monolith.settings.domain.FillRuleItem;
import com.begcode.monolith.settings.repository.FillRuleItemRepository;
import com.begcode.monolith.settings.service.criteria.FillRuleItemCriteria;
import com.begcode.monolith.settings.service.dto.FillRuleItemDTO;
import com.begcode.monolith.settings.service.mapper.FillRuleItemMapper;
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
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.service.mybatis.CriteriaUtil;
import tech.jhipster.service.mybatis.QueryService;

/**
 * 用于对数据库中的{@link FillRuleItem}实体执行复杂查询的Service。
 * 主要输入是一个{@link FillRuleItemCriteria}，它被转换为{@link QueryWrapper}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link FillRuleItemDTO}列表{@link List} 或 {@link FillRuleItemDTO} 的分页列表 {@link Page}。
 */
@Service
public class FillRuleItemQueryService implements QueryService<FillRuleItem> {

    private final Logger log = LoggerFactory.getLogger(FillRuleItemQueryService.class);

    protected final FillRuleItemRepository fillRuleItemRepository;

    protected final FillRuleItemMapper fillRuleItemMapper;

    public FillRuleItemQueryService(FillRuleItemRepository fillRuleItemRepository, FillRuleItemMapper fillRuleItemMapper) {
        this.fillRuleItemRepository = fillRuleItemRepository;
        this.fillRuleItemMapper = fillRuleItemMapper;
    }

    /**
     * Return a {@link List} of {@link FillRuleItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */

    public List<FillRuleItemDTO> findByCriteria(FillRuleItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final QueryWrapper<FillRuleItem> queryWrapper = createQueryWrapper(criteria);
        return fillRuleItemMapper.toDto(fillRuleItemRepository.selectList(queryWrapper));
    }

    /**
     * Return a {@link IPage} of {@link FillRuleItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */

    public IPage<FillRuleItemDTO> findByCriteria(FillRuleItemCriteria criteria, Page<FillRuleItem> page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final QueryWrapper<FillRuleItem> queryWrapper = createQueryWrapper(criteria);
        return fillRuleItemRepository.selectPage(page, queryWrapper).convert(fillRuleItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */

    public long countByCriteria(FillRuleItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final QueryWrapper<FillRuleItem> queryWrapper = createQueryWrapper(criteria);
        return fillRuleItemRepository.selectCount(queryWrapper);
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, FillRuleItemCriteria criteria) {
        return (List<T>) fillRuleItemRepository.selectObjs(createQueryWrapper(criteria).select(fieldName));
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, FillRuleItemCriteria criteria) {
        return fillRuleItemRepository.selectCount(createQueryWrapper(criteria));
    }

    /**
     * Function to convert {@link FillRuleItemCriteria} to a {@link QueryWrapper}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link QueryWrapper} of the entity.
     */
    protected QueryWrapper<FillRuleItem> createQueryWrapper(FillRuleItemCriteria criteria) {
        QueryWrapper<FillRuleItem> queryWrapper = new DynamicJoinQueryWrapper<>(FillRuleItem.class, null);
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
                            (IntegerFilter) new IntegerFilter().setEquals(Integer.valueOf(criteria.getJhiCommonSearchKeywords())),
                            "sort_value"
                        )
                    );
                    q.or(
                        buildRangeSpecification(
                            (IntegerFilter) new IntegerFilter().setEquals(Integer.valueOf(criteria.getJhiCommonSearchKeywords())),
                            "seq_length"
                        )
                    );
                    q.or(
                        buildRangeSpecification(
                            (IntegerFilter) new IntegerFilter().setEquals(Integer.valueOf(criteria.getJhiCommonSearchKeywords())),
                            "seq_increment"
                        )
                    );
                    q.or(
                        buildRangeSpecification(
                            (IntegerFilter) new IntegerFilter().setEquals(Integer.valueOf(criteria.getJhiCommonSearchKeywords())),
                            "seq_start_value"
                        )
                    );
                });
            } else {
                queryWrapper.and(q -> {
                    q.or(
                        buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "field_param_value")
                    );
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "date_pattern"));
                });
            }
        }
        return queryWrapper;
    }

    private QueryWrapper<FillRuleItem> createQueryWrapper(
        QueryWrapper<FillRuleItem> queryWrapper,
        Boolean useOr,
        FillRuleItemCriteria criteria
    ) {
        if (criteria != null) {
            if (useOr == null) {
                useOr = false;
            }
            if (criteria.getId() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getId(), "id", useOr));
            }
            if (criteria.getSortValue() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getSortValue(), "sort_value", useOr));
            }
            if (criteria.getFieldParamType() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getFieldParamType(), "field_param_type", useOr));
            }
            if (criteria.getFieldParamValue() != null) {
                queryWrapper =
                    CriteriaUtil.build(
                        useOr,
                        queryWrapper,
                        buildStringSpecification(criteria.getFieldParamValue(), "field_param_value", useOr)
                    );
            }
            if (criteria.getDatePattern() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getDatePattern(), "date_pattern", useOr));
            }
            if (criteria.getSeqLength() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getSeqLength(), "seq_length", useOr));
            }
            if (criteria.getSeqIncrement() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getSeqIncrement(), "seq_increment", useOr));
            }
            if (criteria.getSeqStartValue() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getSeqStartValue(), "seq_start_value", useOr));
            }
            if (criteria.getFillRuleId() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getFillRuleId(), "fill_rule_id", useOr));
            }
            if (criteria.getFillRuleName() != null) {
                queryWrapper =
                    CriteriaUtil.build(
                        useOr,
                        queryWrapper,
                        buildStringSpecification(criteria.getFillRuleName(), "sys_fill_rule_left_join.name", useOr)
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
     * Return a {@link IPage} of {@link FillRuleItemDTO} which matches the criteria from the database.
     * @param queryWrapper The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    public IPage<FillRuleItemDTO> findByQueryWrapper(QueryWrapper<FillRuleItem> queryWrapper, Page<FillRuleItem> page) {
        log.debug("find by criteria : {}, page: {}", queryWrapper, page);
        return fillRuleItemRepository.selectPage(page, queryWrapper).convert(fillRuleItemMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(FillRuleItemCriteria criteria) {
        QueryWrapper<FillRuleItem> queryWrapper = createQueryWrapper(criteria);
        List<String> selectFields = new ArrayList<>();
        List<String> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields);
        }
        if (criteria.getSortValue() != null) {
            getAggregateAndGroupBy(criteria.getSortValue(), "sort_value", selectFields, groupByFields);
        }
        if (criteria.getFieldParamType() != null) {
            getAggregateAndGroupBy(criteria.getFieldParamType(), "field_param_type", selectFields, groupByFields);
        }
        if (criteria.getFieldParamValue() != null) {
            getAggregateAndGroupBy(criteria.getFieldParamValue(), "field_param_value", selectFields, groupByFields);
        }
        if (criteria.getDatePattern() != null) {
            getAggregateAndGroupBy(criteria.getDatePattern(), "date_pattern", selectFields, groupByFields);
        }
        if (criteria.getSeqLength() != null) {
            getAggregateAndGroupBy(criteria.getSeqLength(), "seq_length", selectFields, groupByFields);
        }
        if (criteria.getSeqIncrement() != null) {
            getAggregateAndGroupBy(criteria.getSeqIncrement(), "seq_increment", selectFields, groupByFields);
        }
        if (criteria.getSeqStartValue() != null) {
            getAggregateAndGroupBy(criteria.getSeqStartValue(), "seq_start_value", selectFields, groupByFields);
        }
        if (CollectionUtils.isNotEmpty(selectFields)) {
            queryWrapper.select(selectFields.toArray(new String[0])).groupBy(CollectionUtils.isNotEmpty(groupByFields), groupByFields);
            return fillRuleItemRepository.selectMaps(queryWrapper);
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
