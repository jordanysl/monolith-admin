<template>
  <template v-if="getShow">
    <LoginFormTitle class="enter-x" />
    <Form class="p-4 enter-x" :model="formData" :rules="getFormRules" ref="formRef">
      <FormItem name="account" class="enter-x">
        <Input class="fix-auto-fill" size="large" v-model:value="formData.account" :placeholder="t('sys.login.userName') || '账号'" />
      </FormItem>
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
      <FormItem name="password" class="enter-x">
        <StrengthMeter size="large" v-model:value="formData.password" :placeholder="t('sys.login.password') || '密码'" />
      </FormItem>
      <FormItem name="confirmPassword" class="enter-x">
        <InputPassword
          size="large"
          visibilityToggle
          v-model:value="formData.confirmPassword"
          :placeholder="t('sys.login.confirmPassword') || '确认密码'"
        />
      </FormItem>

      <FormItem class="enter-x" name="policy">
        <!-- No logic, you need to deal with it yourself -->
        <Checkbox v-model:checked="formData.policy" size="small">
          {{ t('sys.login.policy') || '我同意xxx隐私政策' }}
        </Checkbox>
      </FormItem>

      <Button type="primary" class="enter-x" size="large" block @click="handleRegister" :loading="loading">
        {{ t('sys.login.registerButton') || '注册' }}
      </Button>
      <Button size="large" block class="mt-4 enter-x" @click="handleBackLogin">
        {{ t('sys.login.backSignIn') || '返回' }}
      </Button>
    </Form>
  </template>
</template>
<script lang="ts" setup>
import { reactive, ref, unref, computed, toRaw } from 'vue';
import LoginFormTitle from './LoginFormTitle.vue';
import { Form, Input, Button, Checkbox } from 'ant-design-vue';
import { StrengthMeter } from '@/components/StrengthMeter';
import { CountdownInput } from '@/components/CountDown';
import { useI18n } from '@/hooks/web/useI18n';
import { useMessage } from '@/hooks/web/useMessage';
import { useLoginState, useFormRules, useFormValid, LoginStateEnum } from './useLogin';
import { getSmsCaptcha } from '@/api-service/sys/user'; // Todo 没有，需要增加。

// begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！

const FormItem = Form.Item;
const InputPassword = Input.Password;
const { t } = useI18n();
const { handleBackLogin, getLoginState } = useLoginState();
const { notification, createErrorModal } = useMessage();

const formRef = ref();
const loading = ref(false);

const formData = reactive({
  account: '',
  password: '',
  confirmPassword: '',
  mobile: '',
  sms: '',
  policy: false,
});

const { getFormRules } = useFormRules(formData);
const { validForm } = useFormValid(formRef);

const getShow = computed(() => unref(getLoginState) === LoginStateEnum.REGISTER);

/**
 * 注册
 */
async function handleRegister() {
  const data = await validForm();
  if (!data) return;
  try {
    loading.value = true;
    const resultInfo = await register(
      toRaw({
        username: data.account,
        password: data.password,
        phone: data.mobile,
        smscode: data.sms,
      })
    );
    if (resultInfo && resultInfo.data.success) {
      notification.success({
        description: resultInfo.data.message || t('sys.api.registerMsg') || '注册成功',
        duration: 3,
      });
      handleBackLogin();
    } else {
      notification.warning({
        message: t('sys.api.errorTip') || '错误提示',
        description: resultInfo.data.message || t('sys.api.networkExceptionMsg') || '网络异常，请检查您的网络连接是否正常!',
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
//发送验证码的函数
function sendCodeApi() {
  return getSmsCaptcha({ mobile: formData.mobile, smsmode: SmsEnum.REGISTER });
}
</script>
