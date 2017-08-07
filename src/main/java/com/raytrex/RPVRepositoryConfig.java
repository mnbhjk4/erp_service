package com.raytrex;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@EnableJpaRepositories(
		entityManagerFactoryRef = "rpvEntityManagerFactory", 
		transactionManagerRef = "rpvTransactionManager", 
		basePackages = {
		"com.raytrex.rpv.repository", "com.raytrex.rpv.repository.bean" 
		}
)
public class RPVRepositoryConfig {
	static Logger log = Logger.getLogger(RPVRepositoryConfig.class);
	@Autowired
	JpaVendorAdapter jpaVendorAdapter;
	@Autowired
	@Qualifier("rpvDataSource")
	private DataSource dataSource;

	@Bean("rpvDataSource")
	public DataSource dataSource(RaytrexRPVDbConfig config) {
		JndiObjectFactoryBean jndiBean = new JndiObjectFactoryBean();
		jndiBean.setJndiName("jdbc/raytrexrvp");
		try {
			jndiBean.afterPropertiesSet();
			log.info("Forntier will use datasource with 'jdbc/raytrexrvp'");
			return (DataSource) jndiBean.getObject();
		} catch (Exception e) {
			jndiBean.setJndiName("java:/comp/env/jdbc/raytrexrvp");
			try {
				jndiBean.afterPropertiesSet();
				log.info("Forntier will use datasource with 'jdbc/raytrexrvp'");
				return (DataSource) jndiBean.getObject();
			} catch (Exception e1) {
				log.error("Frontier can't find any JNDI Datesource with name 'jdbc/raytrexrvp'");
			}
		}
		DataSourceBuilder dsb = DataSourceBuilder.create();
		dsb.driverClassName(config.getDriverClassName());
		dsb.username(config.getUsername());
		dsb.password(config.getPassword());
		dsb.url(config.getUrl());
		return dsb.build();
	}

	@Bean(name = "rpvEntityManager")
	@Qualifier("rpvEntityManagerFactory")
	public EntityManager entityManager(EntityManagerFactory factory) {
		return factory.createEntityManager();
	}

	@Bean(name = "rpvEntityManagerFactory")
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		HibernateJpaVendorAdapter vendorAdapter= new HibernateJpaVendorAdapter();
		emf.setDataSource(dataSource);
		emf.setJpaVendorAdapter(vendorAdapter);
		emf.setPackagesToScan("com.raytrex.rpv.repository", "com.raytrex.rpv.repository.bean"); // <-
																								// package
																								// for
																								// entities
		emf.setPersistenceUnitName("rpvPersistenceUnit");
		emf.afterPropertiesSet();
		return emf.getObject();
	}

	@Bean(name = "rpvTransactionManager")
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager(entityManagerFactory());
	}
}
