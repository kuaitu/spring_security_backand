<template>
  <Form ref="searchForm" :model="searchForm" :label-width="100" inline>
    <FormItem class="form-item" label="菜单名称" prop="menuName">
      <Input v-model="searchForm.menuName" placeholder="请输入菜单名称" style="width: 220px"></Input>
    </FormItem>
    <FormItem >
      <Button @click="doSearch" type="primary" icon="ios-search">查询</Button>
      <Button @click="doReset" type="ghost" icon="md-refresh" style="margin-left: 8px">重置</Button>
    </FormItem>
  </Form>
</template>

<script>
  import UserService from '@/service/UserService'

  export default {
  data () {
    return {
      model1: '',
      searchForm: {
        menuName: ''
      }
    }
  },

  methods: {
    doSearch () {
      let param = {}
      if (this.searchForm.menuName) {
        param.menuName = this.searchForm.menuName
      }
      this.$emit('doSearch', param)
    },

    doReset () {
      this.$refs.searchForm.resetFields()
      this.$emit('doSearch', {})
    }
  },
  computed: {
    hasPlatformSearch () {
      return UserService.hasFunc('platform_exp_search')
    }
  }
}
</script>
<style lang="less" scoped>
.form-item {
  width: 300px;
}
</style>
