<template>
  <div v-on:keyup.enter="handleSubmit()"
       class="login fit-parent">
    <div class="login__content">
      <div class="login__content__title">企业征信数据查询系统</div>
      <div class="login__content__form">
        <Avatar :src="userAvator" class="login__content__avatar"/>
        <div class="login__content__desc">注册</div>
        <Form  ref="formInline"
              :model="formModel"
              label-position="right"
              :label-width="120"
              :rules="ruleInline">
          <FormItem label="用  户  名："  class="item"  prop="userName">
            <Input  v-model.trim="formModel.userName" placeholder="请输入用户名" class="form-item-input"/>
          </FormItem>

          <FormItem prop="realName" label="姓 名：" class="item">
            <Input  v-model.trim="formModel.realName" placeholder="请输入姓名" class="form-item-input"/>
          </FormItem>
          <FormItem label="邮    箱：" class="item" prop="email">
          <Input v-model.trim="formModel.email" placeholder="请输入邮箱" class="form-item-input"/>
        </FormItem>
          <FormItem prop="enterprise" label="企业名称：" class="item">
            <Input v-model.trim="formModel.enterprise" placeholder="请输入企业名称" class="form-item-input"/>
          </FormItem>
          <FormItem label="密  码：" class="item" prop="userPwd">
          <Input type='password' v-model="formModel.userPwd" placeholder="请输入密码" class="form-item-input"/>
        </FormItem>
          <FormItem label="确认密码：" class="item" prop="confirmUserPwd">
          <Input type='password' v-model="formModel.confirmUserPwd" placeholder="请输入确认密码" class="form-item-input"/>
        </FormItem>
          <FormItem label="验  证  码：" class="item" prop="verificationCode">
            <Input v-model.trim="formModel.verificationCode" placeholder="请输入验证码" class="form-item-input-code"/>
            <img class="codeImg" :src="codeUrl" @click="changeCode()" />
          </FormItem>
          <div class="btns-row">
            <a href="javascript:void(0);" @click="login()" class="btns-row-forget">已有账号，去登录</a>
          </div>
          <FormItem class="submit-content">
            <Button long
                    type="primary"
                    :loading="loading"
                    @click="handleSubmit()"
                    class="submit-content-btn">
              <span v-if="!loading">注册</span>
              <span v-else>注册中...</span>
            </Button>
            <Button long
                    type="primary"
                    :loading="loading"
                    @click="doReset()"
                    class="submit-content-btn2">
              <span v-if="!loading">重置</span>
            </Button>
          </FormItem>
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

  const CODE_URL = C.API_HEAD + '/User/verificationCode.action?'
export default {
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
      if (value !== this.formModel.userPwd) {
        callback(new Error('两次输入密码不一致'))
      } else if (value.length > 16 || value.length < 6) {
        callback(new Error('密码长度为6到16之间'))
      } else {
        callback()
      }
    }
    return {
      loading: false,
      codeUrl: '',
      verificationCode: '',
      formModel: {
        userName: '',
        realName: '',
        email: '',
        enterprise: '',
        userPwd: ''
      },
      ruleInline: {
        userName: [
          { required: true, message: '用户名不能为空', trigger: 'blur' },
          { type: 'string', max: 32, message: '用户名长度不能大于32', trigger: 'change, blur' }
        ],
        realName: [
          { required: true, message: '真实姓名不能为空', trigger: 'blur' },
          { type: 'string', max: 16, message: '真实姓名不能大于16位', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '邮箱不能为空', trigger: 'blur' },
          { type: 'email', message: '邮箱格式不正确', trigger: 'blur' },
          { type: 'string', max: 64, message: '邮箱长度不能超过64', trigger: 'change, blur' }
        ],
        enterprise: [
          { required: true, message: '企业名称不能为空', trigger: 'blur' },
          { type: 'string', max: 32, message: '企业名称不能大于64', trigger: 'change, blur' }
        ],
        userPwd: [
          { required: true, validator: validatePass, trigger: 'blur' }
        ],
        confirmUserPwd: [
          { required: true, validator: validatePassCheck, trigger: 'blur' }
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
          targetApi.create({user: this.formModel, verificationCode: this.formModel.verificationCode}).then((resp) => {
            this.loading = false
            if (resp.success) {
              this.$Notice.success({ title: '注册成功' })
              this.$router.replace({ path: R.kIndex })
            } else {
              this.changeCode()
              this.$Message.error('注册失败：' + resp.message + '，请重新输入')
            }
          }).catch(err => {
            this.loading = false
            this.changeCode()
            this.$Message.error('注册失败：' + err)
          }).then(() => { this.$indicator.close() })
        }
      })
    },
    changeCode () {
      this.codeUrl = CODE_URL + Math.random()
    },
    forget () {
    },
    doReset () {
      this.$refs.searchForm.resetFields()
    },
    login () {
      this.$router.replace({path: R.kLogin})
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
      width:760px;
      height:680px;
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
      &__avatar {
        width:58px;
        height:58px;
        border-radius:50%;
        margin-top:20px;
      }
      &__desc {
        position: relative;
        left: -340px;
        top: -84px;
        height:19px;
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
    width:70px;
    height:20px;
    position: absolute;
    top: 6.5px;
    margin-left:10px;
  }
  .btns-row {
    display: flex;
    justify-content: flex-end;
    width: 400px;
    margin-left: 40px;
    height: 20px;
    margin-bottom:10px;
    &-forget {
      text-decoration : underline;
      width:115px;
      height:20px;
      font-size:14px;
      font-family:Adobe Heiti Std;
      font-weight:normal;
      right:0;
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
      height:40px;
      font-size:16px;
      margin-right:20px;
      background:rgba(48,83,171,1);
      box-shadow:0px 3px 6px 0px rgba(48,83,171,0.08);
      border-radius:3px;
    }
    &-btn2 {
      width:133px;
      height:40px;
      font-size:16px;
      margin-right:20px;
      background:rgba(0,0,0,0);
      box-shadow:0px 3px 6px 0px rgba(48,83,171,0.08);
      border-radius:3px;
      color:rgba(48,83,171,1);
    }
  }

  .item {
    margin-bottom:20px;
  }
  .form-item-input {
   /*width: 210px;*/
  }
  .form-item-input-code {
    width: 245px;
  }
</style>
