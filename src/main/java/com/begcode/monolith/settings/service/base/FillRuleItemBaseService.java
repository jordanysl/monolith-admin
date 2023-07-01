package com.begcode.monolith.settings.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.settings.domain.FillRuleItem;
import com.begcode.monolith.settings.repository.FillRuleItemRepository;
import com.begcode.monolith.settings.service.dto.FillRuleItemDTO;
import com.begcode.monolith.settings.service.mapper.FillRuleItemMapper;
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
 * Service Implementation for managing {@link FillRuleItem}.
 */
public class FillRuleItemBaseService<R extends FillRuleItemRepository, E extends FillRuleItem>
    extends BaseServiceImpl<FillRuleItemRepository, FillRuleItem> {

    private final Logger log = LoggerFactory.getLogger(FillRuleItemBaseService.class);

    private final List<String> relationCacheNames = Arrays.asList(
        com.begcode.monolith.settings.domain.SysFillRule.class.getName() + ".ruleItems"
    );

    protected final FillRuleItemRepository fillRuleItemRepository;

    protected final CacheManager cacheManager;

    protected final FillRuleItemMapper fillRuleItemMapper;

    public FillRuleItemBaseService(
        FillRuleItemRepository fillRuleItemRepository,
        CacheManager cacheManager,
        FillRuleItemMapper fillRuleItemMapper
    ) {
        this.fillRuleItemRepository = fillRuleItemRepository;
        this.cacheManager = cacheManager;
        this.fillRuleItemMapper = fillRuleItemMapper;
    }

    /**
     * Save a fillRuleItem.
     *
     * @param fillRuleItemDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public FillRuleItemDTO save(FillRuleItemDTO fillRuleItemDTO) {
        log.debug("Request to save FillRuleItem : {}", fillRuleItemDTO);
        FillRuleItem fillRuleItem = fillRuleItemMapper.toEntity(fillRuleItemDTO);

        this.saveOrUpdate(fillRuleItem);
        return findOne(fillRuleItem.getId()).get();
    }

    /**
     * Update a fillRuleItem.
     *
     * @param fillRuleItemDTO the entity to save.
     * @return the persisted entity.
     */
    public FillRuleItemDTO update(FillRuleItemDTO fillRuleItemDTO) {
        log.debug("Request to update FillRuleItem : {}", fillRuleItemDTO);

        FillRuleItem fillRuleItem = fillRuleItemMapper.toEntity(fillRuleItemDTO);

        fillRuleItemRepository.updateById(fillRuleItem);
        return findOne(fillRuleItemDTO.getId()).get();
    }

    /**
     * Partially update a fillRuleItem.
     *
     * @param fillRuleItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Optional<FillRuleItemDTO> partialUpdate(FillRuleItemDTO fillRuleItemDTO) {
        log.debug("Request to partially update FillRuleItem : {}", fillRuleItemDTO);

        return fillRuleItemRepository
            .findById(fillRuleItemDTO.getId())
            .map(existingFillRuleItem -> {
                fillRuleItemMapper.partialUpdate(existingFillRuleItem, fillRuleItemDTO);

                return existingFillRuleItem;
            })
            .map(tempFillRuleItem -> {
                fillRuleItemRepository.save(tempFillRuleItem);
                return fillRuleItemMapper.toDto(fillRuleItemRepository.selectById(tempFillRuleItem.getId()));
            });
    }

    /**
     * Get all the fillRuleItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public IPage<FillRuleItemDTO> findAll(Page<FillRuleItem> pageable) {
        log.debug("Request to get all FillRuleItems");
        return this.page(pageable).convert(fillRuleItemMapper::toDto);
    }

    /**
     * Get one fillRuleItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<FillRuleItemDTO> findOne(Long id) {
        log.debug("Request to get FillRuleItem : {}", id);
        return Optional
            .ofNullable(fillRuleItemRepository.selectById(id))
            .map(fillRuleItem -> {
                Binder.bindRelations(fillRuleItem);
                return fillRuleItem;
            })
            .map(fillRuleItemMapper::toDto);
    }

    /**
     * Delete the fillRuleItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FillRuleItem : {}", id);

        fillRuleItemRepository.deleteById(id);
    }

    /**
     * Update specified field by fillRuleItem
     */
    @Transactional
    public void updateBatch(FillRuleItemDTO changeFillRuleItemDTO, List<String> fieldNames, List<Long> ids) {
        UpdateWrapper<FillRuleItem> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", ids);
        fieldNames.forEach(fieldName ->
            updateWrapper.set(
                CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName),
                BeanUtil.getFieldValue(changeFillRuleItemDTO, fieldName)
            )
        );
        this.update(updateWrapper);
    }

    protected void clearRelationsCache() {
        this.relationCacheNames.forEach(cacheName -> Optional.ofNullable(cacheManager.getCache(cacheName)).ifPresent(Cache::clear));
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
