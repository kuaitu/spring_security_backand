<template>
  <div>
    <Modal v-model="isOpen"
           class-name="vertical-center-modal"
           :mask-closable='false'
           width="1200"
           height="1000">
      <p slot="header">
        <span>配置权限</span>
      </p>
      <div class="func_body">
        <div class="authset-checkall">
          <input type="checkbox"
                 v-model="allCheck" /> 全部
        </div>
        <div class="authset-all">
          <div v-for="menu in menus"
               :key="menu.menuId">
            <dl>
              <dt class="tit">
                <input type="checkbox"
                       v-model="menu.menuHasFunc"
                       @change="menuClick(menu)" />
                {{menu.menuName}}
              </dt>
              <dd>
                <div class="authset-list"
                     v-for="subMenu in menu.children"
                     :key="subMenu.menuId">
                  <label>
                    <input type="checkbox"
                           v-model="subMenu.menuHasFunc"
                           @change="subMenuClick(subMenu)" /> {{subMenu.menuName}}
                  </label>
                  <ul>
                    <li v-for="func in subMenu.jurisdiction4Uw"
                        :key="func.jurisdictionId">
                      <input type="checkbox"
                             v-model="func.hasFunc"
                             @change="funcClick(func)" />
                      {{func.jurisdictionName}}
                    </li>
                    <div style="clear: both"></div>
                  </ul>
                </div>
              </dd>
            </dl>
          </div>
        </div>
      </div>
      <div slot="footer">
        <Button @click="submit"
                class="btn"
                type="primary">提交</Button>
        <Button @click="cancel"
                class="btn"
                style="margin-left: 8px">取消</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  import roleApi from '@/api/sys/role'

  export default {
  components: {},
  props: [],
  data () {
    return {
      allCheck: false,
      menus: [],
      isOpen: false,
      id: null
    }
  },
  watch: {
    allCheck (allCheck) {
      this.menus.map(menu => {
        menu.menuHasFunc = this.allCheck
        menu.children.forEach(subMenu => {
          subMenu.menuHasFunc = this.allCheck
          subMenu.jurisdiction4Uw.map(func => {
            func.hasFunc = this.allCheck
          })
        })
      })
    }
  },
  computed: {},
  methods: {
    openModal (open) {
      this.isOpen = open
    },
    menuClick (menu) {
      let check = menu.menuHasFunc
      menu.children.map(subMenu => {
        subMenu.menuHasFunc = check
        subMenu.jurisdiction4Uw.map(func => {
          func.hasFunc = check
        })
      })
    },

    subMenuClick (subMenu) {
      let check = subMenu.menuHasFunc
      if (check) {
        subMenu.parent.menuHasFunc = check
      } else {
        let hasSubMenu = false
        subMenu.parent.children.map(s => {
          if (s.menuHasFunc) {
            hasSubMenu = true
          }
        })
        if (!hasSubMenu) {
          subMenu.parent.menuHasFunc = false
        }
      }
      subMenu.jurisdiction4Uw.map(func => {
        func.hasFunc = check
      })
    },

    funcClick (func) {
      let check = func.hasFunc
      if (check) {
        func.parent.menuHasFunc = true
        func.parent.parent.menuHasFunc = true
      } else {
        let subMenuHasFunc = false
        func.parent.jurisdiction4Uw.map(f => {
          if (f.hasFunc) {
            subMenuHasFunc = true
          }
        })
        if (!subMenuHasFunc) {
          func.parent.menuHasFunc = false
          let hasSubMenu = false
          func.parent.parent.children.map(subMenu => {
            if (subMenu.menuHasFunc) {
              hasSubMenu = true
            }
          })
          if (!hasSubMenu) {
            func.parent.parent.menuHasFunc = false
          }
        }
      }
    },

    submit () {
      this.$indicator.open()

      var cache = []
      let menuJSON = JSON.stringify(this.menus, (key, value) => {
        if (typeof value === 'object' && value !== null) {
          if (cache.indexOf(value) !== -1) {
            return null
          }
          cache.push(value)
        }
        return value
      })
      cache = null

      roleApi.updateMenuAndFunc(this.id, JSON.parse(menuJSON)).then(resp => {
        if (resp.success) {
          this.$Notice.success({ title: '操作成功', desc: '编辑角色权限成功' })
          this.isOpen = false
          // this.$Message.warning({
          //   content: '将在2秒后刷新权限',
          //   duration: 2
          // })
          // setTimeout(function () { window.location.reload() }, 2000)
        } else {
          this.isOpen = true
          this.$Message.error(`添加角色权限失败：${resp.message}`)
        }
        this.$indicator.close()
      }).catch(err => {
        this.isOpen = true
        this.$Message.error(`添加角色权限失败：${err.message || ''}`)
        this.$indicator.close()
      })
    },

    cancel () {
      this.isOpen = false
    },
    initData (initRole, id) {
      this.menus = initRole
      this.id = id
    }
  },
  created () {},
  mounted () {
  }
}
</script>
<style lang="less" scoped>
dl,
dd,
ul {
  margin: 0;
  padding: 0;
}

.authset-checkall {
  font-size: 12px;
  font-weight: normal;
  line-height: 24px;
  color: #999;
  vertical-align: bottom;
  letter-spacing: normal;
  display: inline-block;
  padding: 0;
}

.authset-all {
  border: 1px solid #ddd;
  margin-bottom: 20px;
  color: #666;
  font-size: 12px;
  font-family: "Microsoft Yahei", "Lucida Grande", Verdana, Lucida, Helvetica,
    Arial, sans-serif;
}

.authset-all dt.tit {
  font-size: 14px;
  line-height: 40px;
  color: #555;
  background-color: #fafafa;
  height: 40px;
  padding: 0px 10px;
  font-weight: normal;
  border-bottom: 1px dotted #ddd;
}

.authset-list {
  width: 100%;
  height: auto;
  display: inline-block;
  clear: both;
  border-bottom: 1px dotted #ddd;
  padding: 10px 0px;
  margin: 0px;
}

.authset-list ul {
  width: 80%;
  min-height: 25px;
  padding-top: 7px;
  padding-left: 5px;
  display: inline-block;
  border-left: dotted 1px #ccc;
  list-style-image: none;
  list-style-type: none;
}

.authset-list li {
  list-style: none;
  font-size: 12px;
  line-height: 20px;
  color: #999;
  letter-spacing: normal;
  word-spacing: normal;
  display: inline-block;
  width: 190px;
  height: 20px;
  margin-bottom: 5px;
  padding-left: 30px;
  text-align: left;
}

.authset-list label {
  float: left;
  padding-top: 7px;
  cursor: pointer;
  text-align: left !important;
  padding-left: 30px;
  padding-right: 0;
  width: 16%;
}

.authset-list input,
.authset-checkall input {
  display: inline-block;
  margin-right: 8px;
  zoom: 1;
  vertical-align: middle;
  margin-top: -2px;
  margin-bottom: 1px;
  cursor: pointer;
}

.grid_button {
  font-weight: inherit;
  color: inherit;
}
.func_body {
  height: 450px;
  padding: 10px;
  overflow: scroll;
  overflow-x: auto;
  overflow-x: 1px;
}
</style>
