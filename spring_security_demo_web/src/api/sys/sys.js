import http from '@/utils/http'
import * as C from '@/utils/constants'

export default {
  findLicensedMenuEasyUI (params) {
    return http.post(C.API_HEAD + '/Menu/findLicensedMenuEasyUI.action', params)
  },

  findSysDictItem (okCb, errCb) {
    return http.post(
      C.API_HEAD + '/sys/dict-item/search.action',
      {
        page: 1,
        rows: -1
      },
      okCb,
      errCb
    )
  }
}
