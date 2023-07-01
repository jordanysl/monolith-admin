package com.begcode.monolith.settings.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SysFillRuleMapperTest {

    private SysFillRuleMapper sysFillRuleMapper;

    @BeforeEach
    public void setUp() {
        sysFillRuleMapper = new SysFillRuleMapperImpl();
    }
}
