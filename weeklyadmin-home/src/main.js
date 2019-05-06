// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import VueResource from 'vue-resource'
import 'vue-easytable/libs/themes-base/index.css'
import {VTable, VPagination} from 'vue-easytable'
import ElementUI from 'element-ui'
import eventVue from '@/utils/event'
import 'element-ui/lib/theme-chalk/index.css'
import tinymce from 'vue-tinymce-editor'
import moment from 'moment'
Vue.component('tinymce', tinymce)
Vue.component(VTable.name, VTable)
Vue.component(VPagination.name, VPagination)

Vue.use(VueResource)
Vue.use(ElementUI)
Vue.use(moment)
Vue.prototype.eventVue = eventVue
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
