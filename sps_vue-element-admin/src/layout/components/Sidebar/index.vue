<!--
  layout组件：左边菜单 + logo部分
-->
<template>
  <div
    :class="{'has-logo':showLogo}"
    wrap-class="scrollbar-wrapper"
  >
    <logo v-if="showLogo" :collapse="isCollapse" />
    <el-scrollbar>
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :background-color="variables.menuBg"
        :text-color="variables.menuText"
        :unique-opened="true"
        :active-text-color="variables.menuActiveText"
        :collapse-transition="false"
        mode="vertical"
      >
        <sidebar-item v-for="route in permission_routes" :key="route.path" :item="route" :base-path="route.path" />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Logo from './Logo'
import SidebarItem from './SidebarItem'
import variables from '@/styles/variables.scss'

export default {
  components: { SidebarItem, Logo },
  data() {
    return {
    }
  },
  computed: {
    ...mapGetters([
      'permission_routes',
      'sidebar'
    ]),
    activeMenu() {
      const route = this.$route
      const { meta, path } = route
      // if set path, the sidebar will highlight the path you set
      if (meta.activeMenu) {
        return meta.activeMenu
      }
      return path
    },
    showLogo() {
      return this.$store.state.settings.sidebarLogo
    },
    variables() {
      return variables
    },
    isCollapse() {
      return !this.sidebar.opened
    }
  },
  created() {
    // 挂载到全局变量
    this.sa.sidebar = this;
  },
  methods: {
  }
}
</script>

<style scoped>
>>>.el-scrollbar__wrap{
  overflow-x: hidden;
}

/* 一级菜单，高度45px */
>>> .el-menu-item,
>>> .el-submenu__title{height: 50px !important; line-height: 50px !important;}

/* 二级以下菜单，高度40px */
>>> .el-submenu .el-menu-item,
>>> .el-submenu .el-submenu .el-submenu__title{height: 45px !important; line-height: 45px !important;}

</style>
