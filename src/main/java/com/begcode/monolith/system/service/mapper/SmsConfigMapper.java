package com.begcode.monolith.system.service.mapper;

import com.begcode.monolith.service.mapper.EntityMapper;
import com.begcode.monolith.system.domain.SmsConfig;
import com.begcode.monolith.system.service.dto.SmsConfigDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SmsConfig} and its DTO {@link SmsConfigDTO}.
 */
@Mapper(componentModel = "spring")
public interface SmsConfigMapper extends EntityMapper<SmsConfigDTO, SmsConfig> {
    @Mapping(target = "delFlag", ignore = true)
    @Mapping(target = "deletedTime", ignore = true)
    SmsConfig toEntity(SmsConfigDTO smsConfigDTO);
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
