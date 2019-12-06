<template>
  <div>
    <role-manager-search-form @doSearch="doSearch"
                              @addRole="addRole"
                              class="search-form"
                              ref="searchForm">
    </role-manager-search-form>
    <role-manager-table :data="data"
                        :total="total"
                        :currentPage="currentPage"
                        @changeRows="changeRows"
                        @pageChange="pageChange"
                        @removeRole="removeRole"
                        @editRole="editRole"
                        @viewRole="viewRole"
                        class="connon-manager-table"
                        @openRole="openConfirm"
                        @closeRole="closeConfirm"
                        @editFunc="editFunc">
    </role-manager-table>
    <form-modal ref="roleForm"
                @onSave="onSave"
                @onUpdate="onUpdate"></form-modal>
    <func-modal ref="funcForm"></func-modal>
    <view-modal ref="viewModal">></view-modal>
  </div>
</template>

<script>
  import FormModal from './widgets/FormModal'
  import FuncModal from './widgets/FuncModal'
  import ViewModal from './widgets/View'
  import RoleManagerSearchForm from './RoleManagerSearchForm'
  import RoleManagerTable from './RoleManagerTable'
  import roleApi from '@/api/sys/role'
  import UserService from '@/service/UserService'
  // import * as R from '@/router/router-types'
