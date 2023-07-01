package com.begcode.monolith.system.service;

import static tech.jhipster.service.mybatis.AggregateUtil.buildAggregate;
import static tech.jhipster.service.mybatis.AggregateUtil.buildGroupBy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.system.domain.*; // for static metamodels
import com.begcode.monolith.system.domain.AnnouncementRecord;
import com.begcode.monolith.system.repository.AnnouncementRecordRepository;
import com.begcode.monolith.system.service.criteria.AnnouncementRecordCriteria;
import com.begcode.monolith.system.service.dto.AnnouncementRecordDTO;
import com.begcode.monolith.system.service.mapper.AnnouncementRecordMapper;
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
import tech.jhipster.service.filter.ZonedDateTimeFilter;
import tech.jhipster.service.mybatis.CriteriaUtil;
import tech.jhipster.service.mybatis.QueryService;

/**
 * 用于对数据库中的{@link AnnouncementRecord}实体执行复杂查询的Service。
 * 主要输入是一个{@link AnnouncementRecordCriteria}，它被转换为{@link QueryWrapper}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link AnnouncementRecordDTO}列表{@link List} 或 {@link AnnouncementRecordDTO} 的分页列表 {@link Page}。
 */
@Service
public class AnnouncementRecordQueryService implements QueryService<AnnouncementRecord> {

    private final Logger log = LoggerFactory.getLogger(AnnouncementRecordQueryService.class);

    protected final AnnouncementRecordRepository announcementRecordRepository;

    protected final AnnouncementRecordMapper announcementRecordMapper;

    public AnnouncementRecordQueryService(
        AnnouncementRecordRepository announcementRecordRepository,
        AnnouncementRecordMapper announcementRecordMapper
    ) {
        this.announcementRecordRepository = announcementRecordRepository;
        this.announcementRecordMapper = announcementRecordMapper;
    }

    /**
     * Return a {@link List} of {@link AnnouncementRecordDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */

    public List<AnnouncementRecordDTO> findByCriteria(AnnouncementRecordCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final QueryWrapper<AnnouncementRecord> queryWrapper = createQueryWrapper(criteria);
        return announcementRecordMapper.toDto(announcementRecordRepository.selectList(queryWrapper));
    }

    /**
     * Return a {@link IPage} of {@link AnnouncementRecordDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */

    public IPage<AnnouncementRecordDTO> findByCriteria(AnnouncementRecordCriteria criteria, Page<AnnouncementRecord> page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final QueryWrapper<AnnouncementRecord> queryWrapper = createQueryWrapper(criteria);
        return announcementRecordRepository.selectPage(page, queryWrapper).convert(announcementRecordMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */

    public long countByCriteria(AnnouncementRecordCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final QueryWrapper<AnnouncementRecord> queryWrapper = createQueryWrapper(criteria);
        return announcementRecordRepository.selectCount(queryWrapper);
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, AnnouncementRecordCriteria criteria) {
        return (List<T>) announcementRecordRepository.selectObjs(createQueryWrapper(criteria).select(fieldName));
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, AnnouncementRecordCriteria criteria) {
        return announcementRecordRepository.selectCount(createQueryWrapper(criteria));
    }

    /**
     * Function to convert {@link AnnouncementRecordCriteria} to a {@link QueryWrapper}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link QueryWrapper} of the entity.
     */
    protected QueryWrapper<AnnouncementRecord> createQueryWrapper(AnnouncementRecordCriteria criteria) {
        QueryWrapper<AnnouncementRecord> queryWrapper = new DynamicJoinQueryWrapper<>(AnnouncementRecord.class, null);
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
                            "annt_id"
                        )
                    );
                    q.or(
                        buildRangeSpecification(
                            (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                            "user_id"
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
                queryWrapper.and(q -> {});
            }
        }
        return queryWrapper;
    }

    private QueryWrapper<AnnouncementRecord> createQueryWrapper(
        QueryWrapper<AnnouncementRecord> queryWrapper,
        Boolean useOr,
        AnnouncementRecordCriteria criteria
    ) {
        if (criteria != null) {
            if (useOr == null) {
                useOr = false;
            }
            if (criteria.getId() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getId(), "id", useOr));
            }
            if (criteria.getAnntId() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getAnntId(), "annt_id", useOr));
            }
            if (criteria.getUserId() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getUserId(), "user_id", useOr));
            }
            if (criteria.getHasRead() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getHasRead(), "has_read", useOr));
            }
            if (criteria.getReadTime() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getReadTime(), "read_time", useOr));
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
     * Return a {@link IPage} of {@link AnnouncementRecordDTO} which matches the criteria from the database.
     * @param queryWrapper The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    public IPage<AnnouncementRecordDTO> findByQueryWrapper(QueryWrapper<AnnouncementRecord> queryWrapper, Page<AnnouncementRecord> page) {
        log.debug("find by criteria : {}, page: {}", queryWrapper, page);
        return announcementRecordRepository.selectPage(page, queryWrapper).convert(announcementRecordMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(AnnouncementRecordCriteria criteria) {
        QueryWrapper<AnnouncementRecord> queryWrapper = createQueryWrapper(criteria);
        List<String> selectFields = new ArrayList<>();
        List<String> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields);
        }
        if (criteria.getAnntId() != null) {
            getAggregateAndGroupBy(criteria.getAnntId(), "annt_id", selectFields, groupByFields);
        }
        if (criteria.getUserId() != null) {
            getAggregateAndGroupBy(criteria.getUserId(), "user_id", selectFields, groupByFields);
        }
        if (criteria.getHasRead() != null) {
            getAggregateAndGroupBy(criteria.getHasRead(), "has_read", selectFields, groupByFields);
        }
        if (criteria.getReadTime() != null) {
            getAggregateAndGroupBy(criteria.getReadTime(), "read_time", selectFields, groupByFields);
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
            return announcementRecordRepository.selectMaps(queryWrapper);
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
