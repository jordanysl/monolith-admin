<script lang="tsx">
import { useRoute, useRouter } from 'vue-router';
import { getCurrentInstance, reactive, computed, defineComponent, h, ref, resolveComponent, toRef, Component } from 'vue';
import config from './config/edit-config';
import { ViewPermission, IViewPermission } from '@/models/system/view-permission.model';

import ServerProvider from '@/api-service/index';
import { BasicForm } from '@/components/Form';
import { PageWrapper } from '@/components/Page';
import Icon from '@/components/Icon/Icon.vue';
import { isBoolean, isFunction } from '@/utils/is';
import { message } from 'ant-design-vue';
import { useI18n } from '@/hooks/web/useI18n';
import { useMultipleTabStore } from '@/store/modules/multipleTab';

export default defineComponent({
  // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
  name: 'SystemViewPermissionEdit',
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
      children: apiService.system.viewPermissionService.tree,
      'parent.id': apiService.system.viewPermissionService.tree,
      authorities: apiService.system.authorityService.tree,
    };
    const route = useRoute();
    const router = useRouter();
    const tabStore = useMultipleTabStore();
    const activeNames = ref<any[]>([]);
    const handleChange = (val: any[]) => {
      activeNames.value = val;
      ctx?.$emit('change', activeNames.value);
    };
    let viewPermissionId = ref('');
    if (props.containerType === 'router') {
      viewPermissionId.value = route.params?.entityId as string;
    } else {
      viewPermissionId.value = props.entityId as string;
    }
    const viewPermission = reactive<IViewPermission>(new ViewPermission());
    if (viewPermissionId.value) {
      const data = await apiService.system.viewPermissionService.find(Number(viewPermissionId.value));
      if (data) {
        Object.assign(viewPermission, data);
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
    const submitButtonTitlePrefix = viewPermissionId.value ? '更新' : '保存';
    const saveOrUpdateApi = viewPermissionId.value
      ? apiService.system.viewPermissionService.update
      : apiService.system.viewPermissionService.create;
    const saveOrUpdate = () => {
      validate()
        .then(result => {
          if (result) {
            Object.assign(viewPermission, result);
            saveOrUpdateApi(viewPermission)
              .then(res => {
                Object.assign(viewPermission, res);
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
            return !!viewPermission.id;
          },
          type: 'primary',
          theme: 'save',
          click: saveOrUpdate,
        },
        {
          hide: () => {
            return !viewPermission.id;
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
        title: '可视权限',
        operation: [],
        component: {
          name: 'a-form',
          props: {
            modelName: 'BASE_ENTITY',
            model: viewPermission,
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
    ]);
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
                  h(childComponent, { ...child.props, ref: child.props.modelName }, {})
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
                  title: viewPermissionId.value ? '编辑可视权限' : '新增可视权限',
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
      viewPermissionId,
      formGroup,
      pageConfig,
      slots,
      viewPermission,
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
