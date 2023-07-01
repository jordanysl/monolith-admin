package com.begcode.monolith.system.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.system.domain.DataPermissionRule;
import com.begcode.monolith.system.repository.DataPermissionRuleRepository;
import com.begcode.monolith.system.service.dto.DataPermissionRuleDTO;
import com.begcode.monolith.system.service.mapper.DataPermissionRuleMapper;
import com.diboot.core.binding.Binder;
import com.diboot.core.service.impl.BaseServiceImpl;
import com.google.common.base.CaseFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Service Implementation for managing {@link DataPermissionRule}.
 */
public class DataPermissionRuleBaseService<R extends DataPermissionRuleRepository, E extends DataPermissionRule>
    extends BaseServiceImpl<DataPermissionRuleRepository, DataPermissionRule> {

    private final Logger log = LoggerFactory.getLogger(DataPermissionRuleBaseService.class);

    protected final DataPermissionRuleRepository dataPermissionRuleRepository;

    protected final CacheManager cacheManager;

    protected final DataPermissionRuleMapper dataPermissionRuleMapper;

    public DataPermissionRuleBaseService(
        DataPermissionRuleRepository dataPermissionRuleRepository,
        CacheManager cacheManager,
        DataPermissionRuleMapper dataPermissionRuleMapper
    ) {
        this.dataPermissionRuleRepository = dataPermissionRuleRepository;
        this.cacheManager = cacheManager;
        this.dataPermissionRuleMapper = dataPermissionRuleMapper;
    }

    /**
     * Save a dataPermissionRule.
     *
     * @param dataPermissionRuleDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public DataPermissionRuleDTO save(DataPermissionRuleDTO dataPermissionRuleDTO) {
        log.debug("Request to save DataPermissionRule : {}", dataPermissionRuleDTO);
        DataPermissionRule dataPermissionRule = dataPermissionRuleMapper.toEntity(dataPermissionRuleDTO);

        this.saveOrUpdate(dataPermissionRule);
        return findOne(dataPermissionRule.getId()).get();
    }

    /**
     * Update a dataPermissionRule.
     *
     * @param dataPermissionRuleDTO the entity to save.
     * @return the persisted entity.
     */
    public DataPermissionRuleDTO update(DataPermissionRuleDTO dataPermissionRuleDTO) {
        log.debug("Request to update DataPermissionRule : {}", dataPermissionRuleDTO);

        DataPermissionRule dataPermissionRule = dataPermissionRuleMapper.toEntity(dataPermissionRuleDTO);

        dataPermissionRuleRepository.updateById(dataPermissionRule);
        return findOne(dataPermissionRuleDTO.getId()).get();
    }

    /**
     * Partially update a dataPermissionRule.
     *
     * @param dataPermissionRuleDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Optional<DataPermissionRuleDTO> partialUpdate(DataPermissionRuleDTO dataPermissionRuleDTO) {
        log.debug("Request to partially update DataPermissionRule : {}", dataPermissionRuleDTO);

        return dataPermissionRuleRepository
            .findById(dataPermissionRuleDTO.getId())
            .map(existingDataPermissionRule -> {
                dataPermissionRuleMapper.partialUpdate(existingDataPermissionRule, dataPermissionRuleDTO);

                return existingDataPermissionRule;
            })
            .map(tempDataPermissionRule -> {
                dataPermissionRuleRepository.save(tempDataPermissionRule);
                return dataPermissionRuleMapper.toDto(dataPermissionRuleRepository.selectById(tempDataPermissionRule.getId()));
            });
    }

    /**
     * Get all the dataPermissionRules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public IPage<DataPermissionRuleDTO> findAll(Page<DataPermissionRule> pageable) {
        log.debug("Request to get all DataPermissionRules");
        return this.page(pageable).convert(dataPermissionRuleMapper::toDto);
    }

    /**
     * Get one dataPermissionRule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<DataPermissionRuleDTO> findOne(Long id) {
        log.debug("Request to get DataPermissionRule : {}", id);
        return Optional
            .ofNullable(dataPermissionRuleRepository.selectById(id))
            .map(dataPermissionRule -> {
                Binder.bindRelations(dataPermissionRule);
                return dataPermissionRule;
            })
            .map(dataPermissionRuleMapper::toDto);
    }

    /**
     * Delete the dataPermissionRule by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DataPermissionRule : {}", id);

        dataPermissionRuleRepository.deleteById(id);
    }

    /**
     * Update specified field by dataPermissionRule
     */
    @Transactional
    public void updateBatch(DataPermissionRuleDTO changeDataPermissionRuleDTO, List<String> fieldNames, List<Long> ids) {
        UpdateWrapper<DataPermissionRule> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", ids);
        fieldNames.forEach(fieldName ->
            updateWrapper.set(
                CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName),
                BeanUtil.getFieldValue(changeDataPermissionRuleDTO, fieldName)
            )
        );
        this.update(updateWrapper);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
