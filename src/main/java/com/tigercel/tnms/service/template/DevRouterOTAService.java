package com.tigercel.tnms.service.template;

import com.tigercel.tnms.dto.PageBean;
import com.tigercel.tnms.model.router.template.HFDevRouterOtaTmp;
import com.tigercel.tnms.repository.template.DevRouterOTARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by freedom on 2016/5/24.
 */
@Service
public class DevRouterOTAService {

    @Autowired
    private DevRouterOTARepository deviceRouterOTARepository;

    public Long count() {
        return deviceRouterOTARepository.count();
    }

    public HFDevRouterOtaTmp findOneByTemplateName(String name) {
        return deviceRouterOTARepository.findOneByTemplateName(name);
    }

    public HFDevRouterOtaTmp save(HFDevRouterOtaTmp ota) {
        return deviceRouterOTARepository.save(ota);
    }

    public Page<HFDevRouterOtaTmp> findAllByTemplateName(PageBean pb, String name) {
        return deviceRouterOTARepository.findAllByTemplateName(name,
                new PageRequest(pb.getPage() - 1,
                        pb.getSize(),
                        Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                        pb.getSortName()
                )
        );
    }

    public Page<HFDevRouterOtaTmp> findAll(PageBean pb) {
        return deviceRouterOTARepository.findAll(
                new PageRequest(pb.getPage() - 1,
                        pb.getSize(),
                        Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                        pb.getSortName()
                )
        );
    }

    public HFDevRouterOtaTmp findOne(Long id) {
        return deviceRouterOTARepository.findOne(id);
    }

    public void delete(Long id) {
        deviceRouterOTARepository.delete(id);
    }

    public List<HFDevRouterOtaTmp> findAll() {
        return deviceRouterOTARepository.findAll();
    }
}
