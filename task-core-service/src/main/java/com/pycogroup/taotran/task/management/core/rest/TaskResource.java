package com.pycogroup.taotran.task.management.core.rest;


import com.pycogroup.taotran.task.management.core.constant.MappingPath;
import com.pycogroup.taotran.task.management.core.entity.Task;
import com.pycogroup.taotran.task.management.core.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping(MappingPath.TASK)
public class TaskResource extends BaseKafkaResource<Task, com.pycogroup.taotran.task.management.core.avroentity.Task> {

    private final TaskService taskService;

    @Autowired
    public TaskResource(TaskService taskService) {
        Assert.notNull(taskService, "'taskService' must not be null!");

        this.taskService = taskService;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Task> save(@RequestBody Task task) throws URISyntaxException {

        final ResponseEntity<Task> responseEntity = super.save(task);
        final Task t = responseEntity.getBody();

        final com.pycogroup.taotran.task.management.core.avroentity.Task serializedTask =
                com.pycogroup.taotran.task.management.core.avroentity.Task.newBuilder()
                        .setId(t.getId())
                        .setTitle(t.getTitle())
                        .setDescription(t.getDescription())
                        .setDueDate(t.getDueDate().getTime())
                        .setCreatedDate(t.getCreatedDate().getTime())
                        .setUpdatedDate(t.getUpdatedDate().getTime())
                        .setPriority(t.getPriority().name().toUpperCase())
                        .build();
        sender.send(serializedTask);

        return responseEntity;
    }
}
