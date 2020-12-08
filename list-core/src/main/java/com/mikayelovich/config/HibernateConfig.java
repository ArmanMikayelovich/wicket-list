package com.mikayelovich.config;


import com.mikayelovich.model.IssueEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    private final ApplicationContext context;

    public HibernateConfig(ApplicationContext context) {
        this.context = context;
    }


    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

        factoryBean.setConfigLocation(context.getResource("classpath:hibernate.cfg.xml")); // load hibernate config from that file
//        factoryBean.setConfigLocation(context.getResource("/hibernate.cfg.xml")); // load hibernate config from that file
        factoryBean.setAnnotatedClasses(IssueEntity.class);
        return factoryBean;
    }

    @Bean
    public HibernateTransactionManager getTransactionManager() {
        final HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }
}