import type { ErrorMessageMode } from '/#/axios';
import { useMessage } from '@/hooks/web/useMessage';
import { useI18n } from '@/hooks/web/useI18n';
// import router from '@/router';
// import { PageEnum } from '@/enums/pageEnum';
import { useUserStoreWithOut } from '@/store/modules/user';
import projectSetting from '@/settings/projectSetting';
import { SessionTimeoutProcessingEnum } from '@/enums/appEnum';

// begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！

const { createMessage, createErrorModal } = useMessage();
const error = createMessage.error!;
const stp = projectSetting.sessionTimeoutProcessing;

export function checkStatus(status: number, msg: string, errorMessageMode: ErrorMessageMode = 'message'): void {
  const { t } = useI18n();
  const userStore = useUserStoreWithOut();
  let errMessage = '';

  switch (status) {
    case 400:
      errMessage = `${msg}`;
      break;
    // 401: Not logged in
    // Jump to the login page if not logged in, and carry the path of the current page
    // Return to the current page after successful login. This step needs to be operated on the login page.
    case 401:
      userStore.setToken(undefined);
      errMessage = msg || t('sys.api.errMsg401') || '用户没有权限（令牌、用户名、密码错误）!';
      if (stp === SessionTimeoutProcessingEnum.PAGE_COVERAGE) {
        userStore.setSessionTimeout(true);
      } else {
        userStore.logout(true);
      }
      break;
    case 403:
      errMessage = t('sys.api.errMsg403') || '用户得到授权，但是访问是被禁止的。!';
      break;
    // 404请求不存在
    case 404:
      errMessage = t('sys.api.errMsg404') || '网络请求错误,未找到该资源!';
      break;
    case 405:
      errMessage = t('sys.api.errMsg405') || '网络请求错误,请求方法未允许!';
      break;
    case 408:
      errMessage = t('sys.api.errMsg408') || '网络请求超时!';
      break;
    case 500:
      errMessage = t('sys.api.errMsg500') || '服务器错误,请联系管理员!';
      break;
    case 501:
      errMessage = t('sys.api.errMsg501') || '网络未实现!';
      break;
    case 502:
      errMessage = t('sys.api.errMsg502') || '网络错误!';
      break;
    case 503:
      errMessage = t('sys.api.errMsg503') || '服务不可用，服务器暂时过载或维护!';
      break;
    case 504:
      errMessage = t('sys.api.errMsg504') || '网络超时!';
      break;
    case 505:
      errMessage = t('sys.api.errMsg505') || 'http版本不支持该请求!';
      break;
    default:
  }

  if (errMessage) {
    if (errorMessageMode === 'modal') {
      createErrorModal({ title: t('sys.api.errorTip') || '错误提示', content: errMessage });
    } else if (errorMessageMode === 'message') {
      error({ content: errMessage, key: `global_error_message_status_${status}` });
    }
  }
}
