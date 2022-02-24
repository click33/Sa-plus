<template>
  <div class="vue-box sbot" style="width: 800px; height: 600px; ">
    <!-- ------- 内容部分 ------- -->
    <div class="s-body">
      <!-- 表格 -->
      <div style="padding: 1em 2em;">
        <el-form>
          <div class="c-title">所有权限</div>
          <!-- 此扩展能递归渲染一个权限树，点击深层次节点，父级节点中没有被选中的节点会被自动选中，单独点击父节点，子节点会全部 选中/去选中 -->
          <el-tree
            ref="tree"
            :data="dataList"
            show-checkbox
            node-key="name"
            :default-expand-all="true"
            :default-checked-keys="selectList"
            :expand-on-click-node="false"
            :check-on-click-node="true"
            :check-strictly="true"
            @node-click="node_click"
            @check="node_click"
          >
            <span slot-scope="s" class="custom-tree-node">
              <span v-if="s.data.hidden !== true" style="color: #2D8CF0;">{{ s.data.meta && s.data.meta.title }}</span>
              <span v-else style="color: #999;">{{ s.data.meta && s.data.meta.title }} （隐藏）</span>
              <!--<span style="color: #999;" v-if="s.data.info">&emsp;———— {{s.data.info}} </span>-->
            </span>
          </el-tree>
        </el-form>
      </div>
    </div>
    <!-- ------- 底部按钮 ------- -->
    <div class="s-foot">
      <el-button type="success" @click="checkedAll()">全选</el-button>
      <el-button type="primary" @click="ok()">确定</el-button>
      <el-button @click="sa.closeModel()">取消</el-button>
    </div>
  </div>
</template>

<script>
import { asyncRoutes } from '@/router/index';

export default {
  name: 'menu-setup',
  props: { param: Object },
  data() {
    return {
      // p: [],
      roleId: this.param.roleId,
      dataList: [],	// 数据集合
      selectList: [],	// 默认选中
      ywList: [],		// 一维数组
      haveList: []		// 这个角色用的权限id，拷贝（用于计算是否修改掉了关键属性）
    }
  },
  created() {
    // 菜单数据
    const menuList = sa.copyArray(asyncRoutes)
    menuList.pop();
    this.dataList = menuList;	// 数据

    // 菜单数据转一维数组
    this.ywList = this.treeToArray(this.dataList);

    // 拉取此 roleId 具有的所有权限
    sa.ajax('/SpRolePermission/getPcodeByRid?roleId=' + this.roleId, function(res) {
      this.selectList = res.data;		// 选中的列表
      this.haveList = [].concat(this.selectList);
    }.bind(this))
  },
  methods: {
    // 保存
    ok: function(clickCount){
      if (clickCount === undefined) {
        clickCount = 5;
      }
      // 判断是否改掉了关键权限
      let keys = this.$refs.tree.getCheckedKeys();		// 设置完拥有的id列表
      let rArr = ['1', '99', 'auth', 'role-list'];		// 敏感菜单id列表
      let isR = false;									// 是否给改掉了
      rArr.forEach(function(item) {
        // 只有原先有，现在没有，才会被这样判定
        if (this.haveList.indexOf(item) > -1 && keys.indexOf(item) === -1) {
          isR = true;
          // console.log(item);
          // console.log(this.haveList);
        }
      }.bind(this))
      // 高危操作的提示
      if (isR) {
        let tipStr = '危险！系统检测到您取消了此角色的重要权限，这将导致与之关联的账号可能会无法正常使用后台，您无论如何都要这样设置吗？';
        tipStr += '<br/>为保证您不是误操作，您还需要继续点击按钮: ' + clickCount + '次'
        tipStr = '<b style="color: red;">' + tipStr + '</b>';
        sa.confirm(tipStr, function(res) {
          if (clickCount <= 1) {
            this.ok2();
          } else {
            clickCount--;
            this.ok(clickCount);
          }
        }.bind(this))
      } else {
        this.ok2();
      }
    },
    // 真正的保存
    ok2: function() {
      let keys = this.$refs.tree.getCheckedKeys()
      let url = '/SpRolePermission/updatePcodeByRid'
      sa.ajax(url, {roleId: this.roleId, codes: keys.join(',')}, function(res) {
        sa.alert('设置成功', function(){
          sa.closeModel();
        });
        // 如果设置的角色与当前登录者的角色一致，则立即显示出来
        // console.log(this.roleId)
        // console.log(sa.$sys.getCurrUser().roleId)
        if (this.roleId == sa.$sys.getCurrUser().roleId) {
          sa.setAuth(keys);
          this.$store.commit('permission/setPerCodes', keys);
        }
      }.bind(this))
    },
    // 点击回调, 处理其子节点跟随父节点的选中
    node_click: function(node) {
      // console.log(node)
      let is_select = this.$refs.tree.getCheckedKeys().indexOf(node.name) !== -1;	// 此节点现在是否被选中
      if (node.children){
        node.children.forEach(function(item) {
          this.$refs.tree.setChecked(item.name, is_select);
          // 递归
          if (item.children) {
            this.node_click(item);
          }
        }.bind(this))
      }
    },
    // 全选/ 取消全选
    checkedAll: function() {
      if (this.$refs.tree.getCheckedKeys().length !== this.ywList.length) {
        this.$refs['tree'].setCheckedNodes(this.ywList);
      } else {
        this.$refs['tree'].setCheckedNodes([]);
      }
    },
    // 将 Tree 菜单 转换为 一维平面数组
    treeToArray: function(menuList) {
      let arr = [];
      function _dg(menu_list) {
        menu_list = menu_list || [];
        for (let i = 0; i < menu_list.length; i++) {
          let menu = menu_list[i];
          arr.push(menu);
          // 如果有子菜单
          if (menu.children) {
            _dg(menu.children);
          }
        }
      }
      _dg(menuList);
      return arr;
    },
  }
}
</script>

<style scoped>

</style>
