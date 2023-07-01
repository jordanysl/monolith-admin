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
    label: '名称',
    field: 'name',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入名称', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '代码',
    field: 'code',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入代码', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '地址',
    field: 'address',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入地址', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '联系电话',
    field: 'phoneNum',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入联系电话', style: 'width: 100%' },
    rules: [],
  },
  {
    label: 'logo地址',
    field: 'logo',
    show: true,
    component: 'ImageUpload',
    rules: [],
  },
  {
    label: '联系人',
    field: 'contact',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入联系人', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '创建用户 Id',
    field: 'createUserId',
    show: true,
    component: 'InputNumber',
    componentProps: { placeholder: '请输入创建用户 Id', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '创建时间',
    field: 'createTime',
    show: true,
    component: 'DatePicker',
    componentProps: { valueFormat: 'YYYY-MM-DD hh:mm:ss', placeholder: '请选择创建时间', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '角色列表',
    field: 'authorities',
    component: 'ApiTreeSelect',
    componentProps: {
      api: null,
      style: 'width: 100%',
      labelInValue: true,
      treeCheckable: true,
      treeCheckStrictly: true,
      fieldNames: { children: 'children', value: 'id', label: 'name' },
      resultField: 'records',
      placeholder: '请选择角色列表',
    },
    rules: [],
  },
  {
    label: '上级',
    field: 'parent.id',
    component: 'ApiTreeSelect',
    componentProps: {
      api: null,
      style: 'width: 100%',
      labelInValue: true,
      fieldNames: { children: 'children', value: 'id', label: 'name' },
      resultField: 'records',
      placeholder: '请选择上级',
    },
    rules: [],
  },
];
export default {
  fields,
};
