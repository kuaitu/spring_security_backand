<template>
  <div>
    <Table :data="data" border :columns="tableColumns" :row-class-name="rowClass">
      <template slot-scope="{ row }" slot="roleName">
        <Tag color="success" v-if="row.roleName && row.roleName !== ''"> {{ row.roleName }}</Tag>
        <Tag color="primary" v-else> {{ '未配置' }} </Tag>
      </template>
      <template slot-scope="{ row }" slot="auditStatus">
        <Tag :color="openStatusColor(row)">{{ translate('AUDIT_RESULT', row.auditResult) }}</Tag>
      </template>
      <template slot-scope="{ row }" slot="workStatus" v-if="row.workStatus && row.workStatus !== ''">
        <Tag :color="workStatusColor(row)" style="width:80px"> {{ translate('WORK_STATUS', row.workStatus) }} </Tag>
      </template>
      <template slot-scope="{ row }"  slot="gender"  v-if="row.gender && row.gender !== ''">
        {{ translate('GENDER', row.gender) }}
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
        title: '企业名称',
        align: 'center',
        minWidth: 120,
        key: 'userName'
      }, {
        title: '统一社会信用代码',
        align: 'center',
        minWidth: 150,
        key: 'memberName'
      }, {
        title: '法人代表',
        align: 'center',
        minWidth: 180,
        key: 'roleName',
        slot: 'roleName'
      }, {
        title: '备案表编号',
        align: 'center',
        width: 180,
        key: 'gender',
        slot: 'gender'
      }, {
        title: '操作',
        align: 'center',
        width: 150,
        slot: 'action',
        key: 'action'
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
