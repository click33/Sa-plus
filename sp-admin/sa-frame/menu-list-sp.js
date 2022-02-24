// 此处定义所有有关 sa-plus 的路由菜单 
// 如需添加自定义菜单，请不要更改此文件，请在 menu-list.js 里添加 (没有这个文件就新建) 
window.menuList = window.menuList || [];
window.menuList.unshift(
	{
		id: 'bas',
		name: '身份相关',
		isShow: false,	// 隐藏显示 
		info: '控制角色的身份', 
		childList: [
			{id: 'dev', name: '开发者权限', info: '系统最高权限（请谨慎授权）', isShow: false},
			{id: 'in-system', name: '后台管理权限', info: '拥有此权限才可以进入后台管理系统', isShow: false},
		]
	},
	{	
		id: 'auth',
		name: '权限控制',
		icon: 'el-icon-unlock',
		info: '对系统角色权限的分配等设计，敏感度较高，请谨慎授权',
		childList: [
			{id: 'role-list', name: '角色列表', url: 'sa-view-sp/sp-role/role-list.html', info: '管理系统各种角色'},
			{id: 'menu-list', name: '菜单列表', url: 'sa-view-sp/sp-role/menu-list.html', info: '所有菜单项预览'},
			{id: 'admin-list', name: '管理员列表', url: 'sa-view-sp/sp-admin/admin-list.html', info: '所有管理员账号'},
			{id: 'admin-add', name: '管理员添加', url: 'sa-view-sp/sp-admin/admin-add.html', info: '添加一个管理员'},
			{id: 'sp-admin-login', name: '管理员登录日志', url: 'sa-view-sp/sp-admin-login/sp-admin-login-list.html', info: '查看管理员历史登录信息'},
		]
	},
	{	
		id: 'console',
		name: '监控中心',
		icon: 'el-icon-view',
		info: '对本系统的各种监控',
		childList: [
			{id: 'redis-console', name: 'Redis控制台', url: 'sa-view-sp/sp-console/redis-console.html', info: 'redis常用工具'},
			{id: 'apilog-list', name: 'API请求日志', url: 'sa-view-sp/sp-apilog/api-log-list.html', info: '记录本系统所有的api请求'},
			{id: 'sql-console', name: 'SQL 监控台', url: 'sa-view-sp/sp-console/sql-console.html', info: 'sql控制台'},
			{id: 'form-generator', name: '在线表单构建', url: 'https://mrhj.gitee.io/form-generator/#/'},
		]
	},
	{
		id: 'sp-cfg', 
		name: '系统配置', 
		icon: 'el-icon-setting', 
		info: '有关系统的一些配置', 
		childList: [
			{id: 'sp-cfg-app', name: '系统对公配置', url: 'sa-view-sp/sp-cfg/app-cfg.html'},
			{id: 'sp-cfg-server', name: '服务器私有配置', url: 'sa-view-sp/sp-cfg/server-cfg.html'},
		]
	},
);