
<template>
  <div>
    <Modal v-model="isOpen"
           class-name="vertical-center-modal"
           width="720"
           :z-index="1000"
           :mask-closable="false">
      <Tabs v-model="tabName">
        <TabPane label="基本信息"
                 name="1">
          <Form ref="roleForm"
                class="form"
                inline
                :model="roleModel"
                :label-width="120">
            <FormItem label="角色名称:"
                      prop="roleName">
              <Input v-model.trim="roleModel.roleName"
                     placeholder="请输入角色名称"
                     :disabled="true"
                     style="width:200px;"></Input>
            </FormItem>
            <FormItem label="角色类型:"
                      prop="roleType"
                      v-if="roleModel.isAdmin !== '1'">
              <dict-item-select dictName="ORG_ROLE_TYPE"
                                :disabled="true"
                                v-model.trim="roleModel.roleType"
                                style="width:200px">
              </dict-item-select>
            </FormItem>
            <FormItem label="组织机构:"
                      prop="orgIdList"
                      v-if="isHide">
              <div style="display:flex;">
                <Tooltip :content="orgNameList"
                         :disabled="!orgNameList || orgNameList === ''"
                         transfer
                         max-width="400"
                         placement="top-start">
                  <Input :value="orgNameList"
                         :disabled="true"
                         :style="{width : '532px'}"></Input>
                </Tooltip>
              </div>
            </FormItem>
            <FormItem label="角色描述:"
                      prop="roleDescribe">
              <Input type="textarea"
                     v-model.trim="roleModel.roleDescribe"
                     placeholder="请输入角色描述"
                     :maxlength="255"
                     :disabled="true"
                     :autosize="{minRows: 4, maxRows: 4}"
                     style="width:532px">
              </Input>
            </FormItem>
            <FormItem label="状态:"
                      prop="roleStatus">
              <i-switch v-model="roleModel.roleStatusOn"
                        size="large"
                        :disabled="true">
                <span slot="enable">开启</span>
                <span slot="disable">关闭</span>
              </i-switch>
            </FormItem>
          </Form>
        </TabPane>
        <TabPane label="权限"
                 name="2">
          <div class="func_body">
            <div class="authset-all"
                 v-if="menus.length > 0">
              <div v-for="menu in menus"
                   :key="menu.menuId">
                <dl>
                  <dt class="tit">
                    {{menu.menuName}}
                  </dt>
                  <dd>
                    <div class="authset-list"
                         v-for="subMenu in menu.children"
                         :key="subMenu.menuId">
                      <label>
                        {{subMenu.menuName}}
                      </label>
                      <ul>
                        <li v-for="func in subMenu.jurisdiction4Uw"
                            :key="func.jurisdictionId">
                          <span>
                            <Icon type="ios-copy" />{{func.jurisdictionName}}</span>
                        </li>
                        <div style="clear: both"></div>
                      </ul>
                    </div>
                  </dd>
                </dl>
              </div>
            </div>
          </div>
        </TabPane>
      </Tabs>
      <div slot="footer">
        <Button @click="cancel"
                class="btn"
                style="margin-left: 8px">取消</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  import roleApi from '@/api/sys/role'

  export default {
  components: {},
  props: [],
  data () {
    return {
      data: [],
      isOpen: false,
      isHide: true,
      orgNameList: '',
      menus: [],
      tabName: '1',
      roleModel: {
        roleId: null,
        roleName: '',
        roleDescribe: '',
        roleStatusOn: true,
        isAdmin: '0',
        orgIdList: [],
        roleType: '99'
      }
    }
  },
  watch: {
    'roleModel.roleType': {
      handler (val) {
        if (val === '99') {
          this.isHide = true
        } else {
          this.isHide = false
        }
      },
      immediate: true
    }
  },
  methods: {
    openModal (open, roleId) {
      this.isOpen = open
      this.reSetForm()
      this.editFunc(roleId)
    },
    editFunc (id) {
      this.$indicator.open()
      roleApi.getMenuAndFunc({roleId: id}).then(resp => {
        if (resp.success) {
          resp.data.map(menu => {
            for (let i = 0; i < menu.children.length; i++) {
              let subMenu = menu.children[i]
              subMenu.parent = menu
              for (let j = 0; j < subMenu.jurisdiction4Uw.length; j++) {
                subMenu.jurisdiction4Uw[j].parent = subMenu
              }
            }
            this.menus.push(menu)
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
    reSetForm () {
      this.$refs.roleForm.resetFields()
      this.orgNameList = ''
      this.roleModel.orgIdList = []
    },

    cancel () {
      this.isOpen = false
      this.tabName = '1'
      this.menus = []
    },
    initData (initRole) {
      this.roleModel.roleId = initRole.roleId
      this.roleModel.roleName = initRole.roleName
      this.roleModel.roleDescribe = initRole.roleDescribe
      if (initRole.roleStatus === 'disable') {
        this.roleModel.roleStatusOn = false
      }
      this.roleModel.isAdmin = initRole.isAdmin
      this.roleModel.roleType = initRole.roleType
      this.roleModel.orgIdList = initRole.orgIdList
      this.orgNameList = initRole.orgNameList
    }
  },
  created () {},
  mounted () {
  }
}
</script>
<style lang="less" scoped>
dl,
dd,
ul {
  margin: 0;
  padding: 0;
}

.func_body {
  height: 290px;
  // padding: 10px;
  overflow: scroll;
  overflow-x: auto;
  overflow-x: 1px;
}

.authset-all {
  border: 1px solid #ddd;
  margin-bottom: 20px;
  color: #666;
  font-size: 12px;
  font-family: "Microsoft Yahei", "Lucida Grande", Verdana, Lucida, Helvetica,
    Arial, sans-serif;
}

.authset-all dt.tit {
  font-size: 14px;
  line-height: 30px;
  color: #555;
  background-color: #fafafa;
  height: 30px;
  padding: 0px 10px;
  font-weight: normal;
  border-bottom: 1px dotted #ddd;
}

.authset-list {
  width: 100%;
  height: auto;
  display: inline-block;
  clear: both;
  border-bottom: 1px dotted #ddd;
  padding: 10px 0px;
  margin: 0px;
}

.authset-list ul {
  width: 80%;
  min-height: 25px;
  display: inline-block;
  border-left: dotted 1px #ccc;
  list-style-image: none;
  list-style-type: none;
}

.authset-list li {
  list-style: none;
  font-size: 12px;
  line-height: 20px;
  // color: #999;
  color: #8b8a8a;
  letter-spacing: normal;
  word-spacing: normal;
  display: inline-block;
  height: 20px;
  margin-bottom: 5px;
  padding-left: 15px;
  text-align: left;
}

.authset-list label {
  float: left;
  text-align: left !important;
  padding-left: 25px;
  padding-right: 0;
  width: 20%;
}
</style>
