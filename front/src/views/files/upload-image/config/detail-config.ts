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
    label: '完整文件名',
    field: 'fullName',
    show: true,
  },
  {
    label: '文件名',
    field: 'name',
    show: true,
  },
  {
    label: '扩展名',
    field: 'ext',
    show: true,
  },
  {
    label: '文件类型',
    field: 'type',
    show: true,
  },
  {
    label: 'Web Url地址',
    field: 'url',
    show: true,
  },
  {
    label: '本地路径',
    field: 'path',
    show: true,
  },
  {
    label: '本地存储目录',
    field: 'folder',
    show: true,
  },
  {
    label: '使用实体名称',
    field: 'ownerEntityName',
    show: true,
  },
  {
    label: '使用实体ID',
    field: 'ownerEntityId',
    show: true,
  },
  {
    label: '业务标题',
    field: 'businessTitle',
    show: true,
  },
  {
    label: '业务自定义描述内容',
    field: 'businessDesc',
    show: true,
  },
  {
    label: '业务状态',
    field: 'businessStatus',
    show: true,
  },
  {
    label: '创建时间',
    field: 'createAt',
    show: true,
  },
  {
    label: '文件大小',
    field: 'fileSize',
    show: true,
  },
  {
    label: '小图Url',
    field: 'smartUrl',
    show: true,
  },
  {
    label: '中等图Url',
    field: 'mediumUrl',
    show: true,
  },
  {
    label: '文件被引用次数',
    field: 'referenceCount',
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
    label: '所属分类',
    field: 'category.id',
  },
];

export default {
  fields,
};
