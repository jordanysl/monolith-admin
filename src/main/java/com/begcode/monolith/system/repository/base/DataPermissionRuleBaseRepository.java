package com.begcode.monolith.system.repository.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.begcode.monolith.system.domain.DataPermissionRule;
import com.diboot.core.mapper.BaseCrudMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.*;
import org.springframework.data.repository.NoRepositoryBean;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data JPA repository for the DataPermissionRule entity.
 */
@SuppressWarnings("unused")
@NoRepositoryBean
public interface DataPermissionRuleBaseRepository<E extends DataPermissionRule> extends BaseCrudMapper<DataPermissionRule> {
    default List<DataPermissionRule> findAll() {
        return this.selectList(null);
    }

    default Optional<DataPermissionRule> findById(Long id) {
        return Optional.ofNullable(this.selectById(id));
    }
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove

}
