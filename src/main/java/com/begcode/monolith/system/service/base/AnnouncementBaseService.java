package com.begcode.monolith.system.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.domain.enumeration.AnnoCategory;
import com.begcode.monolith.domain.enumeration.AnnoSendStatus;
import com.begcode.monolith.security.SecurityUtils;
import com.begcode.monolith.service.UserQueryService;
import com.begcode.monolith.service.criteria.UserCriteria;
import com.begcode.monolith.system.domain.Announcement;
import com.begcode.monolith.system.domain.AnnouncementRecord;
import com.begcode.monolith.system.repository.AnnouncementRecordRepository;
import com.begcode.monolith.system.repository.AnnouncementRepository;
import com.begcode.monolith.system.service.dto.AnnouncementDTO;
import com.begcode.monolith.system.service.mapper.AnnouncementMapper;
import com.begcode.monolith.web.rest.errors.BadRequestAlertException;
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
 * Service Implementation for managing {@link Announcement}.
 */
public class AnnouncementBaseService<R extends AnnouncementRepository, E extends Announcement>
    extends BaseServiceImpl<AnnouncementRepository, Announcement> {

    private final Logger log = LoggerFactory.getLogger(AnnouncementBaseService.class);

    protected final UserQueryService userQueryService;

    protected final AnnouncementRecordRepository announcementRecordRepository;

    protected final AnnouncementRepository announcementRepository;

    protected final CacheManager cacheManager;

    protected final AnnouncementMapper announcementMapper;

    public AnnouncementBaseService(
        UserQueryService userQueryService,
        AnnouncementRecordRepository announcementRecordRepository,
        AnnouncementRepository announcementRepository,
        CacheManager cacheManager,
        AnnouncementMapper announcementMapper
    ) {
        this.userQueryService = userQueryService;
        this.announcementRecordRepository = announcementRecordRepository;
        this.announcementRepository = announcementRepository;
        this.cacheManager = cacheManager;
        this.announcementMapper = announcementMapper;
    }

    /**
     * Save a announcement.
     *
     * @param announcementDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public AnnouncementDTO save(AnnouncementDTO announcementDTO) {
        log.debug("Request to save Announcement : {}", announcementDTO);
        Announcement announcement = announcementMapper.toEntity(announcementDTO);

        this.saveOrUpdate(announcement);
        return findOne(announcement.getId()).get();
    }

    /**
     * Update a announcement.
     *
     * @param announcementDTO the entity to save.
     * @return the persisted entity.
     */
    public AnnouncementDTO update(AnnouncementDTO announcementDTO) {
        log.debug("Request to update Announcement : {}", announcementDTO);

        Announcement announcement = announcementMapper.toEntity(announcementDTO);

        announcementRepository.updateById(announcement);
        return findOne(announcementDTO.getId()).get();
    }

    /**
     * Partially update a announcement.
     *
     * @param announcementDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Optional<AnnouncementDTO> partialUpdate(AnnouncementDTO announcementDTO) {
        log.debug("Request to partially update Announcement : {}", announcementDTO);

        return announcementRepository
            .findById(announcementDTO.getId())
            .map(existingAnnouncement -> {
                announcementMapper.partialUpdate(existingAnnouncement, announcementDTO);

                return existingAnnouncement;
            })
            .map(tempAnnouncement -> {
                announcementRepository.save(tempAnnouncement);
                return announcementMapper.toDto(announcementRepository.selectById(tempAnnouncement.getId()));
            });
    }

    /**
     * Get all the announcements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public IPage<AnnouncementDTO> findAll(Page<Announcement> pageable) {
        log.debug("Request to get all Announcements");
        return this.page(pageable).convert(announcementMapper::toDto);
    }

    /**
     * Get one announcement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<AnnouncementDTO> findOne(Long id) {
        log.debug("Request to get Announcement : {}", id);
        return Optional
            .ofNullable(announcementRepository.selectById(id))
            .map(announcement -> {
                Binder.bindRelations(announcement);
                return announcement;
            })
            .map(announcementMapper::toDto);
    }

    /**
     * Delete the announcement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Announcement : {}", id);

        announcementRepository.deleteById(id);
    }

    @Transactional
    public void release(Long id) {
        Announcement announcement = announcementRepository.selectById(id);
        if (announcement != null) {
            announcement
                .sendStatus(AnnoSendStatus.RELEASED)
                .sendTime(ZonedDateTime.now())
                .senderId(SecurityUtils.getCurrentUserId().orElse(null));
            announcementRepository.save(announcement);
            List<AnnouncementRecord> records = new ArrayList<>();
            ZonedDateTime sendTime = ZonedDateTime.now();
            Long[] userIds = {};
            UserCriteria criteria = new UserCriteria();
            List<Long> receiverIds = Arrays
                .stream(announcement.getReceiverIds().split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
            switch (announcement.getReceiverType()) {
                case ALL:
                    return;
                case USER:
                    userIds = Arrays.stream((announcement.getReceiverIds().split(","))).map(Long::valueOf).toArray(Long[]::new);
                    break;
                case POSITION:
                    criteria.positionId().setIn(receiverIds);
                    userIds = userQueryService.getFieldByCriteria(Long.class, "id", true, criteria).toArray(userIds);
                    break;
                case DEPARTMENT:
                    criteria.departmentId().setIn(receiverIds);
                    userIds = userQueryService.getFieldByCriteria(Long.class, "id", true, criteria).toArray(userIds);
                    break;
                case AUTHORITY:
                    criteria.authoritiesId().setIn(receiverIds);
                    userIds = userQueryService.getFieldByCriteria(Long.class, "id", true, criteria).toArray(userIds);
                    break;
            }

            for (Long userId : userIds) {
                announcementRecordRepository.save(new AnnouncementRecord().anntId(announcement.getId()).userId(userId).hasRead(false));
            }
        } else {
            throw new BadRequestAlertException("未找到指定Id的通知", "Announcement", "IdNotFound");
        }
    }

    /**
     * Update specified field by announcement
     */
    @Transactional
    public void updateBatch(AnnouncementDTO changeAnnouncementDTO, List<String> fieldNames, List<Long> ids) {
        UpdateWrapper<Announcement> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", ids);
        fieldNames.forEach(fieldName ->
            updateWrapper.set(
                CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName),
                BeanUtil.getFieldValue(changeAnnouncementDTO, fieldName)
            )
        );
        this.update(updateWrapper);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
