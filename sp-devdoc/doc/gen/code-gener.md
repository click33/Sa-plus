# 代码生成器 


### 导入测试sql
- 文件：`doc/test-data.sql`, 是为了测试代码生成准备的库，请先在数据库中导入 


### 导入代码生成器
1. 在后端ide中导入项目 `sp-generate`，此为代码生成器
2. 导入项目后，直接运行main方法就可以生成代码
3. 运行前，请先确认相关配置信息，主要的注意点为：
	- 服务端代码-存放路径
	- 后台管理端项目地址（这些可直接设置为`sp-server`和`sp-admin`项目所在路径）
	- 代码作者等基础信息
5. 详细可查看代码注释
6. 生成的后台管理页面如果在页面菜单处看不到，那可能是无权限，在 `权限管理->角色列表->分配权限` 选中即可
7. 生成代码的关键点，在于表注释的特殊声明，具体可查看下方的：[表注释特殊声明](#表注释特殊声明)



### 表注释特殊声明
1. **代码生成器的特殊声明，可以让代码生成器更加智能**
2. 如果在建表时不写特殊声明，则只能生成普通输入框
3. 如果要生成特殊的表单，如：数值输入、多行文本域、图片上传、多图上传、富文本等等，则需要写上特殊声明
4. 语法为，写一个中括号，跟上关键字：`字段注释 [特殊声明]`
5. 参考以下示例 

### 一个简单的例子

例如以下sql，将会自动生成如下所示的UI界面

##### 建表语句
``` sql 
-- 商品表 
drop table if exists ser_goods;
CREATE TABLE `ser_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品id [no]', 
  `name` varchar(200) DEFAULT NULL COMMENT '商品名称 [text]', 
  `avatar` varchar(512) DEFAULT NULL COMMENT '商品头像 [img]', 
  `image_list` varchar(2048) COMMENT '轮播图片 [img-list]', 
  `content` text COMMENT '图文介绍 [f]', 
  `remark` varchar(512) DEFAULT NULL COMMENT '商品备注 [textarea]',
  `money` int(11) DEFAULT '0' COMMENT '商品价格 [num]', 
  `stock_count` int(11) DEFAULT 0 COMMENT '剩余库存 [num]',
  `status` int(11) DEFAULT '1' COMMENT '商品状态 (1=上架,2=下架) [j]',
  `create_time` datetime COMMENT '创建日期 [date-create]',
  `update_time` datetime COMMENT '更新日期 [date-update]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='商品表';


INSERT INTO `ser_goods`(`id`, `name`, `avatar`, `image_list`, `remark`, `content`, `money`, `stock_count`, `status`, `create_time`, `update_time`) VALUES (1001, '小苹果', 'http://127.0.0.1:8099/upload/image/2020/05-11/1589130441278158564136.jpg', 'http://127.0.0.1:8099/upload/image/2020/05-11/15891304215541588315943.png', '这是一个小呀小苹果', '这是一个小呀小苹果<p><br></p>', 23, 213, 1, now(), now());
INSERT INTO `ser_goods`(`id`, `name`, `avatar`, `image_list`, `remark`, `content`, `money`, `stock_count`, `status`, `create_time`, `update_time`) VALUES (1002, '大鸭梨', 'http://127.0.0.1:8099/upload/image/2020/05-11/15891304588142094778376.png', 'http://127.0.0.1:8099/upload/image/2020/05-11/15891301925381859798545.jpg', '大鸭梨', '<p>大鸭梨图文介绍</p>', 214, 234, 1, now(), now());
INSERT INTO `ser_goods`(`id`, `name`, `avatar`, `image_list`, `remark`, `content`, `money`, `stock_count`, `status`, `create_time`, `update_time`) VALUES (1003, '小橘子', 'http://127.0.0.1:8099/upload/image/2020/05-11/15891326019482012079187.jpg', 'http://127.0.0.1:8099/upload/image/2020/05-11/1589133225670119768604.jpg', '小橘子', '<p>小橘子</p>', 123, 123, 2, now(), now());
	
```


##### 最终生成效果

![列表](https://color-test.oss-cn-qingdao.aliyuncs.com/sa-plus/g-list.png)
![增加/修改](https://color-test.oss-cn-qingdao.aliyuncs.com/sa-plus/g-update.png)		
		


### 所有特殊声明 

| 声明				 | 说明															| 详见		|
| :--------			| :--------														| :--------	|
| [text]			| 声明一个普通输入框，这是默认值，还可以写成 `[t]`、`[input]`   （`[text j=like]代表模糊查询`）			| 无		|
| [no]			| 代表生成表单时忽略此字段										| 无		|
| [img]			| 声明一个图片字段，会生成图片上传								| 无		|
| [audio]			| 声明一个音频字段，会生成音频上传								| 无		|
| [video]			| 声明一个视频字段，会生成视频上传								| 无		|
| [file]			| 声明一个文件字段，会生成文件上传(需在`UploadConfig.java`中配置文件后缀白名单)			| 无		|
| [img-list]		| 声明一个图片集合字段，会生成多图片上传，还可以写成 `[imgList]`、`[img_list]` 	| 无		|
| [audio-list]		| 声明一个音频集合字段，会生成多音频上传，还可以写成 `[audioList]`、`[audio_list]`	| 无		|
| [video-list]		| 声明一个视频集合字段，会生成多视频上传，还可以写成 `[videoList]`、`[video_list]`	| 无		|
| [file-list]		| 声明一个文件集合字段，会生成多文件上传，还可以写成 `[fileList]`、`[file_list]`	| 无		|
| [img-video-list]		| 声明一个图片与视频混合的集合字段，还可以写成 `[imgVideoList]`、`[img_video_list]`	| 无		|
| [richtext]		| 声明一个富文本字段，会生成富文本插件，还可以写成 `[f]`			| 无		|
| [textarea]		| 声明一个多行文本域											| 无		|
| [num]			| 声明数值输入字段												| 无		|
| [enum]			| 声明一个枚举字段，具体语法请查看下方示例，还可以写成 `[j]`		| 无		|
| [date]			| 声明一个日期字段												| 无		|
| [date-create]	| 声明一个日期字段（数据创建日期）								| 无		|
| [date-update]	| 声明一个日期字段（数据更新日期）								| 无		|
| [fk-1] 			| 声明一个外键(主表数据<1000时使用，比如商品分类表)，格式为 [fk-1 pk=主表.主键.连表字段.连表字段注释]，例如：`[fk-1 pk=sys_type.id.name.所属分类]` | [fk-1](#-fk-1-简单外键) |
| [fk-2] 			| 声明一个外键(主表数据>1000时使用，比如用户表)，格式为 [fk-2 pk=主表.主键]，例如：`[fk-2 pk=sys_user.id]` | [fk-2](#-fk-2-复杂外键) |
| --notp			| 此字段取消解析								| 无		|


### 详解

##### - fk-1 简单外键
- `fk-1` 为简单外键(主表数据<1000时使用，比如商品分类表)
- 应用场景举例：商品表只存储了商品分类id，在展示时却需要**展示出商品类别名称**，这时候需要声明`fk-1`外键
- 格式为：`[fk-1 pk=主表.主键.连表字段.连表字段注释]`，例如：`[fk-1 pk=sys_type.id.name.所属分类]`

##### - fk-2 复杂外键
- `fk-2` 为复杂外键(主表数据>1000时使用，比如用户表)
- 应用场景举例：订单表存储了user_id，查询订单列表的时候，需要把用户昵称username、头像avatar等信息带出来，这时候需要声明`fk-2`外键
- 此时`fk-2`有三种写法，分别造成三种生成结果

``` mysql 
	-- 写法1，最简单写法，不进行多表查询，此时在后台管理展示的只有user_id （带有超链接，鼠标点击后打开用户详情）
	`user_id` bigint(20) COMMENT '用户id [fk-2 pk=sys_user.id]',
	
	-- 写法2，带出username值，此时在后台管理展示的有user_id、username		(语法与fk-1类似)
	`user_id` bigint(20) COMMENT '用户id [fk-2 pk=sys_user.id.username.用户昵称]',
	
	-- 写法3，带出username、avatar值，此时在后台管理展示的有user_id、username、avatar		(可以按照这个语法一直扩展，带出n个值)
	`user_id` bigint(20) COMMENT '用户id [fk-2 pk=sys_user.id.username.用户昵称.avatar.用户头像]',
	
```

##### - 扩展

- `fk-1` 和 `fk-2` 还都能声明一些额外参数，来控制一些功能
- `link`：是否增加超链接，点击之后打开主表数据详情，`fk-1`默认为`false`，`fk-2`默认为`true`
- `sh1owfk`：是否显示原始的外键字段，`fk-1`默认为`false`，`fk-2`默认为`true`
- `fname`：指定此外键对应的实体类字段名称，这在解决多个外键指向同一个表重名的场景时非常有用，一般情况下你不用指定此字段，因为`sa-plus`会自动为你的字段计算合适的名称
- 参考以下示例

```
	-- 此写法代表生成的后台管理页面，在展示用户id的时候，不再有"点击打开用户详情功能" 
	`user_id` bigint(20) COMMENT '用户id [fk-2 pk=sys_user.id, link=false]',

	-- 此写法代表生成的后台管理页面，在展示用户id的时候，不再展示外键字段，即：只展示username，不展示user_id 
	`user_id` bigint(20) COMMENT '用户id [fk-2 pk=sys_user.id.username.用户昵称, showfk=false]',
```
		
		

### 注意点 

1. 在项目代码生成之后，可以直接重启运行
2. 生成的代码都是**比较粗糙**的，仅能维持基本功能点的运行，不要指望一次性直接生成一个完整的项目，那是任何代码生成器都做不到的事情
3. 如果要作为正式项目开发，你是需要在生成代码的基础上再次修修补补的 
4. 另外一定要注意一点：**尽量不要将代码生成地址配置成你正在开发的项目地址，否则可能会在不小心的情况下，覆盖掉你二次修改后的代码**。
5. 如果要使用`git`功能，则需要把`.gitignore`文件的最后四行删除掉，因为这个文件把代码生成器生成的代码都给屏蔽掉了









