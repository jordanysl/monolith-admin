<template>
  <div class="relative !h-full w-full overflow-hidden" ref="el"></div>
</template>

<script lang="ts" setup>
import { type PropType, ref, onMounted, onUnmounted, watchEffect, watch, unref, nextTick } from 'vue';
import type { Nullable } from '#/utils.d';
import { useWindowSizeFn } from '@/hooks/vben';
import { useDebounceFn } from '@vueuse/core';
import { useAppStore } from '@/store/modules/app';
import CodeMirror from 'codemirror';
import { MODE } from './../typing';
// css
import './codemirror.css';
import 'codemirror/theme/idea.css';
import 'codemirror/theme/material-palenight.css';
// modes
// 需要引入具体的语法高亮库才会有对应的语法高亮效果
import 'codemirror/mode/javascript/javascript';
import 'codemirror/mode/css/css';
import 'codemirror/mode/xml/xml.js';
import 'codemirror/mode/clike/clike.js';
import 'codemirror/mode/htmlmixed/htmlmixed';
import 'codemirror/mode/markdown/markdown.js';
import 'codemirror/mode/python/python.js';
import 'codemirror/mode/r/r.js';
import 'codemirror/mode/shell/shell.js';
import 'codemirror/mode/sql/sql.js';
import 'codemirror/mode/swift/swift.js';
import 'codemirror/mode/vue/vue.js';
// 折叠资源引入:开始
import 'codemirror/addon/fold/foldgutter.css';
import 'codemirror/addon/fold/foldcode.js';
import 'codemirror/addon/fold/brace-fold.js';
import 'codemirror/addon/fold/comment-fold.js';
import 'codemirror/addon/fold/indent-fold.js';
import 'codemirror/addon/fold/foldgutter.js';
// 折叠资源引入:结束
//光标行背景高亮，配置里面也需要styleActiveLine设置为true
import 'codemirror/addon/selection/active-line.js';
// 支持代码自动补全
import 'codemirror/addon/hint/show-hint.css';
import 'codemirror/addon/hint/show-hint.js';
import 'codemirror/addon/hint/anyword-hint.js';

const props = defineProps({
  mode: {
    type: String as PropType<MODE>,
    default: MODE.JSON,
    validator(value: any) {
      // 这个值必须匹配下列字符串中的一个
      return Object.values(MODE).includes(value);
    },
  },
  value: { type: String, default: '' },
  readonly: { type: Boolean, default: false },
});

const emit = defineEmits(['change']);

const el = ref();
let editor: Nullable<CodeMirror.Editor>;

const debounceRefresh = useDebounceFn(refresh, 100);
const appStore = useAppStore();

watch(
  () => props.value,
  async value => {
    await nextTick();
    const oldValue = editor?.getValue();
    if (value !== oldValue) {
      editor?.setValue(value ? value : '');
    }
  },
  { flush: 'post' }
);

watchEffect(() => {
  editor?.setOption('mode', props.mode);
});

watch(
  () => appStore.getDarkMode,
  async () => {
    setTheme();
  },
  {
    immediate: true,
  }
);

function setTheme() {
  unref(editor)?.setOption('theme', appStore.getDarkMode === 'light' ? 'idea' : 'material-palenight');
}

function refresh() {
  editor?.refresh();
}

async function init() {
  const addonOptions = {
    autoCloseBrackets: true,
    autoCloseTags: true,
    foldGutter: true,
    gutters: ['CodeMirror-linenumbers'],
  };

  editor = CodeMirror(el.value!, {
    value: '',
    mode: props.mode,
    readOnly: props.readonly,
    tabSize: 2,
    theme: 'material-palenight',
    lineWrapping: true,
    lineNumbers: true,
    ...addonOptions,
  });
  editor?.setValue(props.value);
  setTheme();
  editor?.on('change', () => {
    emit('change', editor?.getValue());
  });
}

onMounted(async () => {
  await nextTick();
  init();
  useWindowSizeFn(debounceRefresh);
});

onUnmounted(() => {
  editor = null;
});
</script>
