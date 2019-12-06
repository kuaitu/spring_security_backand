<template>
  <img v-on:mouseover="mouseover" :src="src || url" :onerror="errImg"/>
</template>

<script>
export default {
  props: ['src'],

  data () {
    return {
      url: require('@/assets/imgs/error/err.png'),
      errImg: `this.src="${require('@/assets/imgs/error/err.png')}"`
    }
  },

  methods: {
    mouseover () {
      this.$emit('mouseover', this.src)
    }
  }
}
</script>

<style lang="less" scoped>
</style>
