<template>
  <div>
    <a-row class="select-row" type="flex" :gutter="8">
      <a-col class="left" :class="{ full: true }">
        <!-- 显示加载效果 -->
        <a-input v-if="loading" readOnly placeholder="加载中…">
          <template #prefix>
            <LoadingOutlined />
          </template>
        </a-input>
        <a-select
          v-if="showComponentName === 'select'"
          ref="select"
          v-model:value="selectData.value"
          :placeholder="placeholder"
          :mode="multiple"
          :open="false"
          :disabled="disabled"
          :options="options"
          :maxTagCount="maxTagCount"
          @change="handleChange"
          style="width: 100%"
          @click="!disabled && openModal()"
        />
        <a-button v-else-if="showComponentName === 'button'" :icon="buttonIcon" @click="!disabled && openModal()">{{
          modalTitle
        }}</a-button>
      </a-col>
    </a-row>
    <BasicModal
      @register="register"
      :title="modalTitle"
      width="900px"
      wrapClassName="j-user-select-modal"
      @ok="handleOk"
      destroyOnClose
      @visible-change="visibleChange"
    >
      <component :is="dynamicComponent" ref="tableRef" />
    </BasicModal>
  </div>
</template>
<script lang="ts">
import { defineComponent, ref, reactive } from 'vue';
import { propTypes } from '@/utils/propTypes';
import { useAttrs } from '@/hooks/vben/useAttrs';
import { LoadingOutlined } from '@ant-design/icons-vue';
import { useModalInner } from '@/components/Modal';
import BasicModal from '@/components/Modal/src/BasicModal.vue';
import { componentMap } from './comoponentMap';
import { isArray, isObject } from '@/utils/is';
import { MehOutlined } from '@ant-design/icons-vue';

export default defineComponent({
  name: 'SelectModal',
  components: { BasicModal, LoadingOutlined, MehOutlined },
  inheritAttrs: false,
  props: {
    modelValue: propTypes.oneOfType([propTypes.string, propTypes.array, propTypes.number]),
    disabled: propTypes.bool.def(false),
    placeholder: {
      type: String,
      default: '请选择',
    },
    // 是否支持多选，默认 true
    multiple: {
      type: String,
      default: 'multiple',
    },
    // 是否正在加载
    loading: propTypes.bool.def(false),
    // 最多显示多少个 tag
    maxTagCount: propTypes.number,
    // buttonIcon
    buttonIcon: propTypes.string.def(''),
    modalTitle: {
      type: String,
      default: '请选择',
    },
    showComponentName: propTypes.string.def('select'),
    componentName: propTypes.string.def('UserList'),
    api: propTypes.func,
    resultField: propTypes.string.def('records'),
    labelField: propTypes.string.def('label'),
    valueField: propTypes.string.def('value'),
    labelInValue: propTypes.bool.def(false),
    selectOptions: propTypes.arrayOf(propTypes.any).def([]),
  },
  emits: ['handleOpen', 'change', 'register', 'update:value'],
  async setup(props, { emit, refs }) {
    const tableRef = ref(null);
    const [register, { closeModal, setModalProps }] = useModalInner(() => {
      //update-begin-author:taoyan date:2022-6-2 for: VUEN-1112 一对多 用户选择 未显示选择条数，及清空
      setTimeout(() => {
        if (tableRef.value) {
          tableRef.value.setSelectedRowKeys(selectData['value'] || []);
        }
      }, 800);
      //update-end-author:taoyan date:2022-6-2 for: VUEN-1112 一对多 用户选择 未显示选择条数，及清空
    });
    const options = reactive<any[]>([]);
    //接收下拉框选项
    if (props.selectOptions && props.selectOptions.length) {
      props.selectOptions.forEach(option => {
        options.push({
          ...option,
          [props.valueField]: option[props.valueField],
          [props.labelField]: option[props.labelField],
        });
      });
    } else if (props.labelInValue) {
      if (isArray(props.selectValues) && props.selectValues.length) {
        options.push(
          ...props.selectValues.map(valueItem => ({
            ...valueItem,
            value: valueItem[props.valueField],
            label: valueItem[props.labelField],
          }))
        );
      }
      if (isObject(props.selectValues) && Object.keys(props.selectValues).length > 1) {
        options.push({ ...props.selectValues, value: props.selectValues[props.valueField], label: props.selectValues[props.labelField] });
      }
    } else if (props.api) {
      const params: any = {};
      if (props.multiple === 'multiple' && isArray(props.selectValues) && props.selectValues.length > 0) {
        params[`${props.valueField}.in`] = props.selectValues.map(valueItem => valueItem[props.valueField]);
      }
      if (props.multiple !== 'multiple' && props.selectValues) {
        // eslint-disable-next-line vue/no-setup-props-destructure
        params[`${props.valueField}.equals`] = props.selectValues;
      }
      const data = await props.api(params);
      if (data.records && data.records.length) {
        options.push(...data.records.map(record => ({ ...record, value: record[props.valueField], label: record[props.labelField] })));
      }
    }

    //接收选择的值
    const attrs = useAttrs();
    const dynamicComponent = componentMap.get(props.componentName);
    const modalVisible = ref(false);

    /**
     * 打开弹出框
     */
    function openModal() {
      setModalProps({
        visible: true,
      });
    }
    const selectData = reactive({ value: props.modelValue, change: false });

    /**
     * 下拉框值改变事件
     */
    function handleChange(value) {
      selectData.value = value;
      selectData.change = true;
      emit('change', value);
      emit('update:value', value);
    }

    function handleOk() {
      // todo 回传选项和已选择的值
      let selectRecords = [];
      if (tableRef.value) {
        selectRecords = tableRef.value.getCheckboxRecords() || [];
      }
      emit('change', selectRecords);
      emit('update:value', selectRecords);
      closeModal();
    }
    function visibleChange(_visible) {}

    return {
      attrs,
      options,
      handleChange,
      openModal,
      register,
      closeModal,
      handleOk,
      visibleChange,
      dynamicComponent,
      selectData,
      modalVisible,
      setModalProps,
      tableRef,
    };
  },
});
</script>
<style lang="less" scoped>
.select-row {
  @width: 82px;

  .left {
    width: calc(100% - @width - 8px);
  }

  .right {
    width: @width;
  }

  .full {
    width: 100%;
  }

  :deep(.ant-select-search__field) {
    display: none !important;
  }
}
</style>
