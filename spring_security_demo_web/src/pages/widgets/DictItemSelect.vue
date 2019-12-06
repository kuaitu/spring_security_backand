<!--
 * @Description: In User Settings Edit
 * @Author: wgz
 * @Date: 2019-05-21 20:26:01
 * @LastEditTime: 2019-08-09 23:23:17
 * @LastEditors: wgz
 -->
<template>
  <div>
    <Select ref="dictSelect"
            :disabled="disabled"
            @on-change="change"
            :transfer="transfer"
            :clearable="clearable"
            :placeholder="placeholder"
            @on-open-change="onOpenChange"
            :multiple="multiple"
            :value="valueStr">
      <Option v-for="item in dict"
              :value="item.dictItemCode"
              :key="item.dictItemCode"
              :label="item.dictItemValue">
        <span>{{ item.dictItemValue }}</span>
        <span class="option_item_note"
              v-if="item.note">
          {{item.note}}
        </span>
        <div style="clear:both"></div>
      </Option>
    </Select>
  </div>
</template>
<script>
  import sysDict from '@/service/SysDict'

  export default {
  props: {
    dictName: String,
    value: [String, Number],
    disabled: {
      type: Boolean,
      default: false
    },
    allStatus: {
      type: Boolean,
      default: false
    },
    allStatusText: String,
    clearable: {
      type: Boolean,
      default: false
    },
    placeholder: {
      type: String,
      default: ' '
    },
    transfer: {
      type: Boolean,
      default: true
    },
    multiple: {
      type: Boolean,
      default: false
    }
  },
  mounted () {
    this.initData()
  },
  beforeUpdate () {
    if (this.selected) {
      this.selectItemValue = this.selected
    }
  },
  watch: {
    dictName: {
      handler: function (obj) {
        this.initData()
      },
      deep: true
    }
  },
  computed: {
    valueStr () {
      let valueStr
      if (this.value !== undefined && this.value !== null) {
        valueStr = this.value.toString()
      } else {
        valueStr = ''
      }
      if ((typeof this.value) === 'number') {
        this.$emit('input', valueStr)
        this.$emit('onChange', valueStr)
      }
      // 如果值为多选，则转化为数组形式已逗号分隔，如：014,015,016
      if (this.multiple) {
        return valueStr.split(',')
      } else {
        return valueStr
      }
    }
  },
  data () {
    return {
      dict: [],
      defaultOption: [{dictItemCode: 'all', dictItemValue: '全部状态'}]
    }
  },

  methods: {
    initData () {
      let sysDictItem = sysDict.findDict(this.dictName)
      // console.log(sysDictItem)
      // console.log(this.defaultOption)
      if (this.allStatus === true || this.allStatus === '') {
        if (this.allStatusText) {
          this.defaultOption[0].dictItemValue = this.allStatusText
        }
        this.dict = this.defaultOption.concat(sysDictItem)
      } else {
        this.dict = sysDictItem
      }
    },
    change (value) {
      let valueStr
      if (this.multiple) {
        valueStr = value.join(',')
      } else {
        valueStr = value
      }
      this.$emit('input', valueStr)
      this.$emit('onChange', valueStr)
    },
    onOpenChange (type) {
      this.$emit('onOpenChange', type)
    },
    toggleMenu () {
      this.$refs.dictSelect.toggleMenu(null)
    }
  }
}
</script>

<style lang="less" scoped>
.option_item_note {
  float: right;
  margin-right: 12px;
  color: #ccc;
  text-overflow: ellipsis;
  text-align: right;
  margin-left: 10px;
  max-width: 300px;
  overflow: hidden;
}
</style>
