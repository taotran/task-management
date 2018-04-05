package com.pycogroup.taotran.task.management.core.repository;


import com.pycogroup.taotran.task.management.core.entity.Role;

public interface RoleRepository extends DocumentRepository<Role> {

    Role findByRole(String role);
}
