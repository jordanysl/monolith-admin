package com.begcode.monolith.settings.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.begcode.monolith.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommonFieldDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonFieldData.class);
        CommonFieldData commonFieldData1 = new CommonFieldData();
        commonFieldData1.setId(1L);
        CommonFieldData commonFieldData2 = new CommonFieldData();
        commonFieldData2.setId(commonFieldData1.getId());
        assertThat(commonFieldData1).isEqualTo(commonFieldData2);
        commonFieldData2.setId(2L);
        assertThat(commonFieldData1).isNotEqualTo(commonFieldData2);
        commonFieldData1.setId(null);
        assertThat(commonFieldData1).isNotEqualTo(commonFieldData2);
    }
}
