package com.tigercel.tnms.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by somebody on 2016/6/3.
 */
@Data
public class TemplateWifiBean {

    private Long id = new Long(0);

    @Length(min = 1, max = 32)
    private String templateName;

    @Length(max = 256)
    private String templateDescription;


    /* basic */
    @NotNull
    private int wifi = 0;

    @NotBlank
    @Length(min = 1, max = 64)
    private String ssid;

    @NotNull
    private int channel;

    private int maxStation;

    /* security */
    @NotBlank
    private String encryption;

    private String cipher;          //aes/tkip/aes+tkip

    @Length(min = 1, max = 64)
    private String key;


}
