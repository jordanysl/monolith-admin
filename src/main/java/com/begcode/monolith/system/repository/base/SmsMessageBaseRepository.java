package com.begcode.monolith.system.repository.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.begcode.monolith.system.domain.SmsMessage;
import com.diboot.core.mapper.BaseCrudMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.*;
import org.springframework.data.repository.NoRepositoryBean;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data JPA repository for the SmsMessage entity.
 */
@SuppressWarnings("unused")
@NoRepositoryBean
public interface SmsMessageBaseRepository<E extends SmsMessage> extends BaseCrudMapper<SmsMessage> {
    default List<SmsMessage> findAll() {
        return this.selectList(null);
    }

    default Optional<SmsMessage> findById(Long id) {
        return Optional.ofNullable(this.selectById(id));
    }
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove

}
