import sa from '@/sa-frame/sa';
import store from '@/store';

// admin模板初始化函数
export default function() {
  // console.log('初始化。。。。');

  sa.ajax('/AccAdmin/getLoginInfo', function(res) {

    // 验证权限
    if(!(res.data.admin && res.data.perList.indexOf('in-system') > -1)) {
      sa.$sys.setCurrUser(res.data.admin);
      return alert('当前账号暂无进入后台权限');
    }

    // 当前用户信息，保存到本地中
    sa.$sys.setCurrUser(res.data.admin);
    store.dispatch('user/setNameAvatar', {
      name: res.data.admin.name,
      avatar: res.data.admin.avatar
    })

    // 权限数据
    sa.setAuth(res.data.perList);
    store.commit('permission/setPerCodes', res.data.perList);

    // 配置信息
    sa.$sys.setAppCfg(res.appCfg);

    //
  }, { msg: null });
}
