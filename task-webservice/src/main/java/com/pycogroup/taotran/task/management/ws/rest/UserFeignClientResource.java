package com.pycogroup.taotran.task.management.ws.rest;

import com.pycogroup.taotran.task.management.ws.config.feign.BaseFeignConfiguration;
import com.pycogroup.taotran.task.management.ws.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "http://TASK-CORE-MICRO-SERVICE", configuration = BaseFeignConfiguration.class)
public interface UserFeignClientResource {

//    @GetMapping(value = "/v1.0/api/users")
    @RequestMapping(method = RequestMethod.GET, value = "/v1.0/api/users", consumes = "application/json")
    List<User> getUsers();
}
