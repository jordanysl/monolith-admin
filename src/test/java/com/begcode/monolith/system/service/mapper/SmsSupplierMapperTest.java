package com.begcode.monolith.system.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SmsSupplierMapperTest {

    private SmsSupplierMapper smsSupplierMapper;

    @BeforeEach
    public void setUp() {
        smsSupplierMapper = new SmsSupplierMapperImpl();
    }
}
