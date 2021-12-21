import { asyncRoutes, constantRoutes } from '@/router'
import store from '@/store'
import router from '@/router'

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes) {
  const res = []

  routes.forEach(route => {
    const tmp = { ...route }
    if (tmp.children) {
      tmp.children = filterAsyncRoutes(tmp.children)
    }
    // 带有 path 属性才会 push 到路由中
    if (tmp.path) {
      res.push(tmp)
    }
  })

  return res
}

const state = {
  routes: [], // 当前路由表
  per_codes: [], // 当前账号拥有的权限码集合
}

const mutations = {
  // 设置路由
  setRoutes: (state, routes) => {
    // 二次筛选一下
    let routeList = filterAsyncRoutes(routes)

    // 树状菜单
    state.routes = constantRoutes.concat(routeList)

    // 一维数组菜单
    // ...

    // push 到 router 里
    router.addRoutes(routeList);
  },
  // 初始化路由
  initRoutes: function() {
    store.commit('permission/setRoutes', asyncRoutes);
  },
  // 设置当前会话拥有的权限码集合
  setPerCodes: function(state, per_codes) {
    state.per_codes = per_codes;
  }

}

const actions = {
  // generateRoutes({ commit }) {
  //   return new Promise(resolve => {
  //     let accessedRoutes = filterAsyncRoutes(asyncRoutes)
  //     commit('SET_ROUTES', accessedRoutes)
  //     resolve(accessedRoutes)
  //   })
  // }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
