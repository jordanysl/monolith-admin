package com.begcode.monolith.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UploadImageMapperTest {

    private UploadImageMapper uploadImageMapper;

    @BeforeEach
    public void setUp() {
        uploadImageMapper = new UploadImageMapperImpl();
    }
}
