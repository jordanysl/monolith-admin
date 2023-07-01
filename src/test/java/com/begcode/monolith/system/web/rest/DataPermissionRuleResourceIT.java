package com.begcode.monolith.system.web.rest;

import static com.begcode.monolith.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.begcode.monolith.IntegrationTest;
import com.begcode.monolith.system.domain.DataPermissionRule;
import com.begcode.monolith.system.repository.DataPermissionRuleRepository;
import com.begcode.monolith.system.service.criteria.DataPermissionRuleCriteria;
import com.begcode.monolith.system.service.dto.DataPermissionRuleDTO;
import com.begcode.monolith.system.service.mapper.DataPermissionRuleMapper;
import com.begcode.monolith.web.rest.TestUtil;
import com.begcode.monolith.web.rest.TestUtil;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DataPermissionRuleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
public class DataPermissionRuleResourceIT {

    private static final String DEFAULT_PERMISSION_ID = "AAAAAAAAAA";
    private static final String UPDATED_PERMISSION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COLUMN = "AAAAAAAAAA";
    private static final String UPDATED_COLUMN = "BBBBBBBBBB";

    private static final String DEFAULT_CONDITIONS = "AAAAAAAAAA";
    private static final String UPDATED_CONDITIONS = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISABLED = false;
    private static final Boolean UPDATED_DISABLED = true;

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;
    private static final Long SMALLER_CREATED_BY = 1L - 1L;

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_LAST_MODIFIED_BY = 1L;
    private static final Long UPDATED_LAST_MODIFIED_BY = 2L;
    private static final Long SMALLER_LAST_MODIFIED_BY = 1L - 1L;

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Boolean DEFAULT_DEL_FLAG = false;
    private static final Boolean UPDATED_DEL_FLAG = true;

    private static final ZonedDateTime DEFAULT_DELETED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELETED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DELETED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/data-permission-rules";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DataPermissionRuleRepository dataPermissionRuleRepository;

    @Autowired
    private DataPermissionRuleMapper dataPermissionRuleMapper;

    @Autowired
    private MockMvc restDataPermissionRuleMockMvc;

