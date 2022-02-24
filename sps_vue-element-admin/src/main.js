import Vue from 'vue'

import 'normalize.css/normalize.css' // a modern alternative to CSS resets

import Element from 'element-ui'
import './styles/element-variables.scss'

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'

import './icons' // icon
import './permission' // permission control

import {title} from './settings.js'

// element-ui 全局组件样式
Vue.use(Element, {
  size: 'mini'
})
Vue.config.productionTip = false

// 全局弹窗样式
import '@/sa-frame/kj/layer/theme/default/layer.css';
import layer from '@/sa-frame/kj/layer/layer';
global.layer = layer;
Vue.prototype.layer = layer;

// 全局sa对象
import '@/sa-frame/sa.css'
import sa from '@/sa-frame/sa.js'
global.sa = sa;
Vue.prototype.sa = sa

// 全局表单组件
import SaItem from '@/sa-frame/com/sa-item.vue'
import SaTd from '@/sa-frame/com/sa-td.vue'
import SaInfo from '@/sa-frame/com/sa-info.vue'
Vue.component('sa-item', SaItem)
Vue.component('sa-td', SaTd)
Vue.component('sa-info', SaInfo)

// 文件上传工具方法封装
import '@/sa-frame/kj/upload-util.js';

new Vue({
  el: '#app',
  data: {
    title: title
  },
  router,
  store,
  render: h => h(App)
})
