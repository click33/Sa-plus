# 管理员相关


---
### 1、添加管理员
- 接口
``` api
	/admin/add
```
- 参数
``` p
	{String}	name			名称
	{String}	avatar			头像
	{String}	phone			电话
	{long}		roleId			角色id
```
- 返回 
@import(res)


---
### 2、删除管理员
- 接口
``` api
	/admin/delete
```
- 参数
``` p
	{long}		id			管理员id
```
- 返回 
@import(res)


---
### 3、删除管理员 - 根据id列表 
- 接口
``` api
	/admin/deleteByIds
```
- 参数
``` p
	{String}		ids			管理员id列表，多个用逗号隔开，例如（ids=1,2,3）
```
- 返回 
@import(res)


---
### 5、修改资料：改名
- 接口
``` api
	/admin/update
```
- 参数
``` p
	{long}		id			管理员id
	{String}	name			管理员名称 
```
- 返回 
@import(res)


---
### 6、修改资料：改密码 
- 接口
``` api
	/admin/updatePassword
```
- 参数
``` p
	{long}		id			管理员id
	{String}	password	新密码 
```
- 返回 
@import(res)


---
### 7、修改资料：改头像 
- 接口
``` api
	/admin/updateAvatar
```
- 参数
``` p
	{long}		id			管理员id
	{String}	avatar		新头像地址 
```
- 返回 
@import(res)


---
### 8、修改资料：改账号状态 
- 接口
``` api
	/admin/updateStatus
```
- 参数
``` p
	{long}		id			管理员id
	{String}	status		要修改成为的状态（1=正常，2=封禁）
```
- 返回 
@import(res)


---
### 9、修改资料：改角色id
- 接口
``` api
	/admin/updateRole
```
- 参数
``` p
	{long}		id			管理员id
	{String}	roleId		角色id 
```
- 返回 
@import(res)


---
### 10、查 - 根据id 
- 接口
``` api
	/admin/getById
```
- 参数
``` p
	{long}		id			管理员id
```
- 返回 
``` js
{
    "code": 200,
    "msg": "ok",
    "data": {
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
    }
}
```


---
### 11、查询当前登录账号的详细信息 
- 接口
``` api
	/admin/getByCurr
```
- 返回 
``` js
{
    "code": 200,
    "msg": "ok",
    "data": {
		// 参考 getById 
    }
}
```


---
### 12、查询管理员列表 
- 接口
``` api
	/admin/getList
```
- 参数
``` p
	{int}		pageNo = 1			当前页
	{int}		pageSize = 10		页大小 
	{Long}		id					管理员id 
	{String}	name				管理员名称（模糊查询）
	{Long}		roleId				角色id
	{String}	sortType			排序方式（id=id降序 [default]，id__asc=id升序，loginTime=登录时间降序，loginCount=登录次数降序）
```
- 返回 
``` js
{
    "code": 200,
    "msg": "ok",
    "data": [
		// .. 管理员列表 
	]
}
```

---
### 12、修改我的账号信息 
- 接口
``` api
	/admin/updateInfo
```
- 参数
``` p
	{Long}		id					管理员id 
	{String}	name				管理员名称
```
- 返回 
@import(res)


---
### 13、修改我的账号密码
- 接口
``` api
	/AdminPassword/update
```
- 参数
``` p
	{String}		oldPwd			旧密码
	{String}		newPwd			新密码
```
- 返回 
@import(res)



---
### 14、模拟账号登录
- 接口
``` api
	/admin/runAs
```
- 参数
``` p
	{Long}		adminId					管理员id 
```
- 返回 
``` js
{
    "code": 200,
    "msg": "ok",
    "data": 'xxxxxxxxxxxxxxxxx'		// 此账号的会话 Token 
}
```





















