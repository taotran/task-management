package com.pycogroup.taotran.task.management.core.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;

@Document(collection = "role")
public class Role extends AbstractDocument {

    @Field
    @NotNull
    @Indexed(unique = true)
    private String role; //NO SONAR

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Role{");
        sb.append("role='").append(role).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
