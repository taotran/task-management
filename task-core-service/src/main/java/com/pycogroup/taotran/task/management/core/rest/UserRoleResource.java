package com.pycogroup.taotran.task.management.core.rest;

import com.pycogroup.taotran.task.management.core.constant.MappingPath;
import com.pycogroup.taotran.task.management.core.entity.UserRole;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(MappingPath.USER_ROLE)
public class UserRoleResource extends BaseResource<UserRole> {

}
