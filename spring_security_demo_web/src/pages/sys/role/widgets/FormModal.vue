<template>
  <div>
    <Modal v-model="isOpen"
           class-name="vertical-center-modal"
           width="700"
           :mask-closable="false">
      <!-- :z-index="2000" -->
      <p slot="header">
        <span v-if="type === 'ADD'">添加角色</span>
        <span v-else>编辑角色</span>
      </p>
      <div>
        <Form ref="roleForm"
              class="form"
              inline
              :model="roleModel"
              :rules="rules"
              :label-width="120">
          <FormItem label="角色名称:"
                    prop="roleName">
            <Input v-model.trim="roleModel.roleName"
                   placeholder="请输入角色名称"
                   :disabled="disabled"
                   style="width:200px;"></Input>
          </FormItem>
          <!-- <FormItem label="系统权限:" prop="systemSets">
            <Select v-model="roleModel.systemSets" multiple>
              <Option v-for="item in systemList" :value="item.subSystemId" :key="item.subSystemId">{{ item.subSystemName }}</Option>
            </Select>
          </FormItem> -->
          <FormItem label="角色描述:"
                    prop="roleDescribe">
            <Input type="textarea"
                   v-model.trim="roleModel.roleDescribe"
                   placeholder="请输入角色描述"
                   :maxlength="255"
                   :disabled="disabled"
                   :autosize="{minRows: 4, maxRows: 4}"
                   style="width:532px">
            </Input>
          </FormItem>
          <FormItem label="状态:"
                    prop="roleStatus">
            <i-switch v-model="roleModel.roleStatusOn"
                      size="large"
                      :disabled="disabled">
              <span slot="enable">开启</span>
              <span slot="disable">关闭</span>
            </i-switch>
          </FormItem>
        </Form>
      </div>
      <div slot="footer">
        <Button @click="submit"
                class="btn"
                type="primary"
                v-if="!disabled">提交</Button>
        <Button @click="cancel"
                class="btn"
                style="margin-left: 8px">取消</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
export default {
  components: { },
  props: [],
  data () {
    const validateRoleName = (rule, value, callback) => {
      if (!/[·！!@#%^&*-+=~`()#￥（——）：；“”‘、，|《。》？、【】[\]]/.test(value)) {
        callback()
      } else {
        callback(new Error('角色名称不予许输入特殊字符!'))
      }
    }
    return {
      data: [],
      type: '',
      isOpen: false,
      roleId: -1,
      disabled: false,
      roleModel: {
        roleId: null,
        roleName: '',
        roleDescribe: '',
        roleStatusOn: true
      },
      rules: {
        roleName: [
          { required: true, message: '角色名称不能为空', trigger: 'blur' },
          { type: 'string', max: 32, message: '角色名称长度不能超过32', trigger: 'change, blur' },
          { validator: validateRoleName, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    openModal (open, type) {
      this.isOpen = open
      this.reSetForm()
      this.disabled = false
      this.type = type
      if (type === 'VIEW') {
        this.disabled = true
      }
    },
    reSetForm () {
      this.$refs.roleForm.resetFields()
    },
    submit () {
      this.isOpen = true
      this.$refs.roleForm.validate((valid) => {
        if (valid) {
          let roleModel = Object.assign({}, this.roleModel)
          if (this.roleModel.roleStatusOn) {
            roleModel.roleStatus = 'enable'
          } else {
            roleModel.roleStatus = 'disable'
          }
          if (this.type === 'ADD') {
            this.$emit('onSave', roleModel)
          } else {
            this.$emit('onUpdate', roleModel)
          }
        } else {
          this.$Message.error('表单验证失败!')
        }
      })
    },

    cancel () {
      this.isOpen = false
    },
    initData (initRole) {
      this.roleModel.roleId = initRole.roleId
      this.roleModel.roleName = initRole.roleName
      this.roleModel.roleDescribe = initRole.roleDescribe
      if (initRole.roleStatus === 'disable') {
        this.roleModel.roleStatusOn = false
      }
    }
  },
  created () {},
  mounted () {
  }
}
</script>
