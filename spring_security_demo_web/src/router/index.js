/*eslint-disable*/
import Vue from 'vue'
import Router from 'vue-router'
import * as R from '@/router/router-types'
// 菜单管理
import MenuManager from '@/pages/sys/menu/MenuManager'
import MenuManagerChild from '@/pages/sys/menu/child/MenuManager'
import MenuManagerFunc from '@/pages/sys/menu/child/func/FuncManager'
// 权限中心->角色
import UwRoleManager from '@/pages/sys/role/RoleManager'
// ------------------------------ 业 务 系 统Start ------------------------------
// 首页
import IndexMessage from '@/pages/business/home/IndexMessage'
// 企业信息查询 --＞工商信息查询
import Industry from '@/pages/business/enterprise/industry/Manager'
import IndustryView from '@/pages/business/enterprise/industry/View'
// 企业信息查询 --＞ 对外贸易经营者备案信息查询
import ForeignTrader from '@/pages/business/enterprise/foreignTrader/Manager'
import ForeignTraderView from '@/pages/business/enterprise/foreignTrader/View'
// 信用信息管理 --＞ 失信企业管理
import LostLetter from '@/pages/business/credit/lostLetter/Manager'
import LostLetterView from '@/pages/business/credit/lostLetter/View'
// ------------------------------ 用 户 服 务Start------------------------------
// 登录
import Login from '@/pages/user/Login'
// 主界面
import Main from '@/components/main'
// ------------------------------ 系 统 服 务Start ------------------------------
const MenuRouter = [{
  path: R.KMenuManager,
  name: 'MenuManager',
  component: MenuManager,
  meta: {
    topLevel: true,
    auth: true,
    keepAlive: false,
    func: '',
    bread: [
      // { name: '首页', url: R.kIndex },
      { name: '系统管理' },
      { name: '菜单管理' }
    ]
  }
},
{
  path: R.KMenuManagerChild,
  name: 'MenuManagerChild',
  component: MenuManagerChild,
  meta: {
    auth: true,
    func: '',
    keepAlive: false,
    bread: [
      // { name: '首页', url: R.kIndex },
      { name: '系统管理' },
      { name: '菜单管理', url: R.KMenuManager },
      { name: '子菜单' }
    ]
  }
},
{
  path: R.KMenuManagerFunc,
  name: 'MenuManagerFunc',
  component: MenuManagerFunc,
  meta: {
    auth: true,
    func: '',
    keepAlive: false,
    bread: [
      // { name: '首页', url: R.kIndex },
      { name: '系统管理' },
      { name: '菜单管理', url: R.KMenuManager },
      { name: '子菜单' },
      { name: '权限' }
    ]
  }
}]


const UwRoleRouter = [{
  path: R.kUwRole,
  name: 'RoleManager',
  component: UwRoleManager,
  meta: {
    auth: true,
    keepAlive: false,
    func: '',
    bread: [
      // { name: '首页', url: R.kIndex },
      { name: '用户管理' },
      { name: '角色列表' }
    ]
  }
}]

// 加入系统服务总路由
const SysRouter = [].concat(MenuRouter).concat(UwRoleRouter)

// ------------------------------ 系 统 服 务End ------------------------------
const HomeRouter = [{
  path: R.kIndexMessage,
  name: 'IndexMessage',
  component: IndexMessage,
  meta: {
    Login: true,
    auth: true,
    bread: [{name: '首页', icons: 'md-home'}]
  }
}]

const IndustryRouter = [{
  path: R.QIndustry,
  name: 'IndustryManager',
  component: Industry,
  meta: {
    topLevel: true,
    auth: true,
    keepAlive: true,
    func: '',
    bread: [
      // { name: '首页', url: R.kIndex },
      { name: '企业信息查询' },
      { name: '工商信息查询' }
    ]
  }
},
  {
    path: R.QIndustryView,
    name: 'IndustryView',
    component: IndustryView,
    meta: {
      auth: true,
      func: '',
      keepAlive: false,
      bread: [
        // { name: '首页', url: R.kIndex },
        { name: '企业信息查询', },
        { name: '工商信息查询', url: R.QIndustry},
        { name: '工商信息详情' }
      ]
    }
  }]

const ForeignTraderRouter = [{
  path: R.QForeignTrader,
  name: 'ForeignTraderManager',
  component: ForeignTrader,
  meta: {
    topLevel: true,
    auth: true,
    keepAlive: false,
    func: '',
    bread: [
      // { name: '首页', url: R.kIndex },
      { name: '企业信息查询' },
      { name: '对外贸易经营者备案信息查询' }
    ]
  }
},
  {
    path: R.QForeignTraderView,
    name: 'ForeignTraderView',
    component: ForeignTraderView,
    meta: {
      auth: true,
      func: '',
      keepAlive: false,
      bread: [
        // { name: '首页', url: R.kIndex },
        { name: '企业信息查询', },
        { name: '对外贸易经营者备案信息查询', url: R.QForeignTrader},
        { name: '对外贸易经营者备案信息详情' }
      ]
    }
  }]

const LostLetterRouter = [{
  path: R.QLostLetter,
  name: 'LostLetterManager',
  component: LostLetter,
  meta: {
    topLevel: true,
    auth: true,
    keepAlive: false,
    func: '',
    bread: [
      // { name: '首页', url: R.kIndex },
      { name: '信用信息管理' },
      { name: '失信企业管理' }
    ]
  }
},
  {
    path: R.QLostLetterView,
    name: 'LostLetterView',
    component: LostLetterView,
    meta: {
      auth: true,
      func: '',
      keepAlive: false,
      bread: [
        // { name: '首页', url: R.kIndex },
        { name: '企业信息查询', },
        { name: '信用信息管理', url: R.QLostLetter},
        { name: '历史记录' }
      ]
    }
  }]

// 加入业务系统总路由
const BusinessRouter = HomeRouter.concat(IndustryRouter).concat(ForeignTraderRouter).concat(LostLetterRouter)
// ------------------------------ 业 务 系 统End ------------------------------
const LoginRouter = [{
  path: R.kLogin,
  name: 'Login',
  component: Login,
  meta: {
    auth: false,
    Login: true
  }
}]

const MainRouter = [{
  path: R.kIndex,
  name: 'Main',
  component: Main,
  redirect: R.kIndexMessage,
  meta: {
    Login: true,
    auth: true
  },
  children: SysRouter.concat(BusinessRouter)
}]

// ------------------------------ 用 户 服 务End ------------------------------

Vue.use(Router)

const router = new Router({
  routes: LoginRouter.concat(MainRouter)
})

router.beforeEach((to, from, next) => {
  // if (to.meta.auth) {
  //   if (!UserService.hasUserSession()) {
  //     return next(R.kLogin)
  //   }
  //   if (to.meta.func && !UserService.hasFunc(to.meta.func)) {
  //     return next(R.kIndex)
  //   }
  // }
  return next()
})

export default router
