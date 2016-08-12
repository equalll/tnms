package com.tigercel.tnms.model.support;

/**
 * Created by somebody on 2016/8/12.
 */
public enum Gender {

    MALE("male"),
    FEMALE("female");

    private String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String role) {
        this.gender = role;
    }

    public String getId(){
        return name();
    }

    @Override
    public String toString() {
        return gender;
    }
}