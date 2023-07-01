<template>
  <CollapseContainer title="基本设置" :canExpan="false">
    <a-row :gutter="24">
      <a-col :span="14">
        <BasicForm @register="register" />
      </a-col>
      <a-col :span="10">
        <div class="change-avatar">
          <div class="mb-2">头像</div>
          <CropperAvatar
            :uploadApi="uploadImage"
            :value="avatar"
            btnText="更换头像"
            :btnProps="{ preIcon: 'ant-design:cloud-upload-outlined' }"
            @change="updateAvatar"
            width="150"
          />
        </div>
      </a-col>
    </a-row>
    <Button type="primary" @click="handleSubmit"> 更新基本信息 </Button>
  </CollapseContainer>
</template>
<script lang="ts">
import { Button, Row, Col } from 'ant-design-vue';
import { computed, defineComponent, onMounted } from 'vue';
import { BasicForm, useForm } from '@/components/Form/index';
import { CollapseContainer } from '@/components/Container';
import { CropperAvatar } from '@/components/Cropper';

import { useMessage } from '@/hooks/web/useMessage';

import headerImg from '@/assets/images/header.jpg';
import { baseSetschemas } from './data';
import { useUserStore } from '@/store/modules/user';
import uploadImageService from '@/api-service/files/upload-image.service';
import { getFileAccessHttpUrl } from '@/utils/common/compUtils';
import accountService from '@/api-service/account/account.service';

export default defineComponent({
  components: {
    BasicForm,
    CollapseContainer,
    Button,
    ARow: Row,
    ACol: Col,
    CropperAvatar,
  },
  setup() {
    const { createMessage } = useMessage();
    const userStore = useUserStore();

    const [register, { setFieldsValue, validate }] = useForm({
      labelWidth: 120,
      schemas: baseSetschemas,
      showActionButtonGroup: false,
    });

    onMounted(async () => {
      //const data = await accountInfoApi();
      const userInfo = userStore.getUserInfo;
      if (!userInfo.mobile) {
        userInfo.mobile = '';
      }
      console.log('userInfo', userInfo);
      setFieldsValue(userInfo);
    });

    const avatar = computed(() => {
      const { imageUrl } = userStore.getUserInfo;
      return getFileAccessHttpUrl(imageUrl) || headerImg;
    });

    function updateAvatar(src: string, data: string) {
      console.log('data====》', data);
      const userinfo = userStore.getUserInfo;
      userinfo.imageUrl = data;
      userStore.setUserInfo(userinfo);
      if (data) {
        accountService.updateImageUrl(data).then(() => {});
      }
    }
    /**
     *更新基本信息
     * */
    async function handleSubmit() {
      try {
        let values = await validate();
        values.avatar = userStore.getUserInfo.avatar;
        //提交表单
        await accountService.updateAccount(values);
        const userinfo = userStore.getUserInfo;
        Object.assign(userinfo, values);
        userStore.setUserInfo(userinfo);
        createMessage.success('更新成功');
      } catch (e) {
        console.log('e', e);
      }
    }

    return {
      avatar,
      register,
      uploadImage: uploadImageService.uploadImage,
      updateAvatar,
      handleSubmit,
    };
  },
});
</script>

<style lang="less" scoped>
.change-avatar {
  img {
    display: block;
    margin-bottom: 15px;
    border-radius: 50%;
  }
}
</style>
