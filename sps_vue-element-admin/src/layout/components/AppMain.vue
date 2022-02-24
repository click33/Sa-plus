<!--
  layout组件：右边第三行，视图出口
 -->
<template>
  <section class="app-main">
    <transition name="fade" mode="out-in">
      <keep-alive :include="cachedViews">
        <router-view :key="key" ref="curr-view" :param="{isModel: false}" />
      </keep-alive>
    </transition>
  </section>
</template>

<script>
export default {
  name: 'AppMain',
  computed: {
    cachedViews() {
      return this.$store.state.tagsView.cachedViews
    },
    key() {
      return this.$route.path
    }
  },
  created() {
    // 挂载到 sa 全局实例
    sa.appMain = this;
  }
}
</script>

<style lang="scss" scoped>
.app-main {
  /* 50= navbar  50  */
  /*min-height: calc(100vh - 50px);*/
  height: 100vh;
  width: 100%;
  position: relative;
  overflow: hidden;
}

.fixed-header+.app-main {
  padding-top: 50px;
}

.hasTagsView {
  .app-main {
    /* 84 = navbar + tags-view = 50 + 34 */
    /*min-height: calc(100vh - 84px);*/
    height: 100vh;
  }

  .fixed-header+.app-main {
    padding-top: 84px;
  }
}
</style>

<style lang="scss">
// fix css style bug in open el-dialog
.el-popup-parent--hidden {
  .fixed-header {
    //padding-right: 15px;
  }
}
</style>
