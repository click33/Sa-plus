<template>
  <div class="vue-box sbot sbot-fluid" style="width: 500px">
    <!-- ------- 内容部分 ------- -->
    <div class="c-panel">
      <div class="c-title">数据添加</div>
      <el-form v-if="m">
        <!-- no字段： m.id - id -->
        <div class="c-item br">
          <label class="c-label">开始日期：</label>
          <el-date-picker v-model="m.startTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="开始日期" />
        </div>
        <div class="c-item br">
          <label class="c-label">结束日期：</label>
          <el-date-picker v-model="m.endTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="结束日期" />
        </div>
        <div class="c-item br">
          <label class="c-label">已选范围：</label>
          <span style="color: red;">{{ sa.forDate(m.startTime, 2) }}</span> -
          <span style="color: red;">{{ sa.forDate(m.endTime, 2) }} </span>
        </div>
        <div class="c-item br">
          <label class="c-label">操作注意：</label>
          <span style="color: red;">日志删除后不可恢复，请谨慎操作</span>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'apilog-list-delete',
  data() {
    return {
      m: {
        startTime: '',
        endTime: '',
      },
    }
  },
  mounted: function(){

  },
  methods: {
    // 提交数据
    ok: function(){
      if (sa.isNull(this.m.startTime) || sa.isNull(this.m.endTime)) {
        return sa.error('请选择一个时间范围')
      }
      // 开始删除
      sa.ajax('/SgApilog/deleteByStartEnd', this.m, function(res){
        sa.alert('操作成功, 共删除 ' + res.data + ' 条请求记录', function() {
          sa.currView().f5();
          sa.closeModel();
        });
      });
    },
  }
}
</script>

<style scoped>

</style>
