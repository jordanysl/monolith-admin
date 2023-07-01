package com.begcode.monolith.log.service;

import static tech.jhipster.service.mybatis.AggregateUtil.buildAggregate;
import static tech.jhipster.service.mybatis.AggregateUtil.buildGroupBy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.domain.enumeration.LogType;
import com.begcode.monolith.log.domain.*; // for static metamodels
import com.begcode.monolith.log.domain.SysLog;
import com.begcode.monolith.log.repository.SysLogRepository;
import com.begcode.monolith.log.service.criteria.SysLogCriteria;
import com.begcode.monolith.log.service.dto.SysLogDTO;
import com.begcode.monolith.log.service.mapper.SysLogMapper;
import com.diboot.core.binding.query.dynamic.DynamicJoinQueryWrapper;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
 * 用于对数据库中的{@link SysLog}实体执行复杂查询的Service。
 * 主要输入是一个{@link SysLogCriteria}，它被转换为{@link QueryWrapper}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link SysLogDTO}列表{@link List} 或 {@link SysLogDTO} 的分页列表 {@link Page}。
 */
@Service
public class SysLogQueryService implements QueryService<SysLog> {

    private final Logger log = LoggerFactory.getLogger(SysLogQueryService.class);

    protected final SysLogRepository sysLogRepository;

    protected final SysLogMapper sysLogMapper;

    public SysLogQueryService(SysLogRepository sysLogRepository, SysLogMapper sysLogMapper) {
        this.sysLogRepository = sysLogRepository;
        this.sysLogMapper = sysLogMapper;
    }

    /**
     * Return a {@link List} of {@link SysLogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */

    public List<SysLogDTO> findByCriteria(SysLogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final QueryWrapper<SysLog> queryWrapper = createQueryWrapper(criteria);
        return sysLogMapper.toDto(sysLogRepository.selectList(queryWrapper));
    }

    /**
     * Return a {@link IPage} of {@link SysLogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */

