<template>
  <div>
    <Form ref="searchForm"
          :model="searchForm"
          :label-width="80"
          inline>
      <FormItem class="form-item"
                label="企业名称："
                prop="corporationName">
        <Input v-model.trim="searchForm.corporationName"
               placeholder="请输入企业名称"></Input>
      </FormItem>
      <FormItem label="发布渠道："
                prop="publishChannel">
        <dict-item-select dictName="PUBLISH_CHANNEL"
                          allStatus
                          v-model="searchForm.publishChannel"
                          style="width: 157px">
        </dict-item-select>
      </FormItem>
      <FormItem label="状态："
                prop="auditState">
        <dict-item-select dictName="AUDIT_STATE"
                          allStatus
                          v-model="searchForm.auditState"
                          style="width: 157px">
        </dict-item-select>
      </FormItem>
      <div class="action-row-s">
        <div class="action-row-s-t">
          <div class="action-row-s-t-r"></div>
          <div>查询结果：</div>
        </div>
        <div class="action-row-s-b">
          <Button @click="doSearch($event)"
                  type="primary"
                  icon="ios-search"
                  style="margin-left: 8px"
                  html-type="submit"
                  ref="searchBtn">搜索</Button>
          <Button @click="doReset"
                  icon="md-refresh"
                  type="success"
                  style="margin-left: 8px">重置</Button>
          <Button @click="doAdd"
                  icon="md-add"
                  type="warning"
                  style="margin-left: 8px">录入</Button>
        </div>
      </div>
    </Form>
  </div>
</template>

<script>
export default {
  data () {
    return {
      searchForm: {
        corporationName: '',
        publishChannel: '',
        auditState: ''
      }
    }
  },
  mounted () {
  },

  methods: {
    doSearch (event) {
      if (event) {
        event.preventDefault()
      }
      let param = {}
      if (this.searchForm.memberName) {
        param.memberName = this.searchForm.memberName
      }
      if (this.searchForm.organizationId) {
        param.organizationId = this.searchForm.organizationId
      }
      if (this.searchForm.code) {
        param.code = this.searchForm.code
      }
      this.$emit('doSearch', param)
    },

    setSearchBtnFoucs () {
      this.$refs.searchBtn.$el.focus()
    },

    doReset () {
      this.$refs.searchForm.resetFields()
      this.doSearch()
    },
    doAdd () {
      this.$emit('doAdd')
    }
  }
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
  .action-row-s {
    display: flex;
    justify-content: space-between;
  }
  .action-row-s-t {
    margin-right: 10px;
    display: flex;
    margin-top: 20px;
    justify-content: center;
  }
  .action-row-s-t-r {
    width: 3px;
    height: 12px;
    background: #3053ab;
    margin-right: 5px;
    margin-top: 4px;
  }
  .action-row-s-b {
    margin-bottom: 15px;
  }// right: 0;
</style>
