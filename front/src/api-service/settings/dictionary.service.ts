import { defHttp } from '@/utils/http/axios';
import qs from 'qs';
import buildPaginationQueryOpts from '@/utils/jhipster/sorts';
import { PageRecord } from '@/models/baseModel';
import { useMethods } from '@/hooks/system/useMethods';
import { IDictionary } from '@/models/settings/dictionary.model';

const apiUrl = '/api/dictionaries';

export default {
  // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！

  find(id: number): Promise<IDictionary> {
    return defHttp.get({ url: `${apiUrl}/${id}` });
  },

  retrieve(paginationQuery?: any): Promise<PageRecord<IDictionary>> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return defHttp.get({ url: apiUrl + `?${qs.stringify(options, { arrayFormat: 'repeat' })}` });
  },

  stats(queryParams?: any): Promise<any> {
    const options = buildPaginationQueryOpts(queryParams);
    return defHttp.get({ url: `${apiUrl}/stats?${qs.stringify(options, { arrayFormat: 'repeat' })}` });
  },

  recycleBin(paginationQuery?: any): Promise<PageRecord<IDictionary[]>> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return defHttp.get({ url: apiUrl + '/recycle-bin' + `?${qs.stringify(options, { arrayFormat: 'repeat' })}` });
  },

  restoreByIds(ids: number[]): Promise<IDictionary> {
    const dictionary = { delFlag: false };
    return defHttp.put({ url: `${apiUrl}/specified-field-batch`, params: { dictionary, specifiedField: 'delFlag', ids } });
  },

  reallyDelete(ids: number[]): Promise<IDictionary> {
    return defHttp.delete({ url: `${apiUrl}/really-delete`, params: { ids } });
  },

  emptyRecycleBin(): Promise<IDictionary> {
    return defHttp.delete({ url: `${apiUrl}/empty-recycle-bin` });
  },

  delete(id: number): Promise<any> {
    return defHttp.delete({ url: `${apiUrl}/${id}` });
  },

  deleteByIds(ids: number[]): Promise<any> {
    return defHttp.delete({ url: `${apiUrl}` + `?${qs.stringify({ ids }, { arrayFormat: 'repeat' })}` });
  },

  create(dictionary: IDictionary): Promise<IDictionary> {
    return defHttp.post({ url: `${apiUrl}`, params: dictionary });
  },
  update(dictionary: IDictionary, batchIds?: number[], batchFields?: String[]): Promise<IDictionary> {
    let queryParams = '';
    if (batchIds && batchFields) {
      queryParams = qs.stringify({ batchIds, batchFields }, { arrayFormat: 'repeat' });
    }
    return defHttp.put({ url: `${apiUrl}/${dictionary.id}?${queryParams}`, params: dictionary });
  },

  batchUpdateByField(dictionary: IDictionary, specifiedField: String, ids: number[] | number): Promise<IDictionary> {
    return defHttp.put({ url: `${apiUrl}/field-batch`, params: { dictionary, specifiedField, ids } });
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