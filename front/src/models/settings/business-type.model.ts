export interface IBusinessType {
  id?: number; //ID
  name?: string | null; //名称
  code?: string | null; //代码
  description?: string | null; //描述
  icon?: string | null; //图标
  delFlag?: boolean | null; //软删除标志
  deletedTime?: Date | null; //软删除时间
}

export class BusinessType implements IBusinessType {
  constructor(
    public id?: number,
    public name?: string | null,
    public code?: string | null,
    public description?: string | null,
    public icon?: string | null,
    public delFlag?: boolean | null,
    public deletedTime?: Date | null
  ) {
    this.delFlag = this.delFlag ?? false;
  }
}
