<template>
  <div class="fly-panel fly-column">
    <div class="layui-container">
      <div class="fly-column-right layui-hide-xs">
        <div style="position: relative;margin-left: 10px;display: inline-block;vertical-align: top;">
          <i class="layui-icon" v-on:click="handleSerch" style="loat: left;margin-left: 274px;position: absolute;"></i>
          <input type="text" name="1"  v-model="search" placeholder="请输入搜索内容" style="height: 38px;width: 300px; line-height: 38px; border: 1px solid #e6e6e6; background-color: #fff; border-radius: 2px;  box-sizing: border-box!important;">
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
export default {
    data(){
      return {
        search: ''
      }
    },
    methods: {
      handleSerch() {
        this.eventVue.$emit('getSarch', this.search)
      }
    }
  }
</script>
