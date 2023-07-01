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
    label: '角色名称',
    field: 'name',
    show: true,
  },
  {
    label: '角色代号',
    field: 'code',
    show: true,
  },
  {
    label: '信息',
    field: 'info',
    show: true,
  },
  {
    label: '排序',
    field: 'order',
    show: true,
  },
  {
    label: '展示',
    field: 'display',
    show: true,
    render: (value, data) =>
      h(Switch, {
        disabled: true,
        checked: value,
        onChange: checked => {
          data.display = checked;
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
    label: '菜单列表',
    field: 'viewPermissions',
    render: (value, data) => h(Select, { disabled: true, options: value, fieldNames: { label: id, value: text } }),
  },
  {
    label: 'Api权限列表',
    field: 'apiPermissions',
    render: (value, data) => h(Select, { disabled: true, options: value, fieldNames: { label: id, value: name } }),
  },
  {
    label: '上级',
    field: 'parent.id',
  },
];

export default {
  fields,
};
