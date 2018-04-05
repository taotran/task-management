package com.pycogroup.taotran.task.management.core.entity;


import com.pycogroup.taotran.task.management.core.enumeration.TaskPriority;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Document(collection = "task")
public class Task extends AbstractDocument {

    @Field
    @NotNull
    private String title;

    @Field
    private String description;

    @Field
    private Date dueDate = new Date(); //temporary set the current date if NULL

    @Field
    private TaskPriority priority;

    @DBRef
    private User user;


    public Task() { // NOSONAR
    }

    public Task(String title, String description, User user) {
        this.title = title;
        this.description = description;
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
