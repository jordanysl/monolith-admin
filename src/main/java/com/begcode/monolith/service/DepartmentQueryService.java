package com.begcode.monolith.service;

import static tech.jhipster.service.mybatis.AggregateUtil.buildAggregate;
import static tech.jhipster.service.mybatis.AggregateUtil.buildGroupBy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.domain.*; // for static metamodels
import com.begcode.monolith.domain.Department;
import com.begcode.monolith.repository.DepartmentRepository;
import com.begcode.monolith.service.criteria.DepartmentCriteria;
import com.begcode.monolith.service.dto.DepartmentDTO;
import com.begcode.monolith.service.mapper.DepartmentMapper;
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
import tech.jhipster.service.filter.ZonedDateTimeFilter;
import tech.jhipster.service.mybatis.CriteriaUtil;
import tech.jhipster.service.mybatis.QueryService;

/**
 * 用于对数据库中的{@link Department}实体执行复杂查询的Service。
 * 主要输入是一个{@link DepartmentCriteria}，它被转换为{@link QueryWrapper}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link DepartmentDTO}列表{@link List} 或 {@link DepartmentDTO} 的分页列表 {@link Page}。
 */
@Service
public class DepartmentQueryService implements QueryService<Department> {

    private final Logger log = LoggerFactory.getLogger(DepartmentQueryService.class);

    protected final DepartmentRepository departmentRepository;

    protected final DepartmentMapper departmentMapper;

    public DepartmentQueryService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    /**
     * Return a {@link List} of {@link DepartmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */

    public List<DepartmentDTO> findByCriteria(DepartmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final QueryWrapper<Department> queryWrapper = createQueryWrapper(criteria);
        return departmentMapper.toDto(departmentRepository.selectList(queryWrapper));
    }

    /**
     * Return a {@link IPage} of {@link DepartmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */

