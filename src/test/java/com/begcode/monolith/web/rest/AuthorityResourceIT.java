package com.begcode.monolith.web.rest;

import static com.begcode.monolith.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.IntegrationTest;
import com.begcode.monolith.domain.ApiPermission;
import com.begcode.monolith.domain.Authority;
import com.begcode.monolith.domain.Authority;
import com.begcode.monolith.domain.User;
import com.begcode.monolith.domain.ViewPermission;
import com.begcode.monolith.repository.AuthorityRepository;
import com.begcode.monolith.service.AuthorityService;
import com.begcode.monolith.service.criteria.AuthorityCriteria;
import com.begcode.monolith.service.dto.AuthorityDTO;
import com.begcode.monolith.service.mapper.AuthorityMapper;
import com.begcode.monolith.web.rest.ApiPermissionResourceIT;
import com.begcode.monolith.web.rest.AuthorityResourceIT;
import com.begcode.monolith.web.rest.TestUtil;
import com.begcode.monolith.web.rest.UserResourceIT;
import com.begcode.monolith.web.rest.ViewPermissionResourceIT;
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
 * Integration tests for the {@link AuthorityResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AuthorityResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;
    private static final Integer SMALLER_ORDER = 1 - 1;

    private static final Boolean DEFAULT_DISPLAY = false;
    private static final Boolean UPDATED_DISPLAY = true;

    private static final Boolean DEFAULT_DEL_FLAG = false;
    private static final Boolean UPDATED_DEL_FLAG = true;

    private static final ZonedDateTime DEFAULT_DELETED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELETED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DELETED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/authorities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AuthorityRepository authorityRepository;

    @Mock
    private AuthorityRepository authorityRepositoryMock;

    @Autowired
    private AuthorityMapper authorityMapper;

    @Mock
    private AuthorityService authorityServiceMock;

    @Autowired
    private MockMvc restAuthorityMockMvc;

    private Authority authority;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Authority createEntity() {
        Authority authority = new Authority()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .info(DEFAULT_INFO)
            .order(DEFAULT_ORDER)
            .display(DEFAULT_DISPLAY)
            .delFlag(DEFAULT_DEL_FLAG)
            .deletedTime(DEFAULT_DELETED_TIME);
        return authority;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Authority createUpdatedEntity() {
        Authority authority = new Authority()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .info(UPDATED_INFO)
            .order(UPDATED_ORDER)
            .display(UPDATED_DISPLAY)
            .delFlag(UPDATED_DEL_FLAG)
            .deletedTime(UPDATED_DELETED_TIME);
        return authority;
    }

    @BeforeEach
    public void initTest() {
        authority = createEntity();
    }

    @Test
    @Transactional
    void createAuthority() throws Exception {
        int databaseSizeBeforeCreate = authorityRepository.findAll().size();
        // Create the Authority
        AuthorityDTO authorityDTO = authorityMapper.toDto(authority);
        restAuthorityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(authorityDTO)))
            .andExpect(status().isCreated());

        // Validate the Authority in the database
        List<Authority> authorityList = authorityRepository.findAll();
        assertThat(authorityList).hasSize(databaseSizeBeforeCreate + 1);
        Authority testAuthority = authorityList.get(authorityList.size() - 1);
        assertThat(testAuthority.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAuthority.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAuthority.getInfo()).isEqualTo(DEFAULT_INFO);
        assertThat(testAuthority.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testAuthority.getDisplay()).isEqualTo(DEFAULT_DISPLAY);
        assertThat(testAuthority.getDelFlag()).isEqualTo(DEFAULT_DEL_FLAG);
        assertThat(testAuthority.getDeletedTime()).isEqualTo(DEFAULT_DELETED_TIME);
    }

    @Test
    @Transactional
    void createAuthorityWithExistingId() throws Exception {
        // Create the Authority with an existing ID
        authority.setId(1L);
        AuthorityDTO authorityDTO = authorityMapper.toDto(authority);

        int databaseSizeBeforeCreate = authorityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuthorityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(authorityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Authority in the database
        List<Authority> authorityList = authorityRepository.findAll();
        assertThat(authorityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAuthorities() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList
        restAuthorityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(authority.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].display").value(hasItem(DEFAULT_DISPLAY.booleanValue())))
            .andExpect(jsonPath("$.[*].delFlag").value(hasItem(DEFAULT_DEL_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].deletedTime").value(hasItem(sameInstant(DEFAULT_DELETED_TIME))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAuthoritiesWithEagerRelationshipsIsEnabled() throws Exception {
        when(authorityServiceMock.findAll(any())).thenReturn(new Page().setRecords(new ArrayList<>()));

        restAuthorityMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(authorityServiceMock, times(1)).findAll(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAuthoritiesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(authorityServiceMock.findAll(any())).thenReturn(new Page().setRecords(new ArrayList<>()));

        restAuthorityMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(authorityRepositoryMock, times(1)).findAll();
    }

    @Test
    @Transactional
    void getAuthority() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get the authority
        restAuthorityMockMvc
            .perform(get(ENTITY_API_URL_ID, authority.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(authority.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.display").value(DEFAULT_DISPLAY.booleanValue()))
            .andExpect(jsonPath("$.delFlag").value(DEFAULT_DEL_FLAG.booleanValue()))
            .andExpect(jsonPath("$.deletedTime").value(sameInstant(DEFAULT_DELETED_TIME)));
    }

    @Test
    @Transactional
    void getAuthoritiesByIdFiltering() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        Long id = authority.getId();

        defaultAuthorityShouldBeFound("id.equals=" + id);
        defaultAuthorityShouldNotBeFound("id.notEquals=" + id);

        defaultAuthorityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAuthorityShouldNotBeFound("id.greaterThan=" + id);

        defaultAuthorityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAuthorityShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where name equals to DEFAULT_NAME
        defaultAuthorityShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the authorityList where name equals to UPDATED_NAME
        defaultAuthorityShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAuthorityShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the authorityList where name equals to UPDATED_NAME
        defaultAuthorityShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where name is not null
        defaultAuthorityShouldBeFound("name.specified=true");

        // Get all the authorityList where name is null
        defaultAuthorityShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllAuthoritiesByNameContainsSomething() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where name contains DEFAULT_NAME
        defaultAuthorityShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the authorityList where name contains UPDATED_NAME
        defaultAuthorityShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where name does not contain DEFAULT_NAME
        defaultAuthorityShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the authorityList where name does not contain UPDATED_NAME
        defaultAuthorityShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where code equals to DEFAULT_CODE
        defaultAuthorityShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the authorityList where code equals to UPDATED_CODE
        defaultAuthorityShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where code in DEFAULT_CODE or UPDATED_CODE
        defaultAuthorityShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the authorityList where code equals to UPDATED_CODE
        defaultAuthorityShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where code is not null
        defaultAuthorityShouldBeFound("code.specified=true");

        // Get all the authorityList where code is null
        defaultAuthorityShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllAuthoritiesByCodeContainsSomething() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where code contains DEFAULT_CODE
        defaultAuthorityShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the authorityList where code contains UPDATED_CODE
        defaultAuthorityShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where code does not contain DEFAULT_CODE
        defaultAuthorityShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the authorityList where code does not contain UPDATED_CODE
        defaultAuthorityShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByInfoIsEqualToSomething() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where info equals to DEFAULT_INFO
        defaultAuthorityShouldBeFound("info.equals=" + DEFAULT_INFO);

        // Get all the authorityList where info equals to UPDATED_INFO
        defaultAuthorityShouldNotBeFound("info.equals=" + UPDATED_INFO);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByInfoIsInShouldWork() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where info in DEFAULT_INFO or UPDATED_INFO
        defaultAuthorityShouldBeFound("info.in=" + DEFAULT_INFO + "," + UPDATED_INFO);

        // Get all the authorityList where info equals to UPDATED_INFO
        defaultAuthorityShouldNotBeFound("info.in=" + UPDATED_INFO);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByInfoIsNullOrNotNull() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where info is not null
        defaultAuthorityShouldBeFound("info.specified=true");

        // Get all the authorityList where info is null
        defaultAuthorityShouldNotBeFound("info.specified=false");
    }

    @Test
    @Transactional
    void getAllAuthoritiesByInfoContainsSomething() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where info contains DEFAULT_INFO
        defaultAuthorityShouldBeFound("info.contains=" + DEFAULT_INFO);

        // Get all the authorityList where info contains UPDATED_INFO
        defaultAuthorityShouldNotBeFound("info.contains=" + UPDATED_INFO);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByInfoNotContainsSomething() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where info does not contain DEFAULT_INFO
        defaultAuthorityShouldNotBeFound("info.doesNotContain=" + DEFAULT_INFO);

        // Get all the authorityList where info does not contain UPDATED_INFO
        defaultAuthorityShouldBeFound("info.doesNotContain=" + UPDATED_INFO);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where order equals to DEFAULT_ORDER
        defaultAuthorityShouldBeFound("order.equals=" + DEFAULT_ORDER);

        // Get all the authorityList where order equals to UPDATED_ORDER
        defaultAuthorityShouldNotBeFound("order.equals=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByOrderIsInShouldWork() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where order in DEFAULT_ORDER or UPDATED_ORDER
        defaultAuthorityShouldBeFound("order.in=" + DEFAULT_ORDER + "," + UPDATED_ORDER);

        // Get all the authorityList where order equals to UPDATED_ORDER
        defaultAuthorityShouldNotBeFound("order.in=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where order is not null
        defaultAuthorityShouldBeFound("order.specified=true");

        // Get all the authorityList where order is null
        defaultAuthorityShouldNotBeFound("order.specified=false");
    }

    @Test
    @Transactional
    void getAllAuthoritiesByOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where order is greater than or equal to DEFAULT_ORDER
        defaultAuthorityShouldBeFound("order.greaterThanOrEqual=" + DEFAULT_ORDER);

        // Get all the authorityList where order is greater than or equal to UPDATED_ORDER
        defaultAuthorityShouldNotBeFound("order.greaterThanOrEqual=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByOrderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where order is less than or equal to DEFAULT_ORDER
        defaultAuthorityShouldBeFound("order.lessThanOrEqual=" + DEFAULT_ORDER);

        // Get all the authorityList where order is less than or equal to SMALLER_ORDER
        defaultAuthorityShouldNotBeFound("order.lessThanOrEqual=" + SMALLER_ORDER);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where order is less than DEFAULT_ORDER
        defaultAuthorityShouldNotBeFound("order.lessThan=" + DEFAULT_ORDER);

        // Get all the authorityList where order is less than UPDATED_ORDER
        defaultAuthorityShouldBeFound("order.lessThan=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByOrderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where order is greater than DEFAULT_ORDER
        defaultAuthorityShouldNotBeFound("order.greaterThan=" + DEFAULT_ORDER);

        // Get all the authorityList where order is greater than SMALLER_ORDER
        defaultAuthorityShouldBeFound("order.greaterThan=" + SMALLER_ORDER);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByDisplayIsEqualToSomething() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where display equals to DEFAULT_DISPLAY
        defaultAuthorityShouldBeFound("display.equals=" + DEFAULT_DISPLAY);

        // Get all the authorityList where display equals to UPDATED_DISPLAY
        defaultAuthorityShouldNotBeFound("display.equals=" + UPDATED_DISPLAY);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByDisplayIsInShouldWork() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where display in DEFAULT_DISPLAY or UPDATED_DISPLAY
        defaultAuthorityShouldBeFound("display.in=" + DEFAULT_DISPLAY + "," + UPDATED_DISPLAY);

        // Get all the authorityList where display equals to UPDATED_DISPLAY
        defaultAuthorityShouldNotBeFound("display.in=" + UPDATED_DISPLAY);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByDisplayIsNullOrNotNull() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where display is not null
        defaultAuthorityShouldBeFound("display.specified=true");

        // Get all the authorityList where display is null
        defaultAuthorityShouldNotBeFound("display.specified=false");
    }

    @Test
    @Transactional
    void getAllAuthoritiesByDelFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where delFlag equals to DEFAULT_DEL_FLAG
        defaultAuthorityShouldBeFound("delFlag.equals=" + DEFAULT_DEL_FLAG);

        // Get all the authorityList where delFlag equals to UPDATED_DEL_FLAG
        defaultAuthorityShouldNotBeFound("delFlag.equals=" + UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByDelFlagIsInShouldWork() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where delFlag in DEFAULT_DEL_FLAG or UPDATED_DEL_FLAG
        defaultAuthorityShouldBeFound("delFlag.in=" + DEFAULT_DEL_FLAG + "," + UPDATED_DEL_FLAG);

        // Get all the authorityList where delFlag equals to UPDATED_DEL_FLAG
        defaultAuthorityShouldNotBeFound("delFlag.in=" + UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByDelFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where delFlag is not null
        defaultAuthorityShouldBeFound("delFlag.specified=true");

        // Get all the authorityList where delFlag is null
        defaultAuthorityShouldNotBeFound("delFlag.specified=false");
    }

    @Test
    @Transactional
    void getAllAuthoritiesByDeletedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where deletedTime equals to DEFAULT_DELETED_TIME
        defaultAuthorityShouldBeFound("deletedTime.equals=" + DEFAULT_DELETED_TIME);

        // Get all the authorityList where deletedTime equals to UPDATED_DELETED_TIME
        defaultAuthorityShouldNotBeFound("deletedTime.equals=" + UPDATED_DELETED_TIME);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByDeletedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where deletedTime in DEFAULT_DELETED_TIME or UPDATED_DELETED_TIME
        defaultAuthorityShouldBeFound("deletedTime.in=" + DEFAULT_DELETED_TIME + "," + UPDATED_DELETED_TIME);

        // Get all the authorityList where deletedTime equals to UPDATED_DELETED_TIME
        defaultAuthorityShouldNotBeFound("deletedTime.in=" + UPDATED_DELETED_TIME);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByDeletedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where deletedTime is not null
        defaultAuthorityShouldBeFound("deletedTime.specified=true");

        // Get all the authorityList where deletedTime is null
        defaultAuthorityShouldNotBeFound("deletedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllAuthoritiesByDeletedTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where deletedTime is greater than or equal to DEFAULT_DELETED_TIME
        defaultAuthorityShouldBeFound("deletedTime.greaterThanOrEqual=" + DEFAULT_DELETED_TIME);

        // Get all the authorityList where deletedTime is greater than or equal to UPDATED_DELETED_TIME
        defaultAuthorityShouldNotBeFound("deletedTime.greaterThanOrEqual=" + UPDATED_DELETED_TIME);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByDeletedTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where deletedTime is less than or equal to DEFAULT_DELETED_TIME
        defaultAuthorityShouldBeFound("deletedTime.lessThanOrEqual=" + DEFAULT_DELETED_TIME);

        // Get all the authorityList where deletedTime is less than or equal to SMALLER_DELETED_TIME
        defaultAuthorityShouldNotBeFound("deletedTime.lessThanOrEqual=" + SMALLER_DELETED_TIME);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByDeletedTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where deletedTime is less than DEFAULT_DELETED_TIME
        defaultAuthorityShouldNotBeFound("deletedTime.lessThan=" + DEFAULT_DELETED_TIME);

        // Get all the authorityList where deletedTime is less than UPDATED_DELETED_TIME
        defaultAuthorityShouldBeFound("deletedTime.lessThan=" + UPDATED_DELETED_TIME);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByDeletedTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        // Get all the authorityList where deletedTime is greater than DEFAULT_DELETED_TIME
        defaultAuthorityShouldNotBeFound("deletedTime.greaterThan=" + DEFAULT_DELETED_TIME);

        // Get all the authorityList where deletedTime is greater than SMALLER_DELETED_TIME
        defaultAuthorityShouldBeFound("deletedTime.greaterThan=" + SMALLER_DELETED_TIME);
    }

    @Test
    @Transactional
    void getAllAuthoritiesByChildrenIsEqualToSomething() throws Exception {
        Authority children;
        if (authorityRepository.findAll().isEmpty()) {
            authorityRepository.insert(authority);
            children = AuthorityResourceIT.createEntity();
        } else {
            // children = authorityRepository.findAll().get(0);
            children = new Authority(); // todo
        }
        // authority.addChildren(children);
        authorityRepository.insert(authority);
        Long childrenId = children.getId();
        // Get all the authorityList where children equals to childrenId
        defaultAuthorityShouldBeFound("childrenId.equals=" + childrenId);

        // Get all the authorityList where children equals to (childrenId + 1)
        defaultAuthorityShouldNotBeFound("childrenId.equals=" + (childrenId + 1));
    }

    @Test
    @Transactional
    void getAllAuthoritiesByViewPermissionsIsEqualToSomething() throws Exception {
        ViewPermission viewPermissions;
        if (authorityRepository.findAll().isEmpty()) {
            authorityRepository.insert(authority);
            viewPermissions = ViewPermissionResourceIT.createEntity();
        } else {
            // viewPermissions = viewPermissionRepository.findAll().get(0);
            viewPermissions = new ViewPermission(); // todo
        }
        // authority.addViewPermissions(viewPermissions);
        authorityRepository.insert(authority);
        Long viewPermissionsId = viewPermissions.getId();
        // Get all the authorityList where viewPermissions equals to viewPermissionsId
        defaultAuthorityShouldBeFound("viewPermissionsId.equals=" + viewPermissionsId);

        // Get all the authorityList where viewPermissions equals to (viewPermissionsId + 1)
        defaultAuthorityShouldNotBeFound("viewPermissionsId.equals=" + (viewPermissionsId + 1));
    }

    @Test
    @Transactional
    void getAllAuthoritiesByApiPermissionsIsEqualToSomething() throws Exception {
        ApiPermission apiPermissions;
        if (authorityRepository.findAll().isEmpty()) {
            authorityRepository.insert(authority);
            apiPermissions = ApiPermissionResourceIT.createEntity();
        } else {
            // apiPermissions = apiPermissionRepository.findAll().get(0);
            apiPermissions = new ApiPermission(); // todo
        }
        // authority.addApiPermissions(apiPermissions);
        authorityRepository.insert(authority);
        Long apiPermissionsId = apiPermissions.getId();
        // Get all the authorityList where apiPermissions equals to apiPermissionsId
        defaultAuthorityShouldBeFound("apiPermissionsId.equals=" + apiPermissionsId);

        // Get all the authorityList where apiPermissions equals to (apiPermissionsId + 1)
        defaultAuthorityShouldNotBeFound("apiPermissionsId.equals=" + (apiPermissionsId + 1));
    }

    @Test
    @Transactional
    void getAllAuthoritiesByParentIsEqualToSomething() throws Exception {
        Authority parent;
        if (authorityRepository.findAll().isEmpty()) {
            authorityRepository.insert(authority);
            parent = AuthorityResourceIT.createEntity();
        } else {
            // parent = authorityRepository.findAll().get(0);
            parent = new Authority(); // todo
        }
        authority.setParent(parent);
        authorityRepository.insert(authority);
        Long parentId = parent.getId();
        // Get all the authorityList where parent equals to parentId
        defaultAuthorityShouldBeFound("parentId.equals=" + parentId);

        // Get all the authorityList where parent equals to (parentId + 1)
        defaultAuthorityShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    @Test
    @Transactional
    void getAllAuthoritiesByUsersIsEqualToSomething() throws Exception {
        User users;
        if (authorityRepository.findAll().isEmpty()) {
            authorityRepository.insert(authority);
            users = UserResourceIT.createEntity();
        } else {
            // users = userRepository.findAll().get(0);
            users = new User(); // todo
        }
        // authority.addUsers(users);
        authorityRepository.insert(authority);
        Long usersId = users.getId();
        // Get all the authorityList where users equals to usersId
        defaultAuthorityShouldBeFound("usersId.equals=" + usersId);

        // Get all the authorityList where users equals to (usersId + 1)
        defaultAuthorityShouldNotBeFound("usersId.equals=" + (usersId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAuthorityShouldBeFound(String filter) throws Exception {
        restAuthorityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(authority.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].display").value(hasItem(DEFAULT_DISPLAY.booleanValue())))
            .andExpect(jsonPath("$.[*].delFlag").value(hasItem(DEFAULT_DEL_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].deletedTime").value(hasItem(sameInstant(DEFAULT_DELETED_TIME))));

        // Check, that the count call also returns 1
        restAuthorityMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAuthorityShouldNotBeFound(String filter) throws Exception {
        restAuthorityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAuthorityMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAuthority() throws Exception {
        // Get the authority
        restAuthorityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAuthority() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        int databaseSizeBeforeUpdate = authorityRepository.findAll().size();

        // Update the authority
        Authority updatedAuthority = authorityRepository.findById(authority.getId()).orElseThrow();
        updatedAuthority
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .info(UPDATED_INFO)
            .order(UPDATED_ORDER)
            .display(UPDATED_DISPLAY)
            .delFlag(UPDATED_DEL_FLAG)
            .deletedTime(UPDATED_DELETED_TIME);
        AuthorityDTO authorityDTO = authorityMapper.toDto(updatedAuthority);

        restAuthorityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, authorityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorityDTO))
            )
            .andExpect(status().isOk());

        // Validate the Authority in the database
        List<Authority> authorityList = authorityRepository.findAll();
        assertThat(authorityList).hasSize(databaseSizeBeforeUpdate);
        Authority testAuthority = authorityList.get(authorityList.size() - 1);
        assertThat(testAuthority.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAuthority.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAuthority.getInfo()).isEqualTo(UPDATED_INFO);
        assertThat(testAuthority.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testAuthority.getDisplay()).isEqualTo(UPDATED_DISPLAY);
        assertThat(testAuthority.getDelFlag()).isEqualTo(UPDATED_DEL_FLAG);
        assertThat(testAuthority.getDeletedTime()).isEqualTo(UPDATED_DELETED_TIME);
    }

    @Test
    @Transactional
    void putNonExistingAuthority() throws Exception {
        int databaseSizeBeforeUpdate = authorityRepository.findAll().size();
        authority.setId(count.incrementAndGet());

        // Create the Authority
        AuthorityDTO authorityDTO = authorityMapper.toDto(authority);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthorityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, authorityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Authority in the database
        List<Authority> authorityList = authorityRepository.findAll();
        assertThat(authorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAuthority() throws Exception {
        int databaseSizeBeforeUpdate = authorityRepository.findAll().size();
        authority.setId(count.incrementAndGet());

        // Create the Authority
        AuthorityDTO authorityDTO = authorityMapper.toDto(authority);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Authority in the database
        List<Authority> authorityList = authorityRepository.findAll();
        assertThat(authorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAuthority() throws Exception {
        int databaseSizeBeforeUpdate = authorityRepository.findAll().size();
        authority.setId(count.incrementAndGet());

        // Create the Authority
        AuthorityDTO authorityDTO = authorityMapper.toDto(authority);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(authorityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Authority in the database
        List<Authority> authorityList = authorityRepository.findAll();
        assertThat(authorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAuthorityWithPatch() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        int databaseSizeBeforeUpdate = authorityRepository.findAll().size();

        // Update the authority using partial update
        Authority partialUpdatedAuthority = new Authority();
        partialUpdatedAuthority.setId(authority.getId());

        partialUpdatedAuthority
            .code(UPDATED_CODE)
            .info(UPDATED_INFO)
            .order(UPDATED_ORDER)
            .display(UPDATED_DISPLAY)
            .delFlag(UPDATED_DEL_FLAG);

        restAuthorityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuthority.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuthority))
            )
            .andExpect(status().isOk());

        // Validate the Authority in the database
        List<Authority> authorityList = authorityRepository.findAll();
        assertThat(authorityList).hasSize(databaseSizeBeforeUpdate);
        Authority testAuthority = authorityList.get(authorityList.size() - 1);
        assertThat(testAuthority.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAuthority.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAuthority.getInfo()).isEqualTo(UPDATED_INFO);
        assertThat(testAuthority.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testAuthority.getDisplay()).isEqualTo(UPDATED_DISPLAY);
        assertThat(testAuthority.getDelFlag()).isEqualTo(UPDATED_DEL_FLAG);
        assertThat(testAuthority.getDeletedTime()).isEqualTo(DEFAULT_DELETED_TIME);
    }

    @Test
    @Transactional
    void fullUpdateAuthorityWithPatch() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        int databaseSizeBeforeUpdate = authorityRepository.findAll().size();

        // Update the authority using partial update
        Authority partialUpdatedAuthority = new Authority();
        partialUpdatedAuthority.setId(authority.getId());

        partialUpdatedAuthority
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .info(UPDATED_INFO)
            .order(UPDATED_ORDER)
            .display(UPDATED_DISPLAY)
            .delFlag(UPDATED_DEL_FLAG)
            .deletedTime(UPDATED_DELETED_TIME);

        restAuthorityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuthority.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuthority))
            )
            .andExpect(status().isOk());

        // Validate the Authority in the database
        List<Authority> authorityList = authorityRepository.findAll();
        assertThat(authorityList).hasSize(databaseSizeBeforeUpdate);
        Authority testAuthority = authorityList.get(authorityList.size() - 1);
        assertThat(testAuthority.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAuthority.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAuthority.getInfo()).isEqualTo(UPDATED_INFO);
        assertThat(testAuthority.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testAuthority.getDisplay()).isEqualTo(UPDATED_DISPLAY);
        assertThat(testAuthority.getDelFlag()).isEqualTo(UPDATED_DEL_FLAG);
        assertThat(testAuthority.getDeletedTime()).isEqualTo(UPDATED_DELETED_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingAuthority() throws Exception {
        int databaseSizeBeforeUpdate = authorityRepository.findAll().size();
        authority.setId(count.incrementAndGet());

        // Create the Authority
        AuthorityDTO authorityDTO = authorityMapper.toDto(authority);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthorityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, authorityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(authorityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Authority in the database
        List<Authority> authorityList = authorityRepository.findAll();
        assertThat(authorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAuthority() throws Exception {
        int databaseSizeBeforeUpdate = authorityRepository.findAll().size();
        authority.setId(count.incrementAndGet());

        // Create the Authority
        AuthorityDTO authorityDTO = authorityMapper.toDto(authority);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(authorityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Authority in the database
        List<Authority> authorityList = authorityRepository.findAll();
        assertThat(authorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAuthority() throws Exception {
        int databaseSizeBeforeUpdate = authorityRepository.findAll().size();
        authority.setId(count.incrementAndGet());

        // Create the Authority
        AuthorityDTO authorityDTO = authorityMapper.toDto(authority);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorityMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(authorityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Authority in the database
        List<Authority> authorityList = authorityRepository.findAll();
        assertThat(authorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAuthority() throws Exception {
        // Initialize the database
        authorityRepository.save(authority);

        int databaseSizeBeforeDelete = authorityRepository.findAll().size();

        // Delete the authority
        restAuthorityMockMvc
            .perform(delete(ENTITY_API_URL_ID, authority.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Authority> authorityList = authorityRepository.findAll();
        assertThat(authorityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
