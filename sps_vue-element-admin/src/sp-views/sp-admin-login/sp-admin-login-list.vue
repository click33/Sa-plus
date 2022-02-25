<template>
  <div class="vue-box sbot" :style=" isModel ? 'width: 1300px;' : '' ">
    <!-- ------- 内容部分 ------- -->
    <div class="c-panel">
      <!-- ------------- 检索参数 ------------- -->
      <div style="font-weight: bold; margin: 10px 0;">检索参数</div>
      <el-form ref="form" :model='p' @submit.native.prevent>
        <sa-item type="num" name="记录id" v-model="p.id"></sa-item>
        <sa-item type="num" name="管理员id" v-model="p.accId"></sa-item>
        <sa-item type="text" name="登录令牌" v-model="p.accToken"></sa-item>
        <sa-item type="text" name="登陆IP" v-model="p.loginIp"></sa-item>
        <el-button type="primary" icon="el-icon-search" @click="p.pageNo = 1; f5()">查询</el-button>
      </el-form>
      <!-- ------------- 快捷按钮 ------------- -->
      <!-- <sa-item type="fast-btn" show="delete,export,reset"></sa-item> -->
      <div style="height: 10px;"></div>
      <!-- ------------- 数据列表 ------------- -->
      <el-table class="data-table" ref="data-table" :data="dataList">
        <sa-td type="selection"></sa-td>
        <sa-td name="记录id" prop="id" width="90px"></sa-td>
        <sa-td name="登录账号" width="160px">
          <template slot-scope="s">
            <img :src="s.row.spAdminAvatar" @click="sa.showImage(s.row.spAdminAvatar, '400px', '400px')"
                 class="td-img" style="vertical-align: middle; margin-right: 5px;" />
            <el-link type="primary" @click="sa.showIframe('id = ' + s.row.accId + ' 详细信息', '../sp-admin/admin-info.html?id=' + s.row.accId)">
              <b style="font-weight: 400;">{{s.row.spAdminName}}</b>
            </el-link>
          </template>
        </sa-td>
        <sa-td name="登陆IP" prop="loginIp"></sa-td>
        <sa-td name="客户端标识" prop="device"></sa-td>
        <sa-td name="所属系统" prop="system"></sa-td>
        <sa-td name="登录地" prop="address"></sa-td>
        <sa-td name="本次登录Token" prop="accToken" width="180px"></sa-td>
        <sa-td name="登录时间" width="220px">
          <template slot-scope="s">
            <span>{{sa.forDate(s.row.createTime, 2)}}</span> -
            <b style="color: green;">{{sa.isNull(sa.forDate2(s.row.createTime), '无')}}</b>
          </template>
        </sa-td>
        <el-table-column label="操作" fixed="right"  width="120px">
          <template slot-scope="s">
            <el-button class="c-btn" type="danger" icon="el-icon-delete" @click="del(s.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- ------------- 分页 ------------- -->
      <sa-item type="page" :curr.sync="p.pageNo" :size.sync="p.pageSize" :total="dataCount" @change="f5()"></sa-item>
    </div>
  </div>
</template>

<script>
export default {
  name: 'sp-admin-login',
  props: { param: Object },
  data() {
    return {
      isModel: this.param.isModel,
      p: { // 查询参数
        id: '',		// id号
        accId: this.param.accId || '',		// 管理员id
        accToken: '',		// 本次登录Token
        loginIp: '',		// 登陆IP
        pageNo: 1,		// 当前页
        pageSize: 10,	// 页大小
        sortType: 0		// 排序方式
      },
      dataCount: 0,
      dataList: [], // 数据集合
    }
  },
  methods: {
    // 刷新
    f5: function() {
      sa.ajax('/SpAdminLogin/getList', sa.removeNull(this.p), function(res) {
        this.dataList = res.data; // 数据
        this.dataCount = res.dataCount; // 数据总数
        sa.f5TableHeight();		// 刷新表格高度
      }.bind(this));
    },
    // 删除
    del: function(data) {
      sa.confirm('是否删除，此操作不可撤销', function() {
        sa.ajax('/SpAdminLogin/delete?id=' + data.id, function(res) {
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
      if(selection.length === 0) {
        return sa.msg('请至少选择一条数据')
      }
      // 提交删除
      sa.confirm('是否批量删除选中数据？此操作不可撤销', function() {
        sa.ajax('/SpAdminLogin/deleteByIds', {ids: ids.join(',')}, function(res) {
          sa.arrayDelete(this.dataList, selection);
          sa.ok('删除成功');
          sa.f5TableHeight();		// 刷新表格高度
        }.bind(this))
      }.bind(this));
    },
  },
  created: function() {
    this.f5();
    sa.onInputEnter();
  },
}
</script>

<style scoped>
  /*.vue-box{padding: 0; height: 100%; background-color: #FFF;}*/
</style>
