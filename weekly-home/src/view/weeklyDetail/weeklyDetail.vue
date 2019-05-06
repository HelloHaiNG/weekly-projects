<template>
  <div>
    <el-form class="form" ref="weeklyDetail" :model="weeklyDetail" label-width="100px">
      <el-form-item label="周报名称">
        <el-input v-model="weeklyDetail.weeklyName" readonly>{{weeklyDetail.weeklyName}}</el-input>
      </el-form-item>
      <el-form-item label="本周工作内容">
        <el-textarea v-model="weeklyDetail.thisWeekContent" readonly>{{weeklyDetail.thisWeekContent}}</el-textarea>
      </el-form-item>
      <el-form-item label="下周工作计划">
        <el-textarea v-model="weeklyDetail.nextWeekContent" readonly>{{weeklyDetail.nextWeekContent}}</el-textarea>
      </el-form-item>
      <el-form-item label="问题">
        <el-textarea v-model="weeklyDetail.trouble" readonly>{{weeklyDetail.trouble}}</el-textarea>
      </el-form-item>
    </el-form>
    <div><login-dialog :login-dialog-visible="loginDialogVisible" @handleLoginClose="handleLoginClose"/></div>
  </div>
</template>

<script>
import LoginDialog from '@/view/layout/components/LoginDialog'
import store from '@/store'
export default {
  name: 'weeklyDetail',
  components: {
    'login-dialog': LoginDialog
  },
  data () {
    return {
      loginDialogVisible: false,
      weeklyDetail: {
        weeklyId: '',
        weeklyName: '',
        thisWeekContent: '',
        nextWeekContent: '',
        trouble: ''
      }
    }
  },
  created: function () {
    this.getWeeklyDetail()
  },
  methods: {
    handleVerifUser() {
      this.userInfo = store.getters.userInfo
      if (!this.userInfo.username) {
        this.loginDialogVisible = true
        return false
      } else {
        return true
      }
    },
    handleLoginClose () {
      this.loginDialogVisible = false
    },
    getWeeklyDetail () {
      if (this.handleVerifUser()) {
        const weeklyDetail = JSON.parse(sessionStorage.getItem('weeklyDetail'))
        this.weeklyDetail.weeklyId = weeklyDetail.weeklyId
        const token = weeklyDetail.token
        const data = {weeklyId: this.weeklyDetail.weeklyId, token: token}
        const url = '/weeklyservice/detail/weeklyDetailByUser'
        this.$store.dispatch('Post', {'url': url, 'data': data}).then(res => {
          console.log(res)
          if (res.restCode === '0000') {
            this.weeklyDetail.weeklyId = res.data.weeklyId
            this.weeklyDetail.weeklyName = res.data.weeklyName
            this.weeklyDetail.thisWeekContent = res.data.thisWeekContent
            this.weeklyDetail.nextWeekContent = res.data.nextWeekContent
            this.weeklyDetail.trouble = res.data.trouble
          } else {
            this.$message.error(res.restMsg)
          }
        })
      }
    }
  }
}
</script>

<style scoped>

</style>
