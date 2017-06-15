package com.raytrex.erp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@EnableJpaRepositories(
		entityManagerFactoryRef = "frontierEntityManagerFactory", 
		transactionManagerRef = "frontierTransactionManager", 
		basePackages = {"com.raytrex.frontier.repository",
		"com.raytrex.frontier.repository.bean"
				})
public class FrontierRepositoryConfig {
	@Autowired
    private JpaVendorAdapter jpaVendorAdapter;

	@Autowired
	@Qualifier("frontierDatasource")
	private DataSource dataSource;

	@Bean(name="frontierDatasource")
	@Primary
	@ConfigurationProperties(prefix="frontier.datasource")
	public DataSource getDatasource(){
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "frontierEntityManager")
	public EntityManager entityManager() {
		return entityManagerFactory().createEntityManager();
	}

	@Primary
	@Bean(name = "frontierEntityManagerFactory")
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource);
		emf.setJpaVendorAdapter(jpaVendorAdapter);
		emf.setPackagesToScan("com.raytrex.frontier.repository","com.raytrex.frontier.repository.bean");
		emf.setPersistenceUnitName("default"); // <- giving 'default' as name
		emf.afterPropertiesSet();
		return emf.getObject();
	}

	@Bean(name = "forntierTransactionManager")
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager tm = new JpaTransactionManager();
		tm.setEntityManagerFactory(entityManagerFactory());
		return tm;
	}
}
