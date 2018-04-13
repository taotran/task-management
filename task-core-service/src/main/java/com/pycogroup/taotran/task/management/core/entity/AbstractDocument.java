package com.pycogroup.taotran.task.management.core.entity;

import org.mongodb.morphia.annotations.PrePersist;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.PreUpdate;
import java.util.Date;

public class AbstractDocument implements IDocument {

    @Id
//    @org.springframework.data.elasticsearch.annotations.Field(index = FieldIndex.analyzed)
    private String id;

    @Field
    private Date createdDate;

    @Field
    private Date updatedDate;

    protected AbstractDocument() {

    }

    @PrePersist
    public void prePersist() {
        this.createdDate = new Date();
    }


    @PreUpdate
    public void preUpdate() {
        this.updatedDate = new Date();
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
