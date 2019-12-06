<template>
  <div class="pdf">
    <div class="center"
         id='pdf'
         ref="center">
      <div class="contor"
           v-if="initdont">
        <Button size="small"
                type="primary"
                @click="prev"
                :disabled="pageNumber === 1">上一页</Button>
        <Button size="small"
                type="primary"
                @click="next"
                :disabled="pageNumber === pageCount">下一页</Button>
        <span class="page">页数: <span v-text="pageNumber"></span> / <span v-text="pageCount"></span></span>
        <Button size="small"
                type="primary"
                @click="addscale"
                :disabled="scale >= maxscale">
          <Icon type="ios-add"
                size="18"></Icon>
        </Button>
        <Button size="small"
                type="primary"
                @click="minus"
                :disabled="scale <= minscale">
          <Icon type="ios-remove"
                size="18"></Icon>
        </Button>
        <Button size="small"
                type="error"
                @click="closepdf">关闭</Button>
      </div>
      <div class="contor"
           v-if="initdont2">
        <Button class="btn"
                type="error"
                @click="closepdf">关闭</Button>
      </div>
      <canvas v-if="canvasShow"
              id="pdf-canvas"
              ref="pdfCanvas"
              style="margin-top:50px;" />
      <img ref="image" />
    </div>
  </div>
</template>

<script>
  import PDFJS from '../../../static/pdf/pdf.js'

  PDFJS.workerSrc = '../../../static/pdf/pdf.worker.js'
let PDFDocumentProxy = null
let PDFPageProxy = null
const CSS_UNITS = 1.3333333333333333
export default {
  props: {
    pdfurl: {
      type: String
    }
  },
  data () {
    return {
      canvasShow: true,
      initdont: false,
      initdont2: false,
      imgX: 0,
      imgY: 0,
      pageRendering: false,
      pageNumPending: null,
      pageNumber: 1, // 当前页数
      pageCount: 0, // 总页数
      scale: 1.0, // 放大倍数
      maxscale: 4, // 最大放大倍数
      minscale: 0.5 // 最小放大倍数
    }
  },

  methods: {
    init () {
      PDFJS.getDocument({url: this.pdfurl}).then((pdfDoc) => { // 初始化pdf
        PDFDocumentProxy = pdfDoc
        this.pageCount = PDFDocumentProxy.numPages
        this.renderPage(this.pageNumber)
      }).catch(err => {
        if (err.name === 'InvalidPDFException') {
          this.canvasShow = false
          this.drawImage()
        } else {
          this.$Notice.error({title: 'pdf文件不存在'})
          this.closepdf()
        }
      })
    },
    renderPage (num) { // 渲染pdf
      this.pageRendering = true
      PDFDocumentProxy.getPage(num).then((page) => {
        PDFPageProxy = page
        let totalRotation = (PDFPageProxy.rotation + PDFPageProxy.rotate) % 360
        let viewport = PDFPageProxy.getViewport(this.scale * CSS_UNITS, totalRotation)
        this.canvas.height = viewport.height
        this.canvas.width = viewport.width
        let renderContext = {
          canvasContext: this.context,
          viewport: viewport
        }
        let renderTask = PDFPageProxy.render(renderContext)
        renderTask.promise.then(() => {
          this.pageRendering = false
          if (this.pageNumPending !== null) {
            this.renderPage(this.pageNumPending)
            this.pageNumPending = null
          }
        })
      })
      this.initdont = true
    },

    addscale () { // 放大
      if (this.scale >= this.maxscale) {
        return
      }
      this.scale += 0.1
      this.queueRenderPage(this.pageNumber)
    },

    minus () { // 缩小
      if (this.scale <= this.minscale) {
        return
      }
      this.scale -= 0.1
      this.queueRenderPage(this.pageNumber)
    },

    prev () { // 上一页
      if (this.pageNumber <= 1) {
        return
      }
      this.pageNumber--
      this.queueRenderPage(this.pageNumber)
      this.setPdfScrollTop()
    },

    next () { // 下一页
      if (this.pageNumber >= this.pageCount) {
        return
      }
      this.pageNumber++
      this.queueRenderPage(this.pageNumber)
      this.setPdfScrollTop()
    },

    closepdf () { // 关闭PDF
      this.$Message.destroy()
      this.$emit('on-close')
    },

    queueRenderPage (num) {
      if (this.pageRendering) {
        this.pageNumPending = num
      } else {
        this.renderPage(num)
      }
    },

    drawImage () {
      this.imageDOM.src = this.pdfurl
      this.imageDOM.style.margin = 'auto'
      this.imageDOM.style.marginTop = '50px'
      this.imageDOM.style.transition = 'all .3s ease-in-out'
      this.imageDOM.ondragstart = () => {
        return false
      }
      this.imageDOM.onload = () => {
        if (this.imgX <= 0 || this.imgY <= 0) {
          this.imgX = this.imageDOM.width
          this.imgY = this.imageDOM.height
        }
        if (!this.initdont2) {
          setTimeout(() => {
            this.$Message.info({
              content: '滚动鼠标滚轮即可放大缩小',
              duration: 2
            })
          }, 100)
        }
        this.initdont2 = true
        this.imageDOM.width = this.imgX
        this.imageDOM.height = this.imgY
      }
      let center = this.$refs['center']
      // center.style.cursor =
      center.onmousewheel = (event) => {
        let wheelDelta = event.wheelDelta ? event.wheelDelta : (event.deltalY * (-40)) // 获取当前鼠标的滚动情况
        if (wheelDelta > 0) {
          this.imgX = this.imgX + 100
          this.imgY = this.imgY + 100
        } else {
          this.imgX = this.imgX - 100
          this.imgY = this.imgY - 100
        }

        if (this.imgX < document.body.scrollWidth / 1.3 && this.imgX > 200 && this.imgY > 200) {
          this.drawImage()
        }
      }
    },

    setPdfScrollTop () {
      const pdfDom = document.querySelector('#pdf')
      pdfDom.scrollTop = 0
    }
  },

  computed: {
    canvas () {
      // return document.getElementById('pdf-canvas')
      return this.$refs['pdfCanvas']
    },
    context () {
      return this.canvas.getContext('2d')
    },
    imageDOM () {
      return this.$refs['image']
    }
  },

  watch: {
    pdfurl: {
      handler (val) {
        if (val && val !== '') {
          this.init()
        }
      },
      immediate: true
    }
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
  z-index: 999;
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
      background-color: #474747;

      .page {
        margin-right: 5px;
        color: #fff;
      }

      button {
        margin-right: 5px;
      }
    }
  }
}

/*默认滚动条样式*/
::-webkit-scrollbar {
  width: 4px;
  height: 4px;
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
  cursor: pointer;
}
</style>
