<template>
  <div class="view-box">
    <!--背景盒子-->
    <div class="bg-fox">
      <img src="https://oss.dev33.cn/sa-plus/temp/login-sc4.png" style="position: absolute; bottom: 8vh; left: 8vw; width: 450px;">
      <div style="width: 100%; height: 100%">
        <!-- 三角形-->
        <div class="sjx" style="position: absolute; top: 0vh; left: 25vw; transform: rotate(70deg) scale(0.8, 0.8);" />
        <div class="sjx" style="position: absolute; top: 0vh; right: 15vw; transform: rotate(60deg) scale(0.5, 0.5);" />
        <div class="sjx" style="position: absolute; top: 25vh; right: -2vw; transform: rotate(40deg);" />
        <div class="sjx" style="position: absolute; bottom: 10vh; right: 20vw; transform: rotate(10deg) scale(1.5, 1.5);" />
      </div>
    </div>
    <!--内容盒子-->
    <div class="for-box">
      <div class="login-box">
        <div class="login-box-2">
          <!-- 表单盒子 -->
          <div class="from-box" :class="{'from-box-show': show}">
            <h3 class="from-title">
              <img src="@/assets/logo.png" class="logo">
              <span>{{$root.title}}</span>
            </h3>
            <el-form size="small" label-position="left" label-width="0px">
              <el-form-item>
                <el-input v-model="m.key" prefix-icon="el-icon-user" placeholder="请输入账号" size="medium" />
              </el-form-item>
              <el-form-item>
                <el-input v-model="m.password" prefix-icon="el-icon-unlock" type="password" placeholder="请输入密码" size="medium" @keyup.native.enter="ok()" />
              </el-form-item>
              <el-form-item>
                <span style="color: #999;"> <el-checkbox v-model="m.remember">记住我</el-checkbox></span>
                <span style="float: right; color: #999;">测试账号：sa/123456</span>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" size="medium" style="width: 100%;" @click="ok()">登录</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>

      <!-- 底部 版权 -->
      <div style="position: absolute; bottom: 40px; width: 100%; text-align: center; color: #666;">
        Copyright ©2022 {{$root.title}} | xx 市 xx 网络科技有限公司 - 提供技术支持
      </div>

    </div>

  </div>
</template>

<script>
import store from '@/store'
import router from '@/router'
import sa from "@/sa-frame/sa";

export default {
  name: 'sa-login',
  data() {
    return {
      show: false, // 是否显示
      m: {
        key: '',
        password: '',
        remember: false
      }
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        const query = route.query
        if (query) {
          this.redirect = query.redirect
          this.otherQuery = this.getOtherQuery(query)
        }
      },
      immediate: true
    }
  },
  mounted() {
    setTimeout(function(){
      this.show = true;
    }.bind(this), 0);
  },
  methods: {
    // 点击确定
    ok() {
      // 表单验证
      if (this.m.key === '' || this.m.password === '') {
        return this.sa.error2('请输入完整');
      }
      // 请求后台
      this.sa.ajax('/AccAdmin/doLogin', this.m, function(res){
        // 写入token
        if (res.data.tokenInfo) {
          if(this.m.remember) {
            sessionStorage.removeItem('satoken');
            localStorage.setItem('satoken', res.data.tokenInfo.tokenValue);
          } else {
            localStorage.removeItem('satoken');
            sessionStorage.setItem('satoken', res.data.tokenInfo.tokenValue);
          }
        }

        // 写入当前账号信息
        sa.$sys.setCurrUser(res.data.admin);
        this.$store.dispatch('user/setNameAvatar', {
          name: res.data.admin.name,
          avatar: res.data.admin.avatar
        })

        // 写入权限码
        sa.setAuth(res.data.perList);
        this.$store.commit('permission/setPerCodes', res.data.perList);

        // 配置信息
        sa.$sys.setAppCfg(res.data.appCfg);

        // 打个招呼，进入 index
        this.sa.msg('欢迎你：' + res.data.admin.name);
        setTimeout(function() {
          // this.$router.push('/');
          this.$router.push({ path: this.redirect || '/', query: this.otherQuery })
        }.bind(this), 800);
      }.bind(this));
    },
    getOtherQuery(query) {
      return Object.keys(query).reduce((acc, cur) => {
        if (cur !== 'redirect') {
          acc[cur] = query[cur]
        }
        return acc
      }, {})
    }
  }
}
</script>

<style scoped>
/* 视图盒子 */
.view-box{}

/*  */
.bg-fox {
  position: fixed;
  width: 100%;
  height: 100%;
  /*background-color: #fafeff;*/
  /*background-color: #f8f8ff;*/
  background-color: rgb(250, 254, 255);
  pointer-events: none;
}
.for-box{
  position: fixed;
  width: 100%;
  height: 100%;
  z-index: 10;
  display: flex;
  align-items: center;
}

.login-box{
  flex: 1;
}
.login-box-2{
  width: 450px;
  max-width: 90vw;
  margin: auto;
  padding-bottom: 100px;
}

/* 表单 */
.from-box{padding: 45px 50px 25px; background-color: rgba(255,255,255,0.5); border: 1px #e5e5e5 solid;}
.from-box{ border-radius: 1px; /*box-shadow: 1px 1px 2px #666;*/}
.from-title{font-size: 24px; color: #000; margin-bottom: 30px; text-align: center; position: relative; left: -15px;}

.logo{width: 50px; height: 50px; vertical-align: middle; margin-right: 15px;}

/*动画*/
.bg-fox>div{animation: changes 30s 0.2s linear infinite alternate; }  /* normal | alternate */
@keyframes changes {
  from {transform: translate(0, 0vh);}
  to {transform: translate(0, 15vh);}
}

.sjx {
  width: 0;
  height: 0;
  border-left: 100px solid transparent;
  border-right: 100px solid transparent;
  border-bottom: 180px solid #dee7f8;
}

/* 2px圆角 */
.view-box >>> .el-input__inner,
.view-box >>> .el-button{border-radius: 2px !important;}

/* 渐渐显示的动画 */
.from-box{
  opacity: 0;
  transition: opacity 1s;
}
.from-box-show{
  opacity: 1;
}

</style>
