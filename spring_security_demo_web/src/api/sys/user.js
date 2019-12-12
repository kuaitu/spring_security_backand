import http from '@/utils/http'
import * as C from '@/utils/constants'
import Util from '@/utils/util'

export default {
  findById (userId) {
    return http.post(C.API_HEAD + '/User/findUserById.action', {userId: userId})
  },
  openUser (item) {
    return http.postJSON(C.API_HEAD + '/User/open.action', item)
  },
  closeUser (item) {
    return http.postJSON(C.API_HEAD + '/User/close.action', item)
  },
  findAll (params) {
    return http.post(C.API_HEAD + '/User/searchV1.action', params)
  },
  update (item) {
    return http.postJSON(C.API_HEAD + '/User/update.action', item)
  },
  resetPwd (user) {
    let hashPwdFirst = Util.Encrypt('123456')
    let hashPwdSecond = Util.Encrypt(hashPwdFirst + user.userName)
    user.userPwd = hashPwdSecond
    return http.postJSON(C.API_HEAD + '/User/update.action', user)
  }
}
