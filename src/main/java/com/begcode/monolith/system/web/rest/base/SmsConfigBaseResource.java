package com.begcode.monolith.system.web.rest.base;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.begcode.monolith.aop.logging.AutoLog;
import com.begcode.monolith.domain.enumeration.LogType;
import com.begcode.monolith.domain.enumeration.OperateType;
import com.begcode.monolith.system.repository.SmsConfigRepository;
import com.begcode.monolith.system.service.SmsConfigQueryService;
import com.begcode.monolith.system.service.SmsConfigService;
import com.begcode.monolith.system.service.criteria.SmsConfigCriteria;
import com.begcode.monolith.system.service.dto.SmsConfigDTO;
import com.begcode.monolith.util.ExportUtil;
import com.begcode.monolith.util.web.IPageUtil;
import com.begcode.monolith.util.web.PageableUtils;
import com.begcode.monolith.web.rest.errors.BadRequestAlertException;
import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PageRecord;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**

 * 管理实体{@link com.begcode.monolith.system.domain.SmsConfig}的REST Controller。
 */
public class SmsConfigBaseResource {

    protected final Logger log = LoggerFactory.getLogger(SmsConfigBaseResource.class);

    protected static final String ENTITY_NAME = "filesSmsConfig";

    @Value("${jhipster.clientApp.name}")
    protected String applicationName;

    protected final SmsConfigService smsConfigService;

    protected final SmsConfigRepository smsConfigRepository;

    protected final SmsConfigQueryService smsConfigQueryService;

    public SmsConfigBaseResource(
        SmsConfigService smsConfigService,
        SmsConfigRepository smsConfigRepository,
        SmsConfigQueryService smsConfigQueryService
    ) {
        this.smsConfigService = smsConfigService;
        this.smsConfigRepository = smsConfigRepository;
        this.smsConfigQueryService = smsConfigQueryService;
    }

