package com.pycogroup.taotran.task.management.core;

import com.pycogroup.taotran.task.management.core.deserializer.TaskAvroDeserializer;
import com.pycogroup.taotran.task.management.core.entity.AbstractDocument;
import com.pycogroup.taotran.task.management.core.rest.BaseResourceTest;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@TestPropertySource(locations = "classpath:app.properties")
public abstract class BaseMQResourceTest<T extends AbstractDocument, S extends SpecificRecordBase> extends BaseResourceTest<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseMQResourceTest.class);

    private KafkaMessageListenerContainer<String, S> container;

    protected BlockingQueue<ConsumerRecord<String, S>> records;

//    @Value(value = "${bootstrap-servers}")
//    private String bootstrapServers;

    @Autowired
    Environment environment;

    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, senderTopic());

    public BaseMQResourceTest(Class<T> classType) {
        super(classType);
    }

    @Before
    public void setUp() throws Exception {
        // set up the Kafka consumer properties
        final Map<String, Object> consumerProperties =
                KafkaTestUtils.consumerProps("sender", "false", embeddedKafka);

        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, TaskAvroDeserializer.class);
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("bootstrap-servers"));

        // create a Kafka consumer factory
        final DefaultKafkaConsumerFactory<String, S> consumerFactory =
                new DefaultKafkaConsumerFactory<>(consumerProperties);

        // set the topic that needs to be consumed
        final ContainerProperties containerProperties = new ContainerProperties(senderTopic());

        // create a Kafka MessageListenerContainer
        container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);

        // create a thread safe queue to store the received message
        records = new LinkedBlockingQueue<>();

        // setup a Kafka message listener
        container.setupMessageListener(new MessageListener<String, S>() {
            @Override
            public void onMessage(ConsumerRecord<String, S> record) {
                LOGGER.debug("test-listener received message='{}'", record.toString());
                records.add(record);
            }
        });

        // start the container and underlying message listener
        container.start();

        // wait until the container has the required number of assigned partitions
        ContainerTestUtils.waitForAssignment(container, embeddedKafka.getPartitionsPerTopic());
    }

    @After
    public void tearDown() {
        // stop the container
        container.stop();
    }

    protected static String senderTopic() {
        return "tasktopic";
    }
}
