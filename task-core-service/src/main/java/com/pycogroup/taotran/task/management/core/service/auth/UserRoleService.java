package com.pycogroup.taotran.task.management.core.service.auth;


import com.pycogroup.taotran.task.management.core.entity.User;
import com.pycogroup.taotran.task.management.core.entity.UserRole;
import com.pycogroup.taotran.task.management.core.service.DocumentService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRoleService extends DocumentService<UserRole> {

    List<UserRole> findAllByUser(User user, Pageable pageable);
}
