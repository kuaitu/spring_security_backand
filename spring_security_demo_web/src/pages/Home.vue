<template>
  <div class="layout">
    <Layout>
      <Header>
        <Menu mode="horizontal" theme="dark" active-name="1">
          <div class="layout-logo"></div>
          <div class="layout-nav">
            <MenuItem name="1">
              <Icon type="ios-navigate"></Icon>
              Item 1
            </MenuItem>
            <MenuItem name="2">
              <Icon type="ios-keypad"></Icon>
              Item 2
            </MenuItem>
            <MenuItem name="3">
              <Icon type="ios-analytics"></Icon>
              Item 3
            </MenuItem>
            <MenuItem name="4">
              <Icon type="ios-paper"></Icon>
              Item 4
            </MenuItem>
          </div>
        </Menu>
      </Header>
      <Layout>
        <Sider hide-trigger
               :style="{background: '#fff'}">
          <Menu active-name="1-2"
                theme="light"
                width="auto"
                :open-names="['i']">
            <Submenu
              :name="item.id"
              v-for="(item, i) in menus"
              :key="i"
              v-if="item.enabled">
              <template slot="title">
                <Icon :type="item.iconCls" />
                {{item.permissionName}}
              </template>
              <MenuItem
                :name="itemSub.id"
                v-for="(itemSub, j) in item.children"
                :key="j"
                v-if="itemSub.enabled">
                  <Icon :type="itemSub.iconCls" />
                {{itemSub.permissionName}}
              </MenuItem>
              <!--<Submenu :name="itemSub.id" v-for="(itemSub, j) in item.children" :key="j">
                <template slot="title">{{itemSub.permissionName}}</template>
              </Submenu>-->
            </Submenu>
          </Menu>
        </Sider>
        <Layout :style="{padding: '0 24px 24px'}">
          <Breadcrumb :style="{margin: '24px 0'}">
            <BreadcrumbItem>Home</BreadcrumbItem>
            <BreadcrumbItem>Components</BreadcrumbItem>
            <BreadcrumbItem>Layout</BreadcrumbItem>
          </Breadcrumb>
          <Content :style="{padding: '24px', minHeight: '280px', background: '#fff'}">
            Content
          </Content>
        </Layout>
      </Layout>
    </Layout>
  </div>
</template>

<script>
  import UserService from '@/service/UserService'
  import SysDict from '@/service/SysDict'
  import sysApi from '@/api/sys/sys'

  export default {
      data () {
        return {
          menus: []
        }
      },

      beforeCreate () {
        Promise.all([
          SysDict.load(),
          UserService.loadUserFunc()
        ]).then(() => {
          // 加载左侧菜单
          this.$indicator.open()
          sysApi.findLicensedMenuEasyUI().then((res) => {
            if (res.data.length > 0) {
              this.menus = res.data
            } else {
              this.$Message.error(`加载失败：${res.message}`)
              if (res.message === '会话过期，请重新登陆！') {
                UserService.localLogout()
                this.$router.push({
                  path: R.kLogin
                })
              }
            }
            this.$indicator.close()
          }).catch(err => {
            this.$Modal.error({ title: '失败', content: `获取数据失败${err.message || ''}` })
            this.$indicator.close()
          })
        })
      }
    }
</script>

<style scoped>
  .layout{
    border: 1px solid #d7dde4;
    background: #f5f7f9;
    position: relative;
    border-radius: 4px;
    overflow: hidden;
  }
  .layout-logo{
    width: 100px;
    height: 30px;
    background: #5b6270;
    border-radius: 3px;
    float: left;
    position: relative;
    top: 15px;
    left: 20px;
  }
  .layout-nav{
    width: 420px;
    margin: 0 auto;
    margin-right: 20px;
  }
</style>
