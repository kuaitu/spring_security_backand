<template>
  <div v-on:keyup.enter="handleSubmit()"
       class="login fit-parent">
    <div class="login__content">
      <div class="login__content__title">企业征信数据查询系统</div>
      <Avatar :src="userAvator" class="login__content__avatar"/>
      <div class="login__content__desc">登录</div>
      <div class="login__content__form">
        <Form ref="formInline"
              :model="loginForm"
              :rules="ruleInline">
          <FormItem prop="user"
                    class="form-item">
            <Input type="text"
                   prefix="ios-contact"
                   v-model="loginForm.user"
                   placeholder="请输入用户名">
              <!--<Icon type="ios-person-outline"-->
              <!--slot="prepend"></Icon>-->
            </Input>
          </FormItem>

          <FormItem prop="userpwd"
                    class="form-item">
            <Input type="password"
                   prefix="ios-lock"
                   v-model="loginForm.userpwd"
                   placeholder="请输入密码">
              <!--<Icon type=""-->
              <!--slot="prepend"></Icon>-->
            </Input>
          </FormItem>
          <FormItem class="form-item code-item"
                    prop="verificationCode">
            <Input type="text"
                   prefix="ios-mail"
                   v-model="loginForm.verificationCode"
                   placeholder="请输入验证码">
              <!--<Icon type="ios-mail-outline"-->
              <!--slot="prepend"></Icon>-->
            </Input>
            <img class="codeImg"
                 :src="codeUrl"
                 @click="changeCode()" />
          </FormItem>
          <div class="btns-row">
            <a href="javascript:void(0);" @click="forget()" class="btns-row-forget">忘记密码？</a>
            <a href="javascript:void(0);" @click="register()" class="btns-row-register" >我要注册</a>
          </div>
          <FormItem class="submit-content">
            <Button long
                    type="primary"
                    :loading="loading"
                    @click="handleSubmit()"
                    class="submit-content-btn">
              <span v-if="!loading">登录</span>
              <span v-else>登录中...</span>
            </Button>
          </FormItem>
          <!--<div class="login__content__error">{{loginForm.errMsg}}</div>-->
        </Form>
      </div>
    </div>
  </div>
</template>

<script>
  import targetApi from '@/api/user/login'
  import UserService from '@/service/UserService'
  import * as C from '@/utils/constants'
  import * as R from '@/router/router-types'

  const CODE_URL = C.API_HEAD + '/Open/verificationCode.action?'
export default {
  data () {
    return {
      loading: false,
      codeUrl: '',
      loginForm: {
        user: '',
        userpwd: '',
        verificationCode: ''
      },
      ruleInline: {
        user: [
          { required: true, message: '请填写用户名', trigger: 'blur' }
        ],
        userpwd: [
          { required: true, message: '请填写密码', trigger: 'blur' }
        ],
        verificationCode: [
          { required: true, message: '请填图片验证码', trigger: 'blur' }
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
  computed: {
    userAvator () {
      return require('@/assets/imgs/system/default_avatar.png')
    }
  },
  methods: {
    handleSubmit (name) {
      if (this.loading) {
        return
      }
      this.$refs.formInline.validate((valid) => {
        if (valid) {
          this.loading = true
          this.$indicator.open()
          targetApi.login(this.loginForm.user.trim(), this.loginForm.userpwd.trim(), this.loginForm.verificationCode.trim()).then((resp) => {
            this.loading = false
            if (resp.success) {
              UserService.localLogin(resp.data)
              this.$Notice.success({ title: '登录成功' })
              this.$router.replace({ path: R.kIndex })
            } else {
              this.changeCode()
              this.$Message.error('登录失败：' + resp.message + '，请重新输入')
            }
          }).catch(err => {
            this.loading = false
            this.changeCode()
            this.$Message.error('登录失败：' + err)
          }).then(() => { this.$indicator.close() })
        }
      })
    },
    changeCode () {
      this.codeUrl = CODE_URL + Math.random()
    },
    forget () {
      this.$router.replace({path: R.kForgetPassword})
    },
    register () {
      this.$router.replace({path: R.kRegister})
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
    justify-content: center;
    align-content: center;
    align-items: center;
    flex-direction: column;
    &__content {
      width:304px;
      height: 337px;
      &__title {
        position: relative;
        top: 30px;
        text-align: center;
        height:27px;
        font-size:26px;
        font-family:Microsoft YaHei;
        font-weight:bold;
        color:rgba(255,255,255,1);
      }
      &__avatar {
        position: relative;
        left: 123px;
        top: 59px;
        width:58px;
        height:58px;
        border-radius:50%;
      }
      &__desc {
        position: relative;
        left: 40.5px;
        top: 34.5px;
        width:40px;
        height:19px;
        font-size:20px;
        font-family:Adobe Heiti Std;
        font-weight:bold;
        color:rgba(48,83,171,1);
      }
      &__form {
        padding-top: 54px;
        height:269px;
        background:rgba(255,255,255,1);
        border-radius:9px;
      }
    }
  }
  .form-item {
    width:228px;
    height:33px;
    background:rgba(255,255,255,1);
    border-radius:3px;
    margin-left: 40px;
    margin-bottom: 16px;
  }
  .code-item {
    width:159px;
    margin-bottom: 6px;
  }
  .codeImg {
    width:64px;
    height:32px;
    position: absolute;
    top: 0px;
    left: 102%;
  }
  .btns-row {
    display: flex;
    margin-left: 40px;
    justify-content: flex-end;
    width: 227.5px;
    height: 20px;
    &-forget {
      text-decoration : underline;
      width:66px;
      height:9px;
      font-size:9px;
      font-family:Adobe Heiti Std;
      font-weight:normal;
      color:rgba(48,83,171,1);
    }
    &-register {
      text-decoration : underline;
      width:48px;
      height:9px;
      font-size:9px;
      font-family:Adobe Heiti Std;
      font-weight:normal;
      color:rgba(48,83,171,1);
    }

  }
  .submit-content {
    margin-top: 1px;
    text-align: center;
    &-btn {
      width:133px;
      height:30px;
      background:rgba(48,83,171,1);
      box-shadow:0px 3px 6px 0px rgba(48,83,171,0.08);
      border-radius:3px;
    }
  }

</style>
