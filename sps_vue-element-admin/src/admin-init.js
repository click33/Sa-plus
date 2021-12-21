import sa from '@/sa-frame/sa';
import store from '@/store';

// admin模板初始化函数
export default function() {
  // console.log('初始化。。。。');
  sa.ajax('/AccAdmin/fristOpenAdmin', function(res) {
    // 当前用户信息，保存到本地中
    sa.$sys.setCurrUser(res.data.admin);
    store.dispatch('user/setNameAvatar', {
      name: res.data.admin.name,
      avatar: res.data.admin.avatar
    })

    // 权限数据
    sa.setAuth(res.data.per_list);
    store.commit('permission/setPerCodes', res.data.per_list);

    // 配置信息
    sa.$sys.setAppCfg(res.app_cfg);

    //
  }, { msg: null });
}
