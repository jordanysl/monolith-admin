package com.begcode.monolith.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResourceCategoryMapperTest {

    private ResourceCategoryMapper resourceCategoryMapper;

    @BeforeEach
    public void setUp() {
        resourceCategoryMapper = new ResourceCategoryMapperImpl();
    }
}
