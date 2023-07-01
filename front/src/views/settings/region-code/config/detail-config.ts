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
    label: '名称',
    field: 'name',
    show: true,
  },
  {
    label: '地区代码',
    field: 'areaCode',
    show: true,
  },
  {
    label: '城市代码',
    field: 'cityCode',
    show: true,
  },
  {
    label: '全名',
    field: 'mergerName',
    show: true,
  },
  {
    label: '短名称',
    field: 'shortName',
    show: true,
  },
  {
    label: '邮政编码',
    field: 'zipCode',
    show: true,
  },
  {
    label: '等级',
    field: 'level',
    show: true,
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('RegionCodeLevel').find(item => item.value === value) || value;
    },
  },
  {
    label: '经度',
    field: 'lng',
    show: true,
  },
  {
    label: '纬度',
    field: 'lat',
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
    label: '上级节点',
    field: 'parent.id',
  },
];

export default {
  fields,
};
