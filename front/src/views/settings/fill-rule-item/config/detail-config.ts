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
    label: '排序值',
    field: 'sortValue',
    show: true,
  },
  {
    label: '字段参数类型',
    field: 'fieldParamType',
    show: true,
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('FieldParamType').find(item => item.value === value) || value;
    },
  },
  {
    label: '字段参数值',
    field: 'fieldParamValue',
    show: true,
  },
  {
    label: '日期格式',
    field: 'datePattern',
    show: true,
  },
  {
    label: '序列长度',
    field: 'seqLength',
    show: true,
  },
  {
    label: '序列增量',
    field: 'seqIncrement',
    show: true,
  },
  {
    label: '序列起始值',
    field: 'seqStartValue',
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
    label: '填充规则',
    field: 'fillRule.id',
  },
];

export default {
  fields,
};
