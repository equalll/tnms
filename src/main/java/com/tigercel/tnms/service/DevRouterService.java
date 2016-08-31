package com.tigercel.tnms.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tigercel.tnms.dto.*;
import com.tigercel.tnms.model.router.HFDevRouter;
import com.tigercel.tnms.model.router.setup.HFDevRouterLanSetup;
import com.tigercel.tnms.model.router.setup.HFDevRouterOtaSetup;
import com.tigercel.tnms.model.router.setup.HFDevRouterWanSetup;
import com.tigercel.tnms.model.router.setup.HFDevRouterWiFiSetup;
import com.tigercel.tnms.repository.DevRouterRepository;
import com.tigercel.tnms.service.support.MqttMsgSender;
import com.tigercel.tnms.service.support.TNMSJsonBodyBuilder;
import com.tigercel.tnms.service.support.TNMSJsonBuilder;
import com.tigercel.tnms.utils.ClutterUtils;
import com.tigercel.tnms.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.tigercel.tnms.service.support.CacheNameSettings.CACHE_NAME_AP;


/**
 * Created by freedom on 2016/5/23.
 */
@Service
public class DevRouterService {

    @Autowired
    private DevRouterRepository routerRepository;
    /*
        @Autowired
        private DeviceRouterWANRepository deviceRouterWANRepository;

        @Autowired
        private DeviceRouterLANRepository deviceRouterLANRepository;

        @Autowired
        private DeviceRouterWiFiRepository deviceRouterWiFiRepository;
    */
    @Autowired
    private MqttMsgSender mqttMsgSender;


    @Autowired
    private TNMSJsonBuilder tnmsJsonBuilder;

    //@Cacheable(value = CACHE_NAME)
    public Page<HFDevRouter> findAll(PageBean pb) {
        return routerRepository.findAll(
                new PageRequest(pb.getPage() - 1,
                        pb.getSize(),
                        Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                        pb.getSortName()
                )
        );
    }

    //@Cacheable(value = CACHE_NAME)
    public Page<HFDevRouter> findAllByMac(PageBean pb, String mac) {

        return routerRepository.findByUdid(mac,
                new PageRequest(pb.getPage() - 1,
                        pb.getSize(),
                        Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                        pb.getSortName()
                )
        );
    }


    @CacheEvict(value = CACHE_NAME_AP, allEntries = true)
    public void delete(Long id) {
        routerRepository.delete(id);
    }

    //@Cacheable(value = CACHE_NAME_AP, key = "#id.toString().concat('-router')")
    public HFDevRouter findOne(Long id) {
        return routerRepository.findOne(id);
    }

    @Cacheable(value = CACHE_NAME_AP, key = "#mac")
    public HFDevRouter findOneByMac(String mac) {
        return routerRepository.findOneByUdid(mac);
    }

    @CacheEvict(value = CACHE_NAME_AP, allEntries = true)
    public void save(HFDevRouter router,
                     TemplateWanBean wan, TemplateLanBean lan,
                     TemplateWifiBean wifi, TemplateOTABean ota) {

        if(router.getWan() == null) {
            router.setWan(DTOUtil.map(wan, HFDevRouterWanSetup.class));
        }
        else {
            wan.setId(router.getWan().getId());
            DTOUtil.mapTo(wan, router.getWan());
        }

        if(router.getLan() == null) {
            router.setLan(DTOUtil.map(lan, HFDevRouterLanSetup.class));
        }
        else {
            lan.setId(router.getLan().getId());
            DTOUtil.mapTo(lan, router.getLan());
        }

        if(router.getWifi() == null) {
            router.setWifi(DTOUtil.map(wifi, HFDevRouterWiFiSetup.class));
        }
        else {
            wifi.setId(router.getWifi().getId());
            DTOUtil.mapTo(wifi, router.getWifi());
        }

        if(router.getOta() == null) {
            router.setOta(DTOUtil.map(ota, HFDevRouterOtaSetup.class));
        }
        else {
            ota.setId(router.getOta().getId());
            DTOUtil.mapTo(ota, router.getOta());
        }


        routerRepository.save(router);
    }

    @CacheEvict(value = CACHE_NAME_AP, allEntries = true)
    public void save(HFDevRouter router) {
        routerRepository.save(router);
    }

    public void effect(HFDevRouter router, String scope, String userToken) {
        ObjectNode  node;

        if(scope.equalsIgnoreCase("wan")) {        /* topic - wan */
            node = tnmsJsonBuilder.JsonDataBuilder(router.getWan(), userToken, router.getUdid(), "config", scope);
        }
        else if(scope.equalsIgnoreCase("lan")) {        /* topic - lan */
            node = tnmsJsonBuilder.JsonDataBuilder(router.getLan(), userToken, router.getUdid(), "config", scope);
        }
        else if(scope.equalsIgnoreCase("wifi")) {       /* topic - wifi */
            node = tnmsJsonBuilder.JsonDataBuilder(router.getWifi(), userToken, router.getUdid(), "config", scope);
        }
        else if(scope.equalsIgnoreCase("ota")) {       /* topic - ota */
            node = tnmsJsonBuilder.JsonDataBuilder(router.getOta(), userToken, router.getUdid(), "config", scope);
        }
        else {
            node = tnmsJsonBuilder.JsonDataBuilder(router, userToken, router.getUdid(), "config", scope);
        }

        mqttMsgSender.MqttMsgRouterSingleSetup(router.getUdid(), node.toString());
    }

    /* topic - reset */
    public void reset(Long id) {
        HFDevRouter router = routerRepository.findOne(id);

        if(router != null) {
            mqttMsgSender.MqttMsgRouterReset(router.getUdid());
        }
    }

    /* topic - reboot */
    public void reboot(Long id) {
        HFDevRouter router = routerRepository.findOne(id);

        if(router != null) {
            mqttMsgSender.MqttMsgRouterReboot(router.getUdid());
        }
    }
}
