package com.tigercel.tnms.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * Created by somebody on 2016/6/3.
 */
@Data
public class RouterGroupNodeBean {

    private Long id = new Long(0);

    @NotBlank
    @Pattern(regexp = "^[A-Fa-f0-9]{2}(:[A-Fa-f0-9]{2}){5}$", message = "Invalid MAC Address")
    private String start;

    @NotBlank
    @Pattern(regexp = "^[A-Fa-f0-9]{2}(:[A-Fa-f0-9]{2}){5}$", message = "Invalid MAC Address")
    private String end;

    @Length(max = 256)
    private String description;
}