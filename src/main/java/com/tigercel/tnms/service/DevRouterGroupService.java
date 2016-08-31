package com.tigercel.tnms.service;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tigercel.tnms.config.Constants;
import com.tigercel.tnms.dto.PageBean;
import com.tigercel.tnms.model.router.HFDevRouterGroup;
import com.tigercel.tnms.model.router.setup.HFDevRouterLanSetup;
import com.tigercel.tnms.model.router.setup.HFDevRouterOtaSetup;
import com.tigercel.tnms.model.router.setup.HFDevRouterWanSetup;
import com.tigercel.tnms.model.router.setup.HFDevRouterWiFiSetup;
import com.tigercel.tnms.repository.DevRouterGroupRepository;
import com.tigercel.tnms.service.support.MqttMsgSender;
import com.tigercel.tnms.service.support.TNMSJsonBodyBuilder;
import com.tigercel.tnms.service.support.TNMSJsonBuilder;
import com.tigercel.tnms.service.template.DevRouterLANService;
import com.tigercel.tnms.service.template.DevRouterOTAService;
import com.tigercel.tnms.service.template.DevRouterWANService;
import com.tigercel.tnms.service.template.DevRouterWiFiService;
import com.tigercel.tnms.utils.ClutterUtils;
import com.tigercel.tnms.utils.DTOUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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

    @Autowired
    private TNMSJsonBuilder tnmsJsonBuilder;


    public HFDevRouterGroup findOneByName(String name) {
        return deviceRouterGroupRepository.findOneByGroupName(name);
    }
/*
    public HFDevRouterGroup save(RouterGroupBean g) {
        HFDevRouterWanTmp w = deviceRouterWANService.findOneByTemplateName(g.getGroupWanName());
        HFDevRouterLanTmp l = deviceRouterLANService.findOneByTemplateName(g.getGroupLanName());
        HFDevRouterWiFiTmp wi = deviceRouterWiFiService.findOneByTemplateName(g.getGroupWifiName());
        HFDevRouterOtaTmp ota = deviceRouterOTAService.findOneByTemplateName(g.getGroupOTAName());
        HFDevRouterGroup group = new HFDevRouterGroup();

        group.setGroupName(g.getGroupName());
        group.setDescription(g.getDescription());
        group.setWan(w);
        group.setLan(l);
        group.setWifi(wi);
        group.setOta(ota);
        group.setGroupId(IDGenerator.getID());

        return deviceRouterGroupRepository.save(group);
    }
*/

    public HFDevRouterGroup save(HFDevRouterGroup group) {
        return deviceRouterGroupRepository.save(group);
    }


    public Page<HFDevRouterGroup> findAllByName(PageBean pb, String name) {
        return deviceRouterGroupRepository.findAllByGroupName(name,
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

    public HFDevRouterGroup update(HFDevRouterGroup group) {

        if(group.getWan() == null || group.getWan().getTemplateName().equals(group.getGroupWanName()) == false) {
            group.setWan(deviceRouterWANService.findOneByTemplateName(group.getGroupWanName()));
        }

        if(group.getLan() == null || group.getLan().getTemplateName().equals(group.getGroupLanName()) == false) {
            group.setLan(deviceRouterLANService.findOneByTemplateName(group.getGroupLanName()));
        }

        if(group.getWifi() == null || group.getWifi().getTemplateName().equals(group.getGroupWifiName()) == false) {
            group.setWifi(deviceRouterWiFiService.findOneByTemplateName(group.getGroupWifiName()));
        }

        if(group.getOta() == null || group.getOta().getTemplateName().equals(group.getGroupOTAName()) == false) {
            group.setOta(deviceRouterOTAService.findOneByTemplateName(group.getGroupOTAName()));
        }


        return save(group);
    }

    public void delete(Long id) {
        deviceRouterGroupRepository.delete(id);
    }


    public void effect(HFDevRouterGroup group, String scope, String userToken) {
        ObjectNode node;


        if(scope.equalsIgnoreCase("wan")) {             /* topic - wan */
            node = tnmsJsonBuilder.JsonDataBuilder(
                        DTOUtil.map(group.getWan(), HFDevRouterWanSetup.class),
                        userToken, group.getGroupId(), "config", scope);
        }
        else if(scope.equalsIgnoreCase("lan")) {        /* topic - lan */
            node = tnmsJsonBuilder.JsonDataBuilder(
                        DTOUtil.map(group.getLan(), HFDevRouterLanSetup.class),
                        userToken, group.getGroupId(), "config", scope);
        }
        else if(scope.equalsIgnoreCase("wifi")) {       /* topic - wifi */;
            node = tnmsJsonBuilder.JsonDataBuilder(
                        DTOUtil.map(group.getWifi(), HFDevRouterWiFiSetup.class),
                        userToken, group.getGroupId(), "config", scope);
        }
        else if(scope.equalsIgnoreCase("ota")) {       /* topic - ota */
            node = tnmsJsonBuilder.JsonDataBuilder(
                        DTOUtil.map(group.getOta(), HFDevRouterOtaSetup.class),
                        userToken, group.getGroupId(), "config", scope);
        }
        else {
            node = tnmsJsonBuilder.JsonDataBuilder(group, userToken, group.getGroupId(), "config", scope);
        }

        mqttMsgSender.MqttMsgRouterGroupSetup(group.getGroupId(), node.toString());
    }


    public Boolean createFile(HFDevRouterGroup group, MultipartFile file, HttpServletRequest request) {
        byte[]          uploadBytes = null;
        String 			md5;
        File            target ;

        String 			path = request.getSession().getServletContext().getRealPath(Constants.UPLOAD_PATH_ROUTER);



        if(file != null) {

            /* delete old file */
            if(StringUtils.isEmpty(group.getFirmwareFileName()) == false) {
                deleteFile(path + "/" + group.getFirmwareFileName());
            }

            group.setFirmwareFileName(file.getOriginalFilename());

            try {
                uploadBytes = file.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            md5 = ClutterUtils.MD5(uploadBytes);
            if(md5 == null) {
                return false;
            }

            group.setFirmwareMd5(md5);
            group.setFirmwareUrl(request.getContextPath() +
                    Constants.UPLOAD_PATH_ROUTER +
                    group.getFirmwareFileName());

            path = request.getSession().getServletContext().getRealPath(Constants.UPLOAD_PATH_ROUTER);
            target = new File(path);
            if(!target.exists()) {
                target.mkdirs();
            }

            try {
                file.transferTo(new File(path, group.getFirmwareFileName()));
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }
        else {
            return false;
        }
    }


    public void deleteFile(String filename) {
        if(filename != null) {
            File file = new File(filename);
            if (file.isFile() && file.exists()) {
                file.delete();
            }
        }
    }

}