    private DataPermissionRule dataPermissionRule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataPermissionRule createEntity() {
        DataPermissionRule dataPermissionRule = new DataPermissionRule()
            .permissionId(DEFAULT_PERMISSION_ID)
            .name(DEFAULT_NAME)
            .column(DEFAULT_COLUMN)
            .conditions(DEFAULT_CONDITIONS)
            .value(DEFAULT_VALUE)
            .disabled(DEFAULT_DISABLED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .delFlag(DEFAULT_DEL_FLAG)
            .deletedTime(DEFAULT_DELETED_TIME);
        return dataPermissionRule;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataPermissionRule createUpdatedEntity() {
        DataPermissionRule dataPermissionRule = new DataPermissionRule()
            .permissionId(UPDATED_PERMISSION_ID)
            .name(UPDATED_NAME)
            .column(UPDATED_COLUMN)
            .conditions(UPDATED_CONDITIONS)
            .value(UPDATED_VALUE)
            .disabled(UPDATED_DISABLED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .delFlag(UPDATED_DEL_FLAG)
            .deletedTime(UPDATED_DELETED_TIME);
        return dataPermissionRule;
    }

    @BeforeEach
    public void initTest() {
        dataPermissionRule = createEntity();
    }

    @Test
    @Transactional
    void createDataPermissionRule() throws Exception {
        int databaseSizeBeforeCreate = dataPermissionRuleRepository.findAll().size();
        // Create the DataPermissionRule
        DataPermissionRuleDTO dataPermissionRuleDTO = dataPermissionRuleMapper.toDto(dataPermissionRule);
        restDataPermissionRuleMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataPermissionRuleDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DataPermissionRule in the database
        List<DataPermissionRule> dataPermissionRuleList = dataPermissionRuleRepository.findAll();
        assertThat(dataPermissionRuleList).hasSize(databaseSizeBeforeCreate + 1);
        DataPermissionRule testDataPermissionRule = dataPermissionRuleList.get(dataPermissionRuleList.size() - 1);
        assertThat(testDataPermissionRule.getPermissionId()).isEqualTo(DEFAULT_PERMISSION_ID);
        assertThat(testDataPermissionRule.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataPermissionRule.getColumn()).isEqualTo(DEFAULT_COLUMN);
        assertThat(testDataPermissionRule.getConditions()).isEqualTo(DEFAULT_CONDITIONS);
        assertThat(testDataPermissionRule.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testDataPermissionRule.getDisabled()).isEqualTo(DEFAULT_DISABLED);
        assertThat(testDataPermissionRule.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDataPermissionRule.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDataPermissionRule.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testDataPermissionRule.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testDataPermissionRule.getDelFlag()).isEqualTo(DEFAULT_DEL_FLAG);
        assertThat(testDataPermissionRule.getDeletedTime()).isEqualTo(DEFAULT_DELETED_TIME);
    }

    @Test
    @Transactional
    void createDataPermissionRuleWithExistingId() throws Exception {
        // Create the DataPermissionRule with an existing ID
        dataPermissionRule.setId(1L);
        DataPermissionRuleDTO dataPermissionRuleDTO = dataPermissionRuleMapper.toDto(dataPermissionRule);

        int databaseSizeBeforeCreate = dataPermissionRuleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataPermissionRuleMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataPermissionRuleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataPermissionRule in the database
        List<DataPermissionRule> dataPermissionRuleList = dataPermissionRuleRepository.findAll();
        assertThat(dataPermissionRuleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDataPermissionRules() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList
        restDataPermissionRuleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataPermissionRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].permissionId").value(hasItem(DEFAULT_PERMISSION_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].column").value(hasItem(DEFAULT_COLUMN)))
            .andExpect(jsonPath("$.[*].conditions").value(hasItem(DEFAULT_CONDITIONS)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].disabled").value(hasItem(DEFAULT_DISABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.intValue())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].delFlag").value(hasItem(DEFAULT_DEL_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].deletedTime").value(hasItem(sameInstant(DEFAULT_DELETED_TIME))));
    }

    @Test
    @Transactional
    void getDataPermissionRule() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get the dataPermissionRule
        restDataPermissionRuleMockMvc
            .perform(get(ENTITY_API_URL_ID, dataPermissionRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dataPermissionRule.getId().intValue()))
            .andExpect(jsonPath("$.permissionId").value(DEFAULT_PERMISSION_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.column").value(DEFAULT_COLUMN))
            .andExpect(jsonPath("$.conditions").value(DEFAULT_CONDITIONS))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.disabled").value(DEFAULT_DISABLED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.intValue()))
            .andExpect(jsonPath("$.lastModifiedDate").value(sameInstant(DEFAULT_LAST_MODIFIED_DATE)))
            .andExpect(jsonPath("$.delFlag").value(DEFAULT_DEL_FLAG.booleanValue()))
            .andExpect(jsonPath("$.deletedTime").value(sameInstant(DEFAULT_DELETED_TIME)));
    }

    @Test
    @Transactional
    void getDataPermissionRulesByIdFiltering() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        Long id = dataPermissionRule.getId();

        defaultDataPermissionRuleShouldBeFound("id.equals=" + id);
        defaultDataPermissionRuleShouldNotBeFound("id.notEquals=" + id);

        defaultDataPermissionRuleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDataPermissionRuleShouldNotBeFound("id.greaterThan=" + id);

        defaultDataPermissionRuleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDataPermissionRuleShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByPermissionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where permissionId equals to DEFAULT_PERMISSION_ID
        defaultDataPermissionRuleShouldBeFound("permissionId.equals=" + DEFAULT_PERMISSION_ID);

        // Get all the dataPermissionRuleList where permissionId equals to UPDATED_PERMISSION_ID
        defaultDataPermissionRuleShouldNotBeFound("permissionId.equals=" + UPDATED_PERMISSION_ID);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByPermissionIdIsInShouldWork() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where permissionId in DEFAULT_PERMISSION_ID or UPDATED_PERMISSION_ID
        defaultDataPermissionRuleShouldBeFound("permissionId.in=" + DEFAULT_PERMISSION_ID + "," + UPDATED_PERMISSION_ID);

        // Get all the dataPermissionRuleList where permissionId equals to UPDATED_PERMISSION_ID
        defaultDataPermissionRuleShouldNotBeFound("permissionId.in=" + UPDATED_PERMISSION_ID);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByPermissionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where permissionId is not null
        defaultDataPermissionRuleShouldBeFound("permissionId.specified=true");

        // Get all the dataPermissionRuleList where permissionId is null
        defaultDataPermissionRuleShouldNotBeFound("permissionId.specified=false");
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByPermissionIdContainsSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where permissionId contains DEFAULT_PERMISSION_ID
        defaultDataPermissionRuleShouldBeFound("permissionId.contains=" + DEFAULT_PERMISSION_ID);

        // Get all the dataPermissionRuleList where permissionId contains UPDATED_PERMISSION_ID
        defaultDataPermissionRuleShouldNotBeFound("permissionId.contains=" + UPDATED_PERMISSION_ID);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByPermissionIdNotContainsSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where permissionId does not contain DEFAULT_PERMISSION_ID
        defaultDataPermissionRuleShouldNotBeFound("permissionId.doesNotContain=" + DEFAULT_PERMISSION_ID);

        // Get all the dataPermissionRuleList where permissionId does not contain UPDATED_PERMISSION_ID
        defaultDataPermissionRuleShouldBeFound("permissionId.doesNotContain=" + UPDATED_PERMISSION_ID);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where name equals to DEFAULT_NAME
        defaultDataPermissionRuleShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the dataPermissionRuleList where name equals to UPDATED_NAME
        defaultDataPermissionRuleShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDataPermissionRuleShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the dataPermissionRuleList where name equals to UPDATED_NAME
        defaultDataPermissionRuleShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where name is not null
        defaultDataPermissionRuleShouldBeFound("name.specified=true");

        // Get all the dataPermissionRuleList where name is null
        defaultDataPermissionRuleShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByNameContainsSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where name contains DEFAULT_NAME
        defaultDataPermissionRuleShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the dataPermissionRuleList where name contains UPDATED_NAME
        defaultDataPermissionRuleShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where name does not contain DEFAULT_NAME
        defaultDataPermissionRuleShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the dataPermissionRuleList where name does not contain UPDATED_NAME
        defaultDataPermissionRuleShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByColumnIsEqualToSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where column equals to DEFAULT_COLUMN
        defaultDataPermissionRuleShouldBeFound("column.equals=" + DEFAULT_COLUMN);

        // Get all the dataPermissionRuleList where column equals to UPDATED_COLUMN
        defaultDataPermissionRuleShouldNotBeFound("column.equals=" + UPDATED_COLUMN);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByColumnIsInShouldWork() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where column in DEFAULT_COLUMN or UPDATED_COLUMN
        defaultDataPermissionRuleShouldBeFound("column.in=" + DEFAULT_COLUMN + "," + UPDATED_COLUMN);

        // Get all the dataPermissionRuleList where column equals to UPDATED_COLUMN
        defaultDataPermissionRuleShouldNotBeFound("column.in=" + UPDATED_COLUMN);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByColumnIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where column is not null
        defaultDataPermissionRuleShouldBeFound("column.specified=true");

        // Get all the dataPermissionRuleList where column is null
        defaultDataPermissionRuleShouldNotBeFound("column.specified=false");
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByColumnContainsSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where column contains DEFAULT_COLUMN
        defaultDataPermissionRuleShouldBeFound("column.contains=" + DEFAULT_COLUMN);

        // Get all the dataPermissionRuleList where column contains UPDATED_COLUMN
        defaultDataPermissionRuleShouldNotBeFound("column.contains=" + UPDATED_COLUMN);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByColumnNotContainsSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where column does not contain DEFAULT_COLUMN
        defaultDataPermissionRuleShouldNotBeFound("column.doesNotContain=" + DEFAULT_COLUMN);

        // Get all the dataPermissionRuleList where column does not contain UPDATED_COLUMN
        defaultDataPermissionRuleShouldBeFound("column.doesNotContain=" + UPDATED_COLUMN);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByConditionsIsEqualToSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where conditions equals to DEFAULT_CONDITIONS
        defaultDataPermissionRuleShouldBeFound("conditions.equals=" + DEFAULT_CONDITIONS);

        // Get all the dataPermissionRuleList where conditions equals to UPDATED_CONDITIONS
        defaultDataPermissionRuleShouldNotBeFound("conditions.equals=" + UPDATED_CONDITIONS);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByConditionsIsInShouldWork() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where conditions in DEFAULT_CONDITIONS or UPDATED_CONDITIONS
        defaultDataPermissionRuleShouldBeFound("conditions.in=" + DEFAULT_CONDITIONS + "," + UPDATED_CONDITIONS);

        // Get all the dataPermissionRuleList where conditions equals to UPDATED_CONDITIONS
        defaultDataPermissionRuleShouldNotBeFound("conditions.in=" + UPDATED_CONDITIONS);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByConditionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where conditions is not null
        defaultDataPermissionRuleShouldBeFound("conditions.specified=true");

        // Get all the dataPermissionRuleList where conditions is null
        defaultDataPermissionRuleShouldNotBeFound("conditions.specified=false");
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByConditionsContainsSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where conditions contains DEFAULT_CONDITIONS
        defaultDataPermissionRuleShouldBeFound("conditions.contains=" + DEFAULT_CONDITIONS);

        // Get all the dataPermissionRuleList where conditions contains UPDATED_CONDITIONS
        defaultDataPermissionRuleShouldNotBeFound("conditions.contains=" + UPDATED_CONDITIONS);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByConditionsNotContainsSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where conditions does not contain DEFAULT_CONDITIONS
        defaultDataPermissionRuleShouldNotBeFound("conditions.doesNotContain=" + DEFAULT_CONDITIONS);

        // Get all the dataPermissionRuleList where conditions does not contain UPDATED_CONDITIONS
        defaultDataPermissionRuleShouldBeFound("conditions.doesNotContain=" + UPDATED_CONDITIONS);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where value equals to DEFAULT_VALUE
        defaultDataPermissionRuleShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the dataPermissionRuleList where value equals to UPDATED_VALUE
        defaultDataPermissionRuleShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByValueIsInShouldWork() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultDataPermissionRuleShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the dataPermissionRuleList where value equals to UPDATED_VALUE
        defaultDataPermissionRuleShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where value is not null
        defaultDataPermissionRuleShouldBeFound("value.specified=true");

        // Get all the dataPermissionRuleList where value is null
        defaultDataPermissionRuleShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByValueContainsSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where value contains DEFAULT_VALUE
        defaultDataPermissionRuleShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the dataPermissionRuleList where value contains UPDATED_VALUE
        defaultDataPermissionRuleShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByValueNotContainsSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where value does not contain DEFAULT_VALUE
        defaultDataPermissionRuleShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the dataPermissionRuleList where value does not contain UPDATED_VALUE
        defaultDataPermissionRuleShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByDisabledIsEqualToSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where disabled equals to DEFAULT_DISABLED
        defaultDataPermissionRuleShouldBeFound("disabled.equals=" + DEFAULT_DISABLED);

        // Get all the dataPermissionRuleList where disabled equals to UPDATED_DISABLED
        defaultDataPermissionRuleShouldNotBeFound("disabled.equals=" + UPDATED_DISABLED);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByDisabledIsInShouldWork() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where disabled in DEFAULT_DISABLED or UPDATED_DISABLED
        defaultDataPermissionRuleShouldBeFound("disabled.in=" + DEFAULT_DISABLED + "," + UPDATED_DISABLED);

        // Get all the dataPermissionRuleList where disabled equals to UPDATED_DISABLED
        defaultDataPermissionRuleShouldNotBeFound("disabled.in=" + UPDATED_DISABLED);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByDisabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where disabled is not null
        defaultDataPermissionRuleShouldBeFound("disabled.specified=true");

        // Get all the dataPermissionRuleList where disabled is null
        defaultDataPermissionRuleShouldNotBeFound("disabled.specified=false");
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where createdBy equals to DEFAULT_CREATED_BY
        defaultDataPermissionRuleShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the dataPermissionRuleList where createdBy equals to UPDATED_CREATED_BY
        defaultDataPermissionRuleShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultDataPermissionRuleShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the dataPermissionRuleList where createdBy equals to UPDATED_CREATED_BY
        defaultDataPermissionRuleShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where createdBy is not null
        defaultDataPermissionRuleShouldBeFound("createdBy.specified=true");

        // Get all the dataPermissionRuleList where createdBy is null
        defaultDataPermissionRuleShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByCreatedByIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where createdBy is greater than or equal to DEFAULT_CREATED_BY
        defaultDataPermissionRuleShouldBeFound("createdBy.greaterThanOrEqual=" + DEFAULT_CREATED_BY);

        // Get all the dataPermissionRuleList where createdBy is greater than or equal to UPDATED_CREATED_BY
        defaultDataPermissionRuleShouldNotBeFound("createdBy.greaterThanOrEqual=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByCreatedByIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where createdBy is less than or equal to DEFAULT_CREATED_BY
        defaultDataPermissionRuleShouldBeFound("createdBy.lessThanOrEqual=" + DEFAULT_CREATED_BY);

        // Get all the dataPermissionRuleList where createdBy is less than or equal to SMALLER_CREATED_BY
        defaultDataPermissionRuleShouldNotBeFound("createdBy.lessThanOrEqual=" + SMALLER_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByCreatedByIsLessThanSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where createdBy is less than DEFAULT_CREATED_BY
        defaultDataPermissionRuleShouldNotBeFound("createdBy.lessThan=" + DEFAULT_CREATED_BY);

        // Get all the dataPermissionRuleList where createdBy is less than UPDATED_CREATED_BY
        defaultDataPermissionRuleShouldBeFound("createdBy.lessThan=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByCreatedByIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where createdBy is greater than DEFAULT_CREATED_BY
        defaultDataPermissionRuleShouldNotBeFound("createdBy.greaterThan=" + DEFAULT_CREATED_BY);

        // Get all the dataPermissionRuleList where createdBy is greater than SMALLER_CREATED_BY
        defaultDataPermissionRuleShouldBeFound("createdBy.greaterThan=" + SMALLER_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where createdDate equals to DEFAULT_CREATED_DATE
        defaultDataPermissionRuleShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the dataPermissionRuleList where createdDate equals to UPDATED_CREATED_DATE
        defaultDataPermissionRuleShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultDataPermissionRuleShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the dataPermissionRuleList where createdDate equals to UPDATED_CREATED_DATE
        defaultDataPermissionRuleShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where createdDate is not null
        defaultDataPermissionRuleShouldBeFound("createdDate.specified=true");

        // Get all the dataPermissionRuleList where createdDate is null
        defaultDataPermissionRuleShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultDataPermissionRuleShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the dataPermissionRuleList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultDataPermissionRuleShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultDataPermissionRuleShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the dataPermissionRuleList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultDataPermissionRuleShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where createdDate is less than DEFAULT_CREATED_DATE
        defaultDataPermissionRuleShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the dataPermissionRuleList where createdDate is less than UPDATED_CREATED_DATE
        defaultDataPermissionRuleShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultDataPermissionRuleShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the dataPermissionRuleList where createdDate is greater than SMALLER_CREATED_DATE
        defaultDataPermissionRuleShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultDataPermissionRuleShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the dataPermissionRuleList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultDataPermissionRuleShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultDataPermissionRuleShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the dataPermissionRuleList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultDataPermissionRuleShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where lastModifiedBy is not null
        defaultDataPermissionRuleShouldBeFound("lastModifiedBy.specified=true");

        // Get all the dataPermissionRuleList where lastModifiedBy is null
        defaultDataPermissionRuleShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByLastModifiedByIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where lastModifiedBy is greater than or equal to DEFAULT_LAST_MODIFIED_BY
        defaultDataPermissionRuleShouldBeFound("lastModifiedBy.greaterThanOrEqual=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the dataPermissionRuleList where lastModifiedBy is greater than or equal to UPDATED_LAST_MODIFIED_BY
        defaultDataPermissionRuleShouldNotBeFound("lastModifiedBy.greaterThanOrEqual=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByLastModifiedByIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where lastModifiedBy is less than or equal to DEFAULT_LAST_MODIFIED_BY
        defaultDataPermissionRuleShouldBeFound("lastModifiedBy.lessThanOrEqual=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the dataPermissionRuleList where lastModifiedBy is less than or equal to SMALLER_LAST_MODIFIED_BY
        defaultDataPermissionRuleShouldNotBeFound("lastModifiedBy.lessThanOrEqual=" + SMALLER_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByLastModifiedByIsLessThanSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where lastModifiedBy is less than DEFAULT_LAST_MODIFIED_BY
        defaultDataPermissionRuleShouldNotBeFound("lastModifiedBy.lessThan=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the dataPermissionRuleList where lastModifiedBy is less than UPDATED_LAST_MODIFIED_BY
        defaultDataPermissionRuleShouldBeFound("lastModifiedBy.lessThan=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByLastModifiedByIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where lastModifiedBy is greater than DEFAULT_LAST_MODIFIED_BY
        defaultDataPermissionRuleShouldNotBeFound("lastModifiedBy.greaterThan=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the dataPermissionRuleList where lastModifiedBy is greater than SMALLER_LAST_MODIFIED_BY
        defaultDataPermissionRuleShouldBeFound("lastModifiedBy.greaterThan=" + SMALLER_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultDataPermissionRuleShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the dataPermissionRuleList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultDataPermissionRuleShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultDataPermissionRuleShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the dataPermissionRuleList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultDataPermissionRuleShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where lastModifiedDate is not null
        defaultDataPermissionRuleShouldBeFound("lastModifiedDate.specified=true");

        // Get all the dataPermissionRuleList where lastModifiedDate is null
        defaultDataPermissionRuleShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByLastModifiedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where lastModifiedDate is greater than or equal to DEFAULT_LAST_MODIFIED_DATE
        defaultDataPermissionRuleShouldBeFound("lastModifiedDate.greaterThanOrEqual=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the dataPermissionRuleList where lastModifiedDate is greater than or equal to UPDATED_LAST_MODIFIED_DATE
        defaultDataPermissionRuleShouldNotBeFound("lastModifiedDate.greaterThanOrEqual=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByLastModifiedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where lastModifiedDate is less than or equal to DEFAULT_LAST_MODIFIED_DATE
        defaultDataPermissionRuleShouldBeFound("lastModifiedDate.lessThanOrEqual=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the dataPermissionRuleList where lastModifiedDate is less than or equal to SMALLER_LAST_MODIFIED_DATE
        defaultDataPermissionRuleShouldNotBeFound("lastModifiedDate.lessThanOrEqual=" + SMALLER_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByLastModifiedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where lastModifiedDate is less than DEFAULT_LAST_MODIFIED_DATE
        defaultDataPermissionRuleShouldNotBeFound("lastModifiedDate.lessThan=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the dataPermissionRuleList where lastModifiedDate is less than UPDATED_LAST_MODIFIED_DATE
        defaultDataPermissionRuleShouldBeFound("lastModifiedDate.lessThan=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByLastModifiedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where lastModifiedDate is greater than DEFAULT_LAST_MODIFIED_DATE
        defaultDataPermissionRuleShouldNotBeFound("lastModifiedDate.greaterThan=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the dataPermissionRuleList where lastModifiedDate is greater than SMALLER_LAST_MODIFIED_DATE
        defaultDataPermissionRuleShouldBeFound("lastModifiedDate.greaterThan=" + SMALLER_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByDelFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where delFlag equals to DEFAULT_DEL_FLAG
        defaultDataPermissionRuleShouldBeFound("delFlag.equals=" + DEFAULT_DEL_FLAG);

        // Get all the dataPermissionRuleList where delFlag equals to UPDATED_DEL_FLAG
        defaultDataPermissionRuleShouldNotBeFound("delFlag.equals=" + UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByDelFlagIsInShouldWork() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where delFlag in DEFAULT_DEL_FLAG or UPDATED_DEL_FLAG
        defaultDataPermissionRuleShouldBeFound("delFlag.in=" + DEFAULT_DEL_FLAG + "," + UPDATED_DEL_FLAG);

        // Get all the dataPermissionRuleList where delFlag equals to UPDATED_DEL_FLAG
        defaultDataPermissionRuleShouldNotBeFound("delFlag.in=" + UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByDelFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where delFlag is not null
        defaultDataPermissionRuleShouldBeFound("delFlag.specified=true");

        // Get all the dataPermissionRuleList where delFlag is null
        defaultDataPermissionRuleShouldNotBeFound("delFlag.specified=false");
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByDeletedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where deletedTime equals to DEFAULT_DELETED_TIME
        defaultDataPermissionRuleShouldBeFound("deletedTime.equals=" + DEFAULT_DELETED_TIME);

        // Get all the dataPermissionRuleList where deletedTime equals to UPDATED_DELETED_TIME
        defaultDataPermissionRuleShouldNotBeFound("deletedTime.equals=" + UPDATED_DELETED_TIME);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByDeletedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where deletedTime in DEFAULT_DELETED_TIME or UPDATED_DELETED_TIME
        defaultDataPermissionRuleShouldBeFound("deletedTime.in=" + DEFAULT_DELETED_TIME + "," + UPDATED_DELETED_TIME);

        // Get all the dataPermissionRuleList where deletedTime equals to UPDATED_DELETED_TIME
        defaultDataPermissionRuleShouldNotBeFound("deletedTime.in=" + UPDATED_DELETED_TIME);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByDeletedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where deletedTime is not null
        defaultDataPermissionRuleShouldBeFound("deletedTime.specified=true");

        // Get all the dataPermissionRuleList where deletedTime is null
        defaultDataPermissionRuleShouldNotBeFound("deletedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByDeletedTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where deletedTime is greater than or equal to DEFAULT_DELETED_TIME
        defaultDataPermissionRuleShouldBeFound("deletedTime.greaterThanOrEqual=" + DEFAULT_DELETED_TIME);

        // Get all the dataPermissionRuleList where deletedTime is greater than or equal to UPDATED_DELETED_TIME
        defaultDataPermissionRuleShouldNotBeFound("deletedTime.greaterThanOrEqual=" + UPDATED_DELETED_TIME);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByDeletedTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where deletedTime is less than or equal to DEFAULT_DELETED_TIME
        defaultDataPermissionRuleShouldBeFound("deletedTime.lessThanOrEqual=" + DEFAULT_DELETED_TIME);

        // Get all the dataPermissionRuleList where deletedTime is less than or equal to SMALLER_DELETED_TIME
        defaultDataPermissionRuleShouldNotBeFound("deletedTime.lessThanOrEqual=" + SMALLER_DELETED_TIME);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByDeletedTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where deletedTime is less than DEFAULT_DELETED_TIME
        defaultDataPermissionRuleShouldNotBeFound("deletedTime.lessThan=" + DEFAULT_DELETED_TIME);

        // Get all the dataPermissionRuleList where deletedTime is less than UPDATED_DELETED_TIME
        defaultDataPermissionRuleShouldBeFound("deletedTime.lessThan=" + UPDATED_DELETED_TIME);
    }

    @Test
    @Transactional
    void getAllDataPermissionRulesByDeletedTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        // Get all the dataPermissionRuleList where deletedTime is greater than DEFAULT_DELETED_TIME
        defaultDataPermissionRuleShouldNotBeFound("deletedTime.greaterThan=" + DEFAULT_DELETED_TIME);

        // Get all the dataPermissionRuleList where deletedTime is greater than SMALLER_DELETED_TIME
        defaultDataPermissionRuleShouldBeFound("deletedTime.greaterThan=" + SMALLER_DELETED_TIME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDataPermissionRuleShouldBeFound(String filter) throws Exception {
        restDataPermissionRuleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataPermissionRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].permissionId").value(hasItem(DEFAULT_PERMISSION_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].column").value(hasItem(DEFAULT_COLUMN)))
            .andExpect(jsonPath("$.[*].conditions").value(hasItem(DEFAULT_CONDITIONS)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].disabled").value(hasItem(DEFAULT_DISABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.intValue())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].delFlag").value(hasItem(DEFAULT_DEL_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].deletedTime").value(hasItem(sameInstant(DEFAULT_DELETED_TIME))));

        // Check, that the count call also returns 1
        restDataPermissionRuleMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDataPermissionRuleShouldNotBeFound(String filter) throws Exception {
        restDataPermissionRuleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDataPermissionRuleMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDataPermissionRule() throws Exception {
        // Get the dataPermissionRule
        restDataPermissionRuleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDataPermissionRule() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        int databaseSizeBeforeUpdate = dataPermissionRuleRepository.findAll().size();

        // Update the dataPermissionRule
        DataPermissionRule updatedDataPermissionRule = dataPermissionRuleRepository.findById(dataPermissionRule.getId()).orElseThrow();
        updatedDataPermissionRule
            .permissionId(UPDATED_PERMISSION_ID)
            .name(UPDATED_NAME)
            .column(UPDATED_COLUMN)
            .conditions(UPDATED_CONDITIONS)
            .value(UPDATED_VALUE)
            .disabled(UPDATED_DISABLED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .delFlag(UPDATED_DEL_FLAG)
            .deletedTime(UPDATED_DELETED_TIME);
        DataPermissionRuleDTO dataPermissionRuleDTO = dataPermissionRuleMapper.toDto(updatedDataPermissionRule);

        restDataPermissionRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dataPermissionRuleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataPermissionRuleDTO))
            )
            .andExpect(status().isOk());

        // Validate the DataPermissionRule in the database
        List<DataPermissionRule> dataPermissionRuleList = dataPermissionRuleRepository.findAll();
        assertThat(dataPermissionRuleList).hasSize(databaseSizeBeforeUpdate);
        DataPermissionRule testDataPermissionRule = dataPermissionRuleList.get(dataPermissionRuleList.size() - 1);
        assertThat(testDataPermissionRule.getPermissionId()).isEqualTo(UPDATED_PERMISSION_ID);
        assertThat(testDataPermissionRule.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataPermissionRule.getColumn()).isEqualTo(UPDATED_COLUMN);
        assertThat(testDataPermissionRule.getConditions()).isEqualTo(UPDATED_CONDITIONS);
        assertThat(testDataPermissionRule.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testDataPermissionRule.getDisabled()).isEqualTo(UPDATED_DISABLED);
        assertThat(testDataPermissionRule.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDataPermissionRule.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDataPermissionRule.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testDataPermissionRule.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testDataPermissionRule.getDelFlag()).isEqualTo(UPDATED_DEL_FLAG);
        assertThat(testDataPermissionRule.getDeletedTime()).isEqualTo(UPDATED_DELETED_TIME);
    }

    @Test
    @Transactional
    void putNonExistingDataPermissionRule() throws Exception {
        int databaseSizeBeforeUpdate = dataPermissionRuleRepository.findAll().size();
        dataPermissionRule.setId(count.incrementAndGet());

        // Create the DataPermissionRule
        DataPermissionRuleDTO dataPermissionRuleDTO = dataPermissionRuleMapper.toDto(dataPermissionRule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataPermissionRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dataPermissionRuleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataPermissionRuleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataPermissionRule in the database
        List<DataPermissionRule> dataPermissionRuleList = dataPermissionRuleRepository.findAll();
        assertThat(dataPermissionRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDataPermissionRule() throws Exception {
        int databaseSizeBeforeUpdate = dataPermissionRuleRepository.findAll().size();
        dataPermissionRule.setId(count.incrementAndGet());

        // Create the DataPermissionRule
        DataPermissionRuleDTO dataPermissionRuleDTO = dataPermissionRuleMapper.toDto(dataPermissionRule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataPermissionRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataPermissionRuleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataPermissionRule in the database
        List<DataPermissionRule> dataPermissionRuleList = dataPermissionRuleRepository.findAll();
        assertThat(dataPermissionRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDataPermissionRule() throws Exception {
        int databaseSizeBeforeUpdate = dataPermissionRuleRepository.findAll().size();
        dataPermissionRule.setId(count.incrementAndGet());

        // Create the DataPermissionRule
        DataPermissionRuleDTO dataPermissionRuleDTO = dataPermissionRuleMapper.toDto(dataPermissionRule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataPermissionRuleMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataPermissionRuleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DataPermissionRule in the database
        List<DataPermissionRule> dataPermissionRuleList = dataPermissionRuleRepository.findAll();
        assertThat(dataPermissionRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDataPermissionRuleWithPatch() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        int databaseSizeBeforeUpdate = dataPermissionRuleRepository.findAll().size();

        // Update the dataPermissionRule using partial update
        DataPermissionRule partialUpdatedDataPermissionRule = new DataPermissionRule();
        partialUpdatedDataPermissionRule.setId(dataPermissionRule.getId());

        partialUpdatedDataPermissionRule
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restDataPermissionRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDataPermissionRule.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDataPermissionRule))
            )
            .andExpect(status().isOk());

        // Validate the DataPermissionRule in the database
        List<DataPermissionRule> dataPermissionRuleList = dataPermissionRuleRepository.findAll();
        assertThat(dataPermissionRuleList).hasSize(databaseSizeBeforeUpdate);
        DataPermissionRule testDataPermissionRule = dataPermissionRuleList.get(dataPermissionRuleList.size() - 1);
        assertThat(testDataPermissionRule.getPermissionId()).isEqualTo(DEFAULT_PERMISSION_ID);
        assertThat(testDataPermissionRule.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataPermissionRule.getColumn()).isEqualTo(DEFAULT_COLUMN);
        assertThat(testDataPermissionRule.getConditions()).isEqualTo(DEFAULT_CONDITIONS);
        assertThat(testDataPermissionRule.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testDataPermissionRule.getDisabled()).isEqualTo(DEFAULT_DISABLED);
        assertThat(testDataPermissionRule.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDataPermissionRule.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDataPermissionRule.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testDataPermissionRule.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testDataPermissionRule.getDelFlag()).isEqualTo(DEFAULT_DEL_FLAG);
        assertThat(testDataPermissionRule.getDeletedTime()).isEqualTo(DEFAULT_DELETED_TIME);
    }

    @Test
    @Transactional
    void fullUpdateDataPermissionRuleWithPatch() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        int databaseSizeBeforeUpdate = dataPermissionRuleRepository.findAll().size();

        // Update the dataPermissionRule using partial update
        DataPermissionRule partialUpdatedDataPermissionRule = new DataPermissionRule();
        partialUpdatedDataPermissionRule.setId(dataPermissionRule.getId());

        partialUpdatedDataPermissionRule
            .permissionId(UPDATED_PERMISSION_ID)
            .name(UPDATED_NAME)
            .column(UPDATED_COLUMN)
            .conditions(UPDATED_CONDITIONS)
            .value(UPDATED_VALUE)
            .disabled(UPDATED_DISABLED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .delFlag(UPDATED_DEL_FLAG)
            .deletedTime(UPDATED_DELETED_TIME);

        restDataPermissionRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDataPermissionRule.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDataPermissionRule))
            )
            .andExpect(status().isOk());

        // Validate the DataPermissionRule in the database
        List<DataPermissionRule> dataPermissionRuleList = dataPermissionRuleRepository.findAll();
        assertThat(dataPermissionRuleList).hasSize(databaseSizeBeforeUpdate);
        DataPermissionRule testDataPermissionRule = dataPermissionRuleList.get(dataPermissionRuleList.size() - 1);
        assertThat(testDataPermissionRule.getPermissionId()).isEqualTo(UPDATED_PERMISSION_ID);
        assertThat(testDataPermissionRule.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataPermissionRule.getColumn()).isEqualTo(UPDATED_COLUMN);
        assertThat(testDataPermissionRule.getConditions()).isEqualTo(UPDATED_CONDITIONS);
        assertThat(testDataPermissionRule.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testDataPermissionRule.getDisabled()).isEqualTo(UPDATED_DISABLED);
        assertThat(testDataPermissionRule.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDataPermissionRule.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDataPermissionRule.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testDataPermissionRule.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testDataPermissionRule.getDelFlag()).isEqualTo(UPDATED_DEL_FLAG);
        assertThat(testDataPermissionRule.getDeletedTime()).isEqualTo(UPDATED_DELETED_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingDataPermissionRule() throws Exception {
        int databaseSizeBeforeUpdate = dataPermissionRuleRepository.findAll().size();
        dataPermissionRule.setId(count.incrementAndGet());

        // Create the DataPermissionRule
        DataPermissionRuleDTO dataPermissionRuleDTO = dataPermissionRuleMapper.toDto(dataPermissionRule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataPermissionRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dataPermissionRuleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataPermissionRuleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataPermissionRule in the database
        List<DataPermissionRule> dataPermissionRuleList = dataPermissionRuleRepository.findAll();
        assertThat(dataPermissionRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDataPermissionRule() throws Exception {
        int databaseSizeBeforeUpdate = dataPermissionRuleRepository.findAll().size();
        dataPermissionRule.setId(count.incrementAndGet());

        // Create the DataPermissionRule
        DataPermissionRuleDTO dataPermissionRuleDTO = dataPermissionRuleMapper.toDto(dataPermissionRule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataPermissionRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataPermissionRuleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataPermissionRule in the database
        List<DataPermissionRule> dataPermissionRuleList = dataPermissionRuleRepository.findAll();
        assertThat(dataPermissionRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDataPermissionRule() throws Exception {
        int databaseSizeBeforeUpdate = dataPermissionRuleRepository.findAll().size();
        dataPermissionRule.setId(count.incrementAndGet());

        // Create the DataPermissionRule
        DataPermissionRuleDTO dataPermissionRuleDTO = dataPermissionRuleMapper.toDto(dataPermissionRule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataPermissionRuleMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataPermissionRuleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DataPermissionRule in the database
        List<DataPermissionRule> dataPermissionRuleList = dataPermissionRuleRepository.findAll();
        assertThat(dataPermissionRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDataPermissionRule() throws Exception {
        // Initialize the database
        dataPermissionRuleRepository.save(dataPermissionRule);

        int databaseSizeBeforeDelete = dataPermissionRuleRepository.findAll().size();

        // Delete the dataPermissionRule
        restDataPermissionRuleMockMvc
            .perform(delete(ENTITY_API_URL_ID, dataPermissionRule.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DataPermissionRule> dataPermissionRuleList = dataPermissionRuleRepository.findAll();
        assertThat(dataPermissionRuleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
