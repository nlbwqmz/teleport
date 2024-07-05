import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'getClipboard',
    component: () => import(/* webpackChunkName: "about" */ '../views/GetClipboard.vue')
  },
  {
    path: '/addClipboard',
    name: 'addClipboard',
    component: () => import(/* webpackChunkName: "about" */ '../views/AddClipboard.vue')
  },
  {
    path: '/download',
    name: 'download',
    component: () => import(/* webpackChunkName: "about" */ '../views/DownloadView.vue')
  },
  {
    path: '/upload',
    name: 'upload',
    component: () => import(/* webpackChunkName: "about" */ '../views/UploadView.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
