package com.pycogroup.taotran.task.management.core.entity.projection;

import com.pycogroup.taotran.task.management.core.entity.User;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "simple", types = User.class)
public interface UserSimpleProjection {

    String getId();

    String getUsername();

    int getAge();
}
