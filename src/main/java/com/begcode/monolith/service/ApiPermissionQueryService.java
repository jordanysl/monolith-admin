package com.begcode.monolith.service;

import static tech.jhipster.service.mybatis.AggregateUtil.buildAggregate;
import static tech.jhipster.service.mybatis.AggregateUtil.buildGroupBy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.domain.*; // for static metamodels
import com.begcode.monolith.domain.ApiPermission;
import com.begcode.monolith.repository.ApiPermissionRepository;
import com.begcode.monolith.service.criteria.ApiPermissionCriteria;
import com.begcode.monolith.service.dto.ApiPermissionDTO;
import com.begcode.monolith.service.mapper.ApiPermissionMapper;
import com.diboot.core.binding.Binder;
import com.diboot.core.binding.query.dynamic.DynamicJoinQueryWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
 * 用于对数据库中的{@link ApiPermission}实体执行复杂查询的Service。
 * 主要输入是一个{@link ApiPermissionCriteria}，它被转换为{@link QueryWrapper}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link ApiPermissionDTO}列表{@link List} 或 {@link ApiPermissionDTO} 的分页列表 {@link Page}。
 */
@Service
public class ApiPermissionQueryService implements QueryService<ApiPermission> {

    private final Logger log = LoggerFactory.getLogger(ApiPermissionQueryService.class);

    protected final ApiPermissionRepository apiPermissionRepository;

    protected final ApiPermissionMapper apiPermissionMapper;

    public ApiPermissionQueryService(ApiPermissionRepository apiPermissionRepository, ApiPermissionMapper apiPermissionMapper) {
        this.apiPermissionRepository = apiPermissionRepository;
        this.apiPermissionMapper = apiPermissionMapper;
    }

    /**
     * Return a {@link List} of {@link ApiPermissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */

    public List<ApiPermissionDTO> findByCriteria(ApiPermissionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final QueryWrapper<ApiPermission> queryWrapper = createQueryWrapper(criteria);
        return apiPermissionMapper.toDto(apiPermissionRepository.selectList(queryWrapper));
    }

    /**
     * Return a {@link IPage} of {@link ApiPermissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */

