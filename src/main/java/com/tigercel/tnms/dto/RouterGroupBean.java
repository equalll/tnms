package com.tigercel.tnms.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by somebody on 2016/6/3.
 */
@Data
public class RouterGroupBean {

    private Long id = new Long(0);

    @NotNull
    @Length(min = 1, max = 32)
    private String name;

    @Length(max = 256)
    private String description;

    @Length(min = 1, max = 32)
    private String groupWanName;

    @Length(min = 1, max = 32)
    private String groupLanName;

    @Length(min = 1, max = 32)
    private String groupWifiName;

    @Length(min = 1, max = 32)
    private String groupOTAName;

    @Length(min = 1, max = 32)
    private String groupFirewallName;

    @Length(min = 1, max = 32)
    private String groupQosName;
}
