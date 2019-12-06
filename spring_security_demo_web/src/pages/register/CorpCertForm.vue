<template>
  <div>
    <Form ref="userForm" class="form" inline :model="userModel" :label-width="120">
      这是企业资质信息
    </Form>
    <Button class="next" @click="next" type="primary" size="large">下一步</Button>
  </div>
</template>

<script>
export default {
  components: {},
  props: [],
  data () {
    return {
      userModel: {}
    }
  },
  watch: {},
  computed: {},
  methods: {
    next () {
      this.$emit('next')
    }
  },
  created () {},
  mounted () {
  }
}
</script>
<style lang="less" scoped>
.next {
  text-align: center;
  width: 20%;
}
</style>
