import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Vant from 'vant'
import 'vant/lib/index.css'
import './assets/common.less'

window.basePath = process.env.VUE_APP_BASE_URL

createApp(App).use(store).use(router).use(Vant).mount('#app')
