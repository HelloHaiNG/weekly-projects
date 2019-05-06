import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/view/login/login'
import Home from '@/view/home/home'
import Navbar from '@/view/layout/components/Navbar'
import Sidebar from '@/view/layout/components/Sidebar'
import AppMain from '@/view/layout/components/AppMain'
import Foot from '@/view/layout/components/foot'
import Weekly from '@/view/manager/weekly/weekly'
import Group from '@/view/manager/group/group'
import Layout from '../view/layout/layout'
Vue.use(Router)
export default new Router({
  routes: [
    {
      path: '/',
      name: 'Layout',
      component: Layout,
      redirect: '/home',
      children: [
        {
          path: '/navbar',
          name: 'Navbar',
          component: Navbar
        }, {
          path: '/foot',
          name: 'Foot',
          component: Foot
        }, {
          path: '/sidebar',
          name: 'Sidebar',
          component: Sidebar
        }, {
          path: '/appMain',
          name: 'AppMain',
          component: AppMain,
          children: [
            {
              path: '/home',
              name: 'Home',
              component: Home
            },
            {
              path: '/group',
              name: 'Group',
              component: Group
            }, {
              path: '/weekly',
              name: 'Weekly',
              component: Weekly
            }
          ]
        }
      ]
    }, {
      path: '/login',
      name: 'Login',
      component: Login
    }
  ]
})
// export default new Router({
//   routes: [
//     {
//       path: '/',
//       name: 'HelloWorld',
//       component: HelloWorld
//     },
//     {
//       path: '/login',
//       name: 'Login',
//       component: Login
//     },
//     {
//       path: '/home',
//       name: 'Home',
//       component: Home,
//       children: [
//         {
//           path: '/page1',
//           name: 'Page1',
//           component: Page1
//         },
//         {
//           path: '/page2',
//           name: 'Page2',
//           component: Page2
//         },
//         {
//           path: '/page3',
//           name: 'Page3',
//           component: Page3
//         }
//       ]
//     }
//   ]
// })
