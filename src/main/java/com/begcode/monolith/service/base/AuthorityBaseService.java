package com.begcode.monolith.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.domain.Authority;
import com.begcode.monolith.repository.AuthorityRepository;
import com.begcode.monolith.service.dto.AuthorityDTO;
import com.begcode.monolith.service.mapper.AuthorityMapper;
import com.diboot.core.binding.Binder;
import com.diboot.core.service.impl.BaseServiceImpl;
import com.google.common.base.CaseFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
 * Service Implementation for managing {@link Authority}.
 */
public class AuthorityBaseService<R extends AuthorityRepository, E extends Authority>
    extends BaseServiceImpl<AuthorityRepository, Authority> {

    private final Logger log = LoggerFactory.getLogger(AuthorityBaseService.class);

    private final List<String> relationCacheNames = Arrays.asList(
        com.begcode.monolith.domain.Authority.class.getName() + ".parent",
        com.begcode.monolith.domain.ViewPermission.class.getName() + ".authorities",
        com.begcode.monolith.domain.ApiPermission.class.getName() + ".authorities",
        com.begcode.monolith.domain.Authority.class.getName() + ".children",
        com.begcode.monolith.domain.User.class.getName() + ".authorities"
    );

    protected final AuthorityRepository authorityRepository;

    protected final CacheManager cacheManager;

    protected final AuthorityMapper authorityMapper;

    public AuthorityBaseService(AuthorityRepository authorityRepository, CacheManager cacheManager, AuthorityMapper authorityMapper) {
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
        this.authorityMapper = authorityMapper;
    }

    /**
     * Save a authority.
     *
     * @param authorityDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public AuthorityDTO save(AuthorityDTO authorityDTO) {
        log.debug("Request to save Authority : {}", authorityDTO);
        Authority authority = authorityMapper.toEntity(authorityDTO);
        clearChildrenCache();

        this.saveOrUpdate(authority);
        return findOne(authority.getId()).get();
    }

    /**
     * Update a authority.
     *
     * @param authorityDTO the entity to save.
     * @return the persisted entity.
     */
    public AuthorityDTO update(AuthorityDTO authorityDTO) {
        log.debug("Request to update Authority : {}", authorityDTO);

        Authority authority = authorityMapper.toEntity(authorityDTO);

        authorityRepository.updateById(authority);
        return findOne(authorityDTO.getId()).get();
    }

    /**
     * Partially update a authority.
     *
     * @param authorityDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Optional<AuthorityDTO> partialUpdate(AuthorityDTO authorityDTO) {
        log.debug("Request to partially update Authority : {}", authorityDTO);

        return authorityRepository
            .findById(authorityDTO.getId())
            .map(existingAuthority -> {
                authorityMapper.partialUpdate(existingAuthority, authorityDTO);

                return existingAuthority;
            })
            .map(tempAuthority -> {
                authorityRepository.save(tempAuthority);
                return authorityMapper.toDto(authorityRepository.selectById(tempAuthority.getId()));
            });
    }

    /**
     * Get all the authorities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public IPage<AuthorityDTO> findAll(Page<Authority> pageable) {
        log.debug("Request to get all Authorities");
        return this.page(pageable).convert(authorityMapper::toDto);
    }

    /**
     * Get one authority by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<AuthorityDTO> findOne(Long id) {
        log.debug("Request to get Authority : {}", id);
        return Optional
            .ofNullable(authorityRepository.selectById(id))
            .map(authority -> {
                Binder.bindRelations(authority);
                return authority;
            })
            .map(authorityMapper::toDto);
    }

    /**
     * Delete the authority by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Authority : {}", id);

        Authority authority = authorityRepository.selectById(id);
        if (authority.getChildren() != null) {
            authority.getChildren().forEach(subAuthority -> subAuthority.setParent(null));
            // todo 可能涉及到级联删除，需要手动处理，上述代码无效。
        }

        authorityRepository.deleteById(id);
    }

    /**
     * Get one authority by code.
     *
     * @param code the id of the entity.
     * @return the entity.
     */
    public Optional<AuthorityDTO> findFirstByCode(String code) {
        log.debug("Request to get Authority : {}", code);
        return authorityRepository.findFirstByCode(code).map(authorityMapper::toDto);
    }

    /**
     * Update specified field by authority
     */
    @Transactional
    public void updateBatch(AuthorityDTO changeAuthorityDTO, List<String> fieldNames, List<Long> ids) {
        UpdateWrapper<Authority> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", ids);
        fieldNames.forEach(fieldName ->
            updateWrapper.set(
                CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName),
                BeanUtil.getFieldValue(changeAuthorityDTO, fieldName)
            )
        );
        this.update(updateWrapper);
    }

    // 清除children缓存
    private void clearChildrenCache() {
        Objects.requireNonNull(cacheManager.getCache(com.begcode.monolith.domain.Authority.class.getName() + ".children")).clear();
    }

    protected void clearRelationsCache() {
        this.relationCacheNames.forEach(cacheName -> Optional.ofNullable(cacheManager.getCache(cacheName)).ifPresent(Cache::clear));
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
