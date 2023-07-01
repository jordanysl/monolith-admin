package com.begcode.monolith.settings.service.mapper;

import com.begcode.monolith.service.mapper.EntityMapper;
import com.begcode.monolith.settings.domain.CommonFieldData;
import com.begcode.monolith.settings.service.dto.CommonFieldDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommonFieldData} and its DTO {@link CommonFieldDataDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommonFieldDataMapper extends EntityMapper<CommonFieldDataDTO, CommonFieldData> {
    @Mapping(target = "delFlag", ignore = true)
    @Mapping(target = "deletedTime", ignore = true)
    CommonFieldData toEntity(CommonFieldDataDTO commonFieldDataDTO);
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
