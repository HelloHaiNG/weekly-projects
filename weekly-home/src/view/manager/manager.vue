<template>
  <div class="layui-container fly-marginTop fly-user-main">
    <ul class="layui-nav layui-nav-tree layui-inline" lay-filter="user">
      <li class="layui-nav-item " v-for="(item, index) in menuList" :key="index">
        <router-link :to="item.listLink"><i class="layui-icon">{{item.icon}}</i>{{item.title}}</router-link>
      </li>
    </ul>

    <div class="site-tree-mobile layui-hide">
      <i class="layui-icon">&#xe602;</i>
    </div>
    <div class="site-mobile-shade"></div>

    <div class="site-tree-mobile layui-hide">
      <i class="layui-icon">&#xe602;</i>
    </div>
    <div class="site-mobile-shade"/>
    <transition  name="fade" mode="out-in">
      <router-view v-if="isRouterAlive"></router-view>
    </transition>
  </div>
</template>

<script>
export default {
  name: 'manager',
  mounted () {
    this.$store.commit('SET_IS_SHOW_SIDE', false)
  },
  data () {
    return {
      isRouterAlive: true,
      menuList: [
        {
          listLink: '/manager/user',
          title: '基本设置',
          icon: ''
        },
        {
          listLink: '/manager/user_weekly',
          title: '周报管理',
          icon: ''
        }
      ]
    }
  },
  methods: {
    reload () {
      this.isRouterAlive = false
      this.$nextTick(function () {
        this.isRouterAlive = true
      })
    }
  }
}
</script>

<style>
  .layui-nav-tree .layui-nav-item a:hover {
     background-color:rgba(0,0,0,0);
  }
</style>
