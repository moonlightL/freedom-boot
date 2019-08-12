package com.extlight.web.flyway;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * @Author MoonlightL
 * @Title: FlywayConfig
 * @ProjectName: freedom-boot
 * @Description: 自定义 Flyway 配置，由于加载数据顺序问题，因此需要自定义
 * @DateTime: 2019/8/12 18:05
 */
@Configuration
public class FlywayConfig {

	@Autowired
	private DataSource dataSource;

	@PostConstruct
	public void migrate() {
		Flyway flyway = Flyway.configure()
				.dataSource(dataSource)
				.cleanDisabled(true)
				.baselineOnMigrate(true)
				.baselineVersion("0")
				.locations("db/migration")
				.load();
		flyway.migrate();
	}
}
