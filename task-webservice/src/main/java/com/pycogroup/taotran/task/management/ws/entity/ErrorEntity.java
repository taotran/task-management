package com.pycogroup.taotran.task.management.ws.entity;

public class ErrorEntity {

    private String id;
    private String message;
    private String desc;

    public ErrorEntity(String id, String message, String desc) {
        this.id = id;
        this.message = message;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
