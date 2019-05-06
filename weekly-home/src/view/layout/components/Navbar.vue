<template>
  <div class="fly-header layui-bg-black">
    <div class="layui-container">
      <a class="fly-logo" href="./">
        <img :src="logoImg">
      </a>
      <ul v-show="!userInfo.username" class="layui-nav fly-nav-user">
        <!-- 未登入的状态 -->
        <li class="layui-nav-item">
          <a style="padding: 0 4px;"><router-link to="/login">登入</router-link></a>
        </li>
        <li class="layui-nav-item">
          <a href="./" class="iconfont icon-touxiang layui-hide-xs" style="padding: 0 4px;">
          </a>
        </li>
      </ul>
      <ul v-show="userInfo.username" class="layui-nav fly-nav-user">
        <!-- 登入后的状态 -->
        <li class="layui-nav-item">
          <el-dropdown @command="handleCommand">
            <span class="el-dropdown-link">
              {{userInfo.username }}
            </span>
            <el-dropdown-menu slot="dropdown" >
              <el-dropdown-item command="/manager/user"><i class="layui-icon">&#xe620;</i>个人中心</el-dropdown-item>
              <el-dropdown-item command="exit">退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <span class="el-dropdown-link fly-nav-avatar">
            <img :src="imgUrl">
          </span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import store from '@/store'
export default {
  name: 'Header',
  data () {
    return {
      logoImg: './static/logo.png',
      imgUrl: './static/cweg.jpg'
    }
  },
  computed: {
    ...mapGetters([
      'userInfo'
    ]),
    userInfo () {
      console.log('aaa')
      console.log(store.getters.userInfo)
      return store.getters.userInfo
    }
  },
  methods: {
    handleCommand (data) {
      if (data === 'exit') {
        this.$store.dispatch('LogOut').then(res => {
        })
        this.$router.push('/')
      } else {
        this.$router.push(data)
      }
    }
  }
}
</script>

<style scoped>
  .el-dropdown-link {
    cursor: pointer;
    color: #f1eeee;
  }
</style>
