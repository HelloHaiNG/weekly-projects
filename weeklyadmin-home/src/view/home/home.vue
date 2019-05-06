<template>
  <div>
    首页
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import store from '@/store'
export default {
  name: 'home',
  data () {
    return {
      breadcrumbItems: ['导航一'],
      imgUrl: './static/cweg.jpg'
    }
  },
  computed: {
    ...mapGetters([
      'userInfo'
    ]),
    userInfo () {
      console.log(store.getters.userInfo)
      return store.getters.userInfo
    }
  },
  methods: {
    handleSelect (key, keyPath) {
      switch (key) {
        case '1':
          this.$router.push('/page1')
          this.breadcrumbItems = ['导航一']
          break
        case '2':
          this.$router.push('/page2')
          this.breadcrumbItems = ['导航二']
          break
        case '3':
          this.$router.push('/page3')
          this.breadcrumbItems = ['导航三']
          break
      }
    },
    handleCommand (data) {
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>
  .menu {
    margin-left: 200px;
  }
  .el-dropdown-link {
    cursor: pointer;
    color: #f1eeee;
    text-align: center;
  }
</style>
