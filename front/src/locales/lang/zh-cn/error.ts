export default {
  error: {
    title: '错误页面!',
    http: {
      '400': '请求失败.',
      '403': '您没有权限访问此页面.',
      '404': '该页面不存在.',
      '405': '不允许此方法访问页面.',
      '500': '内部服务器错误.',
    },
    concurrencyFailure: '出现并发提交. 您的提交被拒绝.',
    validation: '服务器校验失败.',
  },
};