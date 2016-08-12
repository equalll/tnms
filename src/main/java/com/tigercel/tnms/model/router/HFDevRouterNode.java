package com.tigercel.tnms.model.router;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tigercel.tnms.model.base.BaseModel;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by somebody on 2016/8/1.
 */
@Entity
@Data
@Table(name = "hf_router_node")
@JsonIgnoreProperties(ignoreUnknown = true, value={"group"})
public class HFDevRouterNode extends BaseModel {

    @Column(nullable = false, unique=true, length=18)
    private String start;

    @Column(nullable = false, unique=true, length=18)
    private String end;

    @Column(length=128)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private HFDevRouterGroup group;

    @Transient
    private String groupName;
}
