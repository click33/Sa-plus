# Redis操作 

---
### 1、获取预览数据信息
- 接口
``` api
	/RedisConsole/getPreInfo
```
- 返回 
``` js
{
    "code": 200,
    "msg": "ok",
    "data": {
        "keys_count": 7,		// key总数 
        "keyspace_hits": "25173",		// 命中次数 
        "used_memory_human": "695.59K",	// 已经占用内存数量 
        "used_memory_peak_human": "762.60K",	// 内存消耗峰值 
        "uptime_in_seconds": "1965776",			// Redis 已经启动的秒数 
        "isGtMax": false,						// key总数 是否已经超过了最大值 
    }
}
```


---
### 2、查询 key 集合 
- 接口
``` api
	/RedisConsole/getKeys
```
- 参数
``` p
	{String}		k			筛选条件，支持 * 模糊匹配 
```
- 返回 
``` js
{
    "code": 200,
    "msg": "ok",
    "data": [			// 所有 key 列表 
        "api_pcode_list::1",
        "api_pcode_list::11",
        "api_pcode_list::2",
        "satoken:login:session:10001",
        "satoken:login:token:5b317500-9f75-4f8d-b2ba-c15174f4254c",
        "sp_cfg_::app_cfg",
        "sp_cfg_::server_cfg"
    ]
}
```


---
### 3、查询某个 key 的详细信息 
- 接口
``` api
	/RedisConsole/getByKey
```
- 参数
``` p
	{String}		key			key值 
```
- 返回 
``` js
{
    "code": 200,
    "msg": "ok",
    "data": {
        "key": "xxxxxxxxxxxxxx",	// key
        "value": "10001",			// value	
        "ttl": 2464285				// 剩余有效期，单位：秒 
    }
}
```


---
### 4、添加一个键值
- 接口
``` api
	/RedisConsole/set
```
- 参数
``` p
	{String}		key			key值 
	{String}		value		value值 
	{long}			ttl			有效期（单位：秒）
```
- 返回 
@import(res)


---
### 5、删除一个键值
- 接口
``` api
	/RedisConsole/del
```
- 参数
``` p
	{String}		key			key值 
```
- 返回 
@import(res)


---
### 6、修改一个键值 
- 接口
``` api
	/RedisConsole/updateValue
```
- 参数
``` p
	{String}		key			key值 
	{String}		value		新的 value 值 
```
- 返回 
@import(res)


---
### 7、修改一个键值的 ttl 值 
- 接口
``` api
	/RedisConsole/updateTtl
```
- 参数
``` p
	{String}		key			key值 
	{long}		ttl			新的 ttl 值 
```
- 返回 
@import(res)



---
### 8、批量删除 key 值 
- 接口
``` api
	/RedisConsole/deleteByKeys
```
- 参数
``` p
	{String}		keys			key值列表，多个用逗号隔开，例如：a,b,c
```
- 返回 
@import(res)


























