import { SmsProvider } from '@/models/enumerations/sms-provider.model';
export interface ISmsConfig {
  id?: number; //ID
  provider?: keyof typeof SmsProvider | null; //提供商
  smsCode?: string | null; //资源编号
  templateId?: string | null; //模板ID
  accessKey?: string | null; //accessKey
  secretKey?: string | null; //secretKey
  regionId?: string | null; //regionId
  signName?: string | null; //短信签名
  remark?: string | null; //备注
  enabled?: boolean | null; //启用
  delFlag?: boolean | null; //软删除标志
  deletedTime?: Date | null; //软删除时间
}

export class SmsConfig implements ISmsConfig {
  constructor(
    public id?: number,
    public provider?: keyof typeof SmsProvider | null,
    public smsCode?: string | null,
    public templateId?: string | null,
    public accessKey?: string | null,
    public secretKey?: string | null,
    public regionId?: string | null,
    public signName?: string | null,
    public remark?: string | null,
    public enabled?: boolean | null,
    public delFlag?: boolean | null,
    public deletedTime?: Date | null
  ) {
    this.enabled = this.enabled ?? false;
    this.delFlag = this.delFlag ?? false;
  }
}
