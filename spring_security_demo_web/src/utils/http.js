import Vue from 'vue'
import * as C from './constants'
import UserService from '@/service/UserService'
import router from '@/router'
import * as R from '@/router/router-types'
import {Modal} from 'iview'

export default {
  req (method, url, params, options, okCb, errCb) {
    return new Promise((resolve, reject) => {
      var promise = null
      if (method === 'GET') {
        promise = Vue.http.get(url, { params })
      } else if (method === 'POST' || method === 'POSTJSON') {
        promise = Vue.http.post(url, params, options)
      }
      promise.then(
        resp => {
          this.ssoLoginResp(resp)
          if (resp.headers.map.sessionstatus) {
            if (resp.headers.map.sessionstatus[0] === 'false') {
              // session超时
              UserService.localLogout()
              Vue.$indicator.close()
              router.push({ path: R.kLogin })
            } else {
              if (errCb) {
                errCb(C.ERR_MSG_AUTH, C.ERR_CODE_AUTH)
              }
              reject(C.ERR_MSG_AUTH, C.ERR_CODE_AUTH)
            }
          } else {
            if (okCb) {
              okCb(resp.data)
            }
            resolve(resp.data)
          }
        },
        resp => {
          if (errCb) {
            errCb(C.ERR_MSG_NETWORK, C.ERR_CODE_NETWORK)
          }
          reject(C.ERR_MSG_NETWORK, C.ERR_CODE_NETWORK)
        }
      )
    })
  },

  postLoad (url, params, okCb, errCb) {
    return this.req(
      'POST',
      url,
      params,
      {
        emulateJSON: true,
        responseType: 'arraybuffer',
        headers: {
          'Content-Type': 'application/json; charset=UTF-8'
        }
      },
      okCb,
      errCb
    )
  },

  get (url, params, okCb, errCb) {
    return this.req(
      'GET',
      url,
      params,
      {
        emulateJSON: true,
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        }
      },
      okCb,
      errCb
    )
  },

  post (url, params, okCb, errCb) {
    return this.req(
      'POST',
      url,
      params,
      {
        emulateJSON: true,
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        }
      },
      okCb,
      errCb
    )
  },

  postJSON (url, params, okCb, errCb) {
    return this.req(
      'POSTJSON',
      url,
      params,
      {
        emulateJSON: false,
        headers: {
          'Content-Type': 'application/json'
        }
      },
      okCb,
      errCb
    )
  },
  logOut (url) {
    Vue.http.post(url)
  },

   ssoLoginResp (resp) {
    let SSOSwitch = null
    if (resp.headers.map['ssoswitch']) {
      SSOSwitch = resp.headers.map['ssoswitch'][0]
    }
    if (SSOSwitch !== 'true') {
      return
    }

    let sessionstatus = null
    if (resp.headers.map['sessionstatus']) {
      sessionstatus = resp.headers.map['sessionstatus'][0]
    }

    if (SSOSwitch && sessionstatus && sessionstatus === 'false') {
      let noLogin = ''
      if (resp.headers.map['nologin']) {
        noLogin = resp.headers.map['nologin'][0]
      }

      if (noLogin && noLogin !== '') {
        Vue.$indicator.close()
        Modal.error({
          title: '自动登录失败',
          content: decodeURIComponent(noLogin),
          onOk: () => {
            UserService.localLogout()
            window.location.href = resp.headers.map['redirecturl'][0]
          }
        })
      } else {
        UserService.localLogout()
        window.location.href = resp.headers.map['redirecturl'][0]
      }
      throw new Error()
    }
  }
}
