import { AppRouteRecordRaw } from '@/router/types';
import { getParentLayout } from '@/router/constant';

export const smsConfigRoutes: AppRouteRecordRaw = {
  path: 'sms-config',
  name: 'systemSmsConfig',
  component: getParentLayout('systemSmsConfig'),
  meta: {
    title: '短信配置',
  },
  redirect: '',
  children: [
    {
      path: 'list',
      name: 'systemSmsConfigList',
      component: () => import('@/views/files/sms-config/sms-config-list.vue'),
      meta: { title: '短信配置列表' },
    },
    {
      path: 'new',
      name: 'systemSmsConfigNew',
      component: () => import('@/views/files/sms-config/sms-config-edit.vue'),
      meta: { title: '新建短信配置' },
    },
    {
      path: ':entityId/edit',
      name: 'systemSmsConfigEdit',
      component: () => import('@/views/files/sms-config/sms-config-edit.vue'),
      meta: { title: '编辑短信配置', hideMenu: true },
    },
    {
      path: ':entityId/detail',
      name: 'systemSmsConfigDetail',
      component: () => import('@/views/files/sms-config/sms-config-detail.vue'),
      meta: { title: '查看短信配置', hideMenu: true },
    },
  ],
};
