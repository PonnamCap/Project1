package com.cg;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class AppConfig {

    @Autowired
    private Environment env;

    @Bean(destroyMethod = "close", name = "dataSourceProduct")
    public DataSource dataSourceProduct() {
        return DataSourceBuilder.create().driverClassName(env.getProperty("product.datasource.driver-class-name"))
                .url(env.getProperty("product.datasource.url")).username(env.getProperty("product.datasource.name")).password(env.getProperty("product.datasource.password")).build();
    }

    @Autowired
    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("dataSourceProduct") DataSource dataSourceProduct) {
        return new JdbcTemplate(dataSourceProduct);
    }
}
