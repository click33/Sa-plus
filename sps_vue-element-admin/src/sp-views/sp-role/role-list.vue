<template>
  <div class="vue-box">
    <div class="c-panel">
      <!-- 参数栏 -->
      <div class="c-title">检索参数</div>
      <el-form @submit.native.prevent>
        <sa-item v-model="p.name" type="text" name="角色名称" />
        <el-button type="primary" icon="el-icon-search" @click="f5()">查询</el-button>
        <el-button type="primary" icon="el-icon-plus" @click="add()">新增</el-button>
      </el-form>
      <!-- 数据列表 -->
      <el-table ref="data-table" class="data-table" :data="dataList">
        <el-table-column label="编号" prop="id" width="70px" />
        <el-table-column label="角色名称">
          <template slot-scope="s">
            <el-input v-if="s.row.is_update" v-model="s.row.name" />
            <span v-else>{{ s.row.name }}</span>
          </template>
        </el-table-column>
        <el-table-column label="责任描述">
          <template slot-scope="s">
            <el-input v-if="s.row.is_update" v-model="s.row.info" />
            <span v-else>{{ s.row.info }}</span>
          </template>
        </el-table-column>
        <el-table-column label="是否锁定" title="锁定的角色为系统维持正常运行的重要角色，不可删除">
          <template slot-scope="s">
            <el-tag v-if="s.row.isLock == 1">已锁定</el-tag>
            <el-tag v-else type="success">未锁定</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="创建日期">
          <template slot-scope="s">
            {{ sa.forDate(s.row.createTime, 2) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="220px">
          <template slot-scope="s">
            <el-button type="text" @click="update(s.row)">
              <span :style="s.row.is_update ? 'color: red;' : ''">修改</span>
            </el-button>
            <el-button type="text" @click="del(s.row)">删除</el-button>
            <el-button type="text" @click="menu_setup(s.row)">分配权限</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- ------------- 分页 ------------- -->
      <sa-item type="page" :curr.sync="p.pageNo" :size.sync="p.pageSize" :total="dataList.length" :sizes="[1000]" @change="f5()" />
    </div>

    <!-- <el-dialog
      :title="title"
      :visible.sync="isShow"
      width="100px"
      top="5vh"
      :append-to-body="true"
      :destroy-on-close="true"
      :close-on-click-modal="false"
      custom-class="full-dialog"
    >
      <div>大多数</div>
    </el-dialog>-->

  </div>
</template>

<script>
// import RoleAdd from './role-add';

export default {
  name: 'role-list',
  data() {
    return {
      p: {	// 查询参数
        name: '',
        pageNo: 1,
        pageSize: 1000,
      },
      dataList: [],	// 数据集合
      isShow: false
    }
  },
  created() {
    this.f5();
    sa.onInputEnter();	// 监听表单回车执行查询
  },
  methods: {
    // 刷新
    f5: function() {
      sa.ajax('/role/getList', this.p, function(res) {
        this.dataList = sa.listAU(res.data);
        sa.f5TableHeight();		// 刷新表格高度
      }.bind(this));
    },
    // 修改
    update: function(data) {
      if (data.is_update == false) {
        data.is_update = true;
      } else {
        sa.confirm('是否修改数据？', function() {
          let data2 = sa.copyJSON(data)
          data2.createTime = undefined;
          sa.ajax('/role/update', data2, function(res) {
            sa.ok('修改成功');
            data.is_update = false;
          })
        })
      }
    },
    // 删除
    del: function(data) {
      if (data.isLock == 1) {
        return sa.alert('此角色是维持系统正常运行的重要角色，已被锁定，不可删除');
      }
      sa.confirm('是否删除，此操作不可撤销', function() {
        sa.ajax('/role/delete', { id: data.id }, function(res) {
          sa.arrayDelete(this.dataList, data);
          sa.ok('删除成功');
          sa.f5TableHeight();		// 刷新表格高度
        }.bind(this))
      }.bind(this));
    },
    // 添加
    add: function() {
      sa.showModel('角色添加', () => import('./role-add'));
    },
    // 修改权限菜单
    menu_setup: function(data) {
      const title = '为 [' + data.name + '] 分配权限';
      sa.showModel(title, () => import('./menu-setup'), { btn: false, roleId: data.id });
      // sa.showIframe(title, 'menu-setup.html?roleId=' + data.id, '700px', '600px');
    }
  }
}
</script>

<style scoped>
  .el-tag{border-radius: 0px;}
</style>
