import VueCookie from 'vue-cookie'
import userApi from '@/api/user/user'

const USER_KEY = 'CCI_USER'
let func = []
// let userInfo = {}

export default {
  loadUserFunc () {
    return new Promise((resolve, reject) => {
      userApi
        .getUserFunc(this.getUser().id)
        .then(resp => {
          if (resp.success) {
            func = resp.data
          }
          resolve()
        })
        .catch(() => {
          // reject()
        })
    })
  },
  hasFunc (funcName) {
    let f = func.find(item => {
      return funcName === item.itemId
    })
    return f
  },

  localLogin (user) {
    VueCookie.set(USER_KEY, JSON.stringify(user), { path: '' })
    // userInfo = Object.assign({}, user)
  },

  hasUserSession () {
    return VueCookie.get(USER_KEY)
    // return Object.keys(userInfo).length !== 0
  },

  getUser () {
    return VueCookie.get(USER_KEY) !== ''
      ? JSON.parse(VueCookie.get(USER_KEY))
      : ''
    // return Object.assign({}, userInfo)
  },

  localLogout () {
    VueCookie.set(USER_KEY, '')
    VueCookie.delete(USER_KEY, { path: '' })
    // userInfo = {}
  },

  // 保存用户自定义列cookie
  setTableColumns (tableName, columns) {
    let user = this.getUser()
    window.localStorage.setItem(
      user.userId + '-' + tableName,
      JSON.stringify(columns)
    )
  },

  getTableColumns (tableName) {
    let user = this.getUser()
    return window.localStorage.getItem(user.userId + '-' + tableName) !== ''
      ? JSON.parse(window.localStorage.getItem(user.userId + '-' + tableName))
      : ''
  },

  // 保存用户主题
  setUserTheme (theme) {
    let user = this.getUser()
    window.localStorage.setItem(user.userId + '-theme', theme)
  },
  getUserTheme () {
    let user = this.getUser()
    return window.localStorage.getItem(user.userId + '-theme') !== ''
      ? window.localStorage.getItem(user.userId + '-theme')
      : ''
  }
}
