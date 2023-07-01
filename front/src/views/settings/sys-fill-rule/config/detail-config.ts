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
    label: '规则名称',
    field: 'name',
    show: true,
  },
  {
    label: '规则Code',
    field: 'code',
    show: true,
  },
  {
    label: '规则描述',
    field: 'desc',
    show: true,
  },
  {
    label: '是否启用',
    field: 'enabled',
    show: true,
    render: (value, data) =>
      h(Switch, {
        disabled: true,
        checked: value,
        onChange: checked => {
          data.enabled = checked;
        },
      }),
  },
  {
    label: '重置频率',
    field: 'resetFrequency',
    show: true,
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('ResetFrequency').find(item => item.value === value) || value;
    },
  },
  {
    label: '序列值',
    field: 'seqValue',
    show: true,
  },
  {
    label: '生成值',
    field: 'fillValue',
    show: true,
  },
  {
    label: '规则实现类',
    field: 'implClass',
    show: true,
  },
  {
    label: '规则参数',
    field: 'params',
    show: true,
  },
  {
    label: '重置开始日期',
    field: 'resetStartTime',
    show: true,
  },
  {
    label: '重置结束日期',
    field: 'resetEndTime',
    show: true,
  },
  {
    label: '重置时间',
    field: 'resetTime',
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
  {
    label: '配置项列表',
    field: 'ruleItems',
    render: (value, data) => h(resolveComponent('vxe-grid'), { disabled: true, data: value, columns: {} }),
  },
];

export default {
  fields,
};
