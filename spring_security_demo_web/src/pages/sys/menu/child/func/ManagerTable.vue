<template>
  <div>
    <Table  :data="data" border :columns="columns" stripe highlight-row>
      <template slot-scope="{ row, index }" slot="action">
        <span class="table_action_btn"  @click="edit(row, index)">修改</span>
      </template>
    </Table>
  </div>
</template>
<script>
export default {
  props: ['data'],
  data () {
    return { // 更换表单内容
      columns: [{
        type: 'index',
        title: '序号',
        width: 120,
        align: 'center'
      }, {
        title: '权限名称',
        key: 'jurisdictionName',
        align: 'center'
      }, {
        title: '权限Item',
        key: 'itemId',
        align: 'center'
      }, {
        title: '操作',
        slot: 'action',
        width: 200,
        align: 'center'
      }]
    }
  },
  methods: {
    edit (target, index) {
      this.$emit('edit', target.jurisdictionId)
    }
  }
}
</script>
