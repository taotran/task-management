package com.pycogroup.taotran.task.management.euserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableAutoConfiguration
@EnableEurekaServer
public class EurekaDiscoveryServer {

    public static void main(String[] args) {
        final SpringApplication eurekaServer = new SpringApplication(EurekaDiscoveryServer.class);

        eurekaServer.addListeners(new ApplicationPidFileWriter("eureka-server.pid"));
        eurekaServer.run();
    }
}
