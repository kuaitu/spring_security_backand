// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import 'babel-polyfill'
import Vue from 'vue'
import App from './App'
import axios from 'axios'
import qs from 'qs'
import router from './router'
import VueResource from 'vue-resource'
import iView from 'iview'
import 'iview/dist/styles/iview.css'
import './my-theme/index.less'
import TcUI from '@/components'
import MImg from '@/components/MImg'
import '@/assets/icons/iconfont.css'
import './index.less'
import Base from '@/libs/base'
import '@/filters/filters'
import AlCascader from '@/components/alCascader/alCascader'
import DictItemSelect from '@/pages/widgets/DictItemSelect'
import apiConfig from '../config/api.config'
import echarts from 'echarts'
// 注册全局方法
import UserService from './service/UserService.js'

Vue.prototype.$echarts = echarts
// 注册全局方法
Vue.prototype.$UserService = UserService
// 全局注册，使用方法为:this.$axios
Vue.prototype.$axios = axios
axios.defaults.baseURL = apiConfig.baseUrl
axios.defaults.withCredentials = false
// axios.defaults.baseURL = '/downLoad'
// 全局注册，使用方法为:this.qs
Vue.prototype.qs = qs

Vue.component('m-img', MImg)
Vue.component('AlCascader', AlCascader)
Vue.component('DictItemSelect', DictItemSelect)
Vue.use(VueResource)
Vue.use(iView)
Vue.use(TcUI)
// 引入全局方法
Vue.use(Base)
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
