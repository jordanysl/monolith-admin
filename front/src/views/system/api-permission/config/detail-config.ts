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
    label: '服务名称',
    field: 'serviceName',
    show: true,
  },
  {
    label: '权限名称',
    field: 'name',
    show: true,
  },
  {
    label: 'Code',
    field: 'code',
    show: true,
  },
  {
    label: '权限描述',
    field: 'description',
    show: true,
  },
  {
    label: '类型',
    field: 'type',
    show: true,
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('ApiPermissionType').find(item => item.value === value) || value;
    },
  },
  {
    label: '请求类型',
    field: 'method',
    show: true,
  },
  {
    label: 'url 地址',
    field: 'url',
    show: true,
  },
  {
    label: '状态',
    field: 'status',
    show: true,
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('ApiPermissionState').find(item => item.value === value) || value;
    },
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
