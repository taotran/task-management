package com.pycogroup.taotran.task.management.ws.config.kafka;

import com.pycogroup.taotran.task.management.ws.avroentity.Task;
import com.pycogroup.taotran.task.management.ws.deserializer.AvroDeserializer;
import com.pycogroup.taotran.task.management.ws.rest.KafkaReceiver;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableKafka
@PropertySource("classpath:kafka-config.properties")
public class KafkaReceiverConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaReceiverConfig.class);

    //    @Value("${kafka.bootstrap-servers}")
    //    private String bootstrapServers;

    @Autowired
    Environment env;

    @Bean
    public Map<String, Object> receiverConfigs() {
        final Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("bootstrap-servers"));
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, AvroDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, AvroDeserializer.class);

        props.put(ConsumerConfig.GROUP_ID_CONFIG, "avro");

        return props;
    }


    @Bean
    public ConsumerFactory<String, Task> receiverFactory() {
        return new DefaultKafkaConsumerFactory<>(receiverConfigs(), new StringDeserializer(), new AvroDeserializer<>(Task.class));
    }


    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Task>> kafkaListenerContainerFactory() {
        final ConcurrentKafkaListenerContainerFactory<String, Task> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(receiverFactory());
        return factory;
    }

    @Bean
    public KafkaReceiver receiver() {
        return new KafkaReceiver();
    }

}
