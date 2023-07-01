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
    label: '名称',
    field: 'name',
    show: true,
  },
  {
    label: '字段值',
    field: 'value',
    show: true,
  },
  {
    label: '字段标题',
    field: 'label',
    show: true,
  },
  {
    label: '字段类型',
    field: 'valueType',
    show: true,
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('CommonFieldType').find(item => item.value === value) || value;
    },
  },
  {
    label: '说明',
    field: 'remark',
    show: true,
  },
  {
    label: '排序',
    field: 'sortValue',
    show: true,
  },
  {
    label: '是否禁用',
    field: 'disabled',
    show: true,
    render: (value, data) =>
      h(Switch, {
        disabled: true,
        checked: value,
        onChange: checked => {
          data.disabled = checked;
        },
      }),
  },
  {
    label: '实体名称',
    field: 'ownerEntityName',
    show: true,
  },
  {
    label: '使用实体ID',
    field: 'ownerEntityId',
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
