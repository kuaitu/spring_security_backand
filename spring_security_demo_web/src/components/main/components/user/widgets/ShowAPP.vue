<template>
  <div>
    <Modal v-model="show"
           @on-cancel="cancel"
           title="下载APP"
           width="300"
           class-name="vertical-center-modal">
      <div>
        <img style="width:150px;height:150px;margin-left:60px"
             src="../../../../../assets/imgs/system/APP.png"
             alt="">
      </div>
      <div slot="footer">
        <div style="text-align:left">仅提供安卓版本的下载</div>
        <div style="text-align:left">请用手机浏览器软件扫描二维码下载</div>
      </div>
    </Modal>
  </div>
</template>
<script>
  import UserService from '@/service/UserService'
  import userSetApi from '@/api/user/user'
  import * as R from '@/router/router-types'

  export default {
  props: {
    value: {
      type: Boolean,
      default: false
    }
  },
  data () {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else if (value.length > 16 || value.length < 5) {
        callback(new Error('密码长度为5到16之间'))
      } else {
        if (this.pwdModel.newPassword !== '') {
          this.$refs.pwsForm.validateField('newPasswordConfirm')
        }
        callback()
      }
    }
    const validatePassCheck = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else if (value.length > 16 || value.length < 5) {
        callback(new Error('密码长度为5到16之间'))
      } else if (value !== this.pwdModel.newPassword) {
        callback(new Error('两次输入密码不一致'))
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
      user: {},
      show: false,
      rules: {
        password: [
          { required: true, message: '旧密码不能为空', trigger: 'blur' },
          { type: 'string', min: 5, max: 11, message: '密码长度不能小于5位和不能大于11位', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '新密码不能为空', trigger: 'blur' },
          { validator: validatePass, trigger: 'blur' }
        ],
        newPasswordConfirm: [
          { required: true, message: '确认密码不能为空', trigger: 'blur' },
          { validator: validatePassCheck, trigger: 'blur' }
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
    this.user = UserService.getUser()
  },
  methods: {
    showModal () {
      this.$refs.pwsForm.resetFields()
      this.show = true
    },
    ok () {
      this.$refs.pwsForm.validate((valid) => {
        if (valid) {
          this.$indicator.open()
          userSetApi.changePwd(this.user.userId, this.pwdModel.password, this.pwdModel.newPassword, this.pwdModel.newPasswordConfirm).then(resp => {
            if (resp.success) {
              this.$Notice.success({ title: '修改密码成功,请重新登录' })
              UserService.localLogout()
              this.$router.replace({ path: R.kLogin })
            } else {
              this.$Message.error(`操作失败 ${resp.message || ''}`)
            }
            this.$indicator.close()
          }).catch(err => {
            this.$Message.error(`操作失败 ${err.message || ''}`)
            this.$indicator.close()
          })
        } else {
          this.$Message.error('表单验证失败!')
        }
      })
    },
    cancel () {
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
</style>
