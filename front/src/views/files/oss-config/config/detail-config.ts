import { DescItem } from '@/components/Description';
import { h, resolveComponent } from 'vue';
import { useI18n } from '@/hooks/web/useI18n';
import { Switch } from 'ant-design-vue';
import { CodeEditor } from '@/components/CodeEditor';

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
    label: '提供商',
    field: 'provider',
    show: true,
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('OssProvider').find(item => item.value === value) || value;
    },
  },
  {
    label: '平台',
    field: 'platform',
    show: true,
  },
  {
    label: '启用',
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
    label: '备注',
    field: 'remark',
    show: true,
  },
  {
    label: '配置数据',
    field: 'configData',
    show: true,
    render: (value, data) => h(CodeEditor, { src: value, style: 'width: 100px; height: 100px; object-fit: cover; border-radius: 50%;' }),
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
