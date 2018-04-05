package com.pycogroup.taotran.task.management.core.rest;


import com.pycogroup.taotran.task.management.core.avroentity.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class TaskKafkaSender extends AbstractKafkaSender<Task> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskKafkaSender.class);


    @Value("${kafka.topic.task}")
    private String avroTopic;

    @Override
    public void send(Task task) {
        LOGGER.info("sending task='{}' ", task);
        final ListenableFuture<SendResult<String, Task>> listenableFuture = kafkaTemplate.send(avroTopic, task);

        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, Task>>() {
            @Override
            public void onFailure(Throwable ex) {
                LOGGER.info("fail to send data");
            }

            @Override
            public void onSuccess(SendResult<String, Task> result) {
                LOGGER.info("send '{}' successfully", result.getProducerRecord().value());
            }
        });
    }
}
