package com.safetycar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

import static com.safetycar.util.Constants.ConfigConstants.*;

@Configuration
@PropertySource(APPLICATION_PROPERTIES)
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.safetycar.repositories"})
public class DbConfig {

    private final String dbUrl, dbUsername, dbPassword;

    public DbConfig(Environment env) {
        dbUrl = env.getProperty(DATABASE_URL);
        dbUsername = env.getProperty(DATABASE_USERNAME);
        dbPassword = env.getProperty(DATABASE_PASSWORD);
//        dbUrl = env.getProperty("database.url");
//        dbUsername = env.getProperty("database.username");
//        dbPassword = env.getProperty("database.password");
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(COM_MYSQL_CJ_JDBC_DRIVER);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }

    @Bean(name = ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        vendorAdapter.setDatabasePlatform(ORG_HIBERNATE_DIALECT_MY_SQL_DIALECT);
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(SAFETYCAR_MODELS);
        factory.setDataSource(dataSource);
        factory.setJpaProperties(properties());
        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory(dataSource()).getObject());
        return transactionManager;
    }

    private Properties properties() {
        Properties properties = new Properties();
        properties.setProperty(HIBERNATE_DIALECT, ORG_HIBERNATE_DIALECT_MY_SQL_DIALECT);
//        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.show_sql", "true");
//        properties.setProperty("hibernate.use_sql_comments", "true");
//        properties.setProperty("hibernate.query.substitutions", "true 1, false 0");
//        properties.setProperty("hibernate.jdbc.fetch_size", "20");
        properties.setProperty(HIBERNATE_CONNECTION_AUTOCOMMIT, "false");
        properties.setProperty(HIBERNATE_CONNECTION_RELEASE_MODE, "auto");
        properties.setProperty(HIBERNATE_USE_SQL_COMMENTS, "true");
        //properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        return properties;
    }
}
