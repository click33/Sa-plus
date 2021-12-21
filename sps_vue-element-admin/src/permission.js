import router from './router'
import store from './store'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

// 路由拦截器，每次刷新路由时执行
router.beforeEach(async(to, from, next) => {
  // 开始进度条
  NProgress.start()

  // 如果有权限，就成功进入页面
  //
  if (sa.isAuth(to.name) || to.name == null || to.name == 'home') {
    next();
  } else {
    // 如果无权限，就进入403
    next('/403');
  }

  // set 页面标题
  document.title = getPageTitle(to.meta.title)

  // 结束进度条
  NProgress.done();

  // 如果路由尚未初始化
  if (store.getters.is_init_routes === false) {
    store.commit('permission/initRoutes');
  }
})

router.afterEach(() => {
  // 关闭进度条
  NProgress.done()

  // 解决每次切换视图时table渲染不正确的bug
  setTimeout(function(){
    var view = sa.currView();
    if (view && view.$refs['data-table']) {
      view.$refs['data-table'].doLayout();
    }
  }, 500);
})
