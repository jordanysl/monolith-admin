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
    label: '菜单ID',
    field: 'permissionId',
    show: true,
  },
  {
    label: '规则名称',
    field: 'name',
    show: true,
  },
  {
    label: '字段',
    field: 'column',
    show: true,
  },
  {
    label: '条件',
    field: 'conditions',
    show: true,
  },
  {
    label: '规则值',
    field: 'value',
    show: true,
  },
  {
    label: '禁用状态',
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
