package com.pycogroup.taotran.task.management.ws.config.feign;

import feign.Contract;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseFeignConfiguration {

    @Bean
    public Contract feignContract() {
        return new Contract.Default();
    }

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("admin", "admin123");
    }
}
