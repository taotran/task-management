package com.pycogroup.taotran.task.management.core.entity.projection;

import com.pycogroup.taotran.task.management.core.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "detailed", types = User.class)
public interface UserDetailProjection extends UserSimpleProjection {

    @Value("#{@userService.checkAccess(target) ? target.password : null}")
    String getPassword();
}
