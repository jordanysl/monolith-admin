<template>
  <!-- begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！-->
  <a-card :bordered="false">
    <a-row :gutter="16" v-if="searchFormConfig.toggleSearchStatus">
      <a-col :span="24">
        <search-form :config="searchFormConfig" @formSearch="formSearch" @close="handleToggleSearch"> </search-form>
      </a-col>
    </a-row>
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
        <card-list ref="cardList" v-bind="cardListOptions" @getMethod="cardListFetchMethod">
          <template #header_left>
            <a-row class="toolbar_buttons_xgrid" :gutter="16">
              <a-col :lg="2" :md="2" :sm="4" v-if="filterTreeConfig.treeFilterData.length > 0">
                <span class="table-page-search-submitButtons">
                  <a-button
                    type="primary"
                    :icon="filterTreeConfig.filterTreeSpan > 0 ? 'pic-center' : 'pic-right'"
                    @click="switchFilterTree"
                  ></a-button>
                </span>
              </a-col>
              <a-col v-if="!searchFormConfig.toggleSearchStatus">
                <a-input-search
                  placeholder="请输入关键字"
                  v-model:value="searchValue"
                  allow-clear
                  @search="formSearch"
                  enterButton
                  ref="searchInput"
                >
                  <template #addonAfter v-if="!searchFormConfig.disabled">
                    <a-button type="primary" @click="handleToggleSearch" style="margin-left: -2px">
                      <UpOutlined />
                    </a-button>
                  </template>
                </a-input-search>
              </a-col>
            </a-row>
          </template>
          <template #loadMore>
            <div v-if="loading" style="text-align: center; margin-top: 12px; height: 32px; line-height: 32px">
              <a-spin />
            </div>
            <div v-else style="text-align: center; margin-top: 12px; height: 32px; line-height: 32px">
              <!--              <a-button @click="handleLoadMore">加载更多</a-button>-->
            </div>
          </template>
        </card-list>
      </a-col>
    </a-row>
    <BasicModal v-bind="modalConfig" @register="registerModal">
      <component :is="modalConfig.componentName" @cancel="closeModalOrDrawer" @submit="closeModalOrDrawer" v-bind="modalConfig" />
    </BasicModal>
    <BasicDrawer v-bind="drawerConfig" @register="registerDrawer">
      <component :is="drawerConfig.componentName" @cancel="closeModalOrDrawer" @submit="closeModalOrDrawer" v-bind="drawerConfig" />
    </BasicDrawer>
  </a-card>
</template>

<script lang="ts" src="./u-report-file-list.component.ts"></script>
<style>
.toolbar_buttons_xgrid {
  margin-left: 5px !important;
}
.table-page-search-submitButtons {
  display: inline-block !important;
}
.vxe-tools--wrapper {
  padding-right: 12px;
}
</style>
