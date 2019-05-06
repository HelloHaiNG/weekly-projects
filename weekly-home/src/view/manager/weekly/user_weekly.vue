<template>
  <div class="fly-panel fly-panel-userInfo" pad20>
    <div class="layui-tab layui-tab-brief" lay-filter="userInfo">
      <ul id="LAY_mine" class="layui-tab-title">
        <li class="layui-this" lay-id="info">我的周报</li>
      </ul>
      <div class="p">
        <el-form ref="login" :model="login" label-width="80px">
          <el-row>
            <el-col :span="9">
              <el-form-item label="周报名称">
                <el-input v-model="login.weeklyName" placeholder="周报名称"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-button type="primary" @click="queryMyWeekly">查询</el-button>
            </el-col>
            <el-col :span="4">
              <el-button type="primary" @click="downMyWeekly">下载</el-button>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <div class="tableStyle">
        <el-table
          :data="tableData"
          style="width: 80%"
          @selection-change="handleSelectionChange">
          <el-table-column
            type="selection"
            width="55">
          </el-table-column>
          <el-table-column
            prop="weeklyName"
            label="周报名称"
            width="300">
          </el-table-column>
          <el-table-column
            prop="editorTime"
            label="编辑时间"
            :formatter="formatDate"
            width="370">
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
          class="center">
        </el-pagination>
      </div>
    </div>
    <div><login-dialog :login-dialog-visible="loginDialogVisible" @handleLoginClose="handleLoginClose"/></div>
  </div>
</template>

<script>
import moment from 'moment'
import LoginDialog from '@/view/layout/components/LoginDialog'
export default {
  name: 'user_weekly',
  components: {
    'login-dialog': LoginDialog
  },
  data () {
    return {
      loginDialogVisible: false,
      weeklyIds: [],
      item: {'weeklyName': '', 'editorTime': ''},
      tableData: [],
      total: 0,
      pageSize: 13,
      pageNum: 1,
      multipleSelection: [],
      login: {
        weeklyName: ''
      }
    }
  },
  created: function () {
    this.getMyWeeklyReportList()
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
    getMyWeeklyReportList () {
      const url = '/weeklyservice/detail/pageListByUserId'
      const accessToken = JSON.parse(sessionStorage.getItem('accessToken'))
      const data = {token: accessToken, pageNum: this.pageNum, pageSize: this.pageSize}
      this.$store.dispatch('Post', {'url': url, 'data': data}).then(res => {
        this.total = res.data.total
        this.tableData = res.data.list
      })
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    formatDate: function (row, column) {
      var date = row[column.property]
      return moment(date).format('YYYY-MM-DD HH:mm:ss')
    },
    queryMyWeekly () {
      const weeklyName = this.login.weeklyName
      const accessToken = JSON.parse(sessionStorage.getItem('accessToken'))
      if (weeklyName) {
        const url = '/weeklyservice/detail/findWeeklyByName'
        const data = {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          token: accessToken,
          weeklyName: this.login.weeklyName
        }
        this.$store.dispatch('Post', {'url': url, 'data': data}).then(res => {
          console.log(res)
          this.total = res.data.total
          this.tableData = res.data.list
        })
      } else {
        this.getMyWeeklyReportList()
      }
    },
    handleCurrentChange (val) {
      this.pageNum = val
      this.getMyWeeklyReportList()
    },
    downMyWeekly () {
      if (this.handleVerifUser()) {
        const data = this.multipleSelection
        for (var i = 0; i < data.length; i++) {
          this.weeklyIds[i] = data[i].id
        }
        const accessToken = JSON.parse(sessionStorage.getItem('accessToken'))
        const datas = this.weeklyIds.join('-')
        const data2 = {ids: datas, token: accessToken}
        const url = '/weeklycommon/download/myWeeklys'
        this.$store.dispatch('Post', {'url': url, 'data': data2}).then(res => {
          console.log(res)
          if (res.restCode === '0000') {
            this.$message({
              message: '下载成功',
              type: 'success'
            })
          }
        })
      }
    }
  }
}
</script>

<style scoped>
  .p {
    margin-top: 40px;
  }
  .center {
    margin-left: 200px;
    margin-top: 20px;
  }
</style>
