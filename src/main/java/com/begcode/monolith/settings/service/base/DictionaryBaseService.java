package com.begcode.monolith.settings.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.domain.enumeration.DynamicEnum;
import com.begcode.monolith.settings.domain.CommonFieldData;
import com.begcode.monolith.settings.domain.Dictionary;
import com.begcode.monolith.settings.repository.DictionaryRepository;
import com.begcode.monolith.settings.service.dto.DictionaryDTO;
import com.begcode.monolith.settings.service.mapper.DictionaryMapper;
import com.diboot.core.binding.Binder;
import com.diboot.core.service.DictionaryServiceExtProvider;
import com.diboot.core.service.impl.BaseServiceImpl;
import com.diboot.core.vo.LabelValue;
import com.google.common.base.CaseFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
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
 * Service Implementation for managing {@link Dictionary}.
 */
public class DictionaryBaseService<R extends DictionaryRepository, E extends Dictionary>
    extends BaseServiceImpl<DictionaryRepository, Dictionary>
    implements DictionaryServiceExtProvider {

    private final Logger log = LoggerFactory.getLogger(DictionaryBaseService.class);

    protected final DictionaryRepository dictionaryRepository;

    protected final CacheManager cacheManager;

    protected final DictionaryMapper dictionaryMapper;

    public DictionaryBaseService(DictionaryRepository dictionaryRepository, CacheManager cacheManager, DictionaryMapper dictionaryMapper) {
        this.dictionaryRepository = dictionaryRepository;
        this.cacheManager = cacheManager;
        this.dictionaryMapper = dictionaryMapper;
    }

    /**
     * Save a dictionary.
     *
     * @param dictionaryDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public DictionaryDTO save(DictionaryDTO dictionaryDTO) {
        log.debug("Request to save Dictionary : {}", dictionaryDTO);
        Dictionary dictionary = dictionaryMapper.toEntity(dictionaryDTO);

        this.createOrUpdateN2NRelations(dictionary, Arrays.asList("items"));
        return findOne(dictionary.getId()).get();
    }

    /**
     * Update a dictionary.
     *
     * @param dictionaryDTO the entity to save.
     * @return the persisted entity.
     */
    public DictionaryDTO update(DictionaryDTO dictionaryDTO) {
        log.debug("Request to update Dictionary : {}", dictionaryDTO);

        Dictionary dictionary = dictionaryMapper.toEntity(dictionaryDTO);

        this.createOrUpdateN2NRelations(dictionary, Arrays.asList("items"));
        return findOne(dictionaryDTO.getId()).get();
    }

    /**
     * Partially update a dictionary.
     *
     * @param dictionaryDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Optional<DictionaryDTO> partialUpdate(DictionaryDTO dictionaryDTO) {
        log.debug("Request to partially update Dictionary : {}", dictionaryDTO);

        return dictionaryRepository
            .findById(dictionaryDTO.getId())
            .map(existingDictionary -> {
                dictionaryMapper.partialUpdate(existingDictionary, dictionaryDTO);

                return existingDictionary;
            })
            .map(tempDictionary -> {
                dictionaryRepository.save(tempDictionary);
                return dictionaryMapper.toDto(dictionaryRepository.selectById(tempDictionary.getId()));
            });
    }

    /**
     * Get all the dictionaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public IPage<DictionaryDTO> findAll(Page<Dictionary> pageable) {
        log.debug("Request to get all Dictionaries");
        return this.page(pageable).convert(dictionaryMapper::toDto);
    }

    /**
     * Get one dictionary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<DictionaryDTO> findOne(Long id) {
        log.debug("Request to get Dictionary : {}", id);
        return Optional
            .ofNullable(dictionaryRepository.selectById(id))
            .map(dictionary -> {
                Binder.bindRelations(dictionary);
                return dictionary;
            })
            .map(dictionaryMapper::toDto);
    }

    /**
     * Delete the dictionary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Dictionary : {}", id);

        dictionaryRepository.deleteById(id);
    }

    @Override
    public void bindItemLabel(List voList, String setFieldName, String getFieldName, String type) {
        if (CollectionUtils.isEmpty(voList)) {
            return;
        }
        LambdaQueryWrapper<Dictionary> queryWrapper = new LambdaQueryWrapper<Dictionary>().eq(Dictionary::getDictKey, type);
        List<Dictionary> entityList = super.getEntityList(queryWrapper, null);
        if (CollectionUtils.isNotEmpty(entityList)) {
            Dictionary dictionary = entityList.get(0);
            Binder.bindRelations(dictionary);
            Map<String, String> map = dictionary
                .getItems()
                .stream()
                .collect(Collectors.toMap(CommonFieldData::getValue, CommonFieldData::getLabel));
            for (Object item : voList) {
                Object value = BeanUtil.getProperty(item, getFieldName);
                if (Objects.isNull(value)) {
                    continue;
                }
                Object label = map.get(value);
                if (label == null) {
                    if (value instanceof String && ((String) value).contains(",")) {
                        List<String> labelList = new ArrayList<>();
                        for (String key : ((String) value).split(",")) {
                            labelList.add(map.get(key));
                        }
                        label = String.join(",", labelList);
                    } else if (value instanceof Collection) {
                        List<String> labelList = new ArrayList<>();
                        for (Object key : (Collection) value) {
                            labelList.add(map.get((String) key));
                        }
                        label = labelList;
                    }
                }
                if (Objects.nonNull(label)) {
                    BeanUtil.setProperty(item, setFieldName, label);
                }
            }
        }
    }

    @Override
    public List<LabelValue> getLabelValueList(String dictType) {
        LambdaQueryWrapper<Dictionary> queryWrapper = new LambdaQueryWrapper<Dictionary>().eq(Dictionary::getDictKey, dictType);
        List<Dictionary> entityList = super.getEntityList(queryWrapper, null);
        if (CollectionUtils.isNotEmpty(entityList)) {
            Dictionary dictionary = entityList.get(0);
            Binder.bindRelations(dictionary);
            return dictionary
                .getItems()
                .stream()
                .map(item -> new LabelValue(item.getLabel(), item.getValue()))
                .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public void updateEnums(DictionaryDTO dictionaryDTO) {
        try {
            Class clazz = Class.forName("com.begcode.monolith.domain.enumeration." + dictionaryDTO.getDictKey());
            DynamicEnum.addEnum0(clazz, "d", new Class[0], "");
        } catch (ClassNotFoundException e) {
            log.error("updateEnums error: " + dictionaryDTO.getDictKey(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Update specified field by dictionary
     */
    @Transactional
    public void updateBatch(DictionaryDTO changeDictionaryDTO, List<String> fieldNames, List<Long> ids) {
        UpdateWrapper<Dictionary> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", ids);
        fieldNames.forEach(fieldName ->
            updateWrapper.set(
                CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName),
                BeanUtil.getFieldValue(changeDictionaryDTO, fieldName)
            )
        );
        this.update(updateWrapper);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
