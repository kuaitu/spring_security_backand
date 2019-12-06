<template>
  <div class="header-bar">
    <sider-trigger :collapsed="collapsed" icon="md-menu" @on-change="handleCollpasedChange"></sider-trigger>
    <back-trigger></back-trigger>
    <custom-bread-crumb show-icon style="margin-left: 15px;" :list="breadCrumbList"></custom-bread-crumb>
    <!-- <div style="height:10px;background:red;"></div> -->
    <div class="custom-content-con">
      <slot></slot>
    </div>
    <!-- <div style="height:10px;background:red;"></div> -->
  </div>
</template>
<script>
  import siderTrigger from './sider-trigger'
  import backTrigger from './back-trigger'
  import customBreadCrumb from './custom-bread-crumb'
  import './header-bar.less'

  export default {
  name: 'HeaderBar',
  components: {
    siderTrigger,
    backTrigger,
    customBreadCrumb
  },
  props: {
    collapsed: Boolean
  },
  computed: {
    breadCrumbList () {
      return this.$route.meta.bread
    }
  },
  methods: {
    handleCollpasedChange (state) {
      this.$emit('on-coll-change', state)
    }
  }
}
</script>
