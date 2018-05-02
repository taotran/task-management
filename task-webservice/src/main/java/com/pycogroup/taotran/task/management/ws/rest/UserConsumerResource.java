package com.pycogroup.taotran.task.management.ws.rest;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.pycogroup.taotran.task.management.ws.config.custom.EnableAuthentication;
import com.pycogroup.taotran.task.management.ws.entity.ErrorEntity;
import com.pycogroup.taotran.task.management.ws.entity.User;
import com.pycogroup.taotran.task.management.ws.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static com.pycogroup.taotran.task.management.ws.util.ConsumerUtils.buildHttpRequest;


@RestController
@RequestMapping("/client/v1.0/api/users")
public class UserConsumerResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserConsumerResource.class);

    @Autowired
    private MessageSource messageSource;

    private static final String USER_LIST = "http://localhost:8080/v1.0/api/users";

    private static final String CORE_APP_FAILED_MESSAGE = "core.app.failed.msg";

    private static final String CORE_APP_FAILED_CONTACT = "core.app.failed.contact.info";

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    @HystrixCommand(fallbackMethod = "reliable")
    public ResponseEntity<?> consumeUsers(@EnableAuthentication(adminAuth = true) String authString) {

        return new ResponseEntity<>(userService.findAll(buildHttpRequest(authString)), HttpStatus.OK);
//        return new ResponseEntity<>(userService.findAllUsingFeign(), HttpStatus.OK);
    }

    @PostMapping
    public void saveUsers(@EnableAuthentication(adminAuth = true) String authString, @RequestBody Object user) {

        final HttpEntity<Object> httpEntity = buildHttpRequest(authString, user);

        final ResponseEntity<String> responseEntity = restTemplate.postForEntity(USER_LIST, httpEntity, String.class);
    }

    @SuppressWarnings("all")
    public ResponseEntity<?> reliable(String authString, Throwable e) {
        LOGGER.debug(e.getMessage());
        final String errorId = UUID.randomUUID().toString();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ErrorEntity(errorId
                        , messageSource.getMessage(CORE_APP_FAILED_MESSAGE
                        , null, Locale.ENGLISH), messageSource.getMessage(CORE_APP_FAILED_CONTACT
                        , new String[]{errorId}, Locale.ENGLISH)));
    }

}
