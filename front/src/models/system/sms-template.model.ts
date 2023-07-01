import { MessageSendType } from '@/models/enumerations/message-send-type.model';
export interface ISmsTemplate {
  id?: number; //ID
  name?: string | null; //模板标题
  code?: string | null; //模板CODE
  type?: keyof typeof MessageSendType | null; //模板类型
  content?: string | null; //模板内容
  testJson?: string | null; //模板测试json
  createdBy?: number | null; //创建者Id
  createdDate?: Date | null; //创建时间
  lastModifiedBy?: number | null; //修改者Id
  lastModifiedDate?: Date | null; //修改时间
  delFlag?: boolean | null; //软删除标志
  deletedTime?: Date | null; //软删除时间
}

export class SmsTemplate implements ISmsTemplate {
  constructor(
    public id?: number,
    public name?: string | null,
    public code?: string | null,
    public type?: keyof typeof MessageSendType | null,
    public content?: string | null,
    public testJson?: string | null,
    public createdBy?: number | null,
    public createdDate?: Date | null,
    public lastModifiedBy?: number | null,
    public lastModifiedDate?: Date | null,
    public delFlag?: boolean | null,
    public deletedTime?: Date | null
  ) {
    this.delFlag = this.delFlag ?? false;
  }
}
