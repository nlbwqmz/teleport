<template>
  <div style="width: 100%; height: 100%; overflow: auto">
    <div class="a-content">
      <div :style="{width: '100%', textAlign: props.align}">
        <slot/>
      </div>
    </div>
  </div>
</template>

<script setup>
// eslint-disable-next-line no-undef
const props = defineProps({
  align: {
    type: String,
    default: 'center',
    required: false
  }
})
</script>

<style scoped lang="less">

</style>
