package com.begcode.monolith.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.domain.ViewPermission;
import com.begcode.monolith.repository.ViewPermissionRepository;
import com.begcode.monolith.security.SecurityUtils;
import com.begcode.monolith.service.dto.ViewPermissionDTO;
import com.begcode.monolith.service.mapper.ViewPermissionMapper;
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
 * Service Implementation for managing {@link ViewPermission}.
 */
public class ViewPermissionBaseService<R extends ViewPermissionRepository, E extends ViewPermission>
    extends BaseServiceImpl<ViewPermissionRepository, ViewPermission> {

    private final Logger log = LoggerFactory.getLogger(ViewPermissionBaseService.class);

    private final List<String> relationCacheNames = Arrays.asList(
        com.begcode.monolith.domain.ViewPermission.class.getName() + ".parent",
        com.begcode.monolith.domain.ViewPermission.class.getName() + ".children",
        com.begcode.monolith.domain.Authority.class.getName() + ".viewPermissions"
    );

    protected final ViewPermissionRepository viewPermissionRepository;

    protected final CacheManager cacheManager;

    protected final ViewPermissionMapper viewPermissionMapper;

    public ViewPermissionBaseService(
        ViewPermissionRepository viewPermissionRepository,
        CacheManager cacheManager,
        ViewPermissionMapper viewPermissionMapper
    ) {
        this.viewPermissionRepository = viewPermissionRepository;
        this.cacheManager = cacheManager;
        this.viewPermissionMapper = viewPermissionMapper;
    }

    /**
     * Save a viewPermission.
     *
     * @param viewPermissionDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public ViewPermissionDTO save(ViewPermissionDTO viewPermissionDTO) {
        log.debug("Request to save ViewPermission : {}", viewPermissionDTO);
        ViewPermission viewPermission = viewPermissionMapper.toEntity(viewPermissionDTO);
        clearChildrenCache();

        this.saveOrUpdate(viewPermission);
        return findOne(viewPermission.getId()).get();
    }

    /**
     * Update a viewPermission.
     *
     * @param viewPermissionDTO the entity to save.
     * @return the persisted entity.
     */
    public ViewPermissionDTO update(ViewPermissionDTO viewPermissionDTO) {
        log.debug("Request to update ViewPermission : {}", viewPermissionDTO);

        ViewPermission viewPermission = viewPermissionMapper.toEntity(viewPermissionDTO);

        viewPermissionRepository.updateById(viewPermission);
        return findOne(viewPermissionDTO.getId()).get();
    }

    /**
     * Partially update a viewPermission.
     *
     * @param viewPermissionDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
    public Optional<ViewPermissionDTO> partialUpdate(ViewPermissionDTO viewPermissionDTO) {
        log.debug("Request to partially update ViewPermission : {}", viewPermissionDTO);

        return viewPermissionRepository
            .findById(viewPermissionDTO.getId())
            .map(existingViewPermission -> {
                viewPermissionMapper.partialUpdate(existingViewPermission, viewPermissionDTO);

                return existingViewPermission;
            })
            .map(tempViewPermission -> {
                viewPermissionRepository.save(tempViewPermission);
                return viewPermissionMapper.toDto(viewPermissionRepository.selectById(tempViewPermission.getId()));
            });
    }

    /**
     * Get all the viewPermissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public IPage<ViewPermissionDTO> findAll(Page<ViewPermission> pageable) {
        log.debug("Request to get all ViewPermissions");
        return this.page(pageable).convert(viewPermissionMapper::toDto);
    }

    /**
     * Get one viewPermission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ViewPermissionDTO> findOne(Long id) {
        log.debug("Request to get ViewPermission : {}", id);
        return Optional
            .ofNullable(viewPermissionRepository.selectById(id))
            .map(viewPermission -> {
                Binder.bindRelations(viewPermission);
                return viewPermission;
            })
            .map(viewPermissionMapper::toDto);
    }

    /**
     * Delete the viewPermission by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ViewPermission : {}", id);

        ViewPermission viewPermission = viewPermissionRepository.selectById(id);
        if (viewPermission.getChildren() != null) {
            viewPermission.getChildren().forEach(subViewPermission -> subViewPermission.setParent(null));
            // todo 可能涉及到级联删除，需要手动处理，上述代码无效。
        }

        viewPermissionRepository.deleteById(id);
    }

    /**
     * get all viewPermission by currentUserLogin
     *
     * @return List<ViewPermissionDTO>
     */
    public List<ViewPermissionDTO> getAllByLogin() {
        List<ViewPermission> resultList = new ArrayList<>();
        // 根据login获得用户的角色
        SecurityUtils
            .getCurrentUserId()
            .ifPresent(userId -> {
                List<ViewPermission> viewPermissionsByUserId = this.viewPermissionRepository.findAllViewPermissionsByUserId(userId);
                List<ViewPermission> addViewPermissions = new ArrayList<>();
                viewPermissionsByUserId.forEach(viewPermission -> {
                    Binder.bindRelations(viewPermission, new String[] { "parent", "authorities" });
                    while (viewPermission != null && viewPermission.getParent() != null) {
                        Long parentId = viewPermission.getParent().getId();
                        if (viewPermissionsByUserId.stream().noneMatch(viewPermissionDTO1 -> viewPermissionDTO1.getId().equals(parentId))) {
                            Optional<ViewPermission> oneNoChildren = viewPermissionRepository.findById(parentId);
                            if (oneNoChildren.isPresent()) {
                                addViewPermissions.add(oneNoChildren.get());
                                viewPermission = oneNoChildren.get();
                                Binder.bindRelations(viewPermission, new String[] { "parent", "authorities" });
                            } else {
                                viewPermission = null;
                            }
                        } else {
                            viewPermission = null;
                        }
                    }
                });
                viewPermissionsByUserId.addAll(addViewPermissions);
                // 已经找到所有的当前User相关的ViewPermissions及上级，接下来转换为树形结构。
                viewPermissionsByUserId.forEach(userViewPermission -> {
                    if (userViewPermission.getParent() == null) {
                        Long finalId = userViewPermission.getId();
                        if (resultList.stream().noneMatch(resultViewPermissionDTO -> resultViewPermissionDTO.getId().equals(finalId))) {
                            resultList.add(userViewPermission);
                        }
                    } else {
                        ViewPermission topParent = null;
                        while (userViewPermission != null && userViewPermission.getParent() != null) {
                            Long tempId = userViewPermission.getParent().getId();
                            Optional<ViewPermission> tempViewPermission = viewPermissionsByUserId
                                .stream()
                                .filter(viewPermission -> viewPermission.getId().equals(tempId))
                                .findAny();
                            if (tempViewPermission.isPresent()) {
                                if (tempViewPermission.get().getParent() == null) {
                                    topParent = tempViewPermission.get();
                                    tempViewPermission.get().getChildren().add(userViewPermission);
                                    userViewPermission = null;
                                } else {
                                    tempViewPermission.get().getChildren().add(userViewPermission);
                                    userViewPermission = tempViewPermission.get();
                                }
                            } else {
                                userViewPermission = null;
                            }
                        }
                        if (topParent != null) {
                            ViewPermission finalTopParent = topParent;
                            Optional<ViewPermission> any = resultList
                                .stream()
                                .filter(resultViewPermission -> resultViewPermission.getId().equals(finalTopParent.getId()))
                                .findAny();
                            if (any.isPresent()) {
                                // 处理子列表
                                List<ViewPermission> resultChildren = any.get().getChildren();
                                List<ViewPermission> unCheckChildren = topParent.getChildren();
                                addToResult(resultChildren, unCheckChildren);
                            } else {
                                resultList.add(topParent);
                            }
                        }
                    }
                });
            });
        return viewPermissionMapper.toDto(resultList);
    }

    private void addToResult(List<ViewPermission> resultChildren, List<ViewPermission> unCheckChildren) {
        if (unCheckChildren.size() > 0) {
            unCheckChildren.forEach(child -> {
                Long childId = child.getId();
                Optional<ViewPermission> any = resultChildren
                    .stream()
                    .filter(viewPermission -> viewPermission.getId().equals(childId))
                    .findAny();
                if (any.isPresent()) {
                    addToResult(any.get().getChildren(), child.getChildren());
                } else {
                    resultChildren.add(child);
                }
            });
        }
    }

    /**
     * Update specified field by viewPermission
     */
    @Transactional
    public void updateBatch(ViewPermissionDTO changeViewPermissionDTO, List<String> fieldNames, List<Long> ids) {
        UpdateWrapper<ViewPermission> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", ids);
        fieldNames.forEach(fieldName ->
            updateWrapper.set(
                CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName),
                BeanUtil.getFieldValue(changeViewPermissionDTO, fieldName)
            )
        );
        this.update(updateWrapper);
    }

    // 清除children缓存
    private void clearChildrenCache() {
        Objects.requireNonNull(cacheManager.getCache(com.begcode.monolith.domain.ViewPermission.class.getName() + ".children")).clear();
    }

    protected void clearRelationsCache() {
        this.relationCacheNames.forEach(cacheName -> Optional.ofNullable(cacheManager.getCache(cacheName)).ifPresent(Cache::clear));
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
