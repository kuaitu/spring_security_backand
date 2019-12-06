import Sys from '@/api/sys/sys'

let dict = []

export default {
  load () {
    return new Promise((resolve, reject) => {
      Sys.findSysDictItem().then((page) => {
        dict = page.data
        resolve()
      }).catch(() => {
        reject(new Error())
      })
    })
  },

  translate (dictCode, dictItemCode) {
    if (dict.length > 0) {
      let found = dict.find(item => {
        return item.dictCode === dictCode && item.dictItemCode === dictItemCode
      })
      return found ? found.dictItemValue || '?' : '?'
    } else {
      return dictItemCode
    }
  },

  findDict (dictCode) {
    let found = dict.filter(item => {
      return item.dictCode === dictCode
    })
    return found
  }
}
