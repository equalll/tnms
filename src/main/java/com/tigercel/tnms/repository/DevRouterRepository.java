package com.tigercel.tnms.repository;

import com.tigercel.tnms.model.router.HFDevRouter;
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
public interface DevRouterRepository extends JpaRepository<HFDevRouter, Long> {

    Page<HFDevRouter> findByUdid(String mac, Pageable pageable);
    HFDevRouter findOneByUdid(String mac);
}
