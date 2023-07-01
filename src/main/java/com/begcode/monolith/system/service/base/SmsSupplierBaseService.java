package com.begcode.monolith.system.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.system.domain.SmsSupplier;
import com.begcode.monolith.system.repository.SmsSupplierRepository;
import com.begcode.monolith.system.service.dto.SmsSupplierDTO;
import com.begcode.monolith.system.service.mapper.SmsSupplierMapper;
import com.diboot.core.binding.Binder;
import com.diboot.core.service.impl.BaseServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CaseFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.sms4j.aliyun.config.AlibabaConfig;
import org.dromara.sms4j.comm.enumerate.SupplierType;
import org.dromara.sms4j.core.config.SupplierFactory;
import org.dromara.sms4j.core.factory.SmsFactory;
import org.dromara.sms4j.yunpian.config.YunpianConfig;
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
 * Service Implementation for managing {@link SmsSupplier}.
 */
public class SmsSupplierBaseService<R extends SmsSupplierRepository, E extends SmsSupplier>
    extends BaseServiceImpl<SmsSupplierRepository, SmsSupplier> {

    private final Logger log = LoggerFactory.getLogger(SmsSupplierBaseService.class);

    protected final SmsSupplierRepository smsSupplierRepository;

    protected final CacheManager cacheManager;

    protected final SmsSupplierMapper smsSupplierMapper;

    public SmsSupplierBaseService(
        SmsSupplierRepository smsSupplierRepository,
        CacheManager cacheManager,
        SmsSupplierMapper smsSupplierMapper
    ) {
        this.smsSupplierRepository = smsSupplierRepository;
        this.cacheManager = cacheManager;
        this.smsSupplierMapper = smsSupplierMapper;
    }

    /**
     * Save a smsSupplier.
     *
     * @param smsSupplierDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public SmsSupplierDTO save(SmsSupplierDTO smsSupplierDTO) {
        log.debug("Request to save SmsSupplier : {}", smsSupplierDTO);
        SmsSupplier smsSupplier = smsSupplierMapper.toEntity(smsSupplierDTO);

        this.saveOrUpdate(smsSupplier);
        return findOne(smsSupplier.getId()).get();
    }

    /**
     * Update a smsSupplier.
     *
     * @param smsSupplierDTO the entity to save.
     * @return the persisted entity.
     */
    public SmsSupplierDTO update(SmsSupplierDTO smsSupplierDTO) {
        log.debug("Request to update SmsSupplier : {}", smsSupplierDTO);

        SmsSupplier smsSupplier = smsSupplierMapper.toEntity(smsSupplierDTO);

        smsSupplierRepository.updateById(smsSupplier);
        return findOne(smsSupplierDTO.getId()).get();
    }

    /**
     * Partially update a smsSupplier.
     *
     * @param smsSupplierDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Optional<SmsSupplierDTO> partialUpdate(SmsSupplierDTO smsSupplierDTO) {
        log.debug("Request to partially update SmsSupplier : {}", smsSupplierDTO);

        return smsSupplierRepository
            .findById(smsSupplierDTO.getId())
            .map(existingSmsSupplier -> {
                smsSupplierMapper.partialUpdate(existingSmsSupplier, smsSupplierDTO);

                return existingSmsSupplier;
            })
            .map(tempSmsSupplier -> {
                smsSupplierRepository.save(tempSmsSupplier);
                return smsSupplierMapper.toDto(smsSupplierRepository.selectById(tempSmsSupplier.getId()));
            });
    }

    /**
     * Get all the smsSuppliers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public IPage<SmsSupplierDTO> findAll(Page<SmsSupplier> pageable) {
        log.debug("Request to get all SmsSuppliers");
        return this.page(pageable).convert(smsSupplierMapper::toDto);
    }

    /**
     * Get one smsSupplier by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<SmsSupplierDTO> findOne(Long id) {
        log.debug("Request to get SmsSupplier : {}", id);
        return Optional
            .ofNullable(smsSupplierRepository.selectById(id))
            .map(smsSupplier -> {
                Binder.bindRelations(smsSupplier);
                return smsSupplier;
            })
            .map(smsSupplierMapper::toDto);
    }

    /**
     * Delete the smsSupplier by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SmsSupplier : {}", id);

        smsSupplierRepository.deleteById(id);
    }

    public void initService() {
        LambdaQueryWrapper<SmsSupplier> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SmsSupplier::getEnabled, true);
        List<SmsSupplier> smsSuppliers = smsSupplierRepository.selectList(queryWrapper);
        ObjectMapper objectMapper = new ObjectMapper();
        if (CollectionUtils.isNotEmpty(smsSuppliers)) {
            smsSuppliers.forEach(smsSupplier -> {
                switch (smsSupplier.getProvider()) {
                    case ALIBABA:
                        try {
                            Map<String, String> configMap = objectMapper.readValue(
                                smsSupplier.getConfigData(),
                                new TypeReference<Map<String, String>>() {}
                            );
                            AlibabaConfig alibabaConfig = SupplierFactory.getAlibabaConfig();
                            alibabaConfig.setAccessKeyId(configMap.get("accessKeyId"));
                            alibabaConfig.setAccessKeySecret(configMap.get("accessKeySecret"));
                            alibabaConfig.setSignature(configMap.get("signature"));
                            alibabaConfig.setRequestUrl(configMap.get("requestUrl"));
                            alibabaConfig.setAction(StringUtils.defaultIfBlank(configMap.get("action"), "SendSms"));
                            alibabaConfig.setVersion(StringUtils.defaultIfBlank(configMap.get("version"), "2017-05-25"));
                            alibabaConfig.setRegionId(StringUtils.defaultIfBlank(configMap.get("regionId"), "cn-hangzhou"));
                            SmsFactory.refresh(SupplierType.ALIBABA);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case YUNPIAN:
                        try {
                            Map<String, String> configMap = objectMapper.readValue(
                                smsSupplier.getConfigData(),
                                new TypeReference<Map<String, String>>() {}
                            );
                            YunpianConfig yunpianConfig = SupplierFactory.getYunpianConfig();
                            yunpianConfig.setApikey(configMap.get("apikey"));
                            yunpianConfig.setCallbackUrl(configMap.get("callbackUrl"));
                            SmsFactory.refresh(SupplierType.YUNPIAN);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    default:
                        break;
                }
            });
        }
    }

    /**
     * Update specified field by smsSupplier
     */
    @Transactional
    public void updateBatch(SmsSupplierDTO changeSmsSupplierDTO, List<String> fieldNames, List<Long> ids) {
        UpdateWrapper<SmsSupplier> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", ids);
        fieldNames.forEach(fieldName ->
            updateWrapper.set(
                CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName),
                BeanUtil.getFieldValue(changeSmsSupplierDTO, fieldName)
            )
        );
        this.update(updateWrapper);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
