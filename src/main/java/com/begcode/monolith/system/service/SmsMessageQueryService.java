package com.begcode.monolith.system.service;

import static tech.jhipster.service.mybatis.AggregateUtil.buildAggregate;
import static tech.jhipster.service.mybatis.AggregateUtil.buildGroupBy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.system.domain.*; // for static metamodels
import com.begcode.monolith.system.domain.SmsMessage;
import com.begcode.monolith.system.repository.SmsMessageRepository;
import com.begcode.monolith.system.service.criteria.SmsMessageCriteria;
import com.begcode.monolith.system.service.dto.SmsMessageDTO;
import com.begcode.monolith.system.service.mapper.SmsMessageMapper;
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
 * 用于对数据库中的{@link SmsMessage}实体执行复杂查询的Service。
 * 主要输入是一个{@link SmsMessageCriteria}，它被转换为{@link QueryWrapper}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link SmsMessageDTO}列表{@link List} 或 {@link SmsMessageDTO} 的分页列表 {@link Page}。
 */
@Service
public class SmsMessageQueryService implements QueryService<SmsMessage> {

    private final Logger log = LoggerFactory.getLogger(SmsMessageQueryService.class);

    protected final SmsMessageRepository smsMessageRepository;

    protected final SmsMessageMapper smsMessageMapper;

    public SmsMessageQueryService(SmsMessageRepository smsMessageRepository, SmsMessageMapper smsMessageMapper) {
        this.smsMessageRepository = smsMessageRepository;
        this.smsMessageMapper = smsMessageMapper;
    }

    /**
     * Return a {@link List} of {@link SmsMessageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */

    public List<SmsMessageDTO> findByCriteria(SmsMessageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final QueryWrapper<SmsMessage> queryWrapper = createQueryWrapper(criteria);
        return smsMessageMapper.toDto(smsMessageRepository.selectList(queryWrapper));
    }

    /**
     * Return a {@link IPage} of {@link SmsMessageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */

    public IPage<SmsMessageDTO> findByCriteria(SmsMessageCriteria criteria, Page<SmsMessage> page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final QueryWrapper<SmsMessage> queryWrapper = createQueryWrapper(criteria);
        return smsMessageRepository.selectPage(page, queryWrapper).convert(smsMessageMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */

    public long countByCriteria(SmsMessageCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final QueryWrapper<SmsMessage> queryWrapper = createQueryWrapper(criteria);
        return smsMessageRepository.selectCount(queryWrapper);
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, SmsMessageCriteria criteria) {
        return (List<T>) smsMessageRepository.selectObjs(createQueryWrapper(criteria).select(fieldName));
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, SmsMessageCriteria criteria) {
        return smsMessageRepository.selectCount(createQueryWrapper(criteria));
    }

    /**
     * Function to convert {@link SmsMessageCriteria} to a {@link QueryWrapper}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link QueryWrapper} of the entity.
     */
    protected QueryWrapper<SmsMessage> createQueryWrapper(SmsMessageCriteria criteria) {
        QueryWrapper<SmsMessage> queryWrapper = new DynamicJoinQueryWrapper<>(SmsMessage.class, null);
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
                            "retry_num"
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
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "receiver"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "params"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "fail_result"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "remark"));
                });
            }
        }
        return queryWrapper;
    }

    private QueryWrapper<SmsMessage> createQueryWrapper(QueryWrapper<SmsMessage> queryWrapper, Boolean useOr, SmsMessageCriteria criteria) {
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
            if (criteria.getSendType() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getSendType(), "send_type", useOr));
            }
            if (criteria.getReceiver() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getReceiver(), "receiver", useOr));
            }
            if (criteria.getParams() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getParams(), "params", useOr));
            }
            if (criteria.getSendTime() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getSendTime(), "send_time", useOr));
            }
            if (criteria.getSendStatus() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getSendStatus(), "send_status", useOr));
            }
            if (criteria.getRetryNum() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getRetryNum(), "retry_num", useOr));
            }
            if (criteria.getFailResult() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getFailResult(), "fail_result", useOr));
            }
            if (criteria.getRemark() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getRemark(), "remark", useOr));
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
     * Return a {@link IPage} of {@link SmsMessageDTO} which matches the criteria from the database.
     * @param queryWrapper The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    public IPage<SmsMessageDTO> findByQueryWrapper(QueryWrapper<SmsMessage> queryWrapper, Page<SmsMessage> page) {
        log.debug("find by criteria : {}, page: {}", queryWrapper, page);
        return smsMessageRepository.selectPage(page, queryWrapper).convert(smsMessageMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(SmsMessageCriteria criteria) {
        QueryWrapper<SmsMessage> queryWrapper = createQueryWrapper(criteria);
        List<String> selectFields = new ArrayList<>();
        List<String> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields);
        }
        if (criteria.getTitle() != null) {
            getAggregateAndGroupBy(criteria.getTitle(), "title", selectFields, groupByFields);
        }
        if (criteria.getSendType() != null) {
            getAggregateAndGroupBy(criteria.getSendType(), "send_type", selectFields, groupByFields);
        }
        if (criteria.getReceiver() != null) {
            getAggregateAndGroupBy(criteria.getReceiver(), "receiver", selectFields, groupByFields);
        }
        if (criteria.getParams() != null) {
            getAggregateAndGroupBy(criteria.getParams(), "params", selectFields, groupByFields);
        }
        if (criteria.getSendTime() != null) {
            getAggregateAndGroupBy(criteria.getSendTime(), "send_time", selectFields, groupByFields);
        }
        if (criteria.getSendStatus() != null) {
            getAggregateAndGroupBy(criteria.getSendStatus(), "send_status", selectFields, groupByFields);
        }
        if (criteria.getRetryNum() != null) {
            getAggregateAndGroupBy(criteria.getRetryNum(), "retry_num", selectFields, groupByFields);
        }
        if (criteria.getFailResult() != null) {
            getAggregateAndGroupBy(criteria.getFailResult(), "fail_result", selectFields, groupByFields);
        }
        if (criteria.getRemark() != null) {
            getAggregateAndGroupBy(criteria.getRemark(), "remark", selectFields, groupByFields);
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
            return smsMessageRepository.selectMaps(queryWrapper);
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
