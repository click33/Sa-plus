<template>
  <div class="vue-box" :class="id != 0 ? 'sbot sbot-fluid': null">
    <!-- 参数栏 -->
    <div class="c-panel">
      <h4 class="c-title">添加一个管理员</h4>
      <el-form>
        <!-- 防止密码框被填充 -->
        <div style="height: 0px; overflow: hidden;">
          <el-input />
          <el-input type="password" />
        </div>
        <!-- 表单 -->
        <sa-item v-model="m.avatar" type="img" name="头像" br />
        <sa-item v-model="m.name" type="text" name="名称" br />
        <sa-item v-model="m.password" type="password" name="密码" br />
        <sa-item name="角色" br>
          <el-select v-model="m.roleId">
            <el-option label="请选择" :value="0" disabled />
            <el-option v-for="role in roleList" :key="role.id" :label="role.name" :value="role.id" />
          </el-select>
        </sa-item>
        <sa-item name="" br class="s-ok">
          <el-button type="primary" icon="el-icon-plus" @click="ok()">保存</el-button>
        </sa-item>
      </el-form>
    </div>
  </div>
</template>

<script>

export default {
  name: 'admin-add',
  props: { param: Object },
  data() {
    return {
      id: (this.param && this.param.id) || 0, 	// 超级对象
      m: this.crateModel(),
      roleList: []
    }
  },
  created: function() {
    // 加载角色
    sa.ajax('/role/getList', function(res) {
      this.roleList = res.data;	// 数据
    }.bind(this), { msg: null });
  },
  methods: {
    crateModel() {
      return {
        id: 0,
        name: '',
        avatar: '',
        password: '',
        roleId: 0
      }
    },
    // 修改
    ok: function(){
      // 表单校验
      let m = this.m;
      sa.checkNull(m.avatar, '请选择一个头像');
      sa.checkNull(m.name, '请输入昵称');
      sa.checkNull(m.password, '请输入密码');
      sa.checkNull(m.roleId, '请选择角色');

      // 添加
      sa.ajax('/admin/add', m, function(res) {
        sa.alert('添加成功, 账号id为：' + res.data, function(){
          this.m = this.crateModel();
          if (this.id != 0) {
            sa.closeModel();
            sa.currView().f5();
          }
        }.bind(this));
      }.bind(this));
    }
  }
}
</script>

<style scoped>
/*.vue-box >>> .c-item .el-input__inner{width: 300px;}*/
</style>
