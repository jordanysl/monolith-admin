package com.begcode.monolith.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.begcode.monolith.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UReportFileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UReportFile.class);
        UReportFile uReportFile1 = new UReportFile();
        uReportFile1.setId(1L);
        UReportFile uReportFile2 = new UReportFile();
        uReportFile2.setId(uReportFile1.getId());
        assertThat(uReportFile1).isEqualTo(uReportFile2);
        uReportFile2.setId(2L);
        assertThat(uReportFile1).isNotEqualTo(uReportFile2);
        uReportFile1.setId(null);
        assertThat(uReportFile1).isNotEqualTo(uReportFile2);
    }
}
