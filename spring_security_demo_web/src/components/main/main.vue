<template>
  <Layout style="height: 100%"
          class="main">
    <Sider hide-trigger
           collapsible
           :width="249"
           :collapsed-width="64"
           v-model="collapsed"
           class="left-sider"
           :style="{overflow: 'hidden'}">
      <!-- <a href="http://www.cigage.com" target="view_window"> -->
      <div class="logo-icon">
        <img v-show="!collapsed"
             :src="maxLogo"
             key="max-logo" />
        <!-- <img v-show="collapsed" :src="minLogo" key="min-logo" /> -->
      </div>
      <side-menu accordion
                 ref="sideMenu"
                 :active-name="activeName"
                 :open-names="openNames"
                 :collapsed="collapsed"
                 @on-select="onMenuSelect"
                 @menu-item-select="handleMenuSelect"
                 :menu-list="menus">
      </side-menu>
    </Sider>

    <Layout>
      <Header class="header-con">
        <header-bar :collapsed="collapsed"
                    @on-coll-change="handleCollapsedChange">
          <user :user-avator="userAvator"
                :user="user" />
          <!--<fullscreen v-model="isFullscreen"-->
                      <!--style="margin-right: 10px;" />-->
         <!-- <theme-switch></theme-switch>-->
        </header-bar>
      </Header>
      <Layout v-if="doneInit">
        <Content class="main-content-con">
          <Layout class="main-layout-con">
            <Content class="content-wrapper">
              <!-- 使用keep-alive会导致重新加载页面时，mounted方法不执行，无法刷新页面数据 -->
              <!-- <keep-alive> -->
              <router-view />
              <!-- </keep-alive> -->
            </Content>
          </Layout>
        </Content>
      </Layout>
    </Layout>
    <reset-pwd v-model="show"
               force />
  </Layout>
</template>
<script>
  import UserService from '@/service/UserService'
  import SysDict from '@/service/SysDict'
  import sysApi from '@/api/sys/sys'
  import * as R from '@/router/router-types'
  import SideMenu from './components/side-menu'
  import HeaderBar from './components/header-bar'
  import User from './components/user'
  import Fullscreen from './components/fullscreen'
  import ThemeSwitch from './components/theme-switch/theme-switch'
  import {mapMutations} from 'vuex'
  import maxLogo from '@/assets/imgs/system/logo-max.png'
  import './main.less'
  import '@/assets/css/common.less'
  import ResetPwd from './components/user/widgets/ResetPwd'

  export default {
  name: 'Main',
  data () {
    return {
      doneInit: false,
      user: {},
      collapsed: false,
      // minLogo,
      maxLogo,
      isFullscreen: false,
      userAvator: require('../../assets/imgs/system/default_avatar.png'),
      menus: [],
      openNames: [],
      activeName: undefined,
      show: false
    }
  },

  beforeCreate () {
    this.$indicator.open()
    // 页面打开的时候加载必要的数据(首页数据)
    Promise.all([
      SysDict.load(),
      UserService.loadUserFunc()
    ]).then(() => {
      this.doneInit = true
      this.$indicator.close()
      this.$nextTick(() => {
        this.user = UserService.getUser()
        if (UserService.getUser().salt === '0') {
          this.show = true
          return
        }
        // this.$refs.body.addEventListener('scroll', this.handleScroll)
        // 加载左侧菜单
        this.$indicator.open()
        sysApi.findLicensedMenuEasyUI().then((res) => {
          if (res.length > 0) {
            let unShowMenu = []
            this.menus = res.map(m => {
              if (m.children) {
                m.children = m.children.filter(sm => {
                  return !unShowMenu.find(_menu => sm.url === _menu)
                })
              }
              return m
            })
            this.activeMenu(this.$route)
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
    })
  },

  mounted () {
    document.getElementsByTagName('body')[0].className = 'theme1'
  },

  beforeRouteUpdate (to, from, next) {
    this.activeMenu(to)
    next()
  },
  methods: {
    ...mapMutations([
      'setBreadCrumb'
    ]),
    handleCollapsedChange (state) {
      this.collapsed = state
    },
    activeMenu (to) {
      // 层级菜单转化成菜单数组
      let allMenuItem = this.menus.reduce((sofar, m) => {
        return [...sofar, ...m.children]
      }, [])
      let url = ''
      for (let i = 0; i < allMenuItem.length; i++) {
        if (to.path.indexOf(allMenuItem[i].url) !== -1) {
          url = allMenuItem[i].url
          break
        }
      }
      // 获取子菜单
      let menu = allMenuItem.find(m => {
        return m.url === url
      })
      // 如果找到了子菜单，那么找到他的父菜单，并展开
      if (menu) {
        this.activeName = menu.menuName
        let parent = this.menus.find(m => {
          return m.menuId === menu.parentMenuId
        })
        if (!this.openNames.find(op => op === parent.menuName)) {
          this.openNames.push(parent.menuName)
        }
      } else {
        this.activeName = ''
        this.openNames = []
      }

      this.$nextTick(() => {
        if (this.$refs.menu) {
          this.$refs.menu.updateOpened()
          this.$refs.menu.updateActiveName()
        }
      })
    },
    // 选择菜单
    onMenuSelect (name, activeName) {
      this.activeName = activeName
      let allMenuItem = this.menus.reduce((sofar, m) => {
        return [...sofar, ...m.children]
      }, [])

      let menu = allMenuItem.find(m => {
        return m.menuName === name
      })
      if (menu.url) {
        this.showIndex = false
      } else {
        this.showIndex = true
      }
      this.$router.push({ path: menu.url })
    },
    handleMenuSelect (activeName) {
      this.activeName = activeName
    }
  },

  computed: {
  },

  components: {
    SideMenu,
    HeaderBar,
    Fullscreen,
    ThemeSwitch,
    User,
    ResetPwd
  }
}
</script>
<style scoped>
/* .ivu-menu-item-active .ivu-menu-item-selected {
  background-color: rgb(26, 187, 151) !important;
}
.layout {
  border: 1px solid #d7dde4;
  background: #f5f7f9;
  position: relative;
  border-radius: 4px;
  overflow: hidden;
}
.layout-header-bar {
  background: #fff;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
} */
</style>
