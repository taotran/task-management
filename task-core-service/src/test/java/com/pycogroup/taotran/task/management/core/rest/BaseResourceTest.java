package com.pycogroup.taotran.task.management.core.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pycogroup.taotran.task.management.core.BaseAppTest;
import com.pycogroup.taotran.task.management.core.entity.AbstractDocument;
import com.pycogroup.taotran.task.management.core.service.DocumentService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@DataMongoTest
public abstract class BaseResourceTest<T extends AbstractDocument> extends BaseAppTest {

    protected static final String ID = "anId";

    private Class<T> classType;

    public BaseResourceTest(Class<T> classType) {
        this.classType = classType;
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithUserDetails(value = "admin")
    public void testFindAll() throws Exception {
        final List<T> ts = new ArrayList<>();
        ts.add(mockObject());
        given(mockService().findAll()).willReturn(ts);

        mockMvc.perform(get(getBaseMappingPath())
                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
        ;
    }

    @Test
    @WithUserDetails(value = "admin")
    public void testFindOne() throws Exception {
        given(mockService().findOne(ID)).willReturn(mockObject());

        final MvcResult result = mockMvc.perform(get(getBaseMappingPath() + "/{id}", ID)
                .contentType(MediaType.APPLICATION_JSON)).andReturn()

//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isNotEmpty())
                ;

        final T t = objectMapper.readValue(result.getResponse().getContentAsString(), classType);

        assertTrue(t != null);

        System.out.println(t.toString());
    }


    protected abstract String getBaseMappingPath();

    protected abstract DocumentService<T> mockService();

    protected abstract T mockObject();
}
