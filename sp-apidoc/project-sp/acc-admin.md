# 登录


---
### 1、登录
- 接口
``` api
	/AccAdmin/doLogin
```
- 参数
``` p
	{String}	key			账号 （账号id / 名称 / 手机号）
	{String}	password		密码 
```
- 返回 
``` js
{
    "code": 200,
    "msg": "ok",
    "data": {
        "admin": {
            "id": 10001,		// 账号id 
            "name": "sa",		// 账号名称 
            "avatar": "https://oss.dev33.cn/sa-plus/in-file/avatar1.jpg",	// 头像地址 
            "password": "********",	// 密码 
            "phone": null,		// 手机号
            "roleId": 1,		// 角色id
            "status": 1,		// 账号状态（1=正常，2=禁用）
            "createByAid": -1,	// 此账号的创建人id，-1代表没有 
            "createTime": "2022-02-20 22:46:04",	// 此账号创建时间
            "loginTime": "2022-02-21 18:39:46",		// 此账号最后一次登录时间 
            "loginIp": "127.0.0.1",				// 此账号最后一次登录IP 
            "loginCount": 10,					// 此账号累计登录次数 
            "roleName": "开发者"				// 角色名称 
        },
        "appCfg": "{}",		// 此系统的全局配置，格式为 JSON 字符串
        "perList": [	// 拥有的权限码列表 
            "bas",
            "dev",
			... 
        ],
        "tokenInfo": {
            "tokenName": "satoken",		// token名称，
            "tokenValue": "5b317500-9f75-4f8d-b2ba-c15174f4254c",	// token的值，以后的所有请求都将此token放到header中，参数名为satoken
        }
    }
}
```


---
### 2、注销
- 接口
``` api
	/AccAdmin/doExit
```
- 返回 
@import(res)


---
### 3、获取会话信息
- 接口
``` api
	/AccAdmin/getLoginInfo
```
- 返回 
``` js
{
    "code": 200,
    "msg": "ok",
    "data": {
        "admin": {
            "id": 10001,		// 账号id 
            "name": "sa",		// 账号名称 
            "avatar": "https://oss.dev33.cn/sa-plus/in-file/avatar1.jpg",	// 头像地址 
            "password": "********",	// 密码 
            "phone": null,		// 手机号
            "roleId": 1,		// 角色id
            "status": 1,		// 账号状态（1=正常，2=禁用）
            "createByAid": -1,	// 此账号的创建人id，-1代表没有 
            "createTime": "2022-02-20 22:46:04",	// 此账号创建时间
            "loginTime": "2022-02-21 18:39:46",		// 此账号最后一次登录时间 
            "loginIp": "127.0.0.1",				// 此账号最后一次登录IP 
            "loginCount": 10,					// 此账号累计登录次数 
            "roleName": "开发者"				// 角色名称 
        },
        "appCfg": "{}",				// 此系统的全局配置，格式为 JSON 字符串
        "perList": ["bas", "dev"]	// 拥有的权限码列表 
    }
}
```







