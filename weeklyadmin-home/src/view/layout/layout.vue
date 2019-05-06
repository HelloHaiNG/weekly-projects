<template>
  <el-container>
    <el-header><Navbar/></el-header>
    <el-container>
      <el-aside width="200px"><Sidebar/></el-aside>
      <el-container>
        <el-main><AppMain/></el-main>
      </el-container>
    </el-container>
  </el-container>
</template>

<script>
import { Navbar, Sidebar, AppMain, Foot } from './components'
import { mapGetters } from 'vuex'
import store from '@/store'
export default {
  name: 'Layout',
  components: {
    Navbar,
    Sidebar,
    AppMain,
    Foot
  },
  computed: {
    ...mapGetters([
      'isShowSide'
    ]),
    isShowSide () {
      return store.getters.isShowSide
    }
  },
  data () {
    return {
      isShow: true
    }
  }
}
</script>

<style scoped>
 .side {
   margin-left: 400px;
   margin-top: 50px;
 }
</style>
