package com.lememz.nexguard;

import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;

@Configuration
public class Database {

    @Bean
    @SuppressWarnings("CallToPrintStackTrace")
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        try {
            LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
            Properties props = new Properties();
            props.load(ClassLoader.getSystemResourceAsStream("hibernate.properties"));
            factory.setHibernateProperties(props);
            factory.setAnnotatedClasses(Source.class, ValidAddress.class);
            factory.setDataSource(dataSource);
            return factory;
        }catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:database.db");
        return dataSource;
    }
}
