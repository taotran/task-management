package com.pycogroup.taotran.task.management.core.service.user;

import com.pycogroup.taotran.task.management.core.entity.User;
import com.pycogroup.taotran.task.management.core.service.DocumentService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface UserService extends DocumentService<User> {


    List<User> findAll(int offset);

    List<User> findBySpecificAgeRange(int min, int max);

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    List<User> filterByUsername(String username, Sort sort, Pageable pageable);

    boolean checkAccess(User checkingUser);

    boolean checkAccess();

}
