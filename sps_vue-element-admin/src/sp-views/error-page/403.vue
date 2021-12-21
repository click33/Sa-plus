<template>
  <div class="errPage-container">
    <el-row>
      <el-col :span="12">
        <h2 class="text-jumbo text-ginormous">
          403 Forbidden !
        </h2>
        <!-- gif来源<a href="https://zh.airbnb.com/" target="_blank">airbnb</a> 页面 -->
        <br>
        <h3>你没有权限打开这个页面</h3>
        <br>
        <!-- <h6>如有不满请联系你领导</h6> -->
        <div class="bullshit__info tip-info">请检查您输入的URL是否正确，或单击下面的按钮返回主页。</div>
        <div class="btn-box">

          <router-link to="/">
            <el-button type="primary" size="medium" round>返回首页</el-button>
            <!-- <el-link type="primary">返回首页</el-link> -->
          </router-link>
        </div>
      </el-col>
      <el-col :span="12">
        <img :src="errGif" width="313" height="428" alt="Girl has dropped her ice cream.">
      </el-col>
    </el-row>
    <el-dialog :visible.sync="dialogVisible" title="随便看">
      <img :src="ewizardClap" class="pan-img">
    </el-dialog>
  </div>
</template>

<script>
import errGif from '@/assets/403_images/403.gif'

export default {
  name: 'Page403',
  data() {
    return {
      errGif: errGif + '?' + +new Date(),
      ewizardClap: 'https://wpimg.wallstcn.com/007ef517-bafd-4066-aae4-6883632d9646',
      dialogVisible: false
    }
  },
  methods: {
    back() {
      if (this.$route.query.noGoBack) {
        this.$router.push({ path: '/' })
      } else {
        this.$router.go(-1)
      }
    }
  }
}
</script>

<style lang="scss" scoped>
  .tip-info{color: #999; font-size: 14px;}
  .btn-box{
    margin-top: 20px;
  }
  .errPage-container {
    width: 800px;
    max-width: 100%;
    margin: 100px auto;
    .pan-back-btn {
      background: #008489;
      color: #fff;
      border: none!important;
    }
    .pan-gif {
      margin: 0 auto;
      display: block;
    }
    .pan-img {
      display: block;
      margin: 0 auto;
      width: 100%;
    }
    .text-jumbo {
      font-size: 40px;
      font-weight: 700;
      color: #484848;
    }
    .list-unstyled {
      font-size: 14px;
      li {
        padding-bottom: 5px;
      }
      a {
        color: #008489;
        text-decoration: none;
        &:hover {
          text-decoration: underline;
        }
      }
    }
  }
</style>
