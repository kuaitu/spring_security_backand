<template>
  <Table border
         stripe
         :columns="columns"
         :data="data">
    <template slot-scope="{ row }"
              slot="status">
      <Tag :color="tagColor(row.status)">{{ translate('TODO_STATUS', row.status) }}</Tag>
    </template>
    <template slot-scope="{ row }"
              slot="action">
      <Button size="small"
              @click="openPage(row)">前往处理</Button>
      <Button size="small"
              @click="processed(row)">已处理</Button>
    </template>
  </Table>
</template>

<script>
  import sysDictService from '@/service/SysDict'
  import {ROUTER_NAME} from '../enum'

  export default {
  props: ['data'],

  data () {
    return {
      columns: [
        {
          type: 'index',
          title: '序号',
          width: 80,
          key: 'index',
          align: 'center'
        },
        {
          title: '项目编号',
          align: 'center',
          width: 180,
          key: 'prjNum'
        },
        {
          title: '项目名称',
          align: 'center',
          minWidth: 320,
          tooltip: true,
          key: 'prjName'
        },
        {
          title: '待办内容',
          align: 'center',
          minWidth: 260,
          tooltip: true,
          key: 'todoContent'
        },
        {
          title: '状态',
          align: 'center',
          width: 120,
          key: 'status',
          slot: 'status'
        },
        {
          title: '操作',
          align: 'center',
          width: 240,
          key: 'action',
          slot: 'action'
        }
      ]
      // data: [
      //   {
      //     prjNum: '4102001811300101',
      //     prjName: '开封北大培文学校大礼堂、多功能楼、行政综合楼、体艺楼、1号初中教学楼、2号初中教学楼、1号小学教学楼、2号小学教学楼、食堂及连廊用房、1号宿舍楼',
      //     todoContent: '监督计划待确认',
      //     status: '0',
      //     recordType: 'prj_plan',
      //     prjTypeNum: '02'
      //   },
      //   {
      //     prjNum: '4102001812130102',
      //     prjName: '开封翠庭尚都（悠活城）住宅小区32#、33#住宅楼',
      //     todoContent: '整改通知书待审核',
      //     status: '0',
      //     recordType: 'reform_notify',
      //     prjTypeNum: '01'
      //   },
      //   {
      //     prjNum: '4117231901310103',
      //     prjName: '中铁.美林花园南区',
      //     todoContent: '行政处罚建议书待审批',
      //     status: '0',
      //     recordType: 'punish_notify',
      //     prjTypeNum: '01'
      //   },
      //   {
      //     prjNum: '4100031902280113',
      //     prjName: '郑州航空港区航程正商置业有限公司宇航铭筑3号地',
      //     todoContent: '监督报告待确认',
      //     status: '0',
      //     recordType: 'supervision_report',
      //     prjTypeNum: '01'
      //   },
      //   {
      //     prjNum: '4100031902280113',
      //     prjName: '郑州航空港区航程正商置业有限公司宇航铭筑3号地',
      //     todoContent: '监督报告待确认',
      //     status: '0',
      //     recordType: 'supervision_report',
      //     prjTypeNum: '01'
      //   },
      //   {
      //     prjNum: '4109271903050115',
      //     prjName: '台前建业城19#楼',
      //     todoContent: '投诉待处理，投诉人：张宝刚',
      //     status: '0',
      //     recordType: 'complaint'
      //   }
      // ]
    }
  },

  methods: {
    openPage (row) {
      let name = ROUTER_NAME[row.recordType]
      if (name instanceof Object && row.prjTypeNum && !Array.isArray(name)) {
        name = name[row.prjTypeNum]
      }
      if (name instanceof Array && name.length === 2) {
        this.$router.push({
          name: name[0],
          params: {
            tabName: name[1]
          }
        })
      } else {
        this.$router.push({
          name
        })
      }
    },
    processed (row) {
      this.$emit('processed', row)
    },
    tagColor (status) {
      return status === '1' ? 'success' : 'default'
    },

    translate (dictCode, dictItemCode) {
      return sysDictService.translate(dictCode, dictItemCode)
    }
  }
}
</script>
