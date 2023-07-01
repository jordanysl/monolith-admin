package com.begcode.monolith.settings.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.common.IFillRuleHandler;
import com.begcode.monolith.settings.domain.SysFillRule;
import com.begcode.monolith.settings.repository.SysFillRuleRepository;
import com.begcode.monolith.settings.service.dto.SysFillRuleDTO;
import com.begcode.monolith.settings.service.mapper.SysFillRuleMapper;
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
 * Service Implementation for managing {@link SysFillRule}.
 */
public class SysFillRuleBaseService<R extends SysFillRuleRepository, E extends SysFillRule>
    extends BaseServiceImpl<SysFillRuleRepository, SysFillRule>
    implements IFillRuleHandler {

    private final Logger log = LoggerFactory.getLogger(SysFillRuleBaseService.class);

    protected final SysFillRuleRepository sysFillRuleRepository;

    protected final CacheManager cacheManager;

    protected final SysFillRuleMapper sysFillRuleMapper;

    public SysFillRuleBaseService(
        SysFillRuleRepository sysFillRuleRepository,
        CacheManager cacheManager,
        SysFillRuleMapper sysFillRuleMapper
    ) {
        this.sysFillRuleRepository = sysFillRuleRepository;
        this.cacheManager = cacheManager;
        this.sysFillRuleMapper = sysFillRuleMapper;
    }

    /**
     * Save a sysFillRule.
     *
     * @param sysFillRuleDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public SysFillRuleDTO save(SysFillRuleDTO sysFillRuleDTO) {
        log.debug("Request to save SysFillRule : {}", sysFillRuleDTO);
        SysFillRule sysFillRule = sysFillRuleMapper.toEntity(sysFillRuleDTO);

        this.createOrUpdateN2NRelations(sysFillRule, Arrays.asList("ruleItems"));
        return findOne(sysFillRule.getId()).get();
    }

    /**
     * Update a sysFillRule.
     *
     * @param sysFillRuleDTO the entity to save.
     * @return the persisted entity.
     */
    public SysFillRuleDTO update(SysFillRuleDTO sysFillRuleDTO) {
        log.debug("Request to update SysFillRule : {}", sysFillRuleDTO);

        SysFillRule sysFillRule = sysFillRuleMapper.toEntity(sysFillRuleDTO);

        this.createOrUpdateN2NRelations(sysFillRule, Arrays.asList("ruleItems"));
        return findOne(sysFillRuleDTO.getId()).get();
    }

    /**
     * Partially update a sysFillRule.
     *
     * @param sysFillRuleDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Optional<SysFillRuleDTO> partialUpdate(SysFillRuleDTO sysFillRuleDTO) {
        log.debug("Request to partially update SysFillRule : {}", sysFillRuleDTO);

        return sysFillRuleRepository
            .findById(sysFillRuleDTO.getId())
            .map(existingSysFillRule -> {
                sysFillRuleMapper.partialUpdate(existingSysFillRule, sysFillRuleDTO);

                return existingSysFillRule;
            })
            .map(tempSysFillRule -> {
                sysFillRuleRepository.save(tempSysFillRule);
                return sysFillRuleMapper.toDto(sysFillRuleRepository.selectById(tempSysFillRule.getId()));
            });
    }

    /**
     * Get all the sysFillRules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public IPage<SysFillRuleDTO> findAll(Page<SysFillRule> pageable) {
        log.debug("Request to get all SysFillRules");
        return this.page(pageable).convert(sysFillRuleMapper::toDto);
    }

    /**
     * Get one sysFillRule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<SysFillRuleDTO> findOne(Long id) {
        log.debug("Request to get SysFillRule : {}", id);
        return Optional
            .ofNullable(sysFillRuleRepository.selectById(id))
            .map(sysFillRule -> {
                Binder.bindRelations(sysFillRule);
                return sysFillRule;
            })
            .map(sysFillRuleMapper::toDto);
    }

    /**
     * Delete the sysFillRule by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SysFillRule : {}", id);

        sysFillRuleRepository.deleteById(id);
    }

    @Override
    public Object execute(JSONObject params, JSONObject formData) {
        return null;
    }

    /**
     * Update specified field by sysFillRule
     */
    @Transactional
    public void updateBatch(SysFillRuleDTO changeSysFillRuleDTO, List<String> fieldNames, List<Long> ids) {
        UpdateWrapper<SysFillRule> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", ids);
        fieldNames.forEach(fieldName ->
            updateWrapper.set(
                CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName),
                BeanUtil.getFieldValue(changeSysFillRuleDTO, fieldName)
            )
        );
        this.update(updateWrapper);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
