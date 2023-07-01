export default {
  monolithAdminApp: {
    taskjobTaskJobConfig: {
      home: {
        title: 'Task Job Configs',
        refreshListLabel: 'Refresh list',
        createLabel: 'Create a new Task Job Config',
        createOrEditLabel: 'Create or edit a Task Job Config',
        notFound: 'No Task Job Configs found',
      },
      created: 'A new Task Job Config is created with identifier {{ param }}',
      updated: 'A Task Job Config is updated with identifier {{ param }}',
      deleted: 'A Task Job Config is deleted with identifier {{ param }}',
      delete: {
        question: 'Are you sure you want to delete Task Job Config {{ id }}?',
      },
      detail: {
        title: 'Task Job Config',
      },
      id: 'ID',
      name: 'Name',
      jobClassName: 'Job Class Name',
      cronExpression: 'Cron Expression',
      parameter: 'Parameter',
      description: 'Description',
      jobStatus: 'Job Status',
      createdBy: 'Created By',
      createdDate: 'Created Date',
      lastModifiedBy: 'Last Modified By',
      lastModifiedDate: 'Last Modified Date',
      delFlag: 'Del Flag',
      deletedTime: 'Deleted Time',
      help: {
        name: '任务名称',
        jobClassName: '任务类名',
        cronExpression: 'cron表达式',
        parameter: '参数',
        description: '描述',
        jobStatus: '任务状态',
        createdBy: '创建者Id',
        createdDate: '创建时间',
        lastModifiedBy: '修改者Id',
        lastModifiedDate: '修改时间',
        delFlag: '软删除标志',
        deletedTime: '软删除时间',
      },
    },
  },
};
