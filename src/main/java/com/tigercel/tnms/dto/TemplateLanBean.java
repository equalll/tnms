package com.tigercel.tnms.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * Created by somebody on 2016/6/3.
 */
@Data
public class TemplateLanBean {

    private Long id = new Long(0);

    @Length(min = 1, max = 32)
    private String templateName;

    @Length(max = 256)
    private String templateDescription;

    @NotBlank
    @Pattern(regexp = "^((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)$",
            message="Invalid ip address")
    private String lanAddress;

    @NotBlank
    @Length(min = 7, max = 16)
    private String lanNetmask;

    private int dhcp;

    private int start;

    private int limit;

    private String leaseTime;
}
