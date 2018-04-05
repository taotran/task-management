package com.pycogroup.taotran.task.management.core.rest;

import com.pycogroup.taotran.task.management.core.constant.MappingPath;
import com.pycogroup.taotran.task.management.core.entity.Role;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = MappingPath.ROLE)
public class RoleResource extends BaseResource<Role> {


}
