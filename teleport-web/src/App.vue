<template>
    <div style="width: 100%; height: calc(100% - 50px);">
      <router-view />
    </div>
    <van-tabbar route>
      <van-tabbar-item replace to="/" icon="notes-o">获取剪切板</van-tabbar-item>
      <van-tabbar-item replace to="/addClipboard" icon="records-o">设置剪切板</van-tabbar-item>
      <van-tabbar-item replace to="/download" icon="down">下载文件</van-tabbar-item>
      <van-tabbar-item replace to="/upload" icon="upgrade">上传文件</van-tabbar-item>
    </van-tabbar>
</template>

<style lang="less">
body, html, #app {
  width: 100%;
  height: 100%;
}
* {
  margin: 0;
  padding: 0;
}
</style>
<script setup lang="ts">
</script>
