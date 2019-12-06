<template>
  <Modal v-model="isOpen" width="600" :mask-closable="false" :styles="{top: '36px'}">
    <p slot="header">
      <span >失信企业信息录入</span>
    </p>
    <div>
      <Form ref="dataForm"
            :model="dataModel"
            :label-width="80"
            inline>
        <FormItem class="form-item" label="企业名称：" prop="corporationName">
          <Input v-model.trim="dataModel.corporationName"
                 placeholder="请输入企业名称"></Input>
        </FormItem>
        <FormItem class="form-item" label="统一社会信用代码：" prop="creditCode">
          <Input v-model.trim="dataModel.creditCode"
                 placeholder="统一社会信用代码"></Input>
        </FormItem>
        <FormItem label="发布渠道："
                  prop="publishChannel">
          <dict-item-select dictName="PUBLISH_CHANNEL"
                            allStatus
                            v-model="dataModel.publishChannel"
                            style="width: 157px">
          </dict-item-select>
        </FormItem>
        <FormItem label="适用时间："
                      prop="validTime">
              <DatePicker v-model="dataModel.validTime"
                          placeholder="请输入适用时间"
                          style="width:100%"
                          :editable="false"
                          clearable></DatePicker>
            </FormItem>
      </Form>
    </div>
    <div slot="footer">
      <Button @click="submit" class="btn" type="primary">提交</Button>
      <Button @click="cancel" class="btn" style="margin-left: 8px">取消</Button>
    </div>
  </Modal>
</template>

<script>
  import api from '@/api/business/credit/lost'
  import Util from '@/utils/util'

  export default {
  components: {},
  data () {
    // const validateProportionValue = (rule, value, callback) => {
    //   var reg = /^[1-9]\d*$/
    //   if (!reg.test(value)) {
    //     callback(new Error('请输入抽查量(大于0的整数)'))
    //   } else if (value.length > 11) {
    //     callback(new Error('抽查量输入数字应小于11位'))
    //   } else {
    //     callback()
    //   }
    // }
    return {
      isOpen: false,
      type: 'ADD',
      dataModel: {
        corporationName: '',
        creditCode: '',
        publishChannel: '',
        validTime: ''
      },
      rules: {
        // spotCheckProjectType: [{ required: true, trigger: 'change', type: 'enum', enum: ['01', '02'], message: '请选择项目类型' }],
        // // spotCheckProjectSize: [{ required: true, message: '请选择项目规模', trigger: 'blur' }],
        // imageProgress: [{trigger: 'blur', required: true, message: '请选择形象进度'}],
        // projectSubtypes: [{trigger: 'blur', required: true, type: 'array', min: 1, message: '请选择项目进度'}],
        // regionalCode: [{ required: true, message: '请选择所属地级市', trigger: 'blur' }],
        // proportionValue: [{trigger: 'blur', required: true, validator: validateProportionValue}],
        // spotCheckTeamCode: [{ required: true, message: '请选择所属抽查小组', trigger: 'blur' }]
      }
    }
  },
  methods: {
    cancel () {
      this.isOpen = false
    },
    submit () {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          this.$indicator.open()
          if (this.dataModel.validTime) {
            this.dataModel.validTime = Util.formatDate(this.dataModel.validTime, 'yyyy-MM-dd') + ' 00:00:00'
          }
          api.add(this.dataModel).then(resp => {
            if (resp.success) {
              console.log(resp)
              this.isOpen = false
            } else {
              this.$Message.error('录入失败：' + resp.message)
              this.$indicator.close()
            }
            this.$indicator.close()
          }).catch(err => {
             console.log(err )
            this.$Notice.error({ title: '加载数据失败', desc: err })
            this.$indicator.close()
          })
        } else {
          this.$Message.error('表单验证失败!')
        }
      })
    },
    initData (type, planCode, data, index) {
      // this.type = type
      // this.spotCheckPlanCode = planCode
      // if (this.type === 'ADD') {
      //   this.$refs.dataForm.resetFields()
      // } else {
      //   this.dataModel = data
      //   this.curIndex = index
      // }
      this.isOpen = true // 初始化后打开
    }
  },
  computed: {}
}
</script>

<style>
.label_width {
  width: 420px;
}
</style>
