/*eslint-disable*/
import Vue from 'vue'
import Router from 'vue-router'
import * as R from '@/router/router-types'
// 系统管理 -> 菜单管理
import MenuManager from '@/pages/sys/menu/MenuManager'
import MenuManagerChild from '@/pages/sys/menu/child/MenuManager'
import MenuManagerFunc from '@/pages/sys/menu/child/func/FuncManager'
// 系统管理 -> 用户
import UserManager from '@/pages/sys/user/Manager'
// 系统管理 -> 日志
import LogManager from '@/pages/sys/log/Manager'
// 系统管理 -> 角色
import UwRoleManager from '@/pages/sys/role/RoleManager'
// ------------------------------ 用 户 服 务Start------------------------------
// 登录、注册、忘记密码
import Login from '@/pages/user/Login'
import Register from '@/pages/user/Register'
import ForgetPassword from '@/pages/user/ForgetPassword'
// 主界面
import Main from '@/pages/Home'
// import userService from '@/service/UserService'
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


const UserRouter = [{
  path: R.kUserManager,
  name: 'UserManager',
  component: UserManager,
  meta: {
    auth: true,
    keepAlive: false,
    func: 'adminUser_view',
    bread: [{name: '系统管理'}, {name: '用户管理'}]
  }
}]


const LogRouter = [{
  path: R.kLogManager,
  name: 'LogManager',
  component: LogManager,
  meta: {
    auth: true,
    keepAlive: false,
    func: 'adminUser_view',
    bread: [{name: '系统管理'}, {name: '日志管理'}]
  }
}]


const UwRoleRouter = [{
  path: R.kUwRole,
  name: 'RoleManager',
  component: UwRoleManager,
  meta: {
    auth: true,
    keepAlive: false,
    func: 'user_manage_view ',
    bread: [
      // { name: '首页', url: R.kIndex },
      { name: '用户管理' },
      { name: '角色列表' }
    ]
  }
}]

// 加入系统服务总路由
const SysRouter = [].concat(MenuRouter).concat(UserRouter).concat(UwRoleRouter).concat(LogRouter)

// ------------------------------ 系 统 服 务End ------------------------------
const IndexRouter = [{
  path: R.kLogin,
  name: 'Login',
  component: Login,
  meta: {
    auth: false,
    Login: true
  }
},{
  path: R.kRegister,
  name: 'Register',
  component: Register,
  meta: {
    auth: false,
    Login: true
  }
},{
  path: R.kForgetPassword,
  name: 'ForgetPassword',
  component: ForgetPassword,
  meta: {
    auth: false,
    Login: true
  }
}]

const MainRouter = [{
  path: R.kIndex,
  name: 'Main',
  component: Main,
  // redirect: R.kIndexMessage,
  meta: {
    Login: true,
    auth: true
  },
  children: SysRouter
}]

// ------------------------------ 用 户 服 务End ------------------------------

Vue.use(Router)

const router = new Router({
  routes: IndexRouter.concat(MainRouter)
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
