<template>
  <div class="clearfix">
    <Form ref="searchForm"
          :model="searchForm"
          :label-width="92"
          style="float: left;"
          inline>
      <FormItem prop="roleStatus"
                :label-width="0">
        <dict-item-select dictName="COMMON_STATUS"
                          v-model="searchForm.roleStatus"
                          allStatus>
        </dict-item-select>
      </FormItem>
      <FormItem class="form-item"
                prop="roleName"
                label="角色名称：">
        <Input v-model.trim="searchForm.roleName"
               placeholder="请输入角色名称"
               clearable />
      </FormItem>
    </Form>
    <Button @click="doSearch($event)"
            type="primary"
            icon="ios-search"
            style="margin-left:8px;">搜 索</Button>
    <Button @click="doReset"
            icon="md-refresh"
            style="margin-left: 8px">重置</Button>
    <div style="float:right;">
      <Button @click="addRole"
              type="primary"
              v-if="hasAddFunc"
              icon="md-add">新增角色</Button>
    </div>
  </div>
</template>
<script>
  import UserService from '@/service/UserService'
  import Format from '@/utils/format'

  export default {
  data () {
    return {
      searchForm: {
        roleStatus: '',
        roleName: ''
      }
    }
  },

  methods: {
    doSearch () {
      if (event) {
        event.preventDefault()
      }
      let param = Format.formatFormData(this.searchForm)
      // TODO: 其他特殊的格式化
      this.$emit('doSearch', param)
    },

    doReset () {
      this.searchForm.roleStatus = 'all'
      this.$refs.searchForm.resetFields()
      this.$emit('doSearch', {})
    },

    addRole () {
      this.$emit('addRole')
    }
  },

  computed: {
    hasAddFunc () {
      return UserService.hasFunc('role_add')
    }
  },

  mounted () {
  },

  components: {}
}
</script>
<style lang="less" scoped>
.clearfix:after {
  content: "."; /*内容为“.”就是一个英文的句号而已。也可以不写。*/
  display: block; /*加入的这个元素转换为块级元素。*/
  clear: both; /*清除左右两边浮动。*/
  visibility: hidden; /*可见度设为隐藏。注意它和display:none;是有区别的。visibility:hidden;仍然占据空间，只是看不到而已；*/
  line-height: 0; /*行高为0；*/
  height: 0; /*高度为0；*/
  font-size: 0; /*字体大小为0；*/
}
.ivu-form-item {
  margin-bottom: 12px;
}
.form-item {
  width: 300px;
}
</style>
