<template>
  <div class="login fit-parent">
    <div class="login__content">
      <div class="login__content__title">企业征信数据查询系统</div>
      <div class="login__content__form">
        <div class="login__content__desc">找回密码</div>
        <div>
          <Form  ref="formCode" :model="formModel" label-position="right" :label-width="120" :rules="ruleCode">
            <FormItem label="用户名：" class="item form-item-input" prop="userName">
              <Input v-model.trim="formModel.userName" placeholder="请输入需要重置密码的用户名" class="form-item-input"/>
            </FormItem>
            <FormItem label="电子邮箱：" class="item form-item-input" prop="email">
              <Input v-model.trim="formModel.email" placeholder="请输入注册时填入的邮箱" class="form-item-input"/>
            </FormItem>
            <FormItem label="验  证  码：" class="item" prop="verificationCode">
              <Input v-model.trim="formModel.verificationCode" placeholder="请输入验证码" class="form-item-input-code"/>
              <img class="codeImg" :src="codeUrl" @click="changeCode()" />
            </FormItem>
          </Form>
          <Form  ref="formEmailCode" :model="formModel" label-position="right" :label-width="120" :rules="rulePassword">
            <FormItem prop="emailCode" label="邮箱验证码：" class="item">
              <Input v-model.trim="formModel.emailCode" placeholder="请输入邮箱验证码" class="form-item-input-code"/>
              <Button  type="primary" size="small" @click="getEmailCode()" class="codebtn">获取验证码</Button>
            </FormItem>
            <FormItem label="新  密  码：" class="item" prop="userPwd">
              <Input type='password' v-model="formModel.userPwd" placeholder="请输入新密码" class="form-item-input"/>
            </FormItem>
            <FormItem label="确认密码：" class="item" prop="confirmUserPwd">
              <Input type='password' v-model="formModel.confirmUserPwd" placeholder="请输入确认密码" class="form-item-input"/>
            </FormItem>
            <FormItem class="submit-content">
              <Button type="primary"
                      :loading="loading"
                      @click="handleSubmit()"
                      class="submit-content-btn">
                <span v-if="!loading">提交</span>
                <span v-else>提交中...</span>
              </Button>
            </FormItem>
          </Form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import targetApi from '@/api/user/login'
  import UserService from '@/service/UserService'
  import * as C from '@/utils/constants'
  import * as R from '@/router/router-types'

  const CODE_URL = C.API_HEAD + '/User/verificationCode.action?'
