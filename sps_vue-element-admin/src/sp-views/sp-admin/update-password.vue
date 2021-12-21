<template>
  <div class="vue-box sbot" style="width: 520px;">
    <!-- ------- 内容部分 ------- -->
    <div class="c-panel">
      <div class="c-title">数据添加</div>
      <el-form v-if="m">
        <sa-item v-model="m.oldPwd" type="password" name="旧密码" br />
        <sa-item v-model="m.oldPwd2" type="password" name="再次输入旧密码" br />
        <sa-item v-model="m.newPwd" type="password" name="新密码" br />
        <sa-item v-model="m.newPwd2" type="password" name="再次输入新密码" br />
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'update-password',
  data(){
    return {
      m: {
        oldPwd: '',
        oldPwd2: '',
        newPwd: '',
        newPwd2: ''
      },
    }
  },
  methods: {
    // 提交
    ok: function() {
      // 表单校验
      let m = this.m;
      sa.checkNull(m.oldPwd && m.oldPwd2 && m.newPwd && m.newPwd2, '请填写');
      sa.check(m.oldPwd !== m.oldPwd2, '旧密码两次输入不一致');
      sa.check(m.newPwd !== m.newPwd2, '新密码两次输入不一致');
      sa.check(m.newPwd.length < 4, '新密码请不要低于六位数');
      // 开始修改
      sa.ajax('/AdminPassword/update', this.m, function() {
        sa.alert('修改成功', function(){
          sa.closeModel();
        })
      })
    },
  },
}
</script>

<style scoped>
>>>.c-item .c-label{width: 10em;}
>>>.c-item .el-input__inner{width: 300px;}
</style>
