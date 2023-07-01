import { VxeGridProps } from 'vxe-table';
import { VxeGridPropTypes } from 'vxe-table/types/grid';
import { useI18n } from '@/hooks/web/useI18n';

// begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！-->

const searchForm = (): any[] => [
  {
    title: 'ID',
    field: 'id',
    componentType: 'Text',
    value: '',
    type: 'Long',
    operator: '',
    span: 8,
    componentProps: {},
  },
  {
    title: '消息标题',
    field: 'title',
    componentType: 'Text',
    value: '',
    type: 'String',
    operator: '',
    span: 8,
    componentProps: {},
  },
  {
    title: '发送方式',
    field: 'sendType',
    componentType: 'Select',
    value: '',
    span: 8,
    operator: '',
    type: 'Enum',
    componentProps: () => {
      const { getEnumDict } = useI18n();
      return { options: getEnumDict('MessageSendType'), style: 'width: 100%' };
    },
  },
];

const columns = (): VxeGridPropTypes.Columns => [
  {
    type: 'checkbox',
    width: 60,
  },
  {
    title: 'ID',
    field: 'id',
    minWidth: 80,
    visible: true,
    treeNode: false,
    params: { type: 'LONG' },
    editRender: { name: 'AInputNumber', enabled: false },
  },
  {
    title: '消息标题',
    field: 'title',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '发送方式',
    field: 'sendType',
    minWidth: 100,
    visible: true,
    treeNode: false,
    params: { type: 'ENUM' },
    formatter: ({ cellValue }) => {
      const { getEnumDict } = useI18n();
      return (getEnumDict('MessageSendType').find(item => item.value === cellValue) || { label: cellValue }).label;
    },
  },
  {
    title: '接收人',
    field: 'receiver',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '发送所需参数',
    field: 'params',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '推送时间',
    field: 'sendTime',
    minWidth: 140,
    visible: true,
    treeNode: false,
    params: { type: 'ZONED_DATE_TIME' },
    editRender: { name: 'ADatePicker', props: { type: 'date', format: 'YYYY-MM-DD hh:mm:ss' }, enabled: false },
  },
  {
    title: '推送状态',
    field: 'sendStatus',
    minWidth: 100,
    visible: true,
    treeNode: false,
    params: { type: 'ENUM' },
    formatter: ({ cellValue }) => {
      const { getEnumDict } = useI18n();
      return (getEnumDict('SendStatus').find(item => item.value === cellValue) || { label: cellValue }).label;
    },
  },
  {
    title: '发送次数 超过5次不再发送',
    field: 'retryNum',
    minWidth: 80,
    visible: true,
    treeNode: false,
    params: { type: 'INTEGER' },
    editRender: { name: 'AInputNumber', enabled: false },
  },
  {
    title: '推送失败原因',
    field: 'failResult',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '备注',
    field: 'remark',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '创建者Id',
    field: 'createdBy',
    minWidth: 80,
    visible: true,
    treeNode: false,
    params: { type: 'LONG' },
    editRender: { name: 'AInputNumber', enabled: false },
  },
  {
    title: '创建时间',
    field: 'createdDate',
    minWidth: 140,
    visible: true,
    treeNode: false,
    params: { type: 'ZONED_DATE_TIME' },
    editRender: { name: 'ADatePicker', props: { type: 'date', format: 'YYYY-MM-DD hh:mm:ss' }, enabled: false },
  },
  {
    title: '修改者Id',
    field: 'lastModifiedBy',
    minWidth: 80,
    visible: true,
    treeNode: false,
    params: { type: 'LONG' },
    editRender: { name: 'AInputNumber', enabled: false },
  },
  {
    title: '修改时间',
    field: 'lastModifiedDate',
    minWidth: 140,
    visible: true,
    treeNode: false,
    params: { type: 'ZONED_DATE_TIME' },
    editRender: { name: 'ADatePicker', props: { type: 'date', format: 'YYYY-MM-DD hh:mm:ss' }, enabled: false },
  },
  {
    title: '软删除时间',
    field: 'deletedTime',
    minWidth: 140,
    visible: false,
    treeNode: false,
    params: { type: 'ZONED_DATE_TIME' },
    editRender: { name: 'ADatePicker', props: { type: 'date', format: 'YYYY-MM-DD hh:mm:ss' }, enabled: false },
  },
  {
    title: '操作',
    field: 'operation',
    fixed: 'right',
    width: 140,
    slots: { default: 'recordAction' },
  },
];

const baseGridOptions = (): VxeGridProps => {
  return {
    rowConfig: {
      keyField: 'id',
      isHover: true,
    },
    border: true,
    showHeaderOverflow: true,
    showOverflow: true,
    keepSource: true,
    id: 'full_edit_1',
    height: 600,
    printConfig: {
      columns: [
        // { field: 'name' },
      ],
    },
    filterConfig: {
      remote: true,
    },
    columnConfig: {
      resizable: true,
    },
    sortConfig: {
      trigger: 'cell',
      remote: true,
      orders: ['asc', 'desc', null],
      multiple: true,
      defaultSort: {
        field: 'id',
        order: 'desc',
      },
    },
    pagerConfig: {
      layouts: ['Sizes', 'PrevJump', 'PrevPage', 'Number', 'NextPage', 'NextJump', 'FullJump', 'Total'],
      pageSize: 15,
      pageSizes: [5, 10, 15, 20, 30, 50],
      total: 0,
      pagerCount: 5,
      currentPage: 1,
    },
    importConfig: {},
    exportConfig: {},
    checkboxConfig: {
      // labelField: 'id',
      reserve: true,
      highlight: true,
    },
    editRules: {},
    editConfig: {
      trigger: 'click',
      mode: 'row',
      showStatus: true,
    },
  };
};

export default {
  searchForm,
  columns,
  baseGridOptions,
};
