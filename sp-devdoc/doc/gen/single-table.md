# 单表生成 


### 表注释类型声明
1. **代码生成器的类型声明，可以让代码生成器更加智能**
2. 如果在建表时不写类型声明，则只能生成普通输入框
3. 如果要生成特殊的表单，如：数值输入、多行文本域、图片上传、多图上传、富文本等等，则需要写上类型声明
4. 语法为，写一个中括号，跟上关键字：`字段注释 [类型声明]`
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


##### 最终生成效果如图所示

![列表](https://color-test.oss-cn-qingdao.aliyuncs.com/sa-plus/g-list.png)
![增加/修改](https://color-test.oss-cn-qingdao.aliyuncs.com/sa-plus/g-update.png)		
		


### 所有特殊声明 

| 声明				 | 说明															| 详见		|
| :--------			| :--------														| :--------	|
| [text]			| 声明一个普通输入框，这是默认值，还可以写成 `[t]`、`[input]`   （`[text j=like]代表模糊查询`）			| 无		|
| [textarea]		| 声明一个多行文本域											| 无		|
| [richtext]		| 声明一个富文本字段，会生成富文本插件，还可以写成 `[f]`, 详情见下			| 无		|
| [num]			| 声明数值输入字段												| 无		|
| [img]			| 声明一个图片字段，会生成图片上传								| 无		|
| [audio]			| 声明一个音频字段，会生成音频上传								| 无		|
| [video]			| 声明一个视频字段，会生成视频上传								| 无		|
| [file]			| 声明一个文件字段，会生成文件上传(需在`UploadConfig.java`中配置文件后缀白名单)			| 无		|
| [img-list]		| 声明一个图片集合字段，会生成多图片上传，还可以写成 `[imgList]`、`[img_list]` 	| 无		|
| [audio-list]		| 声明一个音频集合字段，会生成多音频上传，还可以写成 `[audioList]`、`[audio_list]`	| 无		|
| [video-list]		| 声明一个视频集合字段，会生成多视频上传，还可以写成 `[videoList]`、`[video_list]`	| 无		|
| [file-list]		| 声明一个文件集合字段，会生成多文件上传，还可以写成 `[fileList]`、`[file_list]`	| 无		|
| [img-video-list]		| 声明一个图片与视频混合的集合字段，还可以写成 `[imgVideoList]`、`[img_video_list]`	| 无		|
| [link]		| 声明一个超链接字段								| 无		|
| [date]			| 声明一个日期字段												| 无		|
| [date-create]	| 声明一个日期字段（数据创建日期）								| 无		|
| [date-update]	| 声明一个日期字段（数据更新日期）								| 无		|
| [time]		| 声明一个时间字段（时:分:秒）								| 无		|
| [enum]			| 声明一个枚举字段，具体语法请查看下方示例，还可以写成 `[j]`		| [enum](#-enmu-枚举字段)		|
| [logic-delete]		| 声明此表的逻辑删除字段，还可以简写成`[lc-del]`					| [logic-delete](#-logic-delete-逻辑删除)		|
| ~~[fk-1]~~ 			| ~~声明一个外键~~ (已移除此特性，请查看表注释`fk-s`连接外键) 		| 无 |
| ~~[fk-2]~~ 			| ~~声明一个外键~~ (已移除此特性，请查看表注释`fk-s`连接外键)**(此特性由追加参数[click](/gen/comment-args?id=-click-单击打开详情)代替)** 		| 无 |
| ~~[no]~~			| ~~代表生成表单时忽略此字段~~	(已移除此特性，使用`[xxx not-add]`达到同样目的) 									| 无		|
| --notp			| 此字段取消解析								| 无		|


### 类型详解


##### - enmu 枚举字段
- enum指定一个字段为枚举类型，且其取值必须以 `(1=xxx, 2=xxx, 3=xxx)` 的形式声明，例如: 
``` js
	`status` int(11) COMMENT '商品状态 (1=上架, 2=下架) [enum]',
```

- 如果你的枚举取值为`String`类型，你需要指定dt属性：`[enum dt=string]`, 如下示例：
``` js
	`status` varchar(20) COMMENT '商品状态 (on=上架, off=下架) [enum dt=string]',
```

- 其还有两个附加属性，决定表单样式
	- `s-type`：标注列表查询页生成的样式, 取值：1=普通单选, 2=单选文字, 3=单选按钮, 4=下拉选择，`默认值=2`
	- `a-type`：标注添加修改页生成的样式, 取值同上，`默认值=3`
	
- 例如：
``` js
	`sex` int(11) COMMENT '用户性别 (1=男, 2=女, 3=未知)[enum s-type=4, a-type=1]';
```
表示：在列表页以下拉框显示,添加页以普通单选显示

- 如果这个枚举字段只有两个值，你可以将其声明为开关字段 (会在列表中生成开关字段来修改值)
``` js
	`status` int(11) COMMENT '商品状态 (1=上架, 2=下架) [enum switch=true]',
```


##### - logic-delete 逻辑删除
- 当一个表中含有`[logic-delete]`字段时，本表自动打开逻辑删除模式，其取值默认为 `(1=存在, 0=已被删除)`
- 如需自定义数据取值，可用如下方式:
``` js
	`is_eff` int(11) COMMENT '是否有效 [logic-delete yes=1, no=0]',
```




### 注意事项

1. 在项目代码生成之后，可以直接重启运行
2. 生成的代码都是**比较粗糙**的，仅能维持基本功能点的运行，不要指望一次性直接生成一个完整的项目，那是任何代码生成器都做不到的事情
3. 如果要作为正式项目开发，你是需要在生成代码的基础上再次修修补补的 
4. 另外一定要注意一点：**尽量不要将代码生成地址配置成你正在开发的项目地址，否则可能会在不小心的情况下，覆盖掉你二次修改后的代码**。
5. 如果要使用`git`功能，则需要把`.gitignore`文件的最后四行删除掉，因为这个文件把代码生成器生成的代码都给屏蔽掉了









