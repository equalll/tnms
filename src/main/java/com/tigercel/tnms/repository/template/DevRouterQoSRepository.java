package com.tigercel.tnms.repository.template;

import com.tigercel.tnms.model.router.template.HFDevRouterQosTmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by freedom on 2016/5/23.
 */
@Repository
@Transactional
public interface DevRouterQoSRepository extends JpaRepository<HFDevRouterQosTmp, Long> {

    //DeviceRouterTemplateQoS findOneByRouter(DeviceRouter router);
    HFDevRouterQosTmp findOneByTemplateName (String templateName);
}
