<template>
  <div>
    <Upload ref="upload" name="file" :on-success="handleSuccess" multiple :action="uploadAction"
      :show-upload-list="true" :before-upload="handleBeforeUpload" :on-remove="handleRemove">
      <Button icon="ios-cloud-upload-outline">上传附件</Button>
    </Upload>
  </div>
</template>

<script>
export default {
  name: 'UploadFile',

  props: ['defaultFileList'],

  data () {
    return {
      // uploadAction: '/basic_platform/FileUpload/upload.action',
      uploadAction: '/quality_supervision/file/upload.action',
      uploadList: []
    }
  },
  methods: {
    handleRemove (file) {
      // const fileList = this.$refs.upload.fileList
      // this.$refs.upload.fileList.splice(fileList.indexOf(file), 1)
    },
    handleSuccess (res, file) {
      file.url = res.data
      this.$emit('uploadFile', this.uploadList)
    },
    handleBeforeUpload (file) {
    }
  },
  mounted () {
    this.uploadList = this.$refs.upload.fileList
  }
}
</script>

<style lang="less" scoped>
</style>
