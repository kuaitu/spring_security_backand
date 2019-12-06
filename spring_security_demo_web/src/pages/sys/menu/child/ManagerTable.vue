<template>
  <div>
    <Table ref="dragable"
           :data="data"
           border
           :columns="columns"
           stripe
           highlight-row>
      <template slot-scope="{ row }"
                slot="menuIcon">
        <Icon :type="`${row.menuIcon}`"
              size='24' />
      </template>
      <template slot-scope="{ row, index }"
                slot="action">
        <span class="table_action_btn"
              @click="func(row, index)">权限</span>
        <span class="table_action_btn"
              @click="edit(row, index)">修改</span>
      </template>
    </Table>
  </div>
</template>
<script>
  import Sortable from 'sortablejs'

  export default {
  name: 'DragableTable',
  props: ['data'],
  data () {
    return { // 更换表单内容
      columns: [{
        type: 'index',
        title: '序号',
        width: 120,
        align: 'center'
      }, {
        title: '菜单图标',
        key: 'menuIcon',
        align: 'center',
        slot: 'menuIcon'
      }, {
        title: '菜单名称',
        key: 'menuName',
        align: 'center'
      }, {
        title: 'url',
        key: 'url',
        align: 'center'
      }, {
        title: '排序',
        key: 'sortNumber',
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
    startFunc (e) {
      this.$emit('on-start', e.oldIndex)
    },
    endFunc (e) {
      let movedRow = this.data[e.oldIndex]
      this.data.splice(e.oldIndex, 1)
      this.data.splice(e.newIndex, 0, movedRow)
      this.$emit('on-end', {
        value: this.data,
        from: e.oldIndex,
        to: e.newIndex
      })
    },
    chooseFunc (e) {
      this.$emit('on-choose', e.oldIndex)
    },
    func (target, index) {
      this.$emit('func', target.menuId)
    },
    edit (target, index) {
      this.$emit('edit', target.menuId)
    }
  },
  mounted () {
    var el = this.$refs.dragable.$children[1].$el.children[1]
    let vm = this
    Sortable.create(el, {
      onStart: vm.startFunc,
      onEnd: vm.endFunc,
      onChoose: vm.chooseFunc
    })
  }
}
</script>
