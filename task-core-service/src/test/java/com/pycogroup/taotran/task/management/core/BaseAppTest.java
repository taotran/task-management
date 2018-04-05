package com.pycogroup.taotran.task.management.core;

import com.pycogroup.taotran.task.management.core.config.SpringSecurityWebTestConfig;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringSecurityWebTestConfig.class
)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SuppressWarnings("all")
@Ignore
public class BaseAppTest {

    @Autowired
    protected MockMvc mockMvc;
}
