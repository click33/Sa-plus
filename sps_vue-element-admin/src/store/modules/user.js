
const state = {
  name: '',
  avatar: '',
  introduction: ''
}

const mutations = {
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  }
}

const actions = {
  // 写入昵称和头像
  setNameAvatar({ commit, state }, info) {
    commit('SET_NAME', info.name)
    commit('SET_AVATAR', info.avatar)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
