package com.pycogroup.taotran.task.management.core.service.user;

import com.pycogroup.taotran.task.management.core.entity.QUser;
import com.pycogroup.taotran.task.management.core.entity.Role;
import com.pycogroup.taotran.task.management.core.entity.User;
import com.pycogroup.taotran.task.management.core.entity.UserRole;
import com.pycogroup.taotran.task.management.core.exception.DefaultRoleNotFoundException;
import com.pycogroup.taotran.task.management.core.repository.UserRepository;
import com.pycogroup.taotran.task.management.core.search.es.UserESRepository;
import com.pycogroup.taotran.task.management.core.service.DocumentServiceBean;
import com.pycogroup.taotran.task.management.core.service.auth.RoleService;
import com.pycogroup.taotran.task.management.core.service.auth.UserRoleService;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceBean extends DocumentServiceBean<User> implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceBean.class);

    private final MongoOperations operations;

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final UserRoleService userRoleService;

    @Autowired
    private UserESRepository userESRepository;

    @Autowired
    private ElasticsearchOperations elasticsearchTemplate;

    @Autowired
    public UserServiceBean(MongoOperations operations, UserRepository userRepository, RoleService roleService, UserRoleService userRoleService) {
        Assert.notNull(operations, "'operations' must not be null!");
        Assert.notNull(userRepository, "'userRepository' must not be null!");
        Assert.notNull(roleService, "'roleService' must not be null!");
        Assert.notNull(userRoleService, "'userRoleService' must not be null!");

        this.operations = operations;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
    }


    @Override
    @Transactional
    public User save(User user) {
        final Role userRole = roleService.getDefaultRole();

        if (userRole == null) {
            throw new DefaultRoleNotFoundException("Default role could not be found!");
        }

        final User result = super.save(user);

        final UserRole ur = new UserRole(result, userRole);

        userRoleService.save(ur);

//        userESRepository.index(user);
//        elasticsearchOperations.index

        return user;
    }

    @Override
    public List<User> findAll(int offset) {
        return new ArrayList<>();
    }

    @Override
    public List<User> findBySpecificAgeRange(int min, int max) {
        final Query query = new Query();

        query.addCriteria(Criteria.where("age").gte(min).lte(max));

        return operations.find(query, User.class);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> filterByUsername(String username, Sort sort, Pageable pageable) {

        final QUser qUser = new QUser("user");

        final Predicate predicate = qUser.username.contains(username);

        return userRepository.findAll(predicate, pageable).getContent();
    }

    @Override
    public boolean checkAccess(User checkingUser) {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername().equalsIgnoreCase("admin") || userDetails.getUsername().equals(checkingUser.getUsername());
    }

    @Override
    public boolean checkAccess() {
        return true;
    }
}
