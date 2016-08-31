package com.tigercel.tnms.service.support;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by freedom on 2016/4/13.
 */
@Service
public class SViewHelper {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM dd, yyyy");
    private static final SimpleDateFormat DATE_FORMAT_MONTH_DAY = new SimpleDateFormat("MMM dd");

    @Value("${tigercel.app.name}")
    private String appName;

    @Value("${tigercel.app.version}")
    private String appVersion;

    private String path;
    private String username;
    private long startTime;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    private String language;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getResponseTime(){
        return System.currentTimeMillis() - startTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getFormattedDate(Date date){
        return date == null ? "" : DATE_FORMAT.format(date);
    }

    public String getFormattedDate(Long timestamp) {
        return DATE_FORMAT.format(new Date(timestamp));
    }

    public String getFormattedSize(Long length) {
        DecimalFormat df = new DecimalFormat("#.00");
        String size = "";

        if(length < 1024) {
            size = df.format((double) length) + " B";
        }
        else if (length < 1024 * 1024) {
            size = df.format((double) length / 1024) + " KB";
        }
        else if (length < 1024 * 1024 * 1024) {
            size = df.format((double) length / 1024 / 1024) + " MB";
        }
        else {
            size = df.format((double) length / 1024 / 1024 /1024) + " GB";
        }

        return size;
    }

    public String getMonthAndDay(Date date){
        return date == null ? "" : DATE_FORMAT_MONTH_DAY.format(date);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        username = name;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppVersion() {
        return appVersion;
    }



}