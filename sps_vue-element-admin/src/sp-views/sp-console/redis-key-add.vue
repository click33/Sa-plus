<template>
  <div class="vue-box sbot">
    <!-- ------- 内容部分 ------- -->
    <div class="c-panel">
      <div class="c-title">数据添加</div>
      <el-form v-if="m">
        <div class="c-item br">
          <label class="c-label">key：</label>
          <el-input v-model="m.key" />
        </div>
        <div class="c-item br">
          <label class="c-label" style="vertical-align: top;">value：</label>
          <div style="display: inline-block;">
            <el-input v-model="m.value" type="textarea" :autosize="{ minRows: 14, maxRows: 20}" />
          </div>
        </div>
        <div class="c-item br">
          <label class="c-label">ttl：</label>
          <el-input v-model="m.ttl" placeholder="过期时间 单位/毫秒" />
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'redis-key-add',
  data() {
    return {
      sa: sa, 	// 超级对象
      m: this.crate_model()
    }
  },
  mounted: function(){

  },
  methods: {
    // 创建一个空Model
    crate_model() {
      return {
        key: '',
        value: '',
        ttl: '',
        is_show: true
      }
    },
    // 修改
    ok: function(){
      // 开始验证
      var m = this.m;
      sa.checkNull(m.key, '请输入键');
      sa.checkNull(m.value, '请输入值');
      sa.checkNull(m.ttl, '请输入ttl (过期时间)');
      sa.check(isNaN(m.ttl), 'ttl 必须是一个数字 ');

      // 添加
      m.ttl = parseInt(m.ttl);
      sa.ajax('/RedisConsole/set', m, function(res){
        sa.closeModel();
        sa.currView().dataListShow.unshift(m);
        sa.msg('添加成功');
        sa.f5TableHeight();	// 刷新表格高度
      });
    }
  }
}
</script>

<style scoped>
  .vue-box{width: 800px}
  .c-panel .c-label{width: 6em;}
  /*  普通文本和富文本一起变长  */
  .c-panel>>> .el-form .el-input,
  .c-panel>>> .el-form .el-textarea__inner{width: 600px;}
</style>
