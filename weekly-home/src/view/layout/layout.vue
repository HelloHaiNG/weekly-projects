<template>
  <div class="app-wrapper">
    <navbar/>
    <sidebar v-show="isShowSide"  />
    <div>
      <app-main/>
    </div>
    <foot/>
  </div>
</template>

<script>
import { Navbar,Sidebar,AppMain,Foot } from './components'
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
    isShowSide() {
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
  /*@import "src/styles/mixin.scss";*/
  .app-wrapper {
    @include clearfix;
    position: relative;
    height: 100%;
    width: 100%;
  }
  .drawer-bg {
    background: #000;
    opacity: 0.3;
    width: 100%;
    top: 0;
    height: 100%;
    position: absolute;
    z-index: 999;
  }
</style>
