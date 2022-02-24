<template>
  <div class="vue-box">
    <div v-if="m != null" class="c-panel">
      <!-- 提示 -->
      <el-alert style="margin: 10px 0;"
                type="blue" show-icon
                title="App-Config 对外开放，用来配置一些非敏感信息。"
      >
      </el-alert>
      <!-- tab卡片 -->
      <el-tabs v-model="activeTab" class="s-tab">
        <!-- ---------------------------------- 系统参数 ---------------------------------- -->
        <el-tab-pane label="系统参数" name="tab1">
          <div class="c-item br">
            <label class="c-label">系统logo：</label>
            <img v-if="sa.isNull(m.logoUrl) == false" :src="m.logoUrl" class="logo-img" @click="sa.showImage(m.logoUrl, '400px', '400px')">
            <el-link type="primary" @click="sa.uploadImage(src => {m.logoUrl = src; sa.ok2('上传成功');})">选择上传</el-link>
          </div>
          <div class="c-item br">
            <label class="c-label">系统名称：</label>
            <el-input v-model="m.appName" />
          </div>
          <div class="c-item br">
            <label class="c-label">版本编号：</label>
            <el-input v-model="m.appVersionNo" />
          </div>
          <div class="c-item br">
            <label class="c-label">更新描述：</label>
            <el-input v-model="m.appVersionLog" />
          </div>
        </el-tab-pane>

        <!-- ---------------------------------- 其它配置 ---------------------------------- -->
        <el-tab-pane label="其它配置" name="tab2">
          <br>
          <span>其它配置</span>
        </el-tab-pane>

      </el-tabs>

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
  name: 'sp-cfg-app',
  data() {
    return {
      m: null,		//
      activeTab: 'tab1', // 当前显示的tab
      textareaCfg: { minRows: 3, maxRows: 14 } // 文本域的默认配置
    }
  },
  created: function(){
    this.f5();
  },
  methods: {
    // 创建一个默认cfg对象
    create_m: function() {
      return {
        logoUrl: '',	// 系统logo地址
        appName: this.$root.title || '', // 系统名称
        appVersionNo: 'v1.0.0',	// 系统版本
        appVersionLog: '更新于2099-10-1',	// 更新日志
      }
    },
    // 初始化配置
    init: function(str) {
      // 获取
      var cfg = sa.JSONParse(str, {});	// 用户配置
      var default_cfg = this.create_m();		// 默认配置
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
    f5: function(){
      sa.ajax('/SpCfg/getCfg', { cfgName: 'app_cfg' }, function(res){
        this.init(res.data);
      }.bind(this));
    },
    // 提交
    ok: function(){
      sa.ajax('/SpCfg/updateCfg', { cfgName: 'app_cfg', cfgValue: JSON.stringify(this.m) }, function(res){
        sa.ok2('保存成功');
      });
    },
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
