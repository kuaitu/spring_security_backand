<template>
<div class="closable">
  <slot></slot>
  <div @click="close" class="closable__btn">
    <Badge text="x"></Badge>
  </div>
</div>
</template>

<script>
export default {
  methods: {
    close () {
      this.$emit('close')
    }
  }
}
</script>

<style lang="less" scoped>
.closable {
  position: relative;
  &__btn {
    position: absolute;
    right: -128px;
    top: -15px;
  }
}
</style>
