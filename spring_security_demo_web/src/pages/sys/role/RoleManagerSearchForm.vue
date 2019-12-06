<template>
  <div class="clearfix">
    <Form ref="searchForm"
          :model="searchForm"
          :label-width="92"
          style="float: left;"
          inline>
      <FormItem prop="userStatus"
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
      <FormItem class="form-item"
                label="所属机构："
                prop="organizationId">
        <Select v-model="searchForm.organizationId"
                placeholder="请选择所属机构"
                :filterable="true"
                style="width:200px">
          <Option v-for="item in orgList"
                  :value="item.organizationId"
                  :key="item.organizationId">{{ item.organizationName }}</Option>
        </Select>
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

  export default {
  data () {
    return {
      orgList: [],
      searchForm: {
        roleStatus: 'all',
        roleName: ''
      }
    }
  },

  methods: {
    doSearch () {
      let params = {}
      if (this.searchForm.organizationId) {
        params.organizationId = this.searchForm.organizationId
      }
      if (this.searchForm.roleStatus && this.searchForm.roleStatus !== 'all') {
        params.roleStatus = this.searchForm.roleStatus
      }
      if (this.searchForm.roleName) {
        params.roleName = this.searchForm.roleName
      }
      this.$emit('doSearch', params)
    },

    doReset () {
      // this.$refs.advancedSearchForm.resetFields()
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
