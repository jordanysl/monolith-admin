package com.begcode.monolith.system.service.mapper;

import com.begcode.monolith.service.mapper.EntityMapper;
import com.begcode.monolith.system.domain.DataPermissionRule;
import com.begcode.monolith.system.service.dto.DataPermissionRuleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DataPermissionRule} and its DTO {@link DataPermissionRuleDTO}.
 */
@Mapper(componentModel = "spring")
public interface DataPermissionRuleMapper extends EntityMapper<DataPermissionRuleDTO, DataPermissionRule> {
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "delFlag", ignore = true)
    @Mapping(target = "deletedTime", ignore = true)
    DataPermissionRule toEntity(DataPermissionRuleDTO dataPermissionRuleDTO);
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
