<template>
  <div>
    <Table :loading="loading"
           :height="height"
           :width="width"
           :ref="refs"
           :columns="columnsList"
           :data="thisTableData"
           border
           disabled-hover
           stripe></Table>
  </div>
</template>

<script>
/* eslint-disable */
const editButton = (vm, h, currentRow, index) => {
  return h(
    'Button',
    {
      props: {
        type: currentRow.editting ? 'success' : 'primary',
        loading: currentRow.saving
      },
      style: {
        margin: '0 5px',
      },
      on: {
        click: () => {
          if (!currentRow.editting) {
            if (currentRow.edittingCell) {
              for (let name in currentRow.edittingCell) {
                currentRow.edittingCell[name] = false
                vm.thisTableData[index].edittingCell[name] = false
              }
            }
            vm.thisTableData[index].editting = true
            // vm.thisTableData = JSON.parse(JSON.stringify(vm.thisTableData))
          } else {
            vm.thisTableData[index].saving = true
            // vm.thisTableData = JSON.parse(JSON.stringify(vm.thisTableData))
            let edittingRow = vm.thisTableData[index]
            edittingRow.editting = false
            edittingRow.saving = false
            // vm.thisTableData = JSON.parse(JSON.stringify(vm.thisTableData))
            vm.$emit('input', vm.handleBackdata(vm.thisTableData))
            vm.$emit('on-change', vm.handleBackdata(vm.thisTableData), index)
          }
        }
      }
    },
    currentRow.editting ? '保存' : '编辑'
  )
}

const deleteButton = (vm, h, currentRow, index, ifDisabled) => {
  return h(
    'Button',
    {
      style: {
        margin: '0 5px'
      },
      on: {
        click: () => {
          vm.$Modal.confirm({
            title: '删除确认',
            content: `确认要删除吗?`,
            onOk: () => {
              vm.thisTableData.splice(index, 1)
              vm.$emit('input', vm.handleBackdata(vm.thisTableData))
              vm.$emit('on-delete', vm.handleBackdata(vm.thisTableData), index)
            }
          })
        }
      }
    },
    '删除'
  )
}
const incellEditBtn = (vm, h, param) => {
  if (vm.hoverShow) {
    return h(
      'div',
      {
        class: {
          'show-edit-btn': vm.hoverShow
        }
      },
      [
        h('Button', {
          props: {
            type: 'text',
            icon: 'edit'
          },
          on: {
            click: event => {
              vm.thisTableData[param.index].edittingCell[param.column.key] = true
              // vm.thisTableData = JSON.parse(JSON.stringify(vm.thisTableData))
            }
          }
        })
      ]
    )
  } else {
    return h('Button', {
      props: {
        type: 'text',
        icon: 'edit'
      },
      on: {
        click: event => {
          vm.thisTableData[param.index].edittingCell[param.column.key] = true
          // vm.thisTableData = JSON.parse(JSON.stringify(vm.thisTableData))
        }
      }
    })
  }
}
const saveIncellEditBtn = (vm, h, param) => {
  return h('Button', {
    props: {
      type: 'text',
      icon: 'checkmark'
    },
    on: {
      click: event => {
        vm.thisTableData[param.index].edittingCell[param.column.key] = false
        // vm.thisTableData = JSON.parse(JSON.stringify(vm.thisTableData))
        vm.$emit('input', vm.handleBackdata(vm.thisTableData))
        vm.$emit('on-cell-change', vm.handleBackdata(vm.thisTableData), param.index, param.column.key, param)
      }
    }
  })
}

// 生产input编辑框

const foundInput = (vm, h, param, item, ifDisabled) => {
  return h('Input', 
            {
             style: {
              　width: item.width*0.8+'px'              　　　　　　　　
              },
              props: {
                maxlength:40,
                value: vm.thisTableData[param.index][item.key]
              },
              on: {
                'on-change'(event) {
                  vm.thisTableData[param.index][item.key] = event.target.value
                },
                // 失去焦点时保存内容
                'on-blur'(event) {
                  // vm.thisTableData[param.index].edittingCell[param.column.key] = false
                  // vm.thisTableData = JSON.parse(JSON.stringify(vm.thisTableData))
                  vm.$emit('input', vm.handleBackdata(vm.thisTableData))
                  vm.$emit('on-cell-change', vm.handleBackdata(vm.thisTableData), param.index, param.column.key, param)
                }
              }
            }
          )
}

