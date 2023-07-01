package com.begcode.monolith.system.service;

import com.begcode.monolith.system.domain.SmsConfig;
import com.begcode.monolith.system.repository.SmsConfigRepository;
import com.begcode.monolith.system.service.base.SmsConfigBaseService;
import com.begcode.monolith.system.service.mapper.SmsConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link SmsConfig}.
 */
@Service
public class SmsConfigService extends SmsConfigBaseService<SmsConfigRepository, SmsConfig> {

    private final Logger log = LoggerFactory.getLogger(SmsConfigService.class);

    public SmsConfigService(SmsConfigRepository smsConfigRepository, CacheManager cacheManager, SmsConfigMapper smsConfigMapper) {
        super(smsConfigRepository, cacheManager, smsConfigMapper);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
