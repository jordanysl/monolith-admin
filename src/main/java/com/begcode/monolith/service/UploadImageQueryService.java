package com.begcode.monolith.service;

import static tech.jhipster.service.mybatis.AggregateUtil.buildAggregate;
import static tech.jhipster.service.mybatis.AggregateUtil.buildGroupBy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.domain.*; // for static metamodels
import com.begcode.monolith.domain.UploadImage;
import com.begcode.monolith.repository.UploadImageRepository;
import com.begcode.monolith.service.criteria.UploadImageCriteria;
import com.begcode.monolith.service.dto.UploadImageDTO;
import com.begcode.monolith.service.mapper.UploadImageMapper;
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
 * 用于对数据库中的{@link UploadImage}实体执行复杂查询的Service。
 * 主要输入是一个{@link UploadImageCriteria}，它被转换为{@link QueryWrapper}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link UploadImageDTO}列表{@link List} 或 {@link UploadImageDTO} 的分页列表 {@link Page}。
 */
@Service
public class UploadImageQueryService implements QueryService<UploadImage> {

    private final Logger log = LoggerFactory.getLogger(UploadImageQueryService.class);

    protected final UploadImageRepository uploadImageRepository;

    protected final UploadImageMapper uploadImageMapper;

    public UploadImageQueryService(UploadImageRepository uploadImageRepository, UploadImageMapper uploadImageMapper) {
        this.uploadImageRepository = uploadImageRepository;
        this.uploadImageMapper = uploadImageMapper;
    }

    /**
     * Return a {@link List} of {@link UploadImageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */

    public List<UploadImageDTO> findByCriteria(UploadImageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final QueryWrapper<UploadImage> queryWrapper = createQueryWrapper(criteria);
        return uploadImageMapper.toDto(uploadImageRepository.selectList(queryWrapper));
    }

    /**
     * Return a {@link IPage} of {@link UploadImageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */

