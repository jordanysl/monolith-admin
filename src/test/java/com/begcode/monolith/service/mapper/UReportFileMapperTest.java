package com.begcode.monolith.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UReportFileMapperTest {

    private UReportFileMapper uReportFileMapper;

    @BeforeEach
    public void setUp() {
        uReportFileMapper = new UReportFileMapperImpl();
    }
}
