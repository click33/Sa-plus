<template>
  <div class="vue-box">
    <div class="c-panel">
      <!-- ------------- 检索参数 ------------- -->
      <h4 class="c-title">检索参数</h4>
      <el-form>
        <sa-item v-model="p.id" type="num" name="账号id" />
        <sa-item v-model="p.name" type="text" name="名称" />
        <sa-item name="角色">
          <el-select v-model="p.roleId">
            <el-option label="全部" value="" />
            <el-option v-for="role in roleList" :key="role.id" :label="role.name" :value="role.id" />
          </el-select>
        </sa-item>
        <el-button type="primary" icon="el-icon-search" @click="p.pageNo = 1; f5()">查询</el-button>
        <br>
        <sa-item name="综合排序" class="s-radio-text">
          <el-radio-group v-model="p.sortType">
            <el-radio label="id">最近添加</el-radio>
            <el-radio label="loginTime">最近登录</el-radio>
            <el-radio label="loginCount">登录次数</el-radio>
          </el-radio-group>
        </sa-item>
      </el-form>
      <!-- ------------- 快捷按钮 ------------- -->
      <sa-item type="fast-btn" show="add,get,delete,export,reset" />
      <!-- ------------- 数据列表 ------------- -->
      <el-table ref="data-table" class="data-table" :data="dataList">
        <sa-td type="selection" />
        <sa-td type="num" name="记录id" prop="id" min-width="70px" />
        <sa-td type="user-avatar" name="昵称" prop="name,avatar" min-width="120px" />
        <sa-td type="text" name="手机" prop="phone" />
        <sa-td name="创建人">
          <template slot-scope="s">
            <span v-if="s.row.createByAid == -1">无</span>
            <el-link v-else @click="sa.$page.openAdminInfo(s.row.createByAid, s.row.name)">{{ s.row.createByAid }}</el-link>
          </template>
        </sa-td>
        <sa-td type="text" name="所属角色" prop="roleName" />
        <sa-td type="datetime" name="所属角色" prop="createTime" width="150px" />
        <sa-td type="datetime" name="最后登录" prop="loginTime" width="150px" />
        <sa-td type="text" name="登录次数" prop="loginCount" not="0" width="100px" />
        <sa-td type="switch" name="账号状态" prop="status" :jv="{1: '正常', 2: '禁用[#ff4949]'}" width="120px" @change="s => updateStatus(s.row)" />
        <el-table-column label="操作" fixed="right" width="470px">
          <template slot-scope="s">
            <span @click="getInfo(s.row)">
              <el-button type="success" class="c-btn" icon="el-icon-view">查看</el-button>
            </span>
            <span @click="getAdminLogin(s.row)" v-if="sa.isAuth('sp-admin-login')">
              <el-button type="success" class="c-btn">登录日志</el-button>
            </span>
            <el-dropdown trigger="click" style="font-size: 0.85em;">
              <el-button type="primary" class="c-btn">
                修改资料 <i class="el-icon-arrow-down el-icon--right"></i>
              </el-button>
              <el-dropdown-menu slot="dropdown">
									<span @click="updateName(s.row)">
										<el-dropdown-item>改名称</el-dropdown-item>
									</span>
                <span @click="updateAvatar(s.row)">
										<el-dropdown-item>改头像</el-dropdown-item>
									</span>
                <span @click="updatePassword(s.row)">
										<el-dropdown-item>改密码</el-dropdown-item>
									</span>
              </el-dropdown-menu>
            </el-dropdown>
            <el-dropdown trigger="click" style="font-size: 0.85em;">
              <el-button type="primary" class="c-btn">
                修改角色为 <i class="el-icon-arrow-down el-icon--right" />
              </el-button>
              <el-dropdown-menu slot="dropdown">
                <span v-for="role in roleList" :key="role.id" @click="updateRoleId(s.row, role.id, role.name)">
                  <el-dropdown-item :style=" s.row.roleId == role.id ? {color: 'blue'} : null ">{{ role.name }}</el-dropdown-item>
                </span>
              </el-dropdown-menu>
            </el-dropdown>
            <span @click="runAs(s.row)">
              <el-button type="warning" class="c-btn">模拟登录</el-button>
            </span>
            <span @click="del(s.row)">
              <el-button type="danger" class="c-btn" icon="el-icon-delete">删除</el-button>
            </span>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <sa-item type="page" :curr.sync="p.pageNo" :size.sync="p.pageSize" :total="dataCount" @change="f5()" />
    </div>

  </div>
</template>

