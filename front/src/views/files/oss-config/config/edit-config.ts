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
    label: '提供商',
    field: 'provider',
    show: true,
    component: 'Select',
    componentProps: () => {
      const { getEnumDict } = useI18n();
      return { placeholder: '请选择提供商', options: getEnumDict('OssProvider'), style: 'width: 100%' };
    },
    rules: [{ required: true, message: '必填项' }],
  },
  {
    label: '平台',
    field: 'platform',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入平台', style: 'width: 100%' },
    rules: [
      { type: 'string', max: 40 },
      { required: true, message: '必填项' },
    ],
  },
  {
    label: '启用',
    field: 'enabled',
    show: true,
    component: 'Switch',
    componentProps: { placeholder: '请选择启用' },
    rules: [],
  },
  {
    label: '备注',
    field: 'remark',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入备注', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '配置数据',
    field: 'configData',
    show: true,
    component: 'CodeEditor',
    rules: [],
  },
];
export default {
  fields,
};
