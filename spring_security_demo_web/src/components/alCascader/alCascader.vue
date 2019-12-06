<template>
  <div>
    <Cascader v-model="select"
              :data="data"
              :transfer="transfer"
              :load-data="loadData"
              :disabled="disabled"
              :size="size"
              :placeholder="placeholder"
              :render-format="renderFormat"
              :change-on-select="changeOnSelect"
              :clearable="clearable"
              :filterable="filterable"
              :trigger="trigger"
              @on-change="onChange"
              @on-visible-change="onVisibleChange" />
  </div>
</template>
<script>
  import areaData from './pcaa'
  // if (!areaData['410000']['410003']) { areaData['410000']['410003'] = '郑州市航空港区' }
if (!areaData['410900']['410991']) { areaData['410900']['410991'] = '濮阳市开发区' }
const levelShow = (level, code) => {
  if (level === 2) {
    return Object.keys(areaData['86']).indexOf(code) > -1
  } else if (level === 3) {
    return true
  }
}
export default {
  name: 'alCascader',
  props: {
    value: String,
    level: {
      type: [Number, String],
      default: 3,
      validator: val => {
        let levelArr = [0, 1, 2, 3]
        return levelArr.some(i => {
          return i === val
        })
      }
    },
    disabled: {
      type: Boolean,
      default: false
    },
    transfer: {
      type: Boolean,
      default: false
    },
    filterable: {
      type: Boolean,
      default: false
    },
    trigger: {
      type: String,
      default: 'click'
    },
    clearable: {
      type: Boolean,
      default: true
    },
    singleSelect: Boolean,
    defaultValue: String,
    changeOnSelect: Boolean,
    size: String,
    placeholder: {
      type: String,
      default: ''
    },
    renderFormat: {
      type: Function,
      default (label) {
        return label.join(' / ')
      }
    }
  },
  data () {
    return {
      data: [],
      // 当前的组件已被加载的数据code，例如：410000，河南省的省市县数据已全部被加载完成
      currentCode: null,
      // 当前组件是否已被触发，若已被触发则不执行value的监听事件
      visibleStatus: false,
      // 是否正在选择省市县，若正在选择则不执行value的监听事件
      changeStatus: false
    }
  },
  watch: {
    // 为了实现控制（解决点击查看或编辑时省市县组件代码翻译不正确问题）：
    // 1.不重复加载数据（同省数据不重复加载）
    //  1.1 实现一个状态的标记，记录当前已加载的省份数据,已加载的数据不重复加载
    value (val) {
      this.loadAlCascaderData(val)
    }
  },
  computed: {
    showLevel () {
      if (this.singleSelect) {
        return 0
      }
      return parseInt(this.level)
    },
    firstCode () {
      return this.defaultValue || '86'
    },
    select: {
      get () {
        if (this.value && !isNaN(parseInt(this.value))) {
          // 初始化区域数组
          let select = []
          let provinceCode = this.value.substr(0, 2) + '0000'
          select.push(provinceCode)
          for (const cityCode in areaData[provinceCode]) {
            if (this.value === cityCode) {
            // 该编码为市级县
              select.push(cityCode)
            }
          }
          if (select.length <= 1) {
          //  该编码为县级单位
            select.push(this.value.substr(0, 4) + '00')
            select.push(this.value)
          }
          // 选择level的逻辑代码
          if (this.firstCode !== '86') {
            let t = 0
            for (let i = 0; i < select.length; i++) {
              if (select[i] === this.firstCode) {
                t = i
              }
            }
            // slice 函数end不包括end的元素，所以要加2
            select = select.slice(t + 1, t + this.showLevel + 2)
          } else {
            let t = 0
            select = select.slice(0, t + this.showLevel + 2)
          }
          return select
        } else {
          return []
        }
      },
      set (newSelect) {
        let res = this.procesValue(newSelect)
        this.$emit('input', res)
        this.$emit('on-change', res)
      }
    }
  },
  methods: {
    init () {
      let proData = []
      for (const p in areaData[this.firstCode]) {
        let proitem = {
          value: p,
          label: areaData[this.firstCode][p],
          children: []
        }
        if (this.showLevel > 0 && areaData[p] && Object.keys(areaData[p])[0].length === 6) {
          proitem.loading = false
        }
        if (proitem.value !== '410003') {
          proData.push(proitem)
        }
      }
      if (this.firstCode === '410000') {
        proData.push({
          value: '410003',
          label: '郑州市航空港区'
        })
      }
      this.data = proData
    },
    loadAlCascaderData (val) {
      // 避免点击选择省市县时执行这段代码
      if (this.visibleStatus === true) {
        return
      }
      // 避免点击完成后visibleStatus = false 时还执行这段代码可以使用onChange
      if (this.changeStatus === true) {
        this.changeStatus = false
        return
      }
      // 功能：判断与val相关的组件数据是否已被加载过
      // 1.判断val相关的code是否与当前已加载的数据code相关（currentCode)
      // 正则表达式：将currentCode去除末尾的0，如410000->41、410100->4101、410101->410101
      if (this.currentCode && val.startsWith(this.currentCode.replace(/(0+)$/g, ''))) {
        // 数据已加载，返回不重复加载数据
        return
      }
      // 组件数据
      let proData = []
      // val对应的省份code
      let provinceCode = null
      // 是否全量加载数据，如果firstCode不为86则数据全量加载
      let loadAllData = false
      if (this.firstCode === '86') {
        provinceCode = this.value.substr(0, 2) + '0000'
      } else {
        loadAllData = true
      }
      // 加载组件数据，且不加载街道数据，注：街道的code超过6位
      if (areaData[this.firstCode] && Object.keys(areaData[this.firstCode])[0].length === 6) {
        for (const p in areaData[this.firstCode]) {
          let proitem = {
            value: p,
            label: areaData[this.firstCode][p],
            children: []
          }
          if (this.showLevel > 0 && (loadAllData || p === provinceCode)) {
            this.currentCode = p
            this.loadChildrenData(proitem, 1)
          }
          // 判断children还可向下展开，且不加载街道数据，注：街道的code超过6位
          if (this.showLevel > 0 && areaData[p] && Object.keys(areaData[p])[0].length === 6) {
            proitem.loading = false
          }
          // 要将郑州市航空港区放在最后，所以在这里不加入proData中
          if (proitem.value !== '410003') {
            proData.push(proitem)
          }
        }
        // 将郑州市航空港区放在最后展示
        if (this.firstCode === '410000') {
          proData.push({
            value: '410003',
            label: '郑州市航空港区'
          })
        }
        this.data = proData
      }
    },
    loadChildrenData (item, deep) {
      let proData = []
      // 有子项时加载子项的children,且不加载街道数据，注：街道的code超过6位
      if (areaData[item.value] && Object.keys(areaData[item.value])[0].length === 6) {
        for (const p in areaData[item.value]) {
          let proitem = {
            value: p,
            label: areaData[item.value][p],
            children: []
          }
          // 要将郑州市航空港区放在最后，所以在这里不加入proData中
          if (proitem.value !== '410003') {
            proData.push(proitem)
          }
          if (this.showLevel > deep) {
            this.loadChildrenData(proitem, deep + 1)
          }
        }
        // 将郑州市航空港区放在最后展示
        if (item.value === '410000') {
          proData.push({
            value: '410003',
            label: '郑州市航空港区'
          })
        }
        item.children = proData
      }
    },
    onChange (value, selectedData) {
      this.changeStatus = true
      this.$emit('on-change', value, JSON.parse(JSON.stringify(selectedData)))
    },
    onVisibleChange (type) {
      this.visibleStatus = type
      this.$emit('on-visible-change', type)
    },
    loadData (item, callback) {
      item.loading = true
      let childData = []
      let childs = areaData[item.value]
      for (const c in childs) {
        if (c.length === 6) {
          let childitem = {
            value: c,
            label: areaData[item.value][c],
            children: []
          }
          if (areaData[childitem.value] && levelShow(this.showLevel, item.value) && Object.keys(areaData[childitem.value])[0].length === 6) {
            childitem.loading = false
          }
          if (childitem.value !== '410003') {
            childData.push(childitem)
          }
        }
      }
      if (item.value === '410000') {
        childData.push({
          value: '410003',
          label: '郑州市航空港区'
        })
      }
      item.children = childData
      item.loading = false
      callback()
    },
    procesValue (arr) {
      // 去除中文作为编码区域的功能
      if (arr && arr.length > 0) {
        return arr[arr.length - 1]
      } else {
        return ''
      }
      // let i = 0
      // let res = ''
      // while (arr[i]) {
      //   let name = ''
      //   if (i === 0) {
      //     name = areaData[this.firstCode][arr[i]]
      //   } else {
      //     name = areaData[arr[i - 1]][arr[i]]
      //   }
      //   if (this.dataType === 'all') {
      //     res = arr[i]
      //   } else if (this.dataType === 'name') {
      //     res = name
      //   } else {
      //     res = arr[i]
      //   }
      //   i++
      // }
      // return res
    },
    initAreaCodeValue () {
      if (this.value && !isNaN(parseInt(this.value))) {
        // 初始化区域数组
        let select = []
        let provinceCode = this.value.substr(0, 2) + '0000'
        select.push(provinceCode)
        for (const cityCode in areaData[provinceCode]) {
          if (this.value === cityCode) {
          // 该编码为市级县
            select.push(cityCode)
          }
        }
        if (select.length <= 1) {
        //  该编码为县级单位
          select.push(this.value.substr(0, 4) + '00')
          select.push(this.value)
        }
        // 选择level的逻辑代码
        if (this.firstCode !== '86') {
          let t = 0
          for (let i = 0; i < select.length; i++) {
            if (select[i] === this.firstCode) {
              t = i
            }
          }
          // slice 函数end不包括end的元素，所以要加2
          select = select.slice(t + 1, t + this.showLevel + 2)
        } else {
          let t = 0
          select = select.slice(0, t + this.showLevel + 2)
        }
        return select
      } else {
        return []
      }
    }
  },
  mounted () {
    if (this.value) {
      this.loadAlCascaderData(this.value)
    } else {
      // 无value的情况下，初始化组件数据
      this.init()
    }
  }
}
</script>
<style lang="less" scoped>
</style>
