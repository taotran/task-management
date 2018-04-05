package com.pycogroup.taotran.task.management.core.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pycogroup.taotran.task.management.core.BaseMQResourceTest;
import com.pycogroup.taotran.task.management.core.constant.MappingPath;
import com.pycogroup.taotran.task.management.core.entity.Task;
import com.pycogroup.taotran.task.management.core.enumeration.TaskPriority;
import com.pycogroup.taotran.task.management.core.service.DocumentService;
import com.pycogroup.taotran.task.management.core.service.task.TaskService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.assertj.core.api.Java6Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.kafka.test.assertj.KafkaConditions.key;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TaskResourceTest extends BaseMQResourceTest<Task, com.pycogroup.taotran.task.management.core.avroentity.Task> {

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;


    public TaskResourceTest() {
        super(Task.class);
    }

    @Test
    @WithUserDetails(value = "admin")
//    @Ignore // TODO: temporary disabled
    public void givenTask_whenSaveTask_thenReturnJsonObject() throws Exception {

        given(taskService.save(any(Task.class))).willReturn(mockObject());

        mockMvc.perform(post(getBaseMappingPath())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(mockObject()))
        )
                .andExpect(status().isCreated())
        ;

        // check that the message was received
        final ConsumerRecord<String, com.pycogroup.taotran.task.management.core.avroentity.Task> received = records.poll(10, TimeUnit.SECONDS);

        // verify received message not null
        assertNotNull(received);

        // Hamcrest Matchers to check the value
        assertThat(received, hasValue(deserializedReceivedObject()));

        // AssertJ Condition to check the key
        Java6Assertions.assertThat(received).has(key(null));


    }


    @Override
    protected String getBaseMappingPath() {
        return MappingPath.TASK;
    }

    @Override
    protected DocumentService<Task> mockService() {
        return taskService;
    }

    @Override
    protected Task mockObject() {
        final Task task = new Task();
        task.setId("ATestTaskId");
        task.setTitle("A test task");
        task.setDescription("Desc");
        task.setDueDate(new Date(1520245063704L));
        task.setPriority(TaskPriority.CRITICAL);

        task.setCreatedDate(new Date(1520245063704L));
        task.setUpdatedDate(new Date(1520245063704L));

        return task;
    }

    private com.pycogroup.taotran.task.management.core.avroentity.Task deserializedReceivedObject() {
        final Task sendObject = mockObject();

        return com.pycogroup.taotran.task.management.core.avroentity.Task.newBuilder()
                .setId(sendObject.getId())
                .setTitle(sendObject.getTitle())
                .setDescription(sendObject.getDescription())
                .setDueDate(sendObject.getDueDate().getTime())
                .setPriority(sendObject.getPriority().name())
                .setCreatedDate(sendObject.getCreatedDate().getTime())
                .setUpdatedDate(sendObject.getUpdatedDate().getTime())
                .build();
    }
}
