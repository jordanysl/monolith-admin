package com.begcode.monolith.service.mapper;

import com.begcode.monolith.domain.UReportFile;
import com.begcode.monolith.service.dto.UReportFileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UReportFile} and its DTO {@link UReportFileDTO}.
 */
@Mapper(componentModel = "spring")
public interface UReportFileMapper extends EntityMapper<UReportFileDTO, UReportFile> {
    @Mapping(target = "delFlag", ignore = true)
    @Mapping(target = "deletedTime", ignore = true)
    UReportFile toEntity(UReportFileDTO uReportFileDTO);
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
