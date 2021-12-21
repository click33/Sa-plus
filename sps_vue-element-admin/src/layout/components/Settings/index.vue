<!--
  layout组件：布局设置
-->
<template>
  <div class="drawer-container">
    <div>
      <h3 class="drawer-title">布局设置</h3>

      <div class="drawer-item">
        <span>主题颜色</span>
        <theme-picker style="float: right;height: 26px;margin: -3px 8px 0 0;" @change="themeChange" />
      </div>

      <div class="drawer-item">
        <span>标签窗口</span>
        <el-switch v-model="tagsView" class="drawer-switch" />
      </div>

      <div class="drawer-item">
        <span>头部固定</span>
        <el-switch v-model="fixedHeader" class="drawer-switch" />
      </div>

      <div class="drawer-item">
        <span>显示 Logo</span>
        <el-switch v-model="sidebarLogo" class="drawer-switch" />
      </div>

      <div class="drawer-item">
        <el-button type="primary" size="mini" icon="el-icon-plus" @click="saveConfig()">保 存</el-button>
        <el-button type="infor" size="mini" icon="el-icon-refresh" @click="resetConfig()">重 置</el-button>
      </div>

    </div>
  </div>
</template>

<script>
import ThemePicker from './ThemePicker';

export default {
  components: { ThemePicker },
  data() {
    return {}
  },
  computed: {
    fixedHeader: {
      get() {
        return this.$store.state.settings.fixedHeader
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'fixedHeader',
          value: val
        })
      }
    },
    tagsView: {
      get() {
        return this.$store.state.settings.tagsView
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'tagsView',
          value: val
        })
      }
    },
    sidebarLogo: {
      get() {
        return this.$store.state.settings.sidebarLogo
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'sidebarLogo',
          value: val
        })
      }
    }
  },
  methods: {
    themeChange(val) {
      this.$store.dispatch('settings/changeSetting', {
        key: 'theme',
        value: val
      })
    },
    // 持久化配置
    saveConfig() {
      var settings = this.$store.state.settings;
      localStorage.layoutSettings = JSON.stringify(settings);
      this.$message({
        showClose: true,
        message: '保存成功！',
        type: 'success'
      });
    },
    // 重置配置
    resetConfig() {
      localStorage.removeItem('layoutSettings');
      this.$store.dispatch('settings/resetSetting', {});
      this.$message({
        showClose: true,
        message: '重置成功！',
        type: 'success'
      });
    }
  }
}
</script>

<style lang="scss" scoped>
.drawer-container {
  padding: 24px;
  font-size: 14px;
  line-height: 1.5;
  word-wrap: break-word;

  .drawer-title {
    margin-bottom: 12px;
    color: rgba(0, 0, 0, .85);
    font-size: 14px;
    line-height: 22px;
  }

  .drawer-item {
    color: rgba(0, 0, 0, .65);
    font-size: 14px;
    padding: 12px 0;
  }

  .drawer-switch {
    float: right
  }
}
</style>
