package com.pycogroup.taotran.task.management.core.rest;


import com.pycogroup.taotran.task.management.core.constant.MappingPath;
import com.pycogroup.taotran.task.management.core.entity.User;
import com.pycogroup.taotran.task.management.core.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(MappingPath.USER)
public class UserResource extends BaseKafkaResource<User, com.pycogroup.taotran.task.management.core.avroentity.User> {

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {

        Assert.notNull(userService, "'userService' must not be null!");
        this.userService = userService;
    }

    @Override
    public ResponseEntity<User> save(@RequestBody User user) throws URISyntaxException {
        final ResponseEntity<User> responseEntity = super.save(user);

        final User u = responseEntity.getBody();


        final com.pycogroup.taotran.task.management.core.avroentity.User sendingUser =
                com.pycogroup.taotran.task.management.core.avroentity.User.newBuilder()
                        .setId(u.getId())
                        .setUsername(u.getUsername())
                        .setPassword(u.getPassword())
                        .setAge(u.getAge())
                        .setCredentialsNonExpired(u.isCredentialsNonExpired())
                        .setAccountNonExpired(u.isAccountNonExpired())
                        .setAccountNonLocked(u.isAccountNonLocked())
                        .setEnabled(u.isEnabled())
                        .setGrantedAuthorities(new ArrayList<>())
                        .build();

        sender.send(sendingUser);

        return responseEntity;
    }

    @GetMapping(path = "/age-range")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> findByAgeRange(@RequestParam int min, @RequestParam int max) {
        return userService.findBySpecificAgeRange(min, max);
    }

    @GetMapping(path = "/username")
    public List<User> filterUserByUsername(@RequestParam String username, Pageable pageable) {
        return userService.filterByUsername(username, null, pageable);
    }
}
