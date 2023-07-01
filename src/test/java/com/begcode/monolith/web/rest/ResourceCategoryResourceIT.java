package com.begcode.monolith.web.rest;

import static com.begcode.monolith.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.IntegrationTest;
import com.begcode.monolith.domain.ResourceCategory;
import com.begcode.monolith.domain.ResourceCategory;
import com.begcode.monolith.domain.UploadFile;
import com.begcode.monolith.domain.UploadImage;
import com.begcode.monolith.repository.ResourceCategoryRepository;
import com.begcode.monolith.service.ResourceCategoryService;
import com.begcode.monolith.service.criteria.ResourceCategoryCriteria;
import com.begcode.monolith.service.dto.ResourceCategoryDTO;
import com.begcode.monolith.service.mapper.ResourceCategoryMapper;
import com.begcode.monolith.web.rest.ResourceCategoryResourceIT;
import com.begcode.monolith.web.rest.TestUtil;
import com.begcode.monolith.web.rest.UploadFileResourceIT;
import com.begcode.monolith.web.rest.UploadImageResourceIT;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ResourceCategoryResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ResourceCategoryResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER_NUMBER = 1;
    private static final Integer UPDATED_ORDER_NUMBER = 2;
    private static final Integer SMALLER_ORDER_NUMBER = 1 - 1;

    private static final Boolean DEFAULT_DEL_FLAG = false;
    private static final Boolean UPDATED_DEL_FLAG = true;

    private static final ZonedDateTime DEFAULT_DELETED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELETED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DELETED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/resource-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResourceCategoryRepository resourceCategoryRepository;

    @Mock
    private ResourceCategoryRepository resourceCategoryRepositoryMock;

    @Autowired
    private ResourceCategoryMapper resourceCategoryMapper;

    @Mock
    private ResourceCategoryService resourceCategoryServiceMock;

    @Autowired
    private MockMvc restResourceCategoryMockMvc;

    private ResourceCategory resourceCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResourceCategory createEntity() {
        ResourceCategory resourceCategory = new ResourceCategory()
            .title(DEFAULT_TITLE)
            .code(DEFAULT_CODE)
            .orderNumber(DEFAULT_ORDER_NUMBER)
            .delFlag(DEFAULT_DEL_FLAG)
            .deletedTime(DEFAULT_DELETED_TIME);
        return resourceCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResourceCategory createUpdatedEntity() {
        ResourceCategory resourceCategory = new ResourceCategory()
            .title(UPDATED_TITLE)
            .code(UPDATED_CODE)
            .orderNumber(UPDATED_ORDER_NUMBER)
            .delFlag(UPDATED_DEL_FLAG)
            .deletedTime(UPDATED_DELETED_TIME);
        return resourceCategory;
    }

    @BeforeEach
    public void initTest() {
        resourceCategory = createEntity();
    }

    @Test
    @Transactional
    void createResourceCategory() throws Exception {
        int databaseSizeBeforeCreate = resourceCategoryRepository.findAll().size();
        // Create the ResourceCategory
        ResourceCategoryDTO resourceCategoryDTO = resourceCategoryMapper.toDto(resourceCategory);
        restResourceCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resourceCategoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ResourceCategory in the database
        List<ResourceCategory> resourceCategoryList = resourceCategoryRepository.findAll();
        assertThat(resourceCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        ResourceCategory testResourceCategory = resourceCategoryList.get(resourceCategoryList.size() - 1);
        assertThat(testResourceCategory.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testResourceCategory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testResourceCategory.getOrderNumber()).isEqualTo(DEFAULT_ORDER_NUMBER);
        assertThat(testResourceCategory.getDelFlag()).isEqualTo(DEFAULT_DEL_FLAG);
        assertThat(testResourceCategory.getDeletedTime()).isEqualTo(DEFAULT_DELETED_TIME);
    }

    @Test
    @Transactional
    void createResourceCategoryWithExistingId() throws Exception {
        // Create the ResourceCategory with an existing ID
        resourceCategory.setId(1L);
        ResourceCategoryDTO resourceCategoryDTO = resourceCategoryMapper.toDto(resourceCategory);

        int databaseSizeBeforeCreate = resourceCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResourceCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resourceCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResourceCategory in the database
        List<ResourceCategory> resourceCategoryList = resourceCategoryRepository.findAll();
        assertThat(resourceCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllResourceCategories() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList
        restResourceCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resourceCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].delFlag").value(hasItem(DEFAULT_DEL_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].deletedTime").value(hasItem(sameInstant(DEFAULT_DELETED_TIME))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllResourceCategoriesWithEagerRelationshipsIsEnabled() throws Exception {
        when(resourceCategoryServiceMock.findAll(any())).thenReturn(new Page().setRecords(new ArrayList<>()));

        restResourceCategoryMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(resourceCategoryServiceMock, times(1)).findAll(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllResourceCategoriesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(resourceCategoryServiceMock.findAll(any())).thenReturn(new Page().setRecords(new ArrayList<>()));

        restResourceCategoryMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(resourceCategoryRepositoryMock, times(1)).findAll();
    }

    @Test
    @Transactional
    void getResourceCategory() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get the resourceCategory
        restResourceCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, resourceCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resourceCategory.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.orderNumber").value(DEFAULT_ORDER_NUMBER))
            .andExpect(jsonPath("$.delFlag").value(DEFAULT_DEL_FLAG.booleanValue()))
            .andExpect(jsonPath("$.deletedTime").value(sameInstant(DEFAULT_DELETED_TIME)));
    }

    @Test
    @Transactional
    void getResourceCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        Long id = resourceCategory.getId();

        defaultResourceCategoryShouldBeFound("id.equals=" + id);
        defaultResourceCategoryShouldNotBeFound("id.notEquals=" + id);

        defaultResourceCategoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultResourceCategoryShouldNotBeFound("id.greaterThan=" + id);

        defaultResourceCategoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultResourceCategoryShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where title equals to DEFAULT_TITLE
        defaultResourceCategoryShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the resourceCategoryList where title equals to UPDATED_TITLE
        defaultResourceCategoryShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultResourceCategoryShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the resourceCategoryList where title equals to UPDATED_TITLE
        defaultResourceCategoryShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where title is not null
        defaultResourceCategoryShouldBeFound("title.specified=true");

        // Get all the resourceCategoryList where title is null
        defaultResourceCategoryShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByTitleContainsSomething() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where title contains DEFAULT_TITLE
        defaultResourceCategoryShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the resourceCategoryList where title contains UPDATED_TITLE
        defaultResourceCategoryShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where title does not contain DEFAULT_TITLE
        defaultResourceCategoryShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the resourceCategoryList where title does not contain UPDATED_TITLE
        defaultResourceCategoryShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where code equals to DEFAULT_CODE
        defaultResourceCategoryShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the resourceCategoryList where code equals to UPDATED_CODE
        defaultResourceCategoryShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where code in DEFAULT_CODE or UPDATED_CODE
        defaultResourceCategoryShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the resourceCategoryList where code equals to UPDATED_CODE
        defaultResourceCategoryShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where code is not null
        defaultResourceCategoryShouldBeFound("code.specified=true");

        // Get all the resourceCategoryList where code is null
        defaultResourceCategoryShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByCodeContainsSomething() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where code contains DEFAULT_CODE
        defaultResourceCategoryShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the resourceCategoryList where code contains UPDATED_CODE
        defaultResourceCategoryShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where code does not contain DEFAULT_CODE
        defaultResourceCategoryShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the resourceCategoryList where code does not contain UPDATED_CODE
        defaultResourceCategoryShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByOrderNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where orderNumber equals to DEFAULT_ORDER_NUMBER
        defaultResourceCategoryShouldBeFound("orderNumber.equals=" + DEFAULT_ORDER_NUMBER);

        // Get all the resourceCategoryList where orderNumber equals to UPDATED_ORDER_NUMBER
        defaultResourceCategoryShouldNotBeFound("orderNumber.equals=" + UPDATED_ORDER_NUMBER);
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByOrderNumberIsInShouldWork() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where orderNumber in DEFAULT_ORDER_NUMBER or UPDATED_ORDER_NUMBER
        defaultResourceCategoryShouldBeFound("orderNumber.in=" + DEFAULT_ORDER_NUMBER + "," + UPDATED_ORDER_NUMBER);

        // Get all the resourceCategoryList where orderNumber equals to UPDATED_ORDER_NUMBER
        defaultResourceCategoryShouldNotBeFound("orderNumber.in=" + UPDATED_ORDER_NUMBER);
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByOrderNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where orderNumber is not null
        defaultResourceCategoryShouldBeFound("orderNumber.specified=true");

        // Get all the resourceCategoryList where orderNumber is null
        defaultResourceCategoryShouldNotBeFound("orderNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByOrderNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where orderNumber is greater than or equal to DEFAULT_ORDER_NUMBER
        defaultResourceCategoryShouldBeFound("orderNumber.greaterThanOrEqual=" + DEFAULT_ORDER_NUMBER);

        // Get all the resourceCategoryList where orderNumber is greater than or equal to UPDATED_ORDER_NUMBER
        defaultResourceCategoryShouldNotBeFound("orderNumber.greaterThanOrEqual=" + UPDATED_ORDER_NUMBER);
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByOrderNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where orderNumber is less than or equal to DEFAULT_ORDER_NUMBER
        defaultResourceCategoryShouldBeFound("orderNumber.lessThanOrEqual=" + DEFAULT_ORDER_NUMBER);

        // Get all the resourceCategoryList where orderNumber is less than or equal to SMALLER_ORDER_NUMBER
        defaultResourceCategoryShouldNotBeFound("orderNumber.lessThanOrEqual=" + SMALLER_ORDER_NUMBER);
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByOrderNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where orderNumber is less than DEFAULT_ORDER_NUMBER
        defaultResourceCategoryShouldNotBeFound("orderNumber.lessThan=" + DEFAULT_ORDER_NUMBER);

        // Get all the resourceCategoryList where orderNumber is less than UPDATED_ORDER_NUMBER
        defaultResourceCategoryShouldBeFound("orderNumber.lessThan=" + UPDATED_ORDER_NUMBER);
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByOrderNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where orderNumber is greater than DEFAULT_ORDER_NUMBER
        defaultResourceCategoryShouldNotBeFound("orderNumber.greaterThan=" + DEFAULT_ORDER_NUMBER);

        // Get all the resourceCategoryList where orderNumber is greater than SMALLER_ORDER_NUMBER
        defaultResourceCategoryShouldBeFound("orderNumber.greaterThan=" + SMALLER_ORDER_NUMBER);
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByDelFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where delFlag equals to DEFAULT_DEL_FLAG
        defaultResourceCategoryShouldBeFound("delFlag.equals=" + DEFAULT_DEL_FLAG);

        // Get all the resourceCategoryList where delFlag equals to UPDATED_DEL_FLAG
        defaultResourceCategoryShouldNotBeFound("delFlag.equals=" + UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByDelFlagIsInShouldWork() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where delFlag in DEFAULT_DEL_FLAG or UPDATED_DEL_FLAG
        defaultResourceCategoryShouldBeFound("delFlag.in=" + DEFAULT_DEL_FLAG + "," + UPDATED_DEL_FLAG);

        // Get all the resourceCategoryList where delFlag equals to UPDATED_DEL_FLAG
        defaultResourceCategoryShouldNotBeFound("delFlag.in=" + UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByDelFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where delFlag is not null
        defaultResourceCategoryShouldBeFound("delFlag.specified=true");

        // Get all the resourceCategoryList where delFlag is null
        defaultResourceCategoryShouldNotBeFound("delFlag.specified=false");
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByDeletedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where deletedTime equals to DEFAULT_DELETED_TIME
        defaultResourceCategoryShouldBeFound("deletedTime.equals=" + DEFAULT_DELETED_TIME);

        // Get all the resourceCategoryList where deletedTime equals to UPDATED_DELETED_TIME
        defaultResourceCategoryShouldNotBeFound("deletedTime.equals=" + UPDATED_DELETED_TIME);
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByDeletedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where deletedTime in DEFAULT_DELETED_TIME or UPDATED_DELETED_TIME
        defaultResourceCategoryShouldBeFound("deletedTime.in=" + DEFAULT_DELETED_TIME + "," + UPDATED_DELETED_TIME);

        // Get all the resourceCategoryList where deletedTime equals to UPDATED_DELETED_TIME
        defaultResourceCategoryShouldNotBeFound("deletedTime.in=" + UPDATED_DELETED_TIME);
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByDeletedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where deletedTime is not null
        defaultResourceCategoryShouldBeFound("deletedTime.specified=true");

        // Get all the resourceCategoryList where deletedTime is null
        defaultResourceCategoryShouldNotBeFound("deletedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByDeletedTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where deletedTime is greater than or equal to DEFAULT_DELETED_TIME
        defaultResourceCategoryShouldBeFound("deletedTime.greaterThanOrEqual=" + DEFAULT_DELETED_TIME);

        // Get all the resourceCategoryList where deletedTime is greater than or equal to UPDATED_DELETED_TIME
        defaultResourceCategoryShouldNotBeFound("deletedTime.greaterThanOrEqual=" + UPDATED_DELETED_TIME);
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByDeletedTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where deletedTime is less than or equal to DEFAULT_DELETED_TIME
        defaultResourceCategoryShouldBeFound("deletedTime.lessThanOrEqual=" + DEFAULT_DELETED_TIME);

        // Get all the resourceCategoryList where deletedTime is less than or equal to SMALLER_DELETED_TIME
        defaultResourceCategoryShouldNotBeFound("deletedTime.lessThanOrEqual=" + SMALLER_DELETED_TIME);
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByDeletedTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where deletedTime is less than DEFAULT_DELETED_TIME
        defaultResourceCategoryShouldNotBeFound("deletedTime.lessThan=" + DEFAULT_DELETED_TIME);

        // Get all the resourceCategoryList where deletedTime is less than UPDATED_DELETED_TIME
        defaultResourceCategoryShouldBeFound("deletedTime.lessThan=" + UPDATED_DELETED_TIME);
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByDeletedTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        // Get all the resourceCategoryList where deletedTime is greater than DEFAULT_DELETED_TIME
        defaultResourceCategoryShouldNotBeFound("deletedTime.greaterThan=" + DEFAULT_DELETED_TIME);

        // Get all the resourceCategoryList where deletedTime is greater than SMALLER_DELETED_TIME
        defaultResourceCategoryShouldBeFound("deletedTime.greaterThan=" + SMALLER_DELETED_TIME);
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByFilesIsEqualToSomething() throws Exception {
        UploadFile files;
        if (resourceCategoryRepository.findAll().isEmpty()) {
            resourceCategoryRepository.insert(resourceCategory);
            files = UploadFileResourceIT.createEntity();
        } else {
            // files = uploadFileRepository.findAll().get(0);
            files = new UploadFile(); // todo
        }
        // resourceCategory.addFiles(files);
        resourceCategoryRepository.insert(resourceCategory);
        Long filesId = files.getId();
        // Get all the resourceCategoryList where files equals to filesId
        defaultResourceCategoryShouldBeFound("filesId.equals=" + filesId);

        // Get all the resourceCategoryList where files equals to (filesId + 1)
        defaultResourceCategoryShouldNotBeFound("filesId.equals=" + (filesId + 1));
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByChildrenIsEqualToSomething() throws Exception {
        ResourceCategory children;
        if (resourceCategoryRepository.findAll().isEmpty()) {
            resourceCategoryRepository.insert(resourceCategory);
            children = ResourceCategoryResourceIT.createEntity();
        } else {
            // children = resourceCategoryRepository.findAll().get(0);
            children = new ResourceCategory(); // todo
        }
        // resourceCategory.addChildren(children);
        resourceCategoryRepository.insert(resourceCategory);
        Long childrenId = children.getId();
        // Get all the resourceCategoryList where children equals to childrenId
        defaultResourceCategoryShouldBeFound("childrenId.equals=" + childrenId);

        // Get all the resourceCategoryList where children equals to (childrenId + 1)
        defaultResourceCategoryShouldNotBeFound("childrenId.equals=" + (childrenId + 1));
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByImagesIsEqualToSomething() throws Exception {
        UploadImage images;
        if (resourceCategoryRepository.findAll().isEmpty()) {
            resourceCategoryRepository.insert(resourceCategory);
            images = UploadImageResourceIT.createEntity();
        } else {
            // images = uploadImageRepository.findAll().get(0);
            images = new UploadImage(); // todo
        }
        // resourceCategory.addImages(images);
        resourceCategoryRepository.insert(resourceCategory);
        Long imagesId = images.getId();
        // Get all the resourceCategoryList where images equals to imagesId
        defaultResourceCategoryShouldBeFound("imagesId.equals=" + imagesId);

        // Get all the resourceCategoryList where images equals to (imagesId + 1)
        defaultResourceCategoryShouldNotBeFound("imagesId.equals=" + (imagesId + 1));
    }

    @Test
    @Transactional
    void getAllResourceCategoriesByParentIsEqualToSomething() throws Exception {
        ResourceCategory parent;
        if (resourceCategoryRepository.findAll().isEmpty()) {
            resourceCategoryRepository.insert(resourceCategory);
            parent = ResourceCategoryResourceIT.createEntity();
        } else {
            // parent = resourceCategoryRepository.findAll().get(0);
            parent = new ResourceCategory(); // todo
        }
        resourceCategory.setParent(parent);
        resourceCategoryRepository.insert(resourceCategory);
        Long parentId = parent.getId();
        // Get all the resourceCategoryList where parent equals to parentId
        defaultResourceCategoryShouldBeFound("parentId.equals=" + parentId);

        // Get all the resourceCategoryList where parent equals to (parentId + 1)
        defaultResourceCategoryShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultResourceCategoryShouldBeFound(String filter) throws Exception {
        restResourceCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resourceCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].delFlag").value(hasItem(DEFAULT_DEL_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].deletedTime").value(hasItem(sameInstant(DEFAULT_DELETED_TIME))));

        // Check, that the count call also returns 1
        restResourceCategoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultResourceCategoryShouldNotBeFound(String filter) throws Exception {
        restResourceCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResourceCategoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingResourceCategory() throws Exception {
        // Get the resourceCategory
        restResourceCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingResourceCategory() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        int databaseSizeBeforeUpdate = resourceCategoryRepository.findAll().size();

        // Update the resourceCategory
        ResourceCategory updatedResourceCategory = resourceCategoryRepository.findById(resourceCategory.getId()).orElseThrow();
        updatedResourceCategory
            .title(UPDATED_TITLE)
            .code(UPDATED_CODE)
            .orderNumber(UPDATED_ORDER_NUMBER)
            .delFlag(UPDATED_DEL_FLAG)
            .deletedTime(UPDATED_DELETED_TIME);
        ResourceCategoryDTO resourceCategoryDTO = resourceCategoryMapper.toDto(updatedResourceCategory);

        restResourceCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resourceCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resourceCategoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the ResourceCategory in the database
        List<ResourceCategory> resourceCategoryList = resourceCategoryRepository.findAll();
        assertThat(resourceCategoryList).hasSize(databaseSizeBeforeUpdate);
        ResourceCategory testResourceCategory = resourceCategoryList.get(resourceCategoryList.size() - 1);
        assertThat(testResourceCategory.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testResourceCategory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testResourceCategory.getOrderNumber()).isEqualTo(UPDATED_ORDER_NUMBER);
        assertThat(testResourceCategory.getDelFlag()).isEqualTo(UPDATED_DEL_FLAG);
        assertThat(testResourceCategory.getDeletedTime()).isEqualTo(UPDATED_DELETED_TIME);
    }

    @Test
    @Transactional
    void putNonExistingResourceCategory() throws Exception {
        int databaseSizeBeforeUpdate = resourceCategoryRepository.findAll().size();
        resourceCategory.setId(count.incrementAndGet());

        // Create the ResourceCategory
        ResourceCategoryDTO resourceCategoryDTO = resourceCategoryMapper.toDto(resourceCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResourceCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resourceCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resourceCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResourceCategory in the database
        List<ResourceCategory> resourceCategoryList = resourceCategoryRepository.findAll();
        assertThat(resourceCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResourceCategory() throws Exception {
        int databaseSizeBeforeUpdate = resourceCategoryRepository.findAll().size();
        resourceCategory.setId(count.incrementAndGet());

        // Create the ResourceCategory
        ResourceCategoryDTO resourceCategoryDTO = resourceCategoryMapper.toDto(resourceCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResourceCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resourceCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResourceCategory in the database
        List<ResourceCategory> resourceCategoryList = resourceCategoryRepository.findAll();
        assertThat(resourceCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResourceCategory() throws Exception {
        int databaseSizeBeforeUpdate = resourceCategoryRepository.findAll().size();
        resourceCategory.setId(count.incrementAndGet());

        // Create the ResourceCategory
        ResourceCategoryDTO resourceCategoryDTO = resourceCategoryMapper.toDto(resourceCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResourceCategoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resourceCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResourceCategory in the database
        List<ResourceCategory> resourceCategoryList = resourceCategoryRepository.findAll();
        assertThat(resourceCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResourceCategoryWithPatch() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        int databaseSizeBeforeUpdate = resourceCategoryRepository.findAll().size();

        // Update the resourceCategory using partial update
        ResourceCategory partialUpdatedResourceCategory = new ResourceCategory();
        partialUpdatedResourceCategory.setId(resourceCategory.getId());

        partialUpdatedResourceCategory.title(UPDATED_TITLE).orderNumber(UPDATED_ORDER_NUMBER).delFlag(UPDATED_DEL_FLAG);

        restResourceCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResourceCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResourceCategory))
            )
            .andExpect(status().isOk());

        // Validate the ResourceCategory in the database
        List<ResourceCategory> resourceCategoryList = resourceCategoryRepository.findAll();
        assertThat(resourceCategoryList).hasSize(databaseSizeBeforeUpdate);
        ResourceCategory testResourceCategory = resourceCategoryList.get(resourceCategoryList.size() - 1);
        assertThat(testResourceCategory.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testResourceCategory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testResourceCategory.getOrderNumber()).isEqualTo(UPDATED_ORDER_NUMBER);
        assertThat(testResourceCategory.getDelFlag()).isEqualTo(UPDATED_DEL_FLAG);
        assertThat(testResourceCategory.getDeletedTime()).isEqualTo(DEFAULT_DELETED_TIME);
    }

    @Test
    @Transactional
    void fullUpdateResourceCategoryWithPatch() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        int databaseSizeBeforeUpdate = resourceCategoryRepository.findAll().size();

        // Update the resourceCategory using partial update
        ResourceCategory partialUpdatedResourceCategory = new ResourceCategory();
        partialUpdatedResourceCategory.setId(resourceCategory.getId());

        partialUpdatedResourceCategory
            .title(UPDATED_TITLE)
            .code(UPDATED_CODE)
            .orderNumber(UPDATED_ORDER_NUMBER)
            .delFlag(UPDATED_DEL_FLAG)
            .deletedTime(UPDATED_DELETED_TIME);

        restResourceCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResourceCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResourceCategory))
            )
            .andExpect(status().isOk());

        // Validate the ResourceCategory in the database
        List<ResourceCategory> resourceCategoryList = resourceCategoryRepository.findAll();
        assertThat(resourceCategoryList).hasSize(databaseSizeBeforeUpdate);
        ResourceCategory testResourceCategory = resourceCategoryList.get(resourceCategoryList.size() - 1);
        assertThat(testResourceCategory.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testResourceCategory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testResourceCategory.getOrderNumber()).isEqualTo(UPDATED_ORDER_NUMBER);
        assertThat(testResourceCategory.getDelFlag()).isEqualTo(UPDATED_DEL_FLAG);
        assertThat(testResourceCategory.getDeletedTime()).isEqualTo(UPDATED_DELETED_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingResourceCategory() throws Exception {
        int databaseSizeBeforeUpdate = resourceCategoryRepository.findAll().size();
        resourceCategory.setId(count.incrementAndGet());

        // Create the ResourceCategory
        ResourceCategoryDTO resourceCategoryDTO = resourceCategoryMapper.toDto(resourceCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResourceCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, resourceCategoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resourceCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResourceCategory in the database
        List<ResourceCategory> resourceCategoryList = resourceCategoryRepository.findAll();
        assertThat(resourceCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResourceCategory() throws Exception {
        int databaseSizeBeforeUpdate = resourceCategoryRepository.findAll().size();
        resourceCategory.setId(count.incrementAndGet());

        // Create the ResourceCategory
        ResourceCategoryDTO resourceCategoryDTO = resourceCategoryMapper.toDto(resourceCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResourceCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resourceCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResourceCategory in the database
        List<ResourceCategory> resourceCategoryList = resourceCategoryRepository.findAll();
        assertThat(resourceCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResourceCategory() throws Exception {
        int databaseSizeBeforeUpdate = resourceCategoryRepository.findAll().size();
        resourceCategory.setId(count.incrementAndGet());

        // Create the ResourceCategory
        ResourceCategoryDTO resourceCategoryDTO = resourceCategoryMapper.toDto(resourceCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResourceCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resourceCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResourceCategory in the database
        List<ResourceCategory> resourceCategoryList = resourceCategoryRepository.findAll();
        assertThat(resourceCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResourceCategory() throws Exception {
        // Initialize the database
        resourceCategoryRepository.save(resourceCategory);

        int databaseSizeBeforeDelete = resourceCategoryRepository.findAll().size();

        // Delete the resourceCategory
        restResourceCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, resourceCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResourceCategory> resourceCategoryList = resourceCategoryRepository.findAll();
        assertThat(resourceCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
