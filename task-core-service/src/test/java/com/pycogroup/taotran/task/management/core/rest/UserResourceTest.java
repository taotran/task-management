package com.pycogroup.taotran.task.management.core.rest;

import com.pycogroup.taotran.task.management.core.constant.MappingPath;
import com.pycogroup.taotran.task.management.core.entity.User;
import com.pycogroup.taotran.task.management.core.service.DocumentService;
import com.pycogroup.taotran.task.management.core.service.user.UserService;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserResourceTest extends BaseResourceTest<User> {

    @MockBean
    private UserService userService;

    public UserResourceTest() {
        super(User.class);
    }


//    @Test
//    @WithUserDetails(value = "admin")
//    public void givenUsers_whenFindUsers_thenReturnJsonArray() throws Exception {
//
//        final List<User> users = createUsers();
//
//        given(userService.findAll()).willReturn(new ArrayList<>(users)); // using new ArrayList to avoid java.lang.UnsupportedOperationException
//
//        mockMvc.perform(get(BASE_USER_MAPPING_PATH)
//                .contentType(MediaType.APPLICATION_JSON))
//
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].username", is(users.get(0).getUsername())));
//
//    }

    @Test
    @WithUserDetails(value = "admin")
    public void givenUsers_whenFilterUserByUserName_thenReturnJsonArray() throws Exception {

        final List<User> users = createUsers();

        given(userService.filterByUsername("te", null, new PageRequest(0, 2))).willReturn(new ArrayList<>(users));

        mockMvc.perform(get(getBaseMappingPath() + "/username")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", "te")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is(users.get(0).getUsername())));
    }


    @Test
    @WithUserDetails(value = "admin")
    public void givenUsers_whenFindByAgeRage_thenReturnJsonArray() throws Exception {

        final List<User> users = createUsers();

        given(userService.findBySpecificAgeRange(1, 11)).willReturn(new ArrayList<>(users));

        mockMvc.perform(get(getBaseMappingPath() + "/age-range")
                .contentType(MediaType.APPLICATION_JSON)
                .param("min", "1")
                .param("max", "11")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is(users.get(0).getUsername())))
                .andExpect(jsonPath("$[0].age", is(10)))
        ;
    }

    private List<User> createUsers() {
        return Arrays.asList(mockObject());
    }

    @Override
    protected String getBaseMappingPath() {
        return MappingPath.USER;
    }

    @Override
    protected DocumentService<User> mockService() {
        return userService;
    }

    @Override
    protected User mockObject() {
        return new User.Builder("testUser", "testP@ssw0rd")
                .age(10)
                .build();
    }
}
