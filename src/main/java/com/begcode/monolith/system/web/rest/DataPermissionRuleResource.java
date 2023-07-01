package com.begcode.monolith.system.web.rest;

import com.begcode.monolith.system.repository.DataPermissionRuleRepository;
import com.begcode.monolith.system.service.DataPermissionRuleQueryService;
import com.begcode.monolith.system.service.DataPermissionRuleService;
import com.begcode.monolith.system.web.rest.base.DataPermissionRuleBaseResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**

 * 管理实体{@link com.begcode.monolith.system.domain.DataPermissionRule}的REST Controller。
 */
@RestController
@RequestMapping("/api")
@Tag(name = "data-permission-rules", description = "数据权限规则API接口")
public class DataPermissionRuleResource extends DataPermissionRuleBaseResource {

    private final Logger log = LoggerFactory.getLogger(DataPermissionRuleResource.class);

    public DataPermissionRuleResource(
        DataPermissionRuleService dataPermissionRuleService,
        DataPermissionRuleRepository dataPermissionRuleRepository,
        DataPermissionRuleQueryService dataPermissionRuleQueryService
    ) {
        super(dataPermissionRuleService, dataPermissionRuleRepository, dataPermissionRuleQueryService);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
