-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.21-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.2.0.4949
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 freedom 的数据库结构
CREATE DATABASE IF NOT EXISTS `freedom` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `freedom`;


-- 导出  表 freedom.t_sys_permission 结构
CREATE TABLE IF NOT EXISTS `t_sys_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `icon` varchar(50) NOT NULL DEFAULT '' COMMENT '权限图标',
  `url` varchar(50) NOT NULL DEFAULT '' COMMENT '权限访问地址',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '类型 1: 模块 2：菜单 3: 按钮',
  `code` varchar(50) NOT NULL DEFAULT '' COMMENT '权限编码',
  `pid` bigint(20) NOT NULL DEFAULT '0' COMMENT '父级 id',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1:可用 0：禁用',
  `sort` tinyint(4) NOT NULL DEFAULT '1' COMMENT '排序',
  `common` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '是否通用 1：是 0：否， 针对 type=3',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `pid` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COMMENT='系统权限表';

-- 正在导出表  freedom.t_sys_permission 的数据：~22 rows (大约)
/*!40000 ALTER TABLE `t_sys_permission` DISABLE KEYS */;
INSERT INTO `t_sys_permission` (`id`, `name`, `icon`, `url`, `type`, `code`, `pid`, `state`, `sort`, `common`) VALUES
	(1, '系统管理', 'fa fa-cogs', '', 1, '', 0, 1, 1, 1),
	(2, '用户管理', 'fa fa-users', '/system/user/listUI.html', 2, 'system:user:listUI', 1, 1, 1, 1),
	(3, '新增', 'fa fa-plus-circle', '', 3, 'system:user:save', 2, 1, 1, 1),
	(4, '编辑', 'fa fa-edit', '', 3, 'system:user:update', 2, 1, 2, 1),
	(5, '删除', 'fa fa-trash', '', 3, 'system:user:remove', 2, 1, 3, 1),
	(6, '查询', 'fa fa-search', '', 3, 'system:user:query', 2, 1, 4, 1),
	(7, '分配角色', 'fa fa-role', '', 3, 'system:user:assign:role', 2, 1, 5, 0),
	(8, '角色管理', 'fa fa-user-secret', '/system/role/listUI.html', 2, 'system:role:listUI', 1, 1, 2, 1),
	(9, '新增', 'fa fa-plus-circle', '', 3, 'system:role:save', 8, 1, 1, 1),
	(10, '编辑', 'fa fa-edit', '', 3, 'system:role:update', 8, 1, 2, 1),
	(11, '删除', 'fa fa-trash', '', 3, 'system:role:remove', 8, 1, 3, 1),
	(12, '查询', 'fa fa-search', '', 3, 'system:role:query', 8, 1, 4, 1),
	(13, '分配权限', 'fa fa-permission', '', 3, 'system:role:assign:permission', 8, 1, 5, 0),
	(14, '权限管理', 'fa fa-crosshairs', '/system/permission/listUI.html', 2, 'system:permission:listUI', 1, 1, 3, 1),
	(15, '新增', 'fa fa-plus-circle', '', 3, 'system:permission:save', 14, 1, 1, 1),
	(16, '编辑', 'fa fa-edit', '', 3, 'system:permission:update', 14, 1, 2, 1),
	(17, '删除', 'fa fa-trash', '', 3, 'system:permission:remove', 14, 1, 3, 1),
	(18, '日志管理', 'fa fa-monitor', '/system/log/listUI.html', 2, 'system:log:listUI', 1, 1, 4, 1),
	(19, '查询', 'fa fa-search', '', 3, 'system:log:query', 18, 1, 1, 1),
	(20, '代码管理', 'fa fa-code', '', 1, '', 0, 1, 2, 1),
	(21, '数据表管理', 'fa fa-user', '/generator/table/listUI.html', 2, 'generator:table:listUI', 20, 1, 1, 1),
	(22, '系统监控', 'fa fa-eye', '', 1, '', 0, 1, 3, 1),
	(23, '系统信息', 'fa fa-server', '/monitor/server/listUI.html', 2, 'monitor:server:listUI', 22, 1, 1, 1);
/*!40000 ALTER TABLE `t_sys_permission` ENABLE KEYS */;

-- 导出  表 freedom.t_sys_role 结构
CREATE TABLE IF NOT EXISTS `t_sys_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '角色名称',
  `code` varchar(50) NOT NULL DEFAULT '' COMMENT '角色编码',
  `descr` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表';

-- Dumping data for table freedom.t_sys_role: ~1 rows (approximately)
/*!40000 ALTER TABLE `t_sys_role` DISABLE KEYS */;
INSERT INTO `t_sys_role` (`id`, `name`, `code`, `descr`) VALUES
	(1, '游客', 'guest', '游客角色只有查看权限');
/*!40000 ALTER TABLE `t_sys_role` ENABLE KEYS */;

-- 导出  表 freedom.t_sys_role_permission 结构
CREATE TABLE IF NOT EXISTS `t_sys_role_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色 id',
  `permission_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '权限 id',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `permission_id` (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- Dumping data for table freedom.t_sys_role_permission: ~10 rows (approximately)
/*!40000 ALTER TABLE `t_sys_role_permission` DISABLE KEYS */;
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
/*!40000 ALTER TABLE `t_sys_role_permission` ENABLE KEYS */;

-- 导出  表 freedom.t_sys_user 结构
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
  `salt` varchar(12) NOT NULL DEFAULT 'freedom' COMMENT '盐',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态  0：禁用 1：正常 2：锁定',
  `del` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除 1：是 0：否',
  `super_admin` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否是超级管理员 1：是 0：否',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- 正在导出表  freedom.t_sys_user 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `t_sys_user` DISABLE KEYS */;
INSERT INTO `t_sys_user` (`id`, `username`, `nickname`, `password`, `avatar`, `gender`, `email`, `qq`, `mobile`, `address`, `salt`, `state`, `del`, `super_admin`, `remark`) VALUES
	(1, 'admin', '超级管理员', '21232f297a57a5a743894a0e4a801fc3', '/assets/picture/default-avatar.png', 1, 'jx8996@163.com', 0, '', '', 'freedom', 1, 0, 1, '拥有最高权限'),
  (2, 'guest', '游客', 'e10adc3949ba59abbe56e057f20f883e', '/assets/picture/default-avatar.png', 1, 'guest@163.com', 0, '', '', 'freedom', 1, 0, 0, '拥有查看权限');
/*!40000 ALTER TABLE `t_sys_user` ENABLE KEYS */;


-- 导出  表 freedom.t_sys_user_role 结构
CREATE TABLE IF NOT EXISTS `t_sys_user_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户 id',
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色 id',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- Dumping data for table freedom.t_sys_user_role: ~1 rows (approximately)
/*!40000 ALTER TABLE `t_sys_user_role` DISABLE KEYS */;
INSERT INTO `t_sys_user_role` (`user_id`, `role_id`) VALUES ('2', '1');
/*!40000 ALTER TABLE `t_sys_user_role` ENABLE KEYS */;

-- 导出  表 freedom.t_sys_log 结构
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
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统日志';
;