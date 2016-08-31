package com.tigercel.tnms.repository.user;

import com.tigercel.tnms.model.HFUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by somebody on 2016/8/2.
 */
@Repository
@Transactional
public interface HFUserRepository extends JpaRepository<HFUser, Long> {
    HFUser findByName(String name);
    Page<HFUser> findAllByName(String name, Pageable pageable);
}
