<template>
  <div>
    <Row class="toolbar">
      <Col style="float:right;margin-bottom:5px;">
      <Button @click="add"
              type="primary"
              icon="md-add"
              size="large">添加</Button>
      </Col>
    </Row>
    <manager-table :data="data"
                   @edit="edit">
    </manager-table>
    <Modal v-model="show"
           :mask-closable="false">
      <p slot="header">
        <span>添加权限</span>
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
          <span>{{dataModel.menuId}}</span>
        </FormItem>
        <FormItem class="form-item"
                  label="权限名称："
                  prop="jurisdictionName">
          <Input v-model="dataModel.jurisdictionName"
                 clearable></Input>
        </FormItem>
        <FormItem class="form-item"
                  label="权限Item："
                  prop="itemId">
          <Input v-model="dataModel.itemId"
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
      menuId: null,
      show: false,
      type: '',
      dataModel: {},
      modelTitle: '',
      rules: { // 更换规则
        jurisdictionName: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { type: 'string', max: 32, message: '不能大于32', trigger: 'blur' }
        ],
        itemId: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { type: 'string', max: 32, message: '不能大于32', trigger: 'blur' }
        ]
      }
    }
  },

  mounted () {
    this.loadTableList()
  },

  methods: {
    loadTableList () {
      this.$indicator.open()
      menuApi.findFuncByMenuId({menuId: this.$route.params.id}).then(resp => {
        if (resp.success) {
          this.data = resp.data
          this.menuId = this.$route.params.id
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
      this.modelTitle = '新增权限'
      this.dataModel = { // 更换表单内容
        jurisdictionId: null,
        menuId: this.menuId,
        jurisdictionName: '',
        jurisdictionUrl: '',
        itemId: ''
      }
      this.show = true
    },
    edit (jurisdictionId) {
      this.type = 'edit'
      this.modelTitle = '修改权限'
      this.$indicator.open()
      menuApi.findFuncById({id: jurisdictionId}).then(resp => {
        if (resp.success) {
          this.dataModel = resp.data
          this.show = true
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
    onSave (data) {
      this.$indicator.open()
      menuApi.createFunc(data).then(resp => {
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
        // this.$Message.error(`添加失败：${err.message || ''}`)
        this.$Modal.error({
          title: '添加失败',
          content: err
        })
        this.$indicator.close()
      })
    },
    onUpdate (data) {
      this.$indicator.open()
      menuApi.updateFunc(data).then(resp => {
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
