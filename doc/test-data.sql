
-- ======================================== 测试库（仅做测试使用） ====================================  



-- 商品分类表
drop table if exists sys_type;
CREATE TABLE `sys_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id [no]',
  `name` varchar(50) NOT NULL COMMENT '分类名字 [text]',
  `icon` varchar(512) NOT NULL COMMENT '分类图标 [img]',
  `sort` int(11) DEFAULT '1' COMMENT '排序值 [num]',
  `create_time` datetime COMMENT '创建日期 [date-create]',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `NAME` (`NAME`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=gbk ROW_FORMAT=COMPACT COMMENT='商品分类表';

INSERT INTO `sys_type`(`id`, `name`, `icon`, `sort`, `create_time`) VALUES (101, '电脑办公', '', 1, now()); 
INSERT INTO `sys_type`(`id`, `name`, `icon`, `sort`, `create_time`) VALUES (102, '服饰箱包', '', 4, now()); 
INSERT INTO `sys_type`(`id`, `name`, `icon`, `sort`, `create_time`) VALUES (103, '休闲零食', '', 2, now()); 
INSERT INTO `sys_type`(`id`, `name`, `icon`, `sort`, `create_time`) VALUES (104, '虚拟物品', '', 3, now()); 


-- 商品表 
drop table if exists ser_goods;
CREATE TABLE `ser_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品id [no]', 
  `name` varchar(200) DEFAULT NULL COMMENT '商品名称 [text j=like]', 
  `avatar` varchar(512) DEFAULT NULL COMMENT '商品头像 [img]', 
  `image_list` varchar(2048) COMMENT '轮播图片 [img_list]', 
  `content` text COMMENT '图文介绍 [f]', 
  `remark` varchar(512) DEFAULT NULL COMMENT '商品备注 [textarea]',
  `money` int(11) DEFAULT '0' COMMENT '商品价格 [num]', 
  `stock_count` int(11) DEFAULT 0 COMMENT '剩余库存 [num]',
  `audio` varchar(512) DEFAULT NULL COMMENT '音频介绍 [audio]', 
  `video` varchar(512) DEFAULT NULL COMMENT '视频介绍 [video]', 
  `status` int(11) DEFAULT '1' COMMENT '商品状态 (1=上架,2=下架) [j]',
  `create_time` datetime COMMENT '创建日期 [date-create]',
  `update_time` datetime COMMENT '更新日期 [date-update]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='商品表';


INSERT INTO `ser_goods`(`id`, `name`, `avatar`, `image_list`, `remark`, `content`, `money`, `stock_count`, `audio`, `video`, `status`, `create_time`, `update_time`) 
VALUES (1001, '小苹果', 'http://127.0.0.1:8099/upload/image/2020/05-11/1589130441278158564136.jpg', 'http://127.0.0.1:8099/upload/image/2020/05-11/15891304215541588315943.png', '这是一个小呀小苹果', '这是一个小呀小苹果<p><br></p>', 23, 213, '', '', 1, now(), now());
INSERT INTO `ser_goods`(`id`, `name`, `avatar`, `image_list`, `remark`, `content`, `money`, `stock_count`, `audio`, `video`, `status`, `create_time`, `update_time`) 
VALUES (1002, '大鸭梨', 'http://127.0.0.1:8099/upload/image/2020/05-11/15891304588142094778376.png', 'http://127.0.0.1:8099/upload/image/2020/05-11/15891301925381859798545.jpg', '大鸭梨', '<p>大鸭梨图文介绍</p>', 214, 234, '', '', 1, now(), now());
INSERT INTO `ser_goods`(`id`, `name`, `avatar`, `image_list`, `remark`, `content`, `money`, `stock_count`, `audio`, `video`, `status`, `create_time`, `update_time`) 
VALUES (1003, '小橘子', 'http://127.0.0.1:8099/upload/image/2020/05-11/15891326019482012079187.jpg', 'http://127.0.0.1:8099/upload/image/2020/05-11/1589133225670119768604.jpg', '小橘子', '<p>小橘子</p>', 123, 123, '', '', 2, now(), now());




-- 兑换码表  
drop table if exists sys_redeem;
CREATE TABLE `sys_redeem` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id [no]',
  `name` varchar(200) COMMENT '红包名称 [text]',
  `money` bigint(20) DEFAULT NULL COMMENT '红包金额 [num]',
  `type_id` bigint(20) COMMENT '所属分类 [fk-1 pk=sys_type.id.name.所属分类]',
  `goods_id` bigint(20) COMMENT '所属商品id [fk-2 pk=ser_goods.id]',
  `status` int(11) DEFAULT '2' COMMENT '所属状态(1=正常,2=禁用) [j]',
  `end_time` datetime COMMENT '截止日期 [date]',
  `create_time` datetime COMMENT '创建日期 [date-create]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=182192 DEFAULT CHARSET=gbk ROW_FORMAT=COMPACT COMMENT='兑换码表';

INSERT INTO `sys_redeem`(`id`, `name`, `money`, `type_id`, `goods_id`, `status`, `end_time`, `create_time`) VALUES (182192, '通用水果红包', 12, 101, 1002, 1, now(), now());
INSERT INTO `sys_redeem`(`id`, `name`, `money`, `type_id`, `goods_id`, `status`, `end_time`, `create_time`) VALUES (182193, '小苹果红包', 20, 103, 1001, 1, now(), now());
INSERT INTO `sys_redeem`(`id`, `name`, `money`, `type_id`, `goods_id`, `status`, `end_time`, `create_time`) VALUES (182194, '大鸭梨红包', 20, 103, 1002, 1, now(), now());








-- 公告表
drop table if exists sys_notice;
CREATE TABLE `sys_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id号 [no]',
  `title` varchar(50) DEFAULT NULL COMMENT '公告标题 [text]',
  `content` longtext COMMENT '公告内容 [textarea]',
  `see_count` int(11) DEFAULT '0' COMMENT '点击数量 [num]',
  `audio_list` varchar(2048) DEFAULT '' COMMENT '音频列表 [audio_list]',
  `video_list` varchar(2048) DEFAULT '' COMMENT '视频列表 [video_list]',
  `img_video_list` varchar(2048) DEFAULT '' COMMENT '图视结合 [img_video_list]',
  `is_show` int(11) DEFAULT '1' COMMENT '是否显示 (1=是, 2=否)[j]',
  `is_lock` int(11) DEFAULT '2' COMMENT '是否锁定 (1=是, 2=否)[j]',
  `sort` bigint(20) DEFAULT '0' COMMENT '排序索引 [num]',
  `create_time` datetime COMMENT '创建日期 [date-create]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=gbk ROW_FORMAT=COMPACT COMMENT='公告表';

INSERT INTO `sys_notice`(`id`, `title`, `content`, `see_count`, `is_show`, `is_lock`, `sort`, `create_time`) VALUES (1001, '系统通知', '系统通知', 1, 1, 1, 1, '2020-04-20 11:11:37');
INSERT INTO `sys_notice`(`id`, `title`, `content`, `see_count`, `is_show`, `is_lock`, `sort`, `create_time`) VALUES (1002, '系统通知2', '系统通知2', 1, 1, 1, 1, '2020-04-20 11:11:47');
INSERT INTO `sys_notice`(`id`, `title`, `content`, `see_count`, `is_show`, `is_lock`, `sort`, `create_time`) VALUES (1003, '系统通知3', '系统通知3', 1, 1, 1, 1, '2020-04-20 11:11:57');













