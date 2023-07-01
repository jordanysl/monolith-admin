package com.begcode.monolith.service;

import static tech.jhipster.service.mybatis.AggregateUtil.buildAggregate;
import static tech.jhipster.service.mybatis.AggregateUtil.buildGroupBy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.domain.*; // for static metamodels
import com.begcode.monolith.domain.UReportFile;
import com.begcode.monolith.repository.UReportFileRepository;
import com.begcode.monolith.service.criteria.UReportFileCriteria;
import com.begcode.monolith.service.dto.UReportFileDTO;
import com.begcode.monolith.service.mapper.UReportFileMapper;
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
 * 用于对数据库中的{@link UReportFile}实体执行复杂查询的Service。
 * 主要输入是一个{@link UReportFileCriteria}，它被转换为{@link QueryWrapper}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link UReportFileDTO}列表{@link List} 或 {@link UReportFileDTO} 的分页列表 {@link Page}。
 */
@Service
public class UReportFileQueryService implements QueryService<UReportFile> {

    private final Logger log = LoggerFactory.getLogger(UReportFileQueryService.class);

    protected final UReportFileRepository uReportFileRepository;

    protected final UReportFileMapper uReportFileMapper;

    public UReportFileQueryService(UReportFileRepository uReportFileRepository, UReportFileMapper uReportFileMapper) {
        this.uReportFileRepository = uReportFileRepository;
        this.uReportFileMapper = uReportFileMapper;
    }

    /**
     * Return a {@link List} of {@link UReportFileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */

    public List<UReportFileDTO> findByCriteria(UReportFileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final QueryWrapper<UReportFile> queryWrapper = createQueryWrapper(criteria);
        return uReportFileMapper.toDto(uReportFileRepository.selectList(queryWrapper));
    }

    /**
     * Return a {@link IPage} of {@link UReportFileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */

    public IPage<UReportFileDTO> findByCriteria(UReportFileCriteria criteria, Page<UReportFile> page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final QueryWrapper<UReportFile> queryWrapper = createQueryWrapper(criteria);
        return uReportFileRepository.selectPage(page, queryWrapper).convert(uReportFileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */

    public long countByCriteria(UReportFileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final QueryWrapper<UReportFile> queryWrapper = createQueryWrapper(criteria);
        return uReportFileRepository.selectCount(queryWrapper);
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, UReportFileCriteria criteria) {
        return (List<T>) uReportFileRepository.selectObjs(createQueryWrapper(criteria).select(fieldName));
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, UReportFileCriteria criteria) {
        return uReportFileRepository.selectCount(createQueryWrapper(criteria));
    }

    /**
     * Function to convert {@link UReportFileCriteria} to a {@link QueryWrapper}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link QueryWrapper} of the entity.
     */
    protected QueryWrapper<UReportFile> createQueryWrapper(UReportFileCriteria criteria) {
        QueryWrapper<UReportFile> queryWrapper = new DynamicJoinQueryWrapper<>(UReportFile.class, null);
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
                });
            }
        }
        return queryWrapper;
    }

    private QueryWrapper<UReportFile> createQueryWrapper(
        QueryWrapper<UReportFile> queryWrapper,
        Boolean useOr,
        UReportFileCriteria criteria
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
            if (criteria.getCreateAt() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getCreateAt(), "create_at", useOr));
            }
            if (criteria.getUpdateAt() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getUpdateAt(), "update_at", useOr));
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
     * Return a {@link IPage} of {@link UReportFileDTO} which matches the criteria from the database.
     * @param queryWrapper The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    public IPage<UReportFileDTO> findByQueryWrapper(QueryWrapper<UReportFile> queryWrapper, Page<UReportFile> page) {
        log.debug("find by criteria : {}, page: {}", queryWrapper, page);
        return uReportFileRepository.selectPage(page, queryWrapper).convert(uReportFileMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(UReportFileCriteria criteria) {
        QueryWrapper<UReportFile> queryWrapper = createQueryWrapper(criteria);
        List<String> selectFields = new ArrayList<>();
        List<String> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields);
        }
        if (criteria.getName() != null) {
            getAggregateAndGroupBy(criteria.getName(), "name", selectFields, groupByFields);
        }
        if (criteria.getCreateAt() != null) {
            getAggregateAndGroupBy(criteria.getCreateAt(), "create_at", selectFields, groupByFields);
        }
        if (criteria.getUpdateAt() != null) {
            getAggregateAndGroupBy(criteria.getUpdateAt(), "update_at", selectFields, groupByFields);
        }
        if (CollectionUtils.isNotEmpty(selectFields)) {
            queryWrapper.select(selectFields.toArray(new String[0])).groupBy(CollectionUtils.isNotEmpty(groupByFields), groupByFields);
            return uReportFileRepository.selectMaps(queryWrapper);
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
