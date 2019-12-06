<template>
  <div>
    <Table :data="data"
           border
           :columns="tableColumns"
           tooltip-theme="light"
           stripe>
      <template slot-scope="{ row }"
                slot="menuIcon">
        <Icon :type="`${row.menuIcon}`"
              size='24' />
      </template>
      <template slot-scope="{ row }"
                slot="roleType"
                v-if="row.roleType && row.roleType !== ''">
        {{ translate('ORG_ROLE_TYPE', row.roleType) }}
      </template>
      <template slot-scope="{ row }"
                slot="roleStatus">
        <Tag :color="openStatusColor(row)">{{ translate('COMMON_STATUS', row.roleStatus) }}</Tag>
      </template>
      <template slot-scope="{ row }"
                slot="action">
        <Button size="small"
                style="margin-right: 5px"
                @click="editFunc(row)"
                v-if="hasRoleFunc">设置权限</Button>
        <Button size="small"
                @click="viewRole(row)"
                v-if="hasRoleEditFunc">查看</Button>
        <Button size="small"
                @click="editRole(row)"
                v-if="hasRoleEditFunc">编辑</Button>
        <Button size="small"
                @click="openRole(row)"
                v-if="hasRoleEnableFunc && isOpen(row)">启用</Button>
        <Button size="small"
                @click="closeRole(row)"
                v-if="hasRoleEnableFunc && isClose(row)">禁用</Button>
      </template>
    </Table>
    <div v-if="total > 0"
         style="margin: 10px; overflow: hidden;">
      <div style="float: right; ">
        <Page :total="total"
              :current="currentPage"
              show-elevator
              show-total
              show-sizer
              :page-size-opts="pageNumArr"
              @on-page-size-change="pageNum"
              @on-change="pageChange"
              placement="top">
        </Page>
      </div>
    </div>
  </div>
</template>
<script>
  import sysDictService from '@/service/SysDict'
  import userService from '@/service/UserService'
  import Util from '@/utils/util'

  export default {
  props: ['data', 'total', 'currentPage'],
  data () {
    return {
      tableColumns: [{
        title: '序号',
        type: 'index',
        width: 70,
        align: 'center',
        key: 'index'
      }, {
        title: '角色名称',
        key: 'roleName',
        width: 150,
        align: 'center'
      }, {
        title: '角色类型',
        key: 'roleType',
        align: 'center',
        width: 120,
        slot: 'roleType'
      }, {
        title: '创建时间',
        key: 'addTime',
        align: 'center',
        width: 160,
        render: (h, params) => {
          let item = this.data[params.index]
          return h('span', {}, Util.formatDate(item.addTime, 'yyyy-MM-dd hh:mm:ss'))
        }
      }, {
        title: '角色描述',
        key: 'roleDescribe',
        align: 'center'
      }, {
        title: '状态',
        key: 'roleStatus',
        align: 'center',
        slot: 'roleStatus',
        width: 120
      }, {
        title: '操作',
        align: 'center',
        width: 250,
        slot: 'action',
        key: 'action'
      }],
      pageNumArr: [10, 20, 50, 100]
    }
  },
  methods: {
    pageChange (page) {
      this.$emit('pageChange', page)
    },
    pageNum (num) {
      this.$emit('changeRows', num)
    },
    editFunc (role) {
      this.$emit('editFunc', role.roleId)
    },
    viewRole (role) {
      this.$emit('viewRole', role)
    },
    editRole (role) {
      this.$emit('editRole', role)
    },
    closeRole (role) {
      this.$emit('closeRole', role)
    },
    openRole (role) {
      this.$emit('openRole', role)
    },
    isOpen (role) {
      return sysDictService.translate('COMMON_STATUS', role.roleStatus) === '禁用' && role.isAdmin !== '1'
    },
    isClose (role) {
      return sysDictService.translate('COMMON_STATUS', role.roleStatus) === '启用' && role.isAdmin !== '1'
    },
    translate (dictCode, dictItemCode) {
      return sysDictService.translate(dictCode, dictItemCode)
    },
    openStatusColor (role) {
      let roleStatus = sysDictService.translate('COMMON_STATUS', role.roleStatus) === '启用'
      if (roleStatus) {
        return 'success'
      } else {
        return 'error'
      }
    },
    setTableColumns (columns) {
      this.tableColumns = columns
    }
  },
  computed: {
    hasAdd () {
      return userService.hasFunc('role_add')
    },
    hasRoleEnableFunc () {
      return userService.hasFunc('role_enable')
    },
    hasRoleViewFunc () {
      return userService.hasFunc('role_view')
    },
    hasRoleEditFunc () {
      return userService.hasFunc('role_edit')
    },
    hasRoleFunc () {
      return userService.hasFunc('role_func')
    }
  },
  components: {}
}
</script>
