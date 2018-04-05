package com.pycogroup.taotran.task.management.ws.rest;

import com.pycogroup.taotran.task.management.ws.entity.TaskDTO;
import com.pycogroup.taotran.task.management.ws.service.task.TaskService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client/api/v1/tasks")
public class TaskConsumerResource {

    private final TaskService taskService;

    public TaskConsumerResource(TaskService taskService) {
        Assert.notNull(taskService, "'taskService' must not be null!");

        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDTO> findAllTasks() {
        return taskService.findAll();
    }
}
