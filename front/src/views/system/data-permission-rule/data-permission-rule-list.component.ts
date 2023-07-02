import { defineComponent, reactive, ref, getCurrentInstance, h } from 'vue';
import { Alert, message, Modal } from 'ant-design-vue';
import { DownOutlined, UpOutlined, UserOutlined } from '@ant-design/icons-vue';
import { VxeGridInstance, VxeGridListeners, VxeGridProps } from 'vxe-table';
import { getSearchQueryData } from '@/utils/jhipster/entity-utils';
import SearchForm from '@/components/search-form/search-form.vue';
import Icon from '@/components/Icon/Icon.vue';
import { useModalInner, BasicModal } from '@/components/Modal';
import { useDrawerInner, BasicDrawer } from '@/components/Drawer';
import { useGo } from '@/hooks/web/usePage';
import ServerProvider from '@/api-service/index';
import { useRootSetting } from '@/hooks/setting/useRootSetting';
import DataPermissionRuleEdit from './data-permission-rule-edit.vue';
import DataPermissionRuleDetail from './data-permission-rule-detail.vue';

import config from './config/list-config';

// begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！

export default defineComponent({
  components: {
    SearchForm,
    DownOutlined,
    UpOutlined,
    UserOutlined,
    Icon,
    BasicModal,
    BasicDrawer,
    DataPermissionRuleEdit,
    DataPermissionRuleDetail,
  },
  props: {},
  async setup() {
    const [registerModal, { closeModal, setModalProps }] = useModalInner(data => {
      console.log(data);
    });
    const [registerDrawer, { closeDrawer, setDrawerProps }] = useDrawerInner(data => {
      console.log(data);
    });
    const ctx = getCurrentInstance()?.proxy;
    const go = useGo();
    const apiService = ctx?.$apiService as typeof ServerProvider;
    const { getPageSetting } = useRootSetting();
    const relationshipApis: any = {};
    const apis = {
      dataPermissionRuleService: apiService.system.dataPermissionRuleService,
      find: apiService.system.dataPermissionRuleService.retrieve,
      deleteById: apiService.system.dataPermissionRuleService.delete,
      deleteByIds: apiService.system.dataPermissionRuleService.deleteByIds,
      update: apiService.system.dataPermissionRuleService.update,
    };
    const pageConfig = {
      title: '数据权限规则列表',
      baseRouteName: 'systemDataPermissionRule',
    };
    const columns = config.columns();
    const searchFormFields = config.searchForm();
    if (columns && columns.length && Object.keys(relationshipApis).length) {
      columns
        .filter(item => item.field && Object.keys(relationshipApis).includes(item.field))
        .forEach(item => {
          item.editRender = {
            ...item.editRender,
            props: {
              ...item.editRender?.props,
              api: relationshipApis[item.field!],
            },
          };
        });
    }
    const xGrid = ref({} as VxeGridInstance);
    const searchInput = ref(null);
    const searchFormConfig = reactive({
      fieldList: searchFormFields,
      toggleSearchStatus: false,
      useOr: false,
      disabled: false,
    });
    const batchOperations = [];
    const rowOperations = [
      {
        disabled: false,
        name: 'save',
        type: getPageSetting.value.buttonType || 'icon',
      },
      {
        name: 'delete',
        type: getPageSetting.value.buttonType || 'icon',
      },
      {
        title: '详情',
        name: 'detail',
        containerType: 'drawer',
        type: getPageSetting.value.buttonType || 'icon',
      },
    ];
    const tableRowOperations = reactive<any[]>([]);
    const tableRowMoreOperations = reactive<any[]>([]);
    const saveOperation = rowOperations.find(operation => operation.name === 'save');
    if (rowOperations.length > 4 || (saveOperation && rowOperations.length > 3)) {
      if (saveOperation) {
        tableRowOperations.push(...rowOperations?.slice(0, 2));
        tableRowMoreOperations.push(...rowOperations.slice(3));
      } else {
        tableRowOperations.push(...rowOperations?.slice(0, 3));
        tableRowMoreOperations.push(...rowOperations.slice(4));
      }
    } else {
      tableRowOperations.push(...rowOperations);
    }
    const selectedRows = reactive<any>([]);
    const loading = ref(false);
    const searchFormRef = ref<any>(null);
    const searchValue = ref('');
    const mapOfFilter = reactive({});
    const mapOfSort: { [key: string]: any } = reactive({});
    columns?.forEach(column => {
      if (column.sortable && column.field) {
        mapOfSort[column.field] = false;
      }
    });
    const sort = () => {
      const result: any[] = [];
      Object.keys(mapOfSort).forEach(key => {
        if (mapOfSort[key] && mapOfSort[key] !== false) {
          if (mapOfSort[key] === 'asc') {
            result.push(key + ',asc');
          } else if (mapOfSort[key] === 'desc') {
            result.push(key + ',desc');
          }
        }
      });
      return result;
    };
    const treeFilterData = [];
    const filterTreeConfig = reactive({
      filterTreeSpan: treeFilterData && treeFilterData.length > 0 ? 6 : 0,
      treeFilterData,
      expandedKeys: [],
      checkedKeys: [],
      selectedKeys: [],
      autoExpandParent: true,
    });
    const modalConfig = reactive({
      componentName: '',
      entityId: '',
      containerType: 'modal',
    });
    const drawerConfig = reactive({
      componentName: '',
      containerType: 'drawer',
      entityId: '',
    });
    const gridOptions = reactive<VxeGridProps>({
      ...config.baseGridOptions(),
      customConfig: {
        storage: true,
        checkMethod({ column }) {
          return !['nickname', 'role'].includes(column.field);
        },
      },
      proxyConfig: {
        enabled: true,
        autoLoad: true,
        seq: true,
        sort: true,
        filter: true,
        props: {
          result: 'records',
          total: 'total',
        },
        ajax: {
          query: async ({ filters, page, sort, sorts }) => {
            console.log('filters', filters);
            console.log('sorts', sorts);
            const queryParams: any = {};
            queryParams.page = page?.currentPage > 0 ? page.currentPage - 1 : 0;
            queryParams.size = page?.pageSize;
            if (sort && sort.field) {
              queryParams.sort = [sort.field + ',' + (sort.order === 'desc' ? 'DESC' : 'ASC')];
            }
            if (searchValue.value) {
              queryParams['jhiCommonSearchKeywords'] = searchValue.value;
            } else {
              Object.assign(queryParams, getSearchQueryData(searchFormConfig));
            }
            return await apis.find(queryParams);
          },
          queryAll: async () => await apis.find({ size: -1 }),
          delete: async records => await apis.deleteByIds(records.body.removeRecords.map(record => record.id)),
        },
      },
      toolbarConfig: {
        custom: false,
        import: false,
        print: false,
        export: false,
        slots: {
          buttons: 'toolbar_buttons',
        },
        // 表格左上角自定义按钮
        buttons: [
          {
            name: '批量操作',
            circle: false,
            icon: 'vxe-icon-add',
            status: 'primary',
            dropdowns: [{ code: 'new', name: '删除', circle: false, icon: 'ant-design:delete-filled', status: 'primary' }],
          },
        ],
        // 表格右上角自定义按钮
        tools: [{ code: 'new', name: '新增', circle: false, icon: 'vxe-icon-add' }],
      },
      columns,
    });
    gridOptions!.pagerConfig!.slots = {
      left: () => {
        return h(Alert, { type: 'warning', banner: true, message: `已选择 ${selectedRows.length} 项`, style: 'height: 30px' });
      },
    };
    const gridEvents = reactive<VxeGridListeners>({
      checkboxAll: () => {
        const $grid = xGrid.value;
        selectedRows.length = 0;
        selectedRows.push(...$grid.getCheckboxRecords());
      },
      checkboxChange: () => {
        const $grid = xGrid.value;
        selectedRows.length = 0;
        selectedRows.push(...$grid.getCheckboxRecords());
      },
      pageChange({ currentPage, pageSize }) {
        if (gridOptions.pagerConfig) {
          gridOptions.pagerConfig.currentPage = currentPage;
          gridOptions.pagerConfig.pageSize = pageSize;
        }
      },
      radioChange() {
        const $grid = xGrid.value;
        selectedRows.length = 0;
        selectedRows.push($grid.getRadioRecord());
      },
      // 表格左上角按钮事件
      toolbarButtonClick({ code }) {
        switch (code) {
          case 'myInsert': {
            break;
          }
          case 'mySave': {
            break;
          }
          case 'myExport': {
            break;
          }
        }
      },
      // 表格右上角自定义按钮事件
      toolbarToolClick({ code }) {
        switch (code) {
          case 'new':
            if (pageConfig.baseRouteName) {
              go({ name: `${pageConfig.baseRouteName}New` });
            } else {
              console.log('未定义方法');
            }
            break;
        }
      },
      editClosed({ row, column }) {
        const field = column.property;
        const cellValue = row[field];
        // 判断单元格值是否被修改
        if (xGrid.value.isUpdateByRow(row, field)) {
          const entity = { id: row.id };
          entity[field] = cellValue;
          apis
            .update(entity, [row.id], [field])
            .then(() => {
              message.success({
                content: `信息更新成功。 ${field}=${cellValue}`,
                duration: 1,
              });
              xGrid.value.reloadRow(row, null, field);
            })
            .catch(error => {
              console.log('error', error);
              message.error({
                content: `信息保存可能存在问题！ ${field}=${cellValue}`,
                onClose: () => {},
              });
            });
        }
      },
    });
    return {
      sort,
      searchFormConfig,
      selectedRows,
      loading,
      mapOfFilter,
      mapOfSort,
      filterTreeConfig,
      searchValue,
      xGrid,
      searchInput,
      tableRowOperations,
      tableRowMoreOperations,
      modalConfig,
      registerModal,
      closeModal,
      setModalProps,
      drawerConfig,
      registerDrawer,
      closeDrawer,
      setDrawerProps,
      go,
      gridEvents,
      gridOptions,
      batchOperations,
      apis,
      pageConfig,
      searchFormRef,
    };
  },
  methods: {
    handleToggleSearch(): void {
      this.searchFormConfig.toggleSearchStatus = !this.searchFormConfig.toggleSearchStatus;
    },
    formSearch(): void {
      this.xGrid.commitProxy('reload');
    },
    onCheck(checkedKeys) {
      console.log('onCheck', checkedKeys);
      this.filterTreeConfig.checkedKeys = checkedKeys;
    },
    showSearchFormSetting() {
      if (this.searchFormRef) {
        this.searchFormRef.showSettingModal();
      }
    },
    onSelect(selectedKeys, info) {
      const filterData = info.node.dataRef;
      if (filterData.type === 'filterGroup') {
        this.mapOfFilter[info.node.dataRef.key].value = [];
      } else if (filterData.type === 'filterItem') {
        this.mapOfFilter[info.node.dataRef.filterName].value = [info.node.dataRef.filterValue];
      }
      this.formSearch();
      this.filterTreeConfig.selectedKeys = selectedKeys;
    },
    switchFilterTree() {
      this.filterTreeConfig.filterTreeSpan = this.filterTreeConfig.filterTreeSpan > 0 ? 0 : 6;
    },
    rowMoreClick(e, row) {
      const { key } = e;
      const operation = this.tableRowMoreOperations.find(operation => operation.name === key);
      this.rowClickHandler(name, operation, row);
    },
    rowClick(name, row) {
      const operation = this.tableRowOperations.find(operation => operation.name === name);
      this.rowClickHandler(name, operation, row);
    },
    rowClickHandler(name, operation, row) {
      // eslint-disable-next-line @typescript-eslint/no-this-alias
      const _this = this;
      switch (name) {
        case 'save':
          break;
        case 'edit':
          if (operation) {
            if (operation.click) {
              operation.click(row);
            } else {
              switch (operation.containerType) {
                case 'modal':
                  this.modalConfig.componentName = 'data-permission-rule-edit';
                  this.modalConfig.entityId = row.id;
                  this.setModalProps({ visible: true });
                  break;
                case 'drawer':
                  this.drawerConfig.componentName = 'data-permission-rule-edit';
                  this.drawerConfig.entityId = row.id;
                  this.setDrawerProps({ visible: true });
                  break;
                case 'route':
                default:
                  if (this.pageConfig.baseRouteName) {
                    this.go({ name: `${this.pageConfig.baseRouteName}Edit`, params: { entityId: row.id } });
                  } else {
                    console.log('未定义方法');
                  }
              }
            }
          } else {
            if (this.pageConfig.baseRouteName) {
              this.go({ name: `${this.pageConfig.baseRouteName}Edit`, params: { entityId: row.id } });
            } else {
              console.log('未定义方法');
            }
          }
          break;
        case 'detail':
          if (operation) {
            if (operation.click) {
              operation.click(row);
            } else {
              switch (operation.containerType) {
                case 'modal':
                  this.modalConfig.componentName = 'data-permission-rule-detail';
                  this.modalConfig.entityId = row.id;
                  this.setModalProps({ visible: true });
                  break;
                case 'drawer':
                  this.drawerConfig.componentName = 'data-permission-rule-detail';
                  this.drawerConfig.entityId = row.id;
                  this.setDrawerProps({ visible: true });
                  break;
                case 'route':
                default:
                  if (this.pageConfig.baseRouteName) {
                    this.go({ name: `${this.pageConfig.baseRouteName}Detail`, params: { entityId: row.id } });
                  } else {
                    console.log('未定义方法');
                  }
              }
            }
          } else {
            if (this.pageConfig.baseRouteName) {
              this.go({ name: `${this.pageConfig.baseRouteName}Detail`, params: { entityId: row.id } });
            } else {
              console.log('未定义方法');
            }
          }
          break;
        case 'delete':
          Modal.confirm({
            title: `操作提示`,
            content: `是否确认删除ID为${row.id}的记录？`,
            onOk() {
              if (operation.click) {
                operation.click(row);
              } else {
                _this.apis.deleteById(row.id).then(() => {
                  _this.formSearch();
                });
              }
            },
          });
          break;
        default:
          if (operation) {
            if (operation.click) {
              operation.click(row);
            } else {
              console.log('error', `click方法未定义`);
            }
          } else {
            console.log('error', `${name}未定义`);
          }
      }
    },
  },
});
