export interface IUReportFile {
  id?: number; //ID
  name?: string; //名称
  content?: string | null; //内容
  createAt?: Date | null; //创建时间
  updateAt?: Date | null; //更新时间
  delFlag?: boolean | null; //软删除标志
  deletedTime?: Date | null; //软删除时间
}

export class UReportFile implements IUReportFile {
  constructor(
    public id?: number,
    public name?: string,
    public content?: string | null,
    public createAt?: Date | null,
    public updateAt?: Date | null,
    public delFlag?: boolean | null,
    public deletedTime?: Date | null
  ) {
    this.delFlag = this.delFlag ?? false;
  }
}
