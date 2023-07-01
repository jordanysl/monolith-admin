package com.begcode.monolith.taskjob.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskJobConfigMapperTest {

    private TaskJobConfigMapper taskJobConfigMapper;

    @BeforeEach
    public void setUp() {
        taskJobConfigMapper = new TaskJobConfigMapperImpl();
    }
}
