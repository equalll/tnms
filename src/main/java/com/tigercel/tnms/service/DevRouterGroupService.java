package com.tigercel.tnms.service;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tigercel.tnms.dto.PageBean;
import com.tigercel.tnms.dto.RouterGroupBean;
import com.tigercel.tnms.model.router.HFDevRouterGroup;
import com.tigercel.tnms.model.router.setup.HFDevRouterLanSetup;
import com.tigercel.tnms.model.router.setup.HFDevRouterOtaSetup;
import com.tigercel.tnms.model.router.setup.HFDevRouterWanSetup;
import com.tigercel.tnms.model.router.setup.HFDevRouterWiFiSetup;
import com.tigercel.tnms.model.router.template.HFDevRouterLanTmp;
import com.tigercel.tnms.model.router.template.HFDevRouterOtaTmp;
import com.tigercel.tnms.model.router.template.HFDevRouterWanTmp;
import com.tigercel.tnms.model.router.template.HFDevRouterWiFiTmp;
import com.tigercel.tnms.repository.DevRouterGroupRepository;
import com.tigercel.tnms.service.support.MqttMsgSender;
import com.tigercel.tnms.service.support.NMSJsonBuilder;
import com.tigercel.tnms.service.template.DevRouterLANService;
import com.tigercel.tnms.service.template.DevRouterOTAService;
import com.tigercel.tnms.service.template.DevRouterWANService;
import com.tigercel.tnms.service.template.DevRouterWiFiService;
import com.tigercel.tnms.utils.DTOUtil;
import com.tigercel.tnms.utils.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by freedom on 2016/5/25.
 */
@Service
public class DevRouterGroupService {

    @Autowired
    private DevRouterGroupRepository deviceRouterGroupRepository;

    @Autowired
    private DevRouterWANService deviceRouterWANService;

    @Autowired
    private DevRouterLANService deviceRouterLANService;

    @Autowired
    private DevRouterWiFiService deviceRouterWiFiService;

    @Autowired
    private DevRouterOTAService deviceRouterOTAService;

    @Autowired
    private MqttMsgSender mqttMsgSender;


    public HFDevRouterGroup findOneByName(String name) {
        return deviceRouterGroupRepository.findOneByName(name);
    }

    public HFDevRouterGroup save(RouterGroupBean g) {
        HFDevRouterWanTmp w = deviceRouterWANService.findOneByTemplateName(g.getGroupWanName());
        HFDevRouterLanTmp l = deviceRouterLANService.findOneByTemplateName(g.getGroupLanName());
        HFDevRouterWiFiTmp wi = deviceRouterWiFiService.findOneByTemplateName(g.getGroupWifiName());
        HFDevRouterOtaTmp ota = deviceRouterOTAService.findOneByTemplateName(g.getGroupOTAName());
        HFDevRouterGroup group = new HFDevRouterGroup();

        group.setName(g.getName());
        group.setDescription(g.getDescription());
        group.setWan(w);
        group.setLan(l);
        group.setWifi(wi);
        group.setOta(ota);
        group.setGroupId(IDGenerator.getID());

        return deviceRouterGroupRepository.save(group);
    }

    public Page<HFDevRouterGroup> findAllByName(PageBean pb, String name) {
        return deviceRouterGroupRepository.findAllByName(name,
                new PageRequest(pb.getPage() - 1,
                        pb.getSize(),
                        Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                        pb.getSortName()
                )
        );
    }

    public Page<HFDevRouterGroup> findAll(PageBean pb) {
        return deviceRouterGroupRepository.findAll(
                new PageRequest(pb.getPage() - 1,
                        pb.getSize(),
                        Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                        pb.getSortName()
                )
        );
    }

    public HFDevRouterGroup findOne(long id){
        return deviceRouterGroupRepository.findOne(id);
    }

    public HFDevRouterGroup update(HFDevRouterGroup group, String wan, String lan, String wifi, String ota) {

        if(group.getWan() == null || group.getWan().getTemplateName().equals(wan) == false) {
            group.setWan(deviceRouterWANService.findOneByTemplateName(wan));
        }

        if(group.getLan() == null || group.getLan().getTemplateName().equals(lan) == false) {
            group.setLan(deviceRouterLANService.findOneByTemplateName(lan));
        }

        if(group.getWifi() == null || group.getWifi().getTemplateName().equals(wifi) == false) {
            group.setWifi(deviceRouterWiFiService.findOneByTemplateName(wifi));
        }

        if(group.getOta() == null || group.getOta().getTemplateName().equals(ota) == false) {
            group.setOta(deviceRouterOTAService.findOneByTemplateName(ota));
        }

        return deviceRouterGroupRepository.save(group);
    }

    public void delete(Long id) {
        deviceRouterGroupRepository.delete(id);
    }


    public void effect(HFDevRouterGroup group, String scope) {
        ObjectNode node;
        String[]    sc = scope.split(",");
        int         count = 0;


        if(NMSJsonBuilder.contains(sc, "all") >= 0) {   /* topic - all */
            node = NMSJsonBuilder.AllJsonBuilder(group);
            mqttMsgSender.MqttMsgRouterGroupSetup(group.getGroupId(), node.toString());
        }
        else if(sc.length > 1) {                        /* topic - all */
            node = NMSJsonBuilder.AllJsonBuilder(sc, group);
            mqttMsgSender.MqttMsgRouterGroupSetup(group.getGroupId(), node.toString());
        }
        else if(sc[0].equalsIgnoreCase("wan")) {        /* topic - wan */
            node = NMSJsonBuilder.WANJsonBuilder(DTOUtil.map(group.getWan(), HFDevRouterWanSetup.class));
            mqttMsgSender.MqttMsgRouterGroupSetup(group.getGroupId(), node.toString());
        }
        else if(sc[0].equalsIgnoreCase("lan")) {        /* topic - lan */
            node = NMSJsonBuilder.LANJsonBuilder(DTOUtil.map(group.getLan(), HFDevRouterLanSetup.class));
            mqttMsgSender.MqttMsgRouterGroupSetup(group.getGroupId(), node.toString());
        }
        else if(sc[0].equalsIgnoreCase("wifi")) {       /* topic - wifi */
            node = NMSJsonBuilder.WiFiJsonBuilder(DTOUtil.map(group.getWifi(), HFDevRouterWiFiSetup.class));
            mqttMsgSender.MqttMsgRouterGroupSetup(group.getGroupId(), node.toString());
        }
        else if(sc[0].equalsIgnoreCase("ota")) {       /* topic - ota */
            node = NMSJsonBuilder.OTAJsonBuilder(DTOUtil.map(group.getOta(), HFDevRouterOtaSetup.class));
            mqttMsgSender.MqttMsgRouterGroupSetup(group.getGroupId(), node.toString());
        }
    }
}
