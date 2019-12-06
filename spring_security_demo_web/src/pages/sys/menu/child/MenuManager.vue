<template>
  <div>
    <Row v-if="hasAdd"
         class="toolbar">
      <Col style="float:right;margin-bottom:5px;">
      <Button @click="add"
              type="primary"
              icon="md-add"
              size="large">新增子菜单</Button>
      </Col>
    </Row>
    <manager-table :data="data"
                   @edit="edit"
                   @func="func"
                   @on-start="handleOnstart1"
                   @on-end="handleOnend1">
    </manager-table>
    <Modal v-model="show"
           :mask-closable="false">
      <p slot="header">
        <span>{{modelTitle}}</span>
      </p>
      <Form ref="dataForm"
            class="form"
            :rules="rules"
            :model="dataModel"
            :label-width="110">
        <!-- 更换表单 -->
        <FormItem class="form-item"
                  style="text-align:left;"
                  label="所属菜单ID：">
          <span>{{dataModel.parentMenuId}}</span>
        </FormItem>
        <FormItem class="form-item"
                  label="菜单图标："
                  prop="menuIcon">
          <Input v-model="dataModel.menuIcon"
                 clearable></Input>
        </FormItem>
        <FormItem class="form-item"
                  label="菜单名称："
                  prop="menuName">
          <Input v-model="dataModel.menuName"
                 clearable></Input>
        </FormItem>
        <FormItem class="form-item"
                  label="菜单URL："
                  prop="url">
          <Input v-model="dataModel.url"
                 clearable></Input>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button @click="submit"
                type="primary">提交</Button>
        <Button @click="close">关闭</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  import ManagerTable from './ManagerTable'
  import menuApi from '@/api/sys/menu' // 更换对应的API接口
  export default {
  data () {
    return {
      data: [],
      menus: [],
      table1: {
        hasDragged: false,
        isDragging: false,
        oldIndex: 0,
        newIndex: 0,
        draggingRecord: []
      },
      show: false,
      type: '',
      dataModel: {},
      modelTitle: '',
      rules: { // 更换规则
        menuName: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { type: 'string', max: 32, message: '不能大于32', trigger: 'blur' }
        ]
      }
    }
  },
  beforeRouteEnter (to, from, next) {
    next(vm => {
      vm.loadTableList()
    })
  },
  methods: {
    loadTableList () {
      this.$indicator.open()
      menuApi.findAllChild({menuId: this.$route.params.menuId}).then(resp => {
        if (resp.success) {
          this.data = resp.data
        } else {
          // this.$Message.error(resp.message)
          this.$Message.error('加载数据失败' + resp.message)
        }
        this.$indicator.close()
      }).catch(err => {
        this.$Notice.error({ title: '加载数据失败', desc: err })
        this.$indicator.close()
      })
    },
    add () {
      this.type = 'add'
      this.dataModel = { // 更换表单内容
        id: null,
        menuName: '',
        parentMenuId: this.$route.params.menuId,
        url: '',
        menuGrade: 1,
        sortNumber: null
      }
      this.modelTitle = '新增子菜单'
      this.show = true
    },
    edit (menuId) {
      this.type = 'edit'
      this.modelTitle = '修改子菜单'
      this.$indicator.open()
      menuApi.findById({menuId: menuId}).then(resp => {
        if (resp.success) {
          this.dataModel = resp.data
          this.show = true
        } else {
          // this.$Message.error(resp.message)
          this.$Message.error('加载数据失败' + resp.message)
        }
        this.$indicator.close()
      }).catch(err => {
        this.$Message.error(`加载数据失败：${err.message || ''}`)
        this.$indicator.close()
      })
    },
    func (id) {
      this.$router.push({
        name: 'MenuManagerFunc', // 更换对应的详情界面
        params: {
          id
        }
      })
    },
    handleOnstart1 (from) {
      this.table1.oldIndex = from
      this.table1.hasDragged = true
      this.table1.isDragging = true
    },
    handleOnend1 (e) {
      this.table1.isDragging = false
      this.table1.draggingRecord.unshift({
        from: e.from + 1,
        to: e.to + 1
      })
      if (e.from - e.to < 0) {
        for (let i = e.from; i <= e.to; i++) {
          this.menus.push(e.value[i])
        }
        this.menus[this.menus.length - 1].sortNumber = this.menus[this.menus.length - 2].sortNumber
        for (let i = 0; i < this.menus.length - 1; i++) {
          this.menus[i].sortNumber--
        }
        menuApi.changeSort(this.menus).then(resp => {
          if (resp.success) {
            this.loadTableList()
            this.menus = []
          } else {
            this.$Message.error(resp.message)
          }
          this.$indicator.close()
        }).catch(err => {
          this.$Notice.error({ title: '加载数据失败', desc: err })
          this.$indicator.close()
        })
      }
      if (e.from - e.to > 0) {
        for (let i = e.to; i <= e.from; i++) {
          this.menus.push(e.value[i])
        }
        this.menus[0].sortNumber = this.menus[1].sortNumber
        for (let i = 1; i < this.menus.length; i++) {
          this.menus[i].sortNumber++
        }
        menuApi.changeSort(this.menus).then(resp => {
          if (resp.success) {
            this.loadTableList()
            this.menus = []
          } else {
            // this.$Message.error(resp.message)
            this.$Modal.error({
              title: '排序失败',
              content: (resp.message ? resp.message : '未知错误')
            })
          }
          this.$indicator.close()
        }).catch(err => {
          // this.$Notice.error({ title: '排序失败', desc: err })
          this.$Modal.error({
            title: '排序失败',
            content: err
          })
          this.$indicator.close()
        })
      }
    },
    onSave (data) {
      this.$indicator.open()
      menuApi.create(data).then(resp => {
        if (resp.success) {
          this.$Notice.success({ title: '操作成功', content: '添加成功' })
          this.loadTableList()
          this.show = false
        } else {
          // this.$Message.error(`添加失败：${resp.message}`)
          this.$Modal.error({
            title: '添加失败',
            content: (resp.message ? resp.message : '未知错误')
          })
        }
        this.$indicator.close()
      }).catch(err => {
        // this.$Message.error(`编辑失败：${err.message || ''}`)
        this.$Modal.error({
          title: '添加失败',
          content: err
        })
        this.$indicator.close()
      })
    },
    onUpdate (data) {
      this.$indicator.open()
      menuApi.update(data).then(resp => {
        if (resp.success) {
          this.$Notice.success({ title: '操作成功', content: '编辑成功' })
          this.loadTableList()
          this.show = false
        } else {
          // this.$Message.error(`编辑失败：${resp.message}`)
          this.$Modal.error({
            title: '编辑失败',
            content: (resp.message ? resp.message : '未知错误')
          })
        }
        this.$indicator.close()
      }).catch(err => {
        // this.$Message.error(`编辑失败：${err.message || ''}`)
        this.$Modal.error({
          title: '编辑失败',
          content: err
        })
        this.$indicator.close()
      })
    },
    submit () {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          if (this.type === 'add') {
            this.onSave(this.dataModel)
          } else {
            this.onUpdate(this.dataModel)
          }
        }
      })
    },
    close () {
      this.$refs['dataForm'].resetFields()
      this.show = false
    }
  },
  computed: {
    hasAdd () {
      return true
    }
  },
  components: {
    ManagerTable
  }
}
</script>
<style lang="less" scoped>
.form {
  text-align: center;
}
.form-item {
  margin: 40px auto;
}
</style>
