<template>
  <Drawer title="选择显示字段"
          :closable="false"
          v-model="drawerOpen"
          @on-close="onClose">
    <Checkbox v-model="allChecked"
              class="columnsLabel">
      全选
    </Checkbox>
    <Checkbox-group class="checkbox"
                    v-model="tableCheckedColumns">
      <span v-for="(column, index) of columns"
            :key="column.title">
        <Tooltip :content="column.title"
                 placement="left"
                 :transfer="true"
                 :disabled="column.title.length <= 8">
          <Checkbox :label="column.key"
                    :disabled="column.disabled"
                    class="columnsLabel">{{ column.title }}</Checkbox>
        </Tooltip>
        <span style="float:right">
          <Button shape="circle"
                  icon="md-arrow-up"
                  v-show="index !== 0"
                  @click="up(index)"></Button>
          <Button shape="circle"
                  icon="md-arrow-down"
                  v-show="index !== columns.length - 1"
                  @click="down(index)"></Button>
        </span>

        <br>
      </span>
    </Checkbox-group>
    <div class="demo-drawer-footer">
      <Button style="margin-right: 8px"
              @click="handleReset">取消</Button>
      <Button type="primary"
              @click="changeTableColumns">确定</Button>
    </div>
  </Drawer>
</template>
<script>
  import userService from '@/service/UserService'

  export default {
  props: ['columns', 'tableColumns', 'tableName'],
  data () {
    return {
      drawerOpen: false,
      tableCheckedColumns: [],
      customTableColumns: []
    }
  },
  mounted () {
    this.initColumns()
  },
  computed: {
    allChecked: {
      get () {
        if (this.tableCheckedColumns.length === this.columns.length) {
          return true
        } else {
          return false
        }
      },
      set (value) {
        if (value) {
          this.tableCheckedColumns = this.columns.map(item => {
            return item.key
          })
        } else {
          this.tableCheckedColumns = this.columns.map(item => {
            if (item.disabled) {
              return item.key
            }
          })
        }
      }
    }
  },
  methods: {
    onClose () {
      this.handleReset()
    },
    swapArray (arr, index1, index2) {
      arr[index1] = arr.splice(index2, 1, arr[index1])[0]
      return arr
    },
    up (index) {
      this.swapArray(this.tableCheckedColumns, index, index - 1)
      return this.swapArray(this.columns, index, index - 1)
    },
    down (index) {
      this.swapArray(this.tableCheckedColumns, index, index + 1)
      return this.swapArray(this.columns, index, index + 1)
    },
    closeDraw () {
      this.drawerOpen = false
    },
    openDraw () {
      this.drawerOpen = true
    },
    handleReset () {
      this.tableCheckedColumns = []
      this.tableColumns.forEach((item) => {
        if (item.title && item.title.trim() !== '') {
          this.tableCheckedColumns.push(item.key)
        }
      })
      this.columns.sort((a, b) => a.sort - b.sort)
      this.closeDraw()
    },
    changeTableColumns () {
      // 保存自定义列到cookie中
      userService.setTableColumns(this.tableName + 'Table', {
        tableCheckedColumns: this.tableCheckedColumns,
        columnsSort: this.columns.map(item => { return item.key })
      })
      // let len = this.tableColumns.length
      this.customTableColumns = this.tableColumns
      this.customTableColumns = this.columns.filter(item => {
        return this.tableCheckedColumns.indexOf(item.key) !== -1
      }, [])
      // 方法1：新增空白列填补空白
      this.customTableColumns.push({title: ' ', minWidth: 1})
      // 方法2：扩展最后一列使之自适应
      // this.tableColumns[len - 2].minWidth = this.tableColumns[len-2].width
      // // this.tableColumns[len - 2].align = 'left'
      // delete this.tableColumns[len - 2].width

      // 将customTableColumns重新赋值给tableColumns
      // this.tableColumns = this.customTableColumns
      this.$emit('setTableColumns', this.customTableColumns)
      this.closeDraw()
    },
    initColumns () {
      let obj = userService.getTableColumns(this.tableName + 'Table')
      if (obj && obj.tableCheckedColumns) {
        this.tableCheckedColumns = obj.tableCheckedColumns
      } else {
        // 如果cookie中不存在该值，则自动全选
        this.tableCheckedColumns = this.columns.map(item => {
          return item.key
        })
      }
      if (obj && obj.columnsSort) {
        if (obj.columnsSort.length === this.columns.length) {
          this.columns.forEach(element => {
            if (obj.columnsSort.indexOf(element.key) !== -1) {
              element.sort = obj.columnsSort.indexOf(element.key)
            }
          })
          this.columns.sort((a, b) => a.sort - b.sort)
        }
      } else {
        this.columns.forEach((element, index) => {
          element.sort = index
        })
      }
      this.changeTableColumns()
    },
    renderHeader (h, params) {
      return h('div', [
        h('span', '操作'),
        h('divider', {
          props: {
            type: 'vertical'
          }
        }),
        h('span', {
          style: {
            marginLeft: '4px',
            cursor: 'pointer'
          },
          on: {
            click: () => {
              this.drawerOpen = true
            }
          }
        }, [
          h('span', '配置'),
          h('icon', {
            props: {
              type: 'ios-options-outline',
              size: '20'
            }
          })
        ])
      ])
    }
  }
}
</script>
<style lang="less" scoped>
.checkbox {
  font-size: 28px;
}
.demo-drawer-footer {
  width: 100%;
  position: relative;
  bottom: 0;
  left: 0;
  border-top: 1px solid #e8e8e8;
  padding: 10px 16px;
  text-align: right;
  background: #fff;
}
.hidden {
  display: none;
}
.columnsLabel {
  width: 127px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
