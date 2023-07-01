package com.begcode.monolith.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BusinessTypeMapperTest {

    private BusinessTypeMapper businessTypeMapper;

    @BeforeEach
    public void setUp() {
        businessTypeMapper = new BusinessTypeMapperImpl();
    }
}
