package com.tigercel.tnms.model.router;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tigercel.tnms.model.base.BaseModel;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Data
@Table(name = "hf_router_status")
@JsonIgnoreProperties(ignoreUnknown = true, value={"hibernateLazyInitializer", "handler"})
public class HFDevRouterStatus extends BaseModel {


    /*
    {
        "productName": "Neutral version",
        "firmwareVersion": "Alpha.657f0e4_V",
        "hardwareVersion": "WG116",
        "kernelVersion": "3.10.49",
        "OSVersion": "#8 Thu Apr 7 16:47:57 CST 2016"
    }
     */
    private String version;


    /*
    {
        "uptime": "0h 3m 29s",
        "loadAverage": "0.04, 0.10, 0.05",
        "memoryTotal": "60580 kB",
        "memoryFree": "31548 kB",
        "memoryCached": "9792 kB",
        "memoryBuffered": "3096 kB"
    }
     */
    private String system;


    /*
    {
        "protocol": "static",
        "address": "192.168.8.123",
        "netmask": "255.255.255.0",
        "gateway": "192.168.8.1"
    }
     */
    private String wan;


    /*
    {
        "lanAddress": "192.168.100.1",
        "lanNetmask": "255.255.255.0",
        "dhcp": 1,
        "start": 100,
        "limit": 10,
        "leaseTime": "5h"
    }
     */
    private String lan;


    /*
    {
        "wifi": 1,
        "ssid": "mt7620-00CC2222",
        "channel": 9,
        "maxStation": 20,
        "encryption": "none"
    }
     */
    private String wifi;


    /*
    {
        "pridDelay": 10,
        "server": "http://210.22.78.230:8088/ota/",
        "windowStart": 2,
        "windowSize": 2,
        "mode": 0,
        "restoreFlag": 1
    }
     */
    private String ota;


    /*
    [
        {
            "ip": "192.168.100.123",
            "mac": "11:11:11:11:11:11"
        },
        {
            "ip": "192.168.100.122",
            "mac": "11:11:11:11:12:11"
        }
    ]
     */
    private String client;


    @OneToOne(fetch = FetchType.LAZY, mappedBy = "status")
    private HFDevRouter router;

}