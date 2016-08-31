package com.tigercel.tnms.model.router;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tigercel.tnms.model.base.BaseModel;
import com.tigercel.tnms.model.router.template.*;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by somebody on 2016/8/1.
 */
@Entity
@Data
@Table(name = "hf_router_group")
@JsonIgnoreProperties(value = {"nodes", "routers", "wan", "lan", "wifi", "ota", "firewall", "qos"},
        ignoreUnknown = true)
public class HFDevRouterGroup extends BaseModel {

    @Column(nullable = false, unique=true, length = 32)
    private String groupName;

    @Column(nullable = false, unique=true, length = 128)
    private String groupId;


    @Column(length=16)
    private String productModel;

    @Column(length=16)
    private String firmwareVersion;

    @Column(length=64)
    private String firmwareFileName;

    @Column(length=32)
    private String firmwareMd5;

    @Column(length=128)
    private String firmwareUrl;

    @Column(length = 256)
    private String description;



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = CascadeType.REMOVE)
    private Collection<HFDevRouterNode> nodes = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = CascadeType.REMOVE)
    private Collection<HFDevRouter> routers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private HFDevRouterWanTmp wan;

    @ManyToOne(fetch = FetchType.LAZY)
    private HFDevRouterLanTmp lan;

    @ManyToOne(fetch = FetchType.LAZY)
    private HFDevRouterWiFiTmp wifi;

    @ManyToOne(fetch = FetchType.LAZY)
    private HFDevRouterOtaTmp ota;

    @ManyToOne(fetch = FetchType.LAZY)
    private HFDevRouterFirewallTmp firewall;

    @ManyToOne(fetch = FetchType.LAZY)
    private HFDevRouterQosTmp qos;


    @Transient
    private String groupWanName;

    @Transient
    private String groupLanName;

    @Transient
    private String groupWifiName;

    @Transient
    private String groupOTAName;

    @Transient
    private String groupFirewallName;

    @Transient
    private String groupQosName;
}
