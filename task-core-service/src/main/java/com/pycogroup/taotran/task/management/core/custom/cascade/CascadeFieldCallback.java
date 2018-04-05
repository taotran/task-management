package com.pycogroup.taotran.task.management.core.custom.cascade;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;

public class CascadeFieldCallback implements ReflectionUtils.FieldCallback {

    private Object source;
    private MongoOperations operations;

    CascadeFieldCallback(Object source, MongoOperations operations) {
        this.source = source;
        this.operations = operations;
    }

    @Override
    public void doWith(Field field) throws IllegalAccessException {

        ReflectionUtils.makeAccessible(field);

        if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {

            @SuppressWarnings("unchecked") final Object fieldValue = field.get(source);
            if (fieldValue != null) {
                if (fieldValue instanceof List<?>) {

                    for (Object object : (List<?>) fieldValue) {
                        operations.save(object);
                    }
                } else {
                    operations.save(fieldValue);
                }

            }
        }
    }
}
