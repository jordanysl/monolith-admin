package com.begcode.monolith.settings.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegionCodeMapperTest {

    private RegionCodeMapper regionCodeMapper;

    @BeforeEach
    public void setUp() {
        regionCodeMapper = new RegionCodeMapperImpl();
    }
}