//编辑框
const cellInput = (vm, h, param, item, ifDisabled) => {
  if (item.tooltip) {
    return h('Tooltip',{
              props: {
                content: vm.thisTableData[param.index][item.key],
                maxWidth: 200,
                transfer: true
              }
            },
            [
              foundInput(vm, h, param, item, ifDisabled)              
            ]
          )
  } else {
    return foundInput(vm, h, param, item, ifDisabled)
  }
}
//选择框
const cellSelect = (vm, h, param, item, ifDisabled) => {
  let selectvalue = item.value
  let selectlabel = item.label
  return h(
    'Select',
    {
      props: {
        value: vm.thisTableData[param.index][item.key],
        'label-in-value':true,
        transfer: true,
        'disabled': ifDisabled
      },
      on: {
        'on-change'(data) {
          let key = item.key
          let labelKey = item.labelKey
          if(data) {
            vm.thisTableData[param.index][key] = data.value
            vm.thisTableData[param.index][labelKey] = data.label
            // vm.thisTableData = JSON.parse(JSON.stringify(vm.thisTableData))
            vm.$emit('input', vm.handleBackdata(vm.thisTableData))
            vm.$emit('on-cell-change', vm.handleBackdata(vm.thisTableData), param.index, param.column.key, param)
          }
        }
        //失去焦点时保存内容
        // 'on-blur' (event){
        //     vm.thisTableData[param.index].edittingCell[param.column.key] = false;
        //     vm.thisTableData = JSON.parse(JSON.stringify(vm.thisTableData));
        //     vm.$emit('input', vm.handleBackdata(vm.thisTableData));
        //     vm.$emit('on-cell-change', vm.handleBackdata(vm.thisTableData), param.index, param.column.key);
        // }
      }
    },param.column.arrayitem.map(function(type){
      return h('Option',{
        props:{
          value:type[selectvalue]
        }
      },type[selectlabel])
    })
  )
}
const cellSpan = (vm, h, param, item) => {
  return h(
    'span',
    {
      on: {
        click() {
          vm.thisTableData[param.index].edittingCell[param.column.key] = true
          vm.thisTableData = JSON.parse(JSON.stringify(vm.thisTableData))
        }
      }
    },
    item
  )
}
export default {
  name: 'canEditTable',
  props: {
    refs: String,
    columnsList: Array,
    value: Array,
    url: String,
    editIncell: {
      type: Boolean,
      default: false
    },
    hoverShow: {
      type: Boolean,
      default: false
    },
    width: Number,
    height: Number,
    loading: Boolean,
    disabled: Boolean
  },
  data() {
    return {
      columns: [],
      thisTableData: [],
      thisTableData: []
    }
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      let vm = this
      let editableCell = this.columnsList.filter(item => {
        if (item.editable) {
          if (item.editable === true) {
            return item
          }
        }
      })
      // let cloneData = JSON.parse(JSON.stringify(this.value))
      let res = []
      if (this.value) {
        res = this.value.map((item, index) => {
          let isEditting = false
          if (this.thisTableData[index]) {
            if (this.thisTableData[index].editting) {
              isEditting = true
            } else {
              for (const cell in this.thisTableData[index].edittingCell) {
                if (this.thisTableData[index].edittingCell[cell] === true) {
                  isEditting = true
                }
              }
            }
          }
          if (isEditting) {
            return this.thisTableData[index]
          } else {
            this.$set(item, 'editting', false)
            let edittingCell = {}
            editableCell.forEach(item => {
              edittingCell[item.key] = false
            })
            this.$set(item, 'edittingCell', edittingCell)
            return item
          }
        })
      }
      this.thisTableData = res
      // this.thisTableData = JSON.parse(JSON.stringify(this.thisTableData))
      this.columnsList.forEach(item => {
        if (item.editable) {
          item.render = (h, param) => {
            let currentRow = this.thisTableData[param.index]
            if (!currentRow.editting) {
              if (this.editIncell) {
                return h(
                  'Row',
                  {
                    props: {
                      type: 'flex',
                      align: 'middle',
                      justify: 'center'
                    }
                  },
                  [
                    h(
                      'Col',
                      {
                        props: {
                          span: '24'
                        }
                      },
                      [cellInput(this, h, param, item, this.disabled)]
                    )
                    // h(
                    //   'Col',
                    //   {
                    //     props: {
                    //       span: '2'
                    //     }
                    //   },
                    //   [
                    //     currentRow.edittingCell[param.column.key]
                    //       ? saveIncellEditBtn(this, h, param)
                    //       : incellEditBtn(this, h, param)
                    //   ]
                    // )
                  ]
                )
              } else {
                if ( item.tooltip && typeof item.width === 'number') {
                  return h('div', [
              　　　　h('Tooltip', {
              　　　　props: { content: currentRow[item.key] }
              　　　　}, [
              　　　　h('span', {
              　　　　　　style: {
              　　　　　　　　display: 'inline-block',
              　　　　　　　　width: item.width*0.8+'px',
              　　　　　　　　overflow: 'hidden',
              　　　　　　　　textOverflow: 'ellipsis',
              　　　　　　　　whiteSpace: 'nowrap',
                            margin: '11px 0px 0px 0px'
              　　　　　　},
              　　　　　　}, currentRow[item.key]),
              　　　　　　h('span', {
              　　　　　　　　slot: 'content',
              　　　　　　　　style: { whiteSpace: 'normal', wordBreak: 'break-all' }
              　　　　　　}, currentRow[item.key])
              　　　　])  
              　　])
                } else {
                  return h('div', currentRow[item.key])
                }
                
                // return h('Tooltip', {
                //   props: {
                //     content: currentRow[item.key]
                //   }
                // }, currentRow[item.key])
              }
            } else {
              return h('Input', {
                props: {
                  type: 'text',
                  value: currentRow[item.key],
                },
                on: {
                  'on-change'(event) {
                    let key = param.column.key
                    vm.thisTableData[param.index][key] = event.target.value
                  }
                }
              })
            }
          }
        }
        if (item.selectable) {
          item.render = (h, param) => {
            let currentRow = this.thisTableData[param.index]
            if (this.editIncell) {
              return h(
                'Row',
                {
                  props: {
                    type: 'flex',
                    align: 'middle',
                    justify: 'center'
                  }
                },
                [
                  h(
                    'Col',
                    {
                      props: {
                        span: '24'
                      }
                    },
                    [cellSelect(this, h, param, item, this.disabled)]
                  )
                ]
              )
            }else {
              // 在没有labelKey参数的情况下，直接返回key的值
              let params = currentRow[item.labelKey]
              if (item.labelKey === null || item.labelKey === void 0 || item.labelKey === '') {
                params =  currentRow[item.key]

                // 如果key值在数组中能够找到，则返回对应的名称
                if (param.column.arrayitem) {
                const arrayitem = param.column.arrayitem
                  arrayitem.map((arrItem) => {            
                    if (arrItem.dictItemCode.toString() === currentRow[item.key]) {
                      params = arrItem.dictItemValue
                    }
                  })
                }
                
              }
              
              return h('span', params)
            }
          }
        }
        if (item.handle) {
          item.render = (h, param) => {
            let currentRowData = this.thisTableData[param.index]
            let children = []
            children.push(deleteButton(this, h, currentRowData, param.index, this.disabled))
            return h('div', children)
          }
        }
      })
    },
    handleBackdata(data) {
      // let clonedData = JSON.parse(JSON.stringify(data))
      data.forEach(item => {
        delete item.editting
        delete item.edittingCell
        delete item.saving
      })
      return data
    }
  },
  watch: {
    value(data) {
      this.init()
    }
  }
}
</script>

<style lang="less">
@import "./editable-table.less";
</style>