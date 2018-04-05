package com.pycogroup.taotran.task.management.ws.service;

import com.pycogroup.taotran.task.management.ws.entity.User;
import com.pycogroup.taotran.task.management.ws.rest.UserFeignClientResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.pycogroup.taotran.task.management.ws.util.ConsumerUtils.toList;

@Service
public class UserService {

    private static final String USER_LIST = "http://localhost:8080/v1.0/api/users";

    private final RestTemplate restTemplate;

//    @Autowired
//    private UserFeignClientResource userFeignClientResource;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Object> findAll(HttpEntity requestEntity) {
        final ResponseEntity<Object[]> objects = restTemplate.exchange(USER_LIST, HttpMethod.GET, requestEntity, Object[].class);

        return toList(objects.getBody());
    }


//    public List<User> findAllUsingFeign() {
//        return userFeignClientResource.getUsers();
//    }
}
