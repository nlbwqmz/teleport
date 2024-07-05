<template>
  <AContainer>
    <van-uploader accept="*/*" :before-read="upload" v-show="!overlay">
      <van-button icon="plus" type="primary">上传文件</van-button>
    </van-uploader>
  </AContainer>
  <teleport to="body">
    <van-overlay :show="overlay" class-name="a-content">
      <div style="color: wheat; margin-bottom: 10px">{{ fileName }}</div>
      <van-circle
        v-model:current-rate="currentRate"
        :rate="rate"
        size="200px"
      >
        <div class="a-content" style="font-size: 12px; font-weight: bold; color: wheat">
          <div style="font-size: 16px;">进度：{{ rate + '%' }}</div>
          <div>速度：{{ uploadSpeed }}</div>
          <div>大小：{{ `${loadedSize}/${totalSize}` }}</div>
        </div>
      </van-circle>
    </van-overlay>
  </teleport>
</template>

<script setup>
import AContainer from '@/component/AContainer.vue'
import { showConfirmDialog, showNotify } from 'vant'
import request from '@/util/api'
import { ref } from 'vue'
const overlay = ref(false)
const currentRate = ref(0)
const rate = ref(0)
const uploadSpeed = ref('')
const loadedSize = ref('')
const totalSize = ref('')
const fileName = ref('')
const formatBytes = (bytes, decimals = 2) => {
  if (bytes === 0) return '0 Bytes'
  const k = 1024
  const dm = decimals < 0 ? 0 : decimals
  const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB']
  const i = Math.floor(Math.log(bytes) / Math.log(k))
  return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i]
}
const round = (value, n) => {
  return Math.round(value * Math.pow(10, n)) / Math.pow(10, n)
}
const upload = file => {
  showConfirmDialog({
    message: `确认上传：${file.name}?`
  }).then(() => {
    request({
      url: '/uploadCheck',
      method: 'get',
      params: { fileName: file.name }
    }).then(() => {
      currentRate.value = 0
      rate.value = 0
      overlay.value = true
      fileName.value = file.name
      const formData = new FormData()
      formData.append('file', file, file.name)
      request({
        url: '/upload',
        method: 'post',
        headers: {
          'Content-Type': 'multipart/form-data'
        },
        data: formData,
        onUploadProgress: function (e) {
          rate.value = round(e.progress * 100, 4)
          uploadSpeed.value = formatBytes(e.bytes) + '/秒'
          loadedSize.value = formatBytes(e.loaded)
          totalSize.value = formatBytes(e.total)
        }
      }).then(() => {
        showNotify({ type: 'success', message: '上传成功!' })
      }).catch(() => {}).finally(() => {
        overlay.value = false
      })
    }).catch(() => {})
  }).catch(() => {})
  return false
}
</script>
