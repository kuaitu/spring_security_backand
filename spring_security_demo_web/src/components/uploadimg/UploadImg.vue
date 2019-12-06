<template>
  <div class="upload-img">
    <div @change="isIe9">
      <closable @close="remove"
                style="width:80px;">
        <Upload ref="upload"
                :show-upload-list="false"
                :on-success="handleSuccess"
                :data="size"
                :format="['gif', 'jpg','jpeg','png']"
                :max-size="2048"
                :on-format-error="handleFormatError"
                :on-exceeded-size="handleMaxSize"
                :before-upload="handleBeforeUpload"
                type="drag"
                :action="uploadAction"
                class="upload"
                name="annex">
          <div class="img-wrapper">
            <Icon v-if="!isShow"
                  type="plus-round"
                  size="30"
                  class="icon"></Icon>
            <m-img v-if="isShow"
                   :src="getImgUrl"
                   class="img" />
            <div v-if="isShow"
                 class="img-cover"
                 @click.stop="handleView">查看大图</div>
          </div>
        </Upload>
      </closable>
    </div>
    <Modal class="hide-footer"
           v-model="visible"
           scrollable>
      <m-img :src="imgUrl"
             class="img" />
      <div slot="footer">
      </div>
    </Modal>
  </div>
</template>

<script>
  import Closable from './Closable'

  export default {
  name: 'UploadImg',

  props: {
    value: {
      type: String,
      default: ''
    },
    size: {
      type: Object
    }
  },

  data () {
    return {
      uploadAction: '/basic_platform/FileUpload/upload.action',
      visible: false,
      imgUrl: ''
    }
  },
  mounted  () {
  },

  methods: {
    remove () {
      this.$emit('input', '')
    },

    handleView (url) {
      this.imgUrl = this.getImgUrl
      this.visible = true
    },

    handleRemove (file) {
    },

    handleSuccess (res, file) {
      file.url = res.data
      this.$emit('input', res.data)
      this.$emit('success')
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

    handleBeforeUpload () {
    },
    isIe9 () {
      var explorer = window.navigator.userAgent.toLowerCase()
      if (explorer.indexOf('msie') >= 0) {
        var ver = explorer.match(/msie ([\d.]+)/)[1]
      }
      if (ver === '9.0') {
        alert('图片上传不支持ie9,请更换版本高的浏览器')
      }
    }
  },

  computed: {
    isShow () {
      console.log('isShow:' + this.value)
      return this.value
    },

    getImgUrl () {
      console.log('isShow:' + this.value)
      return this.value
    }
  },

  components: {
    Closable
  }
}
</script>

<style lang="less" scoped>
.upload {
  display: inline-block;
  width: 200px;
}
.img-wrapper {
  width: 200px;
  height: 80px;
  // line-height: 80px;
}
.icon {
  text-align: center;
  line-height: inherit;
}
.img {
  height: 100%;
  width: 100%;
}
.img-cover {
  display: none;
  width: 100%;
  left: 0px;
  bottom: 0px;
  position: absolute;
  height: 24px;
  line-height: 24px;
  opacity: 0.7;
  color: #ffffff;
  background: rgba(0, 0, 0, 0.6);
  text-align: center;
  cursor: pointer;
  overflow: hidden;
  z-index: 2017;
}
.img-wrapper:hover .img-cover {
  display: block;
}
</style>
