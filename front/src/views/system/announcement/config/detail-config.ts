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
    label: '标题',
    field: 'title',
    show: true,
  },
  {
    label: '内容',
    field: 'content',
    show: true,
  },
  {
    label: '开始时间',
    field: 'startTime',
    show: true,
  },
  {
    label: '结束时间',
    field: 'endTime',
    show: true,
  },
  {
    label: '发布人Id',
    field: 'senderId',
    show: true,
  },
  {
    label: '优先级',
    field: 'priority',
    show: true,
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('PriorityLevel').find(item => item.value === value) || value;
    },
  },
  {
    label: '消息类型',
    field: 'category',
    show: true,
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('AnnoCategory').find(item => item.value === value) || value;
    },
  },
  {
    label: '通告对象类型',
    field: 'receiverType',
    show: true,
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('ReceiverType').find(item => item.value === value) || value;
    },
  },
  {
    label: '发布状态',
    field: 'sendStatus',
    show: true,
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('AnnoSendStatus').find(item => item.value === value) || value;
    },
  },
  {
    label: '发布时间',
    field: 'sendTime',
    show: true,
  },
  {
    label: '撤销时间',
    field: 'cancelTime',
    show: true,
  },
  {
    label: '业务类型',
    field: 'businessType',
    show: true,
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('AnnoBusinessType').find(item => item.value === value) || value;
    },
  },
  {
    label: '业务id',
    field: 'businessId',
    show: true,
  },
  {
    label: '打开方式',
    field: 'openType',
    show: true,
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('AnnoOpenType').find(item => item.value === value) || value;
    },
  },
  {
    label: '组件/路由 地址',
    field: 'openPage',
    show: true,
  },
  {
    label: '指定接收者id',
    field: 'receiverIds',
    show: true,
  },
  {
    label: '摘要',
    field: 'summary',
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
