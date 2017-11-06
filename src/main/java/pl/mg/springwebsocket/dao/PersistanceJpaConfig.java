package pl.mg.springwebsocket.dao;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.mg.springwebsocket.Profiles;

@Configuration
@ComponentScan(basePackages = "pl.mg")
@EnableTransactionManagement
public class PersistanceJpaConfig {

    @Resource(name = "mysqlDataSource")
    private DataSource dataSource;

    @Bean(name = "mysqlDataSource")
    @Primary
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("qaz123");
        return dataSource;
    }

    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPersistenceUnitName("user_pu");

        // Classpath scanning of @Component, @Service, etc annotated class
        entityManagerFactory.setPackagesToScan("pl.mg.springwebsocket");

        // Vendor adapter
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);

        // Hibernate properties
        Properties additionalProperties = new Properties();
        additionalProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        additionalProperties.put("hibernate.show_sql", "true");
        additionalProperties.put("hibernate.cache.provider_class","org.hibernate.cache.EhCacheProvider" );
        additionalProperties.put("hibernate.cache.region.factory_class","org.hibernate.cache.ehcache.EhCacheRegionFactory" );
        entityManagerFactory.setJpaProperties(additionalProperties);

        return entityManagerFactory;
    }

    @Bean
    @Primary
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    @Primary
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
