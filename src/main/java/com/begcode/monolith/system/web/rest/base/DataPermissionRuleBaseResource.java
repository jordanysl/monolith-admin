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
import com.begcode.monolith.system.repository.DataPermissionRuleRepository;
import com.begcode.monolith.system.service.DataPermissionRuleQueryService;
import com.begcode.monolith.system.service.DataPermissionRuleService;
import com.begcode.monolith.system.service.criteria.DataPermissionRuleCriteria;
import com.begcode.monolith.system.service.dto.DataPermissionRuleDTO;
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

 * 管理实体{@link com.begcode.monolith.system.domain.DataPermissionRule}的REST Controller。
 */
public class DataPermissionRuleBaseResource {

    protected final Logger log = LoggerFactory.getLogger(DataPermissionRuleBaseResource.class);

    protected static final String ENTITY_NAME = "systemDataPermissionRule";

    @Value("${jhipster.clientApp.name}")
    protected String applicationName;

    protected final DataPermissionRuleService dataPermissionRuleService;

    protected final DataPermissionRuleRepository dataPermissionRuleRepository;

    protected final DataPermissionRuleQueryService dataPermissionRuleQueryService;

    public DataPermissionRuleBaseResource(
        DataPermissionRuleService dataPermissionRuleService,
        DataPermissionRuleRepository dataPermissionRuleRepository,
        DataPermissionRuleQueryService dataPermissionRuleQueryService
    ) {
        this.dataPermissionRuleService = dataPermissionRuleService;
        this.dataPermissionRuleRepository = dataPermissionRuleRepository;
        this.dataPermissionRuleQueryService = dataPermissionRuleQueryService;
    }

