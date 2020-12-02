# 表注释


### 需求点

- 有的同学问到，以上都是在字段上标注类型，我能不能把注释写在表上呢？能！
- 与字段注释不同的是，表注释可以写多个，如下方示例所示：

``` java
	-- 兑换码表  
	drop table if exists sys_redeem;
	CREATE TABLE `sys_redeem` (
	  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id [no]', 
	  `name` varchar(200) COMMENT '红包名称 [text]', 
	  `create_time` datetime COMMENT '创建日期 [date-create]', 
	  PRIMARY KEY (`id`) USING BTREE
	) ENGINE=InnoDB AUTO_INCREMENT=182192 DEFAULT CHARSET=gbk ROW_FORMAT=COMPACT 
	COMMENT='兑换码表 [tree] [fk-count js=ser_comment.id.评论数量] [fk-sum js=ser_comment.id.评论数量]';
```

- 注：(如你所料, 注释中支持多个`[xxx]`，支持换行符) 
- 那么表注释中都有哪些能力呢？且往下看


### 所有表声明

| 声明		| 说明																			| 详见						|
| :--------	| :--------																		| :--------					|
| [table]	| 声明正常表信息, icon=菜单图标, 例: `[table icon=el-icon-plus]`					| 无	|
| [tree]	| 声明一个树形表格 (普通形式, 适用于整表数据<1000的场景)							| [树状表格](/gen/tree)	|
| [tree-lazy]	| 声明一个树形表格(懒加载形式, 适用于整表数据>1000的场景)						| [树状表格-懒加载](/gen/tree?id=_5、懒加载模式)	|
| [fk-s]	| 声明一个连接外键																| [连接外键](/gen/fk-s)	|
| [fk-count]| 声明一个聚合外键：取记录总数													| [聚合外键](/gen/fk-poly)	|
| [fk-max]	| 声明一个聚合外键：取最大值													| [聚合外键](/gen/fk-poly)	|
| [fk-min]	| 声明一个聚合外键：取最小值													| [聚合外键](/gen/fk-poly)	|
| [fk-sum]	| 声明一个聚合外键：取总和														| [聚合外键](/gen/fk-poly)	|
| [fk-avg]	| 声明一个聚合外键：取平均数													| [聚合外键](/gen/fk-poly)	|
| [fk-?]	| 声明一个聚合外键：自定义外键, 配合自定义sql, 可以写出任意外键, 详见文档示例	| [聚合外键](/gen/fk-poly)	|







