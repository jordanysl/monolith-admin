import { Component } from 'vue';
import UserList from '@/views/system/user/user-list.vue';

const componentMap = new Map<String, Component>();

componentMap.set('UserList', UserList);
export { componentMap };
