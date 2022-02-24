<!--
  全局 dialog
 -->
<template>
  <!-- 全局 dialog -->
  <div class="os-dialog-fox">
    <el-dialog
      :visible.sync="dg.show"
      :title="dg.title"
      top="auto"
      width="auto"
      :append-to-body="false"
      :close-on-click-modal="false"
      custom-class="os-dialog"
    >
      <div>
        <!-- ------- 内容部分 ------- -->
        <div class="s-body">
          <component :is="dg.view" ref="os-model-view" :param="dg.param" />
        </div>
        <!-- ------- 底部按钮 ------- -->
        <div v-if="dg.param.btn" class="s-foot">
          <el-button :type="getBtnType()" @click="ok">确定</el-button>
          <el-button @click="closeModel()">取消</el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'os-model-view',
  data() {
    return {
      btnType: 'primary',
      dg: {
        show: false, // 是否显示
        title: '提示', // 标题
        view: null, // view视图
        param: { // view视图的参数
          btn: true
        },
      }
    }
  },
  created() {
    this.sa.osModel = this;
  },
  methods: {
    // 显示全局dialog
    showModel: function(title, view, param) {
      // console.log('显示窗口')
      param = param || {};
      param.btn = (param.btn === undefined ? true : param.btn);
      param.isModel = true;

      this.dg.title = title || '信息';
      this.dg.view = view;
      this.dg.param = param;
      this.dg.show = true;
    },

    // 关闭全局dialog
    closeModel: function() {
      this.dg.show = false;
    },

    // 点击 [确定] 按钮执行的事件
    ok: function() {
      if (this.$refs['os-model-view'].ok) {
        this.$refs['os-model-view'].ok();
      } else {
        this.closeModel();
      }
    },
    // 获取按钮类型
    getBtnType: function() {
      if (this.isEnd(this.dg.title, '详情')) {
        return 'success';
      }
      return 'primary';
    },
    // 是否以指定字符串结尾
    isEnd: function(str, target) {
      let start = str.length - target.length;
      let arr = str.substr(start, target.length);
      if (arr == target) {
        return true;
      }
      return false;
    }
  }
}
</script>

<style scoped>

</style>
