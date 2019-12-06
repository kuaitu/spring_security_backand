<template>
  <div v-on:keyup.enter="handleSubmit()"
       class="login fit-parent">
    <Form class="login__form"
          ref="formInline"
          :model="loginForm"
          :rules="ruleInline">
      <FormItem>
        <h3 class="login__title">企业征信信息系统</h3>
      </FormItem>
      <FormItem prop="user"
                style="width: 300px;margin-left:12%">
        <Input type="text"
               v-model="loginForm.user"
               placeholder="请输入用户名">
        <Icon type="ios-person-outline"
              slot="prepend"></Icon>
        </Input>
      </FormItem>

      <FormItem prop="userpwd"
                style="width: 300px;margin-left:12%">
        <Input type="password"
               v-model="loginForm.userpwd"
               placeholder="请输入密码">
        <Icon type="ios-lock-outline"
              slot="prepend"></Icon>
        </Input>
      </FormItem>
      <FormItem class="code"
                prop="verificationCode"
                style="width: 195px;margin-left:12%">
        <Input type="text"
               v-model="loginForm.verificationCode"
               placeholder="请输入验证码"
               style="width: 195px">
        </Input>
        <img class="codeImg"
             :src="codeUrl"
             @click="changeCode()" />
      </FormItem>
      <div class="login__error">{{loginForm.errMsg}}</div>
      <FormItem>
        <Button long
                type="primary"
                :loading="loading"
                @click="handleSubmit()"
                style="width: 300px">
          <span v-if="!loading">登录</span>
          <span v-else>登录中...</span>
        </Button>
      </FormItem>
    </Form>
  </div>
</template>

<script>
  import targetApi from '@/api/user/login'
  import UserService from '@/service/UserService'
  import * as C from '@/utils/constants'

  const CODE_URL = C.API_HEAD + '/Open/verificationCode.action?'
export default {
  data () {
    return {
      loading: false,
      codeUrl: CODE_URL + Math.random(),
      loginForm: {
        user: '',
        userpwd: '',
        errMsg: '',
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
    if (UserService.hasUserSession()) {
      this.$router.replace({ path: '/' })
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
              this.$router.replace({ path: '/' })
            } else {
              this.changeCode()
              this.loginForm.errMsg = resp.message
              this.$Message.error('登录失败!')
            }
            this.$indicator.close()
          }).catch(err => {
            this.loading = false
            this.changeCode()
            this.loginForm.errMsg = err
            this.$Message.error('登录失败!')
            this.$indicator.close()
          })
        }
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
  position: fixed;
  background-image: url(../../assets/imgs/system/login-bg.png);
  background-repeat: no-repeat;
  background-size: 100% 100%;
  -moz-background-size: 100% 100%;
  text-align: center;

  &__title {
    color: white;
    font-size: 30px;
  }

  &__form {
    width: 390px;
    height: 300px;
    margin: auto;
    margin-top: 15%;
    margin-right: 12%;
    float: right;
  }

  &__error {
    text-align: left;
    color: red;
    margin-bottom: 5px;
  }
}
.code {
  text-align: left;
}
.codeImg {
  position: absolute;
  top: 1px;
  left: 102%;
  // right: 0;
}
</style>
