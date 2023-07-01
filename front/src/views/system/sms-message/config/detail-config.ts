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
    label: '消息标题',
    field: 'title',
    show: true,
  },
  {
    label: '发送方式',
    field: 'sendType',
    show: true,
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('MessageSendType').find(item => item.value === value) || value;
    },
  },
  {
    label: '接收人',
    field: 'receiver',
    show: true,
  },
  {
    label: '发送所需参数',
    field: 'params',
    show: true,
  },
  {
    label: '推送内容',
    field: 'content',
    show: true,
  },
  {
    label: '推送时间',
    field: 'sendTime',
    show: true,
  },
  {
    label: '推送状态',
    field: 'sendStatus',
    show: true,
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('SendStatus').find(item => item.value === value) || value;
    },
  },
  {
    label: '发送次数 超过5次不再发送',
    field: 'retryNum',
    show: true,
  },
  {
    label: '推送失败原因',
    field: 'failResult',
    show: true,
  },
  {
    label: '备注',
    field: 'remark',
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
];

export default {
  fields,
};
