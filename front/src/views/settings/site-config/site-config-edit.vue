<script lang="tsx">
import { useRoute, useRouter } from 'vue-router';
import { getCurrentInstance, reactive, computed, defineComponent, h, ref, resolveComponent, toRef, Component } from 'vue';
import config from './config/edit-config';
import { SiteConfig, ISiteConfig } from '@/models/settings/site-config.model';

import ServerProvider from '@/api-service/index';
import { BasicForm } from '@/components/Form';
import { PageWrapper } from '@/components/Page';
import Icon from '@/components/Icon/Icon.vue';
import { getButtonConfig } from '@/components/Button/button-config';
import { isBoolean, isFunction } from '@/utils/is';
import { message } from 'ant-design-vue';
import { useI18n } from '@/hooks/web/useI18n';
import { useMultipleTabStore } from '@/store/modules/multipleTab';

export default defineComponent({
  // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
  name: 'SystemSiteConfigEdit',
  props: {
    entityId: {
      type: [String, Number] as PropType<string | number>,
      default: '',
    },
    containerType: {
      type: String,
      default: 'router',
    },
  },
  async setup(props) {
    const ctx = getCurrentInstance()?.proxy;
    const apiService = ctx?.$apiService as typeof ServerProvider;
    const relationshipApis: any = {
      items: apiService.settings.commonFieldDataService.retrieve,
    };
    const route = useRoute();
    const router = useRouter();
    const tabStore = useMultipleTabStore();
    const activeNames = ref<any[]>([]);
    const handleChange = (val: any[]) => {
      activeNames.value = val;
      ctx?.$emit('change', activeNames.value);
    };
    let siteConfigId = ref('');
    if (props.containerType === 'router') {
      siteConfigId.value = route.params?.entityId as string;
    } else {
      siteConfigId.value = props.entityId as string;
    }
    const siteConfig = reactive<ISiteConfig>(new SiteConfig());
    if (siteConfigId.value) {
      const data = await apiService.settings.siteConfigService.find(Number(siteConfigId.value));
      if (data) {
        Object.assign(siteConfig, data);
      }
    }
    if (config.fields && config.fields.length && Object.keys(relationshipApis).length) {
      config.fields
        .filter(item => item.field && Object.keys(relationshipApis).includes(item.field))
        .forEach(item => {
          item.componentProps = {
            ...item.componentProps,
            api: relationshipApis[item.field],
          };
        });
    }
    const formItemsConfig = config.fields;
    const submitButtonTitlePrefix = siteConfigId.value ? '更新' : '保存';
    const saveOrUpdateApi = siteConfigId.value
      ? apiService.settings.siteConfigService.update
      : apiService.settings.siteConfigService.create;
    const saveOrUpdate = () => {
      validate()
        .then(result => {
          if (result) {
            Object.assign(siteConfig, result);
            saveOrUpdateApi(siteConfig)
              .then(res => {
                Object.assign(siteConfig, res);
                message.success(submitButtonTitlePrefix + '成功！');
                ctx?.$emit('submit', { update: true, containerType: props.containerType });
              })
              .catch(error => {
                console.log('error', error);
                message.error(submitButtonTitlePrefix + '失败！');
              });
          } else {
            message.error('数据验证失败！');
          }
        })
        .catch(error => {
          console.log('error', error);
          message.error('数据验证失败！');
        });
    };
    //获得关联表属性。
    const pageConfig = reactive<any>({
      active: '0',
      operations: [
        {
          title: '关闭',
          type: 'default',
          theme: 'close',
          skipValidate: true,
          click: async () => {
            const { fullPath } = route; //获取当前路径
            await tabStore.closeTabByKey(fullPath, router);
          },
        },
        {
          hide: () => {
            return !!siteConfig.id;
          },
          type: 'primary',
          theme: 'save',
          click: saveOrUpdate,
        },
        {
          hide: () => {
            return !siteConfig.id;
          },
          theme: 'update',
          type: 'primary',
          click: saveOrUpdate,
        },
      ],
    });
    const isEdit = computed(() => {
      return true;
    });
    const validate = async () => {
      let isValid = true;
      let result = {};
      var refKeys = Object.keys(ctx?.$refs as object);
      for (const refKey of refKeys) {
        const component: any = ctx?.$refs[refKey];
        if (component && component.validate) {
          const validateResult = await component.validate();
          if (!validateResult) {
            isValid = false;
            break;
          } else {
            if (refKey === 'BASE_ENTITY') {
              Object.assign(result, validateResult);
            } else {
              result[refKey] = validateResult;
            }
          }
        }
      }
      if (!isValid) {
        return false;
      } else {
        return result;
      }
    };
    const formGroup = reactive([
      {
        title: '网站配置',
        operation: [],
        component: {
          name: 'a-form',
          props: {
            modelName: 'BASE_ENTITY',
            model: siteConfig,
            labelWidth: '120px',
            fieldMapToTime: [],
            compact: true,
            alwaysShowLines: 1,
            schemas: formItemsConfig,
            // formItemsRender,
            size: 'default',
            disabled: false,
            showAdvancedButton: false,
            showResetButton: false,
            showSubmitButton: false,
            showActionButtonGroup: false,
            resetButtonOptions: {
              type: 'default',
              size: 'default',
              text: '关闭',
              preIcon: null,
            },
            actionColOptions: {
              span: 18,
            },
            submitButtonOptions: {
              type: 'primary',
              size: 'default',
              text: submitButtonTitlePrefix,
              preIcon: null,
            },
            resetFunc: () => {
              ctx?.$emit('cancel', { update: false, containerType: props.containerType });
            },
            submitFunc: saveOrUpdate,
          },
          on: {},
        },
      },
      {
        title: '关联表',
        operation: [],
        component: [
          {
            name: 'vxe-grid',
            title: '配置项列表列表',
            props: {
              modelName: 'items',
              data: siteConfig.items,
              columns: config.itemsColumns,
              border: true,
              showOverflow: true,
              editConfig: {
                trigger: 'click',
                mode: 'row',
                activeMethod({ row, rowIndex, column, columnIndex }) {
                  console.log('activeMethod', row, rowIndex, column, columnIndex);
                  return true;
                },
              },
              onEditClosed: ({ $table, row, column }) => {
                console.log('$table', $table);
                if ($table.isUpdateByRow(row)) {
                  row.loading = true;
                  apiService.settings.siteConfigService
                    .updateBySpecifiedField({ id: row.id, [column.field]: row[column.field] }, [row.id], [column.field])
                    .then(data => {
                      $table.reloadRow(row, data, column.field);
                      message.success('保存成功！');
                    })
                    .finally(() => {
                      row.loading = false;
                    });
                }
              },
              fieldMapToTime: [],
              compact: true,
              size: 'default',
              disabled: !isEdit.value,
              toolbarConfig: {
                buttons: [
                  { code: 'insert_actived', name: '新增', icon: 'fa fa-plus' },
                  { code: 'remove', name: '删除', icon: 'fa fa-trash-o' },
                ],
                // 表格右上角自定义按钮
                tools: [
                  // { code: 'myPrint', name: '自定义打印' }
                ],
                import: false,
                export: false,
                print: false,
                custom: false,
              },
            },
          },
        ],
      },
    ]);
    const getXGridSlots = component => {
      if (component.recordActions) {
        const buttons = reactive(component.recordActions.map(action => getButtonConfig(action)));
        buttons.filter(button => button.icon).forEach(button => (button.slots = { default: () => <Icon icon={button.icon} /> }));
        buttons.filter(button => !button.icon).forEach(button => (button.slots = { default: () => button.text }));
        return {
          recordAction: row => (
            <div>
              <a-space>
                {buttons.map(button => {
                  return (
                    <a-button
                      {...{
                        type: button.type || 'primary',
                        shape: button.shape || 'circle',
                        title: button.text || button.name,
                        onClick: () => {
                          if (button.onClick) {
                            button.onClick(row.row, button.name);
                          } else {
                            console.log(button.name, 'onClick事件未定义！');
                          }
                        },
                      }}
                      v-slots={button.slots}
                    ></a-button>
                  );
                })}
              </a-space>
            </div>
          ),
        };
      } else {
        return {
          recordAction: () => <div />,
        };
      }
    };
    const formSlots = {};
    const renderChild = () => {
      const wrapperPros: any = {};
      if (!pageConfig?.canExpand) {
        wrapperPros.bordered = false;
        wrapperPros.size = 'small';
      }
      return formGroup.map(item => {
        var componentRef = toRef(item, 'component').value;
        if (componentRef && !(componentRef instanceof Array)) {
          if (componentRef.name === 'a-form') {
            if (pageConfig?.canExpand) {
              // @ts-ignore
              return h('a-collapse-panel', {}, h(BasicForm, { ...componentRef.props, ref: componentRef.props.modelName }, formSlots));
            } else {
              // @ts-ignore
              return h(BasicForm, { ...componentRef.props, ref: componentRef.props.modelName }, formSlots);
            }
          } else {
            const component = resolveComponent(componentRef.name);
            return h(
              resolveComponent(pageConfig?.canExpand ? 'a-collapse-panel' : 'a-card'),
              { ...wrapperPros },
              h(component, { ...componentRef.props, ref: componentRef.props.modelName })
            );
          }
        } else if (componentRef && componentRef instanceof Array) {
          return h(
            resolveComponent(pageConfig?.canExpand ? 'a-collapse-panel' : 'a-card'),
            { ...wrapperPros },
            h(
              resolveComponent('a-tabs'),
              {},
              componentRef.map((child, index) => {
                const childComponent: Component = resolveComponent(child.name) as Component;
                return h(
                  resolveComponent('a-tab-pane'),
                  { tab: child.title || index, key: index },
                  h(childComponent, { ...child.props, ref: child.props.modelName }, child?.name === 'vxe-grid' ? getXGridSlots(child) : {})
                );
              })
            )
          );
        } else {
          return <div>无内容</div>;
        }
      });
    };
    const slots: any = {
      rightFooter: () => (
        <div>
          <a-space>
            {pageConfig.operations.map((operation: any) => {
              const buttonSlots: any = {};
              if (operation.icon) {
                buttonSlots.icon = () => <Icon icon={operation.icon} />;
              }
              if (operation.text) {
                buttonSlots.default = () => operation.text;
              }
              const hideButton = isBoolean(operation.hide) ? operation.hide : isFunction(operation.hide) ? operation.hide() : false;
              switch (operation.theme) {
                case 'save':
                  if (!buttonSlots.icon) {
                    buttonSlots.icon = () => <Icon icon={'ant-design:save-outlined'} />;
                  }
                  if (!buttonSlots.default) {
                    buttonSlots.default = () => '保存';
                  }
                  return hideButton ? (
                    <span />
                  ) : (
                    <a-button
                      {...{
                        type: operation.type || 'default',
                        onClick: () => {
                          validate().then(result => {
                            operation.click(result);
                          });
                        },
                      }}
                      v-slots={buttonSlots}
                    ></a-button>
                  );
                case 'update':
                  if (!buttonSlots.icon) {
                    buttonSlots.icon = () => <Icon icon={'ant-design:check-outlined'} />;
                  }
                  if (!buttonSlots.default) {
                    buttonSlots.default = () => '更新';
                  }
                  return hideButton ? (
                    <span />
                  ) : (
                    <a-button
                      {...{
                        type: operation.type || 'default',
                        onClick: () => {
                          validate().then(result => {
                            operation.click(result);
                          });
                        },
                      }}
                      v-slots={buttonSlots}
                    ></a-button>
                  );
                default:
                  return hideButton ? (
                    <span />
                  ) : (
                    <a-button
                      {...{
                        type: operation.type || 'default',
                        onClick: () => {
                          if (operation.skipValidate) {
                            operation.click();
                          } else {
                            validate().then(result => {
                              operation.click(result);
                            });
                          }
                        },
                      }}
                    >
                      {operation.title}
                    </a-button>
                  );
              }
            })}
          </a-space>
        </div>
      ),
      default: () => {
        if (pageConfig?.canExpand) {
          return (
            <div>
              <a-collapse value={activeNames} onchange={handleChange} v-slots={{ default: renderChild }} />
            </div>
          );
        } else {
          return (
            <div>
              <a-card
                {...{
                  props: {
                    shadow: 'never',
                  },
                  title: siteConfigId.value ? '编辑网站配置' : '新增网站配置',
                  bordered: false,
                  size: 'small',
                }}
                v-slots={{ default: renderChild }}
              ></a-card>
            </div>
          );
        }
      },
    };
    return {
      // pageControl,
      siteConfigId,
      formGroup,
      pageConfig,
      slots,
      siteConfig,
    };
  },
  methods: {},
  render() {
    if (this.containerType === 'modal' || this.containerType === 'drawer') {
      this.slots.rightFooter = null;
      return <a-card {...this.pageConfig} v-slots={this.slots} />;
    } else {
      return (
        <PageWrapper
          {...{
            props: {
              title: this.pageConfig?.title || '编辑',
            },
          }}
          v-slots={this.slots}
        />
      );
    }
  },
});
</script>
