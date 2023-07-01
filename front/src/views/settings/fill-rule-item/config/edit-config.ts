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
    label: '排序值',
    field: 'sortValue',
    show: true,
    component: 'InputNumber',
    componentProps: { placeholder: '请输入排序值', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '字段参数类型',
    field: 'fieldParamType',
    show: true,
    component: 'Select',
    componentProps: () => {
      const { getEnumDict } = useI18n();
      return { placeholder: '请选择字段参数类型', options: getEnumDict('FieldParamType'), style: 'width: 100%' };
    },
    rules: [],
  },
  {
    label: '字段参数值',
    field: 'fieldParamValue',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入字段参数值', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '日期格式',
    field: 'datePattern',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入日期格式', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '序列长度',
    field: 'seqLength',
    show: true,
    component: 'InputNumber',
    componentProps: { placeholder: '请输入序列长度', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '序列增量',
    field: 'seqIncrement',
    show: true,
    component: 'InputNumber',
    componentProps: { placeholder: '请输入序列增量', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '序列起始值',
    field: 'seqStartValue',
    show: true,
    component: 'InputNumber',
    componentProps: { placeholder: '请输入序列起始值', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '填充规则',
    field: 'fillRule.id',
    component: 'ApiSelect',
    componentProps: {
      api: null,
      style: 'width: 100%',
      labelInValue: true,
      valueField: 'id',
      labelField: 'name',
      resultField: 'records',
      placeholder: '请选择填充规则',
    },
    rules: [],
  },
];
export default {
  fields,
};
