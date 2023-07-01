package com.begcode.monolith.repository.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.begcode.monolith.domain.ResourceCategory;
import com.diboot.core.binding.Binder;
import com.diboot.core.mapper.BaseCrudMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.*;
import org.springframework.data.repository.NoRepositoryBean;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data JPA repository for the ResourceCategory entity.
 */
@NoRepositoryBean
public interface ResourceCategoryBaseRepository<E extends ResourceCategory> extends BaseCrudMapper<ResourceCategory> {
    default Optional<ResourceCategory> findOneWithEagerRelationships(Long id) {
        return Optional
            .ofNullable(this.selectById(id))
            .map(resourceCategory -> {
                Binder.bindRelations(resourceCategory, new String[] { "files", "children", "images", "parent" });
                return resourceCategory;
            });
    }

    default List<ResourceCategory> findAll() {
        return this.selectList(null);
    }

    default Optional<ResourceCategory> findById(Long id) {
        return Optional.ofNullable(this.selectById(id));
    }

    @Select("delete from resource_category resourceCategory where resourceCategory.parent = #{parentId}")
    void deleteAllByParentId(@Param("parentId") Long parentId);

    default IPage<ResourceCategory> findAllByParentIsNull(IPage<ResourceCategory> pageable) {
        return this.selectPage(pageable, new QueryWrapper<ResourceCategory>().isNull("parent_id"));
    }
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove

}
