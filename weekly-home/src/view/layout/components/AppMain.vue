<template>
  <section class="app-main">
    <transition name="fade" mode="out-in">
      <!-- <router-view :key="key"></router-view> -->
      <router-view v-if="isRouterAppMain"/>
    </transition>
  </section>
</template>

<script>
export default {
  name: 'AppMain',
  computed: {
  },
  data () {
    return {
      isRouterAppMain: true
    }
  }
}
</script>

<style scoped>
.app-main {
  /*50 = navbar  */
  min-height: calc(100vh - 50px);
  position: relative;
  overflow: hidden;
}
</style>
