import { DescItem } from '@/components/Description';
import { h, resolveComponent } from 'vue';
import { Switch } from 'ant-design-vue';

// begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！

const fields: DescItem[] = [
  {
    label: '用户ID',
    field: 'id',
    show: values => {
      return values && values.id;
    },
  },
  {
    label: '账户名',
    field: 'login',
    show: true,
  },
  {
    label: '密码',
    field: 'password',
    show: true,
  },
  {
    label: '名字',
    field: 'firstName',
    show: true,
  },
  {
    label: '姓氏',
    field: 'lastName',
    show: true,
  },
  {
    label: '电子邮箱',
    field: 'email',
    show: true,
  },
  {
    label: '手机号码',
    field: 'mobile',
    show: true,
  },
  {
    label: '出生日期',
    field: 'birthday',
    show: true,
  },
  {
    label: '激活状态',
    field: 'activated',
    show: true,
    render: (value, data) =>
      h(Switch, {
        disabled: true,
        checked: value,
        onChange: checked => {
          data.activated = checked;
        },
      }),
  },
  {
    label: '语言Key',
    field: 'langKey',
    show: true,
  },
  {
    label: '头像地址',
    field: 'imageUrl',
    show: true,
    render: (value, data) => h('img', { src: value, style: 'width: 100px; height: 100px; object-fit: cover; border-radius: 50%;' }),
  },
  {
    label: '激活Key',
    field: 'activationKey',
    show: true,
  },
  {
    label: '重置码',
    field: 'resetKey',
    show: true,
  },
  {
    label: '重置时间',
    field: 'resetDate',
    show: true,
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
  {
    label: '部门',
    field: 'department.id',
  },
  {
    label: '岗位',
    field: 'position.id',
  },
  {
    label: '角色列表',
    field: 'authorities',
    render: (value, data) => h(Select, { disabled: true, options: value, fieldNames: { label: id, value: name } }),
  },
];

export default {
  fields,
};
