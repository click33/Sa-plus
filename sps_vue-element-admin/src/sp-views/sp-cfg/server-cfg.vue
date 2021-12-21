<template>
  <div class="vue-box" style="display: none;" :style="'display: block;'">
    <div v-if="m != null" class="c-panel">
      <!-- tab卡片 -->
      <el-tabs v-model="activeTab" class="s-tab">
        <!-- ---------------------------------- 系统参数 ---------------------------------- -->
        <el-tab-pane label="系统参数" name="tab1">
          <!-- <div class="c-item br">
            <label class="c-label">预留信息：</label>
            <el-input v-model="m.reserve_info"></el-input>
          </div> -->
          <div class="c-item">
            <label class="c-label">抛出SQL：</label>
            <el-switch v-model="m.throwOutSql" :active-value="1" :inactive-value="2" />
            <span v-if="m.throwOutSql==1" class="c-remark">开启</span>
            <span v-else class="c-remark">关闭</span>
            <span class="c-remark" style="color: red;">( 抛出sql只为方便调试，建议只在开发环境下打开此选项，生产环境请务必关闭 )</span>
          </div>
        </el-tab-pane>
      </el-tabs>

      <!-- ---------------------------------- 其它配置 ---------------------------------- -->
      <el-tab-pane label="其它配置" name="tab2">
        <br>
        <span>其它配置</span>
      </el-tab-pane>

      <!-- 确定按钮 -->
      <div style="position: absolute; bottom: 0px; width: calc(100% - 3em); line-height: 80px; background-color: #FFF;">
        <hr style="height: 2px;">
        <div class="c-item">
          <label class="c-label" />
          <el-button type="primary" icon="el-icon-check" @click="ok">保存修改</el-button>
          <el-button type="primary" plain icon="el-icon-refresh-right" @click="f5">重置</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'sp-cfg-server',
  data() {
    return {
      m: null, //
      activeTab: 'tab1'
    }
  },
  created: function() {
    this.f5();
  },
  methods: {
    // 创建一个默认的配置对象
    create_m: function() {
      return {
        reserve_info: '预留信息', // 预留信息
        throwOutSql: 2,	// 是否隐藏sql，
      }
    },
    // 初始化配置
    init: function(str) {
      // 获取
      var cfg = sa.JSONParse(str, {}); // 用户配置
      var default_cfg = this.create_m(); // 默认配置
      // 遍历
      for (var key in default_cfg) {
        if (cfg[key] !== undefined && cfg[key] !== null) {
          default_cfg[key] = cfg[key];
        }
      }
      // 赋值
      this.m = default_cfg;
    },
    // 刷新
    f5: function() {
      sa.ajax('/SpCfg/getCfg', {
        cfgName: 'server_cfg'
      }, function(res) {
        this.init(res.data);
      }.bind(this));
    },
    // 提交
    ok: function() {
      sa.ajax('/SpCfg/updateCfg', {
        cfgName: 'server_cfg',
        cfgValue: JSON.stringify(this.m)
      }, function(res) {
        sa.ok2('保存成功');
      });
    }
  }
}
</script>

<style scoped>
.vue-box{height: 100%; overflow: hidden;}
/* .vue-box{padding: 0px;} */
.c-panel{height: calc(100% - 0em); position: relative;}
.c-panel >>> .c-label{width: 10em;}
.c-panel >>> .el-input{width: 500px;}
.c-panel >>> .el-textarea{width: 500px;}
.logo-img{
  width: 35px;
  height: 35px;
  border-radius: 2px;
  vertical-align: middle;
  margin-right: 0.5em;
  cursor: pointer;
}
.s-tab{height: 100%; }
.vue-box >>> .el-tabs__content{height: calc(100% - 130px); overflow: auto;}
</style>
