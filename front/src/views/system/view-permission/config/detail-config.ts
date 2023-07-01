import { DescItem } from '@/components/Description';
import { h, resolveComponent } from 'vue';
import { useI18n } from '@/hooks/web/useI18n';
import { Switch } from 'ant-design-vue';
import Icon from '@/components/Icon/Icon.vue';

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
    label: '权限名称',
    field: 'text',
    show: true,
  },
  {
    label: 'i18n主键',
    field: 'i18n',
    show: true,
  },
  {
    label: '显示分组名',
    field: 'group',
    show: true,
    render: (value, data) =>
      h(Switch, {
        disabled: true,
        checked: value,
        onChange: checked => {
          data.group = checked;
        },
      }),
  },
  {
    label: '路由',
    field: 'link',
    show: true,
  },
  {
    label: '外部链接',
    field: 'externalLink',
    show: true,
  },
  {
    label: '链接目标',
    field: 'target',
    show: true,
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('TargetType').find(item => item.value === value) || value;
    },
  },
  {
    label: '图标',
    field: 'icon',
    show: true,
    render: (value, data) => h(Icon, { class: value, style: 'font-size: 20px;' }),
  },
  {
    label: '禁用菜单',
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
    label: '隐藏菜单',
    field: 'hide',
    show: true,
    render: (value, data) =>
      h(Switch, {
        disabled: true,
        checked: value,
        onChange: checked => {
          data.hide = checked;
        },
      }),
  },
  {
    label: '隐藏面包屑',
    field: 'hideInBreadcrumb',
    show: true,
    render: (value, data) =>
      h(Switch, {
        disabled: true,
        checked: value,
        onChange: checked => {
          data.hideInBreadcrumb = checked;
        },
      }),
  },
  {
    label: '快捷菜单项',
    field: 'shortcut',
    show: true,
    render: (value, data) =>
      h(Switch, {
        disabled: true,
        checked: value,
        onChange: checked => {
          data.shortcut = checked;
        },
      }),
  },
  {
    label: '菜单根节点',
    field: 'shortcutRoot',
    show: true,
    render: (value, data) =>
      h(Switch, {
        disabled: true,
        checked: value,
        onChange: checked => {
          data.shortcutRoot = checked;
        },
      }),
  },
  {
    label: '允许复用',
    field: 'reuse',
    show: true,
    render: (value, data) =>
      h(Switch, {
        disabled: true,
        checked: value,
        onChange: checked => {
          data.reuse = checked;
        },
      }),
  },
  {
    label: '权限代码',
    field: 'code',
    show: true,
  },
  {
    label: '权限描述',
    field: 'description',
    show: true,
  },
  {
    label: '权限类型',
    field: 'type',
    show: true,
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('ViewPermissionType').find(item => item.value === value) || value;
    },
  },
  {
    label: '排序',
    field: 'order',
    show: true,
  },
  {
    label: 'api权限标识串',
    field: 'apiPermissionCodes',
    show: true,
  },
  {
    label: '组件名称',
    field: 'componentFile',
    show: true,
  },
  {
    label: '重定向路径',
    field: 'redirect',
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
    label: '上级',
    field: 'parent.id',
  },
];

export default {
  fields,
};
