<template>
  <Card>
    <search-form @doSearch="doSearch"
                 @doAdd="doAdd"
                class="search-form"
                ref="searchForm">
    </search-form>
    <data-table :data="data"
                :total="total"
                :currentPage="currentPage"
                @changeRows="changeRows"
                @pageChange="pageChange"
                @view="view"
                class="connon-manager-table">
    </data-table>
    <single-modal ref="singleModal"></single-modal>
  </Card>
</template>
<script>
  import targetApi from '@/api/business/credit/lost'
  import DataTable from './Table'
  import SearchForm from './SearchForm'
  import SingleModal from './Modal'

  export default {
  name: 'MachineManager',
  beforeRouteEnter (to, from, next) {
    next(vm => {
      vm.loadData()
    })
  },

  data () {
    return {
      params: {},
      data: [],
      total: 1,
      currentPage: 1,
      pageSize: 10
    }
  },

  methods: {
    loadData () {
      this.$indicator.open()
      targetApi.search({
        exampleJson: JSON.stringify(this.params),
        page: this.currentPage,
        rows: this.pageSize
      }).then(resp => {
        if (resp.success) {
          this.total = resp.total
          this.data = resp.data
        } else {
          this.$Message.error('加载数据失败' + resp.message)
          this.$indicator.close()
        }
        this.$indicator.close()
      }).catch(err => {
        this.$Notice.error({ title: '加载数据失败', desc: err })
        this.$indicator.close()
      })
    },
    pageChange (page) {
      this.currentPage = page
      this.loadData()
    },

    changeRows (num) {
      this.pageSize = num
      this.loadData()
    },
    doSearch (params) {
      this.currentPage = 1
      this.params = params
      this.loadData()
    },
    view (data) {
      this.$router.push({
        name: 'ForeignTraderView', // 更换对应的详情界面
        params: {
          id: data.memberId
        }
      })
    },
    doAdd () {
      this.$refs.singleModal.initData()
    }
  },

  components: {
    SearchForm, DataTable, SingleModal
  }
}
</script>

<style scoped>
</style>
