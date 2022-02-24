<template>
  <div class="vue-box sbot" style="width: 600px;">
    <!-- ------- 内容部分 ------- -->
    <div class="c-panel">
      <el-form v-if="m">
        <sa-info name="编号" br>{{ m.id }}</sa-info>
        <sa-info name="名称" br>{{ m.name }}</sa-info>
        <sa-info name="手机" br>{{ m.phone || '无' }}</sa-info>
        <sa-info name="角色" br>{{ m.roleName }}</sa-info>
        <sa-info name="创建账号" br>
          <span v-if="m.createByAid == -1">无</span>
          <a v-else @click="sa.$page.openAdminInfo(m.createByAid)">{{ m.createByAid }}</a>
        </sa-info>
        <sa-info name="创建时间" br>{{ sa.forDate(m.createTime, 2) }}</sa-info>
        <sa-info name="最后登录" br>{{ sa.forDate(m.loginTime, 2) || '无' }}</sa-info>
        <sa-info name="最后登录IP" br>{{ m.loginIp || '无' }}</sa-info>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'admin-info',
  props: { param: Object },
  data() {
    return {
      id: this.param.id || 0,
      sa: sa,
      m: {}
    }
  },
  created: function() {
    if (this.id == 0 || this.id == sa.$sys.getCurrUser().id) {
      sa.ajax('/admin/getByCurr', function(res) {
        this.m = res.data;
      }.bind(this));
    } else {
      sa.ajax('/admin/getById?id=' + this.id, function(res) {
        this.m = res.data;
      }.bind(this));
    }
  },
  methods: {
  }
}
</script>

<style scoped>
  .vue-box,.c-panel{}
  .c-panel >>> .c-title{margin-bottom: 20px;}
  .c-panel >>> .c-item .c-label{width: 150px;}
  .c-panel >>> .c-item .el-input{width: 300px;}
  /* 链接样式  */
  /*.my-link{position: relative; top: -1px; margin-left: 0.5em;}*/
</style>
