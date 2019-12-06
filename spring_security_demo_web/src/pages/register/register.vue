<template>
  <div class="register">
    <div class="layout-header">
      <div @click="goLogin"
           class="layout-header__btn">
        <Icon type="ios-cog-outline"
              size="large" />
        登录
      </div>
    </div>
    <div class="register__top">
      <Steps :current="current">
        <Step title="开始注册"></Step>
        <Step title="用户信息"></Step>
        <Step title="机构信息"></Step>
        <Step title="注册完成"></Step>
      </Steps>
    </div>
    <div class="register__body">
      <Card style="width:100%;">
        <div class="register__body__mid"
             style="">
          <user-form v-show="current === 1"
                     @next="next"
                     :dataModel="checkCorpRegister.userInfo"></user-form>
          <check-form v-if="current === 2"
                      @next="next"
                      @goBack="goBack"
                      :dataModel="checkCorpRegister.ccCheckCorpInfo"></check-form><br>
          <div style="text-align: center;"
               v-if="this.current === 3">
            <Icon type="md-checkmark-circle"
                  class="iconSuccess"
                  :size="60" />
            <div class="regSuccess">恭喜您！注册完成！</div>
            <Button class="next"
                    @click="registerOk"
                    type="primary"
                    size="large">前往登入</Button>
          </div>
        </div>
      </Card>
    </div>
  </div>
</template>

<script>
  import UserForm from '@/pages/register/UserForm'
  import CorpForm from '@/pages/register/CorpForm'
  import CheckForm from '@/pages/register/CheckCorpForm'
  import SysDict from '@/service/SysDict'
  import corpApi from '@/api/checkcorp/checkcorp'
  import md5 from 'js-md5'
  import * as R from '@/router/router-types'

  export default {
  created () {
    SysDict.load()
  },
  mounted () {
  },
  data () {
    return {
      current: 1,
      checkCorpRegister: {
        userInfo: {},
        ccCheckCorpInfo: {
          pcorpCode: '-1',
          pcorpName: '-1',
          corpCode: '',
          corpName: '',
          businessLicenseNo: '',
          registeredDate: '',
          certendDate: '',
          metrologicalCertificateNo: '',
          contactPhone: '',
          faxNumber: '',
          email: '',
          zipCode: '',
          registeredAddress: '',
          address: '',
          qualityPersonName: '',
          qualityIdcardTypeNum: '',
          qualityPersonIdcard: '',
          qualityGender: '',
          qualityEduLevel: '',
          qualityTechTitle: '',
          qualityCertId: '',
          qualitySpeName: '',
          qualityCheckYear: 0,
          qualityRemark: '',
          techPersonName: '',
          techIdcardTypeNum: '',
          techPersonIdcard: '',
          techGender: '',
          techEduLevel: '',
          techTechTitle: '',
          techCertId: '',
          techSpeName: '',
          techCheckYear: 0,
          techRemark: '',
          remark: '',
          organizationId: '1',
          organizationName: '省厅',
          checkCorpCertList: []
        }
      }
    }
  },
  methods: {
    goLogin () {
      this.$router.push({
        path: R.kLogin
      })
    },
    goBack () {
      this.current = 1
    },
    next (type) {
      if (type === 'user') {
        // 验证用户是否已存在
        corpApi.checkCorpUserIsExist({userName: this.checkCorpRegister.userInfo.userName}).then(resp => {
          if (resp.success) {
            this.current++
          } else {
            this.$Modal.error({
              title: '错误信息',
              content: (resp.message)
            })
          }
          this.$indicator.close()
        }).catch(err => {
          this.$Modal.error({
            title: '错误信息',
            content: (err.message)
          })
        })
      }
      if (type === 'corp') {
        this.$indicator.open()
        let checkCorpRegister = JSON.parse(JSON.stringify(this.checkCorpRegister))
        // 用户信息
        checkCorpRegister.userInfo.userPwd = md5(this.checkCorpRegister.userInfo.userPwd)
        checkCorpRegister.userInfo.confirmUserPwd = md5(this.checkCorpRegister.userInfo.confirmUserPwd)
        // 检测机构日期翻译
        checkCorpRegister.ccCheckCorpInfo.registeredDate = this.formatDate(this.checkCorpRegister.ccCheckCorpInfo.registeredDate, 'yyyy-MM-dd hh:mm:ss')
        checkCorpRegister.ccCheckCorpInfo.certendDate = this.formatDate(this.checkCorpRegister.ccCheckCorpInfo.certendDate, 'yyyy-MM-dd hh:mm:ss')
        // 资质证书中冗余检测机构部分数据
        checkCorpRegister.ccCheckCorpInfo.checkCorpCertList = this.checkCorpRegister.ccCheckCorpInfo.checkCorpCertList.map(item => {
          item.corpName = this.checkCorpRegister.ccCheckCorpInfo.corpCode
          item.businessLicenseNo = this.checkCorpRegister.ccCheckCorpInfo.corpCode
          item.zipCode = this.checkCorpRegister.ccCheckCorpInfo.zipCode
          item.foundedTime = this.formatDate(this.checkCorpRegister.ccCheckCorpInfo.registeredDate)
          item.contactPhone = this.checkCorpRegister.ccCheckCorpInfo.contactPhone
          item.techPersonName = this.checkCorpRegister.ccCheckCorpInfo.techPersonName
          item.techPersonTitle = this.translate('TECH_TITLE', this.checkCorpRegister.ccCheckCorpInfo.techTechTitle)
          item.legalMan = this.checkCorpRegister.ccCheckCorpInfo.legalMan
          item.legalManDuty = this.checkCorpRegister.ccCheckCorpInfo.legalManDuty
          item.legaManProTitle = this.checkCorpRegister.ccCheckCorpInfo.legaManProTitle
          item.address = this.checkCorpRegister.ccCheckCorpInfo.registeredAddress
          return item
        })
        corpApi.register(checkCorpRegister).then(resp => {
          if (resp.success) {
            this.current++
          } else {
            this.$Modal.error({
              title: '错误信息',
              content: (`注册失败:${resp.message}`)
            })
          }
          this.$indicator.close()
        }).catch(err => {
          this.$Modal.error({
            title: '错误信息',
            content: (`注册失败:${err.message}`)
          })
          this.$indicator.close()
        })
      }
    },
    registerOk () {
      this.$router.push({
        path: R.kLogin
      })
    }
  },
  components: {UserForm, CorpForm, CheckForm}
}
</script>

<style lang="less" scoped>
.register {
  // overflow: hidden;
  &__top {
    height: 100px;
    display: flex;
    align-items: center;
    padding-left: 10%;
  }
  &__body {
    display: flex;
    align-items: center;
    justify-content: center;
  }
  &__bottom {
    position: relative;
    height: 100px;
  }
}
.layout-header {
  text-align: right;
  height: 40px;
  background: #3e9fec;
  color: #fff;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
  &__btn {
    color: #fff;
    font-size: 12px;
    display: inline-block;
    padding: 12px 15px;
    height: 100%;
    cursor: pointer;
  }
  &__btn:hover {
    background-color: #64b8fd;
  }
}

.next {
  margin-top: 20px;
  text-align: center;
  width: 20%;
}
.iconSuccess {
  color: #3e9fec;
}
.regSuccess {
  // color: red;
  padding-top: 15px;
  font-size: 30px;
  font-family: "SimHei";
  font-style: italic;
}
</style>
