
-- ======================================== 测试库（仅做测试使用） ====================================  


-- 公告表 (演示点: 单表生成)
drop table if exists sys_notice;
CREATE TABLE `sys_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id [no]',
  `title` varchar(50) DEFAULT NULL COMMENT '公告标题 [text]',
  `content` longtext COMMENT '公告内容 [textarea]',
  `img` varchar(2048) DEFAULT '' COMMENT '单个图片 [img]',
  `audio` varchar(2048) DEFAULT '' COMMENT '单个音频 [audio]',
  `video` varchar(2048) DEFAULT '' COMMENT '单个视频 [video]',
  `img_list` varchar(2048) DEFAULT '' COMMENT '图片集合 [img-list]',
  `audio_list` varchar(2048) DEFAULT '' COMMENT '音频列表 [audio-list]',
  `video_list` varchar(2048) DEFAULT '' COMMENT '视频列表 [video-list]',
  `img_video_list` varchar(2048) DEFAULT '' COMMENT '图视结合 [img-video-list]',
  `is_show` int(11) DEFAULT '1' COMMENT '是否显示 (1=是, 2=否)[j]',
  `is_lock` int(11) DEFAULT '2' COMMENT '是否锁定 (1=是, 2=否)[j]',
  `see_count` int(11) DEFAULT '0' COMMENT '点击数量 [no]',
  `sort` bigint(20) DEFAULT '0' COMMENT '排序索引 [num]',
  `create_time` datetime COMMENT '创建日期 [date-create]',
  `update_time` datetime COMMENT '修改日期 [date-update]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=gbk ROW_FORMAT=COMPACT 
COMMENT='公告表 [table icon=el-icon-bell]';

-- select * from sys_notice;
INSERT INTO `sys_notice`(`id`, `title`, `content`, `img`, `audio`, `video`, `img_list`, `audio_list`, `video_list`, `img_video_list`, `is_show`, `is_lock`, `see_count`, `sort`, `create_time`, `update_time`) 
VALUES (1001, '测试公告1', '测试公告1', 'http://demo.dev33.cn/sp-server/upload/image/2020/09-11/1599805642483567503625.jpg', 'http://demo.dev33.cn/sp-server/upload/audio/2020/09-11/1599805439648277652144.mp3', 'http://demo.dev33.cn/sp-server/upload/video/2020/09-11/1599805450160616641835.mp4', '', 'http://demo.dev33.cn/sp-server/upload/audio/2020/09-11/15998054742041893440109.mp3', 'http://demo.dev33.cn/sp-server/upload/video/2020/09-11/1599805482152468147415.mp4', '', 1, 1, 0, 1, '2020-09-11 14:24:59', '2020-09-11 14:24:59');
INSERT INTO `sys_notice`(`id`, `title`, `content`, `img`, `audio`, `video`, `img_list`, `audio_list`, `video_list`, `img_video_list`, `is_show`, `is_lock`, `see_count`, `sort`, `create_time`, `update_time`) 
VALUES (1002, '测试公告2', '测试公告2', 'http://demo.dev33.cn/sp-server/upload/image/2020/09-11/1599805614320552993661.jpg', 'http://demo.dev33.cn/sp-server/upload/audio/2020/09-11/1599805439648277652144.mp3', 'http://demo.dev33.cn/sp-server/upload/video/2020/09-11/1599805450160616641835.mp4', '', 'http://demo.dev33.cn/sp-server/upload/audio/2020/09-11/15998054742041893440109.mp3', 'http://demo.dev33.cn/sp-server/upload/video/2020/09-11/1599805482152468147415.mp4', '', 1, 1, 0, 1, '2020-09-11 14:24:59', '2020-09-11 14:24:59');
INSERT INTO `sys_notice`(`id`, `title`, `content`, `img`, `audio`, `video`, `img_list`, `audio_list`, `video_list`, `img_video_list`, `is_show`, `is_lock`, `see_count`, `sort`, `create_time`, `update_time`) 
VALUES (1003, '测试公告3', '测试公告3', 'http://demo.dev33.cn/sp-server/upload/image/2020/09-11/15998054293331190096174.jpg', 'http://demo.dev33.cn/sp-server/upload/audio/2020/09-11/1599805439648277652144.mp3', 'http://demo.dev33.cn/sp-server/upload/video/2020/09-11/1599805450160616641835.mp4', '', 'http://demo.dev33.cn/sp-server/upload/audio/2020/09-11/15998054742041893440109.mp3', 'http://demo.dev33.cn/sp-server/upload/video/2020/09-11/1599805482152468147415.mp4', '', 1, 1, 0, 1, '2020-09-11 14:24:59', '2020-09-11 14:24:59');


-- 分类表 (演示点: 聚合外键)
drop table if exists sys_type;
CREATE TABLE `sys_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id [no]',
  `name` varchar(50) NOT NULL COMMENT '分类名字 [text]',
  `icon` varchar(512) NOT NULL COMMENT '分类图标 [img]',
  `sort` int(11) DEFAULT '1' COMMENT '排序索引 [num]',
  `create_time` datetime COMMENT '创建日期 [date-create]',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `NAME` (`NAME`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=gbk ROW_FORMAT=COMPACT 
COMMENT='分类表 [table icon=el-icon-eleme] [fk-count js=ser_goods.type_id.商品数量]';

INSERT INTO `sys_type`(`id`, `name`, `icon`, `sort`, `create_time`) 
VALUES (101, '电脑办公', 'http://demo.dev33.cn/sp-server/upload/image/2020/09-11/1599804288378744599822.jpg', 1, now()); 
INSERT INTO `sys_type`(`id`, `name`, `icon`, `sort`, `create_time`) 
VALUES (102, '服饰箱包', 'http://demo.dev33.cn/sp-server/upload/image/2020/09-11/15998042340421259468332.png', 4, now()); 
INSERT INTO `sys_type`(`id`, `name`, `icon`, `sort`, `create_time`) 
VALUES (103, '休闲零食', 'http://demo.dev33.cn/sp-server/upload/image/2020/09-11/15998043314131110408700.jpg', 2, now()); 
INSERT INTO `sys_type`(`id`, `name`, `icon`, `sort`, `create_time`) 
VALUES (104, '虚拟物品', 'http://demo.dev33.cn/sp-server/upload/image/2020/09-11/1599804375931771963230.jpg', 3, now()); 



-- 商品表 (演示点: 连接外键)
drop table if exists ser_goods;
CREATE TABLE `ser_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id [no]', 
  `name` varchar(200) DEFAULT NULL COMMENT '商品名称 [text j=like]', 
  `avatar` varchar(512) DEFAULT NULL COMMENT '商品头像 [img]', 
  `image_list` varchar(2048) COMMENT '轮播图片 [img-list]', 
  `content` text COMMENT '图文介绍 [f]', 
  `money` int(11) DEFAULT '0' COMMENT '商品价格 [num]', 
	`type_id` bigint(20) COMMENT '所属分类 [num]', 
  `stock_count` int(11) DEFAULT 0 COMMENT '剩余库存 [num]',
  `status` int(11) DEFAULT '1' COMMENT '商品状态 (1=上架,2=下架) [j]',
  `create_time` datetime COMMENT '创建日期 [date-create]',
  `update_time` datetime COMMENT '更新日期 [date-update]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT 
COMMENT='商品表
[table icon=el-icon-apple]
[fk-s js=(type_id=sys_type.id), show=name.所属分类, drop]
';

-- select * from ser_goods
INSERT INTO `ser_goods`(`id`, `name`, `avatar`, `image_list`, `content`, `money`, `type_id`, `stock_count`, `status`, `create_time`, `update_time`) VALUES (1001, '红富士苹果', 'http://demo.dev33.cn/sp-server/upload/image/2020/09-11/15997970168761290331860.jpg', 'http://demo.dev33.cn/sp-server/upload/image/2020/09-11/1599797023755588872264.jpg,http://demo.dev33.cn/sp-server/upload/image/2020/09-11/159979702375259508173.jpg', '<p>红富士苹果、又大又甜</p>', 99, 101, 123, 1, now(), now());
INSERT INTO `ser_goods`(`id`, `name`, `avatar`, `image_list`, `content`, `money`, `type_id`, `stock_count`, `status`, `create_time`, `update_time`) VALUES (1002, '大鸭梨', 'http://demo.dev33.cn/sp-server/upload/image/2020/09-11/15997975812031637411542.jpg', 'http://demo.dev33.cn/sp-server/upload/image/2020/09-11/15997975705891356990788.jpg,http://demo.dev33.cn/sp-server/upload/image/2020/09-11/15997975733991694005505.jpg', '<p>大鸭梨呀 大鸭梨呀&nbsp;</p>', 199, 103, 1000, 1, now(), now());
INSERT INTO `ser_goods`(`id`, `name`, `avatar`, `image_list`, `content`, `money`, `type_id`, `stock_count`, `status`, `create_time`, `update_time`) VALUES (1003, '小橘子', 'http://demo.dev33.cn/sp-server/upload/image/2020/09-11/15997976253641694707239.jpg', 'http://demo.dev33.cn/sp-server/upload/image/2020/09-11/1599797629820639846984.jpg,http://demo.dev33.cn/sp-server/upload/image/2020/09-11/1599797629820432131808.jpeg,http://demo.dev33.cn/sp-server/upload/image/2020/09-11/15997976298192074277937.jpg', '<p>小橘子呀小橘子呀</p>', 299, 103, 1232, 1, now(), now());
INSERT INTO `ser_goods`(`id`, `name`, `avatar`, `image_list`, `content`, `money`, `type_id`, `stock_count`, `status`, `create_time`, `update_time`) VALUES (1004, '贴水面膜', 'http://demo.dev33.cn/sp-server/upload/image/2020/09-11/1599797690037918361522.jpg', 'http://demo.dev33.cn/sp-server/upload/image/2020/09-11/15997976972201194628355.jpg,http://demo.dev33.cn/sp-server/upload/image/2020/09-11/1599797697219564502792.jpg', '<p>贴水面膜贴水面膜</p>', 66, 102, 12, 1, now(), now());





-- 文章表 (演示点: 综合演示)
drop table if exists ser_article;
CREATE TABLE `ser_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id [no]',
  `title` varchar(150) DEFAULT NULL COMMENT '文章标题 [t j=like]',
  `content` longtext COMMENT '文章内容 [f]',
  `type_id` bigint(20) COMMENT '所属分类 [num]', 
  `goods_id` bigint(20) COMMENT '推荐商品 [num click=ser_goods.id]', 
  `eff_time` datetime COMMENT '有效日期 [date]', 
  `create_time` datetime COMMENT '创建日期 [date-create]', 
  `status` int(11) DEFAULT '2' COMMENT '所属状态(1=正常,2=禁用) [j]', 
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT 
COMMENT='文章表 
[table icon=el-icon-document-remove]
[fk-s js=(type_id=sys_type.id), show=name.所属分类, drop]
[fk-s js=(goods_id=ser_goods.id), show=name.商品名称, click]';

-- select * from ser_article;
INSERT INTO `ser_article`(`id`, `title`, `content`, `type_id`, `goods_id`, `eff_time`, `create_time`, `status`) 
VALUES (2, '开业大酬宾', '<p>开业大酬宾，开业了</p>', 104, 1001, now(), now(), 1);
INSERT INTO `ser_article`(`id`, `title`, `content`, `type_id`, `goods_id`, `eff_time`, `create_time`, `status`) 
VALUES (3, '回馈老客户啦，速来', '<p>回馈老客户啦，速来</p>', 103, 1002, now(), now(), 1);
INSERT INTO `ser_article`(`id`, `title`, `content`, `type_id`, `goods_id`, `eff_time`, `create_time`, `status`) 
VALUES (4, '两块钱，你买不了吃亏', '<p>两块钱，你买不了吃亏，两块钱，你买不了上当</p>', 104, 1003, now(), now(), 1);

























