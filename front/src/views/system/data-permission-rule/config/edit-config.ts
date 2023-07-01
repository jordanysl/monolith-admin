import { FormSchema } from '@/components/Form';

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
    label: '菜单ID',
    field: 'permissionId',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入菜单ID', style: 'width: 100%' },
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
    label: '字段',
    field: 'column',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入字段', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '条件',
    field: 'conditions',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入条件', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '规则值',
    field: 'value',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入规则值', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '禁用状态',
    field: 'disabled',
    show: true,
    component: 'Switch',
    componentProps: { placeholder: '请选择禁用状态' },
    rules: [],
  },
  {
    label: '创建者Id',
    field: 'createdBy',
    show: false,
    component: 'InputNumber',
    componentProps: { placeholder: '请输入创建者Id', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '创建时间',
    field: 'createdDate',
    show: false,
    component: 'DatePicker',
    componentProps: { valueFormat: 'YYYY-MM-DD hh:mm:ss', placeholder: '请选择创建时间', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '修改者Id',
    field: 'lastModifiedBy',
    show: false,
    component: 'InputNumber',
    componentProps: { placeholder: '请输入修改者Id', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '修改时间',
    field: 'lastModifiedDate',
    show: false,
    component: 'DatePicker',
    componentProps: { valueFormat: 'YYYY-MM-DD hh:mm:ss', placeholder: '请选择修改时间', style: 'width: 100%' },
    rules: [],
  },
];
export default {
  fields,
};
