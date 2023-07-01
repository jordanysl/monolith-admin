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
    label: '日志类型',
    field: 'logType',
    show: true,
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('LogType').find(item => item.value === value) || value;
    },
  },
  {
    label: '日志内容',
    field: 'logContent',
    show: true,
  },
  {
    label: '操作类型',
    field: 'operateType',
    show: true,
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('OperateType').find(item => item.value === value) || value;
    },
  },
  {
    label: '操作用户账号',
    field: 'userid',
    show: true,
  },
  {
    label: '操作用户名称',
    field: 'username',
    show: true,
  },
  {
    label: 'IP',
    field: 'ip',
    show: true,
  },
  {
    label: '请求java方法',
    field: 'method',
    show: true,
  },
  {
    label: '请求路径',
    field: 'requestUrl',
    show: true,
  },
  {
    label: '请求参数',
    field: 'requestParam',
    show: true,
  },
  {
    label: '请求类型',
    field: 'requestType',
    show: true,
  },
  {
    label: '耗时',
    field: 'costTime',
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