<script>
export default {
  name: 'admin-list',
  data() {
    return {
      p: {		// 查询参数
        id: '',
        name: '',
        roleId: '',
        sortType: 'id',
        pageNo: 1,
        pageSize: 10,
      },
      dataCount: 0,
      dataList: [],	// 数据集合
      roleList: [],	// 角色集合
    }
  },
  created: function() {
    this.f5();
    sa.onInputEnter();	// 监听回车执行查询
    // 加载角色
    sa.ajax('/role/getList', function(res){
      this.roleList = res.data;	// 数据
    }.bind(this), { msg: null });
  },
  methods: {

    // 刷新
    f5: function() {
      sa.ajax('/admin/getList', this.p, function(res) {
        this.dataList = res.data;	// 数据
        this.dataCount = res.dataCount;
        sa.f5TableHeight();		// 刷新表格高度
      }.bind(this));
    },
    // 新增
    add: function() {
      console.log(123);
      sa.showModel('管理员添加', () => import('./admin-add'), { id: -1 });
    },
    // 查看详情
    getInfo: function(data) {
      sa.showModel('管理员详情', () => import('./admin-info'), { id: data.id });
    },
    // 查看登录日志
    getAdminLogin: function(data) {
      sa.showModel('登录日志', () => import('../sp-admin-login/sp-admin-login-list'), {accId: data.id});
    },
    // 查看 - 根据选中的
    getBySelect: function(data) {
      var selection = this.$refs['data-table'].selection;
      if (selection.length === 0) {
        return sa.msg('请至少选择一条数据')
      }
      this.getInfo(selection[0]);
    },
    // 修改名称
    updateName: function(data) {
      sa.layer.prompt({ title: '修改账号名称' }, function(pass, index) {
        sa.layer.close(index);
        sa.ajax('/admin/update', { id: data.id, name: pass }, function(res){
          data.name = pass;
          sa.msg('修改成功');
        })
      });
    },
    // 修改头像
    updateAvatar: function(data) {
      sa.uploadImage(function(src) {
        var p = { id: data.id, avatar: src };
        sa.ajax('/admin/updateAvatar', p, function(res) {
          sa.msg('上传成功');
          data.avatar = src;
        });
      })
    },
    // 修改密码
    updatePassword: function(data) {
      layer.prompt({ title: '修改密码' }, function(pass, index){
        layer.close(index);
        if (pass.length < 4) {
          return layer.msg('新密码长度请不要低于4位');
        }
        sa.ajax('/admin/updatePassword', { id: data.id, password: pass }, function(res){
          layer.msg('修改成功');
        })
      });
    },
    // 修改角色
    updateRoleId: function(data, roleId, roleName) {
      if (data.id == sa.$sys.getCurrUser().id) {
        return sa.alert('不能自己修改自己的角色');
      }
      if (data.roleId == roleId) {
        return sa.alert('该用户已经是' + roleName + '了');
      }
      var str = '将此账号修改为 [' + roleName + '], 请确认?';
      layer.confirm(str, { title: '请确认' }, function() {
        sa.ajax('/admin/updateRole', { id: data.id, roleId: roleId }, function(res) {
          sa.msg('修改成功');
          data.roleId = roleId;
          data.roleName = roleName;
        });
      });
    },
    // 修改用户的状态
    updateStatus: function(data) {
      if (data.id == sa.$sys.getCurrUser().id) {
        data.status = 3 - data.status;
        return sa.alert('不能自己封禁自己');
      }
      var is_ok = false;	// 记录是否成功
      var ajax = sa.ajax('/admin/updateStatus', { adminId: data.id, status: data.status }, function(res) {
        sa.msg('修改成功');
        is_ok = true;
      });
      // 如果未能修改成功, 则回滚
      sa.axios.all([ajax]).then(function(res) {
        if (is_ok == false) {
          data.status = 3 - data.status;
        }
      })
      // $.when(ajax).done(function() {
      //   if(is_ok == false) {
      //     data.status = 3 - data.status;
      //   }
      // })
    },
    // 模拟登陆
    runAs: function(data) {
      // 提交删除
      sa.confirm('将要以账号 [ ' + data.name + ' ] 模拟登录，是否确认？', function() {
        sa.ajax('/admin/runAs?adminId=' + data.id, function(res) {
          sa.ok('登录成功，即将刷新页面');
          sessionStorage.runAsToken = res.data;
          setTimeout(function() {
            top.location.reload(true);
          }, 1000)
        }.bind(this))
      }.bind(this));
    },
    // 删除
    del: function(data) {
      sa.confirm('是否删除，此操作不可撤销', function(){
        sa.ajax('/admin/delete', { id: data.id }, function(res){
          sa.arrayDelete(this.dataList, data);
          sa.ok('删除成功');
          sa.f5TableHeight();		// 刷新表格高度
        }.bind(this))
      }.bind(this));
    },
    // 批量删除
    deleteByIds: function() {
      // 获取选中元素的id列表
      let selection = this.$refs['data-table'].selection;
      let ids = sa.getArrayField(selection, 'id');
      if (selection.length == 0) {
        return sa.msg('请至少选择一条数据')
      }
      // 提交删除
      sa.confirm('是否批量删除选中数据？此操作不可撤销', function() {
        sa.ajax('/admin/deleteByIds', { ids: ids.join(',') }, function(res) {
          sa.arrayDelete(this.dataList, selection);
          sa.ok('删除成功');
          sa.f5TableHeight();		// 刷新表格高度
        }.bind(this))
      }.bind(this));
    },
  }
}
</script>

<style scoped>

</style>
