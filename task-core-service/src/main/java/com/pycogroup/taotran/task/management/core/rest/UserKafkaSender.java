package com.pycogroup.taotran.task.management.core.rest;

import com.pycogroup.taotran.task.management.core.avroentity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;


@Component
public class UserKafkaSender extends AbstractKafkaSender<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserKafkaSender.class);

    @Value("${kafka.topic.user}")
    private String avroTopic;

    @Override
    public void send(User user) {

        LOGGER.info("sending user='{}' ", user.toString());
        ListenableFuture<SendResult<String, User>> listenableFuture = kafkaTemplate.send(avroTopic, user);

        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, User>>() {
            @Override
            public void onFailure(Throwable ex) {
                LOGGER.info("fail to send data");
            }

            @Override
            public void onSuccess(SendResult<String, User> result) {
                LOGGER.info("send '{}' successfully", result.getProducerRecord().value());
            }
        });
    }
}
