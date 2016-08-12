package com.tigercel.tnms.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

/**
 * Created by somebody on 2016/6/17.
 */
@Data
public class TemplateOTABean {

    private Long id = new Long(0);

    @Length(min = 1, max = 32)
    private String templateName;

    @Length(max = 256)
    private String templateDescription;

    @NotNull
    private int mode;

    @NotNull
    @Length(max = 128)
    private String server;

    @NotNull
    private int pridDelay;

    @NotNull
    private int restoreFlag;

    private int windowStart;

    private int windowSize;
}