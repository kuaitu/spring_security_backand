<template>
  <div>
    <Table :data="data" border :columns="tableColumns" :row-class-name="rowClass" >
      <template slot-scope="{ row }" slot="logType">
        {{ translate('LEVEL_LOG', row.logType) }}
      </template>
      <template slot-scope="{ row }"  slot="logTime" v-if="row.logTime && row.logTime != ''">
        {{ row.logTime.slice(0, 10) }}
      </template>
      <template slot-scope="{ row }" slot="action">
        <div class="action-row">
          <div class="action-btn" @click="view(row)" v-if="hasView">详情</div>
        </div>
      </template>
    </Table>
    <div v-if="total > 0" style="margin: 10px; overflow: hidden;">
      <div style="float: right;">
        <Page :total="total"
              :current="currentPage"
              show-elevator
              show-total
              show-sizer
              :page-size-opts="pageNumArr"
              @on-page-size-change="pageNum"
              @on-change="pageChange"
              placement="top">
        </Page>
      </div>
    </div>
  </div>
</template>
<script>
  import sysDictService from '@/service/SysDict'
  import userService from '@/service/UserService'

  export default {
  props: ['data', 'total', 'currentPage'],
  data () {
    return {
      tableColumns: [{
        key: 'index',
        type: 'index',
        title: '序号',
        align: 'center',
        width: 80
      }, {
        title: '操作内容',
        align: 'center',
        minWidth: 120,
        key: 'logContent'
      }, {
        title: '日志类型',
        align: 'center',
        minWidth: 150,
        key: 'logType',
        slot: 'logType'
      }, {
        title: '操作人员',
        align: 'center',
        width: 180,
        key: 'realName'
      }, {
        title: '操作时间',
        align: 'center',
        width: 180,
        key: 'logTime',
        slot: 'logTime'
      }],
      pageNumArr: [10, 20, 50, 100]
    }
  },
  components: {},
  computed: {
    hasView () {
      userService.hasFunc('member_view')
      return true
    }
  },
  methods: {
    rowClass (row, index) {
      if (index % 2 === 0) {
        return 'table-double-row'
      } else {
        return 'table-single-row'
      }
    },
    setTableColumns (columns) {
      this.tableColumns = columns
    },
    pageChange (page) {
      this.$emit('pageChange', page)
    },
    pageNum (num) {
      this.$emit('changeRows', num)
    },
    translate (dictCode, dictItemCode) {
      return sysDictService.translate(dictCode, dictItemCode)
    },
    view (target) {
      this.$emit('view', target)
    },
    workStatusColor (target) {
      let workResult = sysDictService.translate('WORK_STATUS', target.workStatus) === '在职'
      if (workResult) {
        return 'success'
      } else {
        return 'error'
      }
    }
  }
}
</script>

<style lang="less" scoped>
  .action-row {
    display: flex;
    justify-content: center;
    justify-items: center;
  }
  .action-btn {
    font-size:9px;
    font-family:Microsoft YaHei;
    font-weight:bold;
    color:rgba(255,142,2,1);
  }
</style>
