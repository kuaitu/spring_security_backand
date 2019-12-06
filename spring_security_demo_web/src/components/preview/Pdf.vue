<template>
  <div class="pdf">
    <div class="center">
      <div class="contor"
           v-if="initdont">
        <Button class="btn"
                type="primary"
                @click="prev">上一页</Button>
        <Button class="btn"
                type="primary"
                @click="next">下一页</Button>
        <span class="page">页数: <span v-text="page_num"></span> / <span v-text="page_count"></span></span>
        <Button class="btn"
                type="primary"
                @click="addscale">
          <Icon type="ios-add"
                size="18"></Icon>
        </Button>
        <Button class="btn"
                type="primary"
                @click="minus">
          <Icon type="ios-remove"
                size="18"></Icon>
        </Button>
        <Button class="btn"
                type="error"
                @click="closepdf">关闭</Button>
      </div>
      <canvas class="canvasstyle"
              id="pdf-canvas"></canvas>
    </div>
  </div>
</template>

<script>
  import PDFJS from '../../../static/pdf/pdf.js'
  // import api from '@/api/corp/corp'
PDFJS.workerSrc = '../../../static/pdf/pdf.worker.js'

export default {
  props: ['pdfurl'],

  data () {
    return {
      initdont: false,
      pdfDoc: null, // pdfjs 生成的对象
      pageNum: 1,
      pageRendering: false,
      pageNumPending: null,
      scale: 2.0, // 放大倍数
      page_num: 0, // 当前页数
      page_count: 0, // 总页数
      maxscale: 3.5, // 最大放大倍数
      minscale: 1.2 // 最小放大倍数
    }
  },

  methods: {
    renderPage (num) { // 渲染pdf
      let vm = this
      this.pageRendering = true
      let canvas = document.getElementById('pdf-canvas')
      this.pdfDoc.getPage(num).then(function (page) {
        var viewport = page.getViewport(vm.scale)
        canvas.height = viewport.height
        canvas.width = viewport.width
        var renderContext = {
          canvasContext: vm.ctx,
          viewport: viewport
        }
        var renderTask = page.render(renderContext)
        renderTask.promise.then(function () {
          vm.pageRendering = false
          if (vm.pageNumPending !== null) {
            vm.renderPage(vm.pageNumPending)
            vm.pageNumPending = null
          }
        })
      })
      vm.page_num = vm.pageNum
      vm.initdont = true
    },

    addscale () { // 放大
      if (this.scale >= this.maxscale) {
        return
      }
      this.scale += 0.1
      this.queueRenderPage(this.pageNum)
    },

    minus () { // 缩小
      if (this.scale <= this.minscale) {
        return
      }
      this.scale -= 0.1
      this.queueRenderPage(this.pageNum)
    },

    prev () { // 上一页
      let vm = this
      if (vm.pageNum <= 1) {
        return
      }
      vm.pageNum--
      vm.queueRenderPage(vm.pageNum)
    },

    next () { // 下一页
      let vm = this
      if (vm.pageNum >= vm.page_count) {
        return
      }
      vm.pageNum++
      vm.queueRenderPage(vm.pageNum)
    },

    closepdf () { // 关闭PDF
      this.$emit('closepdf')
    },

    queueRenderPage (num) {
      if (this.pageRendering) {
        this.pageNumPending = num
      } else {
        this.renderPage(num)
      }
    },

    convertDataURIToBinary (dataURI) {
      if (!dataURI) {
        return []
      }
      dataURI = dataURI.replace(/[\r\n]/g, '')
      let raw = window.atob(dataURI)
      let rawLength = raw.length
      let array = new Uint8Array(new ArrayBuffer(rawLength))
      for (let i = 0; i < rawLength; i++) {
        array[i] = raw.charCodeAt(i)
      }
      return array
    }
  },

  computed: {
    ctx () {
      let id = document.getElementById('pdf-canvas')
      return id.getContext('2d')
    }
  },

  mounted () {
    // let vm = this
    // api.getPdfFile({filePath: vm.pdfurl}).then(res => {
    //   if (res) {
    //     let url = vm.convertDataURIToBinary(res)
    //     // let url = 'D://tomcat7/apache-tomcat-8.5.31/webapps/file/201810/1.pdf'
    //     // let url = 'D://tomcat7/apache-tomcat-8.5.31/webapps/file/201810/5398246c-4bfe-4349-bee8-eb4ca84e5d4f.pdf'
    //     PDFJS.getDocument({data: url}).then(function (pdfDoc_) { // 初始化pdf
    //       vm.pdfDoc = pdfDoc_
    //       vm.page_count = vm.pdfDoc.numPages
    //       vm.renderPage(vm.pageNum)
    //     }).catch(err => {
    //       console.log(err)
    //       this.$Notice.error({title: 'pdf文件不存在'})
    //       this.closepdf()
    //     })
    //   }
    // }).catch(err => {
    //   console.log(err)
    //   this.$Notice.error({title: 'pdf文件不存在'})
    //   this.closepdf()
    // })
  }
}
</script>

<style lang="less" scoped>
.pdf {
  position: fixed;
  top: 0;
  left: 0;
  background-color: rgba(0, 0, 0, 0.5);
  width: 100%;
  height: 100%;
  z-index: 99999;
  display: flex;
  justify-content: center;
  align-items: center;

  .center {
    text-align: center;
    height: 100%;
    overflow: auto;
    padding-top: 6px;

    .contor {
      position: fixed;
      padding: 10px;
      top: 0;
      left: 0;
      width: 100%;
      display: flex;
      justify-content: center;
      align-items: center;

      .page {
        margin-right: 5px;
      }

      .btn {
        margin-right: 5px;
      }
    }
  }
}

/*默认滚动条样式*/
::-webkit-scrollbar {
  width: 2px;
  height: 8px;
}
::-webkit-scrollbar-track {
  background: #eee;
}
::-webkit-scrollbar-thumb {
  border: 1px #808080 solid;
  border-radius: 10px;
  background: #999;
}
::-webkit-scrollbar-thumb:hover {
  background: #7d7d7d;
}
</style>
