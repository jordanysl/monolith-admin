import { DescItem } from '@/components/Description';
import { h, resolveComponent } from 'vue';
import { useI18n } from '@/hooks/web/useI18n';
import { Switch } from 'ant-design-vue';

// begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！

const fields: DescItem[] = [
  {
    label: 'ID',
    field: 'id',
    show: values => {
      return values && values.id;
    },
  },
  {
    label: '任务名称',
    field: 'name',
    show: true,
  },
  {
    label: '任务类名',
    field: 'jobClassName',
    show: true,
  },
  {
    label: 'cron表达式',
    field: 'cronExpression',
    show: true,
  },
  {
    label: '参数',
    field: 'parameter',
    show: true,
  },
  {
    label: '描述',
    field: 'description',
    show: true,
  },
  {
    label: '任务状态',
    field: 'jobStatus',
    show: true,
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('JobStatus').find(item => item.value === value) || value;
    },
  },
  {
    label: '创建者Id',
    field: 'createdBy',
    show: true,
  },
  {
    label: '创建时间',
    field: 'createdDate',
    show: true,
  },
  {
    label: '修改者Id',
    field: 'lastModifiedBy',
    show: true,
  },
  {
    label: '修改时间',
    field: 'lastModifiedDate',
    show: true,
  },
  {
    label: '软删除标志',
    field: 'delFlag',
    show: values => {
      return values && values.delFlag;
    },
    render: (value, data) =>
      h(Switch, {
        disabled: true,
        checked: value,
        onChange: checked => {
          data.delFlag = checked;
        },
      }),
  },
  {
    label: '软删除时间',
    field: 'deletedTime',
    show: values => {
      return values && values.deletedTime;
    },
  },
];

export default {
  fields,
};
