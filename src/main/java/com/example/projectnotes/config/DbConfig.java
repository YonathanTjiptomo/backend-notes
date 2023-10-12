package com.example.projectnotes.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "projectnotesEntityManagerFactory",
        transactionManagerRef = "targetTransactionManagerprojectnotes",
        basePackages = {"com.example.projectnotes.repo"}
)
public class DbConfig {
    @Primary
    @Bean(name = "projectnotesdbDataSource")
    @ConfigurationProperties(prefix = "spring.projectnotesdb")
    public DataSource projectnotesdbDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "projectnotesEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean projectnotesEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("projectnotesdbDataSource") DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean em = builder
                .dataSource(dataSource)
                .packages("com.example.projectnotes.model")
                .persistenceUnit("projectnotespersistence")
                .build();

        return em;
        //        return builder
//                .dataSource(dataSource)
//                .packages("com.example.projectnotes.model")
//                .persistenceUnit("plink")
//                .build();
    }

    @Primary
    @Bean(name = "targetTransactionManagerprojectnotes")
    public PlatformTransactionManager targetTransactionManagerprojectnotes(
            @Qualifier("projectnotesEntityManagerFactory") EntityManagerFactory projectnotesEntityManagerFactory) {
        return new JpaTransactionManager();
    }
}

