<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',
  provider() {
    return {
      reload: this.reload
    }
  }
}
</script>

<style>
</style>
