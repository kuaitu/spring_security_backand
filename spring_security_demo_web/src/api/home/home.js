import http from '@/utils/http'
import * as C from '@/utils/constants'

export default {
  getSumBaseData (params) {
    return http.post(C.API_HEAD + '/HomePageController/getSumBaseData.action', params)
  },
  getEntCreditList (params) {
    return http.post(C.API_HEAD + '/HomePageController/getEntCreditList.action', params)
  },
  getTotalDetail (params) {
    return http.post(C.API_HEAD + '/HomePageController/getTotalDetail.action', params)
  },
  getTotalData (params) {
    return http.post(C.API_HEAD + '/HomePageController/getTotalData.action', params)
  },
  add (params) {
    delete params.reserveTime
    return http.postJSON('/ReserveInfo/create.action', params)
  }
}
