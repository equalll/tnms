package com.tigercel.tnms.service.template;

import com.tigercel.tnms.dto.PageBean;
import com.tigercel.tnms.model.router.template.HFDevRouterWanTmp;
import com.tigercel.tnms.repository.template.DevRouterWANRepository;
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
public class DevRouterWANService {

    @Autowired
    private DevRouterWANRepository deviceRouterWANRepository;

    public HFDevRouterWanTmp findOneByTemplateName(String templateName) {

        return deviceRouterWANRepository.findOneByTemplateName(templateName);
    }

    public HFDevRouterWanTmp save(HFDevRouterWanTmp wan) {

        return deviceRouterWANRepository.save(wan);
    }

    public Page<HFDevRouterWanTmp> findAll(PageBean pb) {
        return deviceRouterWANRepository.findAll(
                new PageRequest(pb.getPage() - 1,
                        pb.getSize(),
                        Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                        pb.getSortName()
                )
        );
    }

    public Page<HFDevRouterWanTmp> findAllByTemplateName(PageBean pb, String name) {
        return deviceRouterWANRepository.findAllByTemplateName(name,
                new PageRequest(pb.getPage() - 1,
                        pb.getSize(),
                        Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                        pb.getSortName()
                )
        );
    }


    public HFDevRouterWanTmp findOne(Long id) {
        return deviceRouterWANRepository.findOne(id);
    }

    public void delete(Long id) {
        deviceRouterWANRepository.delete(id);
    }

    public List<HFDevRouterWanTmp> findAll() {
        return deviceRouterWANRepository.findAll();
    }

    public long count() {
        return deviceRouterWANRepository.count();
    }
}
