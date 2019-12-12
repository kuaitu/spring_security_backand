import Util from '@/utils/util'

export default {
  formatFormData (o) {
    var p = JSON.parse(JSON.stringify(o))
    for (var attr in p) {
      if (o[attr] === null || o[attr] === undefined || o[attr] === '' || o[attr] === '[Query-All-Status-Option]') {
        delete p[attr] // 删除空属性
      }
      if (Object.prototype.toString.call(o[attr]) === '[object Date]') {
        p[attr] = Util.formatDate(o[attr], 'yyyy-MM-dd') + ' 00:00:00' // 将单个日期默认格式成 yyyy-MM-dd 00:00:00格式
      }
      // else if (Object.prototype.toString.call(o[attr]) === '[object Object]') { // 子项为对象
      //   removeNullItem(o[attr]);
      //   if (isEmptyObj(o[attr])) delete o[attr];
      // } else if (Object.prototype.toString.call(o[attr]) === '[object Array]') {  // 子项为数组
      //
      // }
    }
    return p
  }
}
