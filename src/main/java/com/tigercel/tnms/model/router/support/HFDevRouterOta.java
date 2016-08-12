package com.tigercel.tnms.model.router.support;

import com.tigercel.tnms.model.base.BaseModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by somebody on 2016/8/1.
 */
@MappedSuperclass
@Data
public abstract class HFDevRouterOta extends BaseModel {

    @Column(nullable = false)
    private int mode;

    @Column(length = 128, nullable = false)
    private String server;

    @Column(nullable = false)
    private int pridDelay;

    @Column(nullable = false)
    private int restoreFlag;

    private int windowStart;

    private int windowSize;
}

