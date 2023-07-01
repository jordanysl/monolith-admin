import cacheManagerService from '@/api-service/system/cache-manager.service';
import apiPermissionService from '@/api-service/system/api-permission.service';
import announcementRecordService from '@/api-service/system/announcement-record.service';
import authorityService from '@/api-service/system/authority.service';
import viewPermissionService from '@/api-service/system/view-permission.service';
import userService from '@/api-service/system/user.service';
import dataPermissionRuleService from '@/api-service/system/data-permission-rule.service';
import smsMessageService from '@/api-service/system/sms-message.service';
import smsTemplateService from '@/api-service/system/sms-template.service';
import announcementService from '@/api-service/system/announcement.service';
// jhipster-needle-add-entity-service-to-main-import - JHipster will import entities services here

export default {
  cacheManagerService,
  apiPermissionService,
  announcementRecordService,
  authorityService,
  viewPermissionService,
  userService,
  dataPermissionRuleService,
  smsMessageService,
  smsTemplateService,
  announcementService,
  // jhipster-needle-add-entity-service-to-main-body - JHipster will export entities services here
};
