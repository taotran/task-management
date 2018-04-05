package com.pycogroup.taotran.task.management.core.service.auth;

import com.pycogroup.taotran.task.management.core.entity.Role;
import com.pycogroup.taotran.task.management.core.service.DocumentService;

public interface RoleService extends DocumentService<Role> {

    Role findByRole(String role);

    Role getDefaultRole();
}
