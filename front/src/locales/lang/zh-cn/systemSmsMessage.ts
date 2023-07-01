export default {
  monolithAdminApp: {
    systemSmsMessage: {
      home: {
        title: 'Sms Messages',
        refreshListLabel: 'Refresh list',
        createLabel: '创建新 Sms Message',
        createOrEditLabel: '创建或编辑 Sms Message',
        notFound: 'No Sms Messages found',
      },
      created: 'Sms Message {{ param }} 创建成功',
      updated: 'Sms Message {{ param }} 更新成功',
      deleted: 'Sms Message {{ param }} 删除成功',
      delete: {
        question: '你确定要删除 Sms Message {{ id }} 吗？',
      },
      detail: {
        title: 'Sms Message',
      },
      id: 'ID',
      title: 'Title',
      sendType: 'Send Type',
      receiver: 'Receiver',
      params: 'Params',
      content: 'Content',
      sendTime: 'Send Time',
      sendStatus: 'Send Status',
      retryNum: 'Retry Num',
      failResult: 'Fail Result',
      remark: 'Remark',
      createdBy: 'Created By',
      createdDate: 'Created Date',
      lastModifiedBy: 'Last Modified By',
      lastModifiedDate: 'Last Modified Date',
      delFlag: 'Del Flag',
      deletedTime: 'Deleted Time',
      help: {
        title: '消息标题',
        sendType: '发送方式',
        receiver: '接收人',
        params: '发送所需参数\nJson格式',
        content: '推送内容',
        sendTime: '推送时间',
        sendStatus: '推送状态',
        retryNum: '发送次数 超过5次不再发送',
        failResult: '推送失败原因',
        remark: '备注',
        createdBy: '创建者Id',
        createdDate: '创建时间',
        lastModifiedBy: '修改者Id',
        lastModifiedDate: '修改时间',
        delFlag: '软删除标志',
        deletedTime: '软删除时间',
      },
    },
  },
};
