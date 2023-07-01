package com.begcode.monolith.system.service;

import com.begcode.monolith.system.domain.DataPermissionRule;
import com.begcode.monolith.system.repository.DataPermissionRuleRepository;
import com.begcode.monolith.system.service.base.DataPermissionRuleBaseService;
import com.begcode.monolith.system.service.mapper.DataPermissionRuleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link DataPermissionRule}.
 */
@Service
public class DataPermissionRuleService extends DataPermissionRuleBaseService<DataPermissionRuleRepository, DataPermissionRule> {

    private final Logger log = LoggerFactory.getLogger(DataPermissionRuleService.class);

    public DataPermissionRuleService(
        DataPermissionRuleRepository dataPermissionRuleRepository,
        CacheManager cacheManager,
        DataPermissionRuleMapper dataPermissionRuleMapper
    ) {
        super(dataPermissionRuleRepository, cacheManager, dataPermissionRuleMapper);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
