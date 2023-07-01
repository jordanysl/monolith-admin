import accountService from '@/api-service/account/account-service-provider';
import systemServices from '@/api-service/system/index';
import filesServices from '@/api-service/files/index';
import reportServices from '@/api-service/report/index';
import settingsServices from '@/api-service/settings/index';
import logServices from '@/api-service/log/index';
import taskjobServices from '@/api-service/taskjob/index';
// jhipster-needle-add-entity-service-to-main-import - BegCode will import entities services here

export default {
  account: accountService,
  system: systemServices,
  files: filesServices,
  report: reportServices,
  settings: settingsServices,
  log: logServices,
  taskjob: taskjobServices,
  // jhipster-needle-add-entity-service-to-main-body - BegCode will import entities services here
};
