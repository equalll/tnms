package com.tigercel.tnms.service.template;

import com.tigercel.tnms.dto.PageBean;
import com.tigercel.tnms.model.router.template.HFDevRouterWiFiTmp;
import com.tigercel.tnms.repository.template.DevRouterWiFiRepository;
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
public class DevRouterWiFiService {
    @Autowired
    private DevRouterWiFiRepository deviceRouterWiFiRepository;

    public HFDevRouterWiFiTmp findOneByTemplateName(String templateName) {

        return deviceRouterWiFiRepository.findOneByTemplateName(templateName);
    }

    public HFDevRouterWiFiTmp save(HFDevRouterWiFiTmp wifi) {

        return deviceRouterWiFiRepository.save(wifi);
    }

    public Page<HFDevRouterWiFiTmp> findAll(PageBean pb) {
        return deviceRouterWiFiRepository.findAll(
                new PageRequest(pb.getPage() - 1,
                        pb.getSize(),
                        Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                        pb.getSortName()
                )
        );
    }

    public Page<HFDevRouterWiFiTmp> findAllByTemplateName(PageBean pb, String name) {
        return deviceRouterWiFiRepository.findAllByTemplateName(name,
                new PageRequest(pb.getPage() - 1,
                        pb.getSize(),
                        Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                        pb.getSortName()
                )
        );
    }


    public HFDevRouterWiFiTmp findOne(Long id) {
        return deviceRouterWiFiRepository.findOne(id);
    }

    public void delete(Long id) {
        deviceRouterWiFiRepository.delete(id);
    }

    public List<HFDevRouterWiFiTmp> findAll(){
        return deviceRouterWiFiRepository.findAll();
    }

    public long count() {
        return deviceRouterWiFiRepository.count();
    }
}