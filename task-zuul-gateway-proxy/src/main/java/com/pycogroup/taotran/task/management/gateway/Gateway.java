package com.pycogroup.taotran.task.management.gateway;

import com.pycogroup.taotran.task.management.gateway.filters.ErrorFilter;
import com.pycogroup.taotran.task.management.gateway.filters.PostFilter;
import com.pycogroup.taotran.task.management.gateway.filters.PreFilter;
import com.pycogroup.taotran.task.management.gateway.filters.RouteFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@SpringBootApplication
@EnableAutoConfiguration
@EnableEurekaClient
public class Gateway {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Gateway.class);

        app.addListeners(new ApplicationPidFileWriter("zuul-gateway-proxy.pid"));

        app.run(args);
    }

    @Bean
    public PreFilter preFilter() {
        return new PreFilter();
    }

    @Bean
    public PostFilter postFilter() {
        return new PostFilter();
    }

    @Bean
    public RouteFilter routeFilter() {
        return new RouteFilter();
    }

    @Bean
    public ErrorFilter errorFilter() {
        return new ErrorFilter();
    }

}
