import { defHttp } from '@/utils/http/axios';
import qs from 'qs';
import { get } from 'lodash-es';
import buildPaginationQueryOpts from '@/utils/jhipster/sorts';
import { PageRecord } from '@/models/baseModel';
import { useMethods } from '@/hooks/system/useMethods';
import { IAnnouncement } from '@/models/system/announcement.model';
import { AnnoCategory } from '@/models/enumerations/anno-category.model';

const apiUrl = '/api/announcements';

export default {
  // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！

  find(id: number): Promise<IAnnouncement> {
    return defHttp.get({ url: `${apiUrl}/${id}` });
  },

  retrieve(paginationQuery?: any): Promise<PageRecord<IAnnouncement>> {
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

  recycleBin(paginationQuery?: any): Promise<PageRecord<IAnnouncement[]>> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return defHttp.get({ url: apiUrl + '/recycle-bin' + `?${qs.stringify(options, { arrayFormat: 'repeat' })}` });
  },

  restoreByIds(ids: number[]): Promise<IAnnouncement> {
    const announcement = { delFlag: false };
    return defHttp.put({ url: `${apiUrl}/specified-field-batch`, params: { announcement, specifiedField: 'delFlag', ids } });
  },

  reallyDelete(ids: number[]): Promise<IAnnouncement> {
    return defHttp.delete({ url: `${apiUrl}/really-delete`, params: { ids } });
  },

  emptyRecycleBin(): Promise<IAnnouncement> {
    return defHttp.delete({ url: `${apiUrl}/empty-recycle-bin` });
  },

  delete(id: number): Promise<any> {
    return defHttp.delete({ url: `${apiUrl}/${id}` });
  },

  deleteByIds(ids: number[]): Promise<any> {
    return defHttp.delete({ url: `${apiUrl}` + `?${qs.stringify({ ids }, { arrayFormat: 'repeat' })}` });
  },

  create(announcement: IAnnouncement): Promise<IAnnouncement> {
    return defHttp.post({ url: `${apiUrl}`, params: announcement });
  },
  update(announcement: IAnnouncement, batchIds?: number[], batchFields?: String[]): Promise<IAnnouncement> {
    let queryParams = '';
    if (batchIds && batchFields) {
      queryParams = qs.stringify({ batchIds, batchFields }, { arrayFormat: 'repeat' });
    }
    return defHttp.put({ url: `${apiUrl}/${announcement.id}?${queryParams}`, params: announcement });
  },
  retrieveUnread(category: AnnoCategory): Promise<any> {
    const paginationQuery = {
      page: 0,
      size: 5,
    };
    const options = buildPaginationQueryOpts(paginationQuery);
    return new Promise<any>((resolve, reject) => {
      defHttp
        .get({ url: apiUrl + '/current-user/unread/' + category + `?${qs.stringify(options, { arrayFormat: 'repeat' })}` })
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  },
  release(id: number): Promise<IAnnouncement> {
    return new Promise<IAnnouncement>((resolve, reject) => {
      defHttp
        .put({ url: `${apiUrl}/${id}/release` })
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  },
  read(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      defHttp
        .put({ url: `${apiUrl}/${id}/read` })
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  },

  batchUpdateByField(announcement: IAnnouncement, specifiedField: String, ids: number[] | number): Promise<IAnnouncement> {
    return defHttp.put({ url: `${apiUrl}/field-batch`, params: { announcement, specifiedField, ids } });
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
