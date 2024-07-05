<template>
  <AContainer>
    <div style="width: 100%;">
      <textarea v-model="value" style="height: 300px; width: 80%; padding: 5px"/>
    </div>
    <div style="width: 100%; text-align: center; margin-top: 20px">
      <van-space :size="10">
        <van-button type="danger" @click="clear">清空</van-button>
        <van-button type="success" @click="paste">粘贴</van-button>
        <van-button type="primary" @click="addClipboard">设置</van-button>
      </van-space>
    </div>
  </AContainer>
</template>

<script setup>
import AContainer from '@/component/AContainer.vue'
import { ref } from 'vue'
import { showConfirmDialog, showDialog, showNotify } from 'vant'
import request from '@/util/api'
const value = ref('')
const clear = () => {
  showConfirmDialog({
    message: '确认清空?'
  }).then(() => {
    value.value = ''
  }).catch(() => {})
}
const paste = () => {
  const clipboard = navigator.clipboard
  if (!clipboard) {
    showNotify({ type: 'danger', message: '当前浏览器不支持读取剪切板!' })
    return
  }
  clipboard.readText().then(data => {
    value.value = data
  }).catch(() => {
    showNotify({ type: 'danger', message: '读取剪切板错误!' })
  })
}
const addClipboard = () => {
  if (!value.value) {
    showDialog({
      message: '请先输入文字!'
    }).then(() => {})
    return
  }
  showConfirmDialog({
    message: '确认设置?'
  }).then(() => {
    request({ url: '/addClipboard', method: 'post', data: { value: value.value } }).then(res => {
      showNotify({ type: 'success', message: '设置成功!' })
    }).catch(() => {})
  }).catch(() => {})
}
</script>
