package com.pycogroup.taotran.task.management.core.service.setup;

import com.pycogroup.taotran.task.management.core.entity.User;
import com.pycogroup.taotran.task.management.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

//@Component
public class SetupData implements ApplicationListener<ApplicationEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SetupData.class);

    private static final String INIT_SETUP_DATA = "config.init.setup.data";

    @Resource
    private Environment env;

    //    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationReadyEvent) {
            LOGGER.info("#### on ApplicationReady!!!");
            if (env.getProperty(INIT_SETUP_DATA, Boolean.class)) {
                LOGGER.info("### begin checking & setting up data ###");
                if (userService.findByUsername("admin") == null) {
//                    userService.save(createAdminUser(), Security)
                }
            }
        }
    }

    private User createAdminUser() {
        return new User("admin", "admin1234", createAdminAuthorities());
    }

    private List<? extends GrantedAuthority> createAdminAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
