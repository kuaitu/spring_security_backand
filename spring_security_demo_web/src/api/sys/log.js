import http from '@/utils/http'
import * as C from '@/utils/constants'

export default {
  search (params) {
    return http.post(C.API_HEAD + '/Log/search.action', params)
  },
  findById (userId) {
    return http.post(C.API_HEAD + '/User/findUserById.action', {userId: userId})
  },
  findAll (params) {
    return http.post(C.API_HEAD + '/User/searchV1.action', params)
  },
  update (item) {
    return http.postJSON(C.API_HEAD + '/User/update.action', item)
  }
}
