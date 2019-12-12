import http from '@/utils/http'
import * as C from '@/utils/constants'

export default {
  addRole (role) {
    return http.postJSON(C.API_HEAD + '/Role/create.action', role)
  },

  editRole (role) {
    return http.postJSON(C.API_HEAD + '/Role/update.action', role)
  },

  delRole (role) {
    return http.postJSON(C.API_HEAD + '/Role/delete.action', role)
  },

  findByUserId (userId) {
    return http.post(C.API_HEAD + '/Role/findRoleByUserId.action', userId)
  },

  openRole (role) {
    return http.postJSON(C.API_HEAD + '/Role/open.action', role)
  },

  closeRole (role) {
    return http.postJSON(C.API_HEAD + '/Role/close.action', role)
  },

  findById (roleId) {
    return http.post(C.API_HEAD + '/Role/findById.action', {roleId})
  },

  findMenuAndFunc (roleId) {
    return http.post(C.API_HEAD + '/UwConfigController/findMenuAndFuncByRoleId.action', roleId)
  },

  updateMenuAndFunc (roleId, menus) {
    return http.postJSON(C.API_HEAD + '/UwConfigController/updateMenuAndFunc.action', { roleId, menus })
  },

  search (params) {
    return http.post(C.API_HEAD + '/Role/search.action', params)
  },
  searchAll () {
    return http.post(C.API_HEAD + '/Role/searchAll.action')
  }
}
