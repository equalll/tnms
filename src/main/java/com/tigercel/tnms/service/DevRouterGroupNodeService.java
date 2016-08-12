package com.tigercel.tnms.service;

import com.tigercel.tnms.dto.PageBean;
import com.tigercel.tnms.dto.RouterGroupNodeBean;
import com.tigercel.tnms.model.router.HFDevRouterGroup;
import com.tigercel.tnms.model.router.HFDevRouterNode;
import com.tigercel.tnms.repository.DevRouterGroupNodeRepository;
import com.tigercel.tnms.utils.DTOUtil;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.tigercel.tnms.service.support.CacheNameSettings.CACHE_NAME_AP_NODE;

/**
 * Created by freedom on 2016/5/25.
 */
@Service
public class DevRouterGroupNodeService {

    @Autowired
    private DevRouterGroupNodeRepository deviceRouterGroupNodeRepository;

    @Autowired
    private DevRouterGroupService deviceRouterGroupService;


    public Boolean isMACValid(String start, String end) {
        Page<HFDevRouterNode> nodes = deviceRouterGroupNodeRepository.isMACValid(
                start,
                end,
                new PageRequest(0, 1)
        );

        if(nodes != null && nodes.getContent().isEmpty() == false) {
            return false;
        }

        return true;

    }

    @CacheEvict(value = CACHE_NAME_AP_NODE, allEntries = true)
    public HFDevRouterNode save(RouterGroupNodeBean n, String groupName) {
        HFDevRouterGroup group = deviceRouterGroupService.findOneByName(groupName);
        HFDevRouterNode node = null;

        if(group == null) {
            return null;
        }
        else {
            node = new HFDevRouterNode();
            DTOUtil.mapTo(n, node);
            node.setGroup(group);
        }

        return deviceRouterGroupNodeRepository.save(node);
    }


    public Page<HFDevRouterNode> findAll(PageBean pb) {
        return deviceRouterGroupNodeRepository.findAll(
                new PageRequest(pb.getPage() - 1,
                        pb.getSize(),
                        Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                        pb.getSortName()
                ));
    }

    public HFDevRouterNode findAllByMac(String mac) {
        return deviceRouterGroupNodeRepository.findNodeByMac(mac);
    }

    @CacheEvict(value = CACHE_NAME_AP_NODE, allEntries = true)
    public void delete(long id) {
        deviceRouterGroupNodeRepository.delete(id);
    }

    @Cacheable(value = CACHE_NAME_AP_NODE, key = "#mac")
    public HFDevRouterGroup findGroupByMAC(String mac) {

        HFDevRouterNode node = deviceRouterGroupNodeRepository.findNodeByMac(mac);

        if(node == null) {
            return null;
        }

        Hibernate.initialize(node.getGroup());
        return node.getGroup();
    }

    public Page<HFDevRouterNode> findAllByGroup(PageBean pb, HFDevRouterGroup group) {
        return deviceRouterGroupNodeRepository.findAllByGroup(group,
                new PageRequest(pb.getPage() - 1,
                        pb.getSize(),
                        Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                        pb.getSortName()
                ));
    }
}
