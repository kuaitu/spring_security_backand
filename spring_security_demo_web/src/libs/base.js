import SysDict from '@/service/SysDict.js'
import Util from '@/utils/util'
import UserService from '@/service/UserService'

export default {
  install (Vue, options) {
    Vue.prototype.translate = (dictCode, dictItemCode) => {
      return SysDict.translate(dictCode, dictItemCode)
    }
    Vue.prototype.formatDate = (date, fmt) => {
      if (!fmt) {
        fmt = 'yyyy-MM-dd'
      }
      return Util.formatDate(date, fmt)
    }
    /**
     * @description: 格式化日期函数，在iview Table中column中需要增加fmt格式的参数，默认为'yyyy-MM-dd'
     * @param {type} 默认参数
     * @return: 返回render的渲染
     */
    Vue.prototype.renderFormatDate = (h, params) => {
      let fmt = 'yyyy-MM-dd'
      if (params.column.fmt) {
        fmt = params.column.fmt
      }
      return h('Span', {}, Util.formatDate(params.row[params.column.key], fmt))
    }
    /**
     * @description: 翻译函数，在iview Table中column中需要增加dictName参数
     * @param {type}
     * dictName:需要翻译的数据字典代码，若为空则不翻译；
     * renderType：需要渲染的类型（Tag/Span)，默认为Span;
     * color:需要渲染的颜色，若存在该属性，则默认渲染类型变为Tag。（若该属性为function，则默认有两个参数：row:当前数据,index：当前序号)
     * @return: 返回render的渲染
     */
    Vue.prototype.renderTranslate = (h, params) => {
      let itemValue = ''
      let color = ''
      if (params.column.dictName && params.row[params.column.key]) {
        params.row[params.column.key] = params.row[params.column.key].toString()
        itemValue = SysDict.translate(
          params.column.dictName,
          params.row[params.column.key]
        )
      } else {
        itemValue = params.row[params.column.key]
      }
      if (typeof params.column.color === 'function') {
        color = params.column.color(params.row, params.index)
      } else {
        color = params.column.color
      }
      if (params.column.renderType === 'Tag' || params.column.color) {
        return h(
          'Tag',
          {
            props: {
              color: color
            }
          },
          itemValue
        )
      } else {
        return h('Span', {}, itemValue)
      }
    }
    /**
     * @description: 渲染操作按钮，在iview Table中column中需要增加renderButtons参数例如：
     *          renderButtons:[{method: this.viewLicence, text: '查看', size: 'small', marginRight: '5px', hasFunc:true/handle_method}]
     *          render: this.renderAction
     * @param {type} 默认参数
     * @return: 返回render的渲染
     */
    Vue.prototype.renderAction = (h, params) => {
      let renderButtons = params.column.renderButtons
      let buttons = []
      if (renderButtons instanceof Array) {
        renderButtons.forEach(element => {
          let hasFunc =
            !element.hasFunc || element.hasFunc(params.row, params.index)
          if (hasFunc) {
            buttons.push(
              h(
                'Button',
                {
                  props: {
                    type: element.type ? element.type : null,
                    size: element.size ? element.size : 'small'
                  },
                  style: {
                    marginRight: element.marginRight
                      ? element.marginRight
                      : '5px'
                  },
                  on: {
                    click: () => {
                      element.method(params.row, params.index)
                    }
                  }
                },
                element.text
              )
            )
          }
        })
      } else if (renderButtons instanceof Object) {
        let hasFunc =
          !renderButtons.hasFunc || renderButtons.hasFunc(params.row)
        if (hasFunc) {
          buttons.push(
            h(
              'Button',
              {
                props: {
                  type: renderButtons.type ? renderButtons.type : null,
                  size: renderButtons.size ? renderButtons.size : 'small'
                },
                style: {
                  marginRight: renderButtons.marginRight
                    ? renderButtons.marginRight
                    : '5px'
                },
                on: {
                  click: () => {
                    renderButtons.method(params.row, params.index)
                  }
                }
              },
              renderButtons.text
            )
          )
        }
      }
      return h('div', buttons)
    }
    /**
     * @description: 获取省市县全局翻译
     * @param {type} areaCode 区域编码
     * @param {type} split 分隔符
     * @return: 返回地区编码返回的地址
     */
    Vue.prototype.getAreaNameByCode = (areaCode, split) => {
      return Util.getAreaNameByCode(areaCode, split)
    }

    /**
     * @description: 身份证号合法性验证;支持15位和18位身份证号;支持地址编码、出生日期、校验位验证(用于表单校验)
     * @param {type} 身份证号
     * @return: 验证结果
     */
    Vue.prototype.valIdcardNumber = (rule, value, callback) => {
      value = value.replace('x', 'X')
      let val = Util.IdentityCodeValid(value)
      if (val.success) {
        callback()
      } else {
        callback(new Error(val.message))
      }
    }
    /**
     * @description: 验证电话号码
     * @param {type} 电话号码
     * @return: 校验结果
     */
    Vue.prototype.phoneValid = (rule, value, callback) => {
      value = value.replace('x', 'X')
      if (Util.PhoneValid(value)) {
        callback()
      } else {
        callback(new Error('号码不合法'))
      }
    }

    Vue.prototype.validateCorpCode = (rule, value, callback) => {
      // 18位校验及大写校验 /^[\da-z]+$/i（i不区分大小写）  /^[0-9a-zA-Z]+$/（数字和字母）
      let patrn = /^[0-9A-Z]+$/
      if (patrn.test(value) === false || value.length !== 18) {
        callback(new Error('不是有效的统一社会信用代码'))
      } else {
        callback()
      }
    }

    Vue.prototype.hasFunc = func => {
      return UserService.hasFunc(func)
    }

    Vue.prototype.getUser = () => {
      return UserService.getUser()
    }

    Vue.prototype.pagePrint = (innerHTML, margin) => {
      let _margin = '25.4mm 31.8mm'
      if (margin) {
        _margin = margin
      }
      let printWindow = window.open('_blank')
      var headHtml =
        "<head><style type='text/css' media='print'>.print-width{width:150.3mm;} @page{ size:  auto;   /* auto is the initial value */margin: " +
        _margin +
        ";  /* this affects the margin in the printer settings */}html{/*background-color: #FFFFFF; */margin: 0px;  /* this affects the margin on the html before sending to printer */}</style></head><body style='margin:0px;'>"
      var bottHtml = '</body>'
      var bdhtml = headHtml + innerHTML + bottHtml
      printWindow.document.write(bdhtml)
      printWindow.print()
      printWindow.close()
    }
  }
}
