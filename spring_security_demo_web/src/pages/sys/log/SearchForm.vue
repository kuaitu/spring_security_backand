<template>
  <div>
    <Form ref="searchForm"
          :model="searchForm"
          :label-width="80"
          inline>
      <FormItem label="日志类型："
                prop="logType">
        <dict-item-select dictName="LEVEL_LOG"
                          allStatusText="全部类型"
                          allStatus
                          v-model="searchForm.logType"
                          style="width: 157px"
                          @onChange="setSearchBtnFoucs">
        </dict-item-select>
      </FormItem>
        <FormItem label="时间："
                      prop="logTime">
              <DatePicker v-model="searchForm.logTime"
                          placeholder="时间"
                          style="width:100%"
                          :editable="false"
                          clearable></DatePicker>
            </FormItem>
      <FormItem class="form-item"
                label="操作人员："
                prop="realName">
        <Input v-model.trim="searchForm.realName"
               placeholder="请输入操作人员"></Input>
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
        </div>
      </div>
    </Form>
  </div>
</template>

<script>
  import Format from '@/utils/format'

  export default {
  data () {
    return {
      searchForm: {
        logType: '',
        logTime: '',
        realName:''
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
      let param = Format.formatFormData(this.searchForm)
      // TODO: 其他特殊的格式化
      this.$emit('doSearch', param)
    },

    setSearchBtnFoucs () {
      this.$refs.searchBtn.$el.focus()
    },

    doReset () {
      this.$refs.searchForm.resetFields()
      this.doSearch()
    },
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
  }


</style>
