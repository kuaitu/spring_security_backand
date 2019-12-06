import areaData from '@/components/alCascader/pcaa.js'
import CryptoJS from 'crypto-js'

if (!areaData['410000']['410003']) {
  areaData['410000']['410003'] = '郑州市航空港区'
}
if (!areaData['410900']['410991']) {
  areaData['410900']['410991'] = '濮阳市开发区'
}
// 身份证校验规则
var CheckIdCardRules = {
  // Wi 加权因子 Xi 余数0~10对应的校验码 Pi省份代码
  Wi: [
    '7',
    '9',
    '10',
    '5',
    '8',
    '4',
    '2',
    '1',
    '6',
    '3',
    '7',
    '9',
    '10',
    '5',
    '8',
    '4',
    '2'
  ],
  Xi: ['1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'],
  Pi: [
    '11',
    '12',
    '13',
    '14',
    '15',
    '21',
    '22',
    '23',
    '31',
    '32',
    '33',
    '34',
    '35',
    '36',
    '37',
    '41',
    '42',
    '43',
    '44',
    '45',
    '46',
    '50',
    '51',
    '52',
    '53',
    '54',
    '61',
    '62',
    '63',
    '64',
    '65',
    '71',
    '81',
    '82',
    '91'
  ],

  // 检验18位身份证号码出生日期是否有效
  // parseFloat过滤前导零，年份必需大于等于1900且小于等于当前年份，用Date()对象判断日期是否有效。
  brithday18 (sIdCard) {
    var mixYear = 1900
    var year = parseFloat(sIdCard.substr(6, 4))
    var month = parseFloat(sIdCard.substr(10, 2))
    var day = parseFloat(sIdCard.substr(12, 2))
    var checkDay = new Date(year, month - 1, day)
    var nowDay = new Date()
    if (
      mixYear <= year &&
      year <= nowDay.getFullYear() &&
      month === checkDay.getMonth() + 1 &&
      day === checkDay.getDate()
    ) {
      return true
    }
  },

  // 检验15位身份证号码出生日期是否有效
  brithday15 (sIdCard) {
    var year = parseFloat(sIdCard.substr(6, 2))
    var month = parseFloat(sIdCard.substr(8, 2))
    var day = parseFloat(sIdCard.substr(10, 2))
    var checkDay = new Date(year, month - 1, day)
    if (month === checkDay.getMonth() + 1 && day === checkDay.getDate()) {
      return true
    }
    return false
  },

  // 检验校验码是否有效
  validate (sIdCard) {
    var aIdCard = sIdCard.split('')
    var sum = 0
    for (var i = 0; i < CheckIdCardRules.Wi.length; i++) {
      sum += CheckIdCardRules.Wi[i] * aIdCard[i] // 线性加权求和
    }
    var index = sum % 11 // 求模，可能为0~10,可求对应的校验码是否于身份证的校验码匹配
    if (CheckIdCardRules.Xi[index] === aIdCard[17].toUpperCase()) {
      return true
    }
  },

  // 检验输入的省份编码是否有效
  province (sIdCard) {
    var p2 = sIdCard.substr(0, 2)
    for (var i = 0; i < CheckIdCardRules.Pi.length; i++) {
      if (CheckIdCardRules.Pi[i] === p2) {
        return true
      }
    }
  }
}

