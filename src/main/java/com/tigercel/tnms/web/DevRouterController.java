package com.tigercel.tnms.web;

import com.tigercel.tnms.dto.*;
import com.tigercel.tnms.model.router.HFDevRouter;
import com.tigercel.tnms.service.DevRouterService;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by freedom on 2016/5/23.
 */
@Controller
@RequestMapping("device")
public class DevRouterController {

    @Autowired
    private DevRouterService deviceRouterService;

    private static Logger logger = LoggerFactory.getLogger(DevRouterController.class);

    /* get device index.html */
    @RequestMapping(value = "ap", method = GET)
    private String index(Model model) {
        model.addAttribute("menu", "menuDevice");

        return "device/index";
    }

    /* show routers */
    @RequestMapping(value = "ap/show", method = GET)
    public String show(@RequestParam(value = "search", required = false) String search,
                       PageBean pb, Model model) {
        Page<HFDevRouter> routers = null;

        if(search != null && search.length() != 0) {
            routers = deviceRouterService.findAllByMac(pb, search);
        }
        else {
            routers = deviceRouterService.findAll(pb);
        }

        model.addAttribute("result", "success");
        model.addAttribute("total", routers.getTotalElements());
        model.addAttribute("rows", routers.getContent());

        return "jsonTemplate";
    }

    /* get status.html */
    @RequestMapping(value="ap/{id}", method = GET)
    public String status(Model model, @PathVariable(value = "id") Long id) {
        HFDevRouter router = null;

        router = deviceRouterService.findOne(id);

        if(router == null) {
            model.addAttribute("router", "");
        }
        else {
            model.addAttribute("router", router);
        }

        model.addAttribute("menu", "menuDevice");
        return "device/ap/status";
    }

    /* delete router */
    @RequestMapping(value="ap/{id}/delete", method = POST)
    public String delete(Model model, @PathVariable(value = "id") Long id) {

        if(id > 0) {
            deviceRouterService.delete(id);
        }

        model.addAttribute("result", "success");

        return "jsonTemplate";
    }

    /* show router status */
    @RequestMapping(value="ap/{id}/show", method = GET)
    public String show(Model model, @PathVariable(value = "id") Long id) {

        HFDevRouter router = null;

        if(id > 0) {
            router = deviceRouterService.findOne(id);

            if(router == null) {
                model.addAttribute("result", "error");
                model.addAttribute("msg", "id invalid");
            }
            else if(router.getStatus() == null) {
                model.addAttribute("result", "error");
                model.addAttribute("msg", "no status info");
            }
            else {
                model.addAttribute("result", "success");
                Hibernate.initialize(router.getStatus());
                model.addAttribute("status", router.getStatus());
            }
        }
        else {
            model.addAttribute("result", "error");
        }

        return "jsonTemplate";
    }

    /* reset */
    @RequestMapping(value="ap/{id}/reset", method = POST)
    public String reset(Model model, @PathVariable(value = "id") Long id) {

        deviceRouterService.reset(id);
        model.addAttribute("result", "success");

        return "jsonTemplate";
    }

    /* reboot */
    @RequestMapping(value="ap/{id}/reboot", method = POST)
    public String reboot(Model model, @PathVariable(value = "id") Long id) {

        deviceRouterService.reboot(id);
        model.addAttribute("result", "success");

        return "jsonTemplate";
    }

    /* get settings.html*/
    @RequestMapping(value="ap/{id}/settings", method = GET)
    public String settings(Model model, @PathVariable(value = "id") Long id) {

        model.addAttribute("menu", "menuDevice");
        model.addAttribute("router", deviceRouterService.findOne(id));
        return "device/ap/settings";
    }


    /* show router status */
    @RequestMapping(value="ap/{id}/settings/show", method = GET)
    public String showSettings(Model model, @PathVariable(value = "id") Long id) {

        HFDevRouter router = null;

        if(id > 0) {
            router = deviceRouterService.findOne(id);

            if(router == null) {
                model.addAttribute("result", "error");
            }
            else {
                model.addAttribute("result", "success");
                Hibernate.initialize(router.getWan());
                model.addAttribute("wan", router.getWan());
                Hibernate.initialize(router.getLan());
                model.addAttribute("lan", router.getLan());
                Hibernate.initialize(router.getWifi());
                model.addAttribute("wifi", router.getWifi());
                Hibernate.initialize(router.getOta());
                model.addAttribute("ota", router.getOta());
            }
        }
        else {
            model.addAttribute("result", "error");
        }

        return "jsonTemplate";
    }

    /* just save settigns */

    @RequestMapping(value="ap/{id}/settings/save", method = POST)
    public String save(Model model, @PathVariable(value = "id") Long id,
                       TemplateWanBean wan, TemplateLanBean lan,
                       TemplateWifiBean wifi, TemplateOTABean ota) {

        HFDevRouter router = deviceRouterService.findOne(id);
        if(router == null) {
            logger.debug("====== not found : " + id);
            model.addAttribute("result", "error");
        }
        else {
            deviceRouterService.save(router, wan, lan, wifi, ota);
            logger.debug("====== saved ");
            model.addAttribute("result", "success");
        }

        return "jsonTemplate";
        //return "message/success";
    }

    /*
        @RequestMapping(value="ap/{id}/settings/save", method = POST)
        public String save(Model model, @PathVariable(value = "id") Long id) {


                logger.debug("====== saved ");
                model.addAttribute("result", "success");


            //return "jsonTemplate";
            return "message/success";
        }
    */
    /* Make the settigns take effect */
    @RequestMapping(value="ap/{id}/settings/config", method = POST)
    public String config(Model model, @PathVariable(value = "id") Long id,
                         @RequestParam(name = "scope", defaultValue = "all") String scope) {

        HFDevRouter router = deviceRouterService.findOne(id);


        if(router == null) {
            model.addAttribute("result", "error");
        }
        else {
            deviceRouterService.effect(router, scope);
            model.addAttribute("result", "success");
        }

        return "jsonTemplate";
    }

}

