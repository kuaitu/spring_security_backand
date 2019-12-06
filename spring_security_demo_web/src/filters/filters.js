import Vue from 'vue'
import sysDictService from '@/service/SysDict'

Vue.filter('fEnable', val => {
  return (
    {
      '0': '已关闭',
      '1': '已开启'
    }[val] || '未知状态'
  )
})

Vue.filter('fIsOpen', val => {
  return sysDictService.translate('COMMON_STATUS', val)
})

Vue.filter('fBoolean', val => {
  return sysDictService.translate('boolean', val)
})

Vue.filter('prettyJSON', val => {
  try {
    val = JSON.stringify(JSON.parse(val), null, 2)
  } catch (e) {}
  return val
})

Vue.filter('fSupervisionRange', val => {
  return sysDictService.translate('SUPERVISION_RANGE', val) || ''
})

Vue.filter('fTodoStatus', val => {
  return sysDictService.translate('TODO_STATUS', val) || ''
})