export default {
  formatDate (date, fmt) {
    if (typeof date === 'string') {
      date = new Date(
        date
          .replace(/-/g, '/')
          .replace(/T|Z/g, ' ')
          .trim()
      )
    } else {
      date = new Date(date)
    }
    var o = {
      'M+': date.getMonth() + 1,
      'd+': date.getDate(),
      'h+': date.getHours(),
      'm+': date.getMinutes(),
      's+': date.getSeconds(),
      'q+': Math.floor((date.getMonth() + 3) / 3),
      S: date.getMilliseconds()
    }
    if (/(y+)/.test(fmt)) {
      fmt = fmt.replace(
        RegExp.$1,
        (date.getFullYear() + '').substr(4 - RegExp.$1.length)
      )
    }
    for (var k in o) {
      if (new RegExp('(' + k + ')').test(fmt)) {
        fmt = fmt.replace(
          RegExp.$1,
          RegExp.$1.length === 1
            ? o[k]
            : ('00' + o[k]).substr(('' + o[k]).length)
        )
      }
    }
    return fmt
  },

  formatBeginDate (date) {
    return this.formatDate(date, 'yyyy-MM-dd') + ' 00:00:00'
  },

  formatEndDate (date) {
    if (typeof date === 'string') {
      date = new Date(
        date
          .replace(/-/g, '/')
          .replace(/T|Z/g, ' ')
          .trim()
      )
    } else {
      date = new Date(date)
    }
    // return this.formatDate(date, 'yyyy-MM-dd') + ' 23:59:59'
    return (
      this.formatDate(
        new Date(date.setDate(date.getDate() + 1)),
        'yyyy-MM-dd'
      ) + ' 00:00:00'
    )
  },

  timeStampToDate (timestampStr) {
    let timestamp = new Date(timestampStr)
    let commonTime = ''
    if (timestampStr === null || timestampStr === '') {
      commonTime = '-'
    } else {
      let month =
        timestamp.getMonth() + 1 < 10
          ? '0' + (timestamp.getMonth() + 1)
          : timestamp.getMonth() + 1
      let day =
        timestamp.getDate() < 10
          ? '0' + timestamp.getDate()
          : timestamp.getDate()
      let hours =
        timestamp.getHours() < 10
          ? '0' + timestamp.getHours()
          : timestamp.getHours()
      let min =
        timestamp.getMinutes() < 10
          ? '0' + timestamp.getMinutes()
          : timestamp.getMinutes()
      let second =
        timestamp.getSeconds() < 10
          ? '0' + timestamp.getSeconds()
          : timestamp.getSeconds()
      commonTime =
        timestamp.getFullYear() +
        '-' +
        month +
        '-' +
        day +
        ' ' +
        hours +
        ':' +
        min +
        ':' +
        second
    }
    return commonTime
  },

  eventUp (events) {
    let res = {}
    events.map(e => {
      res[e] = function mappedEvent (...args) {
        this.$emit(e, ...args)
      }
    })
    return res
  },

  getDateDiff (dateTimeStamp) {
    dateTimeStamp = new Date(dateTimeStamp)
    var minute = 1000 * 60
    var hour = minute * 60
    var day = hour * 24
    var month = day * 30
    var now = new Date().getTime()
    var diffValue = now - dateTimeStamp
    if (diffValue < 0) {
      return
    }
    var monthC = diffValue / month
    var weekC = diffValue / (7 * day)
    var dayC = diffValue / day
    var hourC = diffValue / hour
    var minC = diffValue / minute
    var result = ''
    if (monthC >= 1) {
      result = '' + parseInt(monthC) + '月前'
    } else if (weekC >= 1) {
      result = '' + parseInt(weekC) + '周前'
    } else if (dayC >= 1) {
      result = '' + parseInt(dayC) + '天前'
    } else if (hourC >= 1) {
      result = '' + parseInt(hourC) + '小时前'
    } else if (minC >= 1) {
      result = '' + parseInt(minC) + '分钟前'
    } else {
      result = '刚刚'
    }
    return result
  },

  getTheDayBeforeDate (date) {
    return new Date(date.setDate(date.getDate() - 1))
  },

  getBasePath () {
    return (
      window.location.protocol +
      '//' +
      window.location.host +
      window.location.pathname
    )
  },

  sizeConversion (limit) {
    var size = ''
    if (limit < 0.1 * 1024) {
      size = limit.toFixed(2) + 'B'
    } else if (limit < 0.1 * 1024 * 1024) {
      size = (limit / 1024).toFixed(2) + 'KB'
    } else if (limit < 0.1 * 1024 * 1024 * 1024) {
      size = (limit / (1024 * 1024)).toFixed(2) + 'MB'
    } else {
      size = (limit / (1024 * 1024 * 1024)).toFixed(2) + 'GB'
    }
    var sizestr = size + ''
    var len = sizestr.indexOf('.') //eslint-disable-line
    var dec = sizestr.substr(len + 1, 2)
    if (dec === '00') {
      return sizestr.substring(0, len) + sizestr.substr(len + 3, 2)
    }
    return sizestr
  },

  // 身份证号合法性验证
  // 支持15位和18位身份证号
  // 支持地址编码、出生日期、校验位验证
  IdentityCodeValid1 (code) {
    let city = {
      11: '北京',
      12: '天津',
      13: '河北',
      14: '山西',
      15: '内蒙古',
      21: '辽宁',
      22: '吉林',
      23: '黑龙江 ',
      31: '上海',
      32: '江苏',
      33: '浙江',
      34: '安徽',
      35: '福建',
      36: '江西',
      37: '山东',
      41: '河南',
      42: '湖北 ',
      43: '湖南',
      44: '广东',
      45: '广西',
      46: '海南',
      50: '重庆',
      51: '四川',
      52: '贵州',
      53: '云南',
      54: '西藏 ',
      61: '陕西',
      62: '甘肃',
      63: '青海',
      64: '宁夏',
      65: '新疆',
      71: '台湾',
      81: '香港',
      82: '澳门',
      91: '国外 '
    }
    let message = null
    let success = true
    // 验证身份证格式（6个地区编码，8位出生日期，3位顺序号，1位校验位）
    if (
      !code ||
      !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(
        code
      )
    ) {
      message = '身份证号格式错误'
      success = false
    } else if (!city[code.substr(0, 2)]) {
      message = '身份证地址编码错误'
      success = false
    } else {
      // 18位身份证需要验证最后一位校验位
      if (code.length === 18) {
        code = code.split('')
        // ∑(ai×Wi)(mod 11)
        // 加权因子
        let factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2]
        // 校验位
        let parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2]
        let sum = 0
        let ai = 0
        let wi = 0
        for (let i = 0; i < 17; i++) {
          ai = code[i]
          wi = factor[i]
          sum += ai * wi
        }
        // let last = parity[sum % 11]
        if (parity[sum % 11] !== code[17]) {
          message = '身份证校验位错误'
          success = false
        }
      }
    }
    let param = {}
    param.success = success
    param.message = message
    return param
  },

  IdentityCodeValid (val) {
    var sIdCard = val.replace(/^\s+|\s+$/g, '') // 去除字符串的前后空格，允许用户不小心输入前后空格
    if (sIdCard.match(/^\d{14,17}(\d|X)$/gi) === null) {
      // 判断是否全为18或15位数字，最后一位可以是大小写字母X
      return { success: false, message: '身份证号码校验错误' }
    } else if (sIdCard.length === 18) {
      if (
        CheckIdCardRules.province(sIdCard) &&
        CheckIdCardRules.brithday18(sIdCard) &&
        CheckIdCardRules.validate(sIdCard)
      ) {
        return { success: true, message: '' }
      } else {
        return { success: false, message: '身份证号码校验错误' }
      }
    } else if (sIdCard.length === 15) {
      if (
        CheckIdCardRules.province(sIdCard) &&
        CheckIdCardRules.brithday15(sIdCard)
      ) {
        return { success: true, message: '' }
      } else {
        return { success: false, message: '身份证号码校验错误' }
      }
    } else {
      return { success: false, message: '身份证号码校验错误' }
    }
  },

  // 验证手机号码
  CellphoneValid (cellphone) {
    if (cellphone !== '' && cellphone !== null) {
      var myreg = /^[0-9]{11}$/ // 验证是否是11位数字
      if (myreg.test(cellphone)) {
        return true
      }
    }
    return false
  },

  // 验证电话号码
  PhoneValid (phone) {
    if (phone !== '' && phone !== null) {
      var myreg = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/
      if (myreg.test(phone)) {
        return true
      }
    }
    return false
  },
  // PBKDF2加密算法
  Encrypt (word, key = 'hnykx123456') {
    let salt = CryptoJS.enc.Utf8.parse(key)
    let encrypted = CryptoJS.PBKDF2(word, salt, {
      keySize: 256 / 32,
      iterations: 10000,
      hasher: CryptoJS.algo.SHA256
    })
    return encrypted.toString()
  },

  // @description: 根据编码获取市级名称
  //  @param {type} areaCode 区域编码
  //  @return: 市级名称

  getCityNameByCode (areaCode) {
    let i = 0
    let areaName = ''
    let select = this.getAreaCodeArr(areaCode)
    while (select[i] && i <= 2) {
      let name = ''
      if (i === 0) {
        name = areaData['86'][select[i]]
      } else {
        name = areaData[select[i - 1]][select[i]]
        areaName = name
      }
      i++
    }
    return areaName
  },
  /**
   * 返回省市县的编码数组，如：[110000, 110100, 110101],代表北京->北京市->东城区
   * 如果是市级编码则只返回[110000, 110100]
   * @param {*} areaCode 地区编码
   */
  getAreaCodeArr (areaCode) {
    areaCode = typeof areaCode === 'number' ? areaCode.toString() : areaCode
    let select = []
    let provinceCode = areaCode.substr(0, 2) + '0000'
    select.push(provinceCode)
    for (const cityCode in areaData[provinceCode]) {
      if (areaCode === cityCode) {
        // 该编码为市级县
        select.push(cityCode)
      }
    }
    if (select.length <= 1) {
      //  该编码为县级单位
      select.push(areaCode.substr(0, 4) + '00')
      select.push(areaCode)
    }
    return select
  }
}
