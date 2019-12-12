<template>
  <div>
    <Table :data="data" border :columns="tableColumns" stripe>
      <template slot-scope="{ row }" slot="name">
        <span>{{ row.userName }}</span>
        <Tag color="success" v-for="role of row.uwRole.filter(_role => {return _role.hasRole}, [])" :key="role.roleId">
          {{ role.roleName }}
        </Tag>
      </template>
      <template slot-scope="{ row }" slot="userType">
        <Tag >{{ translate('SYS_USER_TYPE', row.userType) }}</Tag>
      </template>
      <template slot-scope="{ row }" slot="userStatus">
        <Tag :color="openStatusColor(row)">{{ translate('COMMON_STATUS', row.userStatus) }}</Tag>
      </template>
      <template slot-scope="{ row }" slot="action">
        <div class="action-row">
          <div class="action-btn" @click="resetPwd(row)" v-if="hasUuserEditPwd" style=" color:rgba(58,224,165);">重置密码</div>
          <div class="action-btn" @click="closeUser(row)" v-if="hasUserEnable && isOpen(row)" style=" color:rgba(255,50,50);">禁用</div>
          <div class="action-btn" @click="openUser(row)" v-if="hasUserEnable && isClose(row)" style=" color:rgba(131,160,222);">启用</div>
          <div class="action-btn" @click="editUser(row)" v-if="hasUserEdit" style=" color:rgba(131,160,222);">编辑</div>
          <div class="action-btn" @click="editUserRole(row)" v-if="hasUserRoleEdit" style=" color:rgba(131,160,222);">修改角色</div>
        </div>
      </template>
    </Table>
    <div v-if="total > 0" style="margin: 10px; overflow: hidden;">
      <div style="float: right; ">
        <Page :total="total" :current="currentPage" show-elevator show-total show-sizer :page-size-opts="pageNumArr" @on-page-size-change="pageNum"
          @on-change="pageChange" placement="top">
        </Page>
      </div>
    </div>
  </div>
</template>
<script>
  import sysDictService from '@/service/SysDict'
  import userService from '@/service/UserService'

  export default {
  props: ['data', 'total', 'currentPage'],
  data () {
    return {
      tableColumns: [{
        title: '序号',
        type: 'index',
        width: 70,
        // fixed: 'left',
        align: 'center',
        disabled: true,
        key: 'index'
      }, {
        title: '姓名',
        align: 'center',
        width: 200,
        slot: 'name',
        key: 'name'
      }, {
        title: '真实姓名',
        key: 'realName',
        width: 120,
        align: 'center'
      }, {
        title: '电子邮箱',
        ellipsis: true,
        tooltip: true,
        key: 'email',
        align: 'center'
      }, {
        title: '状态',
        key: 'userStatus',
        align: 'center',
        width: 100,
        slot: 'userStatus'
      }, {
        title: '操作',
        align: 'center',
        width: 300,
        slot: 'action',
        key: 'action',
        disabled: true
      }],
      pageNumArr: [10, 20, 50, 100]
    }
  },
  methods: {
    setTableColumns (columns) {
      this.tableColumns = columns
    },
    pageChange (page) {
      this.$emit('pageChange', page)
    },
    pageNum (num) {
      this.$emit('changeRows', num)
    },
    translate (dictCode, dictItemCode) {
      return sysDictService.translate(dictCode, dictItemCode)
    },
    editUserRole (user) {
      this.$emit('editUserRole', user.userId)
    },
    editUser (user) {
      this.$emit('editUser', user.userId)
    },
    closeUser (user) {
      this.$emit('closeUser', user)
    },
    openUser (user) {
      this.$emit('openUser', user)
    },
    resetPwd (user) {
      this.$emit('resetPwd', user)
    },
    isOpen (user) {
      return sysDictService.translate('COMMON_STATUS', user.userStatus) === '启用'
    },
    isClose (user) {
      return sysDictService.translate('COMMON_STATUS', user.userStatus) === '禁用'
    },
    openStatusColor (user) {
      let userStatus = sysDictService.translate('COMMON_STATUS', user.userStatus) === '启用'
      if (userStatus) {
        return 'success'
      } else {
        return 'error'
      }
    }
  },
  mounted () {
  },
  computed: {
    hasUuserEditPwd () {
      return userService.hasFunc('user_manage_reset')
    },
    hasUserEdit () {
      return  userService.hasFunc('user_manage_edit')
    },
    hasUserEnable () {
      return  userService.hasFunc('user_manage_ok')
    },
    hasUserRoleEdit () {
      return userService.hasFunc('user_manage_editRole')
    }
  },
  components: {}
}
</script>
<style lang="less" scoped>
  .action-row {
    display: flex;
    justify-content: center;
    justify-items: center;
  }
  .action-btn {
    font-size:12px;
    font-family:Microsoft YaHei;
    font-weight:bold;
    margin-left: 7px;
    cursor: pointer;
  }
</style>
