package com.pycogroup.taotran.task.management.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrix
@EnableHystrixDashboard
@EnableAutoConfiguration
@EnableFeignClients
public class TaskWebservice {

    public static void main(String[] args) {
        final SpringApplication taskClientMicroService = new SpringApplication(TaskWebservice.class);
        taskClientMicroService.addListeners(
                new ApplicationPidFileWriter("task-client-micro-service.pid")
        );

        taskClientMicroService.run(args);
    }
}
