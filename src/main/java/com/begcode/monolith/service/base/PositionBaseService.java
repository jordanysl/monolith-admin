package com.begcode.monolith.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.domain.Position;
import com.begcode.monolith.repository.PositionRepository;
import com.begcode.monolith.service.dto.PositionDTO;
import com.begcode.monolith.service.mapper.PositionMapper;
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
 * Service Implementation for managing {@link Position}.
 */
public class PositionBaseService<R extends PositionRepository, E extends Position> extends BaseServiceImpl<PositionRepository, Position> {

    private final Logger log = LoggerFactory.getLogger(PositionBaseService.class);

    protected final PositionRepository positionRepository;

    protected final CacheManager cacheManager;

    protected final PositionMapper positionMapper;

    public PositionBaseService(PositionRepository positionRepository, CacheManager cacheManager, PositionMapper positionMapper) {
        this.positionRepository = positionRepository;
        this.cacheManager = cacheManager;
        this.positionMapper = positionMapper;
    }

    /**
     * Save a position.
     *
     * @param positionDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public PositionDTO save(PositionDTO positionDTO) {
        log.debug("Request to save Position : {}", positionDTO);
        Position position = positionMapper.toEntity(positionDTO);

        this.saveOrUpdate(position);
        return findOne(position.getId()).get();
    }

    /**
     * Update a position.
     *
     * @param positionDTO the entity to save.
     * @return the persisted entity.
     */
    public PositionDTO update(PositionDTO positionDTO) {
        log.debug("Request to update Position : {}", positionDTO);

        Position position = positionMapper.toEntity(positionDTO);

        positionRepository.updateById(position);
        return findOne(positionDTO.getId()).get();
    }

    /**
     * Partially update a position.
     *
     * @param positionDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Optional<PositionDTO> partialUpdate(PositionDTO positionDTO) {
        log.debug("Request to partially update Position : {}", positionDTO);

        return positionRepository
            .findById(positionDTO.getId())
            .map(existingPosition -> {
                positionMapper.partialUpdate(existingPosition, positionDTO);

                return existingPosition;
            })
            .map(tempPosition -> {
                positionRepository.save(tempPosition);
                return positionMapper.toDto(positionRepository.selectById(tempPosition.getId()));
            });
    }

    /**
     * Get all the positions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public IPage<PositionDTO> findAll(Page<Position> pageable) {
        log.debug("Request to get all Positions");
        return this.page(pageable).convert(positionMapper::toDto);
    }

    /**
     * Get one position by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<PositionDTO> findOne(Long id) {
        log.debug("Request to get Position : {}", id);
        return Optional
            .ofNullable(positionRepository.selectById(id))
            .map(position -> {
                Binder.bindRelations(position);
                return position;
            })
            .map(positionMapper::toDto);
    }

    /**
     * Delete the position by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Position : {}", id);

        positionRepository.deleteById(id);
    }

    /**
     * Update specified field by position
     */
    @Transactional
    public void updateBatch(PositionDTO changePositionDTO, List<String> fieldNames, List<Long> ids) {
        UpdateWrapper<Position> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", ids);
        fieldNames.forEach(fieldName ->
            updateWrapper.set(
                CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName),
                BeanUtil.getFieldValue(changePositionDTO, fieldName)
            )
        );
        this.update(updateWrapper);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
