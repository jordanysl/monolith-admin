package com.begcode.monolith.system.service;

import static tech.jhipster.service.mybatis.AggregateUtil.buildAggregate;
import static tech.jhipster.service.mybatis.AggregateUtil.buildGroupBy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.system.domain.*; // for static metamodels
import com.begcode.monolith.system.domain.SmsSupplier;
import com.begcode.monolith.system.repository.SmsSupplierRepository;
import com.begcode.monolith.system.service.criteria.SmsSupplierCriteria;
import com.begcode.monolith.system.service.dto.SmsSupplierDTO;
import com.begcode.monolith.system.service.mapper.SmsSupplierMapper;
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
 * 用于对数据库中的{@link SmsSupplier}实体执行复杂查询的Service。
 * 主要输入是一个{@link SmsSupplierCriteria}，它被转换为{@link QueryWrapper}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link SmsSupplierDTO}列表{@link List} 或 {@link SmsSupplierDTO} 的分页列表 {@link Page}。
 */
@Service
public class SmsSupplierQueryService implements QueryService<SmsSupplier> {

    private final Logger log = LoggerFactory.getLogger(SmsSupplierQueryService.class);

    protected final SmsSupplierRepository smsSupplierRepository;

    protected final SmsSupplierMapper smsSupplierMapper;

    public SmsSupplierQueryService(SmsSupplierRepository smsSupplierRepository, SmsSupplierMapper smsSupplierMapper) {
        this.smsSupplierRepository = smsSupplierRepository;
        this.smsSupplierMapper = smsSupplierMapper;
    }

    /**
     * Return a {@link List} of {@link SmsSupplierDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */

    public List<SmsSupplierDTO> findByCriteria(SmsSupplierCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final QueryWrapper<SmsSupplier> queryWrapper = createQueryWrapper(criteria);
        return smsSupplierMapper.toDto(smsSupplierRepository.selectList(queryWrapper));
    }

    /**
     * Return a {@link IPage} of {@link SmsSupplierDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */

    public IPage<SmsSupplierDTO> findByCriteria(SmsSupplierCriteria criteria, Page<SmsSupplier> page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final QueryWrapper<SmsSupplier> queryWrapper = createQueryWrapper(criteria);
        return smsSupplierRepository.selectPage(page, queryWrapper).convert(smsSupplierMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */

    public long countByCriteria(SmsSupplierCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final QueryWrapper<SmsSupplier> queryWrapper = createQueryWrapper(criteria);
        return smsSupplierRepository.selectCount(queryWrapper);
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, SmsSupplierCriteria criteria) {
        return (List<T>) smsSupplierRepository.selectObjs(createQueryWrapper(criteria).select(fieldName));
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, SmsSupplierCriteria criteria) {
        return smsSupplierRepository.selectCount(createQueryWrapper(criteria));
    }

    /**
     * Function to convert {@link SmsSupplierCriteria} to a {@link QueryWrapper}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link QueryWrapper} of the entity.
     */
    protected QueryWrapper<SmsSupplier> createQueryWrapper(SmsSupplierCriteria criteria) {
        QueryWrapper<SmsSupplier> queryWrapper = new DynamicJoinQueryWrapper<>(SmsSupplier.class, null);
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
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "config_data"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "sign_name"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "remark"));
                });
            }
        }
        return queryWrapper;
    }

    private QueryWrapper<SmsSupplier> createQueryWrapper(
        QueryWrapper<SmsSupplier> queryWrapper,
        Boolean useOr,
        SmsSupplierCriteria criteria
    ) {
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
            if (criteria.getConfigData() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getConfigData(), "config_data", useOr));
            }
            if (criteria.getSignName() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getSignName(), "sign_name", useOr));
            }
            if (criteria.getRemark() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getRemark(), "remark", useOr));
            }
            if (criteria.getEnabled() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getEnabled(), "enabled", useOr));
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
     * Return a {@link IPage} of {@link SmsSupplierDTO} which matches the criteria from the database.
     * @param queryWrapper The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    public IPage<SmsSupplierDTO> findByQueryWrapper(QueryWrapper<SmsSupplier> queryWrapper, Page<SmsSupplier> page) {
        log.debug("find by criteria : {}, page: {}", queryWrapper, page);
        return smsSupplierRepository.selectPage(page, queryWrapper).convert(smsSupplierMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(SmsSupplierCriteria criteria) {
        QueryWrapper<SmsSupplier> queryWrapper = createQueryWrapper(criteria);
        List<String> selectFields = new ArrayList<>();
        List<String> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields);
        }
        if (criteria.getProvider() != null) {
            getAggregateAndGroupBy(criteria.getProvider(), "provider", selectFields, groupByFields);
        }
        if (criteria.getConfigData() != null) {
            getAggregateAndGroupBy(criteria.getConfigData(), "config_data", selectFields, groupByFields);
        }
        if (criteria.getSignName() != null) {
            getAggregateAndGroupBy(criteria.getSignName(), "sign_name", selectFields, groupByFields);
        }
        if (criteria.getRemark() != null) {
            getAggregateAndGroupBy(criteria.getRemark(), "remark", selectFields, groupByFields);
        }
        if (criteria.getEnabled() != null) {
            getAggregateAndGroupBy(criteria.getEnabled(), "enabled", selectFields, groupByFields);
        }
        if (CollectionUtils.isNotEmpty(selectFields)) {
            queryWrapper.select(selectFields.toArray(new String[0])).groupBy(CollectionUtils.isNotEmpty(groupByFields), groupByFields);
            return smsSupplierRepository.selectMaps(queryWrapper);
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
