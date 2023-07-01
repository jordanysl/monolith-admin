package com.begcode.monolith.settings.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SiteConfigMapperTest {

    private SiteConfigMapper siteConfigMapper;

    @BeforeEach
    public void setUp() {
        siteConfigMapper = new SiteConfigMapperImpl();
    }
}
