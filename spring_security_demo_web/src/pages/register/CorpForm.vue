<template>
  <div class="c_body">
    <Form ref="dataForm"
          class="form"
          inline
          :model="corpModel"
          :rules="rules"
          :label-width="150">
      <Row>
        <Col :span="8">
        <FormItem class="form-item"
                  label="社会统一信用代码："
                  prop="corpCode">
          <Input v-model.trim="corpModel.corpCode"
                 placeholder="请输入社会统一信用代码"
                 style="width:180px"></Input>
        </FormItem>
        </Col>
        <Col :span="8">
        <FormItem class="form-item"
                  label="企业名称："
                  prop="corpName">
          <Input v-model.trim="corpModel.corpName"
                 placeholder="请输入企业名称"
                 style="width:180px"></Input>
        </FormItem>
        </Col>
        <Col :span="8">
        <FormItem class="form-item text-left"
                  label="企业类型："
                  prop="corpType">
          <dict-item-select dictName='CORP_TYPE'
                            style="width:180px"
                            v-model="corpModel.corpType">
          </dict-item-select>
        </FormItem>
        </Col>
      </Row>
      <Row>
        <Col :span="8">
        <FormItem class="form-item"
                  label="营业执照注册号："
                  prop="licenseNum">
          <Input v-model="corpModel.licenseNum"
                 placeholder="请输入营业执照注册号"
                 style="width:180px"></Input>
        </FormItem>
        </Col>
        <Col :span="8">
        <FormItem class="form-item"
                  label="注册地区编码："
                  prop="areaCode">
          <info-config-select v-model="corpModel.aCode"
                              style="width:180px"></info-config-select>
        </FormItem>
        </Col>
        <Col :span="8">
        <FormItem class="form-item"
                  label="注册日期："
                  prop="registerDate">
          <DatePicker type="date"
                      v-model="corpModel.registerDate"
                      placeholder="请选择注册日期"
                      style="width:180px"></DatePicker>
        </FormItem>
        </Col>
      </Row>
      <Row>
        <Col :span="8">
        <FormItem class="form-item"
                  label="企业营业地址："
                  prop="address">
          <Input v-model.trim="corpModel.address"
                 placeholder="请输入企业营业地址"
                 style="width:180px"></Input>
        </FormItem>
        </Col>
        <Col :span="8">
        <FormItem class="form-item"
                  label="邮政编码："
                  prop="zipCode">
          <Input v-model.trim="corpModel.zipCode"
                 placeholder="请输入邮政编码"
                 style="width:180px"></Input>
        </FormItem>
        </Col>
        <Col :span="8">
        <FormItem class="form-item"
                  label="法定代表人姓名："
                  prop="legalMan">
          <Input v-model.trim="corpModel.legalMan"
                 placeholder="请输入企业名称"
                 style="width:180px"></Input>
        </FormItem>
        </Col>
      </Row>
      <Row>
        <Col :span="8">
        <FormItem class="form-item"
                  label="法定代表人职务："
                  prop="legalManDuty">
          <Input v-model="corpModel.legalManDuty"
                 placeholder="请输入法定代表人职务"
                 style="width:180px"></Input>
        </FormItem>
        </Col>
        <Col :span="8">
        <FormItem class="form-item"
                  label="法定代表人职称："
                  prop="legaManProTitle">
          <Input v-model="corpModel.legaManProTitle"
                 placeholder="请输入法定代表人职称"
                 style="width:180px"></Input>
        </FormItem>
        </Col>
        <Col :span="8">
        <FormItem class="form-item text-left"
                  label="法定代表人证件类型："
                  prop="legalManIdcardType">
          <dict-item-select dictName='LEGAL_MAN_ID_CARD_TYPE'
                            style="width:180px"
                            v-model="corpModel.legalManIdcardType">
          </dict-item-select>
        </FormItem>
        </Col>
      </Row>
      <Row>
        <Col :span="8">
        <FormItem class="form-item"
                  label="法定代表人证件号码："
                  prop="legalManIdcardNumber">
          <Input v-model.trim="corpModel.legalManIdcardNumber"
                 placeholder="请输入法定代表人证件号码"
                 style="width:180px"></Input>
        </FormItem>
        </Col>
        <Col :span="8">
        <FormItem class="form-item"
                  label="注册资本(万元)："
                  prop="regCapital">
          <Input v-model.trim="corpModel.regCapital"
                 placeholder="请输入注册资本"
                 style="width:180px"></Input>
        </FormItem>
        </Col>
        <Col :span="8">
        <FormItem class="form-item"
                  label="实收资本(万元)："
                  prop="factRegCapital">
          <Input v-model.trim="corpModel.factRegCapital"
                 placeholder="请输入实收资本"
                 style="width:180px"></Input>
        </FormItem>
        </Col>
      </Row>
      <Row>
        <Col :span="8">
        <FormItem class="form-item text-left"
                  label="资本币种："
                  prop="capitalCurrencyType">
          <dict-item-select dictName='CAPITAL_CURRENCY_TYPE'
                            style="width:180px"
                            v-model="corpModel.capitalCurrencyType">
          </dict-item-select>
        </FormItem>
        </Col>
        <Col :span="8">
        <FormItem class="form-item"
                  label="成立日期："
                  prop="establishDate">
          <DatePicker type="date"
                      v-model="corpModel.establishDate"
                      style="width:180px"
                      placeholder="请选择成立日期"></DatePicker>
        </FormItem>
        </Col>
        <Col :span="8">
        <FormItem class="form-item"
                  label="办公电话："
                  prop="officePhone">
          <Input v-model.trim="corpModel.officePhone"
                 placeholder="请输入办公电话"
                 style="width:180px"></Input>
        </FormItem>
        </Col>
      </Row>
      <Row>
        <Col :span="8">
        <FormItem class="form-item"
                  label="传真号码："
                  prop="faxNumber">
          <Input v-model.trim="corpModel.faxNumber"
                 placeholder="请输入传真号码"
                 style="width:180px"></Input>
        </FormItem>
        </Col>
        <Col :span="8">
        <FormItem class="form-item"
                  label="联系人姓名"
                  prop="linkMan">
          <Input v-model="corpModel.linkMan"
                 placeholder="请输入联系人姓名"
                 style="width:180px"></Input>
        </FormItem>
        </Col>
        <Col :span="8">
        <FormItem class="form-item"
                  label="联系人电话："
                  prop="linkPhone">
          <Input v-model="corpModel.linkPhone"
                 placeholder="请输入联系人电话"
                 style="width:180px"></Input>
        </FormItem>
        </Col>
      </Row>
      <Row>
        <Col :span="8">
        <FormItem class="form-item"
                  label="企业邮箱："
                  prop="email">
          <Input v-model.trim="corpModel.email"
                 placeholder="请输入企业邮箱"
                 style="width:180px"></Input>
        </FormItem>
        </Col>
        <Col :span="8">
        <FormItem class="form-item"
                  label="企业网址："
                  prop="webSite">
          <Input v-model.trim="corpModel.webSite"
                 placeholder="请输入企业网址"
                 style="width:180px"></Input>
        </FormItem>
        </Col>
      </Row>
    </Form>
    <Button class="next"
            @click="goBack"
            type="primary"
            size="large">上一步</Button>
    <Button class="next"
            @click="next"
            type="primary"
            size="large">提交注册</Button>
  </div>
