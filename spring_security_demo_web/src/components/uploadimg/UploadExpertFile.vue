<template>
  <div class="upload-file">
    <Upload ref="upload"
            name="annex"
            :on-success="handleSuccess"
            multiple
            :action="uploadAction"
            :show-upload-list="false"
            :before-upload="handleBeforeUpload"
            :on-remove="handleRemove">
      <Button type="primary"
              icon="ios-cloud-upload-outline">批量导入</Button>
    </Upload>
  </div>
</template>

<script>
  import * as C from '@/utils/constants'

  export default {
  name: 'UploadFile',

  props: ['defaultFileList'],

  data () {
    return {
      // uploadAction: '/' + prjName + '/FileUpload/upload.action',
      uploadAction: '/' + C.API_HEAD + '/DeExpert/importExcel.action',
      uploadList: []
    }
  },
  methods: {
    handleRemove (file) {
      const fileList = this.$refs.upload.fileList
      this.$refs.upload.fileList.splice(fileList.indexOf(file), 1)
    },
    handleSuccess (res, file) {
      file.url = res.data
      this.$emit('uploadFlie', this.uploadList)
    },
    handleBeforeUpload (file) {
    },
    clearfile () {
      this.$refs.upload.clearFiles()
    }
  },
  mounted () {
    this.uploadList = this.$refs.upload.fileList
  }
}
</script>

<style lang="less" scoped>
</style>
