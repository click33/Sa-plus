import variables from '@/styles/element-variables.scss'
import defaultSettings from '@/settings'

const { showSettings, tagsView, fixedHeader, sidebarLogo } = defaultSettings

let state = {
  theme: variables.theme,
  showSettings: showSettings,
  tagsView: tagsView,
  fixedHeader: fixedHeader,
  sidebarLogo: sidebarLogo
}

// 从本地读取
if (localStorage.layoutSettings) {
  state = JSON.parse(localStorage.layoutSettings);
}

const mutations = {
  CHANGE_SETTING: (state, { key, value }) => {
    // eslint-disable-next-line no-prototype-builtins
    if (state.hasOwnProperty(key)) {
      state[key] = value
    }
  },
  // 重置
  reset_setting(state, { key, value }) {
    state.theme = variables.theme;
    state.showSettings = showSettings;
    state.tagsView = tagsView;
    state.fixedHeader = fixedHeader;
    state.sidebarLogo = sidebarLogo;
  }
}

const actions = {
  changeSetting({ commit }, data) {
    commit('CHANGE_SETTING', data)
  },
  // 重置配置
  resetSetting: function({ commit }, data) {
    commit('reset_setting', data)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
