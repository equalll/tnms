package com.tigercel.tnms.repository.template;

import com.tigercel.tnms.model.router.template.HFDevRouterOtaTmp;
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
public interface DevRouterOTARepository extends JpaRepository<HFDevRouterOtaTmp, Long> {

    //DeviceRouterTemplateOTA findOneByRouter(DeviceRouter router);
    HFDevRouterOtaTmp findOneByTemplateName (String templateName);
    Page<HFDevRouterOtaTmp> findAllByTemplateName(String name, Pageable pageable);
}
