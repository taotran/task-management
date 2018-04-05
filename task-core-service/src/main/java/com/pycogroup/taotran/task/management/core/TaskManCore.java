package com.pycogroup.taotran.task.management.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableAutoConfiguration
@EnableEurekaClient
@PropertySource("classpath:/app.conf/appconfig.properties")
public class TaskManCore {

    public static void main(String[] args) {
        final SpringApplication taskService = new SpringApplication(TaskManCore.class);

        taskService.addListeners(new ApplicationPidFileWriter("task-core-micro-service.pid"));

        taskService.run(args);
    }


}