    /**
     * {@code POST  /sms-configs} : Create a new smsConfig.
     *
     * @param smsConfigDTO the smsConfigDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new smsConfigDTO, or with status {@code 400 (Bad Request)} if the smsConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sms-configs")
    @Operation(tags = "新建短信配置", description = "创建并返回一个新的短信配置")
    @AutoLog(value = "新建短信配置", logType = LogType.OPERATE, operateType = OperateType.ADD)
    public ResponseEntity<SmsConfigDTO> createSmsConfig(@RequestBody SmsConfigDTO smsConfigDTO) throws URISyntaxException {
        log.debug("REST request to save SmsConfig : {}", smsConfigDTO);
        if (smsConfigDTO.getId() != null) {
            throw new BadRequestAlertException("A new smsConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SmsConfigDTO result = smsConfigService.save(smsConfigDTO);
        return ResponseEntity
            .created(new URI("/api/sms-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sms-configs/:id} : Updates an existing smsConfig.
     *
     * @param id the id of the smsConfigDTO to save.
     * @param smsConfigDTO the smsConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated smsConfigDTO,
     * or with status {@code 400 (Bad Request)} if the smsConfigDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the smsConfigDTO couldn't be updated.
     */
    @PutMapping("/sms-configs/{id}")
    @Operation(tags = "更新短信配置", description = "根据主键更新并返回一个更新后的短信配置")
    @AutoLog(value = "更新短信配置", logType = LogType.OPERATE, operateType = OperateType.EDIT)
    public ResponseEntity<SmsConfigDTO> updateSmsConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SmsConfigDTO smsConfigDTO,
        @RequestParam(value = "batchIds", required = false) List<Long> batchIds,
        @RequestParam(value = "batchFields", required = false) List<String> batchFields
    ) {
        log.debug("REST request to update SmsConfig : {}, {}", id, smsConfigDTO);
        if (smsConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, smsConfigDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (smsConfigRepository.findById(id).isEmpty()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SmsConfigDTO result = null;
        if (CollectionUtils.isNotEmpty(batchFields) && CollectionUtils.isNotEmpty(batchIds)) {
            batchIds.add(id);
            smsConfigService.updateBatch(smsConfigDTO, batchFields, batchIds);
            result = smsConfigService.findOne(id).get();
        } else {
            result = smsConfigService.update(smsConfigDTO);
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, smsConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sms-configs/:id} : Partial updates given fields of an existing smsConfig, field will ignore if it is null
     *
     * @param id the id of the smsConfigDTO to save.
     * @param smsConfigDTO the smsConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated smsConfigDTO,
     * or with status {@code 400 (Bad Request)} if the smsConfigDTO is not valid,
     * or with status {@code 404 (Not Found)} if the smsConfigDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the smsConfigDTO couldn't be updated.
     */
    @PatchMapping(value = "/sms-configs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @Operation(tags = "部分更新短信配置", description = "根据主键及实体信息实现部分更新，值为null的属性将忽略，并返回一个更新后的短信配置")
    @AutoLog(value = "部分更新短信配置", logType = LogType.OPERATE, operateType = OperateType.EDIT)
    public ResponseEntity<SmsConfigDTO> partialUpdateSmsConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SmsConfigDTO smsConfigDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SmsConfig partially : {}, {}", id, smsConfigDTO);
        if (smsConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, smsConfigDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (smsConfigRepository.findById(id).isEmpty()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SmsConfigDTO> result = smsConfigService.partialUpdate(smsConfigDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, smsConfigDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sms-configs} : get all the smsConfigs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of smsConfigs in body.
     */
    @GetMapping("/sms-configs")
    @Operation(tags = "获取短信配置分页列表", description = "获取短信配置的分页列表数据")
    @AutoLog(value = "获取短信配置分页列表", logType = LogType.OPERATE, operateType = OperateType.LIST)
    public ResponseEntity<PageRecord<SmsConfigDTO>> getAllSmsConfigs(
        SmsConfigCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SmsConfigs by criteria: {}", criteria);

        IPage<SmsConfigDTO> page;
        page = smsConfigQueryService.findByCriteria(criteria, PageableUtils.toPage(pageable));
        PageRecord<SmsConfigDTO> result = new PageRecord<>();
        result.records(page.getRecords()).size(page.getSize()).total(page.getTotal()).page(page.getCurrent());
        HttpHeaders headers = IPageUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(result);
    }

    /**
     * {@code GET  /sms-configs/count} : count all the smsConfigs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sms-configs/count")
    public ResponseEntity<Long> countSmsConfigs(SmsConfigCriteria criteria) {
        log.debug("REST request to count SmsConfigs by criteria: {}", criteria);
        return ResponseEntity.ok().body(smsConfigQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sms-configs/:id} : get the "id" smsConfig.
     *
     * @param id the id of the smsConfigDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the smsConfigDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sms-configs/{id}")
    @Operation(tags = "获取指定主键的短信配置", description = "获取指定主键的短信配置信息")
    @AutoLog(value = "获取指定主键的短信配置", logType = LogType.OPERATE, operateType = OperateType.VIEW)
    public ResponseEntity<SmsConfigDTO> getSmsConfig(@PathVariable Long id) {
        log.debug("REST request to get SmsConfig : {}", id);
        Optional<SmsConfigDTO> smsConfigDTO = smsConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(smsConfigDTO);
    }

    /**
     * {@code DELETE  /sms-configs/:id} : delete the "id" smsConfig.
     *
     * @param id the id of the smsConfigDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sms-configs/{id}")
    @Operation(tags = "删除指定主键的短信配置", description = "删除指定主键的短信配置信息")
    @AutoLog(value = "删除指定主键的短信配置", logType = LogType.OPERATE, operateType = OperateType.DELETE)
    public ResponseEntity<Void> deleteSmsConfig(@PathVariable Long id) {
        log.debug("REST request to delete SmsConfig : {}", id);

        smsConfigService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * GET  /sms-configs/export : export the smsConfigs.
     *
     */
    @GetMapping("/sms-configs/export")
    @Operation(tags = "短信配置EXCEL导出", description = "导出全部短信配置为EXCEL文件")
    @AutoLog(value = "短信配置EXCEL导出", logType = LogType.OPERATE, operateType = OperateType.EXPORT)
    public void exportToExcel(HttpServletResponse response) {
        List<SmsConfigDTO> data = smsConfigService.findAll(new Page<>(1, Integer.MAX_VALUE)).getRecords();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("短信配置一览表", "短信配置"), SmsConfigDTO.class, data);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = "短信配置_" + sdf.format(new Date()) + ".xlsx";
        try {
            ExportUtil.excel(workbook, filename, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * POST  /sms-configs/import : import the smsConfigs from excel file.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the smsConfigDTO, or with status 404 (Not Found)
     */
    @PostMapping("/sms-configs/import")
    @Operation(tags = "短信配置EXCEL导入", description = "根据短信配置EXCEL文件导入全部数据")
    @AutoLog(value = "短信配置EXCEL导入", logType = LogType.OPERATE, operateType = OperateType.IMPORT)
    public ResponseEntity<Void> exportToExcel(MultipartFile file) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<SmsConfigDTO> list = ExcelImportUtil.importExcel(file.getInputStream(), SmsConfigDTO.class, params);
        list.forEach(smsConfigService::save);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code DELETE  /sms-configs} : delete all the "ids" SmsConfigs.
     *
     * @param ids the ids of the articleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sms-configs")
    @Operation(tags = "删除多个短信配置", description = "根据主键删除多个短信配置")
    @AutoLog(value = "删除多个短信配置", logType = LogType.OPERATE, operateType = OperateType.DELETE)
    public ResponseEntity<Void> deleteSmsConfigsByIds(@RequestParam("ids") ArrayList<Long> ids) {
        log.debug("REST request to delete SmsConfigs : {}", ids);
        if (ids != null) {
            ids.forEach(smsConfigService::delete);
        }
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, (ids != null ? ids.toString() : "NoIds")))
            .build();
    }

    @GetMapping("/sms-configs/stats")
    @Operation(tags = "根据条件对短信配置进行统计", description = "条件和统计的配置通过短信配置的Criteria类来实现")
    @AutoLog(value = "根据条件对短信配置进行统计", logType = LogType.OPERATE, operateType = OperateType.STATS)
    public ResponseEntity<List<Map<String, Object>>> stats(SmsConfigCriteria criteria) {
        log.debug("REST request to get stats by criteria: {}", criteria);
        List<Map<String, Object>> statsMapList = smsConfigQueryService.statsByAggregateCriteria(criteria);
        return ResponseEntity.ok().body(statsMapList);
    }
    // jhipster-needle-rest-resource-add-api - JHipster will add getters and setters here, do not remove

}
