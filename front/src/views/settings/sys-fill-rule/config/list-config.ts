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
    title: '规则名称',
    field: 'name',
    componentType: 'Text',
    value: '',
    type: 'String',
    operator: '',
    span: 8,
    componentProps: {},
  },
  {
    title: '规则Code',
    field: 'code',
    componentType: 'Text',
    value: '',
    type: 'String',
    operator: '',
    span: 8,
    componentProps: {},
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
    title: '规则名称',
    field: 'name',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '规则Code',
    field: 'code',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '规则描述',
    field: 'desc',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '是否启用',
    field: 'enabled',
    minWidth: 70,
    visible: true,
    treeNode: false,
    params: { type: 'BOOLEAN' },
    cellRender: { name: 'ASwitch', props: { disabled: false } },
  },
  {
    title: '重置频率',
    field: 'resetFrequency',
    minWidth: 100,
    visible: true,
    treeNode: false,
    params: { type: 'ENUM' },
    formatter: ({ cellValue }) => {
      const { getEnumDict } = useI18n();
      return (getEnumDict('ResetFrequency').find(item => item.value === cellValue) || { label: cellValue }).label;
    },
  },
  {
    title: '序列值',
    field: 'seqValue',
    minWidth: 80,
    visible: true,
    treeNode: false,
    params: { type: 'LONG' },
    editRender: { name: 'AInputNumber', enabled: false },
  },
  {
    title: '生成值',
    field: 'fillValue',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '规则实现类',
    field: 'implClass',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '规则参数',
    field: 'params',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '重置开始日期',
    field: 'resetStartTime',
    minWidth: 140,
    visible: true,
    treeNode: false,
    params: { type: 'ZONED_DATE_TIME' },
    editRender: { name: 'ADatePicker', props: { type: 'date', format: 'YYYY-MM-DD hh:mm:ss' }, enabled: false },
  },
  {
    title: '重置结束日期',
    field: 'resetEndTime',
    minWidth: 140,
    visible: true,
    treeNode: false,
    params: { type: 'ZONED_DATE_TIME' },
    editRender: { name: 'ADatePicker', props: { type: 'date', format: 'YYYY-MM-DD hh:mm:ss' }, enabled: false },
  },
  {
    title: '重置时间',
    field: 'resetTime',
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
