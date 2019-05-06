<template>
  <el-container>
    <el-header>
      <div class="fly-header layui-bg-black">
        <div class="layui-container">
          <a class="fly-logo" href="./">
            <img :src="logoImg">
          </a>
          <ul v-show="true" class="layui-nav fly-nav-user">
            <!-- 未登入的状态 -->
            <li class="layui-nav-item">
              <a style="padding: 0 4px;"><router-link to="/login">登入</router-link></a>
            </li>
            <li class="layui-nav-item">
              <a href="./" style="padding: 0 4px;"> 签到
              </a>
            </li>
            <li class="layui-nav-item">
              <a href="./" style="padding: 0 4px;"> 签退
              </a>
            </li>
          </ul>
        </div>
      </div>
    </el-header>
    <el-main>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: 'index',
  data () {
    return {
      logoImg: './static/logo.png',
      imgUrl: './static/cweg.jpg',
      welcome: './static/welcome.gif'
    }
  }
}
</script>

<style>
</style>
