package com.begcode.monolith.system.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SmsTemplateMapperTest {

    private SmsTemplateMapper smsTemplateMapper;

    @BeforeEach
    public void setUp() {
        smsTemplateMapper = new SmsTemplateMapperImpl();
    }
}
