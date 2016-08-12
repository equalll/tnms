package com.tigercel.tnms.model.router.setup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tigercel.tnms.model.router.HFDevRouter;
import com.tigercel.tnms.model.router.support.HFDevRouterOta;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by somebody on 2016/8/1.
 */
@Entity
@Data
@Table(name = "hf_router_ota")
@JsonIgnoreProperties(ignoreUnknown = true, value={"router", "hibernateLazyInitializer", "handler"})
public class HFDevRouterOtaSetup extends HFDevRouterOta {

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "ota")
    private HFDevRouter router;
}
