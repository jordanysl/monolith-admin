package com.begcode.monolith.system.web.rest;

import com.begcode.monolith.system.repository.SmsConfigRepository;
import com.begcode.monolith.system.service.SmsConfigQueryService;
import com.begcode.monolith.system.service.SmsConfigService;
import com.begcode.monolith.system.web.rest.base.SmsConfigBaseResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**

 * 管理实体{@link com.begcode.monolith.system.domain.SmsConfig}的REST Controller。
 */
@RestController
@RequestMapping("/api")
@Tag(name = "sms-configs", description = "短信配置API接口")
public class SmsConfigResource extends SmsConfigBaseResource {

    private final Logger log = LoggerFactory.getLogger(SmsConfigResource.class);

    public SmsConfigResource(
        SmsConfigService smsConfigService,
        SmsConfigRepository smsConfigRepository,
        SmsConfigQueryService smsConfigQueryService
    ) {
        super(smsConfigService, smsConfigRepository, smsConfigQueryService);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
