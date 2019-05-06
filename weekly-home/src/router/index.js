import Vue from 'vue'
import Router from 'vue-router'
import Layout from '../view/layout/layout'
import Index from '../view/index'
// import HelloWorld from '@/components/HelloWorld'
// import Login from '@/view/login/weekly_login'
// import Home from '@/view/home/home'
// import WriteWeekly from '@/view/weeklyReport/writeWeekly'
// import WeeklyDetail from '@/view/weeklyDetail/weeklyDetail'
// import Layout from '@/view/layout/layout'
// import Header from '@/view/layout/header'
// import Foot from '@/view/layout/foot'
Vue.use(Router)

export const constantRouterMap = [
  {
    path: '/',
    component: Layout,
    redirect: '/home',
    name: 'Home',
    hidden: true,
    children: [{
      path: 'home',
      component: () => import('@/view/home/home')
    },
    {
      path: 'login',
      name: 'login',
      component: () => import('@/view/login/weekly_login')
    },
    {
      path: 'weeklyDetail',
      name: 'weeklyDetail',
      component: () => import('@/view/weeklyDetail/weeklyDetail')
    },
    {
      path: 'writeWeekly',
      name: 'writeWeekly',
      component: () => import('@/view/weeklyReport/writeWeekly')
    },
    {
      path: 'manager',
      name: 'manager',
      component: () => import('@/view/manager/manager'),
      children: [{
        path: 'user',
        name: 'user',
        component: () => import('@/view/manager/user/user_info')
      },
      {
        path: 'user_weekly',
        name: 'user_weekly',
        component: () => import('@/view/manager/weekly/user_weekly')
      }]
    }]
  },
  { path: '*', redirect: '/', hidden: true },
  {path: '/index', name: 'Index', component: Index}
]
export default new Router({
  // mode: 'history', //  后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
