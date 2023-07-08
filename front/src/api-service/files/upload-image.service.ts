import { defHttp } from '@/utils/http/axios';
import qs from 'qs';
import { get } from 'lodash-es';
import buildPaginationQueryOpts from '@/utils/jhipster/sorts';
import { PageRecord } from '@/models/baseModel';
import { useMethods } from '@/hooks/system/useMethods';
import { IUploadImage } from '@/models/files/upload-image.model';
import { UploadFileParams } from '@/types/axios';
import { pickBy } from 'lodash-es';

const apiUrl = '/api/upload-images';

export default {
  // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！

  find(id: number): Promise<IUploadImage> {
    return defHttp.get({ url: `${apiUrl}/${id}` });
  },

  retrieve(paginationQuery?: any): Promise<PageRecord<IUploadImage>> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return defHttp.get({ url: apiUrl + `?${qs.stringify(options, { arrayFormat: 'repeat' })}` });
  },

  stats(queryParams?: any): Promise<any> {
    const options = buildPaginationQueryOpts(queryParams);
    return defHttp.get({ url: `${apiUrl}/stats?${qs.stringify(options, { arrayFormat: 'repeat' })}` });
  },

  exist(queryParams?: any): Promise<Boolean> {
    if (!queryParams.hasOwnProperty('id.aggregate.count') && get(queryParams, 'id.aggregate.count')) {
      queryParams['id.aggregate.count'] = true;
    }
    const options = buildPaginationQueryOpts(queryParams);
    return new Promise((resolve, reject) => {
      defHttp
        .get({ url: `${apiUrl}/stats?${qs.stringify(options, { arrayFormat: 'repeat' })}` })
        .then(res => {
          resolve(res && res[0] && res[0]['id_count'] > 0);
        })
        .catch(err => reject(err));
    });
  },

  recycleBin(paginationQuery?: any): Promise<PageRecord<IUploadImage[]>> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return defHttp.get({ url: apiUrl + '/recycle-bin' + `?${qs.stringify(options, { arrayFormat: 'repeat' })}` });
  },

  restoreByIds(ids: number[]): Promise<IUploadImage> {
    const uploadImage = { delFlag: false };
    return defHttp.put({ url: `${apiUrl}/specified-field-batch`, params: { uploadImage, specifiedField: 'delFlag', ids } });
  },

  reallyDelete(ids: number[]): Promise<IUploadImage> {
    return defHttp.delete({ url: `${apiUrl}/really-delete`, params: { ids } });
  },

  emptyRecycleBin(): Promise<IUploadImage> {
    return defHttp.delete({ url: `${apiUrl}/empty-recycle-bin` });
  },

  delete(id: number): Promise<any> {
    return defHttp.delete({ url: `${apiUrl}/${id}` });
  },

  deleteByIds(ids: number[]): Promise<any> {
    return defHttp.delete({ url: `${apiUrl}` + `?${qs.stringify({ ids }, { arrayFormat: 'repeat' })}` });
  },

  update(uploadImage: IUploadImage, batchIds?: number[], batchFields?: String[]): Promise<IUploadImage> {
    let queryParams = '';
    if (batchIds && batchFields) {
      queryParams = qs.stringify({ batchIds, batchFields }, { arrayFormat: 'repeat' });
    }
    return defHttp.put({ url: `${apiUrl}/${uploadImage.id}?${queryParams}`, params: uploadImage });
  },
  create(uploadImage: IUploadImage, onUploadProgress?: (progressEvent: ProgressEvent) => void) {
    const params: UploadFileParams = <UploadFileParams>{};
    params.name = 'image';
    params.file = uploadImage.uploadFile as File;
    if (uploadImage.filename) {
      params.filename = uploadImage.filename;
    } else {
      params.filename = uploadImage!.uploadFile!.name;
    }
    const newUploadImage = { ...uploadImage };
    delete newUploadImage.uploadFile;
    params.data = { uploadImageDTO: pickBy(newUploadImage, value => !!value) };
    return defHttp.uploadFile<IUploadImage>(
      {
        url: `${apiUrl}`,
        onUploadProgress,
      },
      params,
      { isReturnResponse: true }
    );
  },
  uploadImageUrl: apiUrl,

  batchUpdateByField(uploadImage: IUploadImage, specifiedField: String, ids: number[] | number): Promise<IUploadImage> {
    return defHttp.put({ url: `${apiUrl}/field-batch`, params: { uploadImage, specifiedField, ids } });
  },

  /**
   * 导出xls
   * @param fileName 文件名
   * @param paginationQuery 分页参数
   * @param isXlsx 是否导出xlsx
   */
  exportExcel(fileName, paginationQuery?: any, isXlsx = false) {
    const { handleExportXls, handleExportXlsx } = useMethods();
    const options = buildPaginationQueryOpts(paginationQuery);
    if (isXlsx) {
      return handleExportXlsx(fileName, `${apiUrl}/export`, options);
    } else {
      return handleExportXls(fileName, `${apiUrl}/export`, options);
    }
  },

  /**
   * 导入xls
   * @param file 导入的文件
   * @param success 成功回调
   */
  importExcel(file, success) {
    const { handleImportXls } = useMethods();
    return handleImportXls(file, `${apiUrl}/import`, success);
  },
  // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove
};
