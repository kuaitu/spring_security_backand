<template>
  <div style="display:inline-block;padding:0 6px;">
    <Dropdown trigger="click" @on-click="setTheme">
      <a href="javascript:void(0)">
          <Icon :style="{marginTop: '-2px', verticalAlign: 'middle'}" color="#495060" :size="20" type="md-color-palette"></Icon>
      </a>
      <DropdownMenu slot="list">
        <DropdownItem v-for="(item, index) in themeList" :key="index" :name="item.name">
          <Row type="flex" justify="center" align="middle" @on-click="setTheme(item.name)">
            <span style="margin-right:10px;"><Icon :size="20" type="ios-happy" :color="item.menu"/></span>
            <span><Icon :size="22" type="md-information-circle" :color="item.element"/></span>
          </Row>
        </DropdownItem>
      </DropdownMenu>
    </Dropdown>
  </div>
</template>
<script>
  import userService from '@/service/UserService'

  export default {
  data () {
    return {
      themeList: [{
        name: 'theme1',
        menu: '#515a6e',
        element: '#2d8cf0'
      }, {
        name: 'theme2',
        menu: '#3053AB',
        element: '#FFFFFF'
      }]
    }
  },
  methods: {
    setTheme (theme) {
      document.getElementsByTagName('body')[0].className = theme
      userService.setUserTheme(theme)
    }
  },
  created () {
    document.getElementsByTagName('body')[0].className = userService.getUserTheme()
  }
}
</script>
