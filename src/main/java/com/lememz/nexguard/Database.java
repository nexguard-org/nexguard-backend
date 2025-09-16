package com.lememz.nexguard;

import java.io.IOException;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Database {
    
    @Bean
    @SuppressWarnings("CallToPrintStackTrace")
    public SessionFactory getDb() {
        try {
            Configuration config = new Configuration();
            Properties props = new Properties();
            props.load(ClassLoader.getSystemResourceAsStream("hibernate.properties"));
            config.setProperties(props);
            config.addAnnotatedClass(Source.class);
            ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(config.getProperties())
                .build();
            return config.buildSessionFactory(registry);
        }catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
