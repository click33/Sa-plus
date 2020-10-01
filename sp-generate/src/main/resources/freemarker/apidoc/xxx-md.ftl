# ${t.tableComment}


---
### 1、增加
- 接口
``` api
	/${t.mkNameBig}/add
```
- 参数
``` p
<#list t.columnList as c>
	{${c.fieldType}}	${c.fieldName}			${c.columnComment2} 
</#list>
```
- 返回 
@import(res)


--- 
### 2、删除
- 接口
``` api
	/${t.mkNameBig}/delete
```
- 参数
``` p
	{${t.primaryKey.fieldType}}	${t.primaryKey.fieldName}			要删除的记录${t.primaryKey.fieldName}
```
- 返回
@import(res)


---
### 3、批量删除
- 接口
``` api
	/${t.mkNameBig}/deleteByIds
```
- 参数
``` p
	{数组}	ids			要删除的记录id数组，逗号隔开，例：ids=1,2,3,4
```
- 返回
@import(res)


---
### 4、修改
- 接口
``` api
	/${t.mkNameBig}/update
```
- 参数
``` p
<#list t.columnList as c>
	{${c.fieldType}}	${c.fieldName}			${c.columnComment2} 
</#list>
```
- 返回
@import(res)


---
### 5、查 - 根据id
- 接口
```  api 
	/${t.mkNameBig}/getById
```
- 参数
``` p
	{${t.primaryKey.fieldType}}	id			要查询的记录id
```
- 返回示例
``` js
	{
		"code": 200,
		"msg": "ok",
		"data": {
		<#list t.columnList as c>
			"${c.fieldName}": ${c.defaultValue},		// ${c.columnComment2}
		</#list>
		<#list t.getAllDbFk_12() as fk>
			"${fk.fieldName}": "",			// ${fk.fkPkConcatComment}
		</#list>
		<#list t.getAllDbFk_jh() as fk>
			"${fk.getAsColumnName_fs()}": "",			// ${fk.tx.comment} 
		</#list>
		},
		"dataCount": -1
	}
```


---
### 6、查 - 列表
- 接口
``` api
	/${t.mkNameBig}/getList
```
- 参数 (参数为null或者0时代表不限制此条件)
``` p
	{int}	pageNo = 1			当前页
	{int}	pageSize = 10		页大小 
<#list t.columnList as c>
	{${c.fieldType}}	${c.fieldName}			${c.columnComment2} 
</#list>
	{int}	sortType = 0		排序方式 (0 = 默认<#list t.columnList as c>, ${c_index + 1} = ${c.columnComment2}</#list>)
```
- 返回 
``` js
	{
		"code": 200,
		"msg": "ok",
		"data": [
			// 数据列表，格式参考getById 
		],
		"dataCount": 100	// 数据总数
	}
```




---
### 7、修改 - 空值不改
- 接口
``` api
	/${t.mkNameBig}/updateByNotNull
```
- 参数
``` p
<#list t.columnList as c>
	{${c.fieldType}}	${c.fieldName}			${c.columnComment2} 
</#list>
```
- 返回
@import(res)







