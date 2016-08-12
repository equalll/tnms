package com.tigercel.tnms.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * Created by somebody on 2016/6/23.
 */
public class DataValidationUtil {

    public static Boolean isMACValid(String mac) {

        if(StringUtils.isEmpty(mac)) {
            return false;
        }

        String pattern="^[A-Fa-f0-9]{2}(:[A-Fa-f0-9]{2}){5}$";

        return Pattern.compile(pattern).matcher(mac).find();
    }
}