</template>

<script>
  import Util from '@/utils/util'
  // import DictItemSelect from '@/widgets/DictItemSelect'
// import InfoConfigSelect from '@/widgets/InfoConfig'
// import SysDict from '@/service/SysDict'

export default {
  components: {},
  props: [],
  data () {
    const validateIdCardNo = (rule, value, callback) => {
      if (Util.checkIDCard(value)) {
        callback()
      } else {
        callback(new Error('请输入正确的身份证号码'))
      }
    }
    const validateCorpCode = (rule, value, callback) => {
      // 18位校验及大写校验 /^[\da-z]+$/i（i不区分大小写）  /^[0-9a-zA-Z]+$/（数字和字母）
      let patrn = /^[0-9A-Z]+$/
      if (patrn.test(value) === false || value.length !== 18) {
        callback(new Error('不是有效的统一社会信用代码(18位及大写)'))
      } else {
        callback()
      }
    }
    return {
      corpModel: {
        corpCode: '',
        corpName: '',
        corpType: '',
        licenseNum: '',
        areaCode: '',
        zipCode: '',
        legalMan: '',
        legalManDuty: '',
        legaManProTitle: '',
        legalManIdcardType: '1',
        legalManIdcardNumber: '',
        regCapital: '',
        factRegCapital: '',
        capitalCurrencyType: '1',
        registerDate: '',
        establishDate: '',
        officePhone: '',
        faxNumber: '',
        linkMan: '',
        linkPhone: '',
        email: '',
        webSite: '',
        address: '',
        aCode: []
      },
      rules: {
        corpCode: [
          { required: true, message: '统一信用代码不能为空', trigger: 'blur' },
          { validator: validateCorpCode, trigger: 'blur' }
        ],
        corpName: [
          { required: true, message: '企业名称不能为空', trigger: 'blur' }
        ],
        corpType: [
          { required: true, message: '企业类型不能为空', trigger: 'blur' }
        ],
        areaCode: [
          { required: true, message: '注册地区编码不能为空', trigger: 'blur' }
        ],
        address: [
          { required: true, message: '企业营业地址不能为空', trigger: 'blur' }
        ],
        legalManIdcardNumber: [
          { required: true, message: '证件号码不能为空', trigger: 'blur' },
          { validator: validateIdCardNo, trigger: 'blur' }
        ],
        registerDate: [
          { required: true, type: 'date', message: '成立日期不能为空', trigger: 'change' }
        ]
      }
    }
  },
  watch: {},
  computed: {},
  methods: {
    goBack () {
      this.$emit('goBack')
    },
    next () {
      if (this.corpModel.registerDate) {
        this.corpModel.registerDate = new Date(this.corpModel.registerDate)
      }
      if (this.corpModel.establishDate) {
        this.corpModel.establishDate = new Date(this.corpModel.establishDate)
      }
      this.corpModel.areaCode = this.corpModel.aCode[2]
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          if (this.corpModel.registerDate) {
            this.corpModel.registerDate = Util.formatDate(this.corpModel.registerDate, 'yyyy-MM-dd hh:mm:ss')
          }
          if (this.corpModel.establishDate) {
            this.corpModel.establishDate = Util.formatDate(this.corpModel.establishDate, 'yyyy-MM-dd hh:mm:ss')
          }
          this.$emit('next', this.corpModel, 'corp')
        } else {
          this.$Message.error('表单验证失败!')
        }
      })
    }
  },
  created () {
  },
  mounted () {
  }
}
</script>
<style lang="less" scoped>
// ::-webkit-scrollbar {
//   width: 8px;
//   height: 8px;
// }
.c_body {
  height: 450px;
  padding: 10px 10px 80px 10px;
  overflow: scroll;
  overflow-x: auto;
  overflow-x: 1px;
}
.next {
  text-align: center;
  width: 20%;
}
.text-left {
  text-align: left;
}
</style>
