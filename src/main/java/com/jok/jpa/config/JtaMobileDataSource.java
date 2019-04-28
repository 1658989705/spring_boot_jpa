package com.jok.jpa.config;

import java.sql.SQLException;
import java.util.HashMap;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import com.mysql.cj.jdbc.MysqlXADataSource;

@Configuration
@DependsOn("transactionManager")
@EnableJpaRepositories(basePackages = {"com.jok.jpa.mobile.dao"}, // 用于扫描持久层
		entityManagerFactoryRef = "mobileEntityManager", // 实体管理工厂
		transactionManagerRef = "transactionManager") // 事物管理器
@EnableConfigurationProperties(DBMobileConfig.class)
public class JtaMobileDataSource {
	@Autowired
	private JpaVendorAdapter jpaVendorAdapter;

	@Autowired
	public DBMobileConfig config;

	// 配置数据源
	@Bean(name = "mobileDataSource")
	public DataSource mobileDataSource() throws SQLException {
		// 新建数据源，并将数据源配置信息装置
		MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
		mysqlXADataSource.setUrl(config.getUrl());
		mysqlXADataSource.setUser(config.getUsername());
		mysqlXADataSource.setPassword(config.getPassword());
		// 将事物信息交给atomikos进行统一管理
		AtomikosDataSourceBean xDataSource = new AtomikosDataSourceBean();
		xDataSource.setXaDataSource(mysqlXADataSource); // 将数据源信息放入到atomikos容器中
		xDataSource.setUniqueResourceName("mobileDataSource");
		// 设置数据源其他参数信息
		xDataSource.setMinPoolSize(config.getMinPoolSize());
		xDataSource.setMaxPoolSize(config.getMaxPoolSize());
		xDataSource.setMaxLifetime(config.getMaxLifetime());
		xDataSource.setBorrowConnectionTimeout(config.getBorrowConnectionTimeout());
		xDataSource.setLoginTimeout(config.getLoginTimeout());
		xDataSource.setMaintenanceInterval(config.getMaintenanceInterval());
		xDataSource.setMaxIdleTime(config.getMaxIdleTime());
		xDataSource.setTestQuery(config.getTestQuery());
		return xDataSource;
	}

	@Bean(name = "mobileEntityManager")
	@DependsOn("transactionManager")
	public LocalContainerEntityManagerFactoryBean mobileEntityManager() throws Throwable {

		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
		properties.put("javax.persistence.transactionType", "JTA");

		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setJtaDataSource(mobileDataSource());
		entityManager.setJpaVendorAdapter(jpaVendorAdapter);
		entityManager.setPackagesToScan("com.jok.jpa.mobile.domain");
		entityManager.setPersistenceUnitName("mobilePersistenceUnit");
		entityManager.setJpaPropertyMap(properties);
		return entityManager;
	}

}
