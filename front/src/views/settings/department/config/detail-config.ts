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
    label: '名称',
    field: 'name',
    show: true,
  },
  {
    label: '代码',
    field: 'code',
    show: true,
  },
  {
    label: '地址',
    field: 'address',
    show: true,
  },
  {
    label: '联系电话',
    field: 'phoneNum',
    show: true,
  },
  {
    label: 'logo地址',
    field: 'logo',
    show: true,
    render: (value, data) => h('img', { src: value, style: 'width: 100px; height: 100px; object-fit: cover; border-radius: 50%;' }),
  },
  {
    label: '联系人',
    field: 'contact',
    show: true,
  },
  {
    label: '创建用户 Id',
    field: 'createUserId',
    show: true,
  },
  {
    label: '创建时间',
    field: 'createTime',
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
    label: '角色列表',
    field: 'authorities',
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
