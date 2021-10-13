// 此处定义所有有关 sa-plus 的路由菜单 
// 如需添加自定义菜单，请不要更改此文件，请在 menu-list.js 里添加 (没有这个文件就新建) 
window.menuList = window.menuList || [];
window.menuList.unshift(
	{
		id: 'bas',
		name: '身份相关',
		isShow: false,	// 隐藏显示 
		info: '身份相关权限，不显示在菜单上', 
		childList: [
			{id: '1', name: '身份-超管', info: '最高权限，超管身份的代表（请谨慎授权）', isShow: false},
			{id: '11', name: '身份-普通账号', isShow: false, info: '无特殊权限'},
			{id: '99', name: '允许进入后台管理', isShow: false, info: '只有拥有这个权限的角色才可以进入后台'},
		]
	},
	{	
		id: 'console',
		name: '监控中心',
		icon: 'el-icon-view',
		info: '对本系统的各种监控',
		childList: [
			{id: 'sql-console', name: 'SQL监控台', url: 'sa-view-sp/sp-console/sql-console.html', info: 'sql控制台'},
			{id: 'redis-console', name: 'Redis控制台', url: 'sa-view-sp/sp-console/redis-console.html', info: 'redis常用工具'},
			{id: 'apilog-list', name: 'API请求日志', url: 'sa-view-sp/sp-apilog/api-log-list.html', info: '记录本系统所有的api请求'},
			{id: 'form-generator', name: '在线表单构建', url: 'https://mrhj.gitee.io/form-generator/#/'},
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
			// {id: 'apilog-list', name: '请求日志监控', url: 'sa-view-sp/sp-apilog/api-log-list.html', info: '记录本系统所有的api请求'},
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