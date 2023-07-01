export default {
  reset: {
    request: {
      title: '重置您的密码',
      form: {
        button: '重置密码',
      },
      messages: {
        info: '请输入您注册时使用的邮件地址',
        success: '已将重置密码的操作说明发送到您的邮箱，请检查邮件.',
      },
    },
    finish: {
      title: '重置密码',
      form: {
        button: '确定',
      },
      messages: {
        info: '请设置新密码',
        success: '<strong>您的密码已被重置.</strong> 请 ',
        keymissing: '无效的重置密码请求.',
        error: '无法重置密码. 您必须在请求重置密码后24小时内完成重置.',
      },
    },
  },
};
