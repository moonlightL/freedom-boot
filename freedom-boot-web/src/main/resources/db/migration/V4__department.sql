CREATE TABLE `t_sys_department` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
	`name` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '名称',
	`code` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '编号',
	`pid` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '父级 id',
	`state` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '状态 1：可用 0：禁用',
	`descr` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '描述',
	`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
	PRIMARY KEY (`id`),
	INDEX `idx_pid` (`pid`)
)
ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

INSERT INTO `freedom-boot`.`sys_department` (`name`, `code`, `descr`) VALUES ('总裁办', 'boss', '总裁办');

ALTER TABLE `t_sys_user`
	ADD COLUMN `department_id` BIGINT NOT NULL DEFAULT '1' COMMENT '部门 id' AFTER `remark`,
	ADD INDEX `idx_department_id` (`department_id`);

INSERT INTO `t_sys_permission` (`id`, `name`, `icon`, `url`,`module_code`, `per_type`, `resource_type`, `per_code`, `pid`, `state`, `sort`, `is_common`) VALUES
  (41, '部门管理', 'fa fa-department', '/core/department/listUI.html', '', 2, 1, 'core:department:listUI', 1, 1, 4, 1),
	(42, '新增', 'fa fa-plus-circle', '', '', 3, 1, 'core:department:save', 41, 1, 1, 1),
	(43, '编辑', 'fa fa-edit', '', '', 3, 1, 'core:department:update', 41, 1, 2, 1),
	(44, '删除', 'fa fa-trash', '', '', 3, 1, 'core:department:remove', 41, 1, 3, 1),
	(45, '查询', 'fa fa-search', '', '', 3, 1, 'core:department:query', 41, 1, 4, 1);