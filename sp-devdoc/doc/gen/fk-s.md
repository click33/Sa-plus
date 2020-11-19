# 连接外键


### 1、何为连接外键
- 连接外键就是在一个单表查询时，通过`列子查询`的方式，带出其他表某个字段的查询方式
- 已商品表和分类表举例：
> **在查询商品表时，需要根据分类id得知分类名称**
> ``` sql 
> 	select *, 
> 	(select name from sys_type where id = sys_goods.type_id) as sys_type_name
> 	from sys_goods 
> ```
> 如上sql，将查询出商品表所有字段+分类表的name字段


### 2、需求点
如上，连接外键是我们经常遇到的查询需求，比如以下情景：

- 查询商品表时，顺带根据分类id查出分类名称
- 查询订单表时，顺带根据商品id查询商品名称
- 查询成绩表时，顺带根据用户id查询用户名称
- ... 

那么我们如何在表注释上声明一连接外键信息呢？



### 3、详细语法 

- 举例
``` js
	CREATE TABLE `ser_article` (
		... 
	) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT 
	COMMENT='文章表 [fk-s js=(type_id=sys_type.id), show=name.所属分类]';
```
- 将根据配置的外键信息生成如下sql 
``` java
	select *, 
	(select name from sys_type where id = ser_article.type_id) as sys_type_name, 
	from ser_article 
```


**配置详解**
- `fk-s`: 代表声明一个连接外键
- `js` = 配置连接信息，格式为`(curr=jt.jc)`，含义为`(当前外键字段=要连接的表名.要连接的表字段)`，此简写可以拆分成以下格式：
	- `curr` = 当前外键字段 
	- `jt` = 要连接的表名 
	- `jc`= 要连接的表字段 
- `show` = 配置展示信息，格式为`catc.comment`，此简写可以拆分成以下配置：
	- `catc` = 要展示的展示列 
	- `comment` = 展示列的注释 
- `drop` = 是否在表格检索条件里，展示下拉列表框 (可省略)
- `click` = 是否在表格展示时，点击跳转详情 (可省略)
- `java-type` = 设定java中类型，默认值 String (可省略)
- `as` = 给列起个别名，如不配置框架会自动计算合适的列别名 (可省略) 


### 4、示例详解


- 缩写形式 (指定外键的关联关系，和要展示的列) **推荐形式**
``` 
	[fk-s js=(type_id=sys_type.id), show=name.所属分类]
```

- 拆分形式 (依次指定curr、jt、jc、catc、comment五个元素) 
``` 
	[fk-s curr=type_id, jt=sys_type, jc=id, catc=name, comment=所属分类]
```

- 把展示字段的input输入框改为下拉列表框
``` 
	[fk-s js=(type_id=sys_type.id), show=name.所属分类, drop]
```

- 让展示的字段也能通过点击打开此数据详情
``` 
	[fk-s js=(type_id=sys_type.id), show=name.所属分类, click]
```

- 从字表里带出多个值 (show属性可以一直点下去，带出多个值)
``` 
	[fk-s js=(type_id=sys_type.id), show=name.所属分类.icon.分类图标]
```

### 5、注意事项
- 参数不限制格式，可以自由组合，只要符合[xx a=1, b=2]的格式即可






