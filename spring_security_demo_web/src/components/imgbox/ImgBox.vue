<template>
  <div v-if="url"
       class="img-box">
    <img :src="url"
         class="img">
    <div class="img-cover"
         @click.stop="handleView">查看大图</div>
    <Modal title="查看图片"
           v-model="visible">
      <img :src="url"
           v-if="visible"
           class="img-view">
    </Modal>
  </div>
</template>

<script>
export default {
  props: ['url'],
  data () {
    return {
      visible: false
    }
  },

  methods: {
    handleView () {
      this.visible = true
    }
  }
}
</script>

<style lang="less" scoped>
.img-box {
  position: relative;
  display: inline-block;
  width: 80px;
  height: 80px;
}
.img {
  width: 80px;
  height: 80px;
}

.img-cover {
  display: none;
  width: 80px;
  left: 0px;
  bottom: 0px;
  position: absolute;
  height: 24px;
  line-height: 24px;
  opacity: 0.7;
  color: #ffffff;
  background: rgba(0, 0, 0, 0.6);
  text-align: center;
  cursor: pointer;
  overflow: hidden;
}

.img-box:hover .img-cover {
  display: block;
}

.img-view {
  width: 100%;
  height: 100%;
}
</style>