    /**
     * {@code POST  /data-permission-rules} : Create a new dataPermissionRule.
     *
     * @param dataPermissionRuleDTO the dataPermissionRuleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dataPermissionRuleDTO, or with status {@code 400 (Bad Request)} if the dataPermissionRule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/data-permission-rules")
    @Operation(tags = "新建数据权限规则", description = "创建并返回一个新的数据权限规则")
    @AutoLog(value = "新建数据权限规则", logType = LogType.OPERATE, operateType = OperateType.ADD)
    public ResponseEntity<DataPermissionRuleDTO> createDataPermissionRule(@RequestBody DataPermissionRuleDTO dataPermissionRuleDTO)
        throws URISyntaxException {
        log.debug("REST request to save DataPermissionRule : {}", dataPermissionRuleDTO);
        if (dataPermissionRuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new dataPermissionRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataPermissionRuleDTO result = dataPermissionRuleService.save(dataPermissionRuleDTO);
        return ResponseEntity
            .created(new URI("/api/data-permission-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /data-permission-rules/:id} : Updates an existing dataPermissionRule.
     *
     * @param id the id of the dataPermissionRuleDTO to save.
     * @param dataPermissionRuleDTO the dataPermissionRuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataPermissionRuleDTO,
     * or with status {@code 400 (Bad Request)} if the dataPermissionRuleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dataPermissionRuleDTO couldn't be updated.
     */
    @PutMapping("/data-permission-rules/{id}")
    @Operation(tags = "更新数据权限规则", description = "根据主键更新并返回一个更新后的数据权限规则")
    @AutoLog(value = "更新数据权限规则", logType = LogType.OPERATE, operateType = OperateType.EDIT)
    public ResponseEntity<DataPermissionRuleDTO> updateDataPermissionRule(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DataPermissionRuleDTO dataPermissionRuleDTO,
        @RequestParam(value = "batchIds", required = false) List<Long> batchIds,
        @RequestParam(value = "batchFields", required = false) List<String> batchFields
    ) {
        log.debug("REST request to update DataPermissionRule : {}, {}", id, dataPermissionRuleDTO);
        if (dataPermissionRuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dataPermissionRuleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (dataPermissionRuleRepository.findById(id).isEmpty()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DataPermissionRuleDTO result = null;
        if (CollectionUtils.isNotEmpty(batchFields) && CollectionUtils.isNotEmpty(batchIds)) {
            batchIds.add(id);
            dataPermissionRuleService.updateBatch(dataPermissionRuleDTO, batchFields, batchIds);
            result = dataPermissionRuleService.findOne(id).get();
        } else {
            result = dataPermissionRuleService.update(dataPermissionRuleDTO);
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataPermissionRuleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /data-permission-rules/:id} : Partial updates given fields of an existing dataPermissionRule, field will ignore if it is null
     *
     * @param id the id of the dataPermissionRuleDTO to save.
     * @param dataPermissionRuleDTO the dataPermissionRuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataPermissionRuleDTO,
     * or with status {@code 400 (Bad Request)} if the dataPermissionRuleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dataPermissionRuleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dataPermissionRuleDTO couldn't be updated.
     */
    @PatchMapping(value = "/data-permission-rules/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @Operation(
        tags = "部分更新数据权限规则",
        description = "根据主键及实体信息实现部分更新，值为null的属性将忽略，并返回一个更新后的数据权限规则"
    )
    @AutoLog(value = "部分更新数据权限规则", logType = LogType.OPERATE, operateType = OperateType.EDIT)
    public ResponseEntity<DataPermissionRuleDTO> partialUpdateDataPermissionRule(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DataPermissionRuleDTO dataPermissionRuleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DataPermissionRule partially : {}, {}", id, dataPermissionRuleDTO);
        if (dataPermissionRuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dataPermissionRuleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (dataPermissionRuleRepository.findById(id).isEmpty()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DataPermissionRuleDTO> result = dataPermissionRuleService.partialUpdate(dataPermissionRuleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataPermissionRuleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /data-permission-rules} : get all the dataPermissionRules.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dataPermissionRules in body.
     */
    @GetMapping("/data-permission-rules")
    @Operation(tags = "获取数据权限规则分页列表", description = "获取数据权限规则的分页列表数据")
    @AutoLog(value = "获取数据权限规则分页列表", logType = LogType.OPERATE, operateType = OperateType.LIST)
    public ResponseEntity<PageRecord<DataPermissionRuleDTO>> getAllDataPermissionRules(
        DataPermissionRuleCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DataPermissionRules by criteria: {}", criteria);

        IPage<DataPermissionRuleDTO> page;
        page = dataPermissionRuleQueryService.findByCriteria(criteria, PageableUtils.toPage(pageable));
        PageRecord<DataPermissionRuleDTO> result = new PageRecord<>();
        result.records(page.getRecords()).size(page.getSize()).total(page.getTotal()).page(page.getCurrent());
        HttpHeaders headers = IPageUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(result);
    }

    /**
     * {@code GET  /data-permission-rules/count} : count all the dataPermissionRules.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/data-permission-rules/count")
    public ResponseEntity<Long> countDataPermissionRules(DataPermissionRuleCriteria criteria) {
        log.debug("REST request to count DataPermissionRules by criteria: {}", criteria);
        return ResponseEntity.ok().body(dataPermissionRuleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /data-permission-rules/:id} : get the "id" dataPermissionRule.
     *
     * @param id the id of the dataPermissionRuleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataPermissionRuleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data-permission-rules/{id}")
    @Operation(tags = "获取指定主键的数据权限规则", description = "获取指定主键的数据权限规则信息")
    @AutoLog(value = "获取指定主键的数据权限规则", logType = LogType.OPERATE, operateType = OperateType.VIEW)
    public ResponseEntity<DataPermissionRuleDTO> getDataPermissionRule(@PathVariable Long id) {
        log.debug("REST request to get DataPermissionRule : {}", id);
        Optional<DataPermissionRuleDTO> dataPermissionRuleDTO = dataPermissionRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataPermissionRuleDTO);
    }

    /**
     * {@code DELETE  /data-permission-rules/:id} : delete the "id" dataPermissionRule.
     *
     * @param id the id of the dataPermissionRuleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data-permission-rules/{id}")
    @Operation(tags = "删除指定主键的数据权限规则", description = "删除指定主键的数据权限规则信息")
    @AutoLog(value = "删除指定主键的数据权限规则", logType = LogType.OPERATE, operateType = OperateType.DELETE)
    public ResponseEntity<Void> deleteDataPermissionRule(@PathVariable Long id) {
        log.debug("REST request to delete DataPermissionRule : {}", id);

        dataPermissionRuleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * GET  /data-permission-rules/export : export the dataPermissionRules.
     *
     */
    @GetMapping("/data-permission-rules/export")
    @Operation(tags = "数据权限规则EXCEL导出", description = "导出全部数据权限规则为EXCEL文件")
    @AutoLog(value = "数据权限规则EXCEL导出", logType = LogType.OPERATE, operateType = OperateType.EXPORT)
    public void exportToExcel(HttpServletResponse response) {
        List<DataPermissionRuleDTO> data = dataPermissionRuleService.findAll(new Page<>(1, Integer.MAX_VALUE)).getRecords();
        Workbook workbook = ExcelExportUtil.exportExcel(
            new ExportParams("数据权限规则一览表", "数据权限规则"),
            DataPermissionRuleDTO.class,
            data
        );
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = "数据权限规则_" + sdf.format(new Date()) + ".xlsx";
        try {
            ExportUtil.excel(workbook, filename, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * POST  /data-permission-rules/import : import the dataPermissionRules from excel file.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the dataPermissionRuleDTO, or with status 404 (Not Found)
     */
    @PostMapping("/data-permission-rules/import")
    @Operation(tags = "数据权限规则EXCEL导入", description = "根据数据权限规则EXCEL文件导入全部数据")
    @AutoLog(value = "数据权限规则EXCEL导入", logType = LogType.OPERATE, operateType = OperateType.IMPORT)
    public ResponseEntity<Void> exportToExcel(MultipartFile file) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<DataPermissionRuleDTO> list = ExcelImportUtil.importExcel(file.getInputStream(), DataPermissionRuleDTO.class, params);
        list.forEach(dataPermissionRuleService::save);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code DELETE  /data-permission-rules} : delete all the "ids" DataPermissionRules.
     *
     * @param ids the ids of the articleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data-permission-rules")
    @Operation(tags = "删除多个数据权限规则", description = "根据主键删除多个数据权限规则")
    @AutoLog(value = "删除多个数据权限规则", logType = LogType.OPERATE, operateType = OperateType.DELETE)
    public ResponseEntity<Void> deleteDataPermissionRulesByIds(@RequestParam("ids") ArrayList<Long> ids) {
        log.debug("REST request to delete DataPermissionRules : {}", ids);
        if (ids != null) {
            ids.forEach(dataPermissionRuleService::delete);
        }
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, (ids != null ? ids.toString() : "NoIds")))
            .build();
    }

    @GetMapping("/data-permission-rules/stats")
    @Operation(tags = "根据条件对数据权限规则进行统计", description = "条件和统计的配置通过数据权限规则的Criteria类来实现")
    @AutoLog(value = "根据条件对数据权限规则进行统计", logType = LogType.OPERATE, operateType = OperateType.STATS)
    public ResponseEntity<List<Map<String, Object>>> stats(DataPermissionRuleCriteria criteria) {
        log.debug("REST request to get stats by criteria: {}", criteria);
        List<Map<String, Object>> statsMapList = dataPermissionRuleQueryService.statsByAggregateCriteria(criteria);
        return ResponseEntity.ok().body(statsMapList);
    }
    // jhipster-needle-rest-resource-add-api - JHipster will add getters and setters here, do not remove

}
