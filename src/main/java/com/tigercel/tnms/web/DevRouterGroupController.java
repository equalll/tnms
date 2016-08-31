package com.tigercel.tnms.web;

import com.tigercel.tnms.dto.PageBean;
import com.tigercel.tnms.dto.RouterGroupBean;
import com.tigercel.tnms.model.User;
import com.tigercel.tnms.model.router.HFDevRouterGroup;
import com.tigercel.tnms.model.router.template.HFDevRouterLanTmp;
import com.tigercel.tnms.model.router.template.HFDevRouterOtaTmp;
import com.tigercel.tnms.model.router.template.HFDevRouterWanTmp;
import com.tigercel.tnms.model.router.template.HFDevRouterWiFiTmp;
import com.tigercel.tnms.service.DevRouterGroupService;
import com.tigercel.tnms.service.UserService;
import com.tigercel.tnms.service.template.DevRouterLANService;
import com.tigercel.tnms.service.template.DevRouterOTAService;
import com.tigercel.tnms.service.template.DevRouterWANService;
import com.tigercel.tnms.service.template.DevRouterWiFiService;
import com.tigercel.tnms.utils.DTOUtil;
import com.tigercel.tnms.utils.IDGenerator;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by freedom on 2016/5/23.
 */
@Controller
@RequestMapping("device/ap/group")
public class DevRouterGroupController {

    @Autowired
    private DevRouterGroupService deviceRouterGroupService;

    @Autowired
    private DevRouterWANService deviceRouterWANService;

    @Autowired
    private DevRouterLANService deviceRouterLANService;

    @Autowired
    private DevRouterWiFiService deviceRouterWiFiService;

    @Autowired
    private DevRouterOTAService deviceRouterOTAService;

    @Autowired
    private UserService userService;




    @RequestMapping(value = "", method = GET)
    public String index(Model model) {
        List<HFDevRouterWanTmp> wan = deviceRouterWANService.findAll();
        List<HFDevRouterLanTmp> lan = deviceRouterLANService.findAll();
        List<HFDevRouterWiFiTmp> wifi = deviceRouterWiFiService.findAll();
        List<HFDevRouterOtaTmp> ota = deviceRouterOTAService.findAll();

        model.addAttribute("menu", "menuGroupList");
        model.addAttribute("wans", wan);
        model.addAttribute("lans", lan);
        model.addAttribute("wifis", wifi);
        model.addAttribute("otas", ota);

        return "device/ap/group/index";
    }


    @RequestMapping(value = "", method = POST)
    public String add(Model model,
                      RouterGroupBean rgb,
                      @RequestParam(value = "file", required = false) MultipartFile file,
                      HttpServletRequest request) {
        HFDevRouterGroup group = deviceRouterGroupService.findOneByName(rgb.getGroupName());

        if(group != null) {
            model.addAttribute("result", "error");
            model.addAttribute("msg", "group name is already exist");
            return "jsonTemplate";
        }


        if(StringUtils.isEmpty(rgb.getGroupWanName()) ||
                StringUtils.isEmpty(rgb.getGroupLanName()) ||
                StringUtils.isEmpty(rgb.getGroupWifiName()) ||
                StringUtils.isEmpty(rgb.getGroupOTAName())) {

            model.addAttribute("result", "error");
            model.addAttribute("msg", "invalid template name");
            return "jsonTemplate";
        }

        group = new HFDevRouterGroup();
        group.setGroupId(IDGenerator.getID());
        DTOUtil.mapTo(rgb, group);

        if(file == null) {
            deviceRouterGroupService.update(group);
            model.addAttribute("result", "success");
        }
        else {
            //DTOUtil.mapTo(rgb, group);
            Boolean ret = deviceRouterGroupService.createFile(group, file, request);
            if(ret == false) {
                model.addAttribute("result", "error");
            }
            else {
                deviceRouterGroupService.update(group);
                model.addAttribute("result", "success");
            }
        }

        return "jsonTemplate";
    }

    @RequestMapping(value = "show", method = GET)
    public String show(@RequestParam(value = "search", required = false) String search,
                       PageBean pb, Model model) {

        Page<HFDevRouterGroup> groups = null;

        if(search != null && search.length() != 0) {
            groups = deviceRouterGroupService.findAllByName(pb, search);
        }
        else {
            groups = deviceRouterGroupService.findAll(pb);
        }

        setGroupsTemplateName(groups);
        model.addAttribute("result", "success");
        model.addAttribute("total", groups.getTotalElements());
        model.addAttribute("rows", groups.getContent());

        return "jsonTemplate";
    }


