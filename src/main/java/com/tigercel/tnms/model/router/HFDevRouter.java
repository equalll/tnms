package com.tigercel.tnms.model.router;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tigercel.tnms.model.HFUser;
import com.tigercel.tnms.model.base.HFDevice;
import com.tigercel.tnms.model.router.setup.*;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by somebody on 2016/8/1.
 */
@Entity
@Data
@Table(name = "hf_router")
@JsonIgnoreProperties(value = {"status", "users", "wan", "lan", "wifi", "ota", "firewall", "qos", "group"},
        ignoreUnknown = true)
public class HFDevRouter extends HFDevice {

    @Column(length = 18)
    private String ip;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private HFDevRouterStatus status;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "hf_user_router",
            joinColumns = {@JoinColumn(name = "router_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName ="id")})
    private Collection<HFUser> users = new ArrayList<>();


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private HFDevRouterWanSetup wan;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private HFDevRouterLanSetup lan;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private HFDevRouterWiFiSetup wifi;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private HFDevRouterOtaSetup ota;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private HFDevRouterFirewallSetup firewall;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private HFDevRouterQosSetup qos;

    @ManyToOne(fetch = FetchType.LAZY)
    private HFDevRouterGroup group;

}