    public IPage<SysLogDTO> findByCriteria(SysLogCriteria criteria, Page<SysLog> page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final QueryWrapper<SysLog> queryWrapper = createQueryWrapper(criteria);
        return sysLogRepository.selectPage(page, queryWrapper).convert(sysLogMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */

    public long countByCriteria(SysLogCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final QueryWrapper<SysLog> queryWrapper = createQueryWrapper(criteria);
        return sysLogRepository.selectCount(queryWrapper);
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, SysLogCriteria criteria) {
        return (List<T>) sysLogRepository.selectObjs(createQueryWrapper(criteria).select(fieldName));
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, SysLogCriteria criteria) {
        return sysLogRepository.selectCount(createQueryWrapper(criteria));
    }

    /**
     * Function to convert {@link SysLogCriteria} to a {@link QueryWrapper}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link QueryWrapper} of the entity.
     */
    protected QueryWrapper<SysLog> createQueryWrapper(SysLogCriteria criteria) {
        QueryWrapper<SysLog> queryWrapper = new DynamicJoinQueryWrapper<>(SysLog.class, null);
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
                            "cost_time"
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
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "log_content"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "userid"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "username"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "ip"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "method"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "request_url"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "request_type"));
                });
            }
        }
        return queryWrapper;
    }

    private QueryWrapper<SysLog> createQueryWrapper(QueryWrapper<SysLog> queryWrapper, Boolean useOr, SysLogCriteria criteria) {
        if (criteria != null) {
            if (useOr == null) {
                useOr = false;
            }
            if (criteria.getId() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getId(), "id", useOr));
            }
            if (criteria.getLogType() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getLogType(), "log_type", useOr));
            }
            if (criteria.getLogContent() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getLogContent(), "log_content", useOr));
            }
            if (criteria.getOperateType() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getOperateType(), "operate_type", useOr));
            }
            if (criteria.getUserid() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getUserid(), "userid", useOr));
            }
            if (criteria.getUsername() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getUsername(), "username", useOr));
            }
            if (criteria.getIp() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getIp(), "ip", useOr));
            }
            if (criteria.getMethod() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getMethod(), "method", useOr));
            }
            if (criteria.getRequestUrl() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getRequestUrl(), "request_url", useOr));
            }
            if (criteria.getRequestType() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getRequestType(), "request_type", useOr));
            }
            if (criteria.getCostTime() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getCostTime(), "cost_time", useOr));
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
     * Return a {@link IPage} of {@link SysLogDTO} which matches the criteria from the database.
     * @param queryWrapper The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    public IPage<SysLogDTO> findByQueryWrapper(QueryWrapper<SysLog> queryWrapper, Page<SysLog> page) {
        log.debug("find by criteria : {}, page: {}", queryWrapper, page);
        return sysLogRepository.selectPage(page, queryWrapper).convert(sysLogMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(SysLogCriteria criteria) {
        QueryWrapper<SysLog> queryWrapper = createQueryWrapper(criteria);
        List<String> selectFields = new ArrayList<>();
        List<String> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields);
        }
        if (criteria.getLogType() != null) {
            getAggregateAndGroupBy(criteria.getLogType(), "log_type", selectFields, groupByFields);
        }
        if (criteria.getLogContent() != null) {
            getAggregateAndGroupBy(criteria.getLogContent(), "log_content", selectFields, groupByFields);
        }
        if (criteria.getOperateType() != null) {
            getAggregateAndGroupBy(criteria.getOperateType(), "operate_type", selectFields, groupByFields);
        }
        if (criteria.getUserid() != null) {
            getAggregateAndGroupBy(criteria.getUserid(), "userid", selectFields, groupByFields);
        }
        if (criteria.getUsername() != null) {
            getAggregateAndGroupBy(criteria.getUsername(), "username", selectFields, groupByFields);
        }
        if (criteria.getIp() != null) {
            getAggregateAndGroupBy(criteria.getIp(), "ip", selectFields, groupByFields);
        }
        if (criteria.getMethod() != null) {
            getAggregateAndGroupBy(criteria.getMethod(), "method", selectFields, groupByFields);
        }
        if (criteria.getRequestUrl() != null) {
            getAggregateAndGroupBy(criteria.getRequestUrl(), "request_url", selectFields, groupByFields);
        }
        if (criteria.getRequestType() != null) {
            getAggregateAndGroupBy(criteria.getRequestType(), "request_type", selectFields, groupByFields);
        }
        if (criteria.getCostTime() != null) {
            getAggregateAndGroupBy(criteria.getCostTime(), "cost_time", selectFields, groupByFields);
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
            return sysLogRepository.selectMaps(queryWrapper);
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

    public Map<String, Object> logInfo() {
        Map<String, Object> result = new HashMap<>();
        // 获取一天的开始和结束时间
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dayStart = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date dayEnd = calendar.getTime();
        // 获取系统访问记录
        SysLogCriteria sysLogCriteria = new SysLogCriteria();
        sysLogCriteria.logType().setEquals(LogType.LOGIN);
        Long totalVisitCount = countByCriteria(sysLogCriteria);
        result.put("totalVisitCount", totalVisitCount);
        sysLogCriteria
            .createdDate()
            .setGreaterThanOrEqual(dayStart.toInstant().atZone(ZoneId.systemDefault()))
            .setLessThan(dayEnd.toInstant().atZone(ZoneId.systemDefault()));
        Long todayVisitCount = countByCriteria(sysLogCriteria);
        result.put("todayVisitCount", todayVisitCount);
        Long todayIp = countByFieldNameAndCriteria("ip", true, sysLogCriteria);
        result.put("todayIp", todayIp);
        return result;
    }

    public List<Map<String, Object>> countVisit() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date dayEnd = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date dayStart = calendar.getTime();
        String[] selections = new String[4];
        selections[0] = "count(*) as visit";
        selections[1] = "count(distinct(ip)) as ip";
        selections[2] = "DATE_FORMAT(created_date, '%Y-%m-%d') as day";
        selections[3] = "DATE_FORMAT(created_date, '%m-%d') as type";
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(selections);
        queryWrapper.ge("created_date", dayStart);
        queryWrapper.lt("created_date", dayEnd);
        queryWrapper.eq("log_type", LogType.LOGIN);
        queryWrapper.groupBy("day", "type");
        queryWrapper.orderBy(true, true, "day");
        return sysLogRepository.selectMaps(queryWrapper);
    }
}
