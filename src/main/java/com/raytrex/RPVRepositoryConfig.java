package com.raytrex;

import java.util.Properties;

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
	entityManagerFactoryRef = "rpvEntityManagerFactory",
	transactionManagerRef = "rpvTransactionManager", 
	basePackages = {"com.raytrex.rpv.repository",
		"com.raytrex.rpv.repository.bean" })
public class RPVRepositoryConfig {
	@Autowired
    JpaVendorAdapter jpaVendorAdapter;
	@Autowired
	@Qualifier("rpvDataSource")
	private DataSource dataSource;

	@Bean("rpvDataSource")
	@ConfigurationProperties(prefix="rpv.datasource")
    public DataSource dataSource() {
		 return DataSourceBuilder.create().build();
    }

    @Bean(name = "rpvEntityManager")
    public EntityManager entityManager() {
        return entityManagerFactory().createEntityManager();
    }

    @Bean(name = "rpvEntityManagerFactory")
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setPackagesToScan("com.raytrex.rpv.repository","com.raytrex.rpv.repository.bean");   // <- package for entities
        emf.setPersistenceUnitName("rpvPersistenceUnit");
        emf.afterPropertiesSet();
        return emf.getObject();
    }

    @Bean(name = "rpvTransactionManager")
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }
}