    public IPage<DepartmentDTO> findByCriteria(DepartmentCriteria criteria, Page<Department> page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final QueryWrapper<Department> queryWrapper = createQueryWrapper(criteria);
        return departmentRepository.selectPage(page, queryWrapper).convert(departmentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */

    public long countByCriteria(DepartmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final QueryWrapper<Department> queryWrapper = createQueryWrapper(criteria);
        return departmentRepository.selectCount(queryWrapper);
    }

    /**
     * Get all the departments for parent is null.
     *
     * @param page the pagination information
     * @return the list of entities
     */
    public IPage<DepartmentDTO> findAllTop(DepartmentCriteria criteria, Page<Department> page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        criteria.parentId().setSpecified(false);
        final QueryWrapper<Department> queryWrapper = createQueryWrapper(criteria);
        return departmentRepository
            .selectPage(page, queryWrapper)
            .convert(department -> {
                Binder.bindRelations(department, new String[] { "authorities", "parent", "users" });
                return departmentMapper.toDto(department);
            });
    }

    /**
     * Get all the departments for parent is parentId.
     *
     * @param parentId the Id of parent
     * @return the list of entities
     */
    public List<DepartmentDTO> findChildrenByParentId(Long parentId) {
        log.debug("Request to get all Departments for parent is parentId");
        return departmentRepository
            .selectList(new LambdaUpdateWrapper<Department>().eq(Department::getParentId, parentId))
            .stream()
            .map(department -> {
                Binder.bindRelations(department, new String[] { "authorities", "parent", "users" });
                return departmentMapper.toDto(department);
            })
            .collect(Collectors.toList());
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, DepartmentCriteria criteria) {
        return (List<T>) departmentRepository.selectObjs(createQueryWrapper(criteria).select(fieldName));
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, DepartmentCriteria criteria) {
        return departmentRepository.selectCount(createQueryWrapper(criteria));
    }

    /**
     * Function to convert {@link DepartmentCriteria} to a {@link QueryWrapper}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link QueryWrapper} of the entity.
     */
    protected QueryWrapper<Department> createQueryWrapper(DepartmentCriteria criteria) {
        QueryWrapper<Department> queryWrapper = new DynamicJoinQueryWrapper<>(Department.class, null);
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
                            "create_user_id"
                        )
                    );
                });
            } else {
                queryWrapper.and(q -> {
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "name"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "code"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "address"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "phone_num"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "logo"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "contact"));
                });
            }
        }
        return queryWrapper;
    }

    private QueryWrapper<Department> createQueryWrapper(QueryWrapper<Department> queryWrapper, Boolean useOr, DepartmentCriteria criteria) {
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
            if (criteria.getCode() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getCode(), "code", useOr));
            }
            if (criteria.getAddress() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getAddress(), "address", useOr));
            }
            if (criteria.getPhoneNum() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getPhoneNum(), "phone_num", useOr));
            }
            if (criteria.getLogo() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getLogo(), "logo", useOr));
            }
            if (criteria.getContact() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getContact(), "contact", useOr));
            }
            if (criteria.getCreateUserId() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getCreateUserId(), "create_user_id", useOr));
            }
            if (criteria.getCreateTime() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getCreateTime(), "create_time", useOr));
            }
            if (criteria.getChildrenId() != null) {
                // todo 未实现
            }
            if (criteria.getChildrenName() != null) {
                // todo 未实现 one-to-many;[object Object];name
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
            if (criteria.getParentId() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getParentId(), "parent_id", useOr));
            }
            if (criteria.getParentName() != null) {
                queryWrapper =
                    CriteriaUtil.build(
                        useOr,
                        queryWrapper,
                        buildStringSpecification(criteria.getParentName(), "department_left_join.name", useOr)
                    );
            }
            if (criteria.getUsersId() != null) {
                // todo 未实现
            }
            if (criteria.getUsersFirstName() != null) {
                // todo 未实现 one-to-many;[object Object];firstName
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
     * Return a {@link IPage} of {@link DepartmentDTO} which matches the criteria from the database.
     * @param queryWrapper The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    public IPage<DepartmentDTO> findByQueryWrapper(QueryWrapper<Department> queryWrapper, Page<Department> page) {
        log.debug("find by criteria : {}, page: {}", queryWrapper, page);
        return departmentRepository.selectPage(page, queryWrapper).convert(departmentMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(DepartmentCriteria criteria) {
        QueryWrapper<Department> queryWrapper = createQueryWrapper(criteria);
        List<String> selectFields = new ArrayList<>();
        List<String> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields);
        }
        if (criteria.getName() != null) {
            getAggregateAndGroupBy(criteria.getName(), "name", selectFields, groupByFields);
        }
        if (criteria.getCode() != null) {
            getAggregateAndGroupBy(criteria.getCode(), "code", selectFields, groupByFields);
        }
        if (criteria.getAddress() != null) {
            getAggregateAndGroupBy(criteria.getAddress(), "address", selectFields, groupByFields);
        }
        if (criteria.getPhoneNum() != null) {
            getAggregateAndGroupBy(criteria.getPhoneNum(), "phone_num", selectFields, groupByFields);
        }
        if (criteria.getLogo() != null) {
            getAggregateAndGroupBy(criteria.getLogo(), "logo", selectFields, groupByFields);
        }
        if (criteria.getContact() != null) {
            getAggregateAndGroupBy(criteria.getContact(), "contact", selectFields, groupByFields);
        }
        if (criteria.getCreateUserId() != null) {
            getAggregateAndGroupBy(criteria.getCreateUserId(), "create_user_id", selectFields, groupByFields);
        }
        if (criteria.getCreateTime() != null) {
            getAggregateAndGroupBy(criteria.getCreateTime(), "create_time", selectFields, groupByFields);
        }
        if (CollectionUtils.isNotEmpty(selectFields)) {
            queryWrapper.select(selectFields.toArray(new String[0])).groupBy(CollectionUtils.isNotEmpty(groupByFields), groupByFields);
            return departmentRepository.selectMaps(queryWrapper);
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
