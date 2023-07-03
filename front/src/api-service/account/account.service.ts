import { defHttp } from '@/utils/http/axios';
import { GetUserInfoModel, LoginParams } from '@/api-service/sys/model/userModel';
import { ErrorMessageMode } from '@/types/axios';
import { User } from '@/models/system/user.model';

const baseApiUrl = '/api';

// begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！

export default {
  authenticateWithoutCaptcha(params: LoginParams, mode: ErrorMessageMode = 'modal'): Promise<Object> {
    return new Promise<Object>((resolve, reject) => {
      defHttp
        .post({ url: `${baseApiUrl}/authenticate/withoutCaptcha`, params }, { errorMessageMode: mode })
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  },
  authenticate(params: LoginParams, mode: ErrorMessageMode = 'modal'): Promise<Object> {
    return new Promise<Object>((resolve, reject) => {
      defHttp
        .post({ url: `${baseApiUrl}/authenticate`, params }, { errorMessageMode: mode })
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  },
  getAccount(mode: ErrorMessageMode = 'none'): Promise<User> {
    return defHttp.get({ url: `${baseApiUrl}/account` }, { errorMessageMode: mode });
  },
  updateImageUrl(url: string): Promise<Object> {
    return new Promise<GetUserInfoModel>((resolve, reject) => {
      defHttp
        .put({ url: `${baseApiUrl}/account/imageUrl`, params: `?imageUrl=${url}` }, { errorMessageMode: 'none' })
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  },
  updateAccount(userInfo: any): Promise<User> {
    return new Promise<User>((resolve, reject) => {
      defHttp
        .post({ url: `${baseApiUrl}/account`, params: userInfo }, { errorMessageMode: 'none' })
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  },
  changePassword(param: any): Promise<Object> {
    return new Promise<GetUserInfoModel>((resolve, reject) => {
      defHttp
        .post({ url: `${baseApiUrl}/account/change-password`, params: param }, { errorMessageMode: 'none' })
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  },

  // jhipster-needle-service-add-method - BegCode will add getters and setters here, do not remove
};
