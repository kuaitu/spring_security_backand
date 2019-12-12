<template>
  <div>
    <search-form @doSearch="doSearch" class="search-form" ref="searchForm">
    </search-form>
    <manager-table :data="data" :total="total" :currentPage="currentPage"  @changeRows="changeRows"
      @pageChange="pageChange"  class="connon-manager-table" @editUser="editUser"
      @openUser="openConfirm" @closeUser="closeConfirm" @resetPwd="resetPwdConfirm" @editUserRole="editUserRole">
    </manager-table>
    <single-modal ref="singleModal" @loadData="loadData"></single-modal>
  </div>
</template>

<script>
  import SearchForm from './SearchForm'
  import ManagerTable from './Table'
  import userApi from '@/api/sys/user'
  import SingleModal from './Modal'

  export default {
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
      userApi.findAll({
        exampleJson: JSON.stringify(this.params),
        page: this.currentPage,
        rows: this.pageSize
      }).then(resp => {
        if (resp.success) {
          this.total = resp.total
          this.data = resp.data
        } else {
          this.$Message.error('加载数据失败')
          this.$indicator.close()
        }
        this.$indicator.close()
      }).catch(err => {
        this.$Notice.error({ title: '加载数据失败', desc: err })
        this.$indicator.close()
      })
    },

    doSearch (params) {
      this.currentPage = 1
      this.params = params
      this.loadData()
    },

    pageChange (page) {
      this.currentPage = page
      this.loadData()
    },

    editUser (id) {
      this.$refs.singleModal.initData('EDIT', id)
    },
    editUserRole (id) {
      this.$refs.singleModal.initData('EDITROLE', id)
    },
    openUser (user) {
      this.$indicator.open()
      userApi.openUser(user).then(resp => {
        if (resp.success) {
          this.$Notice.success({ title: '开启用户成功' })
          this.loadData()
        } else {
          this.$Message.error(`开启用户失败(${resp.message || ''})`)
        }
        this.$indicator.close()
      }).catch(err => {
        this.$Message.error(`开启用户失败(${err.message || ''})`)
        this.$indicator.close()
      })
      this.$refs.cm.cancel()
    },

    closeUser (user) {
      this.$indicator.open()
      userApi.closeUser(user).then(resp => {
        if (resp.success) {
          this.$Notice.success({ title: '关闭用户成功' })
          this.loadData()
        } else {
          this.$Message.error(`关闭用户失败(${resp.message || ''})`)
        }
        this.$indicator.close()
      }).catch(err => {
        this.$Message.error(`关闭用户失败(${err.message || ''})`)
        this.$indicator.close()
      })
      this.$refs.cm2.cancel()
    },

    resetPwd (user) {
      this.$indicator.open()
      userApi.resetPwd(user).then(resp => {
        if (resp.success) {
          setTimeout(() => {
            this.$Modal.confirm({
              title: '提示',
              content: '重置密码成功！<br>新密码为：123456'
            })
          }, 300)
        } else {
          this.$Message.error(`重置用户密码失败(${resp.message || ''})`)
        }
      }).catch(err => {
        this.$Message.error(`重置用户密码失败(${err.message || ''})`)
      }).then(() => { this.$indicator.close() })
    },
    openConfirm (target) {
      this.$Modal.confirm({
        title: '提示',
        content: '确认要开启用户吗？',
        onOk: () => {
          this.openUser(target)
        }
      })
    },

    closeConfirm (target) {
      this.$Modal.confirm({
        title: '提示',
        content: '确认要关闭用户吗？',
        onOk: () => {
          this.closeUser(target)
        }
      })
    },

    resetPwdConfirm (target) {
      this.$Modal.confirm({
        title: '提示',
        content: '确认要重置用户密码吗？',
        onOk: () => {
          this.resetPwd(target)
        }
      })
    },
    changeRows (num) {
      this.pageSize = num
      this.loadData()
    },
    submit () {
      this.$refs.advancedSearch.submit()
    },
    reSetForm () {
      this.$refs.advancedSearch.reSetForm()
    }
  },

  components: {
    SearchForm, ManagerTable, SingleModal
  }
}
</script>
