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
    label: '岗位代码',
    field: 'code',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入岗位代码', style: 'width: 100%' },
    rules: [
      { type: 'string', max: 50 },
      { required: true, message: '必填项' },
    ],
  },
  {
    label: '名称',
    field: 'name',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入名称', style: 'width: 100%' },
    rules: [
      { type: 'string', max: 50 },
      { required: true, message: '必填项' },
    ],
  },
  {
    label: '排序',
    field: 'sortNo',
    show: true,
    component: 'InputNumber',
    componentProps: { placeholder: '请输入排序', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '描述',
    field: 'description',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入描述', style: 'width: 100%' },
    rules: [{ type: 'string', max: 200 }],
  },
];
export default {
  fields,
};
