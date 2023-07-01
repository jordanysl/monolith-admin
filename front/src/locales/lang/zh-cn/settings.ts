export default {
  settings: {
    title: '[<strong>{{username}}</strong>] 的用户设置',
    form: {
      firstname: '名字',
      'firstname.placeholder': '您的名字',
      lastname: '姓氏',
      'lastname.placeholder': '您的姓氏',
      language: '语言',
      button: '保存',
    },
    messages: {
      error: {
        fail: '<strong>发生错误!</strong> 设置无法保存.',
        emailexists: '<strong>该邮件地址已被使用!</strong> 请使用另外的邮件地址.',
      },
      success: '<strong>设置保存成功!</strong>',
      validate: {
        firstname: {
          required: '您的名字是必填项.',
          minlength: '您的名字长度至少要有1个字符',
          maxlength: '您的名字长度不能超过50个字符',
        },
        lastname: {
          required: '您的姓氏是必填项.',
          minlength: '您的姓氏长度至少要有1个字符',
          maxlength: '您的姓氏长度不能超过50个字符',
        },
      },
    },
  },
};
