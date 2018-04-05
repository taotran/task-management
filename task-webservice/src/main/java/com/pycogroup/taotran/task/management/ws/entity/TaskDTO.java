package com.pycogroup.taotran.task.management.ws.entity;


import com.pycogroup.taotran.task.management.ws.avroentity.Task;
import com.pycogroup.taotran.task.management.ws.enumeration.TaskPriority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "task")
public class TaskDTO extends AbstractEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "dueDate")
    private Date dueDate;

    @Column(name = "priority")
    private TaskPriority priority;


    public TaskDTO() { // NOSONAR
    }

    public TaskDTO(Task task) {
        setId(task.getId().toString());
        setTitle(task.getTitle().toString());
        setDescription(task.getDescription().toString());
        setDueDate(new Date(task.getDueDate()));
        setPriority(TaskPriority.valueOf(task.getPriority().toString()));
    }

    public TaskDTO(String title, String description) {
        this.title = title;
        this.description = description;
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


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TaskDTO{");
        sb.append("title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", dueDate=").append(dueDate);
        sb.append(", priority=").append(priority);
        sb.append('}');
        return sb.toString();
    }
}
