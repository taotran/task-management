package com.pycogroup.taotran.task.management.core.service.auth;

import com.pycogroup.taotran.task.management.core.constant.RoleConst;
import com.pycogroup.taotran.task.management.core.entity.Role;
import com.pycogroup.taotran.task.management.core.repository.RoleRepository;
import com.pycogroup.taotran.task.management.core.service.DocumentServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class RoleServiceBean extends DocumentServiceBean<Role> implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceBean(RoleRepository roleRepository) {
        Assert.notNull(roleRepository, "'roleRepository' must not be null!");

        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByRole(String role) {
        return roleRepository.findByRole(role);
    }

    @Override
    public Role getDefaultRole() {
        return findByRole(RoleConst.USER);
    }
}
