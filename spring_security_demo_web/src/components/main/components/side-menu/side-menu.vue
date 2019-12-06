<template>
  <div class="side-menu-wrapper">
    <slot></slot>
    <Menu ref="menu" v-show="!collapsed" :active-name="activeName" :open-names="openNames" :accordion="accordion" :theme="theme" width="auto" @on-select="handleSelect">
       <Submenu v-for="m in menuList" :key="m.id" :name="m.menuName">
        <template slot="title">
          <Icon :type='m.menuIcon'></Icon>
          {{m.menuName}}
        </template>
        <MenuItem class="childrenMenu" v-for="sm in m.children" :key="sm.id" :name="sm.menuName">
          <Icon :type='sm.menuIcon'></Icon>
          </span> {{sm.menuName}}
        </MenuItem>
      </Submenu>
    </Menu>

    <div class="menu-collapsed" v-show="collapsed" :list="menuList">
      <template v-for="item in menuList">
        <collapsed-menu v-if="item.children && item.children.length > 0" @on-click="handleSelect" @menu-item-select-enter="handleMenuSelectEnter" @menu-item-select-out="handleMenuSelectOut" :active-name="activeName" :hide-title="!collapsed" :root-icon-size="rootIconSize" :icon-size="iconSize" :theme="theme" :parent-item="item" :key="item.id"></collapsed-menu>

        <!-- <Tooltip transfer v-else :content="(item.meta && item.meta.title) || (item.children && item.children[0] && item.children[0].meta.title)" placement="right" :key="`drop-menu-${item.name}`">
          <a @click="handleSelect(getNameOrHref(item, true))" class="drop-menu-a" :style="{textAlign: 'center'}"></a>
        </Tooltip> -->

      </template>
    </div>
  </div>
</template>
<script>
  import SideMenuItem from './side-menu-item.vue'
  import CollapsedMenu from './collapsed-menu.vue'
  import {getUnion} from '@/libs/tools'

  export default {
  name: 'SideMenu',
  components: {
    SideMenuItem,
    CollapsedMenu,
    defaultActiveName: ''
  },
  props: {
    menuList: {
      type: Array,
      default () {
        return []
      }
    },
    collapsed: {
      type: Boolean
    },
    theme: {
      type: String,
      default: 'dark'
    },
    rootIconSize: {
      type: Number,
      default: 20
    },
    iconSize: {
      type: Number,
      default: 16
    },
    accordion: Boolean,
    activeName: {
      type: String,
      default: ''
    },
    openNames: {
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      openedNames: []
    }
  },
  methods: {
    handleSelect (name, activeName) {
      this.defaultActiveName = activeName
      this.$emit('on-select', name, activeName)
    },
    handleMenuSelectEnter (menuName, activeName) {
      this.$emit('menu-item-select', menuName)
    },
    handleMenuSelectOut () {
      this.$emit('menu-item-select', this.defaultActiveName)
    },
    getOpenedNamesByActiveName (name) {
      return this.$route.matched.map(item => item.name).filter(item => item !== name)
    },
    updateOpenName (name) {
      if (name === this.$config.homeName) this.openedNames = []
      else this.openedNames = this.getOpenedNamesByActiveName(name)
    }
  },
  computed: {
    textColor () {
      return this.theme === 'dark' ? '#fff' : '#495060'
    }
  },
  watch: {
    activeName (name) {
      if (this.accordion) this.openedNames = this.getOpenedNamesByActiveName(name)
      else this.openedNames = getUnion(this.openedNames, this.getOpenedNamesByActiveName(name))
    },
    openNames (newNames) {
      this.openedNames = newNames
    },
    openedNames () {
      this.$nextTick(() => {
        // this.$refs.menu.updateOpened()
      })
    }
  },
  mounted () {
    this.openedNames = getUnion(this.openedNames, this.getOpenedNamesByActiveName(name))
  }
}
</script>
<style lang="less">
@import './side-menu.less';
</style>
