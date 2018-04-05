package com.pycogroup.taotran.task.management.core.repository;

import com.pycogroup.taotran.task.management.core.entity.User;
import com.pycogroup.taotran.task.management.core.entity.UserRole;

import java.util.List;

public interface UserRoleRepository extends DocumentRepository<UserRole> {

    List<UserRole> findAllByUser(User user);
}
