package com.begcode.monolith.service;

import static tech.jhipster.service.mybatis.AggregateUtil.buildAggregate;
import static tech.jhipster.service.mybatis.AggregateUtil.buildGroupBy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.domain.*; // for static metamodels
import com.begcode.monolith.domain.BusinessType;
import com.begcode.monolith.repository.BusinessTypeRepository;
import com.begcode.monolith.service.criteria.BusinessTypeCriteria;
import com.begcode.monolith.service.dto.BusinessTypeDTO;
import com.begcode.monolith.service.mapper.BusinessTypeMapper;
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
import tech.jhipster.service.mybatis.CriteriaUtil;
import tech.jhipster.service.mybatis.QueryService;

/**
 * 用于对数据库中的{@link BusinessType}实体执行复杂查询的Service。
 * 主要输入是一个{@link BusinessTypeCriteria}，它被转换为{@link QueryWrapper}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link BusinessTypeDTO}列表{@link List} 或 {@link BusinessTypeDTO} 的分页列表 {@link Page}。
 */
@Service
public class BusinessTypeQueryService implements QueryService<BusinessType> {

    private final Logger log = LoggerFactory.getLogger(BusinessTypeQueryService.class);

    protected final BusinessTypeRepository businessTypeRepository;

    protected final BusinessTypeMapper businessTypeMapper;

    public BusinessTypeQueryService(BusinessTypeRepository businessTypeRepository, BusinessTypeMapper businessTypeMapper) {
        this.businessTypeRepository = businessTypeRepository;
        this.businessTypeMapper = businessTypeMapper;
    }

    /**
     * Return a {@link List} of {@link BusinessTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */

    public List<BusinessTypeDTO> findByCriteria(BusinessTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final QueryWrapper<BusinessType> queryWrapper = createQueryWrapper(criteria);
        return businessTypeMapper.toDto(businessTypeRepository.selectList(queryWrapper));
    }

    /**
     * Return a {@link IPage} of {@link BusinessTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */

    public IPage<BusinessTypeDTO> findByCriteria(BusinessTypeCriteria criteria, Page<BusinessType> page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final QueryWrapper<BusinessType> queryWrapper = createQueryWrapper(criteria);
        return businessTypeRepository.selectPage(page, queryWrapper).convert(businessTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */

    public long countByCriteria(BusinessTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final QueryWrapper<BusinessType> queryWrapper = createQueryWrapper(criteria);
        return businessTypeRepository.selectCount(queryWrapper);
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, BusinessTypeCriteria criteria) {
        return (List<T>) businessTypeRepository.selectObjs(createQueryWrapper(criteria).select(fieldName));
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, BusinessTypeCriteria criteria) {
        return businessTypeRepository.selectCount(createQueryWrapper(criteria));
    }

    /**
     * Function to convert {@link BusinessTypeCriteria} to a {@link QueryWrapper}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link QueryWrapper} of the entity.
     */
    protected QueryWrapper<BusinessType> createQueryWrapper(BusinessTypeCriteria criteria) {
        QueryWrapper<BusinessType> queryWrapper = new DynamicJoinQueryWrapper<>(BusinessType.class, null);
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
                });
            } else {
                queryWrapper.and(q -> {
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "name"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "code"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "description"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "icon"));
                });
            }
        }
        return queryWrapper;
    }

    private QueryWrapper<BusinessType> createQueryWrapper(
        QueryWrapper<BusinessType> queryWrapper,
        Boolean useOr,
        BusinessTypeCriteria criteria
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
            if (criteria.getDescription() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getDescription(), "description", useOr));
            }
            if (criteria.getIcon() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getIcon(), "icon", useOr));
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
     * Return a {@link IPage} of {@link BusinessTypeDTO} which matches the criteria from the database.
     * @param queryWrapper The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    public IPage<BusinessTypeDTO> findByQueryWrapper(QueryWrapper<BusinessType> queryWrapper, Page<BusinessType> page) {
        log.debug("find by criteria : {}, page: {}", queryWrapper, page);
        return businessTypeRepository.selectPage(page, queryWrapper).convert(businessTypeMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(BusinessTypeCriteria criteria) {
        QueryWrapper<BusinessType> queryWrapper = createQueryWrapper(criteria);
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
        if (criteria.getDescription() != null) {
            getAggregateAndGroupBy(criteria.getDescription(), "description", selectFields, groupByFields);
        }
        if (criteria.getIcon() != null) {
            getAggregateAndGroupBy(criteria.getIcon(), "icon", selectFields, groupByFields);
        }
        if (CollectionUtils.isNotEmpty(selectFields)) {
            queryWrapper.select(selectFields.toArray(new String[0])).groupBy(CollectionUtils.isNotEmpty(groupByFields), groupByFields);
            return businessTypeRepository.selectMaps(queryWrapper);
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
