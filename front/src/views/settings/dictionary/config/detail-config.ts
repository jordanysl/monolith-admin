import { DescItem } from '@/components/Description';
import { h, resolveComponent } from 'vue';
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
    label: '字典名称',
    field: 'dictName',
    show: true,
  },
  {
    label: '字典Key',
    field: 'dictKey',
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
    label: '排序',
    field: 'sortValue',
    show: true,
  },
  {
    label: '是否内置',
    field: 'builtIn',
    show: true,
    render: (value, data) =>
      h(Switch, {
        disabled: true,
        checked: value,
        onChange: checked => {
          data.builtIn = checked;
        },
      }),
  },
  {
    label: '更新枚举',
    field: 'syncEnum',
    show: true,
    render: (value, data) =>
      h(Switch, {
        disabled: true,
        checked: value,
        onChange: checked => {
          data.syncEnum = checked;
        },
      }),
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
    label: '字典项列表',
    field: 'items',
    render: (value, data) => h(resolveComponent('vxe-grid'), { disabled: true, data: value, columns: {} }),
  },
];

export default {
  fields,
};
