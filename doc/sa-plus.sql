

-- ======================================== sa-plus 系统库 ====================================  


-- 系统角色表 
drop table if exists sp_role; 
CREATE TABLE `sp_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id，--主键、自增',
  `name` varchar(20) NOT NULL COMMENT '角色名称, 唯一约束',
  `info` varchar(200) DEFAULT NULL COMMENT '角色详细描述',
  `is_lock` int(11) NOT NULL DEFAULT '1' COMMENT '是否锁定(1=是,2=否), 锁定之后不可随意删除, 防止用户误操作',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统角色表';

INSERT INTO `sp_role`(`id`, `name`, `info`, `is_lock`) VALUES (1, '超级管理员', '最高权限', 1);
INSERT INTO `sp_role`(`id`, `name`, `info`, `is_lock`) VALUES (2, '二级管理员', '二级管理员', 2);
INSERT INTO `sp_role`(`id`, `name`, `info`, `is_lock`) VALUES (11, '普通账号', '普通账号', 1);
INSERT INTO `sp_role`(`id`, `name`, `info`, `is_lock`) VALUES (12, '测试角色', '测试角色', 2);


-- 角色权限对应表  
drop table if exists sp_role_permission; 
CREATE TABLE `sp_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id号',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID ',
  `permission_code` varchar(50) DEFAULT NULL COMMENT '菜单项ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色权限中间表';

insert into sp_role_permission() values (0, '1', 'bas', now());
insert into sp_role_permission() values (0, '1', '1', now());
insert into sp_role_permission() values (0, '1', '11', now());
insert into sp_role_permission() values (0, '1', '99', now());

insert into sp_role_permission() values (0, '1', 'console', now());
insert into sp_role_permission() values (0, '1', 'sql-console', now());
insert into sp_role_permission() values (0, '1', 'redis-console', now());
insert into sp_role_permission() values (0, '1', 'apilog-list', now());
insert into sp_role_permission() values (0, '1', 'form-generator', now());

insert into sp_role_permission() values (0, '1', 'auth', now());
insert into sp_role_permission() values (0, '1', 'role-list', now());
insert into sp_role_permission() values (0, '1', 'menu-list', now());
insert into sp_role_permission() values (0, '1', 'admin-list', now());
insert into sp_role_permission() values (0, '1', 'admin-add', now());

insert into sp_role_permission() values (0, '1', 'sp-cfg', now());
insert into sp_role_permission() values (0, '1', 'sp-cfg-app', now());
insert into sp_role_permission() values (0, '1', 'sp-cfg-server', now());


-- 系统管理员表 
drop table if exists sp_admin; 
CREATE TABLE `sp_admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id，--主键、自增',
  `name` varchar(100) NOT NULL COMMENT 'admin名称',
  `avatar` varchar(500) DEFAULT NULL COMMENT '头像地址',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `pw` varchar(50) DEFAULT NULL COMMENT '明文密码',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `role_id` int(11) DEFAULT '11' COMMENT '所属角色id',
  `status` int(11) DEFAULT '1' COMMENT '账号状态(1=正常, 2=禁用)',
  `create_by_aid` bigint(20) DEFAULT '-1' COMMENT '创建自哪个管理员',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `login_time` datetime DEFAULT NULL COMMENT '上次登陆时间',
  `login_ip` varchar(50) DEFAULT NULL COMMENT '上次登陆IP',
  `login_count` int(11) DEFAULT '0' COMMENT '登陆次数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统管理员表';

INSERT INTO `sp_admin`(`id`, `name`, `avatar`, `password`, `pw`, `role_id`, create_time) 
VALUES (10001, 'sa', 'http://sa-admin.dev33.cn/sa-frame/admin-logo.png', 'E4EF2A290589A23EFE1565BB698437F5', '123456', 1, now()); 
INSERT INTO `sp_admin`(`id`, `name`, `avatar`, `password`, `pw`, `role_id`, create_time) 
VALUES (10002, 'admin', 'http://sa-admin.dev33.cn/sa-frame/admin-logo.png', '1DE197572C0B23B82BB2F54202E8E00B', 'admin', 1, now()); 



-- 配置信息表   
drop table if exists sp_cfg;
CREATE TABLE `sp_cfg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id号',
  `cfg_name` varchar(50) NOT NULL COMMENT '配置名',
  `cfg_value` text COMMENT '配置值',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `cfg_name` (`cfg_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='配置信息表';

INSERT INTO `sp_cfg`(`id`, `cfg_name`, `cfg_value`, `remarks`) VALUES (1, 'app_cfg', '{}', '应用配置信息，对外公开');
INSERT INTO `sp_cfg`(`id`, `cfg_name`, `cfg_value`, `remarks`) VALUES (2, 'server_cfg', '{}', '服务器私有配置');




-- 系统api请求记录表 
-- 如果此段脚本执行报错，请将 datetime(3) 改为 datetime 再次执行
drop table if exists sp_apilog; 
CREATE TABLE `sp_apilog` (
  `id` bigint(50) NOT NULL AUTO_INCREMENT COMMENT '请求id',
  `req_ip` varchar(100) DEFAULT NULL COMMENT '客户端ip',
  `req_api` varchar(512) DEFAULT NULL COMMENT '请求api',
  `req_parame` text COMMENT '请求参数',
  `req_type` varchar(50) DEFAULT NULL COMMENT '请求类型（GET、POST...）',
  `req_token` varchar(50) DEFAULT NULL COMMENT '请求token',
  `req_header` text DEFAULT NULL COMMENT '请求header',
  `res_code` varchar(50) DEFAULT NULL COMMENT '返回-状态码',
  `res_msg` text COMMENT '返回-信息描述',
  `res_string` text COMMENT '返回-整个信息字符串形式',
  `user_id` bigint(20) DEFAULT NULL COMMENT 'user_id',
  `admin_id` bigint(20) DEFAULT NULL COMMENT 'admin_id',
  `start_time` datetime(3) DEFAULT NULL COMMENT '请求开始时间',
  `end_time` datetime(3) DEFAULT NULL COMMENT '请求结束时间',
  `cost_time` bigint(20) DEFAULT NULL COMMENT '花费时间，单位ms',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='api请求记录表';


















