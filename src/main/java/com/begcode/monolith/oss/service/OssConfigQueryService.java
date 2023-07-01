package com.begcode.monolith.oss.service;

import static tech.jhipster.service.mybatis.AggregateUtil.buildAggregate;
import static tech.jhipster.service.mybatis.AggregateUtil.buildGroupBy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.oss.domain.*; // for static metamodels
import com.begcode.monolith.oss.domain.OssConfig;
import com.begcode.monolith.oss.repository.OssConfigRepository;
import com.begcode.monolith.oss.service.criteria.OssConfigCriteria;
import com.begcode.monolith.oss.service.dto.OssConfigDTO;
import com.begcode.monolith.oss.service.mapper.OssConfigMapper;
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
 * 用于对数据库中的{@link OssConfig}实体执行复杂查询的Service。
 * 主要输入是一个{@link OssConfigCriteria}，它被转换为{@link QueryWrapper}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link OssConfigDTO}列表{@link List} 或 {@link OssConfigDTO} 的分页列表 {@link Page}。
 */
@Service
public class OssConfigQueryService implements QueryService<OssConfig> {

    private final Logger log = LoggerFactory.getLogger(OssConfigQueryService.class);

    protected final OssConfigRepository ossConfigRepository;

    protected final OssConfigMapper ossConfigMapper;

    public OssConfigQueryService(OssConfigRepository ossConfigRepository, OssConfigMapper ossConfigMapper) {
        this.ossConfigRepository = ossConfigRepository;
        this.ossConfigMapper = ossConfigMapper;
    }

    /**
     * Return a {@link List} of {@link OssConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */

    public List<OssConfigDTO> findByCriteria(OssConfigCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final QueryWrapper<OssConfig> queryWrapper = createQueryWrapper(criteria);
        return ossConfigMapper.toDto(ossConfigRepository.selectList(queryWrapper));
    }

    /**
     * Return a {@link IPage} of {@link OssConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */

    public IPage<OssConfigDTO> findByCriteria(OssConfigCriteria criteria, Page<OssConfig> page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final QueryWrapper<OssConfig> queryWrapper = createQueryWrapper(criteria);
        return ossConfigRepository.selectPage(page, queryWrapper).convert(ossConfigMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */

    public long countByCriteria(OssConfigCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final QueryWrapper<OssConfig> queryWrapper = createQueryWrapper(criteria);
        return ossConfigRepository.selectCount(queryWrapper);
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, OssConfigCriteria criteria) {
        return (List<T>) ossConfigRepository.selectObjs(createQueryWrapper(criteria).select(fieldName));
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, OssConfigCriteria criteria) {
        return ossConfigRepository.selectCount(createQueryWrapper(criteria));
    }

    /**
     * Function to convert {@link OssConfigCriteria} to a {@link QueryWrapper}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link QueryWrapper} of the entity.
     */
    protected QueryWrapper<OssConfig> createQueryWrapper(OssConfigCriteria criteria) {
        QueryWrapper<OssConfig> queryWrapper = new DynamicJoinQueryWrapper<>(OssConfig.class, null);
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
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "platform"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "remark"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "config_data"));
                });
            }
        }
        return queryWrapper;
    }

    private QueryWrapper<OssConfig> createQueryWrapper(QueryWrapper<OssConfig> queryWrapper, Boolean useOr, OssConfigCriteria criteria) {
        if (criteria != null) {
            if (useOr == null) {
                useOr = false;
            }
            if (criteria.getId() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getId(), "id", useOr));
            }
            if (criteria.getProvider() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getProvider(), "provider", useOr));
            }
            if (criteria.getPlatform() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getPlatform(), "platform", useOr));
            }
            if (criteria.getEnabled() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getEnabled(), "enabled", useOr));
            }
            if (criteria.getRemark() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getRemark(), "remark", useOr));
            }
            if (criteria.getConfigData() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getConfigData(), "config_data", useOr));
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
     * Return a {@link IPage} of {@link OssConfigDTO} which matches the criteria from the database.
     * @param queryWrapper The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    public IPage<OssConfigDTO> findByQueryWrapper(QueryWrapper<OssConfig> queryWrapper, Page<OssConfig> page) {
        log.debug("find by criteria : {}, page: {}", queryWrapper, page);
        return ossConfigRepository.selectPage(page, queryWrapper).convert(ossConfigMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(OssConfigCriteria criteria) {
        QueryWrapper<OssConfig> queryWrapper = createQueryWrapper(criteria);
        List<String> selectFields = new ArrayList<>();
        List<String> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields);
        }
        if (criteria.getProvider() != null) {
            getAggregateAndGroupBy(criteria.getProvider(), "provider", selectFields, groupByFields);
        }
        if (criteria.getPlatform() != null) {
            getAggregateAndGroupBy(criteria.getPlatform(), "platform", selectFields, groupByFields);
        }
        if (criteria.getEnabled() != null) {
            getAggregateAndGroupBy(criteria.getEnabled(), "enabled", selectFields, groupByFields);
        }
        if (criteria.getRemark() != null) {
            getAggregateAndGroupBy(criteria.getRemark(), "remark", selectFields, groupByFields);
        }
        if (criteria.getConfigData() != null) {
            getAggregateAndGroupBy(criteria.getConfigData(), "config_data", selectFields, groupByFields);
        }
        if (CollectionUtils.isNotEmpty(selectFields)) {
            queryWrapper.select(selectFields.toArray(new String[0])).groupBy(CollectionUtils.isNotEmpty(groupByFields), groupByFields);
            return ossConfigRepository.selectMaps(queryWrapper);
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
