import { AppRouteRecordRaw } from '@/router/types';
import { getParentLayout } from '@/router/constant';

export const dataPermissionRuleRoutes: AppRouteRecordRaw = {
  path: 'data-permission-rule',
  name: 'systemDataPermissionRule',
  component: getParentLayout('systemDataPermissionRule'),
  meta: {
    title: '数据权限规则',
  },
  redirect: '',
  children: [
    {
      path: 'list',
      name: 'systemDataPermissionRuleList',
      component: () => import('@/views/system/data-permission-rule/data-permission-rule-list.vue'),
      meta: { title: '数据权限规则列表' },
    },
    {
      path: 'new',
      name: 'systemDataPermissionRuleNew',
      component: () => import('@/views/system/data-permission-rule/data-permission-rule-edit.vue'),
      meta: { title: '新建数据权限规则' },
    },
    {
      path: ':entityId/edit',
      name: 'systemDataPermissionRuleEdit',
      component: () => import('@/views/system/data-permission-rule/data-permission-rule-edit.vue'),
      meta: { title: '编辑数据权限规则', hideMenu: true },
    },
    {
      path: ':entityId/detail',
      name: 'systemDataPermissionRuleDetail',
      component: () => import('@/views/system/data-permission-rule/data-permission-rule-detail.vue'),
      meta: { title: '查看数据权限规则', hideMenu: true },
    },
  ],
};
