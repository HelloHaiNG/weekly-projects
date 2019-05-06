<template>
  <div class="fly-panel fly-panel-userInfo" pad20>
    <div class="layui-tab layui-tab-brief" lay-filter="userInfo">
      <ul id="LAY_mine" class="layui-tab-title">
        <li class="layui-this" lay-id="info">我的资料</li>
      </ul>
      <div class="layui-tab-content" style="padding: 20px 0;">
        <div class="p">
          <el-form ref="user" :model="user" label-width="80px">
            <el-row>
              <el-col :span="8">
                <el-form-item label="用户名">
                  <el-input v-model="user.username" readonly></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="密码">
                  <el-input v-model="user.password" readonly></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="9">
                <el-form-item label="组别">
                  <el-select
                    v-model="groupName"
                    filterable
                    remote
                    reserve-keyword
                    placeholder="请输入关键词"
                    :remote-method="remoteMethod"
                    :loading="loading">
                    <el-option
                      v-for="item in options"
                      :key="item"
                      :label="item.label"
                      :value="item">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="10" class="p">
                <el-button type="primary" @click="updataMyGroup">更新</el-button>
              </el-col>
            </el-row>
          </el-form>
        </div>
      </div>
    </div>
    <div><login-dialog :login-dialog-visible="loginDialogVisible" @handleLoginClose="handleLoginClose"/></div>
  </div>
</template>

<script>
import LoginDialog from '@/view/layout/components/LoginDialog'
import store from '@/store'
import { mapGetters } from 'vuex'
export default {
  name: 'UserInfo',
  components: {
    'login-dialog': LoginDialog
  },
  computed: {
    ...mapGetters([
      'userInfo'
    ]),
    userInfo() {
      return store.getters.userInfo
    }
  },
  created: function () {
    this.user.username = store.getters.userInfo.username
    this.user.password = store.getters.userInfo.password
    this.getUserGroupName()
    this.findAllGroup()
  },
  data() {
    return {
      loginDialogVisible: false,
      user: {
        username: '',
        password: '',
      },
      loading: false,
      weeklyGroup: [],
      options: [],
      groupName: '',
      list: [],
    }
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
    getUserGroupName () {
      const url = '/weeklyusercenter/frontUser/findByUserIdToken'
      const accessToken = JSON.parse(sessionStorage.getItem('accessToken'))
      const data = {token: accessToken}
      this.$store.dispatch('Post', {'url': url, 'data': data}).then(res => {
        if (res.restCode === '0000') {
          const groupId = res.data.groupId
          const url = '/weeklyservice/group/findById'
          const data = {groupId: groupId}
          this.$store.dispatch('Post', {'url': url, 'data': data}).then(res => {
            if (res.restCode === '0000') {
              this.groupName = res.data.groupName
            }
          })
        }
      })
    },
    remoteMethod(query) {
      if (query !== '') {
        this.loading = true;
        setTimeout(() => {
          this.loading = false;
          this.options = this.weeklyGroup.filter(item => {
            if (item.indexOf(query) >= 0) {
              return item
            }
          });
        }, 200);
      }
    },
    findAllGroup() {
      const url = '/weeklyservice/group/allGroupOnStatus'
      this.$store.dispatch('Post', {'url': url}).then(res => {
        if (res.restCode === '0000') {
          this.weeklyGroup = res.data
        } else {
          this.$message.error(res.restMsg)
        }
      })
    },
    updataMyGroup () {
      if (this.handleVerifUser()) {
        const url = '/weeklyservice/group/findGroupByName'
        const accessToken = JSON.parse(sessionStorage.getItem('accessToken'))
        const data = {groupName: this.groupName}
        this.$store.dispatch('Post', {'url': url, 'data': data}).then(res => {
          if (res.restCode === '0000') {
            const groupId = res.data.groupId
            const url = '/weeklyusercenter/front/updateGroupId'
            const data = {token: accessToken, groupId: groupId}
            this.$store.dispatch('Post', {'url': url, 'data': data}).then(res => {
              console.log(res)
              if (res.restCode === '0000') {
                this.$message({
                  message: '更新成功',
                  type: 'success'
                })
              }
            })
          }
        })
      }
    },
  }
}
</script>

<style >
  .p {
    margin-left: 30px;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #009688;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 131px;
    height: 131px;
    line-height: 131px;
    text-align: center;
  }
  .avatar {
    width: 220px;
    height: 220px;
    display: block;
  }
  .avatar-add img {
    position: absolute;
    left: 70%;
    top: 43%;
    width: 120px;
    height: 120px;
    margin: -50px 0 0 -84px;
    border-radius: 100%;
  }

  .input_color {
    height: 38px;
    line-height: 1.3;
    border-width: 1px;
    border-style: solid;
    background:#F2F2F2;
    display: block;
    width: 100%;
    padding-left: 10px;
  }
</style>
