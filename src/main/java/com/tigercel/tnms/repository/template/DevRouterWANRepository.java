package com.tigercel.tnms.repository.template;

import com.tigercel.tnms.model.router.template.HFDevRouterWanTmp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by freedom on 2016/5/23.
 */
@Repository
@Transactional
public interface DevRouterWANRepository extends JpaRepository<HFDevRouterWanTmp, Long> {

    //DeviceRouterTemplateWAN findOneByRouter(DeviceRouter router);
    HFDevRouterWanTmp findOneByTemplateName (String templateName);

    Page<HFDevRouterWanTmp> findAllByTemplateName(String name, Pageable pageable);
}
