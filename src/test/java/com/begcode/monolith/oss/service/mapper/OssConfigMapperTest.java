package com.begcode.monolith.oss.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OssConfigMapperTest {

    private OssConfigMapper ossConfigMapper;

    @BeforeEach
    public void setUp() {
        ossConfigMapper = new OssConfigMapperImpl();
    }
}
