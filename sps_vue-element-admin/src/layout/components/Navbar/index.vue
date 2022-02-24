<!--
  layout：右边第一行：工具栏
-->
<template>
  <div class="navbar">

    <!--折叠菜单-->
    <hamburger id="hamburger-container" :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <!--面包屑导航-->
    <breadcrumb id="breadcrumb-container" class="breadcrumb-container" />

    <div class="right-menu">
      <template v-if="device!=='mobile'">

        <template v-if="runAsToken">
          <div class="right-menu-item hover-effect" style="font-size: 14px;">
            <span style="font-size: 0.8em; font-weight: bold; ">当前模拟登录账号：{{sa.$sys.getCurrUser().id}}，</span>
            <span style="font-size: 0.8em; color: #44f; text-decoration: underline; cursor: pointer;" @click="closeRunAs()">退出</span>
          </div>
        </template>
<!--			<span title="模拟登陆" v-if="runAsToken">
				<span style="font-size: 0.8em; font-weight: bold; ">当前模拟登录账号：{{sa.$sys.getCurrUser().id}}，</span>
				<span style="font-size: 0.8em; color: #44f; text-decoration: underline; cursor: pointer;" @click="$root.closeRunAs()">退出</span>
			</span>-->

        <el-dropdown v-if="$store.getters.name" class="avatar-container right-menu-item hover-effect" trigger="click" size="medium">
          <div class="avatar-wrapper" style="line-height: 50px; margin-top: 0px;">
            <img :src="$store.getters.avatar" class="user-avatar" style=" vertical-align: middle;">
            <span style="font-size: 14px;">{{ $store.getters.name }}</span>
            <i class="el-icon-caret-bottom" />
          </div>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="sa.$page.openAdminInfo()">
              <span style="display:block;">我的资料</span>
            </el-dropdown-item>
            <el-dropdown-item @click.native="updateName()">
              <span style="display:block;">修改名称</span>
            </el-dropdown-item>
            <el-dropdown-item @click.native="updatePassword()">
              <span style="display:block;">修改密码</span>
            </el-dropdown-item>
            <el-dropdown-item divided @click.native="logout">
              <span style="display:block;">注销</span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <div v-else class="right-menu-item hover-effect" style="font-size: 14px;" @click="$router.push('/login')">
          <span style="position: relative; top: 2px;">未登录</span>
        </div>

        <div class="right-menu-item hover-effect" style="display: inline-block;" @click="openNote()">
          <svg-icon icon-class="edit" style="vertical-align: middle; font-width: 700; fill: #5a5e66;" />
        </div>

        <search id="header-search" class="right-menu-item hover-effect" />

        <screenfull id="screenfull" class="right-menu-item hover-effect" />

        <!--<el-tooltip content="Global Size" effect="dark" placement="bottom">
          <size-select id="size-select" class="right-menu-item hover-effect" />
        </el-tooltip>-->

      </template>

    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from './Breadcrumb'
import Hamburger from './Hamburger'
import Screenfull from './Screenfull'
import Search from './HeaderSearch'

export default {
  components: {
    Breadcrumb,
    Hamburger,
    Screenfull,
    Search
  },
  data() {
    return {
      runAsToken: sessionStorage.runAsToken,	// 模拟登陆-Token
    }
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'device'
    ])
  },
  created() {
    // console.log(this.$store.getters)
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    // 修改名称
    updateName() {
      sa.prompt('请输入新名称', function(pass){
        sa.ajax('/admin/updateInfo', { name: pass }, function(){
          sa.ok2('修改成功');
          this.$store.dispatch('user/setNameAvatar', {
            name: pass,
            avatar: this.$store.getters.avatar
          })
        }.bind(this));
      }.bind(this));
    },
    // 修改密码
    updatePassword() {
      sa.showModel('修改密码', () => import('@/sp-views/sp-admin/update-password'));
    },
    // 打开便签
    openNote: function() {
      var w = (document.body.clientWidth * 0.4) + 'px';
      var h = (document.body.clientHeight * 0.6) + 'px';
      var default_content = '一个简单的小便签, 关闭浏览器后再次打开仍然可以加载到上一次的记录, 你可以用它来记录一些临时资料';
      var value = localStorage.getItem('sa_admin_note') || default_content;
      var index = sa.layer.prompt({
        title: '一个小便签',
        value: value,
        formType: 2,
        area: [w, h],
        btn: ['保存'],
        maxlength: 99999999,
        skin: 'layer-note-class'
      }, function(pass, index){
        sa.layer.close(index)
      });
      var se = '#layui-layer' + index + ' .layui-layer-input';
      var d = document.querySelector(se);
      d.oninput = function() {
        localStorage.setItem('sa_admin_note', this.value);
      }
    },
    // 注销
    logout() {
      let _this = this;
      sa.confirm('退出登录？', function() {
        sa.ajax('/AccAdmin/doExit', function(res) {
          // 清空账号数据
          _this.$store.dispatch('user/setNameAvatar', { name: '', avatar: '' });

          // 清空权限数据
          _this.$store.commit('permission/setPerCodes', []);
          sa.setAuth([]);

          // 弹窗提问
          sa.alert('注销成功', function() {
            _this.$router.push(`/login?redirect=${_this.$route.fullPath}`);
          })
        })
      });
    },
    // 退出模拟登录
    closeRunAs: function() {
      sa.layer.confirm('退出模拟登录？', function() {
        sa.ajax('/AccAdmin/doExit', function(res) {
          sa.ok('退出成功，即将刷新页面');
          sessionStorage.removeItem('runAsToken');
          setTimeout(function() {
            top.location.reload(true);
          }, 1000);
        })
      });
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;
    padding-right: 10px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 10px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .1)
        }
      }
    }

    .avatar-container {
      //margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 35px;
          height: 35px;
          border-radius: 50%;
          margin-right: 5px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          margin-left: 5px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
