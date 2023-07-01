package com.begcode.monolith.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UploadFileMapperTest {

    private UploadFileMapper uploadFileMapper;

    @BeforeEach
    public void setUp() {
        uploadFileMapper = new UploadFileMapperImpl();
    }
}
