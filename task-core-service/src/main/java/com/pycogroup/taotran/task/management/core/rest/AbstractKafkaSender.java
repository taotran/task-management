package com.pycogroup.taotran.task.management.core.rest;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public abstract class AbstractKafkaSender<T extends SpecificRecordBase> {

    @Autowired
    protected KafkaTemplate<String, T> kafkaTemplate;

    public abstract void send(T t);
}
