package com.tigercel.tnms.repository.template;

import com.tigercel.tnms.model.router.template.HFDevRouterFirewallTmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by freedom on 2016/5/23.
 */
@Repository
@Transactional
public interface DevRouterFirewallRepository extends JpaRepository<HFDevRouterFirewallTmp, Long> {
    HFDevRouterFirewallTmp findOneByTemplateName (String templateName);
}
