package com.pycogroup.taotran.task.management.gateway.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

@Configuration
@EnableWebSecurity
@Order(100)
public class AuthenticationConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.requestMatchers()
//                .antMatchers("/login", "/oauth/authorize")
//                .and()
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated()

//                .and()
//                .addFilter(new PreFilter())
//                .addFilterBefore(securityContextPersistenceFilter(), SecurityContextPersistenceFilter.class)
//                .and()
//                .formLogin()
//                .permitAll()
//        ;

        http.logout().and()
                .antMatcher("/**").authorizeRequests().anyRequest().permitAll()
                ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.parentAuthenticationManager(authenticationManager)
                .inMemoryAuthentication()
                .withUser("admin")
                .password("admin")
                .roles("ADMIN");
    }

    @Bean
    protected SecurityContextPersistenceFilter securityContextPersistenceFilter() {
        return new SecurityContextPersistenceFilter();
    }

//    @Bean
//    public DefaultSecurityContextRepository securityContextRepository() {
//        return new DefaultSecurityContextRepository(accessTokenRepository, userDetailService, authorityService);
//    }
}
