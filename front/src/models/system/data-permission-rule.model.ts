export interface IDataPermissionRule {
  id?: number; //ID
  permissionId?: string | null; //菜单ID
  name?: string | null; //规则名称
  column?: string | null; //字段
  conditions?: string | null; //条件
  value?: string | null; //规则值
  disabled?: boolean | null; //禁用状态
  createdBy?: number | null; //创建者Id
  createdDate?: Date | null; //创建时间
  lastModifiedBy?: number | null; //修改者Id
  lastModifiedDate?: Date | null; //修改时间
  delFlag?: boolean | null; //软删除标志
  deletedTime?: Date | null; //软删除时间
}

export class DataPermissionRule implements IDataPermissionRule {
  constructor(
    public id?: number,
    public permissionId?: string | null,
    public name?: string | null,
    public column?: string | null,
    public conditions?: string | null,
    public value?: string | null,
    public disabled?: boolean | null,
    public createdBy?: number | null,
    public createdDate?: Date | null,
    public lastModifiedBy?: number | null,
    public lastModifiedDate?: Date | null,
    public delFlag?: boolean | null,
    public deletedTime?: Date | null
  ) {
    this.disabled = this.disabled ?? false;
    this.delFlag = this.delFlag ?? false;
  }
}