export default {
  data () {
    const validatePass = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入新密码'))
      } else if (value.length > 16 || value.length < 6) {
        callback(new Error('新密码长度为6到16之间'))
      } else {
        callback()
      }
    }
    const validatePassCheck = (rule, value, callback) => {
      if (value !== this.formModel.userPwd) {
        callback(new Error('两次输入密码不一致'))
      } else if (value.length > 16 || value.length < 6) {
        callback(new Error('确认密码长度为6到16之间'))
      } else {
        callback()
      }
    }
    return {
      loading: false,
      codeUrl: '',
      formModel: {
        userName:'',
        email: '',
        emailCode: '',
        verificationCode: '',
        userPwd: '',
        confirmUserPwd: ''
      },
      rulePassword: {
        emailCode: [
          { required: true, message: '邮箱验证码不能为空', trigger: 'blur' },
          { type: 'string', max: 6, message: '邮箱验证码不能大于6位', trigger: 'change, blur' }
        ],
        userPwd: [
          { required: true, validator: validatePass, trigger: 'blur' }
        ],
        confirmUserPwd: [
          { required: true, validator: validatePassCheck, trigger: 'blur' }
        ]
      },
      ruleCode: {
        userName: [
          { required: true, message: '用户名不能为空', trigger: 'blur' },
          { type: 'string', max: 32, message: '用户名长度不能大于32', trigger: 'change, blur' }
        ],
        email: [
          { required: true, message: '邮箱不能为空', trigger: 'blur' },
          { type: 'email', message: '邮箱格式不正确', trigger: 'blur' },
          { type: 'string', max: 64, message: '邮箱长度不能超过64位', trigger: 'change, blur' }
        ],
        verificationCode: [
          { required: true, message: '请填写验证码', trigger: 'blur' },
          { type: 'string', max: 4, message: '验证码不能大于4位', trigger: 'change, blur' }
        ]
      }
    }
  },
  mounted () {
    this.changeCode()
    if (UserService.hasUserSession()) {
      this.$router.replace({ path: '/' })
    }
  },
  methods: {
    getEmailCode () {
      this.$refs.formCode.validate((valid) => {
        if (valid) {
          this.$indicator.open()
          targetApi.getEmailCode(this.formModel.userName,this.formModel.email, this.formModel.verificationCode).then((resp) => {
            if (resp.success) {
              this.$Notice.success({ title: '已发送邮箱验证码' })
            } else {
              this.formModel.verificationCode = ''
              this.changeCode()
              this.$Message.error('获取验证码失败：' + resp.message)
            }
          }).catch(err => {
            this.$Message.error('获取邮件验证码失败：' + err)
          }).then(() => { this.$indicator.close() })
        }
      })
    },

    handleSubmit () {
      if (this.loading) {
        return
      }
      this.$refs.formCode.validate((valid) => {
        this.$refs.formEmailCode.validate((valid) => {
          if (valid) {
            this.loading = true
            this.$indicator.open()
            targetApi.backPassword(this.formModel.userName,this.formModel.email, this.formModel.emailCode,this.formModel.userPwd,this.formModel.confirmUserPwd).then((resp) => {
              this.loading = false
              if (resp.success) {
                this.$Notice.success({ title: '更改密码成功' })
                this.$router.replace({ path: R.kIndex })
              } else {
                this.formModel.verificationCode = ''
                this.changeCode()
                this.$Message.error('更改密码失败：' + resp.message)
              }
            }).catch(err => {
              this.loading = false
              this.$Message.error('更改密码失败：' + err)
            }).then(() => { this.$indicator.close() })
          }
        })
      })
    },
    changeCode () {
      this.codeUrl = CODE_URL + Math.random()
    }
  }
}
</script>

<style lang="less" scoped>
  .login {
    background-image: url(../../assets/imgs/system/login-bg.png);
    background-repeat: no-repeat;
    background-size: 100% 100%;
    -moz-background-size: 100% 100%;
    display: flex;
    align-content: center;
    align-items: center;
    flex-direction: column;
    &__content {
      width:760px;
      height:390px;
      justify-content: center;
      align-items: center;
      &__title {
        text-align: center;
        height:27px;
        font-size:26px;
        font-family:Microsoft YaHei;
        font-weight:bold;
        color:rgba(255,255,255,1);
        margin-bottom: 23.5px;
      }
      &__desc {
        /*position: relative;*/
        /*left: -340px;*/
        width: 100%;
        margin-left: 41.5px;
        height: 48px;
        font-size:20px;
        font-family:Adobe Heiti Std;
        font-weight:bold;
        color:rgba(48,83,171,1);
      }
      &__form {
        width:760px;
        height:580px;
        background:rgba(255,255,255,1);
        border-radius:15px;
        display: flex;
        padding-top:10px;
        align-items: center;
        margin-right: 129px;
        flex-direction: column;
      }
    }
  }

  .form-item {
    width:175px;
    background:rgba(255,255,255,1);
    border-radius:3px;
    margin-bottom: 10px !important;
  }
  .code-item {
    width:159px;
    margin-bottom: 6px;
  }
  .codeImg {
    width:80px;
    height:32px;
    position: absolute;
    margin-left: 2px;
    /*margin-left:100%;*/
  }
  .codebtn {
    position: absolute;
    width:80px;
    height:30px;
    margin-left: 2px;
    top:2px;
    background:rgba(48,83,171,1);
    /*margin-left:100%;*/
  }
  .submit-content {
    margin-top: 1px;
    text-align: center;
    &-btn {
      width:133px;
      height:40px;
      font-size:16px;
      margin-right:20px;
      background:rgba(48,83,171,1);
      box-shadow:0px 3px 6px 0px rgba(48,83,171,0.08);
      border-radius:3px;
    }
  }

  .item {
    margin-bottom:20px;
  }
  .form-item-input {
    width: 300px;
  }
  .form-item-input-code {
    width: 220px;
  }
</style>
