package com.pycogroup.taotran.task.management.ws.rest;

import com.pycogroup.taotran.task.management.ws.BaseAppTest;
import com.pycogroup.taotran.task.management.ws.avroentity.Task;
import com.pycogroup.taotran.task.management.ws.config.serializer.AvroSerializer;
import com.pycogroup.taotran.task.management.ws.entity.TaskDTO;
import com.pycogroup.taotran.task.management.ws.service.task.TaskService;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;

@TestPropertySource(locations = "classpath:app.properties")
//@Transactionalx
public class TaskConsumerAppTest extends BaseAppTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskConsumerAppTest.class);

    private static String RECEIVER_TOPIC = "tasktopic";

    @ClassRule
    public static KafkaEmbedded kafkaEmbedded = new KafkaEmbedded(1, true, RECEIVER_TOPIC);

    private KafkaTemplate<String, Task> kafkaTemplate;

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @Autowired
    private KafkaReceiver receiver;

    @MockBean
    private TaskService taskService;


    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();

        //setup Kafka producer properties
//        final Map<String, Object> senderProperties = KafkaTestUtils.senderProps(kafkaEmbedded.getBrokersAsString());
        final Map<String, Object> senderProperties = KafkaTestUtils.producerProps(kafkaEmbedded);
        senderProperties.put("key.serializer", StringSerializer.class);
        senderProperties.put("value.serializer", AvroSerializer.class);

        // create Kafka producer factory
        final ProducerFactory<String, Task> producerFactory =
                new DefaultKafkaProducerFactory<>(senderProperties);

        // create Kafka template
        kafkaTemplate = new KafkaTemplate<>(producerFactory);

        // set default topic to send to
        kafkaTemplate.setDefaultTopic(RECEIVER_TOPIC);

        // wait until the partitions are assigned
        for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry.getListenerContainers()) {
            ContainerTestUtils.waitForAssignment(messageListenerContainer, kafkaEmbedded.getPartitionsPerTopic());
        }

    }


    @Test
    public void testReceiveTask() throws Exception {
        // send object
        kafkaTemplate.sendDefault(sendingObject());

        //stub
        Mockito.when(taskService.save(any(TaskDTO.class))).thenReturn(new TaskDTO());

        LOGGER.debug("test-sender sent message='{}'", sendingObject());

        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);

        assertThat(receiver.getLatch().getCount()).isEqualTo(0);

    }


    private Task sendingObject() {

        return Task.newBuilder()
                .setId("ATestTaskId")
                .setTitle("A test task")
                .setDescription("Desc")
                .setDueDate(1520245063704L)
                .setPriority("CRITICAL")
                .setCreatedDate(1520245063704L)
                .setUpdatedDate(1520245063704L)
                .build();
    }
}
