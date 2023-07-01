package com.begcode.monolith.settings.service;

import static tech.jhipster.service.mybatis.AggregateUtil.buildAggregate;
import static tech.jhipster.service.mybatis.AggregateUtil.buildGroupBy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.settings.domain.*; // for static metamodels
import com.begcode.monolith.settings.domain.SiteConfig;
import com.begcode.monolith.settings.repository.SiteConfigRepository;
import com.begcode.monolith.settings.service.criteria.SiteConfigCriteria;
import com.begcode.monolith.settings.service.dto.SiteConfigDTO;
import com.begcode.monolith.settings.service.mapper.SiteConfigMapper;
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
import tech.jhipster.service.filter.ZonedDateTimeFilter;
import tech.jhipster.service.mybatis.CriteriaUtil;
import tech.jhipster.service.mybatis.QueryService;

/**
 * 用于对数据库中的{@link SiteConfig}实体执行复杂查询的Service。
 * 主要输入是一个{@link SiteConfigCriteria}，它被转换为{@link QueryWrapper}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link SiteConfigDTO}列表{@link List} 或 {@link SiteConfigDTO} 的分页列表 {@link Page}。
 */
@Service
public class SiteConfigQueryService implements QueryService<SiteConfig> {

    private final Logger log = LoggerFactory.getLogger(SiteConfigQueryService.class);

    protected final SiteConfigRepository siteConfigRepository;

    protected final SiteConfigMapper siteConfigMapper;

    public SiteConfigQueryService(SiteConfigRepository siteConfigRepository, SiteConfigMapper siteConfigMapper) {
        this.siteConfigRepository = siteConfigRepository;
        this.siteConfigMapper = siteConfigMapper;
    }

    /**
     * Return a {@link List} of {@link SiteConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */

    public List<SiteConfigDTO> findByCriteria(SiteConfigCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final QueryWrapper<SiteConfig> queryWrapper = createQueryWrapper(criteria);
        return siteConfigMapper.toDto(siteConfigRepository.selectList(queryWrapper));
    }

    /**
     * Return a {@link IPage} of {@link SiteConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */

    public IPage<SiteConfigDTO> findByCriteria(SiteConfigCriteria criteria, Page<SiteConfig> page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final QueryWrapper<SiteConfig> queryWrapper = createQueryWrapper(criteria);
        return siteConfigRepository.selectPage(page, queryWrapper).convert(siteConfigMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */

    public long countByCriteria(SiteConfigCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final QueryWrapper<SiteConfig> queryWrapper = createQueryWrapper(criteria);
        return siteConfigRepository.selectCount(queryWrapper);
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, SiteConfigCriteria criteria) {
        return (List<T>) siteConfigRepository.selectObjs(createQueryWrapper(criteria).select(fieldName));
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, SiteConfigCriteria criteria) {
        return siteConfigRepository.selectCount(createQueryWrapper(criteria));
    }

    /**
     * Function to convert {@link SiteConfigCriteria} to a {@link QueryWrapper}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link QueryWrapper} of the entity.
     */
    protected QueryWrapper<SiteConfig> createQueryWrapper(SiteConfigCriteria criteria) {
        QueryWrapper<SiteConfig> queryWrapper = new DynamicJoinQueryWrapper<>(SiteConfig.class, null);
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
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "category_name"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "category_key"));
                });
            }
        }
        return queryWrapper;
    }

    private QueryWrapper<SiteConfig> createQueryWrapper(QueryWrapper<SiteConfig> queryWrapper, Boolean useOr, SiteConfigCriteria criteria) {
        if (criteria != null) {
            if (useOr == null) {
                useOr = false;
            }
            if (criteria.getId() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getId(), "id", useOr));
            }
            if (criteria.getCategoryName() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getCategoryName(), "category_name", useOr));
            }
            if (criteria.getCategoryKey() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getCategoryKey(), "category_key", useOr));
            }
            if (criteria.getDisabled() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getDisabled(), "disabled", useOr));
            }
            if (criteria.getSortValue() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getSortValue(), "sort_value", useOr));
            }
            if (criteria.getBuiltIn() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getBuiltIn(), "built_in", useOr));
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
            if (criteria.getItemsId() != null) {
                // todo 未实现
            }
            if (criteria.getItemsName() != null) {
                // todo 未实现 one-to-many;[object Object];name
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
     * Return a {@link IPage} of {@link SiteConfigDTO} which matches the criteria from the database.
     * @param queryWrapper The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    public IPage<SiteConfigDTO> findByQueryWrapper(QueryWrapper<SiteConfig> queryWrapper, Page<SiteConfig> page) {
        log.debug("find by criteria : {}, page: {}", queryWrapper, page);
        return siteConfigRepository.selectPage(page, queryWrapper).convert(siteConfigMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(SiteConfigCriteria criteria) {
        QueryWrapper<SiteConfig> queryWrapper = createQueryWrapper(criteria);
        List<String> selectFields = new ArrayList<>();
        List<String> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields);
        }
        if (criteria.getCategoryName() != null) {
            getAggregateAndGroupBy(criteria.getCategoryName(), "category_name", selectFields, groupByFields);
        }
        if (criteria.getCategoryKey() != null) {
            getAggregateAndGroupBy(criteria.getCategoryKey(), "category_key", selectFields, groupByFields);
        }
        if (criteria.getDisabled() != null) {
            getAggregateAndGroupBy(criteria.getDisabled(), "disabled", selectFields, groupByFields);
        }
        if (criteria.getSortValue() != null) {
            getAggregateAndGroupBy(criteria.getSortValue(), "sort_value", selectFields, groupByFields);
        }
        if (criteria.getBuiltIn() != null) {
            getAggregateAndGroupBy(criteria.getBuiltIn(), "built_in", selectFields, groupByFields);
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
            return siteConfigRepository.selectMaps(queryWrapper);
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
