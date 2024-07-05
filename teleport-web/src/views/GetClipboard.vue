<template>
  <AContainer>
    <div style="width: 100%;">
      <textarea v-model="value" style="height: 300px; width: 80%; padding: 5px"/>
    </div>
    <div style="width: 100%; text-align: center; margin-top: 20px">
      <van-space :size="10">
        <van-button type="danger" @click="clear">清空</van-button>
        <van-button type="success" @click="copy">复制</van-button>
        <van-button type="primary" @click="getClipboard">获取</van-button>
      </van-space>
    </div>
  </AContainer>
</template>
<script setup>
import AContainer from '@/component/AContainer.vue'
import { ref } from 'vue'
import { showConfirmDialog, showDialog, showNotify } from 'vant'
import useClipboard from 'vue-clipboard3'
import request from '@/util/api'
const { toClipboard } = useClipboard()
const value = ref('')
const clear = () => {
  showConfirmDialog({
    message: '确认清空?'
  }).then(() => {
    value.value = ''
  }).catch(() => {})
}
const copy = () => {
  if (!value.value) {
    showDialog({
      message: '没有内容可以复制!'
    }).then(() => {})
    return
  }
  toClipboard(value.value).then(() => {
    showNotify({ type: 'success', message: '复制成功!' })
  })
}
const getClipboard = () => {
  request({ url: '/getClipboard', method: 'get' }).then(res => {
    value.value = res.data
  }).catch(() => {})
}
</script>
