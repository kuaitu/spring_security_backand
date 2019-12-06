<template>
  <div class="horizontal-bg">
    <div class="horizontal-bg-t"></div>
    <div class="horizontal-bg-b" :style="{width: width + 'px'}">
      <div class="horizontal-bg-b-r"></div>
      <div class="view-label">{{title}}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PartInfoTitle',
  props: {
    title: {
      type: String,
      default: '分块信息标题'
    },
    width: {
      type: Number,
      default: 130
    }
  }
}
</script>

<style scoped>
  .horizontal-bg {
    display: flex;
    flex-direction: column;
    margin: 12px 0;
  }
  .horizontal-bg-t {
    width:100%;
    height:3px;
    background:rgba(205,218,250,1);
  }
  .horizontal-bg-b {
    display: flex;
    justify-content: start;
    width:130px;
    height:20px;
    background: linear-gradient(-45deg, transparent 15px, rgba(205,218,250,1) 0);
    margin-right: 10px;
    justify-content: start;
  }
  .horizontal-bg-b-r {
    width: 3px;
    height: 12px;
    background: #3053ab;
    margin-right: 5px;
    margin-left: 15px;
    margin-top: 4px;
  }
</style>
