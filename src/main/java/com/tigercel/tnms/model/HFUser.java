package com.tigercel.tnms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tigercel.tnms.model.base.BaseModel;
import com.tigercel.tnms.model.router.HFDevRouter;
import com.tigercel.tnms.model.support.Gender;
import com.tigercel.tnms.model.support.UserRole;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by somebody on 2016/8/1.
 */
@Entity
@Data
@Table(name = "hf_user")
@JsonIgnoreProperties(value = {"password"})
public class HFUser extends BaseModel {

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role = UserRole.ROLE_USER;

    @Column(nullable = false, unique = true,length = 64)
    private String name;

    @Column(nullable = false, length = 256)
    private String password;

    @Column(nullable = false, length = 128)
    private String token;

    @Column(nullable = false, length = 64)
    private String username;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Gender userGender = Gender.FEMALE;

    @Column(nullable = false, length = 32)
    private String userCell;

    @Column(length = 64)
    private String userVid;

    @Column(length = 256)
    private String userAddr;

    @Column(length = 256)
    private String description;


    public HFUser(String name, String password, UserRole role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public HFUser() {

    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private Collection<HFDevRouter> routers = new ArrayList<>();
}