<template>
  <div class="menuDiv">
    <el-menu default-active="1" class="el-menu-vertical-demo" background-color="#545c64" text-color="#fff">
      <el-submenu v-for="menu in menus" :index="menu.menuId" :key="menu.menuId">
        <template slot="title">
          <i class="el-icon-setting"></i>
          <span>{{menu.menuName}}</span>
        </template>

        <el-menu-item v-for="subMenu in menu.subMenus" :index="subMenu.subMenuId" :key="subMenu.subMenuId" @click="handleClick(subMenu.subMenuPath)">
          <i class="el-icon-location"></i>
          <span>{{subMenu.subMenuName}}</span>
        </el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
  <!--<div class="layui-container fly-marginTop fly-user-main">-->
    <!--<ul class="layui-nav layui-nav-tree layui-inline" lay-filter="user">-->
      <!--<li class="layui-nav-item " v-for="(item, index) in menuList" :key="index">-->
        <!--<router-link :to="item.listLink"><i class="layui-icon">{{item.icon}}</i>{{item.title}}</router-link>-->
      <!--</li>-->
    <!--</ul>-->

    <!--<div class="site-tree-mobile layui-hide">-->
      <!--<i class="layui-icon">&#xe602;</i>-->
    <!--</div>-->
    <!--<div class="site-mobile-shade"></div>-->

    <!--<div class="site-tree-mobile layui-hide">-->
      <!--<i class="layui-icon">&#xe602;</i>-->
    <!--</div>-->
    <!--<div class="site-mobile-shade"/>-->
    <!--<transition  name="fade" mode="out-in">-->
      <!--<router-view v-if="isRouterAlive"></router-view>-->
    <!--</transition>-->
  <!--</div>-->
</template>

<script>
export default {
  name: 'siderbar',
  data () {
    return {
      menus: [
        {
          menuId: '1',
          menuName: '周报管理',
          subMenus: [
            {
              subMenuId: '1-1',
              subMenuName: '周报管理',
              subMenuPath: '/weekly'
            }
          ]
        }, {
          menuId: '2',
          menuName: '组别管理',
          subMenus: [
            {
              subMenuId: '2-1',
              subMenuName: '组别管理',
              subMenuPath: '/group'
            }
          ]
        }
      ],
      isRouterAlive: true,
      menuList: [
        {
          listLink: '/weekly',
          title: '周报管理',
          icon: ''
        },
        {
          listLink: '/group',
          title: '组别管理',
          icon: ''
        }
      ]
    }
  },
  methods: {
    handleClick (path) {
      this.$router.push(path)
    }
  }
}
</script>

<style scoped>
  .menuDiv {
    text-align: left;
  }
</style>
