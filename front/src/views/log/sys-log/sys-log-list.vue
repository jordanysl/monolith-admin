<template>
  <!-- begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！-->
  <a-card v-if="searchFormConfig.toggleSearchStatus" title="高级搜索" class="bc-list-search-form-card">
    <template #extra>
      <a-space>
        <a-button
          type="default"
          @click="showSearchFormSetting"
          preIcon="ant-design:setting-outlined"
          shape="circle"
          size="small"
        ></a-button>
      </a-space>
    </template>
    <search-form :config="searchFormConfig" @formSearch="formSearch" @close="handleToggleSearch" />
  </a-card>
  <a-card :bordered="false" class="bc-list-result-card" :bodyStyle="{ 'padding-top': '1px' }">
    <template #title>
      <a-button type="link" @click="formSearch" size="small">系统日志列表</a-button>
    </template>
    <template #extra>
      <a-space>
        <a-button type="default" @click="handleToggleSearch" preIcon="ant-design:search-outlined" shape="circle" size="small"></a-button>
        <a-divider type="vertical" />
        <a-button
          type="default"
          @click="xGrid.openImport()"
          preIcon="ant-design:cloud-upload-outlined"
          shape="circle"
          size="small"
        ></a-button>
        <a-button type="default" @click="xGrid.openExport()" preIcon="ant-design:download-outlined" shape="circle" size="small"></a-button>
        <a-button type="default" @click="xGrid.openPrint()" preIcon="ant-design:printer-outlined" shape="circle" size="small"></a-button>
        <a-button type="default" preIcon="ant-design:setting-outlined" shape="circle" size="small"></a-button>
        <a-button type="default" preIcon="ant-design:rest-twotone" shape="circle" size="small"></a-button>
      </a-space>
    </template>
    <a-row :gutter="16">
      <a-col :span="filterTreeConfig.filterTreeSpan" v-if="filterTreeConfig.filterTreeSpan > 0">
        <a-tree
          style="border: #bbcedd 1px solid; height: 100%"
          v-model="filterTreeConfig.checkedKeys"
          :expandedKeys="filterTreeConfig.expandedKeys"
          :autoExpandParent="filterTreeConfig.autoExpandParent"
          :selectedKeys="filterTreeConfig.selectedKeys"
          :treeData="filterTreeConfig.treeFilterData"
          @select="onSelect"
          @expand="onExpand"
        />
      </a-col>
      <a-col :span="24 - filterTreeConfig.filterTreeSpan">
        <vxe-grid ref="xGrid" v-bind="gridOptions" v-on="gridEvents">
          <template #toolbar_buttons>
            <a-row :gutter="16">
              <a-col :lg="2" :md="2" :sm="4" v-if="filterTreeConfig.treeFilterData.length > 0">
                <span class="table-page-search-submitButtons">
                  <a-button
                    type="primary"
                    :icon="filterTreeConfig.filterTreeSpan > 0 ? 'pic-center' : 'pic-right'"
                    @click="switchFilterTree"
                  />
                </span>
              </a-col>
              <a-col v-if="!searchFormConfig.toggleSearchStatus">
                <a-space>
                  <a-input v-model:value="searchValue" placeholder="请输入关键字" allow-clear @change="formSearch" @pressEnter="formSearch">
                    <template #prefix>
                      <Icon icon="ant-design:search-outlined" />
                    </template>
                  </a-input>
                  <template v-for="button of gridOptions.toolbarConfig.buttons">
                    <a-button v-if="!button.dropdowns">{{ button.name }}</a-button>
                    <a-dropdown v-else :key="button.name" :content="button.name">
                      <template #overlay>
                        <a-menu @click="" v-for="subButton of button.dropdowns">
                          <a-menu-item :key="subButton.name + 's'">
                            <Icon :icon="subButton.icon"></Icon>
                            {{ subButton.name }}
                          </a-menu-item>
                        </a-menu>
                      </template>
                      <a-button>
                        {{ button.name }}
                        <DownOutlined />
                      </a-button>
                    </a-dropdown>
                  </template>
                </a-space>
              </a-col>
            </a-row>
          </template>
          <template #recordAction="{ row, column }">
            <template v-if="tableRowOperations.length">
              <template
                v-for="operation in tableRowOperations.filter(
                  rowOperation => !rowOperation.disabled && !(rowOperation.hide && rowOperation.hide(row))
                )"
              >
                <template v-if="operation.name === 'save'">
                  <a-button
                    :type="operation.type || 'link'"
                    status="primary"
                    v-if="$refs.xGrid.isActiveByRow(row)"
                    :key="operation.name"
                    :icon="operation.icon || 'step-forward-outlined'"
                    :title="operation.title || '保存'"
                    @click="rowClick('save', row)"
                  >
                    <Icon icon="ant-design:save-outlined" #icon v-if="operation.type !== 'link'" />
                    <span v-else>{{ operation.title || '保存' }}</span>
                  </a-button>
                  <a-button
                    :type="operation.type || 'link'"
                    :key="operation.name"
                    v-else
                    :title="operation.title || '编辑'"
                    shape="circle"
                    @click="rowClick('edit', row)"
                  >
                    <Icon icon="ant-design:edit-outlined" #icon v-if="operation.type !== 'link'" />
                    <span v-else>{{ operation.title || '编辑' }}</span>
                  </a-button>
                </template>
                <template v-else-if="operation.name === 'delete' && !operation.disabled">
                  <a-button
                    :type="operation.type || 'link'"
                    :key="operation.name"
                    :title="operation.title || '删除'"
                    shape="circle"
                    @click="rowClick('delete', row)"
                  >
                    <Icon :icon="operation.icon || 'ant-design:delete-outlined'" #icon v-if="operation.type !== 'link'" />
                    <span v-else>{{ operation.title || '删除' }}</span>
                  </a-button>
                </template>
                <template v-else>
                  <a-button
                    :type="operation.type || 'link'"
                    v-if="!operation.disabled"
                    :key="operation.name"
                    :title="operation.title || '操作'"
                    shape="circle"
                    @click="rowClick(operation.name, row)"
                  >
                    <Icon :icon="operation.icon || 'ant-design:info-circle-outlined'" v-if="operation.type !== 'link'" #icon />
                    <span v-else>{{ operation.title || '操作' }}</span>
                  </a-button>
                </template>
              </template>
              <a-dropdown v-if="tableRowMoreOperations && tableRowMoreOperations.length">
                <template #overlay>
                  <a-menu @click="rowMoreClick($event, row)" style="border-radius: 25%">
                    <a-menu-item :key="operation.name" v-for="operation in tableRowMoreOperations.filter(operation => !operation.disabled)">
                      <Icon :icon="operation.icon" v-if="operation.icon" />
                      <span v-if="operation.type === 'link'">{{ operation.title }}</span>
                    </a-menu-item>
                  </a-menu>
                </template>
                <a-button shape="circle">
                  <Icon icon="ant-design:small-dash-outlined" />
                </a-button>
              </a-dropdown>
            </template>
          </template>
        </vxe-grid>
      </a-col>
    </a-row>
    <BasicModal v-bind="modalConfig" @register="registerModal">
      <component :is="modalConfig.componentName" @cancel="closeModal" v-bind="modalConfig" />
    </BasicModal>
    <BasicDrawer v-bind="drawerConfig" @register="registerDrawer">
      <component :is="drawerConfig.componentName" @cancel="closeDrawer" v-bind="drawerConfig" />
    </BasicDrawer>
  </a-card>
</template>

<script lang="ts" src="./sys-log-list.component.ts"></script>
<style scoped></style>
