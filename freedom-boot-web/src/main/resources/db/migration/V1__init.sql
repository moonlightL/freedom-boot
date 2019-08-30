CREATE TABLE IF NOT EXISTS `t_sys_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `icon` varchar(50) NOT NULL DEFAULT '' COMMENT '权限图标',
  `url` varchar(50) NOT NULL DEFAULT '' COMMENT '权限访问地址',
  `resource_type` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '资源类型 1: 模块 2：菜单 3: 按钮',
	`business_type` TINYINT(4) NOT NULL DEFAULT '3' COMMENT '业务类型 1：核心 2：扩展 3：业务',
  `code` varchar(50) NOT NULL DEFAULT '' COMMENT '权限编码',
  `pid` bigint(20) NOT NULL DEFAULT '0' COMMENT '父级 id',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1:可用 0：禁用',
  `sort` tinyint(4) NOT NULL DEFAULT '1' COMMENT '排序',
  `common` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '是否通用 1：是 0：否， 针对 type=3',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_pid` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COMMENT='系统权限表';

INSERT INTO `t_sys_permission` (`id`, `name`, `icon`, `url`, `resource_type`, `business_type`, `code`, `pid`, `state`, `sort`, `common`, `create_time`, `update_time`) VALUES
	(1, '系统管理', 'fa fa-cogs', '', 1, 1, '', 0, 1, 1, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(2, '用户管理', 'fa fa-users', '/core/user/listUI.html', 2, 1, 'core:user:listUI', 1, 1, 1, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(3, '新增', 'fa fa-plus-circle', '', 3, 1, 'core:user:save', 2, 1, 1, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(4, '编辑', 'fa fa-edit', '', 3, 1, 'core:user:update', 2, 1, 2, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(5, '删除', 'fa fa-trash', '', 3, 1, 'core:user:remove', 2, 1, 3, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(6, '查询', 'fa fa-search', '', 3, 1, 'core:user:query', 2, 1, 4, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(7, '分配角色', 'fa fa-role', '', 3, 1, 'core:user:assign:role', 2, 1, 5, 0, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(8, '角色管理', 'fa fa-user-secret', '/core/role/listUI.html', 2, 1, 'core:role:listUI', 1, 1, 2, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(9, '新增', 'fa fa-plus-circle', '', 3, 1, 'core:role:save', 8, 1, 1, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(10, '编辑', 'fa fa-edit', '', 3, 1, 'core:role:update', 8, 1, 2, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(11, '删除', 'fa fa-trash', '', 3, 1, 'core:role:remove', 8, 1, 3, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(12, '查询', 'fa fa-search', '', 3, 1, 'core:role:query', 8, 1, 4, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(13, '分配权限', 'fa fa-permission', '', 3, 1, 'core:role:assign:permission', 8, 1, 5, 0, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(14, '权限管理', 'fa fa-crosshairs', '/core/permission/listUI.html', 2, 1, 'core:permission:listUI', 1, 1, 3, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(15, '新增', 'fa fa-plus-circle', '', 3, 1, 'core:permission:save', 14, 1, 1, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(16, '编辑', 'fa fa-edit', '', 3, 1, 'core:permission:update', 14, 1, 2, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(17, '删除', 'fa fa-trash', '', 3, 1, 'core:permission:remove', 14, 1, 3, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(18, '日志管理', 'fa fa-monitor', '/core/log/listUI.html', 2, 1, 'core:log:listUI', 1, 1, 4, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(19, '查询', 'fa fa-search', '', 3, 1, 'core:log:query', 18, 1, 1, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(20, '代码管理', 'fa fa-code', '', 1, 2, '', 0, 1, 2, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(21, '数据表管理', 'fa fa-user', '/generator/table/listUI.html', 2, 2, 'generator:table:listUI', 20, 1, 1, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(22, '系统监控', 'fa fa-eye', '', 1, 2, '', 0, 1, 3, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(23, '系统信息', 'fa fa-server', '/monitor/server/listUI.html', 2, 2, 'monitor:server:listUI', 22, 1, 1, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(24, 'SQL监控', 'fa fa-sql', '/druid/index.html', 2, 2, 'monitor:druid:index', 22, 1, 1, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(25, '文件管理', 'fa fa-file', '', 1, 2, '', 0, 1, 4, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(26, '文件上传', 'fa fa-upload', '/file/data/listUI.html', 2, 2, 'file:data:listUI', 25, 1, 1, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(27, '上传', 'fa fa-cloud-upload', '', 3, 2, 'file:data:save', 26, 1, 1, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(28, '删除', 'fa fa-trash', '', 3, 2, 'file:data:remove', 26, 1, 2, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(29, '查询', 'fa fa-search', '', 3, 2, 'file:data:query', 26, 1, 3, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(30, '下载', 'fa fa-cloud-download', '', 3, 2, 'file:data:download', 26, 1, 4, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(31, '文件配置', 'fa fa-config', '/file/config/listUI.html', 2, 2, 'file:config:listUI', 25, 1, 1, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46'),
	(32, '保存', 'fa fa-save', '', 3, 2, 'file:config:save', 31, 1, 1, 1, '2019-08-26 10:16:46', '2019-08-26 10:16:46');

CREATE TABLE IF NOT EXISTS `t_sys_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '角色名称',
  `code` varchar(50) NOT NULL DEFAULT '' COMMENT '角色编码',
  `descr` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表';

INSERT INTO `t_sys_role` (`id`, `name`, `code`, `descr`) VALUES
	(1, '游客', 'guest', '游客角色只有查看权限');

CREATE TABLE IF NOT EXISTS `t_sys_role_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色 id',
  `permission_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '权限 id',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_permission_id` (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

INSERT INTO `t_sys_role_permission` (`id`, `role_id`, `permission_id`) VALUES
	(1, 1, 1),
	(2, 1, 2),
	(3, 1, 6),
	(4, 1, 8),
	(5, 1, 12),
	(6, 1, 14),
	(7, 1, 18),
	(8, 1, 19),
	(9, 1, 20),
	(10, 1, 21),
	(11, 1, 22),
	(12, 1, 23);

CREATE TABLE IF NOT EXISTS `t_sys_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(30) NOT NULL COMMENT '用户名',
  `nickname` varchar(30) NOT NULL COMMENT '昵称',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `avatar` varchar(255) NOT NULL COMMENT '头像',
  `gender` tinyint(4) NOT NULL COMMENT '性別 1：男 0:  女',
  `email` varchar(50) NOT NULL DEFAULT '' COMMENT '邮箱地址',
  `qq` int(20) NOT NULL DEFAULT '0' COMMENT 'qq 号码',
  `mobile` varchar(32) NOT NULL DEFAULT '' COMMENT '手机号',
  `address` varchar(50) NOT NULL DEFAULT '' COMMENT '住址',
  `salt` varchar(12) NOT NULL DEFAULT 'freedom-boot' COMMENT '盐',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态  0：禁用 1：正常 2：锁定',
  `del` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除 1：是 0：否',
  `super_admin` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否是超级管理员 1：是 0：否',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`),
  UNIQUE KEY `idx_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

INSERT INTO `t_sys_user` (`id`, `username`, `nickname`, `password`, `avatar`, `gender`, `email`, `qq`, `mobile`, `address`, `salt`, `state`, `del`, `super_admin`, `remark`) VALUES
	(1, 'admin', '超级管理员', '21232f297a57a5a743894a0e4a801fc3', '/assets/picture/default-avatar.png', 1, 'jx8996@163.com', 0, '', '', 'freedom-boot', 1, 0, 1, '拥有最高权限'),
  (2, 'guest', '游客', 'e10adc3949ba59abbe56e057f20f883e', '/assets/picture/default-avatar.png', 1, 'guest@163.com', 0, '', '', 'freedom-boot', 1, 0, 0, '拥有查看权限');

CREATE TABLE IF NOT EXISTS `t_sys_user_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户 id',
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色 id',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

INSERT INTO `t_sys_user_role` (`user_id`, `role_id`) VALUES ('2', '1');

CREATE TABLE IF NOT EXISTS `t_sys_log` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
	`module_name` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '模块名称',
	`action_type` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '操作类型 1：增加 2：删除 3：修改 4：其他',
	`method_name` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '方法名称',
	`method_param` VARCHAR(2000) NOT NULL DEFAULT '' COMMENT '参数',
	`user_id` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '操作用户 id',
	`ip` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '操作 ip',
	`location` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '所在地',
	`remark` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '备注',
	`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统日志';
;

CREATE TABLE `t_file_data` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
	`filename` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '文件名称',
	`original_filename` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '原始文件名称',
	`url` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '文件路径',
	`thumbnail_url` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '缩略图路径',
	`file_key` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '文件 key(七牛云返回)',
	`code` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '管理类型 1：默认 2：七牛 3：oss',
	`content_type` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '内容类型',
	`operator_id` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '操作者 id',
	`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
	PRIMARY KEY (`id`),
	INDEX `idx_operator_id` (`operator_id`),
	INDEX `idx_filename` (`filename`),
	INDEX `idx_original_filename` (`original_filename`),
	INDEX `idx_create_time` (`create_time`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件数据';
;

CREATE TABLE `t_file_config` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `config_name` varchar(50) NOT NULL DEFAULT '' COMMENT '配置名称',
  `config_value` varchar(100) NOT NULL DEFAULT '' COMMENT '配置值',
  `config_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '类型 1：默认 2：七牛 3：oss',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_config_name` (`config_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件配置';

INSERT INTO `t_file_config`(`id`, `config_name`, `config_value`, `config_type`) VALUES
  (1, 'manage_mode', '1', 0),
  (2, 'upload_dir', '', 1),
  (3, 'qn_domain', '', 2),
  (4, 'qn_access_key', '', 2),
  (5, 'qn_secret_key', '', 2),
  (6, 'qn_bucket', '', 2),
  (7, 'oss_endpoint', '', 3),
  (8, 'oss_access_key', '', 3),
  (9, 'oss_secret_key', '', 3),
  (10, 'oss_bucket', '', 3);
