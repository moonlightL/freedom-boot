CREATE TABLE `t_task_job` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `job_name` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group_name` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '任务组',
  `bean_name` VARCHAR(64) NOT NULL DEFAULT '' COMMENT 'bean 名称',
  `method_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '方法名',
  `params` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '参数',
  `cron_expression` VARCHAR(12) NOT NULL DEFAULT '' COMMENT 'cron 表达式',
  `state` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '状态 0：关闭 1：开启 2：暂停',
  `remark` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  INDEX `idx_job_name` (`job_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务作业';