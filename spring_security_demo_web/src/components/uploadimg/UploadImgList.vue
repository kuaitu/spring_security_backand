<template>
  <div>
    <div class="demo-upload-list"
         v-for="item in uploadList"
         :value="item.url"
         :key="item.url">
      <div v-if="item.status === 'finished'">
        <img :src="item.url">
        <div class="demo-upload-list-cover">
          <Icon type="ios-eye-outline"
                @click.native="handleView(item.url)"></Icon>
          <Icon type="ios-trash-outline"
                @click.native="handleRemove(item)"></Icon>
        </div>
      </div>
      <div v-else>
        <Progress v-if="item.showProgress"
                  :percent="item.percentage"
                  hide-info></Progress>
      </div>
    </div>
    <Upload ref="upload"
            :show-upload-list="false"
            :default-file-list="defaultList"
            :on-success="handleSuccess"
            :format="['jpg','jpeg','png']"
            :max-size="2048"
            :on-format-error="handleFormatError"
            :on-exceeded-size="handleMaxSize"
            :before-upload="handleBeforeUpload"
            multiple
            type="drag"
            action="/basic_platform/FileUpload/upload.action"
            style="display: inline-block;width:58px;"
            name="annex">
      <div style="width: 58px;height:58px;line-height: 58px;">
        <Icon type="camera"
              size="20"></Icon>
      </div>
    </Upload>
    <Modal title="查看图片"
           v-model="visible">
      <img :src="imgUrl"
           v-if="visible"
           style="width: 100%">
    </Modal>
  </div>
</template>
<script>
export default {
  name: 'UploadImgList',
  data () {
    return {
      defaultList: [],
      imgUrl: '',
      visible: false,
      uploadList: []
    }
  },
  methods: {
    handleView (url) {
      this.imgUrl = url
      this.visible = true
    },
    handleRemove (file) {
      const fileList = this.$refs.upload.fileList
      this.$refs.upload.fileList.splice(fileList.indexOf(file), 1)
    },
    handleSuccess (res, file) {
      file.url = res.data
      this.$emit('uploadList', this.uploadList)
    },
    handleFormatError (file) {
      this.$Notice.warning({
        title: '文件格式不正确',
        desc: '文件 ' + file.name + ' 格式不正确，请上传GIF、JPG、JPEG、PNG格式的图片。'
      })
    },
    handleMaxSize (file) {
      this.$Notice.warning({
        title: '超出文件大小限制',
        desc: '文件 ' + file.name + ' 太大，不能超过 2M。'
      })
    },
    handleBeforeUpload () {}
  },
  mounted () {
    this.uploadList = this.$refs.upload.fileList
  }
}
</script>
<style>
.demo-upload-list {
  display: inline-block;
  width: 60px;
  height: 60px;
  text-align: center;
  line-height: 60px;
  border: 1px solid transparent;
  border-radius: 4px;
  overflow: hidden;
  background: #fff;
  position: relative;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.2);
  margin-right: 4px;
}
.demo-upload-list img {
  width: 100%;
  height: 100%;
}
.demo-upload-list-cover {
  display: none;
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background: rgba(0, 0, 0, 0.6);
}
.demo-upload-list:hover .demo-upload-list-cover {
  display: block;
}
.demo-upload-list-cover i {
  color: #fff;
  font-size: 20px;
  cursor: pointer;
  margin: 0 2px;
}
</style>
