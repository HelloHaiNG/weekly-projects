<template>
  <div>
    <el-form class="form" ref="weeklyDetail" :model="weeklyDetail" label-width="100px">
    <el-form-item label="周报名称">
    <el-input v-model="weeklyDetail.weeklyName" readonly>{{weeklyDetail.weeklyName}}</el-input>
    </el-form-item>
    <el-form-item label="本周工作内容">
      <tinymce id="d1" v-model="weeklyDetail.thisWeekContent" :other_options="{min_height:100,'height':100,forced_root_block:''}">
      </tinymce>
    </el-form-item>
      <el-form-item label="下周工作计划">
        <tinymce id="d2" v-model="weeklyDetail.nextWeekContent" :other_options="{min_height:100,'height':100,forced_root_block:''}">
        </tinymce>
      </el-form-item>
    <el-form-item label="问题">
      <tinymce id="d3" v-model="weeklyDetail.trouble" :other_options="{min_height:100,'height':100,forced_root_block:''}">
      </tinymce>
    </el-form-item>
    <el-form-item>
    <el-button type="primary" @click="writeWeekly">提交</el-button>
    <el-button @click="resetForm">重置</el-button>
    </el-form-item>
    </el-form>
    <div><login-dialog :login-dialog-visible="loginDialogVisible" @handleLoginClose="handleLoginClose"/></div>
  </div>
</template>

<script>
import LoginDialog from '@/view/layout/components/LoginDialog'
import store from '@/store'
export default {
  name: 'WriteWeekly',
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
    const weeklyReport = JSON.parse(sessionStorage.getItem('weeklyReport'))
    this.weeklyDetail.weeklyId = weeklyReport.weeklyId
    this.weeklyDetail.weeklyName = weeklyReport.weeklyName
  },
  methods: {
    handleVerifUser () {
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
    writeWeekly () {
      if (this.handleVerifUser()) {
        const accessToken = JSON.parse(sessionStorage.getItem('accessToken'))
        const url = '/weeklyservice/detail/addWeeklyDetail'
        const data = {
          weeklyId: this.weeklyDetail.weeklyId,
          weeklyName: this.weeklyDetail.weeklyName,
          thisWeekContent: this.weeklyDetail.thisWeekContent,
          nextWeekContent: this.weeklyDetail.nextWeekContent,
          trouble: this.weeklyDetail.trouble,
          token: accessToken
        }
        this.$store.dispatch('Post', {'url': url, 'data': data}).then(res => {
          console.log(res)
          if (res.restCode === '0000') {
            this.$message({
              message: '周报书写成功',
              type: 'success'
            })
            this.$router.push('/home')
          } else {
            const message = res.restMsg
            this.$message.error(message)
          }
        }).catch(() => {
          this.$message.error('后台处理异常，联系管理员')
        })
      }
    },
    resetForm () {
      this.weeklyDetail.thisWeekContent = ''
      this.weeklyDetail.nextWeekContent = ''
      this.weeklyDetail.trouble = ''
    }
  }
}
</script>

<style>
  .form {
    position: absolute;
    left: 0;
    right: 0;
    width: 720px;
    padding: 30px 35px 15px 35px;
    margin: 1px auto;
  }
</style>
