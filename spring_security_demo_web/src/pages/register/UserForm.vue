<template>
  <div>
    <Form ref="dataForm"
          class="view-form"
          inline
          :model="dataModel"
          :rules="rules"
          :label-width="140">
      <Row>
        <Col :span="8">
        <FormItem label="用户名："
                  prop="userName">
          <Input v-model.trim="dataModel.userName"
                 placeholder="请输入用户名"
                 style="width:180px"></Input>
        </FormItem>
        </Col>
        <Col :span="8">
        <FormItem label="密码："
                  prop="userPwd">
          <Input type='password'
                 v-model="dataModel.userPwd"
                 placeholder="请输入密码"
                 style="width:180px"></Input>
        </FormItem>
        </Col>
        <Col :span="8">
        <FormItem label="确认密码："
                  prop="confirmUserPwd">
          <Input type='password'
                 v-model="dataModel.confirmUserPwd"
                 placeholder="请输入确认密码"
                 style="width:180px"></Input>
        </FormItem>
        </Col>
      </Row>
      <Row>
        <Col :span="8">
        <FormItem label="真实姓名："
                  prop="realName">
          <Input v-model.trim="dataModel.realName"
                 placeholder="请输入真实姓名"
                 style="width:180px"></Input>
        </FormItem>
        </Col>
        <Col :span="8">
        <FormItem label="手机号码："
                  prop="phone">
          <Input v-model.trim="dataModel.phone"
                 placeholder="请输入手机号码"
                 style="width:180px"></Input>
        </FormItem>
        </Col>
        <Col :span="8">
        <FormItem label="电子邮箱："
                  prop="email">
          <Input v-model.trim="dataModel.email"
                 placeholder="请输入电子邮箱"
                 style="width:180px"></Input>
        </FormItem><br>
        </Col>
      </Row>
    </Form>
    <div style="text-align: center;">
      <Button class="next"
              @click="next"
              type="primary"
              size="large">下一步</Button>
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  props: {
    dataModel: {
      type: Object,
      default: () => {
        return {
          userName: '',
          realName: '',
          userPwd: '',
          confirmUserPwd: '',
          phone: '',
          email: '',
          isAdmin: '0',
          uwRole: []
        }
      }
    }
  },
  data () {
    const valiidatePhone = (rule, value, callback) => {
      let phone = /1\d{10}$/
      if (phone.test(value)) {
        callback()
      } else {
        callback(new Error('手机格式不合法'))
      }
    }
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
      if (value !== this.dataModel.userPwd) {
        callback(new Error('两次输入密码不一致'))
      } else if (value.length > 16 || value.length < 6) {
        callback(new Error('密码长度为6到16之间'))
      } else {
        callback()
      }
    }
    return {
      rules: {
        userName: [
          { required: true, message: '用户名不能为空', trigger: 'blur' },
          { type: 'string', max: 32, message: '用户名长度不能大于32', trigger: 'change, blur' }
        ],
        realName: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { type: 'string', max: 16, message: '真实姓名不能大于16位', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { validator: valiidatePhone, trigger: 'blur' }
        ],
        email: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { type: 'email', message: '邮箱格式不正确', trigger: 'blur' },
          { type: 'string', max: 64, message: '邮箱长度不能超过64', trigger: 'change, blur' }
        ],
        userPwd: [
          { required: true, validator: validatePass, trigger: 'blur' }
        ],
        confirmUserPwd: [
          { required: true, validator: validatePassCheck, trigger: 'blur' }
        ]
      }
    }
  },
  watch: {},
  computed: {},
  methods: {
    next () {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          this.$emit('next', 'user')
        } else {
          this.$Message.error('表单验证失败!')
        }
      })
    }
  },
  created () {},
  mounted () {}
}
</script>
<style lang="less" scoped>
.next {
  text-align: center;
  width: 20%;
}
</style>