    public IPage<UploadImageDTO> findByCriteria(UploadImageCriteria criteria, Page<UploadImage> page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final QueryWrapper<UploadImage> queryWrapper = createQueryWrapper(criteria);
        return uploadImageRepository.selectPage(page, queryWrapper).convert(uploadImageMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */

    public long countByCriteria(UploadImageCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final QueryWrapper<UploadImage> queryWrapper = createQueryWrapper(criteria);
        return uploadImageRepository.selectCount(queryWrapper);
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, UploadImageCriteria criteria) {
        return (List<T>) uploadImageRepository.selectObjs(createQueryWrapper(criteria).select(fieldName));
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, UploadImageCriteria criteria) {
        return uploadImageRepository.selectCount(createQueryWrapper(criteria));
    }

    /**
     * Function to convert {@link UploadImageCriteria} to a {@link QueryWrapper}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link QueryWrapper} of the entity.
     */
    protected QueryWrapper<UploadImage> createQueryWrapper(UploadImageCriteria criteria) {
        QueryWrapper<UploadImage> queryWrapper = new DynamicJoinQueryWrapper<>(UploadImage.class, null);
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
                            "file_size"
                        )
                    );
                    q.or(
                        buildRangeSpecification(
                            (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                            "reference_count"
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
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "full_name"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "name"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "ext"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "type"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "url"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "path"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "folder"));
                    q.or(
                        buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "owner_entity_name")
                    );
                    q.or(
                        buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "owner_entity_id")
                    );
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "business_title"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "business_desc"));
                    q.or(
                        buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "business_status")
                    );
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "smart_url"));
                    q.or(buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), "medium_url"));
                });
            }
        }
        return queryWrapper;
    }

    private QueryWrapper<UploadImage> createQueryWrapper(
        QueryWrapper<UploadImage> queryWrapper,
        Boolean useOr,
        UploadImageCriteria criteria
    ) {
        if (criteria != null) {
            if (useOr == null) {
                useOr = false;
            }
            if (criteria.getId() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getId(), "id", useOr));
            }
            if (criteria.getFullName() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getFullName(), "full_name", useOr));
            }
            if (criteria.getName() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getName(), "name", useOr));
            }
            if (criteria.getExt() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getExt(), "ext", useOr));
            }
            if (criteria.getType() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getType(), "type", useOr));
            }
            if (criteria.getUrl() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getUrl(), "url", useOr));
            }
            if (criteria.getPath() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getPath(), "path", useOr));
            }
            if (criteria.getFolder() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getFolder(), "folder", useOr));
            }
            if (criteria.getOwnerEntityName() != null) {
                queryWrapper =
                    CriteriaUtil.build(
                        useOr,
                        queryWrapper,
                        buildStringSpecification(criteria.getOwnerEntityName(), "owner_entity_name", useOr)
                    );
            }
            if (criteria.getOwnerEntityId() != null) {
                queryWrapper =
                    CriteriaUtil.build(
                        useOr,
                        queryWrapper,
                        buildStringSpecification(criteria.getOwnerEntityId(), "owner_entity_id", useOr)
                    );
            }
            if (criteria.getBusinessTitle() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getBusinessTitle(), "business_title", useOr));
            }
            if (criteria.getBusinessDesc() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getBusinessDesc(), "business_desc", useOr));
            }
            if (criteria.getBusinessStatus() != null) {
                queryWrapper =
                    CriteriaUtil.build(
                        useOr,
                        queryWrapper,
                        buildStringSpecification(criteria.getBusinessStatus(), "business_status", useOr)
                    );
            }
            if (criteria.getCreateAt() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getCreateAt(), "create_at", useOr));
            }
            if (criteria.getFileSize() != null) {
                queryWrapper = CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getFileSize(), "file_size", useOr));
            }
            if (criteria.getSmartUrl() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getSmartUrl(), "smart_url", useOr));
            }
            if (criteria.getMediumUrl() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildStringSpecification(criteria.getMediumUrl(), "medium_url", useOr));
            }
            if (criteria.getReferenceCount() != null) {
                queryWrapper =
                    CriteriaUtil.build(
                        useOr,
                        queryWrapper,
                        buildRangeSpecification(criteria.getReferenceCount(), "reference_count", useOr)
                    );
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
            if (criteria.getCategoryId() != null) {
                queryWrapper =
                    CriteriaUtil.build(useOr, queryWrapper, buildRangeSpecification(criteria.getCategoryId(), "category_id", useOr));
            }
            if (criteria.getCategoryTitle() != null) {
                queryWrapper =
                    CriteriaUtil.build(
                        useOr,
                        queryWrapper,
                        buildStringSpecification(criteria.getCategoryTitle(), "resource_category_left_join.title", useOr)
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
     * Return a {@link IPage} of {@link UploadImageDTO} which matches the criteria from the database.
     * @param queryWrapper The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    public IPage<UploadImageDTO> findByQueryWrapper(QueryWrapper<UploadImage> queryWrapper, Page<UploadImage> page) {
        log.debug("find by criteria : {}, page: {}", queryWrapper, page);
        return uploadImageRepository.selectPage(page, queryWrapper).convert(uploadImageMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(UploadImageCriteria criteria) {
        QueryWrapper<UploadImage> queryWrapper = createQueryWrapper(criteria);
        List<String> selectFields = new ArrayList<>();
        List<String> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields);
        }
        if (criteria.getFullName() != null) {
            getAggregateAndGroupBy(criteria.getFullName(), "full_name", selectFields, groupByFields);
        }
        if (criteria.getName() != null) {
            getAggregateAndGroupBy(criteria.getName(), "name", selectFields, groupByFields);
        }
        if (criteria.getExt() != null) {
            getAggregateAndGroupBy(criteria.getExt(), "ext", selectFields, groupByFields);
        }
        if (criteria.getType() != null) {
            getAggregateAndGroupBy(criteria.getType(), "type", selectFields, groupByFields);
        }
        if (criteria.getUrl() != null) {
            getAggregateAndGroupBy(criteria.getUrl(), "url", selectFields, groupByFields);
        }
        if (criteria.getPath() != null) {
            getAggregateAndGroupBy(criteria.getPath(), "path", selectFields, groupByFields);
        }
        if (criteria.getFolder() != null) {
            getAggregateAndGroupBy(criteria.getFolder(), "folder", selectFields, groupByFields);
        }
        if (criteria.getOwnerEntityName() != null) {
            getAggregateAndGroupBy(criteria.getOwnerEntityName(), "owner_entity_name", selectFields, groupByFields);
        }
        if (criteria.getOwnerEntityId() != null) {
            getAggregateAndGroupBy(criteria.getOwnerEntityId(), "owner_entity_id", selectFields, groupByFields);
        }
        if (criteria.getBusinessTitle() != null) {
            getAggregateAndGroupBy(criteria.getBusinessTitle(), "business_title", selectFields, groupByFields);
        }
        if (criteria.getBusinessDesc() != null) {
            getAggregateAndGroupBy(criteria.getBusinessDesc(), "business_desc", selectFields, groupByFields);
        }
        if (criteria.getBusinessStatus() != null) {
            getAggregateAndGroupBy(criteria.getBusinessStatus(), "business_status", selectFields, groupByFields);
        }
        if (criteria.getCreateAt() != null) {
            getAggregateAndGroupBy(criteria.getCreateAt(), "create_at", selectFields, groupByFields);
        }
        if (criteria.getFileSize() != null) {
            getAggregateAndGroupBy(criteria.getFileSize(), "file_size", selectFields, groupByFields);
        }
        if (criteria.getSmartUrl() != null) {
            getAggregateAndGroupBy(criteria.getSmartUrl(), "smart_url", selectFields, groupByFields);
        }
        if (criteria.getMediumUrl() != null) {
            getAggregateAndGroupBy(criteria.getMediumUrl(), "medium_url", selectFields, groupByFields);
        }
        if (criteria.getReferenceCount() != null) {
            getAggregateAndGroupBy(criteria.getReferenceCount(), "reference_count", selectFields, groupByFields);
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
            return uploadImageRepository.selectMaps(queryWrapper);
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
