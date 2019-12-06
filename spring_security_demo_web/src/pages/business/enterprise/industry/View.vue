<template>
  <div style="height: 100%">
    <Card>
      <Row class="view-data-form">
        <Col span="2">
          <div class="view-label">企业名称：</div>
        </Col>
        <Col span="10">
          <div class="view-value">福建天础信息科技有限公司{{targetId}}</div>
        </Col>
        <Col span="3">
          <div class="view-label">统一社会信用代码：</div>
        </Col>
        <Col span="9">
          <div class="view-value">G198726373123132132{{targetId}}</div>
        </Col>
      </Row>
      <Divider class="horizontal-margin"/>
      <Row class="row-data">
        <Col span="2" >
          <div class="view-label">经营范围：</div>
        </Col>
        <Col span="22">
          <div class="view-value">统一社会信用代码统一社会信用代码统一社会信用代码统一社会信用代码统一社会信用代码统一社会信用代码统一社会信用代码统一社会信用代码统一社会信用代码统一社会信用代码统一社会信用代码统一社会信用代码统一社会信用代码统一社会信用代码统一社会信用代码统一社会信用代码统一社会信用代码统一社会信用代码{{targetId}}</div>
        </Col>
      </Row>
    </Card>
  </div>
</template>

<script>
// import OrgMemberInfo from './widgets/OrgMemberInfo'

export default {
  components: {},
  props: [],
  data () {
    return {
      value: '',
      type: '',
      targetId: 0
    }
  },
  methods: {
    initData () {
      this.value = 'memberInfo'
      // this.$refs['orgMemberInfo'].initData(this.targetId)
      // this.$refs['auditManagerTable'].loadData(this.memberId)
    }
  },
  beforeRouteEnter (to, from, next) {
    next(vm => {
      vm.$nextTick(() => {
        vm.targetId = vm.$route.params.id
        vm.initData()
      })
    })
  }
}
</script>
