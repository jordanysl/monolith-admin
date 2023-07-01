package com.begcode.monolith.service.base;

import cn.hutool.core.bean.BeanUtil;
import cn.xuyanwu.spring.file.storage.FileInfo;
import cn.xuyanwu.spring.file.storage.FileStorageService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.domain.UploadFile;
import com.begcode.monolith.repository.UploadFileRepository;
import com.begcode.monolith.service.dto.UploadFileDTO;
import com.begcode.monolith.service.mapper.UploadFileMapper;
import com.begcode.monolith.web.rest.errors.BadRequestAlertException;
import com.diboot.core.binding.Binder;
import com.diboot.core.service.impl.BaseServiceImpl;
import com.google.common.base.CaseFormat;
import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
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
 * Service Implementation for managing {@link UploadFile}.
 */
public class UploadFileBaseService<R extends UploadFileRepository, E extends UploadFile>
    extends BaseServiceImpl<UploadFileRepository, UploadFile> {

    private final Logger log = LoggerFactory.getLogger(UploadFileBaseService.class);

    private final List<String> relationCacheNames = Arrays.asList(com.begcode.monolith.domain.ResourceCategory.class.getName() + ".files");

    protected final FileStorageService fileStorageService;

    protected final UploadFileRepository uploadFileRepository;

    protected final CacheManager cacheManager;

    protected final UploadFileMapper uploadFileMapper;

    public UploadFileBaseService(
        FileStorageService fileStorageService,
        UploadFileRepository uploadFileRepository,
        CacheManager cacheManager,
        UploadFileMapper uploadFileMapper
    ) {
        this.fileStorageService = fileStorageService;
        this.uploadFileRepository = uploadFileRepository;
        this.cacheManager = cacheManager;
        this.uploadFileMapper = uploadFileMapper;
    }

    /**
     * Update a uploadFile.
     *
     * @param uploadFileDTO the entity to save.
     * @return the persisted entity.
     */
    public UploadFileDTO update(UploadFileDTO uploadFileDTO) {
        log.debug("Request to update UploadFile : {}", uploadFileDTO);

        UploadFile uploadFile = uploadFileMapper.toEntity(uploadFileDTO);

        uploadFileRepository.updateById(uploadFile);
        return findOne(uploadFileDTO.getId()).get();
    }

    /**
     * Partially update a uploadFile.
     *
     * @param uploadFileDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Optional<UploadFileDTO> partialUpdate(UploadFileDTO uploadFileDTO) {
        log.debug("Request to partially update UploadFile : {}", uploadFileDTO);

        return uploadFileRepository
            .findById(uploadFileDTO.getId())
            .map(existingUploadFile -> {
                uploadFileMapper.partialUpdate(existingUploadFile, uploadFileDTO);

                return existingUploadFile;
            })
            .map(tempUploadFile -> {
                uploadFileRepository.save(tempUploadFile);
                return uploadFileMapper.toDto(uploadFileRepository.selectById(tempUploadFile.getId()));
            });
    }

    /**
     * Get all the uploadFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public IPage<UploadFileDTO> findAll(Page<UploadFile> pageable) {
        log.debug("Request to get all UploadFiles");
        return this.page(pageable).convert(uploadFileMapper::toDto);
    }

    /**
     * Get one uploadFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<UploadFileDTO> findOne(Long id) {
        log.debug("Request to get UploadFile : {}", id);
        return Optional
            .ofNullable(uploadFileRepository.selectById(id))
            .map(uploadFile -> {
                Binder.bindRelations(uploadFile);
                return uploadFile;
            })
            .map(uploadFileMapper::toDto);
    }

    /**
     * Delete the uploadFile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UploadFile : {}", id);

        uploadFileRepository.deleteById(id);
    }

    /**
     * Save a uploadFile.
     *
     * @param uploadFileDTO the entity to save
     * @return the persisted entity
     */
    @Transactional
    public UploadFileDTO save(UploadFileDTO uploadFileDTO) {
        log.debug("Request to save UploadFile : {}", uploadFileDTO);
        if (!uploadFileDTO.getFile().isEmpty()) {
            final String extName = FilenameUtils.getExtension(uploadFileDTO.getFile().getOriginalFilename());
            final String randomNameNew = UUID.randomUUID().toString().replaceAll("\\-", "");
            final String yearAndMonth = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM"));
            final String savePathNew = yearAndMonth + File.separator;
            final String saveFileName = savePathNew + randomNameNew + "." + extName;
            final long fileSize = uploadFileDTO.getFile().getSize();
            FileInfo upload = fileStorageService.of(uploadFileDTO.getFile()).setPath(savePathNew).upload();
            uploadFileDTO.setCreateAt(ZonedDateTime.now());
            uploadFileDTO.setExt(extName);
            uploadFileDTO.setFullName(uploadFileDTO.getFile().getOriginalFilename());
            uploadFileDTO.setName(uploadFileDTO.getFile().getName());
            uploadFileDTO.setFolder(savePathNew);
            uploadFileDTO.setUrl(upload.getUrl());
            uploadFileDTO.setFileSize(fileSize);
        } else {
            throw new BadRequestAlertException("Invalid file", "UploadFile", "imagesnull");
        }
        UploadFile uploadFile = uploadFileMapper.toEntity(uploadFileDTO);
        this.saveOrUpdate(uploadFile);
        return uploadFileMapper.toDto(this.getById(uploadFile.getId()));
    }

    /**
     * Update specified field by uploadFile
     */
    @Transactional
    public void updateBatch(UploadFileDTO changeUploadFileDTO, List<String> fieldNames, List<Long> ids) {
        UpdateWrapper<UploadFile> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", ids);
        fieldNames.forEach(fieldName ->
            updateWrapper.set(
                CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName),
                BeanUtil.getFieldValue(changeUploadFileDTO, fieldName)
            )
        );
        this.update(updateWrapper);
    }

    protected void clearRelationsCache() {
        this.relationCacheNames.forEach(cacheName -> Optional.ofNullable(cacheManager.getCache(cacheName)).ifPresent(Cache::clear));
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
