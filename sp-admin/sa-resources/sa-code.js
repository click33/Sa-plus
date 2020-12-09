// 在使用时，不建议你直接魔改模板的代码，以免在运行时出现意外bug，而是在本文件中根据模板的提供的API，来适应你的业务逻辑 
// sa-plus 快速开发平台:		http://sa-plus.dev33.cn
// ....

// ================================= 示例：一些基本信息 ================================= 


// ================================= 用户信息 和 菜单 =================================
sa.ajax('/AccAdmin/fristOpenAdmin', function(res) {

	// 验证权限 
	// if(!(res.data.admin && res.data.per_list.indexOf('99') > -1)) {
	// 	return sa.alert('当前账号暂无进入后台权限');
	// }	
	
	// 配置 
	sa_admin.title = "sa-plus 后台";
	sa_admin.logo_url = 'sa-resources/admin-logo.png';    // 设置logo图标地址 
	sa_admin.icon_url = "sa-resources/admin-logo.png";    // 设置logo图标地址 
	
	
	// 当前用户信息 
	sa_admin.user = {
		username: res.data.admin.name,
		avatar: !!res.data.admin.avatar ? res.data.admin.avatar : 'sa-resources/admin-logo.png' // 使用logo作为头像 
		// avatar: res.data.admin.avatar // 此写法为账号头像 
	};		
	sa.$sys.setCurrUser(res.data.admin);
	
	
	// 所有菜单
	// var myMenuList = window.menuList;    // window.menuList 在 menu-list.js 中定义 
	sa_admin.initMenu(res.data.per_list);    // 初始化菜单   
	sa.setAuth(res.data.per_list);		// 当前用户权限码集合  
	
	// 配置信息 
	sa.$sys.setAppCfg(res.app_cfg);
	
	// 初始化模板(必须调用) 
	sa_admin.init();	
	
}.bind(this), {msg: '正在加载登录信息', login_url: 'login.html'});



// 如果需要获得更多操作能力，如：动态添加菜单、删除菜单等
// 可直接 sa_admin.menuList 获得菜单引用，直接操作对象 


// ================================= 示例：js控制打开某个菜单 =================================

// 打开一个 菜单，根据 id
// sa_admin.showMenuById('1-1');	
 
// 关闭一个 页面，根据 id 
// sa_admin.closePageById('');
 
// 打开一个自定义 页面  
// sa_admin.showPage({id: 12345, name: '新页面', url: 'http://web.yanzhi21.com'});		// id尽量不要和已有的菜单id冲突，其它属性均可参照菜单项


// ================================= 设置user信息 =================================



// ================================= 示例：设置登录后的头像处，下拉可以出现的选项  =================================
sa_admin.dropList = [		// 头像点击处可操作的选项
	{
		name: '我的资料',
		click: function() {
			sa.showIframe('我的资料', 'sa-html-sp/sp-admin/admin-info.html', '700px', '600px');
		}
	},
    {
        name: '修改名称',
        click: function () {
			layer.prompt({title: '请输入新名称'}, function(pass, index){
				layer.close(index);
				sa.ajax('/admin/updateInfo', {name: pass}, function(res){
					sa_admin.user.username = pass;
					sa.ok2('修改成功');
				});
			});
        }
	},
    {
        name: '修改密码',
        click: function () {
			sa.showIframe('修改密码', 'sa-html-sp/sp-admin/update-password.html', '550px', '350px');
        }
	},
    {
        name: '切换账号',
        click: function () {
			// sa.showIframe('切换账号', 'login.html', '70%', '80%');
			sa.$page.openLogin('login.html');
        }
	},
	{
		name: '退出登录',
		click: function() {
			layer.confirm('退出登录？', function() {
				sa.ajax('/AccAdmin/doExit', function(res) {
					layer.alert('注销成功', function() {
						location.href="login.html";
					})
				})
			});
		}
	}
]


// 或者以下方式，增加配置项
// sa_admin.init({
// 	themeDefault: '1',	// 默认的主题，可选值：1、2、3、4
// 	switchDefault: '1',	// 默认的切换动画，可选值：fade、slide、cube、coverflow、flip
// });
