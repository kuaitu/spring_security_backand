import http from '@/utils/http'
import * as C from '@/utils/constants'
import Util from '@/utils/util'

export default {
  login (userName, password, verificationCode, okCb, errCb) {
    return new Promise(resolve => {
      // 提交前的三次加密操作
      setTimeout(() => {
        let hashPwdFirst = Util.Encrypt(password)
        let hashPwdSecond = Util.Encrypt(hashPwdFirst + userName)
        // console.log(hashPwdSecond)
        let hashPwdThird = Util.Encrypt(
          hashPwdSecond + verificationCode.toLowerCase()
        )
        resolve(
          http.post(C.API_HEAD + '/User/login.action', {
            username: userName,
            password: password,
            verificationCode
          })
        )
      }, 1)
    })
  },

  create (params) {
    return new Promise(resolve => {
      // 提交前的三次加密操作
      setTimeout(() => {
        let user = params.user
        let hashPwdFirst = Util.Encrypt(user.userPwd)
        let hashPwdSecond = Util.Encrypt(hashPwdFirst + user.userName)
        params.user.userPwd = hashPwdSecond
        params.user = JSON.stringify(params.user)
        resolve(
          http.post(C.API_HEAD + '/User/create.action', params)
        )
      }, 1)
    })
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
  },

  getEmailCode (userName,email,code) {
    return http.post(C.API_HEAD + '/Report/sendMail.action',{userName: userName,code: code,email: email})
  },
  backPassword (userName,email,eCode,newPassword,newPasswordConfirm) {
    return new Promise(resolve => {
      // 提交前的加密操作
      setTimeout(() => {
        let hashPwdFirst = Util.Encrypt(newPassword)
        let hashPwdSecond = Util.Encrypt(hashPwdFirst + userName)
        resolve(
          http.post(C.API_HEAD + '/User/backPassword.action',{email: email,eCode: eCode,newPassword:hashPwdSecond,newPasswordConfirm:newPasswordConfirm})
        )
      }, 1)
    })
  }
}
