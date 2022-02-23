# 用户表

---

### 1、上传图片 
- 接口
``` api
	/upload/image
```
- 参数
``` p
	{二进制流}	file			文件流  
```
- 返回 
``` js
	{
		"code": 200,
		"msg": "ok",
		"data": 'xxxxxxxxxxxxxx',  // 上传过的文件地址  
		"dataCount": 0	// 数据总数
	}
```

### 2、上传视频
- 接口
``` api
	/upload/video
```
- 参数
``` p
	{二进制流}	file			文件流  
```
- 返回 
``` js
	{
		"code": 200,
		"msg": "ok",
		"data": 'xxxxxxxxxxxxxx',  // 上传过的文件地址  
		"dataCount": 0	// 数据总数
	}
```


### 3、上传音频
- 接口
``` api
	/upload/audio
```
- 参数
``` p
	{二进制流}	file			文件流  
```
- 返回 
``` js
	{
		"code": 200,
		"msg": "ok",
		"data": 'xxxxxxxxxxxxxx',  // 上传过的文件地址  
		"dataCount": 0// 数据总数
	}
```

### 4、上传apk
- 接口
``` api
	/upload/apk
```
- 参数
``` p
	{二进制流}	file			文件流  
```
- 返回 
``` js
	{
		"code": 200,
		"msg": "ok",
		"data": 'xxxxxxxxxxxxxx',  // 上传过的文件地址  
		"dataCount": 0// 数据总数
	}
```
