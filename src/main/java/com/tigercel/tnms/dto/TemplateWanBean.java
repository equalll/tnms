package com.tigercel.tnms.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * Created by somebody on 2016/6/3.
 */
@Data
public class TemplateWanBean {

    private Long id = new Long(0);


    @Length(min = 1, max = 32)
    private String templateName;

    @Length(max = 256)
    private String templateDescription;

    @NotBlank
    private String protocol;

    /* static */
    @Pattern(regexp = "^((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)$",
            message = "Invalid ip address")
    private String address;

    @Length(min = 7, max = 16)
    private String netmask;

    @Length(min = 7, max = 16)
    private String gateway;

    /* pppoe */
    @Length(min = 1, max = 64)
    private String username;

    @Length(min = 1, max = 64)
    private String password;

    /* 3/4G */
    @Length(min = 1, max = 32)
    private String modemDevice;

    @Length(min = 1, max = 32)
    private String serviceType;

    @Length(min = 1, max = 32)
    private String APN;

    @Length(min = 1, max = 32)
    private String dialNumber;
}
