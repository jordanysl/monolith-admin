<template class="inline-block">
  <Select
    @dropdown-visible-change="handleFetch"
    v-bind="$attrs"
    @change="handleChange"
    :options="getOptions"
    :show-search="showSearch"
    @search="searchHandle"
    v-model:value="state"
  >
    <template #[item]="data" v-for="item in Object.keys($slots)">
      <slot :name="item" v-bind="data || {}"></slot>
    </template>
    <template #suffixIcon v-if="loading || showAdd">
      <LoadingOutlined spin v-if="loading" />
      <plus-outlined @click="emitAdd" v-if="showAdd" />
    </template>
    <template #notFoundContent v-if="loading">
      <span>
        <LoadingOutlined spin class="mr-1" />
        请等待数据加载完成...
      </span>
    </template>
  </Select>
</template>
<script lang="ts">
import { defineComponent, PropType, ref, watchEffect, computed, unref, watch } from 'vue';
import { Select } from 'ant-design-vue';
import { isFunction } from '@/utils/is';
import { useRuleFormItem } from '@/hooks/component/useFormItem';
import { useAttrs } from '@/hooks/vben/useAttrs';
import { get, omit } from 'lodash-es';
import { LoadingOutlined, PlusOutlined } from '@ant-design/icons-vue';
import { propTypes } from '@/utils/propTypes';

type OptionsItem = { label: string; value: string; disabled?: boolean };

export default defineComponent({
  name: 'ApiSelect',
  components: {
    Select,
    LoadingOutlined,
    PlusOutlined,
  },
  inheritAttrs: false,
  props: {
    value: [Array, Object, String, Number],
    numberToString: propTypes.bool,
    api: {
      type: Function as PropType<(arg?: Recordable) => Promise<OptionsItem[]>>,
      default: null,
    },
    // api params
    params: {
      type: Object as PropType<Recordable>,
      default: () => ({}),
    },
    searchParams: {
      type: Object as PropType<Recordable>,
      default: () => ({}),
    },
    showSearch: propTypes.bool.def(false),
    // support xxx.xxx.xx
    resultField: propTypes.string.def(''),
    labelField: propTypes.string.def('label'),
    valueField: propTypes.string.def('value'),
    immediate: propTypes.bool.def(true),
    alwaysLoad: propTypes.bool.def(false),
    showAdd: propTypes.bool.def(false),
  },
  emits: ['options-change', 'change', 'show-add'],
  setup(props, { emit }) {
    const options = ref<OptionsItem[]>([]);
    const loading = ref(false);
    const isFirstLoad = ref(true);
    const emitData = ref<any[]>([]);
    const attrs = useAttrs();
    const searchKeyword = ref('');
    const searchParamValues = computed(() => {
      const res: Recordable = { ...props.searchParams };
      Object.keys(props.searchParams)
        .filter(param => param !== 'useOr')
        .forEach(key => {
          if (!res[key]) {
            res[key] = searchKeyword.value;
          }
        });
      return res;
    });

    // Embedded in the form, just use the hook binding to perform form verification
    const [state, setState] = useRuleFormItem(props, 'value', 'change', emitData);

    const getOptions = computed(() => {
      const { labelField, valueField, numberToString } = props;

      return unref(options).reduce((prev, next: Recordable) => {
        if (next) {
          const value = next[valueField];
          prev.push({
            ...omit(next, [labelField, valueField]),
            label: next[labelField],
            value: numberToString ? `${value}` : value,
          });
        }
        return prev;
      }, [] as OptionsItem[]);
    });

    watchEffect(() => {
      props.immediate && !props.alwaysLoad && fetch();
    });

    watch(
      () => props.params,
      () => {
        console.log('watch:props.params', props.params);
        !unref(isFirstLoad) && fetch();
      },
      { deep: true }
    );
    watch(
      () => searchKeyword.value,
      () => {
        !unref(isFirstLoad) && fetch();
      },
      { deep: true }
    );

    async function fetch() {
      const api = props.api;
      if (!api || !isFunction(api)) return;
      options.value = [];
      try {
        loading.value = true;
        console.log('props.params', props.params);
        if (props.params) {
          delete props.params.apiSelectRefresh;
        }
        const res = await api({ ...props.params, ...unref(searchParamValues) });
        if (Array.isArray(res)) {
          options.value = res;
          emitChange();
          return;
        }
        if (props.resultField) {
          options.value = get(res, props.resultField) || [];
        }
        emitChange();
      } catch (error) {
        console.warn(error);
      } finally {
        loading.value = false;
        unref(attrs).mode == 'multiple' && !Array.isArray(unref(state)) && setState([]);
      }
    }

    async function handleFetch(visible) {
      if (visible) {
        if (props.alwaysLoad) {
          await fetch();
        } else if (!props.immediate && unref(isFirstLoad)) {
          await fetch();
          isFirstLoad.value = false;
        }
      }
    }

    function emitChange() {
      emit('options-change', unref(getOptions));
    }

    function handleChange(_, ...args) {
      // 需要还原为原始值
      const { valueField, labelField } = props;
      if (attrs?.labelInValue) {
        args.forEach((item: any) => {
          if (valueField !== 'value') {
            item[valueField] = item.value;
          }
          if (labelField !== 'label') {
            item[labelField] = item.label;
          }
        });
        Object.assign(_, args[0].option);
      }
      emitData.value = args;
    }

    function searchHandle(value) {
      searchKeyword.value = value;
      fetch();
    }

    const emitAdd = () => {
      emit('show-add');
    };
    return { state, attrs, getOptions, loading, handleFetch, handleChange, searchHandle, emitAdd };
  },
});
</script>
