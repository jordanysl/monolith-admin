package com.begcode.monolith.settings.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.settings.domain.SiteConfig;
import com.begcode.monolith.settings.repository.SiteConfigRepository;
import com.begcode.monolith.settings.service.dto.SiteConfigDTO;
import com.begcode.monolith.settings.service.mapper.SiteConfigMapper;
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
 * Service Implementation for managing {@link SiteConfig}.
 */
public class SiteConfigBaseService<R extends SiteConfigRepository, E extends SiteConfig>
    extends BaseServiceImpl<SiteConfigRepository, SiteConfig> {

    private final Logger log = LoggerFactory.getLogger(SiteConfigBaseService.class);

    protected final SiteConfigRepository siteConfigRepository;

    protected final CacheManager cacheManager;

    protected final SiteConfigMapper siteConfigMapper;

    public SiteConfigBaseService(SiteConfigRepository siteConfigRepository, CacheManager cacheManager, SiteConfigMapper siteConfigMapper) {
        this.siteConfigRepository = siteConfigRepository;
        this.cacheManager = cacheManager;
        this.siteConfigMapper = siteConfigMapper;
    }

    /**
     * Save a siteConfig.
     *
     * @param siteConfigDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public SiteConfigDTO save(SiteConfigDTO siteConfigDTO) {
        log.debug("Request to save SiteConfig : {}", siteConfigDTO);
        SiteConfig siteConfig = siteConfigMapper.toEntity(siteConfigDTO);

        this.createOrUpdateN2NRelations(siteConfig, Arrays.asList("items"));
        return findOne(siteConfig.getId()).get();
    }

    /**
     * Update a siteConfig.
     *
     * @param siteConfigDTO the entity to save.
     * @return the persisted entity.
     */
    public SiteConfigDTO update(SiteConfigDTO siteConfigDTO) {
        log.debug("Request to update SiteConfig : {}", siteConfigDTO);

        SiteConfig siteConfig = siteConfigMapper.toEntity(siteConfigDTO);

        this.createOrUpdateN2NRelations(siteConfig, Arrays.asList("items"));
        return findOne(siteConfigDTO.getId()).get();
    }

    /**
     * Partially update a siteConfig.
     *
     * @param siteConfigDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Optional<SiteConfigDTO> partialUpdate(SiteConfigDTO siteConfigDTO) {
        log.debug("Request to partially update SiteConfig : {}", siteConfigDTO);

        return siteConfigRepository
            .findById(siteConfigDTO.getId())
            .map(existingSiteConfig -> {
                siteConfigMapper.partialUpdate(existingSiteConfig, siteConfigDTO);

                return existingSiteConfig;
            })
            .map(tempSiteConfig -> {
                siteConfigRepository.save(tempSiteConfig);
                return siteConfigMapper.toDto(siteConfigRepository.selectById(tempSiteConfig.getId()));
            });
    }

    /**
     * Get all the siteConfigs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public IPage<SiteConfigDTO> findAll(Page<SiteConfig> pageable) {
        log.debug("Request to get all SiteConfigs");
        return this.page(pageable).convert(siteConfigMapper::toDto);
    }

    /**
     * Get one siteConfig by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<SiteConfigDTO> findOne(Long id) {
        log.debug("Request to get SiteConfig : {}", id);
        return Optional
            .ofNullable(siteConfigRepository.selectById(id))
            .map(siteConfig -> {
                Binder.bindRelations(siteConfig);
                return siteConfig;
            })
            .map(siteConfigMapper::toDto);
    }

    /**
     * Delete the siteConfig by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SiteConfig : {}", id);

        siteConfigRepository.deleteById(id);
    }

    /**
     * Update specified field by siteConfig
     */
    @Transactional
    public void updateBatch(SiteConfigDTO changeSiteConfigDTO, List<String> fieldNames, List<Long> ids) {
        UpdateWrapper<SiteConfig> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", ids);
        fieldNames.forEach(fieldName ->
            updateWrapper.set(
                CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName),
                BeanUtil.getFieldValue(changeSiteConfigDTO, fieldName)
            )
        );
        this.update(updateWrapper);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
