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
    label: '提供商',
    field: 'provider',
    show: true,
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('SmsProvider').find(item => item.value === value) || value;
    },
  },
  {
    label: '资源编号',
    field: 'smsCode',
    show: true,
  },
  {
    label: '模板ID',
    field: 'templateId',
    show: true,
  },
  {
    label: 'accessKey',
    field: 'accessKey',
    show: true,
  },
  {
    label: 'secretKey',
    field: 'secretKey',
    show: true,
  },
  {
    label: 'regionId',
    field: 'regionId',
    show: true,
  },
  {
    label: '短信签名',
    field: 'signName',
    show: true,
  },
  {
    label: '备注',
    field: 'remark',
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