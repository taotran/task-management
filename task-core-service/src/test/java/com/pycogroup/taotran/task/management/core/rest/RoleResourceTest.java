package com.pycogroup.taotran.task.management.core.rest;

import com.pycogroup.taotran.task.management.core.constant.MappingPath;
import com.pycogroup.taotran.task.management.core.entity.Role;
import com.pycogroup.taotran.task.management.core.service.auth.RoleService;
import org.springframework.boot.test.mock.mockito.MockBean;

public class RoleResourceTest extends BaseResourceTest<Role> {

    public RoleResourceTest() {
        super(Role.class);
    }

    @MockBean
    private RoleService roleService;

    @Override
    protected String getBaseMappingPath() {
        return MappingPath.ROLE;
    }

    @Override
    protected RoleService mockService() {
        return roleService;
    }

    @Override
    protected Role mockObject() {
        final Role role = new Role();
        role.setId(ID);
        role.setRole("ROLE_TEST");
        return role;
    }
}
