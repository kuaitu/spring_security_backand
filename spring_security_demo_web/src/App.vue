<template>
  <router-view class="fs-text"
               id="app"></router-view>
</template>

<script>
export default {
  name: 'app',

  mounted () {
    this.$Message.config({
      top: 50,
      duration: 10
    })
    this.$router.push('/')
  }
}
</script>

<style lang="less">
.size {
  width: 100%;
  height: 100%;
}
html,
body {
  .size;
  // overflow: hidden;
  margin: 0;
  padding: 0;
  // 下面2个是为了适应页面拉小新增属性
  min-width: 1280px;
  overflow: auto;
}
#app {
  .size;
}
</style>
