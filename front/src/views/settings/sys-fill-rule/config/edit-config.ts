import { FormSchema } from '@/components/Form';
import { useI18n } from '@/hooks/web/useI18n';

// begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！

const fields: FormSchema[] = [
  {
    label: 'ID',
    field: 'id',
    show: ({ values }) => {
      return values && values.id;
    },
    dynamicDisabled: true,
    component: 'InputNumber',
    componentProps: { placeholder: '请输入ID', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '规则名称',
    field: 'name',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入规则名称', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '规则Code',
    field: 'code',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入规则Code', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '规则描述',
    field: 'desc',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入规则描述', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '是否启用',
    field: 'enabled',
    show: true,
    component: 'Switch',
    componentProps: { placeholder: '请选择是否启用' },
    rules: [],
  },
  {
    label: '重置频率',
    field: 'resetFrequency',
    show: true,
    component: 'Select',
    componentProps: () => {
      const { getEnumDict } = useI18n();
      return { placeholder: '请选择重置频率', options: getEnumDict('ResetFrequency'), style: 'width: 100%' };
    },
    rules: [],
  },
  {
    label: '序列值',
    field: 'seqValue',
    show: true,
    component: 'InputNumber',
    componentProps: { placeholder: '请输入序列值', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '生成值',
    field: 'fillValue',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入生成值', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '规则实现类',
    field: 'implClass',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入规则实现类', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '规则参数',
    field: 'params',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入规则参数', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '重置开始日期',
    field: 'resetStartTime',
    show: true,
    component: 'DatePicker',
    componentProps: { valueFormat: 'YYYY-MM-DD hh:mm:ss', placeholder: '请选择重置开始日期', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '重置结束日期',
    field: 'resetEndTime',
    show: true,
    component: 'DatePicker',
    componentProps: { valueFormat: 'YYYY-MM-DD hh:mm:ss', placeholder: '请选择重置结束日期', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '重置时间',
    field: 'resetTime',
    show: true,
    component: 'DatePicker',
    componentProps: { valueFormat: 'YYYY-MM-DD hh:mm:ss', placeholder: '请选择重置时间', style: 'width: 100%' },
    rules: [],
  },
];
const ruleItemsColumns = [
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
    title: '排序值',
    field: 'sortValue',
    minWidth: 80,
    visible: true,
    treeNode: false,
    params: { type: 'INTEGER' },
    editRender: { name: 'AInputNumber', enabled: false },
  },
  {
    title: '字段参数类型',
    field: 'fieldParamType',
    minWidth: 100,
    visible: true,
    treeNode: false,
    params: { type: 'ENUM' },
    formatter: ({ cellValue }) => {
      const { getEnumDict } = useI18n();
      return (getEnumDict('FieldParamType').find(item => item.value === cellValue) || { label: cellValue }).label;
    },
  },
  {
    title: '字段参数值',
    field: 'fieldParamValue',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '日期格式',
    field: 'datePattern',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '序列长度',
    field: 'seqLength',
    minWidth: 80,
    visible: true,
    treeNode: false,
    params: { type: 'INTEGER' },
    editRender: { name: 'AInputNumber', enabled: false },
  },
  {
    title: '序列增量',
    field: 'seqIncrement',
    minWidth: 80,
    visible: true,
    treeNode: false,
    params: { type: 'INTEGER' },
    editRender: { name: 'AInputNumber', enabled: false },
  },
  {
    title: '序列起始值',
    field: 'seqStartValue',
    minWidth: 80,
    visible: true,
    treeNode: false,
    params: { type: 'INTEGER' },
    editRender: { name: 'AInputNumber', enabled: false },
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
export default {
  fields,
  ruleItemsColumns,
};
