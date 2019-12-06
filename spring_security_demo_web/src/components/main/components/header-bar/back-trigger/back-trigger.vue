<template>
  <a @click="handleChange" class='back-trigger-a' type="text"><Icon :type="icon" :size="size" /></a>
</template>
<script>
export default {
  name: 'backTrigger',
  props: {
    icon: {
      type: String,
      default: 'md-rewind'
    },
    size: {
      type: Number,
      default: 26
    }
  },
  methods: {
    handleChange () {
      this.$emit('on-change', this.$router.go(-1))
    }
  }
}
</script>
<style lang="less">
@import './back-trigger.less';
</style>
