package com.begcode.monolith.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthorityMapperTest {

    private AuthorityMapper authorityMapper;

    @BeforeEach
    public void setUp() {
        authorityMapper = new AuthorityMapperImpl();
    }
}
