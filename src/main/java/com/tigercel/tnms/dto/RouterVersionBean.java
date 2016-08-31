package com.tigercel.tnms.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;


/**
 * Created by freedom on 2016/3/23.
 */
@Getter
@Setter
public class RouterVersionBean {


    /* id */
    @NotNull
    private String u;

    /* mac */
    @NotNull
    @Pattern(regexp = "^[A-Fa-f0-9]{2}(:[A-Fa-f0-9]{2}){5}$", message = "Invalid MAC Address")
    private String a;

    /* random */
    @NotNull
    private String b;

    /* md5 */
    @NotNull
    @Size(min=32, max=32)
    private String c;

    /* timestamp */
    private Long d;

    @Override
    public String toString() {
        return  "id: " + u +
                "mac: " + a +
                "rnd: " + b +
                "md5: " + c +
                "time: " + d;
    }
}
