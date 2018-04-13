package com.pycogroup.taotran.task.management.core.service.auth;

import com.mysema.commons.lang.Assert;
import com.pycogroup.taotran.task.management.core.entity.User;
import com.pycogroup.taotran.task.management.core.entity.UserRole;
import com.pycogroup.taotran.task.management.core.repository.UserRoleRepository;
import com.pycogroup.taotran.task.management.core.service.DocumentServiceBean;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceBean extends DocumentServiceBean<UserRole> implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceBean(UserRoleRepository userRoleRepository) {
        Assert.notNull(userRoleRepository, "'userRoleRepository' must not be nulll!");
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public List<UserRole> findAllByUser(User user, Pageable pageable) {
        return userRoleRepository.findAllByUser(user, pageable);
    }
}
