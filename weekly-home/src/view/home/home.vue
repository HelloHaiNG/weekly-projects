<template>
  <div class="layui-container">
  <div class="tableStyle">
  <el-table
    :data="tableData"
    style="width: 70%">
    <el-table-column
      prop="weeklyName"
      label="周报名称"
      width="300">
    </el-table-column>
    <el-table-column
      prop="modifyTime"
      label="修改时间"
      :formatter="formatDate"
      width="300">
    </el-table-column>
    <el-table-column
      prop="downloadCount"
      label="下载次数"
      width="200">
    </el-table-column>
    <el-table-column
      label="操作"
      width="250">
      <template slot-scope="scope">
        <el-button @click="goWriteWeeklyReload(scope.row)" type="text" size="small">写周报</el-button>
        <el-button @click="downLoadWeekly(scope.row)" type="text" size="small">下载</el-button>
        <el-button @click="weeklyIdDetail(scope.row)" type="text" size="small">详情</el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination
    :page-size="this.pageSize"
    :pager-count="5"
    prev-text="上一页"
    next-text="下一页"
    layout="prev, pager, next"
    :total="this.total"
    @current-change="handleCurrentChange"
    class="p">
  </el-pagination>
  </div>
  <div><login-dialog :login-dialog-visible="loginDialogVisible" @handleLoginClose="handleLoginClose"/></div>
  </div>
</template>

<script>
import LoginDialog from '@/view/layout/components/LoginDialog'
import store from '@/store'
import moment from 'moment'
export default {
  name: 'Home',
  components: {
    'login-dialog': LoginDialog
  },
  created: function () {
    this.$store.commit('SET_IS_SHOW_SIDE', true)
    this.getWeeklyReportList()
  },
  data () {
    return {
      loginDialogVisible: false,
      keyword: '',
      item: {'weeklyName': '', 'downloadCount': ''},
      tableData: [],
      total: 0,
      pageSize: 13,
      pageNum: 1
    }
  },
  mounted () {
    this.$nextTick(function () {
      this.handleSarch()
      this.getWeeklyReportList()
    })
  },
  computed: {
  },
  watch: {
  },
  methods: {
    formatDate: function (row, column) {
      var date = row[column.property]
      return moment(date).format('YYYY-MM-DD HH:mm:ss')
    },
    handleVerifUser () {
      this.userInfo = store.getters.userInfo
      if (!this.userInfo.username) {
        this.loginDialogVisible = true
        return false
      } else {
        return true
      }
    },
    handleCurrentChange (val) {
      this.pageNum = val
      this.getWeeklyReportList()
    },
    handleSarch () {
      this.eventVue.$on('getSarch', (message) => { // 这里最好用箭头函数，不然this指向有问题
        this.keyword = message
        this.pageNum = 1
        if (this.keyword) {
          const url = '/weeklyservice/weeklyReport/findWeeklyByName'
          const data = {pageNum: this.pageNum, pageSize: this.pageSize, weeklyName: this.keyword}
          this.$store.dispatch('Post', {'url': url, 'data': data}).then(res => {
            console.log(res)
            this.total = res.data.total
            this.tableData = res.data.list
          })
        } else {
          this.getWeeklyReportList()
        }
      })
    },
    getWeeklyReportList () {
      const url = '/weeklyservice/weeklyReport/pageList'
      const data = {pageNum: this.pageNum, pageSize: this.pageSize}
      this.$store.dispatch('Post', {'url': url, 'data': data}).then(res => {
        this.total = res.data.total
        this.tableData = res.data.list
      })
    },
    goWriteWeeklyReload (row) {
      const accessToken = sessionStorage.getItem('accessToken')
      if (accessToken != null) {
        const weeklyReport = {weeklyId: row.weeklyId, weeklyName: row.weeklyName}
        sessionStorage.setItem('weeklyReport', JSON.stringify(weeklyReport))
        this.$router.push('/writeWeekly')
      } else {
        this.$message.error('您尚未登陆')
      }
    },
    downLoadWeekly (row) {
      if (this.handleVerifUser()) {
        const accessToken = JSON.parse(sessionStorage.getItem('accessToken'))
        const weeklyId = row.weeklyId
        const url = '/weeklycommon/download/weeklyReport'
        const data = {weeklyId: weeklyId, token: accessToken}
        this.$store.dispatch('Post', {'url': url, 'data': data}).then(res => {
          console.log(res)
          if (res.restCode === '0000') {
            this.$message({
              message: '下载成功',
              type: 'success'
            })
            row.downloadCount = res.data
          } else {
            this.$message.error(res.restMsg)
          }
        })
      }
    },
    weeklyIdDetail (row) {
      if (this.handleVerifUser()) {
        const accessToken = JSON.parse(sessionStorage.getItem('accessToken'))
        const url = '/weeklyservice/detail/weeklyDetailByUser'
        const weeklyDetail = {weeklyId: row.weeklyId, token: accessToken}
        this.$store.dispatch('Post', {'url': url, 'data': weeklyDetail}).then(res => {
          if (res.restCode === '0000') {
            sessionStorage.setItem('weeklyDetail', JSON.stringify(weeklyDetail))
            this.$router.push('/weeklyDetail')
          } else {
            this.$message.error(res.restMsg)
          }
        })
      }
    },
    handleLoginClose () {
      this.loginDialogVisible = false
    }
  }
}
</script>

<style scoped>
 .tableStyle {
   margin-top: 50px;
   margin-left: 100px;
   width: 1500px;
 }
  .p {
    margin-left: 350px;
    margin-top: 20px;
  }
</style>
