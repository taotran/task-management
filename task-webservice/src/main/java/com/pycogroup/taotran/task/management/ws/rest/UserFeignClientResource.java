package com.pycogroup.taotran.task.management.ws.rest;

import com.pycogroup.taotran.task.management.ws.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("http://TASK-CLIENT-MICRO-SERVICE")
public interface UserFeignClientResource {

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    List<User> getUsers();
}
