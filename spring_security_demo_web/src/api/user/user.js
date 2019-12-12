import http from '@/utils/http'
import * as C from '@/utils/constants'
import Util from '@/utils/util'

export default {
  getUserFunc (userId, okCb) {
    return http.post(
      C.API_HEAD + '/sys/permission/findByUserId.action',
      {
        userId
      },
      okCb
    )
  },

  changePwd (password, newPassword, newPasswordConfirm, userName) {
    return new Promise(resolve => {
      // 提交前的三次加密操作
      setTimeout(() => {
        let hashPwdFirst = Util.Encrypt(password)
        let hashPwdSecond = Util.Encrypt(hashPwdFirst + userName)

        let hashNewPwdFirst = Util.Encrypt(newPassword)
        let hashNewPwdSecond = Util.Encrypt(hashNewPwdFirst + userName)

        let hashNewPwdConfirmFirst = Util.Encrypt(newPasswordConfirm)
        let hashNewPwdConfirmSecond = Util.Encrypt(
          hashNewPwdConfirmFirst + userName
        )
        resolve(
          http.post(C.API_HEAD + '/User/updatePwd.action', {
            password: hashPwdSecond,
            newPassword: hashNewPwdSecond,
            newPasswordConfirm: hashNewPwdConfirmSecond
          })
        )
      }, 1)
    })
  },
  logout () {
    return http.post(C.API_HEAD + '/User/logout.action')
  }
}