// import Util from '@/utils/util'

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
      roleApi.search({
        exampleJson: JSON.stringify(this.params),
        page: this.currentPage,
        rows: this.pageSize
      }).then(resp => {
        if (resp.success) {
          this.total = resp.total
          this.data = resp.data
        } else {
          this.$Message.error('加载数据失败' + resp.message)
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

    addRole () {
      // this.$router.push({ path: R.kUwRoleAdd })
      this.$refs.roleForm.openModal(true, 'ADD')
    },

    pageChange (page) {
      this.currentPage = page
      this.loadData()
    },

    removeRole (role) {
      this.$Modal.confirm({
        title: '删除确认',
        content: `确认要删除角色吗?`,
        onOk: () => {
          this.$indicator.open()
          roleApi.delRole(role).then(resp => {
            if (resp.success) {
              this.$Notice.success({ title: '删除角色成功' })
              this.loadData()
            } else {
              // this.$Message.error(`删除角色失败(${resp.message || ''})`)
              this.$Modal.error({
                title: '删除角色失败',
                content: (resp.message ? resp.message : '未知错误')
              })
            }
            this.$indicator.close()
          }).catch(err => {
            // this.$Message.error(`删除角色失败(${err || ''})`)
            this.$Modal.error({
              title: '删除角色失败',
              content: err
            })
            this.$indicator.close()
          })
        }
      })
    },

    editRole (item) {
      // this.$router.push({
      //   name: 'UwRoleEdit',
      //   params: {
      //     id
      //   }
      // })
      this.$refs.roleForm.openModal(true, 'EDIT')
      this.$refs.roleForm.initData(item)
    },

    viewRole (item) {
      // this.$refs.roleForm.openModal(true, 'VIEW')
      // this.$refs.roleForm.initData(item)
      this.$refs.viewModal.openModal(true, item.roleId)
      this.$refs.viewModal.initData(item)
    },

    openRole (role) {
      this.$indicator.open()
      roleApi.openRole(role).then(resp => {
        if (resp.success) {
          this.$Notice.success({ title: '开启角色成功' })
          this.loadData()
        } else {
          // this.$Message.error(`开启角色失败(${resp.message || ''})`)
          this.$Modal.error({
            title: '开启角色失败',
            content: (resp.message ? resp.message : '未知错误')
          })
        }
        this.$indicator.close()
      }).catch(err => {
        // this.$Message.error(`开启角色失败(${err || ''})`)
        this.$Modal.error({
          title: '开启角色失败',
          content: err
        })
        this.$indicator.close()
      })
    },

    closeRole (role) {
      this.$indicator.open()
      roleApi.closeRole(role).then(resp => {
        if (resp.success) {
          this.$Notice.success({ title: '关闭角色成功' })
          this.loadData()
        } else {
          // this.$Message.error(`关闭角色失败(${resp.message || ''})`)
          this.$Modal.error({
            title: '关闭角色失败',
            content: (resp.message ? resp.message : '未知错误')
          })
        }
        this.$indicator.close()
      }).catch(err => {
        // this.$Message.error(`关闭角色失败(${err || ''})`)
        this.$Modal.error({
          title: '关闭角色失败',
          content: err
        })
        this.$indicator.close()
      })
    },

    openConfirm (target) {
      this.$Modal.confirm({
        title: '提示',
        content: '确认要开启角色吗？',
        onOk: () => {
          this.openRole(target)
        }
      })
    },

    closeConfirm (target) {
      this.$Modal.confirm({
        title: '提示',
        content: '确认要关闭角色吗？',
        onOk: () => {
          this.closeRole(target)
        }
      })
    },

    editFunc (id) {
      // this.$router.push({
      //   name: 'UwRoleFunc',
      //   params: {
      //     id
      //   }
      // })
      this.$indicator.open()
      roleApi.findMenuAndFunc({roleId: id}).then(resp => {
        if (resp.success) {
          let menus = []
          resp.data.map(menu => {
            for (let i = 0; i < menu.children.length; i++) {
              let subMenu = menu.children[i]
              subMenu.parent = menu
              for (let j = 0; j < subMenu.jurisdiction4Uw.length; j++) {
                subMenu.jurisdiction4Uw[j].parent = subMenu
              }
            }
            menus.push(menu)
            this.$refs.funcForm.openModal(true)
            this.$refs.funcForm.initData(menus, id)
          })
        } else {
          this.$Notice.error({ title: '加载数据失败', desc: `获取权限菜单失败:${resp.message}` })
        }
        this.$indicator.close()
      }).catch(err => {
        this.$Notice.error({ title: '加载数据失败', desc: `获取权限菜单失败:${err | ''}` })
        this.$indicator.close()
      })
    },

    onSave (role) {
      this.$indicator.open()
      roleApi.addRole(role).then(resp => {
        if (resp.success) {
          this.$Notice.success({ title: '操作成功', content: '添加角色成功' })
          this.$refs.roleForm.openModal(false)
          this.loadData()
        } else {
          // this.$Message.error(`添加角色失败：${resp.message}`)
          this.$Modal.error({
            title: '添加角色失败',
            content: (resp.message ? resp.message : '未知错误')
          })
        }
        this.$indicator.close()
      }).catch(err => {
        // this.$Message.error(`添加角色失败：${err || ''}`)
        this.$Modal.error({
          title: '添加角色失败',
          content: err
        })
        this.$indicator.close()
      })
    },

    onUpdate (role) {
      this.$indicator.open()
      roleApi.editRole(role).then(resp => {
        if (resp.success) {
          this.$Notice.success({ title: '操作成功', content: '编辑角色成功' })
          this.$refs.roleForm.openModal(false)
          this.loadData()
        } else {
          // this.$Message.error(`编辑角色失败${resp.message || ''}`)
          this.$Modal.error({
            title: '编辑角色失败',
            content: (resp.message ? resp.message : '未知错误')
          })
        }
        this.$indicator.close()
      }).catch(err => {
        // this.$Message.error(`编辑角色失败${err || ''}`)
        this.$Modal.error({
          title: '编辑角色失败',
          content: err
        })
        this.$indicator.close()
      })
    },

    changeRows (num) {
      this.pageSize = num
      this.loadData()
    }
  },

  computed: {
    hasAddFunc () {
      return UserService.hasFunc('platformRole_add')
    }
  },

  components: {
    RoleManagerSearchForm, RoleManagerTable, FormModal, FuncModal, ViewModal
  }
}
</script>
