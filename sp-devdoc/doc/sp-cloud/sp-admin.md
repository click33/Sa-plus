# 后台管理UI界面

1. 首先将`sa-plus`基础版下载到本地，`sp-admin`为后台管理UI界面
2. 打开`\sp-admin\static\sa.js`文件，修改后台服务地址，如下所示
``` js
	// 公司开发环境
	var cfg_dev = {
		api_url: 'http://127.0.0.1:8080/sp-admin',	// 所有ajax请求接口父地址
		web_url: 'http://www.baidu.com'		// 此项目前台地址 (此配置项非必须)
	}
```
注: 将`cfg_dev.api_url`改为：`http://127.0.0.1:8080/sp-admin`

3. 在前端ide中导入`sp-admin`项目，并打开`index.html`, 默认账号为: `sa` `123456`
4. 如果看到如下界面，将证明集成成功

![测试qps限流](http://oss.dev33.cn/sp-cloud/sp-admin-home.png)



