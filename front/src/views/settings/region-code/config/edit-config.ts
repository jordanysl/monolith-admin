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
    label: '地区代码',
    field: 'areaCode',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入地区代码', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '城市代码',
    field: 'cityCode',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入城市代码', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '全名',
    field: 'mergerName',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入全名', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '短名称',
    field: 'shortName',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入短名称', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '邮政编码',
    field: 'zipCode',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入邮政编码', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '等级',
    field: 'level',
    show: true,
    component: 'Select',
    componentProps: () => {
      const { getEnumDict } = useI18n();
      return { placeholder: '请选择等级', options: getEnumDict('RegionCodeLevel'), style: 'width: 100%' };
    },
    rules: [],
  },
  {
    label: '经度',
    field: 'lng',
    show: true,
    component: 'InputNumber',
    componentProps: { placeholder: '请输入经度', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '纬度',
    field: 'lat',
    show: true,
    component: 'InputNumber',
    componentProps: { placeholder: '请输入纬度', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '上级节点',
    field: 'parent.id',
    component: 'ApiTreeSelect',
    componentProps: {
      api: null,
      style: 'width: 100%',
      labelInValue: true,
      fieldNames: { children: 'children', value: 'id', label: 'name' },
      resultField: 'records',
      placeholder: '请选择上级节点',
    },
    rules: [],
  },
];
export default {
  fields,
};
