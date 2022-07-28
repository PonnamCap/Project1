package com.cg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@SuppressWarnings("deprecation")
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication().withUser("admin").password("root").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable().antMatcher("/**").authorizeRequests().anyRequest().hasRole("ADMIN")
                .and().httpBasic();
    }

    @Bean
    public PasswordEncoder gerPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
