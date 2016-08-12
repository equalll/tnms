package com.tigercel.tnms.repository;

import com.tigercel.tnms.model.router.HFDevRouterGroup;
import com.tigercel.tnms.model.router.HFDevRouterNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by freedom on 2016/5/23.
 */
@Repository
@Transactional
public interface DevRouterGroupNodeRepository extends JpaRepository<HFDevRouterNode, Long> {

    Page<HFDevRouterNode> findAllByGroup(HFDevRouterGroup group, Pageable pageRequest);

    @Query("select o from HFDevRouterNode o where (o.start <= :mac and o.end >= :mac)")
    HFDevRouterNode findNodeByMac(@Param("mac") String mac);

    @Query( "select o from HFDevRouterNode o where (" +
            "(o.start <= :a and :a <= o.end) or " +
            "(o.start <= :b and :b <= o.end) or " +
            "(o.start >= :a and :b >= o.end)) ")
    Page<HFDevRouterNode> isMACValid(@Param("a") String a, @Param("b") String b, Pageable pageRequest);

}