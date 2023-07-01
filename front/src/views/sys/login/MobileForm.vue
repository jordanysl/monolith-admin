<template>
  <template v-if="getShow">
    <LoginFormTitle class="enter-x" />
    <Form class="p-4 enter-x" :model="formData" :rules="getFormRules" ref="formRef">
      <FormItem name="mobile" class="enter-x">
        <Input size="large" v-model:value="formData.mobile" :placeholder="t('sys.login.mobile') || '手机号码'" class="fix-auto-fill" />
      </FormItem>
      <FormItem name="sms" class="enter-x">
        <CountdownInput
          size="large"
          class="fix-auto-fill"
          v-model:value="formData.sms"
          :placeholder="t('sys.login.smsCode') || '短信验证码'"
          :sendCodeApi="sendCodeApi"
        />
      </FormItem>

      <FormItem class="enter-x">
        <Button type="primary" size="large" block @click="handleLogin" :loading="loading">
          {{ t('sys.login.loginButton') || '登录' }}
        </Button>
        <Button size="large" block class="mt-4" @click="handleBackLogin">
          {{ t('sys.login.backSignIn') || '返回' }}
        </Button>
      </FormItem>
    </Form>
  </template>
</template>
<script lang="ts" setup>
import { reactive, ref, computed, unref, toRaw } from 'vue';
import { Form, Input, Button } from 'ant-design-vue';
import { CountdownInput } from '@/components/CountDown';
import LoginFormTitle from './LoginFormTitle.vue';
import { useI18n } from '@/hooks/web/useI18n';
import { useMessage } from '@/hooks/web/useMessage';
import { useLoginState, useFormRules, useFormValid, LoginStateEnum, SmsEnum } from './useLogin';
import { useUserStore } from '@/store/modules/user'; // todo 没有需要增加
import { getSmsCaptcha } from '@/api-service/sys/user'; // todo 这个统一处理到apiService中。

// begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！

const FormItem = Form.Item;
const { t } = useI18n();
const { handleBackLogin, getLoginState } = useLoginState();
const { getFormRules } = useFormRules();
const { notification, createErrorModal } = useMessage();
const userStore = useUserStore();

const formRef = ref();
const loading = ref(false);

const formData = reactive({
  mobile: '',
  sms: '',
});

const { validForm } = useFormValid(formRef);

const getShow = computed(() => unref(getLoginState) === LoginStateEnum.MOBILE);

/**
 * 登录
 */
async function handleLogin() {
  const data = await validForm();
  if (!data) return;
  try {
    loading.value = true;
    const userInfo = await userStore.phoneLogin(
      toRaw({
        mobile: data.mobile,
        captcha: data.sms,
        mode: 'none', //不要默认的错误提示
      })
    );
    if (userInfo) {
      notification.success({
        message: t('sys.login.loginSuccessTitle') || '登录成功',
        description: `${t('sys.login.loginSuccessDesc') || '欢迎回来'}: ${userInfo.realname}`,
        duration: 3,
      });
    }
  } catch (error) {
    console.log('error', error);
    notification.error({
      message: t('sys.api.errorTip') || '错误提示',
      description: error.message || t('sys.api.networkExceptionMsg') || '网络异常，请检查您的网络连接是否正常!',
      duration: 3,
    });
  } finally {
    loading.value = false;
  }
}
//倒计时执行前的函数
function sendCodeApi() {
  return getSmsCaptcha({ mobile: formData.mobile, smsmode: SmsEnum.FORGET_PASSWORD });
}
</script>
