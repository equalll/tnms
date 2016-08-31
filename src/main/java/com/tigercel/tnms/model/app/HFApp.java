package com.tigercel.tnms.model.app;

import com.tigercel.tnms.model.base.BaseModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by somebody on 2016/8/12.
 */
@Entity
@Data
@Table(name = "t_app")
public class HFApp extends BaseModel {

    @Column(nullable = false, unique=true, length = 32)
    private String appName;

    @Column(nullable = false, length = 32)
    private String version;

    @Column(nullable = false, length=64)
    private String filename;

    @Column(nullable = false, length=32)
    private String md5;

    @Column(nullable = false, length=128)
    private String url;

}
