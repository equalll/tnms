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
public abstract class HFDevRouterWiFi extends BaseModel {

    /* basic */
    @Column(name = "wifiEnable")
    private int wifi;

    @Column(name = "wifiSSID", length = 64)
    private String ssid;

    @Column(name = "wifiChannel")
    private int channel;

    @Column(name = "wifiMaxStation")
    private int maxStation;

    /* security */
    @Column(name = "wifiEncryption", length = 16)
    private String encryption;

    @Column(name = "wifiCipher", length = 16)
    private String cipher;          //aes/tkip/aes+tkip

    @Column(name = "wifiKey", length = 64)
    private String key;

}