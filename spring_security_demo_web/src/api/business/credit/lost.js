import http from '@/utils/http'
import * as C from '@/utils/constants'

export default {
  search (params) {
    return http.post(C.API_HEAD + '/Rating/search.action', params)
  },
  add (params) {
    return http.postJSON(
      C.API_HEAD + '/Rating/create.action',
      params
    )
  },
  edit (params) {
    return http.postJSON(
      C.API_HEAD + '/Rating/update.action',
      params
    )
  },
  findById (memberId) {
    return http.post(C.API_HEAD + '/Rating/findById.action', {
      memberId
    })
  }
}
