import http from '@/utils/http'
import * as C from '@/utils/constants'

export default {
  getSysDictItem (okCb, errCb) {
    return http.post(C.API_HEAD + '/Open/getSysDictItem.action',
      {
        page: 1,
        rows: -1
      },
      okCb,
      errCb)
  }
}
