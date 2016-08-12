package com.tigercel.tnms.model.router.support;

import com.tigercel.tnms.model.base.BaseModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by somebody on 2016/8/1.
 */
@MappedSuperclass
@Data
public abstract class HFDevRouterWan extends BaseModel {

    @Column(nullable = false)
    private String protocol;

    /* static */
    @Column(length = 16)
    private String address;

    @Column(length = 16)
    private String netmask;

    @Column(length = 16)
    private String gateway;

    /* pppoe */
    @Column(length = 64)
    private String username;

    @Column(length = 64)
    private String password;

    /* 3/4G */
    @Column(length = 32)
    private String modemDevice;

    @Column(length = 32)
    private String serviceType;

    @Column(length = 32)
    private String APN;

    @Column(length = 32)
    private String dialNumber;

}