    @RequestMapping(value = "edit", method = POST)
    public String edit(Model model, RouterGroupBean rgb,
                       @RequestParam(value = "file", required = false) MultipartFile file,
                       HttpServletRequest request) {

        HFDevRouterGroup group;
        HFDevRouterGroup tmp;

        if(StringUtils.isEmpty(rgb.getGroupWanName()) ||
                StringUtils.isEmpty(rgb.getGroupLanName()) ||
                StringUtils.isEmpty(rgb.getGroupWifiName()) ||
                StringUtils.isEmpty(rgb.getGroupOTAName())) {

            model.addAttribute("result", "error");
            model.addAttribute("msg", "invalid template name");
            return "jsonTemplate";
        }

        if(rgb.getId() <= 0) {
            model.addAttribute("result", "error");
            model.addAttribute("msg", "id :" + rgb.getId() + " is invalid");
            return "jsonTemplate";
        }

        group = deviceRouterGroupService.findOne(rgb.getId());
        if(group == null) {
            model.addAttribute("result", "error");
            model.addAttribute("msg", "id :" + group.getId() + " is invalid");
            return "jsonTemplate";
        }
        else {
            if(rgb.getGroupName().equals(group.getGroupName()) == false) {
                tmp = deviceRouterGroupService.findOneByName(rgb.getGroupName());
                if(tmp != null) {
                    model.addAttribute("result", "error");
                    model.addAttribute("msg", "group name :" + rgb.getGroupName()
                            + " is already exist");
                    return "jsonTemplate";
                }
            }
        }

        //DTOUtil.mapTo(rgb, group);
        if(file != null) {
            Boolean ret = deviceRouterGroupService.createFile(group, file, request);
            if(!ret) {
                model.addAttribute("result", "error");
            }
            else {
                DTOUtil.mapTo(rgb, group);
                deviceRouterGroupService.update(group);
                model.addAttribute("result", "success");
            }
        }
        else {
            DTOUtil.mapTo(rgb, group);
            deviceRouterGroupService.update(group);
            model.addAttribute("result", "success");
        }

        return "jsonTemplate";
    }

    @RequestMapping(value = "delete", method = POST)
    public String delete(Model model, @RequestParam(name="id", required = true) Long id) {

        if(id < 1) {
            model.addAttribute("result", "error");
            model.addAttribute("msg", "id :" + id + " is invalid");
        }
        else {
            deviceRouterGroupService.delete(id);
            model.addAttribute("result", "success");
        }

        return "jsonTemplate";
    }

    /* Make the settigns take effect */
    @RequestMapping(value="{group}/config", method = POST)
    public String config(Model model, @PathVariable(value = "group") String groupName,
                         @RequestParam(name = "scope", defaultValue = "all") String scope,
                         javax.servlet.http.HttpServletRequest request) {

        HFDevRouterGroup group = deviceRouterGroupService.findOneByName(groupName);

        if(group == null) {
            model.addAttribute("result", "error");
        }
        else {
            SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                    .getSession().getAttribute("SPRING_SECURITY_CONTEXT");
            User user = userService.findByUsername(securityContextImpl.getAuthentication().getName());
            deviceRouterGroupService.effect(group, scope, user.getToken());
            model.addAttribute("result", "success");
        }

        return "jsonTemplate";
    }


    @RequestMapping(value="{groupId}/configuration", method = GET)
    public String configuration(Model model,
                                @PathVariable(value = "groupId") Long groupId) {

        //model.addAttribute("group", deviceRouterGroupService.findOne(groupId));
        model.addAttribute("groupId", groupId);
        model.addAttribute("menu", "menuGroupList");

        return "device/ap/group/configuration";
    }

    @RequestMapping(value="{groupId}/configuration/show", method = GET)
    public String configurationShow(Model model,
                                    @PathVariable(value = "groupId") Long groupId) {

        HFDevRouterGroup group = deviceRouterGroupService.findOne(groupId);

        if(group == null) {
            model.addAttribute("result", "error");
        }
        else {
            Hibernate.initialize(group.getWan());
            Hibernate.initialize(group.getLan());
            Hibernate.initialize(group.getWifi());
            setGroupTemplateName(group);

            model.addAttribute("result", "success");
            model.addAttribute("group", group);
            model.addAttribute("wan", group.getWan());
            model.addAttribute("lan", group.getLan());
            model.addAttribute("wifi", group.getWifi());
            model.addAttribute("ota", group.getOta());
        }

        return "jsonTemplate";
    }

    private void setGroupsTemplateName(Page<HFDevRouterGroup> groups) {
        for(HFDevRouterGroup group : groups.getContent()) {
            setGroupTemplateName(group);
        }
    }

    private void setGroupTemplateName(HFDevRouterGroup group) {
        if(group.getWan() != null) {
            group.setGroupWanName(group.getWan().getTemplateName());
        }

        if(group.getLan() != null) {
            group.setGroupLanName(group.getLan().getTemplateName());
        }

        if(group.getWifi() != null) {
            group.setGroupWifiName(group.getWifi().getTemplateName());
        }

        if(group.getOta() != null) {
            group.setGroupOTAName(group.getOta().getTemplateName());
        }

        if(group.getFirewall() != null) {
            group.setGroupFirewallName(group.getFirewall().getTemplateName());
        }

        if(group.getQos() != null) {
            group.setGroupQosName(group.getQos().getTemplateName());
        }
    }
}
