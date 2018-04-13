package com.pycogroup.taotran.task.management.core.entity;

import com.pycogroup.taotran.task.management.core.custom.cascade.CascadeSave;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
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


@Document(collection = "user")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "user", type = "user", shards = 1, replicas = 0, refreshInterval = "-1")
public class User extends AbstractDocument implements UserDetails {

    @Field
    @NotNull
    @NotBlank
    @Indexed(unique = true)
//    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.String)
    private String username;

    @Field
    @Length(min = 8, max = 100)
    @Valid
    private String password;

    @Field
    @Min(10)
    @Max(100)
    private int age;

    @DBRef
    @CascadeSave
    private List<Task> taskList;

    @Field
    private List<? extends GrantedAuthority> grantedAuthorities;

    @Field
    private boolean accountNonExpired;

    @Field
    private boolean accountNonLocked;

    @Field
    private boolean credentialsNonExpired;

    @Field
    private boolean enabled;


    private int index;

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

    public void setGrantedAuthorities(List<GrantedAuthority> grantedAuthorities) {
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private boolean refresh;

    public boolean isRefresh() {
        return refresh;
    }

    public void setRefresh(boolean refresh) {
        this.refresh = refresh;
    }

    private boolean searchSimilar;

    public boolean isSearchSimilar() {
        return searchSimilar;
    }

    public void setSearchSimilar(boolean searchSimilar) {
        this.searchSimilar = searchSimilar;
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

        public Builder taskList(List<Task> value) {
            this.taskList = value;
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
