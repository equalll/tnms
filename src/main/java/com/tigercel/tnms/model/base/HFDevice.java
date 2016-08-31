package com.tigercel.tnms.model.base;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by somebody on 2016/8/1.
 */
@MappedSuperclass
@Data
public abstract class HFDevice extends BaseModel {

    @Column(nullable = false, length = 64)
    private String udid;

    @Column(length = 64)
    private String owner;

    private Boolean shared = false;

    private Boolean online = false;

    @Column(length = 128)
    private String token;

    @Column(length = 256)
    private String description;
}
