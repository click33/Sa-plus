# 常见操作
本篇介绍开发中常见问题以及对应的解决方案 

--- 

### 1、后台管理如何添加一个菜单?
1. 菜单文件定义在 `sp-admin/sa-resources/` 的 `menu-list.js`中，打开此文件，按照既有格式添加菜单即可 
2. 添加完成后，刷新页面，如果菜单没有显示，可能是权限问题，打开：权限管理 -> 角色列表，在列表中点击分配权限按钮，选择相应的菜单即可 


### 2、能否用js操作sa-admin的面板打开或者关闭某些窗口
- 能，参考：[sa-admin常见操作](http://sa-admin.dev33.cn/#1-1)


### 3、能否将权限分配细化到页面的一个按钮上?
- 能，参考：[sa-admin鉴权操作](http://sa-admin.dev33.cn/#1-2)


### 4、后台管理右上角的头像昵称等信息怎么设置？
- 有关sa-admin后台初始化的操作，都定义在 `sp-admin/sa-resources/` 的 `sa-code.js` 文件里，具体可查看本文件相关注释


### 5、系统配置模块，怎么使用？
- 大多数项目中，都会有一些杂七杂八的配置，比如：全局配送费、是否开放注册、新用户默认头像等等
- 像这样的配置，我们统一定义为一个json字符串，存储在数据库中 
- 于是就产生好几个问题，配置怎么读？怎么写？怎么添加一个配置项？怎么读取一个配置项？
- 且往下看 


### 6、如何添加并读取一个配置项？
配置项分为`服务器私有配置` 与 `系统对公配置`，以系统对公配置为例：
1. 打开 `sp-admin/sa-html-sp/sp-cfg/app-cfg.html` 文件 
2. 在 `create_m` 函数中，给配置项添加一个默认值，例如：`test_name: '张三',	// 测试名称 `
3. 在上方的html中，仿照既有格式，为 `test_name` 配置项添加一个输入框，例如：
``` html
	<div class="c-item br">
		<label class="c-label">测试名称：</label>
		<el-input size="mini" v-model="m.test_name"></el-input>
	</div>
```
4. 刷新页面，点击保存按钮一次
5. 在后端代码中，打开 `com.pj.project4sp.spcfg.SpCfgUtil` 类，仿照既有格式，复制一个读取方法，例如：
``` java 
	// 获取配置信息：测试名称 
	public static String get_test_name() {
		return SpCfgUtil.getAppCfg("test_name", "");
	}
```
6. 然后就可以在代码任意处，获取这一项配置了, 例如：
``` java 
	System.out.println(SpCfgUtil.get_test_name());
```




