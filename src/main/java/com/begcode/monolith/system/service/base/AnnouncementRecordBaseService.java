package com.begcode.monolith.system.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.security.SecurityUtils;
import com.begcode.monolith.system.domain.AnnouncementRecord;
import com.begcode.monolith.system.repository.AnnouncementRecordRepository;
import com.begcode.monolith.system.service.dto.AnnouncementDTO;
import com.begcode.monolith.system.service.dto.AnnouncementRecordDTO;
import com.begcode.monolith.system.service.mapper.AnnouncementRecordMapper;
import com.diboot.core.binding.Binder;
import com.diboot.core.service.impl.BaseServiceImpl;
import com.google.common.base.CaseFormat;
import java.time.ZonedDateTime;
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
 * Service Implementation for managing {@link AnnouncementRecord}.
 */
public class AnnouncementRecordBaseService<R extends AnnouncementRecordRepository, E extends AnnouncementRecord>
    extends BaseServiceImpl<AnnouncementRecordRepository, AnnouncementRecord> {

    private final Logger log = LoggerFactory.getLogger(AnnouncementRecordBaseService.class);

    protected final AnnouncementRecordRepository announcementRecordRepository;

    protected final CacheManager cacheManager;

    protected final AnnouncementRecordMapper announcementRecordMapper;

    public AnnouncementRecordBaseService(
        AnnouncementRecordRepository announcementRecordRepository,
        CacheManager cacheManager,
        AnnouncementRecordMapper announcementRecordMapper
    ) {
        this.announcementRecordRepository = announcementRecordRepository;
        this.cacheManager = cacheManager;
        this.announcementRecordMapper = announcementRecordMapper;
    }

    /**
     * Save a announcementRecord.
     *
     * @param announcementRecordDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public AnnouncementRecordDTO save(AnnouncementRecordDTO announcementRecordDTO) {
        log.debug("Request to save AnnouncementRecord : {}", announcementRecordDTO);
        AnnouncementRecord announcementRecord = announcementRecordMapper.toEntity(announcementRecordDTO);

        this.saveOrUpdate(announcementRecord);
        return findOne(announcementRecord.getId()).get();
    }

    /**
     * Update a announcementRecord.
     *
     * @param announcementRecordDTO the entity to save.
     * @return the persisted entity.
     */
    public AnnouncementRecordDTO update(AnnouncementRecordDTO announcementRecordDTO) {
        log.debug("Request to update AnnouncementRecord : {}", announcementRecordDTO);

        AnnouncementRecord announcementRecord = announcementRecordMapper.toEntity(announcementRecordDTO);

        announcementRecordRepository.updateById(announcementRecord);
        return findOne(announcementRecordDTO.getId()).get();
    }

    /**
     * Partially update a announcementRecord.
     *
     * @param announcementRecordDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Optional<AnnouncementRecordDTO> partialUpdate(AnnouncementRecordDTO announcementRecordDTO) {
        log.debug("Request to partially update AnnouncementRecord : {}", announcementRecordDTO);

        return announcementRecordRepository
            .findById(announcementRecordDTO.getId())
            .map(existingAnnouncementRecord -> {
                announcementRecordMapper.partialUpdate(existingAnnouncementRecord, announcementRecordDTO);

                return existingAnnouncementRecord;
            })
            .map(tempAnnouncementRecord -> {
                announcementRecordRepository.save(tempAnnouncementRecord);
                return announcementRecordMapper.toDto(announcementRecordRepository.selectById(tempAnnouncementRecord.getId()));
            });
    }

    /**
     * Get all the announcementRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public IPage<AnnouncementRecordDTO> findAll(Page<AnnouncementRecord> pageable) {
        log.debug("Request to get all AnnouncementRecords");
        return this.page(pageable).convert(announcementRecordMapper::toDto);
    }

    /**
     * Get one announcementRecord by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<AnnouncementRecordDTO> findOne(Long id) {
        log.debug("Request to get AnnouncementRecord : {}", id);
        return Optional
            .ofNullable(announcementRecordRepository.selectById(id))
            .map(announcementRecord -> {
                Binder.bindRelations(announcementRecord);
                return announcementRecord;
            })
            .map(announcementRecordMapper::toDto);
    }

    /**
     * Delete the announcementRecord by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnnouncementRecord : {}", id);

        announcementRecordRepository.deleteById(id);
    }

    @Transactional
    public void updateRecord(List<AnnouncementDTO> announcementDTOS) {
        Long userId = SecurityUtils.getCurrentUserId().get();
        announcementDTOS.forEach(announcementDTO -> {
            Long anntId = announcementDTO.getId();
            if (
                announcementRecordRepository.selectCount(
                    new LambdaQueryWrapper<AnnouncementRecord>()
                        .eq(AnnouncementRecord::getAnntId, announcementDTO.getId())
                        .eq(AnnouncementRecord::getUserId, userId)
                ) ==
                0
            ) {
                announcementRecordRepository.save(new AnnouncementRecord().anntId(anntId).userId(userId));
            }
        });
    }

    @Transactional
    public void setRead(Long anntId) {
        Long userId = SecurityUtils.getCurrentUserId().get();
        this.update(
                new LambdaUpdateWrapper<AnnouncementRecord>()
                    .set(AnnouncementRecord::getHasRead, true)
                    .set(AnnouncementRecord::getReadTime, ZonedDateTime.now())
                    .eq(AnnouncementRecord::getAnntId, anntId)
                    .eq(AnnouncementRecord::getUserId, userId)
            );
    }

    /**
     * Update specified field by announcementRecord
     */
    @Transactional
    public void updateBatch(AnnouncementRecordDTO changeAnnouncementRecordDTO, List<String> fieldNames, List<Long> ids) {
        UpdateWrapper<AnnouncementRecord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", ids);
        fieldNames.forEach(fieldName ->
            updateWrapper.set(
                CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName),
                BeanUtil.getFieldValue(changeAnnouncementRecordDTO, fieldName)
            )
        );
        this.update(updateWrapper);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
