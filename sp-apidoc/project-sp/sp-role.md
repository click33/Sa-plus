# Role 角色操作 

---
### 1、添加一个角色
- 接口
``` api
	/role/add
```
- 参数
``` p
	{int}		id			角色id
	{String}	name		角色名称
	{String}	info		角色描述
```
- 返回 
@import(res)


---
### 2、删除一个角色
- 接口
``` api
	/role/delete
```
- 参数
``` p
	{int}		id		角色id
```
- 返回 
@import(res)


---
### 3、修改角色信息
- 接口
``` api
	/role/update
```
- 参数
``` p
	{int}		id			角色id
	{String}	name		角色名称
	{String}	info		角色描述
```
- 返回 
@import(res)


---
### 4、根据id查询角色信息 
- 接口
``` api
	/role/getById
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
	"data": {
		"id": 1,				// 角色id
		"name": "开发者",		// 角色名称
		"info": "系统开发人员，最高权限",	// 角色描述
		"isLock": 1,				// 是否锁定（1=是，2=否）
		"createTime": "2022-02-17 04:08:47"	// 此角色的创建时间
	}
}
```


---
### 5、查询角色列表 
- 接口
``` api
	/role/getList
```
- 参数
``` p
	{String}		name			角色名称筛选（模糊匹配）
```
- 返回 
``` js
{
    "code": 200,
    "msg": "ok",
	"data": [
		{
			"id": 1,				// 角色id
			"name": "开发者",		// 角色名称
			"info": "系统开发人员，最高权限",	// 角色描述
			"isLock": 1,				// 是否锁定（1=是，2=否）
			"createTime": "2022-02-17 04:08:47"	// 此角色的创建时间
		},
		// .... 
	]
}
```


---
### 6、拉取指定角色拥有的所有权限码 
- 接口
``` api
	/SpRolePermission/getPcodeByRid
```
- 参数
``` p
	{long}		roleId			角色id
```
- 返回 
``` js
{
    "code": 200,
    "msg": "ok",
    "data": [	// 拥有的所有权限码  
        "bas",
        "dev",
		// ... 
    ]
}
```


---
### 7、拉取当前登录角色拥有的所有权限码 
- 接口
``` api
	/SpRolePermission/getPcodeByCurrRid
```
- 返回 
``` js
{
    "code": 200,
    "msg": "ok",
    "data": [	// 拥有的所有权限码  
        "bas",
        "dev",
		// ... 
    ]
}
```


---
### 8、修改指定角色拥有的所有权限码 
- 接口
``` api
	/SpRolePermission/updatePcodeByRid
```
- 参数
``` p
	{long}		roleId			角色id
	{String}	codes			新权限码集合，多个用逗号隔开，例如：user-add,user-update,user-delete
```
- 返回 
@import(res)