    public IPage<ApiPermissionDTO> findByCriteria(ApiPermissionCriteria criteria, Page<ApiPermission> page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final QueryWrapper<ApiPermission> queryWrapper = createQueryWrapper(criteria);
        return apiPermissionRepository.selectPage(page, queryWrapper).convert(apiPermissionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */

    public long countByCriteria(ApiPermissionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final QueryWrapper<ApiPermission> queryWrapper = createQueryWrapper(criteria);
        return apiPermissionRepository.selectCount(queryWrapper);
    }

    /**
     * Get all the apiPermissions for parent is null.
     *
     * @param page the pagination information
     * @return the list of entities
     */
    public IPage<ApiPermissionDTO> findAllTop(ApiPermissionCriteria criteria, Page<ApiPermission> page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        criteria.parentId().setSpecified(false);
        final QueryWrapper<ApiPermission> queryWrapper = createQueryWrapper(criteria);
        return apiPermissionRepository
            .selectPage(page, queryWrapper)
            .convert(apiPermission -> {
                Binder.bindRelations(apiPermission, new String[] { "parent", "authorities" });
                return apiPermissionMapper.toDto(apiPermission);
            });
    }

    /**
     * Get all the apiPermissions for parent is parentId.
     *
     * @param parentId the Id of parent
     * @return the list of entities
     */
    public List<ApiPermissionDTO> findChildrenByParentId(Long parentId) {
        log.debug("Request to get all ApiPermissions for parent is parentId");
        return apiPermissionRepository
            .selectList(new LambdaUpdateWrapper<ApiPermission>().eq(ApiPermission::getParentId, parentId))
            .stream()
            .map(apiPermission -> {
                Binder.bindRelations(apiPermission, new String[] { "parent", "authorities" });
                return apiPermissionMapper.toDto(apiPermission);
            })
            .collect(Collectors.toList());
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, ApiPermissionCriteria criteria) {
        return (List<T>) apiPermissionRepository.selectObjs(createQueryWrapper(criteria).select(fieldName));
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, ApiPermissionCriteria criteria) {
        return apiPermissionRepository.selectCount(createQueryWrapper(criteria));
    }

    /**
     * Function to convert {@link ApiPermissionCriteria} to a {@link QueryWrapper}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link QueryWrapper} of the entity.
     */
    protected QueryWrapper<ApiPermission> createQueryWrapper(ApiPermissionCriteria criteria) {
        QueryWrapper<ApiPermission> queryWrapper = new DynamicJoinQueryWrapper<>(ApiPermission.class, null);
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
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "service_name"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "name"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "code"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "description"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "method"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "url"));
                });
            }
        }
        return queryWrapper;
    }

    private QueryWrapper<ApiPermission> createQueryWrapper(
        QueryWrapper<ApiPermission> queryWrapper,
        Boolean useOr,
        ApiPermissionCriteria criteria
    ) {
        if (criteria != null) {
            if (useOr == null) {
                useOr = false;
            }
            if (criteria.getId() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getId(), "id", useOr));
            }
            if (criteria.getServiceName() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getServiceName(), "service_name", useOr));
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
            if (criteria.getType() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getType(), "type", useOr));
            }
            if (criteria.getMethod() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getMethod(), "method", useOr));
            }
            if (criteria.getUrl() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getUrl(), "url", useOr));
            }
            if (criteria.getStatus() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildSpecification(criteria.getStatus(), "status", useOr));
            }
            if (criteria.getChildrenId() != null) {
                // todo 未实现
            }
            if (criteria.getChildrenName() != null) {
                // todo 未实现 one-to-many;[object Object];name
            }
            if (criteria.getParentId() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getParentId(), "parent_id", useOr));
            }
            if (criteria.getParentName() != null) {
                queryWrapper =
                    CriteriaUtil.build(
                        useOr,
                        queryWrapper,
                        buildStringSpecification(criteria.getParentName(), "api_permission_left_join.name", useOr)
                    );
            }
            if (criteria.getAuthoritiesId() != null) {
                queryWrapper =
                    CriteriaUtil.build(
                        useOr,
                        queryWrapper,
                        buildRangeSpecification(criteria.getAuthoritiesId(), "jhi_authority_left_join.id", useOr)
                    );
            }
            if (criteria.getAuthoritiesName() != null) {
                queryWrapper =
                    CriteriaUtil.build(
                        useOr,
                        queryWrapper,
                        buildStringSpecification(criteria.getAuthoritiesName(), "jhi_authority_left_join.name", useOr)
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
     * Return a {@link IPage} of {@link ApiPermissionDTO} which matches the criteria from the database.
     * @param queryWrapper The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    public IPage<ApiPermissionDTO> findByQueryWrapper(QueryWrapper<ApiPermission> queryWrapper, Page<ApiPermission> page) {
        log.debug("find by criteria : {}, page: {}", queryWrapper, page);
        return apiPermissionRepository.selectPage(page, queryWrapper).convert(apiPermissionMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(ApiPermissionCriteria criteria) {
        QueryWrapper<ApiPermission> queryWrapper = createQueryWrapper(criteria);
        List<String> selectFields = new ArrayList<>();
        List<String> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields);
        }
        if (criteria.getServiceName() != null) {
            getAggregateAndGroupBy(criteria.getServiceName(), "service_name", selectFields, groupByFields);
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
        if (criteria.getType() != null) {
            getAggregateAndGroupBy(criteria.getType(), "type", selectFields, groupByFields);
        }
        if (criteria.getMethod() != null) {
            getAggregateAndGroupBy(criteria.getMethod(), "method", selectFields, groupByFields);
        }
        if (criteria.getUrl() != null) {
            getAggregateAndGroupBy(criteria.getUrl(), "url", selectFields, groupByFields);
        }
        if (criteria.getStatus() != null) {
            getAggregateAndGroupBy(criteria.getStatus(), "status", selectFields, groupByFields);
        }
        if (CollectionUtils.isNotEmpty(selectFields)) {
            queryWrapper.select(selectFields.toArray(new String[0])).groupBy(CollectionUtils.isNotEmpty(groupByFields), groupByFields);
            return apiPermissionRepository.selectMaps(queryWrapper);
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
