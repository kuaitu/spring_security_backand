import http from '@/utils/http'
import * as C from '@/utils/constants'

export default {
  search (params) {
    return http.post(C.API_HEAD + '/Menu/search.action', params)
  },
  create (params) {
    return http.postJSON(C.API_HEAD + '/Menu/create.action', params)
  },
  update (params) {
    return http.postJSON(C.API_HEAD + '/Menu/update.action', params)
  },
  changeSort (params) {
    return http.postJSON(C.API_HEAD + '/Menu/changeSort.action', params)
  },
  findAll () {
    return http.get(C.API_HEAD + '/Menu/findAll.action')
  },
  findAllChild (menuId) {
    return http.get(C.API_HEAD + '/Menu/findAllChild.action', menuId)
  },
  findById (menuId) {
    return http.get(C.API_HEAD + '/Menu/findById.action', menuId)
  },
  findFuncByMenuId (menuId) {
    return http.get(C.API_HEAD + '/Menu/findFuncByMenuId.action', menuId)
  },
  createFunc (params) {
    return http.postJSON(C.API_HEAD + '/Jurisdiction/create.action', params)
  },
  updateFunc (params) {
    return http.postJSON(C.API_HEAD + '/Jurisdiction/update.action', params)
  },
  findFuncById (id) {
    return http.get(C.API_HEAD + '/Jurisdiction/findById.action', id)
  }
}
