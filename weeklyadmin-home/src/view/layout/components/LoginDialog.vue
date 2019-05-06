<template>
  <el-dialog
    title="登陆"
    :visible.sync="loginDialogVisible"
    :before-close="handleLoginClose"
    width="20%">
    <div>
      <el-form :model="login" label-width="60px">
        <el-form-item label="用户名">
          <el-input v-model="login.username"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input type="password" v-model="login.password"></el-input>
        </el-form-item>
          <el-button type="primary" @click="getLogin">登陆</el-button>
          <el-button  @click="resetForm">重置</el-button>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: 'LoginDialog',
  props: {
    loginDialogVisible: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      login: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    handleLoginClose () {
      this.$emit('handleLoginClose')
    },
    getLogin () {
      const userInfo = {username: this.login.username, password: this.login.password}
      this.$store.dispatch('Login', userInfo).then(res => {
        console.log(res)
        if (res.restCode === '0000') {
          this.$message({
            message: '登陆成功',
            type: 'success'
          })
          this.$emit('handleLoginClose')
          sessionStorage.setItem('accessToken', JSON.stringify(res.data))
          this.$store.commit('USER_INFO', userInfo)
          this.$router.push('/home')
        } else {
          this.$message.error('登陆失败')
        }
      }).catch(() => {
        this.$message.error('后台处理异常，联系管理员')
      })
    },
    resetForm () {
      this.login.username = ''
      this.login.password = ''
    }
  }
}
</script>

<style >
  .form {
    position: absolute;
    left: 0;
    right: 0;
    width: 450px;
    padding: 30px 35px 15px 35px;
    margin: 200px auto;
  }
  .button {
    position: absolute;
    left: 140px;
    right: 0;
    width: 450px;
    padding: 30px 35px 15px 35px;
  }

</style>
