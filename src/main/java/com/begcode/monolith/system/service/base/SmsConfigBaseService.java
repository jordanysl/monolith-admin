package com.begcode.monolith.system.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.system.domain.SmsConfig;
import com.begcode.monolith.system.repository.SmsConfigRepository;
import com.begcode.monolith.system.service.dto.SmsConfigDTO;
import com.begcode.monolith.system.service.mapper.SmsConfigMapper;
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
 * Service Implementation for managing {@link SmsConfig}.
 */
public class SmsConfigBaseService<R extends SmsConfigRepository, E extends SmsConfig>
    extends BaseServiceImpl<SmsConfigRepository, SmsConfig> {

    private final Logger log = LoggerFactory.getLogger(SmsConfigBaseService.class);

    protected final SmsConfigRepository smsConfigRepository;

    protected final CacheManager cacheManager;

    protected final SmsConfigMapper smsConfigMapper;

    public SmsConfigBaseService(SmsConfigRepository smsConfigRepository, CacheManager cacheManager, SmsConfigMapper smsConfigMapper) {
        this.smsConfigRepository = smsConfigRepository;
        this.cacheManager = cacheManager;
        this.smsConfigMapper = smsConfigMapper;
    }

    /**
     * Save a smsConfig.
     *
     * @param smsConfigDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public SmsConfigDTO save(SmsConfigDTO smsConfigDTO) {
        log.debug("Request to save SmsConfig : {}", smsConfigDTO);
        SmsConfig smsConfig = smsConfigMapper.toEntity(smsConfigDTO);

        this.saveOrUpdate(smsConfig);
        return findOne(smsConfig.getId()).get();
    }

    /**
     * Update a smsConfig.
     *
     * @param smsConfigDTO the entity to save.
     * @return the persisted entity.
     */
    public SmsConfigDTO update(SmsConfigDTO smsConfigDTO) {
        log.debug("Request to update SmsConfig : {}", smsConfigDTO);

        SmsConfig smsConfig = smsConfigMapper.toEntity(smsConfigDTO);

        smsConfigRepository.updateById(smsConfig);
        return findOne(smsConfigDTO.getId()).get();
    }

    /**
     * Partially update a smsConfig.
     *
     * @param smsConfigDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Optional<SmsConfigDTO> partialUpdate(SmsConfigDTO smsConfigDTO) {
        log.debug("Request to partially update SmsConfig : {}", smsConfigDTO);

        return smsConfigRepository
            .findById(smsConfigDTO.getId())
            .map(existingSmsConfig -> {
                smsConfigMapper.partialUpdate(existingSmsConfig, smsConfigDTO);

                return existingSmsConfig;
            })
            .map(tempSmsConfig -> {
                smsConfigRepository.save(tempSmsConfig);
                return smsConfigMapper.toDto(smsConfigRepository.selectById(tempSmsConfig.getId()));
            });
    }

    /**
     * Get all the smsConfigs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public IPage<SmsConfigDTO> findAll(Page<SmsConfig> pageable) {
        log.debug("Request to get all SmsConfigs");
        return this.page(pageable).convert(smsConfigMapper::toDto);
    }

    /**
     * Get one smsConfig by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<SmsConfigDTO> findOne(Long id) {
        log.debug("Request to get SmsConfig : {}", id);
        return Optional
            .ofNullable(smsConfigRepository.selectById(id))
            .map(smsConfig -> {
                Binder.bindRelations(smsConfig);
                return smsConfig;
            })
            .map(smsConfigMapper::toDto);
    }

    /**
     * Delete the smsConfig by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SmsConfig : {}", id);

        smsConfigRepository.deleteById(id);
    }

    /**
     * Update specified field by smsConfig
     */
    @Transactional
    public void updateBatch(SmsConfigDTO changeSmsConfigDTO, List<String> fieldNames, List<Long> ids) {
        UpdateWrapper<SmsConfig> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", ids);
        fieldNames.forEach(fieldName ->
            updateWrapper.set(
                CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName),
                BeanUtil.getFieldValue(changeSmsConfigDTO, fieldName)
            )
        );
        this.update(updateWrapper);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
