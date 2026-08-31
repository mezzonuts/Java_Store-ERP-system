package com.sosha.config;
import org.hibernate.SessionFactory;
import org.hibernate.Interceptor;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class HibernateConfig {
    @Autowired private TenantInterceptor tenantInterceptor;
    @Bean
    public org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter vendorAdapter(){
        org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter a = new org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter();
        a.setShowSql(true);
        return a;
    }
    @Bean
    public org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean entityManagerFactory(javax.sql.DataSource ds){
        org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean emf = new org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(ds);
        emf.setPackagesToScan("com.sosha.core.domain");
        emf.setJpaVendorAdapter(vendorAdapter());
        emf.getJpaPropertyMap().put("hibernate.ejb.interceptor", tenantInterceptor);
        emf.getJpaPropertyMap().put("hibernate.physical_naming_strategy", new CamelCaseToUnderscoresNamingStrategy());
        return emf;
    }
}
