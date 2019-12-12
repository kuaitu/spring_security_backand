<template>
  <Modal v-model="isOpen" width="400"  @on-visible-change="modalInit"  :mask-closable="false" :styles="{top: '36px'}">
    <p slot="header">
      <span>{{title}}</span>
    </p>
    <div>
      <Form ref="dataForm"
            :model="dataModel"
            :label-width="150"
            v-if="isForm"
            :rules="rules"
            inline>
        <FormItem label="用户名：" prop="userName">
          <Input v-model.trim="dataModel.userName" placeholder="请输入用户名"
                 class="form-item-input" :disabled="disabledEdit"/>
        </FormItem>
        <FormItem label="真实姓名：" prop="realName">
          <Input v-model.trim="dataModel.realName" placeholder="请输入真实姓名"
                 class="form-item-input" :disabled="disabledEdit"/>
        </FormItem>
        <FormItem label="邮    箱：" class="item" prop="email">
          <Input v-model.trim="dataModel.email" placeholder="请输入邮箱"
                 class="form-item-input"  :disabled="disabledEdit"/>
        </FormItem>
        <FormItem class="form-item" label="企业名称：" prop="enterprise">
          <Input v-model.trim="dataModel.enterprise" placeholder="请输入企业名称"
                 class="form-item-input" :disabled="disabledEdit"></Input>
        </FormItem>
        <FormItem class="form-item" label="角色配置：" prop="roleId" v-if="roleList && roleList.length > 0">
          <Select ref="roleConfig"  class="form-item-input"  v-model="dataModel.roleId" placeholder="请选择角色">
            <Option v-for="item in roleList" :value="item.roleId" :key="item.roleId">{{ item.roleName }}</Option>
          </Select>
        </FormItem>
      </Form>
    </div>
    <div slot="footer">
      <Button @click="submit" class="btn" type="primary">提交</Button>
      <Button @click="cancel" class="btn" style="margin-left: 8px">取消</Button>
    </div>
  </Modal>
</template>

<script>
  import api from '@/api/sys/user'
  // import Util from '@/utils/util'
  import roleApi from '@/api/sys/role'

  export default {
  components: {},
  data () {
    const isEmpty = (rule, value, callback) => {
      if (value === null || value === undefined || value === '') {
        callback(new Error(''))
      } else {
        callback()
      }
    }
    return {
      isOpen: false,
      type: 'EDIT',
      isForm: true,
      title: '',
      roleList: [],
      dataModel: {
        userId: '',
        userName: '',
        realName: '',
        email: '',
        enterprise: '',
        roleId: ''
      },
      rules: {
        userName: [
          {required: true, message: '用户名不能为空！', trigger: 'blur', type: 'String', validator: isEmpty},
          { required: true, max: 36, message: '长度不能大于36位', trigger: 'change' }
        ],
        realName: [
          {required: true, message: '真实姓名不能为空！', trigger: 'blur', type: 'String', validator: isEmpty},
          { required: true, max: 36, message: '长度不能大于36位', trigger: 'change' }
        ],
        enterprise: [
          {required: true, message: '企业名称不能为空！', trigger: 'blur', type: 'String', validator: isEmpty},
          { required: true, max: 20, message: '长度不能大于20位', trigger: 'change' }
        ],
        email: [
          { required: true, message: '邮箱不能为空', trigger: 'blur' },
          { type: 'email', message: '邮箱格式不正确', trigger: 'blur' },
          { type: 'string', max: 64, message: '邮箱长度不能超过64', trigger: 'change, blur' }
        ],
        roleId: [
          {required: true, message: '角色不能为空！', trigger: 'blur', type: 'number'}
        ]
      }
    }
  },
  mounted () {
    this.getRoleList()
  },
  computed: {
    disabledEdit () {
      if (this.type === 'EDITROLE') {
        return true
      } else {
        return false
      }
    }
  },
  methods: {
    cancel () {
      this.isOpen = false
    },
    modalInit (val) {
      this.isForm = false
      setTimeout(() => {
        this.isForm = true
      }, 1)
    },
    submit () {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          this.$indicator.open()
          api.update(this.dataModel).then(resp => {
            if (resp.success) {
              this.isOpen = false
              this.$Notice.success({ title: '更新记录成功' })
              this.$emit('loadData')
            } else {
              this.$Message.error('更新记录失败：' + resp.message)
            }
          }).catch(err => {
            this.$Notice.error({ title: '加载数据失败', desc: err })
          }).then(() => this.$indicator.close())
        } else {
          this.$Message.error('请完善所有信息!')
        }
      })
    },
    initData (type, id) {
      this.type = type
      if (this.type === 'EDIT') {
        this.title = '用户信息编辑'
      } else {
        this.title = '用户角色修改'
      }
      api.findById(id).then(resp => {
        if (resp.success) {
          this.dataModel = resp.data
        } else {
          this.$Message.error('无记录：' + resp.message)
        }
      }).catch(err => {
        this.$Notice.error({ title: '加载数据失败', desc: err })
      })
      this.isOpen = true // 初始化后打开
    },
    getRoleList () {
      this.roleList = []
      roleApi.searchAll().then(resp => {
        if (resp.success) {
          this.roleList = resp.data
        } else {
          this.$Message.error('暂时获取不到角色信息')
        }
      }).catch(err => {
        this.$Notice.error({ title: '获取角色信息失败', desc: err })
      })
    }
  }
}
</script>

<style>
  .label_width {
    width: 420px;
  }
  .form-item-input {
    width: 157px;
  }
</style>
