package com.begcode.monolith.settings.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.begcode.monolith.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FillRuleItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FillRuleItem.class);
        FillRuleItem fillRuleItem1 = new FillRuleItem();
        fillRuleItem1.setId(1L);
        FillRuleItem fillRuleItem2 = new FillRuleItem();
        fillRuleItem2.setId(fillRuleItem1.getId());
        assertThat(fillRuleItem1).isEqualTo(fillRuleItem2);
        fillRuleItem2.setId(2L);
        assertThat(fillRuleItem1).isNotEqualTo(fillRuleItem2);
        fillRuleItem1.setId(null);
        assertThat(fillRuleItem1).isNotEqualTo(fillRuleItem2);
    }
}
