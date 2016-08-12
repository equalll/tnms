package com.tigercel.tnms.service.template;

import com.tigercel.tnms.dto.PageBean;
import com.tigercel.tnms.model.router.template.HFDevRouterLanTmp;
import com.tigercel.tnms.repository.template.DevRouterLANRepository;
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
public class DevRouterLANService {
    @Autowired
    private DevRouterLANRepository deviceRouterLANRepository;

    public HFDevRouterLanTmp findOneByTemplateName(String templateName) {

        return deviceRouterLANRepository.findOneByTemplateName(templateName);
    }

    public HFDevRouterLanTmp save(HFDevRouterLanTmp lan) {

        return deviceRouterLANRepository.save(lan);
    }

    public Page<HFDevRouterLanTmp> findAll(PageBean pb) {
        return deviceRouterLANRepository.findAll(
                new PageRequest(pb.getPage() - 1,
                        pb.getSize(),
                        Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                        pb.getSortName()
                )
        );
    }

    public Page<HFDevRouterLanTmp> findAllByTemplateName(PageBean pb, String name) {
        return deviceRouterLANRepository.findAllByTemplateName(name,
                new PageRequest(pb.getPage() - 1,
                        pb.getSize(),
                        Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                        pb.getSortName()
                )
        );
    }


    public HFDevRouterLanTmp findOne(Long id) {
        return deviceRouterLANRepository.findOne(id);
    }

    public void delete(Long id) {
        deviceRouterLANRepository.delete(id);
    }

    public List<HFDevRouterLanTmp> findAll() {
        return deviceRouterLANRepository.findAll();
    }

    public long count() {
        return deviceRouterLANRepository.count();
    }
}
