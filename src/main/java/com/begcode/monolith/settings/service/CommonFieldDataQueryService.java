package com.begcode.monolith.settings.service;

import static tech.jhipster.service.mybatis.AggregateUtil.buildAggregate;
import static tech.jhipster.service.mybatis.AggregateUtil.buildGroupBy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.settings.domain.*; // for static metamodels
import com.begcode.monolith.settings.domain.CommonFieldData;
import com.begcode.monolith.settings.repository.CommonFieldDataRepository;
import com.begcode.monolith.settings.service.criteria.CommonFieldDataCriteria;
import com.begcode.monolith.settings.service.dto.CommonFieldDataDTO;
import com.begcode.monolith.settings.service.mapper.CommonFieldDataMapper;
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
 * 用于对数据库中的{@link CommonFieldData}实体执行复杂查询的Service。
 * 主要输入是一个{@link CommonFieldDataCriteria}，它被转换为{@link QueryWrapper}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link CommonFieldDataDTO}列表{@link List} 或 {@link CommonFieldDataDTO} 的分页列表 {@link Page}。
 */
@Service
public class CommonFieldDataQueryService implements QueryService<CommonFieldData> {

    private final Logger log = LoggerFactory.getLogger(CommonFieldDataQueryService.class);

    protected final CommonFieldDataRepository commonFieldDataRepository;

    protected final CommonFieldDataMapper commonFieldDataMapper;

    public CommonFieldDataQueryService(CommonFieldDataRepository commonFieldDataRepository, CommonFieldDataMapper commonFieldDataMapper) {
        this.commonFieldDataRepository = commonFieldDataRepository;
        this.commonFieldDataMapper = commonFieldDataMapper;
    }

    /**
     * Return a {@link List} of {@link CommonFieldDataDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */

    public List<CommonFieldDataDTO> findByCriteria(CommonFieldDataCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final QueryWrapper<CommonFieldData> queryWrapper = createQueryWrapper(criteria);
        return commonFieldDataMapper.toDto(commonFieldDataRepository.selectList(queryWrapper));
    }

    /**
     * Return a {@link IPage} of {@link CommonFieldDataDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */

    public IPage<CommonFieldDataDTO> findByCriteria(CommonFieldDataCriteria criteria, Page<CommonFieldData> page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final QueryWrapper<CommonFieldData> queryWrapper = createQueryWrapper(criteria);
        return commonFieldDataRepository.selectPage(page, queryWrapper).convert(commonFieldDataMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */

    public long countByCriteria(CommonFieldDataCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final QueryWrapper<CommonFieldData> queryWrapper = createQueryWrapper(criteria);
        return commonFieldDataRepository.selectCount(queryWrapper);
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, CommonFieldDataCriteria criteria) {
        return (List<T>) commonFieldDataRepository.selectObjs(createQueryWrapper(criteria).select(fieldName));
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, CommonFieldDataCriteria criteria) {
        return commonFieldDataRepository.selectCount(createQueryWrapper(criteria));
    }

    /**
     * Function to convert {@link CommonFieldDataCriteria} to a {@link QueryWrapper}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link QueryWrapper} of the entity.
     */
    protected QueryWrapper<CommonFieldData> createQueryWrapper(CommonFieldDataCriteria criteria) {
        QueryWrapper<CommonFieldData> queryWrapper = new DynamicJoinQueryWrapper<>(CommonFieldData.class, null);
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
                });
            } else {
                queryWrapper.and(q -> {
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "name"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "value"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "label"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "remark"));
                    q.or(
                        buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "owner_entity_name")
                    );
                    q.or(
                        buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "owner_entity_id")
                    );
                });
            }
        }
        return queryWrapper;
    }

    private QueryWrapper<CommonFieldData> createQueryWrapper(
        QueryWrapper<CommonFieldData> queryWrapper,
        Boolean useOr,
        CommonFieldDataCriteria criteria
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
            if (criteria.getValue() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getValue(), "value", useOr));
            }
            if (criteria.getLabel() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getLabel(), "label", useOr));
            }
            if (criteria.getValueType() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getValueType(), "value_type", useOr));
            }
            if (criteria.getRemark() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getRemark(), "remark", useOr));
            }
            if (criteria.getSortValue() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getSortValue(), "sort_value", useOr));
            }
            if (criteria.getDisabled() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getDisabled(), "disabled", useOr));
            }
            if (criteria.getOwnerEntityName() != null) {
                queryWrapper =
                    CriteriaUtil.build(
                        useOr,
                        queryWrapper,
                        buildStringSpecification(criteria.getOwnerEntityName(), "owner_entity_name", useOr)
                    );
            }
            if (criteria.getOwnerEntityId() != null) {
                queryWrapper =
                    CriteriaUtil.build(
                        useOr,
                        queryWrapper,
                        buildStringSpecification(criteria.getOwnerEntityId(), "owner_entity_id", useOr)
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
     * Return a {@link IPage} of {@link CommonFieldDataDTO} which matches the criteria from the database.
     * @param queryWrapper The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    public IPage<CommonFieldDataDTO> findByQueryWrapper(QueryWrapper<CommonFieldData> queryWrapper, Page<CommonFieldData> page) {
        log.debug("find by criteria : {}, page: {}", queryWrapper, page);
        return commonFieldDataRepository.selectPage(page, queryWrapper).convert(commonFieldDataMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(CommonFieldDataCriteria criteria) {
        QueryWrapper<CommonFieldData> queryWrapper = createQueryWrapper(criteria);
        List<String> selectFields = new ArrayList<>();
        List<String> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields);
        }
        if (criteria.getName() != null) {
            getAggregateAndGroupBy(criteria.getName(), "name", selectFields, groupByFields);
        }
        if (criteria.getValue() != null) {
            getAggregateAndGroupBy(criteria.getValue(), "value", selectFields, groupByFields);
        }
        if (criteria.getLabel() != null) {
            getAggregateAndGroupBy(criteria.getLabel(), "label", selectFields, groupByFields);
        }
        if (criteria.getValueType() != null) {
            getAggregateAndGroupBy(criteria.getValueType(), "value_type", selectFields, groupByFields);
        }
        if (criteria.getRemark() != null) {
            getAggregateAndGroupBy(criteria.getRemark(), "remark", selectFields, groupByFields);
        }
        if (criteria.getSortValue() != null) {
            getAggregateAndGroupBy(criteria.getSortValue(), "sort_value", selectFields, groupByFields);
        }
        if (criteria.getDisabled() != null) {
            getAggregateAndGroupBy(criteria.getDisabled(), "disabled", selectFields, groupByFields);
        }
        if (criteria.getOwnerEntityName() != null) {
            getAggregateAndGroupBy(criteria.getOwnerEntityName(), "owner_entity_name", selectFields, groupByFields);
        }
        if (criteria.getOwnerEntityId() != null) {
            getAggregateAndGroupBy(criteria.getOwnerEntityId(), "owner_entity_id", selectFields, groupByFields);
        }
        if (CollectionUtils.isNotEmpty(selectFields)) {
            queryWrapper.select(selectFields.toArray(new String[0])).groupBy(CollectionUtils.isNotEmpty(groupByFields), groupByFields);
            return commonFieldDataRepository.selectMaps(queryWrapper);
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
