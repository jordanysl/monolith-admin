<template>
  <Dropdown placement="bottomLeft" :overlayClassName="`${prefixCls}-dropdown-overlay`">
    <span :class="[prefixCls, `${prefixCls}--${theme}`]" class="flex">
      <img :class="`${prefixCls}__header`" :src="getAvatarUrl" />
      <span :class="`${prefixCls}__info hidden md:block`">
        <span :class="`${prefixCls}__name  `" class="truncate">
          {{ getUserInfo.firstName }}
        </span>
      </span>
    </span>

    <template #overlay>
      <Menu @click="handleMenuClick">
        <MenuItem key="doc" :text="t('layout.header.dropdownItemDoc')" icon="ion:document-text-outline" v-if="getShowDoc" />
        <MenuDivider v-if="getShowDoc" />
        <MenuItem key="account" :text="t('layout.header.dropdownItemSwitchAccount')" icon="ant-design:setting-outlined" />
        <MenuItem key="password" :text="t('layout.header.dropdownItemSwitchPassword')" icon="ant-design:edit-outlined" />
        <!--        <MenuItem key="depart" :text="t('layout.header.dropdownItemSwitchDepart')" icon="ant-design:cluster-outlined" />-->
        <MenuItem v-if="getUseLockPage" key="lock" :text="t('layout.header.tooltipLock')" icon="ion:lock-closed-outline" />
        <MenuItem key="logout" :text="t('layout.header.dropdownItemLoginOut')" icon="ion:power-outline" />
      </Menu>
    </template>
  </Dropdown>
  <LockAction @register="register" />
  <UpdatePassword ref="updatePasswordRef" />
</template>
<script lang="ts">
// components
import { defineComponent, computed, ref } from 'vue';
import { Dropdown, Menu } from 'ant-design-vue';
import type { MenuInfo } from 'ant-design-vue/lib/menu/src/interface';

import { SITE_URL } from '@/settings/siteSetting';

import { useUserStore } from '@/store/modules/user';
import { useHeaderSetting } from '@/hooks/setting/useHeaderSetting';
import { useI18n } from '@/hooks/web/useI18n';
import { useDesign } from '@/hooks/web/useDesign';
import { useModal } from '@/components/Modal';
import { useMessage } from '@/hooks/web/useMessage';
import { useGo } from '@/hooks/web/usePage';

import headerImg from '@/assets/images/header.jpg';
import { propTypes } from '@/utils/propTypes';
import { openWindow } from '@/utils';

import { createAsyncComponent } from '@/utils/factory/createAsyncComponent';

import { getFileAccessHttpUrl } from '@/utils/common/compUtils';

type MenuEvent = 'logout' | 'doc' | 'lock' | 'cache' | 'depart' | 'account' | 'password';
const { createMessage } = useMessage();

export default defineComponent({
  name: 'UserDropdown',
  components: {
    Dropdown,
    Menu,
    MenuItem: createAsyncComponent(() => import('./DropMenuItem.vue')),
    MenuDivider: Menu.Divider,
    LockAction: createAsyncComponent(() => import('../lock/LockModal.vue')),
    UpdatePassword: createAsyncComponent(() => import('./UpdatePassword.vue')),
  },
  props: {
    theme: propTypes.oneOf(['dark', 'light']),
  },
  setup() {
    const { prefixCls } = useDesign('header-user-dropdown');
    const { t } = useI18n();
    const { getShowDoc, getUseLockPage } = useHeaderSetting();
    const userStore = useUserStore();
    const go = useGo();

    const getUserInfo = computed(() => {
      const { firstName = '', imageUrl, desc } = userStore.getUserInfo || {};
      return { firstName, avatar: imageUrl || headerImg, desc };
    });

    const getAvatarUrl = computed(() => {
      let { avatar } = getUserInfo.value;
      if (avatar == headerImg) {
        return avatar;
      } else {
        return getFileAccessHttpUrl(avatar);
      }
    });

    const [register, { openModal }] = useModal();
    /**
     * 多部门弹窗逻辑
     */
    const loginSelectRef = ref();

    function handleLock() {
      openModal(true);
    }

    //  login out
    function handleLoginOut() {
      userStore.confirmLoginOut();
    }

    // open doc
    function openDoc() {
      openWindow(SITE_URL);
    }
    // 切换部门
    function updateCurrentDepart() {
      loginSelectRef.value.show();
    }
    // 修改密码
    const updatePasswordRef = ref();
    function updatePassword() {
      updatePasswordRef.value.show(userStore.getUserInfo.login);
    }

    function handleMenuClick(e: MenuInfo) {
      switch (e.key as MenuEvent) {
        case 'logout':
          handleLoginOut();
          break;
        case 'doc':
          openDoc();
          break;
        case 'lock':
          handleLock();
          break;
        case 'depart':
          updateCurrentDepart();
          break;
        case 'password':
          updatePassword();
          break;
        case 'account':
          go(`/account/settings`);
          break;
      }
    }

    return {
      t,
      prefixCls,
      getUserInfo,
      getAvatarUrl,
      handleMenuClick,
      getShowDoc,
      register,
      getUseLockPage,
      loginSelectRef,
      updatePasswordRef,
    };
  },
});
</script>
<style lang="less">
@prefix-cls: ~'@{namespace}-header-user-dropdown';

.@{prefix-cls} {
  align-items: center;
  height: @header-height;
  padding: 0 0 0 10px;
  padding-right: 10px;
  overflow: hidden;
  font-size: 12px;
  cursor: pointer;

  img {
    width: 24px;
    height: 24px;
    margin-right: 12px;
  }

  &__header {
    border-radius: 50%;
  }

  &__name {
    font-size: 14px;
  }

  &--dark {
    &:hover {
      background-color: @header-dark-bg-hover-color;
    }
  }

  &--light {
    &:hover {
      background-color: @header-light-bg-hover-color;
    }

    .@{prefix-cls}__name {
      color: @text-color-base;
    }

    .@{prefix-cls}__desc {
      color: @header-light-desc-color;
    }
  }

  &-dropdown-overlay {
    .ant-dropdown-menu-item {
      min-width: 160px;
    }
  }
}
</style>
