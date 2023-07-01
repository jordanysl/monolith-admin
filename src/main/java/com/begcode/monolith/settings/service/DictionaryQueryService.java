package com.begcode.monolith.settings.service;

import static tech.jhipster.service.mybatis.AggregateUtil.buildAggregate;
import static tech.jhipster.service.mybatis.AggregateUtil.buildGroupBy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.settings.domain.*; // for static metamodels
import com.begcode.monolith.settings.domain.Dictionary;
import com.begcode.monolith.settings.repository.DictionaryRepository;
import com.begcode.monolith.settings.service.criteria.DictionaryCriteria;
import com.begcode.monolith.settings.service.dto.DictionaryDTO;
import com.begcode.monolith.settings.service.mapper.DictionaryMapper;
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
 * 用于对数据库中的{@link Dictionary}实体执行复杂查询的Service。
 * 主要输入是一个{@link DictionaryCriteria}，它被转换为{@link QueryWrapper}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link DictionaryDTO}列表{@link List} 或 {@link DictionaryDTO} 的分页列表 {@link Page}。
 */
@Service
public class DictionaryQueryService implements QueryService<Dictionary> {

    private final Logger log = LoggerFactory.getLogger(DictionaryQueryService.class);

    protected final DictionaryRepository dictionaryRepository;

    protected final DictionaryMapper dictionaryMapper;

    public DictionaryQueryService(DictionaryRepository dictionaryRepository, DictionaryMapper dictionaryMapper) {
        this.dictionaryRepository = dictionaryRepository;
        this.dictionaryMapper = dictionaryMapper;
    }

    /**
     * Return a {@link List} of {@link DictionaryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */

    public List<DictionaryDTO> findByCriteria(DictionaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final QueryWrapper<Dictionary> queryWrapper = createQueryWrapper(criteria);
        return dictionaryMapper.toDto(dictionaryRepository.selectList(queryWrapper));
    }

    /**
     * Return a {@link IPage} of {@link DictionaryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */

    public IPage<DictionaryDTO> findByCriteria(DictionaryCriteria criteria, Page<Dictionary> page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final QueryWrapper<Dictionary> queryWrapper = createQueryWrapper(criteria);
        return dictionaryRepository.selectPage(page, queryWrapper).convert(dictionaryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */

    public long countByCriteria(DictionaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final QueryWrapper<Dictionary> queryWrapper = createQueryWrapper(criteria);
        return dictionaryRepository.selectCount(queryWrapper);
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, DictionaryCriteria criteria) {
        return (List<T>) dictionaryRepository.selectObjs(createQueryWrapper(criteria).select(fieldName));
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, DictionaryCriteria criteria) {
        return dictionaryRepository.selectCount(createQueryWrapper(criteria));
    }

    /**
     * Function to convert {@link DictionaryCriteria} to a {@link QueryWrapper}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link QueryWrapper} of the entity.
     */
    protected QueryWrapper<Dictionary> createQueryWrapper(DictionaryCriteria criteria) {
        QueryWrapper<Dictionary> queryWrapper = new DynamicJoinQueryWrapper<>(Dictionary.class, null);
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
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "dict_name"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "dict_key"));
                });
            }
        }
        return queryWrapper;
    }

    private QueryWrapper<Dictionary> createQueryWrapper(QueryWrapper<Dictionary> queryWrapper, Boolean useOr, DictionaryCriteria criteria) {
        if (criteria != null) {
            if (useOr == null) {
                useOr = false;
            }
            if (criteria.getId() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getId(), "id", useOr));
            }
            if (criteria.getDictName() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getDictName(), "dict_name", useOr));
            }
            if (criteria.getDictKey() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getDictKey(), "dict_key", useOr));
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
            if (criteria.getSyncEnum() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getSyncEnum(), "sync_enum", useOr));
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
     * Return a {@link IPage} of {@link DictionaryDTO} which matches the criteria from the database.
     * @param queryWrapper The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    public IPage<DictionaryDTO> findByQueryWrapper(QueryWrapper<Dictionary> queryWrapper, Page<Dictionary> page) {
        log.debug("find by criteria : {}, page: {}", queryWrapper, page);
        return dictionaryRepository.selectPage(page, queryWrapper).convert(dictionaryMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(DictionaryCriteria criteria) {
        QueryWrapper<Dictionary> queryWrapper = createQueryWrapper(criteria);
        List<String> selectFields = new ArrayList<>();
        List<String> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields);
        }
        if (criteria.getDictName() != null) {
            getAggregateAndGroupBy(criteria.getDictName(), "dict_name", selectFields, groupByFields);
        }
        if (criteria.getDictKey() != null) {
            getAggregateAndGroupBy(criteria.getDictKey(), "dict_key", selectFields, groupByFields);
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
        if (criteria.getSyncEnum() != null) {
            getAggregateAndGroupBy(criteria.getSyncEnum(), "sync_enum", selectFields, groupByFields);
        }
        if (CollectionUtils.isNotEmpty(selectFields)) {
            queryWrapper.select(selectFields.toArray(new String[0])).groupBy(CollectionUtils.isNotEmpty(groupByFields), groupByFields);
            return dictionaryRepository.selectMaps(queryWrapper);
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
