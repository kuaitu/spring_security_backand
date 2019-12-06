import http from '@/utils/http'
import * as C from '@/utils/constants'

export default {
  search (params) {
    return http.post(C.API_HEAD + '/OrganizationMember/search.action', params)
  },
  searchV1 (params) {
    return http.post(C.API_HEAD + '/OrganizationMember/searchV1.action', params)
  },
  add (params) {
    return http.postJSON(
      C.API_HEAD + '/OrganizationMember/create.action',
      params
    )
  },
  edit (params) {
    return http.postJSON(
      C.API_HEAD + '/OrganizationMember/update.action',
      params
    )
  },
  findById (memberId) {
    return http.post(C.API_HEAD + '/OrganizationMember/findById.action', {
      memberId
    })
  },
  audit (params) {
    return http.postJSON(
      C.API_HEAD + '/OrganizationMember/audit.action',
      params
    )
  },
  getMemberUnderLoginUserOrganization () {
    return http.post(
      C.API_HEAD +
      '/OrganizationMember/getMemberUnderLoginUserOrganization.action'
    )
  },
  findByMemberId (memberId) {
    return http.post(C.API_HEAD + '/OrganizationMember/findByMemberId.action', {
      memberId
    })
  },
  reinstatement (params) {
    return http.post(
      C.API_HEAD + '/OrganizationMember/reinstatement.action',
      params
    )
  },
  dimission (params) {
    return http.post(
      C.API_HEAD + '/OrganizationMember/dimission.action',
      params
    )
  }
}
