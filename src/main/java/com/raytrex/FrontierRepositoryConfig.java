package com.raytrex;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@EnableJpaRepositories(entityManagerFactoryRef = "frontierEntityManagerFactory", transactionManagerRef = "frontierTransactionManager", basePackages = {
		"com.raytrex.frontier.repository", "com.raytrex.frontier.repository.bean" })
public class FrontierRepositoryConfig {
	static Logger log = Logger.getLogger(FrontierRepositoryConfig.class);
	@Autowired
	private JpaVendorAdapter jpaVendorAdapter;

	@Autowired
	@Qualifier("frontierDatasource")
	private DataSource dataSource;

	@Bean(name = "frontierDatasource")
	@Primary
	@ConfigurationProperties(prefix = "frontier.datasource")
	public DataSource getDatasource() {
		JndiObjectFactoryBean jndiBean = new JndiObjectFactoryBean();
		jndiBean.setJndiName("jdbc/frontier");
		try {
			jndiBean.afterPropertiesSet();
			log.info("Forntier will use datasource with 'jdbc/frontier'");
			return (DataSource) jndiBean.getObject();
		} catch (Exception e) {
			jndiBean.setJndiName("java:/comp/env/jdbc/frontier");
			try {
				jndiBean.afterPropertiesSet();
				log.info("Forntier will use datasource with 'jdbc/frontier'");
				return (DataSource) jndiBean.getObject();
			} catch (Exception e1) {
				log.error("Frontier can't find any JNDI Datesource with name 'jdbc/frontier'");
			}
		}
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
		emf.setPackagesToScan("com.raytrex.frontier.repository", "com.raytrex.frontier.repository.bean");
		emf.setPersistenceUnitName("default"); // <- giving 'default' as name
		Properties p = new Properties();
		p.setProperty("hibernate.event.merge.entity_copy_observer", "allow");
		emf.setJpaProperties(p);
		emf.afterPropertiesSet();
		return emf.getObject();
	}

	@Bean(name = "frontierTransactionManager")
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager tm = new JpaTransactionManager();
		tm.setEntityManagerFactory(entityManagerFactory());
		return tm;
	}
}
