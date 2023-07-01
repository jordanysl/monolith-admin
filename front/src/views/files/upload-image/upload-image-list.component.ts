import { defineComponent, reactive, ref, getCurrentInstance } from 'vue';
import { Modal } from 'ant-design-vue';
import { DownOutlined, UpOutlined, UserOutlined } from '@ant-design/icons-vue';
import { getSearchQueryData } from '@/utils/jhipster/entity-utils';
import SearchForm from '@/components/search-form/search-form.vue';
import Icon from '@/components/Icon/Icon.vue';
import { useModalInner, BasicModal } from '@/components/Modal';
import { useDrawerInner, BasicDrawer } from '@/components/Drawer';
import { useGo } from '@/hooks/web/usePage';
import ServerProvider from '@/api-service/index';
import UploadImageEdit from './upload-image-edit.vue';
import UploadImageDetail from './upload-image-detail.vue';
import { CardList } from '@/components/CardList';
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
    UploadImageEdit,
    UploadImageDetail,
    CardList,
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
    const relationshipApis: any = {
      category: apiService.files.resourceCategoryService.tree,
    };
    const apis = {
      find: apiService.files.uploadImageService.retrieve,
      deleteById: apiService.files.uploadImageService.delete,
      deleteByIds: apiService.files.uploadImageService.deleteByIds,
      updateBySpecifiedField: apiService.files.uploadImageService.updateBySpecifiedField,
    };
    const pageConfig = {
      title: '上传图片列表',
      baseRouteName: 'ossUploadImage',
    };
    const columns = config.columns();
    if (columns && columns.length && Object.keys(relationshipApis).length) {
      columns
        .filter(item => item.field && Object.keys(relationshipApis).includes(item.field))
        .forEach(item => {
          Object.assign(item, { editRender: { props: { api: relationshipApis[item.field!] } } });
        });
    }
    const searchFormFields = config.searchForm();
    const cardList = ref<any>(null);
    const searchInput = ref(null);
    const searchFormConfig = reactive({
      fieldList: searchFormFields,
      toggleSearchStatus: false,
      matchType: 'and',
      disabled: false,
    });
    const batchOperations = [];
    const rowOperations = [
      {
        disabled: false,
        name: 'save',
      },
      {
        name: 'delete',
      },
      {
        title: '详情',
        name: 'detail',
        containerType: 'drawer',
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
    const cardListOptions = reactive({
      params: {},
      api: apis.find,
      imageField: 'imageUrl',
      resultField: 'records',
      totalField: 'total',
      toolButtons: [
        {
          title: '新增',
          icon: 'ant-design:upload-outlined',
          click: () => {
            modalConfig.componentName = 'UploadImageEdit';
            modalConfig.entityId = '';
            modalConfig.containerType = 'modal';
            setModalProps({
              visible: true,
              title: '新增上传图片',
              width: 800,
              destroyOnClose: true,
              footer: null,
            });
          },
          hidden: false,
          disabled: false,
        },
      ],
      rowOperations: [
        {
          title: '编辑',
          click: (row: any) => {},
        },
      ],
      fetchMethod: params =>
        new Promise(resolve => {
          resolve(params);
        }),
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
      cardList,
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
      cardListOptions,
      batchOperations,
      apis,
      pageConfig,
    };
  },
  methods: {
    cardListFetchMethod(method) {
      this.cardListOptions.fetchMethod = method;
    },
    handleToggleSearch(): void {
      this.searchFormConfig.toggleSearchStatus = !this.searchFormConfig.toggleSearchStatus;
    },
    closeModalOrDrawer({ containerType, update }): void {
      if (containerType === 'modal') {
        this.closeModal();
      } else if (containerType === 'drawer') {
        this.closeDrawer();
      }
      if (update) {
        this.formSearch();
      }
    },
    formSearch(): void {
      let params = {};
      if (this.searchValue) {
        params['jhiCommonSearchKeywords'] = this.searchValue;
      } else {
        params = Object.assign({}, this.cardListOptions.params, getSearchQueryData(this.searchFormConfig));
      }
      this.cardListOptions.fetchMethod(params).then(res => {
        console.log('fetch.res', res);
      });
    },
    onCheck(checkedKeys) {
      console.log('onCheck', checkedKeys);
      this.filterTreeConfig.checkedKeys = checkedKeys;
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
    xGridSortChange({ property, order }) {
      this.mapOfSort = {};
      this.mapOfSort[property] = order;
      this.formSearch();
    },
    xGridFilterChange({ column, property, values, datas }) {
      const type = column.params ? column.params.type : '';
      let tempValues;
      if (type === 'STRING') {
        tempValues = datas[0];
      } else if (type === 'INTEGER' || type === 'LONG' || type === 'DOUBLE' || type === 'FLOAT' || type === 'ZONED_DATE_TIME') {
        tempValues = datas[0];
      } else if (type === 'BOOLEAN') {
        tempValues = values;
      }
      this.mapOfFilter[property] = { value: tempValues, type: type };
      this.formSearch();
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
                  this.modalConfig.componentName = 'upload-image-edit';
                  this.modalConfig.entityId = row.id;
                  this.setModalProps({ visible: true });
                  break;
                case 'drawer':
                  this.drawerConfig.componentName = 'upload-image-edit';
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
                  this.modalConfig.componentName = 'upload-image-detail';
                  this.modalConfig.entityId = row.id;
                  this.setModalProps({ visible: true });
                  break;
                case 'drawer':
                  this.drawerConfig.componentName = 'upload-image-detail';
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
                _this.apis
                  .deleteById(row.id)
                  .then(() => {
                    _this.formSearch();
                  })
                  .catch(err => {
                    console.log('err', err);
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
