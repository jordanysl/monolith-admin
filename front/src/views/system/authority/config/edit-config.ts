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
    label: '角色名称',
    field: 'name',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入角色名称', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '角色代号',
    field: 'code',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入角色代号', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '信息',
    field: 'info',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入信息', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '排序',
    field: 'order',
    show: true,
    component: 'InputNumber',
    componentProps: { placeholder: '请输入排序', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '展示',
    field: 'display',
    show: true,
    component: 'Switch',
    componentProps: { placeholder: '请选择展示' },
    rules: [],
  },
  {
    label: '菜单列表',
    field: 'viewPermissions',
    component: 'ApiTreeSelect',
    componentProps: {
      api: null,
      style: 'width: 100%',
      labelInValue: true,
      treeCheckable: true,
      treeCheckStrictly: true,
      fieldNames: { children: 'children', value: 'id', label: 'text' },
      resultField: 'records',
      placeholder: '请选择菜单列表',
    },
    rules: [],
  },
  {
    label: 'Api权限列表',
    field: 'apiPermissions',
    component: 'ApiTreeSelect',
    componentProps: {
      api: null,
      style: 'width: 100%',
      labelInValue: true,
      treeCheckable: true,
      treeCheckStrictly: true,
      fieldNames: { children: 'children', value: 'id', label: 'name' },
      resultField: 'records',
      placeholder: '请选择Api权限列表',
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
