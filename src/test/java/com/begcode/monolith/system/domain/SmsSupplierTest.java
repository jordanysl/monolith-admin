package com.begcode.monolith.system.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.begcode.monolith.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SmsSupplierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SmsSupplier.class);
        SmsSupplier smsSupplier1 = new SmsSupplier();
        smsSupplier1.setId(1L);
        SmsSupplier smsSupplier2 = new SmsSupplier();
        smsSupplier2.setId(smsSupplier1.getId());
        assertThat(smsSupplier1).isEqualTo(smsSupplier2);
        smsSupplier2.setId(2L);
        assertThat(smsSupplier1).isNotEqualTo(smsSupplier2);
        smsSupplier1.setId(null);
        assertThat(smsSupplier1).isNotEqualTo(smsSupplier2);
    }
}
