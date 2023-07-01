package com.begcode.monolith.settings.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.settings.domain.CommonFieldData;
import com.begcode.monolith.settings.repository.CommonFieldDataRepository;
import com.begcode.monolith.settings.service.dto.CommonFieldDataDTO;
import com.begcode.monolith.settings.service.mapper.CommonFieldDataMapper;
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
 * Service Implementation for managing {@link CommonFieldData}.
 */
public class CommonFieldDataBaseService<R extends CommonFieldDataRepository, E extends CommonFieldData>
    extends BaseServiceImpl<CommonFieldDataRepository, CommonFieldData> {

    private final Logger log = LoggerFactory.getLogger(CommonFieldDataBaseService.class);

    protected final CommonFieldDataRepository commonFieldDataRepository;

    protected final CacheManager cacheManager;

    protected final CommonFieldDataMapper commonFieldDataMapper;

    public CommonFieldDataBaseService(
        CommonFieldDataRepository commonFieldDataRepository,
        CacheManager cacheManager,
        CommonFieldDataMapper commonFieldDataMapper
    ) {
        this.commonFieldDataRepository = commonFieldDataRepository;
        this.cacheManager = cacheManager;
        this.commonFieldDataMapper = commonFieldDataMapper;
    }

    /**
     * Save a commonFieldData.
     *
     * @param commonFieldDataDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public CommonFieldDataDTO save(CommonFieldDataDTO commonFieldDataDTO) {
        log.debug("Request to save CommonFieldData : {}", commonFieldDataDTO);
        CommonFieldData commonFieldData = commonFieldDataMapper.toEntity(commonFieldDataDTO);

        this.saveOrUpdate(commonFieldData);
        return findOne(commonFieldData.getId()).get();
    }

    /**
     * Update a commonFieldData.
     *
     * @param commonFieldDataDTO the entity to save.
     * @return the persisted entity.
     */
    public CommonFieldDataDTO update(CommonFieldDataDTO commonFieldDataDTO) {
        log.debug("Request to update CommonFieldData : {}", commonFieldDataDTO);

        CommonFieldData commonFieldData = commonFieldDataMapper.toEntity(commonFieldDataDTO);

        commonFieldDataRepository.updateById(commonFieldData);
        return findOne(commonFieldDataDTO.getId()).get();
    }

    /**
     * Partially update a commonFieldData.
     *
     * @param commonFieldDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Optional<CommonFieldDataDTO> partialUpdate(CommonFieldDataDTO commonFieldDataDTO) {
        log.debug("Request to partially update CommonFieldData : {}", commonFieldDataDTO);

        return commonFieldDataRepository
            .findById(commonFieldDataDTO.getId())
            .map(existingCommonFieldData -> {
                commonFieldDataMapper.partialUpdate(existingCommonFieldData, commonFieldDataDTO);

                return existingCommonFieldData;
            })
            .map(tempCommonFieldData -> {
                commonFieldDataRepository.save(tempCommonFieldData);
                return commonFieldDataMapper.toDto(commonFieldDataRepository.selectById(tempCommonFieldData.getId()));
            });
    }

    /**
     * Get all the commonFieldData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public IPage<CommonFieldDataDTO> findAll(Page<CommonFieldData> pageable) {
        log.debug("Request to get all CommonFieldData");
        return this.page(pageable).convert(commonFieldDataMapper::toDto);
    }

    /**
     * Get one commonFieldData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<CommonFieldDataDTO> findOne(Long id) {
        log.debug("Request to get CommonFieldData : {}", id);
        return Optional
            .ofNullable(commonFieldDataRepository.selectById(id))
            .map(commonFieldData -> {
                Binder.bindRelations(commonFieldData);
                return commonFieldData;
            })
            .map(commonFieldDataMapper::toDto);
    }

    /**
     * Delete the commonFieldData by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommonFieldData : {}", id);

        commonFieldDataRepository.deleteById(id);
    }

    /**
     * Update specified field by commonFieldData
     */
    @Transactional
    public void updateBatch(CommonFieldDataDTO changeCommonFieldDataDTO, List<String> fieldNames, List<Long> ids) {
        UpdateWrapper<CommonFieldData> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", ids);
        fieldNames.forEach(fieldName ->
            updateWrapper.set(
                CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName),
                BeanUtil.getFieldValue(changeCommonFieldDataDTO, fieldName)
            )
        );
        this.update(updateWrapper);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
