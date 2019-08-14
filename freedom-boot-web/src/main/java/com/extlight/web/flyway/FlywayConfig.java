package com.extlight.web.flyway;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Author MoonlightL
 * @ClassName: FlywayConfig
 * @ProjectName: freedom-boot
 * @Description: 自定义 Flyway 配置，由于加载数据顺序问题，因此需要自定义
 * @DateTime: 2019/8/12 18:05
 */
@Configuration
public class FlywayConfig {

	@Value("${spring.datasource.druid.master.url}")
	private String url;

	@Value("${spring.datasource.druid.master.username}")
	private String username;

	@Value("${spring.datasource.druid.master.password}")
	private String password;

	@PostConstruct
	public void migrate() {
		Flyway flyway = Flyway.configure()
				.dataSource(url, username, password)
				.cleanDisabled(true)
				.baselineOnMigrate(true)
				.baselineVersion("0")
				.locations("db/migration/")
				.load();
		flyway.migrate();
	}
}
