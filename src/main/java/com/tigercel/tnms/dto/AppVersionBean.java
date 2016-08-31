package com.tigercel.tnms.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by somebody on 2016/8/12.
 */
@Data
public class AppVersionBean {

    private Long id = new Long(0);

    @NotNull
    @Length(min = 1, max = 32)
    private String appName;

    @NotNull
    @Length(min = 1, max = 32)
    private String version;

}
