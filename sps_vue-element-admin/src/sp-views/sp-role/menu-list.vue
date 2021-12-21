<template>
  <div class="vue-box">
    <!-- 表格 -->
    <div style="padding: 0 1em;">
      <div class="c-title">菜单预览</div>
      <!-- 树插件 -->
      <el-tree
        v-if="dataList.length > 0"
        ref="tree"
        :data="dataList"
        node-key="id"
        :default-expand-all="true"
      >
        <span slot-scope="s" class="custom-tree-node">
          <span v-if="s.data.hidden !== true" style="color: #2D8CF0;">{{ s.data.meta && s.data.meta.title }}</span>
          <span v-else style="color: #999;">{{ s.data.meta && s.data.meta.title }} （隐藏）</span>
          <!--<span style="color: #999;" v-if="s.data.info">&emsp;———— {{s.data.info}} </span>-->
        </span>
      </el-tree>
      <br><br><br>
    </div>
  </div>
</template>

<script>
import { asyncRoutes } from '@/router/index';

export default {
  name: 'menu-list',
  data() {
    return {
      dataList: [],	// 数据集合
    }
  },
  created() {
    // 全部
    sa.ajax2('/SysMenu/getList', function(res){
      const menuList = sa.copyArray(asyncRoutes)
      menuList.pop();
      this.dataList = menuList;	// 数据
      // console.log(menuList)
    }.bind(this));
  },
  methods: {

  }
}
</script>

<style scoped>
.vue-box >>> .el-tree{background-color: #eee;}
.vue-box >>> .el-tree-node{margin: 0.15em 0 !important;}
/* 悬浮时颜色更深一点 */
.vue-box >>> .el-tree-node__content:hover{background-color: #CFE8FC !important;}
</style>
