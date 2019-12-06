<template>
  <div style="height:100%">
    <div class="c_body">
      <place-form-modal ref="dataForm"
                        :type="EDIT"
                        :dataModel="dataModel"></place-form-modal>
      <div class="center_position">
        <Button class="next"
                @click="goBack"
                type="primary"
                size="large">上一步</Button>
        <Button class="next"
                @click="next"
                type="primary"
                size="large">提交注册</Button>
      </div>
    </div>
  </div>
</template>
<script>
  import PlaceFormModal from '@/pages/checkcorp/widgets/CheckCorpFormModal'

  export default {
  components: {PlaceFormModal},
  props: {
    dataModel: {
      type: Object,
      default: () => {
        return {
          pcorpcode: '-1',
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
  watch: {},
  computed: {},
  methods: {
    goBack () {
      this.$emit('goBack')
    },
    next () {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          this.$emit('next', 'corp')
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
  height: calc(~"100vh - 100px");
  -moz-height: calc(~"100vh - 200px");
  padding: 10px 10px 80px 10px;
  overflow: scroll;
  overflow-x: auto;
  overflow-x: 1px;
}
.next {
  // text-align: center;
  width: 20%;
}
.text-left {
  text-align: left;
}
</style>
