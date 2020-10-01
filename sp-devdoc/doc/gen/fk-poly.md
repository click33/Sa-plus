# 聚合外键


### 何为聚合外键
- 聚合外键就是指以聚合函数(`count`、`max`、`min`、`sum`、`avg`)为基础的关联查询 
- 以一个论坛的文章表和评论表举例：
> **在查询文章列表的时候，需要根据评论表统计出每条文章的评论数量**
> ``` sql
> 	select *,
> 	(select count(*) from 评论表 where 文章_id = 文章表.id) as 评论数量 
> 	from 文章表
> ```
> 如上sql，将查询文章表所有数据+每条文章的评论数量


### 需求点
如上，聚合外键是我们经常遇到的查询需求，比如以下情景：

- 查询商品列表，需要统计每条商品的评论数量
- 查询分类列表，需要统计每个分类有多少个引用
- 查询用户列表，需要根据文章表统计出每个用户发表的文章数量
- ... 

那么我们如何在表注释上声明一聚合外键信息呢？


### 详细语法 

- 举例
``` java
	CREATE TABLE `ser_goods` (
		...字段信息 
	) ENGINE=InnoDB AUTO_INCREMENT=182192 DEFAULT CHARSET=gbk ROW_FORMAT=COMPACT 
	COMMENT='商品表 [fk-count jt=ser_comment, jc=goods_id, comment=评论数量] [fk-count js=ser_comment.goods_id.评论数量2]';
```
- 将根据配置的外键信息生成如下sql 
``` java
	select *, 
	(select count(*) from ser_comment where goods_id = ser_goods.id) as ser_comment_count, 
	(select count(*) from ser_comment where goods_id = ser_goods.id) as ser_comment_count2
	from ser_goods 
```


**所有可配置字段**
- `jt` = 连接表名
- `jc` = 连接字段名
- `comment` = 字段注释
- `js` = 以上三者的缩写, 语法见下方示例
- `and` = 追加条件 (非必写)
- `where` = 完全自定义条件 (非必写)
- `sql` = 完全自定义sql (非必写) 
- `ac` = 设定聚合列, 默认: * (非必写) 
- `java-type` = 设定java中类型，默认值 long(非必写) 
- `as` = 给列起个别名(非必写) 
- `ttc` = 指定连接本表的哪个字段, 默认连主键 (一般不用指定) 


### 示例详解

- 基本形式 (依次指定jt、jc、comment三个元素)
``` 
	[fk-count jt=ser_comment, jc=goods_id, comment=评论数量]
```

- 缩写形式 (作用同上)
``` 
	[fk-count js=ser_comment.goods_id.评论数量2]
```

- 如果你还有附加条件，可以使用 `and=` 追加条件 
``` 
	[fk-count js=ser_comment.goods_id.评论数量2, and=(type = 13)]
```

- 如果你的where条件非常特殊，可以使用 `where=` 自定义where后的内容 
``` 
	[fk-count jt=ser_comment, where=(balabala...可以写任意sql语句), comment=评论数量]
```

- 如果你的整个sql语句都非常特殊，可以使用 `sql=` 自定义整个sql内容 
``` 
	[fk-? sql=(balabala...可以写任意sql语句), as=ct_count, comment=评论数量]
```


### 注意事项
- 聚合外键不只`fk-count`，还有：`fk-max`、`fk-min`、`fk-sum`、`fk-avg`，分别代表最大值、最小值、求和、求平均数
- 不难看出，在`自定义sql`时，`fk-`的类型已经无足轻重，此时你可以写`任意sql`，而`fk-`的类型可以直接声明为: `fk-?`






