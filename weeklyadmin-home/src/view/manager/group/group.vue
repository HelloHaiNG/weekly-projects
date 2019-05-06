<template>
  <div class="fly-panel fly-panel-userInfo" pad20>
    <div class="layui-tab layui-tab-brief" lay-filter="userInfo">
      <div class="p">
        <el-form ref="groupFrom" :model="groupFrom" label-width="80px">
          <el-row>
            <el-col :span="4">
              <el-button type="primary" @click="addGroup">新建</el-button>
            </el-col>
            <el-col :span="5">
              <el-form-item label="组别名称">
                <el-input v-model="groupFrom.groupName" placeholder="组别名称"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-button type="primary" @click="queryGroup">查询</el-button>
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
            prop="groupName"
            label="组别名称"
            width="300">
          </el-table-column>
          <el-table-column
            prop="modifyTime"
            label="修改时间"
            :formatter="formatDate"
            width="300">
          </el-table-column>
          <el-table-column
            prop="modifyUser"
            label="创建人"
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
              <el-button @click="update(scope.row)" type="text" size="small">编辑</el-button>
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
      :visible.sync="groupUpdateDialogVisible"
      :before-close="handleGroupUpdateClose"
      width="20%"
      title="修改组别名称">
      <el-form :model="updateGroupFrom" label-width="80px">
        <el-form-item label="组别名称">
          <el-input v-model="updateGroupFrom.groupName"></el-input>
        </el-form-item>
        <el-button type="primary" @click="cancel">取消</el-button>
        <el-button type="primary" @click="commit">修改</el-button>
      </el-form>
    </el-dialog>
    <el-dialog
      :visible.sync="groupAddDialogVisible"
      :before-close="handleGroupAddClose"
      width="20%"
      title="添加组别">
      <el-form :model="addGroupForm" label-width="80px">
        <el-form-item label="组别名称">
          <el-input v-model="addGroupForm.groupName"></el-input>
        </el-form-item>
        <el-button type="primary" @click="cancel">取消</el-button>
        <el-button type="primary" @click="add">添加</el-button>
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
  name: 'group',
  components: {
    'login-dialog': LoginDialog
  },
  data () {
    return {
      loginDialogVisible: false,
      groupUpdateDialogVisible: false,
      groupAddDialogVisible: false,
      tableData: [],
      total: 0,
      pageSize: 10,
      pageNum: 1,
      groupFrom: {
        groupName: '',
      },
      addGroupForm: {
        groupName: ''
      },
      updateGroupFrom: {
        groupId: '',
        groupName: ''
      }
    }
  },
  created: function () {
    this.getGroupList()
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
    handleGroupAddClose() {
      this.groupAddDialogVisible = false
    },
    handleGroupUpdateClose () {
      this.groupUpdateDialogVisible = false
    },
    cancel () {
      this.updateGroupFrom.groupName = ''
      this.updateGroupFrom.groupId = ''
      this.addGroupForm.groupName = ''
      this.groupAddDialogVisible = false
      this.groupUpdateDialogVisible = false
    },
    addGroup () {
      if (this.handleVerifUser()) {
        this.groupAddDialogVisible = true
      }
    },
    add () {
      const accessToken = JSON.parse(sessionStorage.getItem('accessToken'))
      const url = '/weeklyadminweb/weeklyadminweb/addGroup'
      const data = {token: accessToken, groupName: this.addGroupForm.groupName}
      if (this.addGroupForm.groupName) {
        this.$store.dispatch('Post', {'url': url, 'data': data}).then(res => {
          if (res.restCode === '0000') {
            this.$message({
              message: '添加组别成功',
              type: 'success'
            })
            this.addGroupForm.groupName = ''
            this.groupAddDialogVisible = false
            this.getGroupList()
          } else {
            this.$message.error(res.restMsg)
          }
        })
      }
    },
    getGroupList () {
      const url = '/weeklyadminweb/weeklyadminweb/pageListGroup'
      const data = {pageNum: this.pageNum, pageSize: this.pageSize}
      this.$store.dispatch('Post', {'url': url, 'data': data}).then(res => {
        this.total = res.data.total
        this.tableData = res.data.list
      })
    },
    handlChangeStatus (row) {
      if (this.handleVerifUser()) {
        const url = '/weeklyadminweb/weeklyadminweb/updateGroupStatus'
        const accessToken = JSON.parse(sessionStorage.getItem('accessToken'))
        const data = {groupId: row.groupId, status: row.status, token: accessToken }
        this.$store.dispatch('Post', {'url': url, 'data': data}).then(res => {
          if (res.restCode === '0000') {
            this.$message({
              message: '修改成功',
              type: 'success'
            })
          } else {
            this.$message.error(res.restMsg)
          }
          this.getGroupList()
        })
      }
    },
    formatDate: function (row, column) {
      var date = row[column.property]
      return moment(date).format('YYYY-MM-DD HH:mm:ss')
    },
    queryGroup () {
      const groupName = this.groupFrom.groupName
      if (groupName) {
        const url = '/weeklyadminweb/weeklyadminweb/pageListByGroupName'
        const data = {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          groupName: this.groupFrom.groupName
        }
        this.$store.dispatch('Post', {'url': url, 'data': data}).then(res => {
          if (res.restCode === '0000') {
            this.total = res.data.total
            this.tableData = res.data.list
          }
        })
      } else {
        this.getGroupList()
      }
    },
    handleCurrentChange (val) {
      this.pageNum = val
      this.getGroupList()
    },
    update (row) {
      if (this.handleVerifUser()) {
        // this.updateGroupFrom = this._.cloneDeep(row)
        this.updateGroupFrom.groupName = row.groupName
        this.updateGroupFrom.groupId = row.groupId
        this.groupUpdateDialogVisible = true
      }
    },
    commit () {
      if (this.updateGroupFrom.groupName) {
        const url = '/weeklyadminweb/weeklyadminweb/updateGroup'
        const accessToken = JSON.parse(sessionStorage.getItem('accessToken'))
        const data = {token: accessToken, groupId: this.updateGroupFrom.groupId, groupName: this.updateGroupFrom.groupName}
        this.$store.dispatch('Post', {'url': url, 'data': data}).then(res => {
          console.log(res)
          this.updateGroupFrom.groupName = ''
          this.updateGroupFrom.groupId = ''
          this.groupUpdateDialogVisible = false
          this.getGroupList()
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
    margin-left: 600px;
    margin-top: 20px;
  }
</style>
