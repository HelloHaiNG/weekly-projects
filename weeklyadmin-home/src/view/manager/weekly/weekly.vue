<template>
  <div class="fly-panel fly-panel-userInfo" pad20>
    <div class="layui-tab layui-tab-brief" lay-filter="userInfo">
      <div class="p">
        <el-form ref="weekly" :model="weekly" label-width="80px">
          <el-row>
            <el-col :span="4">
              <el-button type="primary" @click="addWeekly">新建</el-button>
            </el-col>
            <el-col :span="5">
              <el-form-item label="周报名称">
                <el-input v-model="weekly.weeklyName" placeholder="周报名称"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-button type="primary" @click="queryMyWeekly">查询</el-button>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <div class="tableStyle">
        <el-table
          :data="tableData"
          style="width: 95%"
        >
          <el-table-column
            prop="weeklyName"
            label="周报名称"
            width="300">
          </el-table-column>
          <el-table-column
            prop="creatTime"
            label="创建时间"
            :formatter="formatDate"
            width="300">
          </el-table-column>
          <el-table-column
            prop="modifyUser"
            label="创建人"
            width="220">
          </el-table-column>
          <el-table-column
            prop="downloadCount"
            label="下载人数"
            width="220">
          </el-table-column>
          <el-table-column
            prop="status"
            label="状态"
            width="250">
            <template slot-scope="scope">
              <el-switch @change="handlChangeStatus(scope.row)"
                         v-model="scope.row.status"
                         active-text="禁止"
                         active-color="#ff4949"
                         inactive-color="#13ce66"
                         :active-value="0" :inactive-value="1"
                         inactive-text="开启">
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            width="250">
            <template slot-scope="scope">
              <el-button @click="downLoadWeekly(scope.row)" type="text" size="small">下载</el-button>
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
          class="center">
        </el-pagination>
      </div>
    </div>
    <el-dialog
      :visible.sync="weeklyDialogVisible"
      :before-close="handleClose"
      width="25%"
      title="添加周报">
      <el-form :model="addWeeklyForm" label-width="40px">
        <el-form-item label="月份">
          <el-date-picker
            v-model="addWeeklyForm.date"
            type="month"
            placeholder="选择月">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="周数">
          <el-select v-model="addWeeklyForm.weeklyNum" placeholder="请选择">
            <el-option
              v-for="item in weeklys"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-button type="primary" @click="addWeekly">添加</el-button>
      </el-form>
    </el-dialog>
    <div><login-dialog :login-dialog-visible="loginDialogVisible" @handleLoginClose="handleLoginClose"/></div>
  </div>
</template>

<script>
import moment from 'moment'
import LoginDialog from '@/view/layout/components/LoginDialog'
import store from '@/store'
export default {
  name: 'weekly',
  components: {
    'login-dialog': LoginDialog
  },
  data () {
    return {
      loginDialogVisible: false,
      weeklyDialogVisible: false,
      weeklyIds: [],
      item: {'weeklyName': '', 'modifyUser': '', 'creatTime': '', 'downloadCount': '', 'status': ''},
      tableData: [],
      total: 0,
      pageSize: 13,
      pageNum: 1,
      multipleSelection: [],
      weekly: {
        weeklyName: ''
      },
      addWeeklyForm: {
        date: '',
        weeklyNum: ''
      },
      weeklys: [{
        value: '第一周',
        label: '第一周'
      }, {
        value: '第二周',
        label: '第二周'
      }, {
        value: '第三周',
        label: '第三周'
      }, {
        value: '第四周',
        label: '第四周'
      }]
    }
  },
  created: function () {
    this.getWeeklyReportList()
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
    addWeekly () {
      if (this.handleVerifUser()) {
        this.weeklyDialogVisible = true
      }
    },
    handleClose () {
      this.weeklyDialogVisible = false
    },
    getWeeklyReportList () {
      const url = '/weeklyadminweb/weeklyadminweb/pageList'
      const data = {pageNum: this.pageNum, pageSize: this.pageSize}
      this.$store.dispatch('Post', {'url': url, 'data': data}).then(res => {
        this.total = res.data.total
        this.tableData = res.data.list
      })
    },
    handlChangeStatus (row) {
      if (this.handleVerifUser()) {
        const url = '/weeklyadminweb/weeklyadminweb/updateStatus'
        const accessToken = JSON.parse(sessionStorage.getItem('accessToken'))
        const data = {weeklyId: row.weeklyId, status: row.status, token: accessToken}
        this.$store.dispatch('Post', {'url': url, 'data': data}).then(res => {
          console.log(res)
          if (res.restCode === '0000') {
            this.$message({
              message: '修改成功',
              type: 'success'
            })
          } else {
            this.$message.error(res.restMsg)
          }
          this.getWeeklyReportList()
        })
      }
    },
    downLoadWeekly (row) {
      if (this.handleVerifUser()) {
        const weeklyId = row.weeklyId
        const url = '/weeklycommon/download/byWeeklyId'
        const data = {weeklyId: weeklyId}
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
    formatDate: function (row, column) {
      var date = row[column.property]
      return moment(date).format('YYYY-MM-DD HH:mm:ss')
    },
    queryMyWeekly () {
      const weeklyName = this.weekly.weeklyName
      if (weeklyName) {
        const url = '/weeklyadminweb/weeklyadminweb/findWeeklyByName'
        const data = {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          weeklyName: this.weekly.weeklyName
        }
        this.$store.dispatch('Post', {'url': url, 'data': data}).then(res => {
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
    addWeekly () {
      const date = moment(this.addWeeklyForm.date).format('YYYY-MM-DD')
      const year = date.substring(0, 4) + '年'
      const month = date.substring(5, 7) + '月'
      const weeklyName = year.concat(month).concat(this.addWeeklyForm.weeklyNum)
      const accessToken = JSON.parse(sessionStorage.getItem('accessToken'))
      const url = '/weeklyadminweb/weeklyadminweb/addWeeklyReport'
      const data = {weeklyName: weeklyName, token: accessToken}
      this.$store.dispatch('Post', {'url': url, 'data': data}).then(res => {
        if (res.restCode === '0000') {
          this.$message({
            message: '添加周报成功',
            type: 'success'
          })
        } else {
          this.$message.error('添加周报失败')
        }
        this.weeklyDialogVisible = false
        this.getWeeklyReportList()
      }).catch(() => {
        this.$message.error('后台处理异常，联系管理员')
      })
    }
  }
}
</script>

<style scoped>
  .p {
    margin-top: 40px;
  }
  .center {
    margin-left: 600px;
    margin-top: 20px;
  }
</style>
