package com.tigercel.tnms.repository;

import com.tigercel.tnms.model.router.HFDevRouter;
import com.tigercel.tnms.model.router.HFDevRouterStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by freedom on 2016/5/23.
 */
public interface DevRouterStatusRepository extends JpaRepository<HFDevRouterStatus, Long> {

    HFDevRouterStatus findOneByRouter(HFDevRouter router);
}
