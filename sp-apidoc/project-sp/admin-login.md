# Admin 管理员登录日志 


---
### 1、删除一条登录日志 
- 接口
``` api
	/SpAdminLogin/delete
```
- 参数
``` p
	{int}		id			记录id
```
- 返回 
@import(res)


---
### 2、批量删除 - 根据id列表 
- 接口
``` api
	/SpAdminLogin/deleteByIds
```
- 参数
``` p
	{String}		ids			记录id列表，多个用逗号隔开，例如（ids=1,2,3）
```
- 返回 
@import(res)




---
### 3、根据id查询记录信息 
- 接口
``` api
	/SpAdminLogin/getById
```
- 参数
``` p
	{int}		id			角色id
```
- 返回 
``` js
{
    "code": 200,
    "msg": "ok",
    "data": [
        {
            "id": 1004,		// 记录id
            "accId": 10002,	// 登录账号 
            "accToken": "18b10fdb-46ed-4f0e-8420-b62b75557b90",		// 会话token 
            "loginIp": "127.0.0.1",		// 登录ip
            "address": "山东 济南",		// 登录所在地
            "device": "Chrome 9",		// 客户端设备标识
            "system": "Windows 10",		// 客户端系统标识
            "createTime": "2022-02-24 01:56:58",	// 登录的时间
            "spAdminName": "admin",			// 登录账号名称
            "spAdminAvatar": "https://oss.dev33.cn/sa-plus/in-file/avatar2.png"	// 登录账号头像 
        }
    ],
    "dataCount": 4,	// 数据总数 
}
```


---
### 4、查询登录日志列表 
- 接口
``` api
	/SpAdminLogin/getList
```
- 参数
``` p
	{long}			id			记录id 
	{long}			accId		账号id
	{String}		accToken	会话token 
	{String}		loginIp		登录地址
```
- 返回 
``` js
{
    "code": 200,
    "msg": "ok",
	"data": [
		// .... 
	]
}
```
