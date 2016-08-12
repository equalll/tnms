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
public abstract class HFDevRouterLan extends BaseModel {

    @Column(length = 16)
    private String lanAddress;

    @Column(length = 16)
    private String lanNetmask;

    @Column(name = "lanDhcp")
    private int dhcp;

    @Column(name = "lanStart")
    private int start;

    @Column(name = "lanLimit")
    private int limit;

    @Column(length = 16)
    private String leaseTime;

}
