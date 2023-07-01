package com.begcode.monolith.system.service;

import static tech.jhipster.service.mybatis.AggregateUtil.buildAggregate;
import static tech.jhipster.service.mybatis.AggregateUtil.buildGroupBy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.system.domain.*; // for static metamodels
import com.begcode.monolith.system.domain.Announcement;
import com.begcode.monolith.system.repository.AnnouncementRepository;
import com.begcode.monolith.system.service.criteria.AnnouncementCriteria;
import com.begcode.monolith.system.service.dto.AnnouncementDTO;
import com.begcode.monolith.system.service.mapper.AnnouncementMapper;
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
 * 用于对数据库中的{@link Announcement}实体执行复杂查询的Service。
 * 主要输入是一个{@link AnnouncementCriteria}，它被转换为{@link QueryWrapper}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link AnnouncementDTO}列表{@link List} 或 {@link AnnouncementDTO} 的分页列表 {@link Page}。
 */
@Service
public class AnnouncementQueryService implements QueryService<Announcement> {

    private final Logger log = LoggerFactory.getLogger(AnnouncementQueryService.class);

    protected final AnnouncementRepository announcementRepository;

    protected final AnnouncementMapper announcementMapper;

    public AnnouncementQueryService(AnnouncementRepository announcementRepository, AnnouncementMapper announcementMapper) {
        this.announcementRepository = announcementRepository;
        this.announcementMapper = announcementMapper;
    }

    /**
     * Return a {@link List} of {@link AnnouncementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */

    public List<AnnouncementDTO> findByCriteria(AnnouncementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final QueryWrapper<Announcement> queryWrapper = createQueryWrapper(criteria);
        return announcementMapper.toDto(announcementRepository.selectList(queryWrapper));
    }

    /**
     * Return a {@link IPage} of {@link AnnouncementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */

    public IPage<AnnouncementDTO> findByCriteria(AnnouncementCriteria criteria, Page<Announcement> page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final QueryWrapper<Announcement> queryWrapper = createQueryWrapper(criteria);
        return announcementRepository.selectPage(page, queryWrapper).convert(announcementMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */

    public long countByCriteria(AnnouncementCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final QueryWrapper<Announcement> queryWrapper = createQueryWrapper(criteria);
        return announcementRepository.selectCount(queryWrapper);
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, AnnouncementCriteria criteria) {
        return (List<T>) announcementRepository.selectObjs(createQueryWrapper(criteria).select(fieldName));
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, AnnouncementCriteria criteria) {
        return announcementRepository.selectCount(createQueryWrapper(criteria));
    }

    /**
     * Function to convert {@link AnnouncementCriteria} to a {@link QueryWrapper}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link QueryWrapper} of the entity.
     */
    protected QueryWrapper<Announcement> createQueryWrapper(AnnouncementCriteria criteria) {
        QueryWrapper<Announcement> queryWrapper = new DynamicJoinQueryWrapper<>(Announcement.class, null);
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
                            "sender_id"
                        )
                    );
                    q.or(
                        buildRangeSpecification(
                            (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                            "business_id"
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
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "title"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "open_page"));
                });
            }
        }
        return queryWrapper;
    }

    private QueryWrapper<Announcement> createQueryWrapper(
        QueryWrapper<Announcement> queryWrapper,
        Boolean useOr,
        AnnouncementCriteria criteria
    ) {
        if (criteria != null) {
            if (useOr == null) {
                useOr = false;
            }
            if (criteria.getId() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getId(), "id", useOr));
            }
            if (criteria.getTitle() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getTitle(), "title", useOr));
            }
            if (criteria.getStartTime() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getStartTime(), "start_time", useOr));
            }
            if (criteria.getEndTime() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getEndTime(), "end_time", useOr));
            }
            if (criteria.getSenderId() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getSenderId(), "sender_id", useOr));
            }
            if (criteria.getPriority() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getPriority(), "priority", useOr));
            }
            if (criteria.getCategory() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getCategory(), "category", useOr));
            }
            if (criteria.getReceiverType() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getReceiverType(), "receiver_type", useOr));
            }
            if (criteria.getSendStatus() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getSendStatus(), "send_status", useOr));
            }
            if (criteria.getSendTime() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getSendTime(), "send_time", useOr));
            }
            if (criteria.getCancelTime() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getCancelTime(), "cancel_time", useOr));
            }
            if (criteria.getBusinessType() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getBusinessType(), "business_type", useOr));
            }
            if (criteria.getBusinessId() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getBusinessId(), "business_id", useOr));
            }
            if (criteria.getOpenType() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getOpenType(), "open_type", useOr));
            }
            if (criteria.getOpenPage() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getOpenPage(), "open_page", useOr));
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
     * Return a {@link IPage} of {@link AnnouncementDTO} which matches the criteria from the database.
     * @param queryWrapper The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    public IPage<AnnouncementDTO> findByQueryWrapper(QueryWrapper<Announcement> queryWrapper, Page<Announcement> page) {
        log.debug("find by criteria : {}, page: {}", queryWrapper, page);
        return announcementRepository.selectPage(page, queryWrapper).convert(announcementMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(AnnouncementCriteria criteria) {
        QueryWrapper<Announcement> queryWrapper = createQueryWrapper(criteria);
        List<String> selectFields = new ArrayList<>();
        List<String> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields);
        }
        if (criteria.getTitle() != null) {
            getAggregateAndGroupBy(criteria.getTitle(), "title", selectFields, groupByFields);
        }
        if (criteria.getStartTime() != null) {
            getAggregateAndGroupBy(criteria.getStartTime(), "start_time", selectFields, groupByFields);
        }
        if (criteria.getEndTime() != null) {
            getAggregateAndGroupBy(criteria.getEndTime(), "end_time", selectFields, groupByFields);
        }
        if (criteria.getSenderId() != null) {
            getAggregateAndGroupBy(criteria.getSenderId(), "sender_id", selectFields, groupByFields);
        }
        if (criteria.getPriority() != null) {
            getAggregateAndGroupBy(criteria.getPriority(), "priority", selectFields, groupByFields);
        }
        if (criteria.getCategory() != null) {
            getAggregateAndGroupBy(criteria.getCategory(), "category", selectFields, groupByFields);
        }
        if (criteria.getReceiverType() != null) {
            getAggregateAndGroupBy(criteria.getReceiverType(), "receiver_type", selectFields, groupByFields);
        }
        if (criteria.getSendStatus() != null) {
            getAggregateAndGroupBy(criteria.getSendStatus(), "send_status", selectFields, groupByFields);
        }
        if (criteria.getSendTime() != null) {
            getAggregateAndGroupBy(criteria.getSendTime(), "send_time", selectFields, groupByFields);
        }
        if (criteria.getCancelTime() != null) {
            getAggregateAndGroupBy(criteria.getCancelTime(), "cancel_time", selectFields, groupByFields);
        }
        if (criteria.getBusinessType() != null) {
            getAggregateAndGroupBy(criteria.getBusinessType(), "business_type", selectFields, groupByFields);
        }
        if (criteria.getBusinessId() != null) {
            getAggregateAndGroupBy(criteria.getBusinessId(), "business_id", selectFields, groupByFields);
        }
        if (criteria.getOpenType() != null) {
            getAggregateAndGroupBy(criteria.getOpenType(), "open_type", selectFields, groupByFields);
        }
        if (criteria.getOpenPage() != null) {
            getAggregateAndGroupBy(criteria.getOpenPage(), "open_page", selectFields, groupByFields);
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
            return announcementRepository.selectMaps(queryWrapper);
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
