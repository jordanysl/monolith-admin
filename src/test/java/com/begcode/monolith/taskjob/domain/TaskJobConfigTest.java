package com.begcode.monolith.taskjob.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.begcode.monolith.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaskJobConfigTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskJobConfig.class);
        TaskJobConfig taskJobConfig1 = new TaskJobConfig();
        taskJobConfig1.setId(1L);
        TaskJobConfig taskJobConfig2 = new TaskJobConfig();
        taskJobConfig2.setId(taskJobConfig1.getId());
        assertThat(taskJobConfig1).isEqualTo(taskJobConfig2);
        taskJobConfig2.setId(2L);
        assertThat(taskJobConfig1).isNotEqualTo(taskJobConfig2);
        taskJobConfig1.setId(null);
        assertThat(taskJobConfig1).isNotEqualTo(taskJobConfig2);
    }
}
