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
    label: '名称',
    field: 'name',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入名称', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '字段值',
    field: 'value',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入字段值', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '字段标题',
    field: 'label',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入字段标题', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '字段类型',
    field: 'valueType',
    show: true,
    component: 'Select',
    componentProps: () => {
      const { getEnumDict } = useI18n();
      return { placeholder: '请选择字段类型', options: getEnumDict('CommonFieldType'), style: 'width: 100%' };
    },
    rules: [],
  },
  {
    label: '说明',
    field: 'remark',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入说明', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '排序',
    field: 'sortValue',
    show: true,
    component: 'InputNumber',
    componentProps: { placeholder: '请输入排序', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '是否禁用',
    field: 'disabled',
    show: true,
    component: 'Switch',
    componentProps: { placeholder: '请选择是否禁用' },
    rules: [],
  },
  {
    label: '实体名称',
    field: 'ownerEntityName',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入实体名称', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '使用实体ID',
    field: 'ownerEntityId',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入使用实体ID', style: 'width: 100%' },
    rules: [],
  },
];
export default {
  fields,
};
