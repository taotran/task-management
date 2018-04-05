package com.pycogroup.taotran.task.management.ws.entity;


import com.pycogroup.taotran.task.management.ws.avroentity.Task;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class User extends AbstractEntity implements UserDetails {


    @NotNull
    @NotBlank
    private String username;

    @Length(min = 8, max = 100)
    @Valid
    private String password;

    @Min(10)
    @Max(100)
    private int age;

    private List<Task> taskList;

    private List<? extends GrantedAuthority> grantedAuthorities;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;


    public User() { // NOSONAR
        super();
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    public User(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.grantedAuthorities = builder.grantedAuthorities;
        this.age = builder.age;
        this.taskList = builder.taskList;
        this.accountNonExpired = builder.accountNonExpired;
        this.accountNonLocked = builder.accountNonLocked;
        this.credentialsNonExpired = builder.credentialsNonExpired;
        this.enabled = builder.enabled;

    }

    public User(String username, String password, List<? extends GrantedAuthority> grantedAuthorities) {
        this();
        this.username = username;
        this.password = password;
        this.grantedAuthorities = grantedAuthorities;
    }

    public User(String username, String password, GrantedAuthority... authorities) {
        this(username, password, Arrays.asList(authorities));
    }

    public User(String username, String password) {
        this(username, password, new ArrayList<>());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    public void setAuthorities(List<? extends GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public int getAge() {
        return age;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public static final class Builder {

        private String username;

        private String password;

        private int age;

        private List<Task> taskList;

        private List<? extends GrantedAuthority> grantedAuthorities;

        private boolean accountNonExpired;

        private boolean accountNonLocked;

        private boolean credentialsNonExpired;

        private boolean enabled;

        public Builder(String username, String password, List<? extends GrantedAuthority> grantedAuthorities) {
            this.username = username;
            this.password = password;
            this.grantedAuthorities = grantedAuthorities;
            this.accountNonExpired = true;
            this.accountNonLocked = true;
            this.credentialsNonExpired = true;
            this.enabled = true;
        }

        public Builder(String username, String password) {
            this(username, password, new ArrayList<>());
        }

        public Builder age(int value) {
            this.age = value;
            return this;
        }

        public Builder todoList(List<Task> value) {
            this.taskList = value;
            return this;
        }

        public Builder authorities(List<? extends GrantedAuthority> value) {
            this.grantedAuthorities = value;
            return this;
        }

        public Builder accountNonExpired(boolean value) {
            this.accountNonExpired = value;
            return this;
        }

        public Builder accountNonLocked(boolean value) {
            this.accountNonLocked = value;
            return this;
        }

        public Builder credentialsNonExpired(boolean value) {
            this.credentialsNonExpired = value;
            return this;
        }

        public Builder enabled(boolean value) {
            this.enabled = value;
            return this;
        }

        public User build() {
            return new User(this);
        }


    }
}
