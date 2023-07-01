package com.begcode.monolith.system.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SmsConfigMapperTest {

    private SmsConfigMapper smsConfigMapper;

    @BeforeEach
    public void setUp() {
        smsConfigMapper = new SmsConfigMapperImpl();
    }
}
