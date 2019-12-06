<template>
  <div class="user-avator-dropdown">
    <Avatar :src="userAvator" />
    <font class="user-name">{{user.realName && user.realName !== '' ? user.realName : user.userName}}</font>
    <a href="javascript:void(0);" @click="logout()" class="user-logout">退出</a>
  </div>
</template>

<script>
  import './user.less'
  import {fileName} from '@/libs/global'
  import ResetPwd from './widgets/ResetPwd'
  import UserService from '@/service/UserService'
  import * as R from '@/router/router-types'
  import userApi from '@/api/user/user'
  import showAPP from './widgets/ShowAPP'

  export default {
  components: { ResetPwd, showAPP },
  name: 'User',
  data () {
    return {
      show: false,
      showAPP: false
    }
  },
  props: {
    user: {
      type: Object,
      default: function () {
        return { userName: '', realName: '' }
      }
    }
  },
  methods: {
    logout () {
      this.$Modal.confirm({
        title: '温馨提示',
        content: '确认退出登录吗？',
        onOk: () => {
          this.$Notice.destroy()
          UserService.localLogout()
          userApi.logout()
          this.$router.push({ path: R.kLogin })
        }
      })
    }
  },
  computed: {
    userAvator () {
      if (this.user.profile) {
        return fileName + this.user.profile
      } else {
        return require('@/assets/imgs/system/default_avatar.png')
      }
    }
  }
}
</script>
