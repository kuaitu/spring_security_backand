<template>
  <div>
    <Modal v-model="show"
           @on-cancel="cancel"
           :closable="!force"
           title="修改密码"
           width="500"
           class-name="vertical-center-modal"
           :mask-closable="false">
      <div>
        <div v-if="force">
          <div class="prompt">提示：该用户必须进行修改密码后才能继续操作。</div><br />
          <div class="prompt-reason">出现原因：<br />
            1.该用户是第一次登陆。<br />
            2.该用户已重置密码，但未修改。</div>
        </div>
        <Form ref="pwsForm"
              class="form"
              :model="pwdModel"
              :label-width="110"
              :rules="rules">
          <FormItem label="输入旧密码"
                    prop="password"
                    class="psw-input">
            <Input type="password"
                   v-model="pwdModel.password"
                   placeholder="请输入旧密码"></Input>
          </FormItem>
          <FormItem label="输入新密码"
                    prop="newPassword"
                    class="psw-input">
            <Input type="password"
                   v-model="pwdModel.newPassword"
                   placeholder="请输入新密码"></Input>
          </FormItem>
          <FormItem label="确认新密码"
                    prop="newPasswordConfirm"
                    class="psw-input">
            <Input type="password"
                   v-model="pwdModel.newPasswordConfirm"
                   placeholder="请输入确认新密码"></Input>
          </FormItem>
        </Form>
      </div>
      <div slot="footer">
        <Button type="primary"
                @click="ok">提交</Button>
        <Button @click="cancel"
                v-if="!force">取消</Button>
      </div>
    </Modal>
  </div>
</template>
<script>
  import UserService from '@/service/UserService'
  import targetApi from '@/api/user/login'
  // import * as R from '@/router/router-types'
export default {
  props: {
    value: {
      type: Boolean,
      default: false
    },
    force: {
      type: Boolean,
      default: false
    }
  },
  data () {
    const validatePass = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入密码'))
      } else if (value.length > 16 || value.length < 6) {
        callback(new Error('密码长度为6到16之间'))
      } else {
        callback()
      }
    }
    const validatePassCheck = (rule, value, callback) => {
      if (value !== this.pwdModel.newPassword) {
        callback(new Error('两次输入密码不一致'))
      } else if (value.length > 16 || value.length < 6) {
        callback(new Error('密码长度为6到16之间'))
      } else {
        callback()
      }
    }
    return {
      pwdModel: {
        password: '',
        newPassword: '',
        newPasswordConfirm: ''
      },
      show: false,
      rules: {
        password: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { type: 'string', min: 1, max: 38, message: '密码长度不能小于1位和不能大于38位', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, validator: validatePass, trigger: 'blur' }
        ],
        newPasswordConfirm: [
          { required: true, validator: validatePassCheck, trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    value (val) {
      if (typeof val === 'boolean') {
        this.show = val
      }
    }
  },
  mounted () {
  },
  methods: {
    showModal () {
      this.$refs.pwsForm.resetFields()
      this.show = true
    },
    ok () {
      this.$refs.pwsForm.validate((valid) => {
        this.$Message.destroy()
        if (valid) {
          this.$indicator.open()
          targetApi.changePwd(this.pwdModel.password, this.pwdModel.newPassword, this.pwdModel.newPasswordConfirm, UserService.getUser().userName).then(resp => {
            if (resp.success) {
              // this.$Notice.success({ title: '修改密码成功,请重新登录' })
              // UserService.localLogout()
              // this.$router.replace({ path: R.kLogin })
              this.cancel()
              if (!this.force) {
                this.$Notice.success({ title: '修改密码成功, 下次登录生效' })
              } else {
                this.$Notice.success({ title: '修改密码成功, 等待页面刷新...' })
                setTimeout(() => {
                  this.$router.go(0)
                }, 1500)
              }
            } else {
              this.$Modal.error({
                title: '错误信息',
                content: (`修改失败：${resp.message || ''}`)
              })
            }
            this.$indicator.close()
          }).catch(err => {
            this.$Modal.error({
              title: '错误信息',
              content: (`修改失败：${err || ''}`)
            })
            this.$indicator.close()
          })
        } else {
          this.$Message.error('表单验证失败!')
        }
      })
    },
    cancel () {
      this.pwdModel = {
        password: '',
        newPassword: '',
        newPasswordConfirm: ''
      }
      this.show = false
      this.$emit('input', false)
    }
  }
}
</script>
<style lang="less" scoped>
.form {
  margin-top: 30px;
  width: 500px;
}

.form-title {
  margin: 20px 0px;
  font-weight: bold;
}
.updatepwd {
  position: relative;
  float: right;
  right: 150px;
}

.psw-input {
  width: 450px;
}

.prompt {
  font-size: 14px;
  margin-left: 32px;
  color: red;
}

.prompt-reason {
  font-size: 14px;
  margin-left: 32px;
  // color: red;
}
</style>
