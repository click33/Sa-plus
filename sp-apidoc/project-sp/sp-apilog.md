# API 访问日志 

---
### 1、删除
- 接口
``` api
	/SgApilog/delete
```
- 参数
``` p
	{long}		id			记录id
```
- 返回 
@import(res)


---
### 2、批量删除
- 接口
``` api
	/SgApilog/deleteByIds
```
- 参数
``` p
	{数组}	ids			要删除的记录id数组，逗号隔开，例：ids=1,2,3,4
```
- 返回
@import(res)


---
### 3、根据日期范围批量删除 
- 接口
``` api
	/SgApilog/deleteByStartEnd
```
- 参数
``` p
	{String}	startTime		开始日期
	{String}	endTime			结束日期
```
- 返回
@import(res)


---
### 4、查集合 - 根据条件
- 接口
``` api
	/SgApilog/getList
```
- 参数 （参数为空时代表忽略指定条件）
``` p
	{int}		pageNo = 1			当前页
	{int}		pageSize = 10		页大小 
	{Long}		id					记录id 
	{String}	reqToken			请求Token
	{String}	reqIp				请求IP 
	{String}	reqApi				请求API
	{int}		resCode				响应状态码 
	{long}		userId				请求的 userId
	{long}		adminId				请求的 adminId
	{String}	sTime				请求时间范围：开始
	{String}	eTime				请求时间范围：结束
	{int}		sortType			排序方式 (id = id倒叙, start_time = 请求时间倒叙，cost_time = 请求耗时倒叙)
```
- 返回 
``` js
	{
		"code": 200,
		"msg": "ok",
		"data": [
			{
			    "id": "1496168883617402881",	// 记录id
			    "reqIp": "127.0.0.1",			// 请求ip
			    "reqApi": "/role/getList",		// 请求API
			    "reqParame": "{}",				// 请求参数 
			    "reqType": "POST",				// 请求类型 
			    "reqToken": "5b317500-9f75-4f8d-b2ba-c15174f4254c",		// 请求Token 
			    "reqHeader": "{xxx:xxx}",				// 请求头
			    "resCode": 200,					// 响应code码
			    "resMsg": "ok",					// 响应msg消息 
			    "resString": "{xxx:xxx}",		// 响应体 String形式 
			    "userId": 0,					// 请求者 userId
			    "adminId": 10001,				// 请求者 adminId
			    "startTime": "2022-02-23 01:03:53.988",		// 请求时间
			    "endTime": "2022-02-23 01:03:54.058",		// 响应时间
			    "costTime": 70								// 请求耗时，单位：ms 
			}
			// ... 
		],
		"dataCount": 100	// 数据总数
	}
```



---
### 5、API 数据统计 
- 接口
``` api
	/SgApilog/staBy
```
- 参数 （参数为空时代表忽略指定条件）
``` p
	{int}		pageNo = 1			当前页
	{int}		pageSize = 10		页大小 
	{Long}		id					记录id 
	{String}	reqToken			请求Token
	{String}	reqIp				请求IP 
	{String}	reqApi				请求API
	{int}		resCode				响应状态码 
	{long}		userId				请求的 userId
	{long}		adminId				请求的 adminId
	{String}	sTime				请求时间范围：开始
	{String}	eTime				请求时间范围：结束
```
- 返回 
``` js
	{
		"code": 200,
		"msg": "ok",
		"data": {
			cost_time_count: 503342	// 所有请求总耗时，单位毫秒
		},
		"dataCount": 100	// 数据总数
	}
```

