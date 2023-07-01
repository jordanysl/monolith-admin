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
    label: '任务名称',
    field: 'name',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入任务名称', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '任务类名',
    field: 'jobClassName',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入任务类名', style: 'width: 100%' },
    rules: [],
  },
  {
    label: 'cron表达式',
    field: 'cronExpression',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入cron表达式', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '参数',
    field: 'parameter',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入参数', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '描述',
    field: 'description',
    show: true,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入描述', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '任务状态',
    field: 'jobStatus',
    show: true,
    component: 'Select',
    componentProps: () => {
      const { getEnumDict } = useI18n();
      return { placeholder: '请选择任务状态', options: getEnumDict('JobStatus'), style: 'width: 100%' };
    },
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
