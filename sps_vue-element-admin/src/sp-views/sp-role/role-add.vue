<template>
  <div class="vue-box sbot-fluid" :class="{sbot: id}">
    <div class="c-panel">
      <el-form v-if="m">
        <!-- no字段： m.id - id -->
        <sa-item v-model="m.id" type="text" name="角色id" br />
        <sa-item v-model="m.name" type="text" name="角色昵称" br />
        <sa-item v-model="m.info" type="text" name="责任描述" br />
        <sa-item name="" class="s-ok" br>
          <el-button type="primary" icon="el-icon-plus" @click="ok()">保存</el-button>
        </sa-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'role-add',
  data() {
    return {
      id: -1,		// 获取超链接中的id参数（0=添加，非0=修改）
      m: null,		// 实体对象
    }
  },
  mounted: function() {
    this.m = this.createModel();
  },

  methods: {
    // 创建一个 默认Model
    createModel: function() {
      return {
        id: '',
        name: '',
        info: '',
        isLock: 2,
        // createTime: new Date(),
        is_update: false,
      }
    },
    // 提交数据
    ok: function() {
      // console.log(this.$parent.$parent.$refs['curr-view'].f5())
      // return ;
      // 验证
      let m = this.m;		// 获取 m对象
      sa.checkNull(m.name, '请输入角色名字');
      sa.checkNull(m.info, '请输入责任描述');

      // this.$parent

      // 开始增加
      sa.ajax('/role/add', this.m, function(res) {
        sa.alert('增加成功', function() {
          this.m = this.createModel();
          sa.closeModel();
          sa.currView().f5();
        }.bind(this));
      }.bind(this));
    },
  }
}
</script>

<style scoped>

</style>
