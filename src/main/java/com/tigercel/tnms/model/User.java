package com.tigercel.tnms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tigercel.tnms.model.base.BaseModel;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by freedom on 2016/3/29.
 */
@Entity
@Data
@Table(name = "t_user")
@JsonIgnoreProperties(value = {"password"})
public class User extends BaseModel {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER  = "ROLE_USER";

    @Column(nullable = false, unique=true, length = 32)
    private String username;

    @Column(nullable = false, length = 256)
    private String password;

    @Column(nullable = false)
    private String role = ROLE_USER;

    @Column(length = 256)
    private String description;


    public User() {

    }

    public User(String name, String password, String role) {
        this.username = name;
        this.password = password;
        this.role = role;
    }

}