<template>
  <AContainer align="left">
    <van-cell-group>
      <van-cell :title="item.fileName" :value="item.size" v-for="(item, index) in list" :key="index" @click="download(item)"/>
    </van-cell-group>
  </AContainer>
</template>

<script setup>
import AContainer from '@/component/AContainer.vue'
import { onMounted, ref } from 'vue'
import request from '@/util/api'
import { showConfirmDialog } from 'vant'
const list = ref([])

const selectList = () => {
  request({
    url: '/list',
    method: 'get'
  }).then(res => {
    list.value = res.data
  })
}

onMounted(() => {
  selectList()
})

const download = file => {
  showConfirmDialog({
    message: `确认下载：${file.fileName}?`
  }).then(() => {
    const link = document.createElement('a')
    link.href = `${window.basePath}/download?fileName=${file.fileName}`
    link.download = file.fileName
    link.target = '_blank'
    link.click()
  }).catch(() => {})
}
</script>
