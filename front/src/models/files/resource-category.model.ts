import { IUploadFile } from '@/models/files/upload-file.model';
import { IUploadImage } from '@/models/files/upload-image.model';

export interface IResourceCategory {
  id?: number; //ID
  title?: string | null; //标题
  code?: string | null; //代码
  orderNumber?: number | null; //排序
  delFlag?: boolean | null; //软删除标志
  deletedTime?: Date | null; //软删除时间
  files?: IUploadFile[] | null; //文件列表
  children?: IResourceCategory[] | null; //下级列表
  images?: IUploadImage[] | null; //图片列表
  parent?: IResourceCategory | null; //上级
  expand?: boolean;
  nzAddLevel?: number;
}

export class ResourceCategory implements IResourceCategory {
  constructor(
    public id?: number,
    public title?: string | null,
    public code?: string | null,
    public orderNumber?: number | null,
    public delFlag?: boolean | null,
    public deletedTime?: Date | null,
    public files?: IUploadFile[] | null,
    public children?: IResourceCategory[] | null,
    public images?: IUploadImage[] | null,
    public parent?: IResourceCategory | null,
    public expand?: boolean,
    public nzAddLevel?: number
  ) {
    this.delFlag = this.delFlag ?? false;
  }
}
