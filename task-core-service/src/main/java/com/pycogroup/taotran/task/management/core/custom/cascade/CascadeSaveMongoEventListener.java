package com.pycogroup.taotran.task.management.core.custom.cascade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.util.ReflectionUtils;

public class CascadeSaveMongoEventListener extends AbstractMongoEventListener {

    @Autowired
    private MongoOperations operations;

    @Override
    public void onBeforeConvert(BeforeConvertEvent event) {
        final Object object = event.getSource();

        ReflectionUtils.doWithFields(object.getClass(), new CascadeFieldCallback(object, operations));
    }
}
