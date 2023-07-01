package com.begcode.monolith.system.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.system.domain.SmsTemplate;
import com.begcode.monolith.system.repository.SmsTemplateRepository;
import com.begcode.monolith.system.service.dto.SmsTemplateDTO;
import com.begcode.monolith.system.service.mapper.SmsTemplateMapper;
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
 * Service Implementation for managing {@link SmsTemplate}.
 */
public class SmsTemplateBaseService<R extends SmsTemplateRepository, E extends SmsTemplate>
    extends BaseServiceImpl<SmsTemplateRepository, SmsTemplate> {

    private final Logger log = LoggerFactory.getLogger(SmsTemplateBaseService.class);

    protected final SmsTemplateRepository smsTemplateRepository;

    protected final CacheManager cacheManager;

    protected final SmsTemplateMapper smsTemplateMapper;

    public SmsTemplateBaseService(
        SmsTemplateRepository smsTemplateRepository,
        CacheManager cacheManager,
        SmsTemplateMapper smsTemplateMapper
    ) {
        this.smsTemplateRepository = smsTemplateRepository;
        this.cacheManager = cacheManager;
        this.smsTemplateMapper = smsTemplateMapper;
    }

    /**
     * Save a smsTemplate.
     *
     * @param smsTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public SmsTemplateDTO save(SmsTemplateDTO smsTemplateDTO) {
        log.debug("Request to save SmsTemplate : {}", smsTemplateDTO);
        SmsTemplate smsTemplate = smsTemplateMapper.toEntity(smsTemplateDTO);

        this.saveOrUpdate(smsTemplate);
        return findOne(smsTemplate.getId()).get();
    }

    /**
     * Update a smsTemplate.
     *
     * @param smsTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    public SmsTemplateDTO update(SmsTemplateDTO smsTemplateDTO) {
        log.debug("Request to update SmsTemplate : {}", smsTemplateDTO);

        SmsTemplate smsTemplate = smsTemplateMapper.toEntity(smsTemplateDTO);

        smsTemplateRepository.updateById(smsTemplate);
        return findOne(smsTemplateDTO.getId()).get();
    }

    /**
     * Partially update a smsTemplate.
     *
     * @param smsTemplateDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Optional<SmsTemplateDTO> partialUpdate(SmsTemplateDTO smsTemplateDTO) {
        log.debug("Request to partially update SmsTemplate : {}", smsTemplateDTO);

        return smsTemplateRepository
            .findById(smsTemplateDTO.getId())
            .map(existingSmsTemplate -> {
                smsTemplateMapper.partialUpdate(existingSmsTemplate, smsTemplateDTO);

                return existingSmsTemplate;
            })
            .map(tempSmsTemplate -> {
                smsTemplateRepository.save(tempSmsTemplate);
                return smsTemplateMapper.toDto(smsTemplateRepository.selectById(tempSmsTemplate.getId()));
            });
    }

    /**
     * Get all the smsTemplates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public IPage<SmsTemplateDTO> findAll(Page<SmsTemplate> pageable) {
        log.debug("Request to get all SmsTemplates");
        return this.page(pageable).convert(smsTemplateMapper::toDto);
    }

    /**
     * Get one smsTemplate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<SmsTemplateDTO> findOne(Long id) {
        log.debug("Request to get SmsTemplate : {}", id);
        return Optional
            .ofNullable(smsTemplateRepository.selectById(id))
            .map(smsTemplate -> {
                Binder.bindRelations(smsTemplate);
                return smsTemplate;
            })
            .map(smsTemplateMapper::toDto);
    }

    /**
     * Delete the smsTemplate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SmsTemplate : {}", id);

        smsTemplateRepository.deleteById(id);
    }

    /**
     * Update specified field by smsTemplate
     */
    @Transactional
    public void updateBatch(SmsTemplateDTO changeSmsTemplateDTO, List<String> fieldNames, List<Long> ids) {
        UpdateWrapper<SmsTemplate> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", ids);
        fieldNames.forEach(fieldName ->
            updateWrapper.set(
                CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName),
                BeanUtil.getFieldValue(changeSmsTemplateDTO, fieldName)
            )
        );
        this.update(updateWrapper);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
