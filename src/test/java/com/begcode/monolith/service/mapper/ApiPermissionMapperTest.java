package com.begcode.monolith.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApiPermissionMapperTest {

    private ApiPermissionMapper apiPermissionMapper;

    @BeforeEach
    public void setUp() {
        apiPermissionMapper = new ApiPermissionMapperImpl();
    }
}
