package com.tigercel.tnms.web.template;

import com.tigercel.tnms.dto.PageBean;
import com.tigercel.tnms.dto.TemplateLanBean;
import com.tigercel.tnms.model.router.template.HFDevRouterLanTmp;
import com.tigercel.tnms.service.template.DevRouterLANService;
import com.tigercel.tnms.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by freedom on 2016/5/23.
 */
@Controller
@RequestMapping("device/ap/template/lan")
public class DevRouterLANController {

    @Autowired
    private DevRouterLANService deviceRouterLANService;

    @RequestMapping(value = "", method = GET)
    public String index(Model model) {


        model.addAttribute("menu", "menuTemplateLAN");
        return "device/ap/template/lan";
    }

    @RequestMapping(value = "", method = POST)
    public String add(Model model, TemplateLanBean lan) {
        HFDevRouterLanTmp drl;

        if(deviceRouterLANService.count() >= 10) {
            model.addAttribute("result", "error");
            model.addAttribute("msg", "template table is full");
            return "jsonTemplate";
        }

        if(lan == null || lan.getTemplateName() == null) {
            model.addAttribute("result", "error");
            model.addAttribute("msg", "invalid parameters");
            return "jsonTemplate";
        }

        drl = deviceRouterLANService.findOneByTemplateName(lan.getTemplateName());
        if(drl != null) {
            model.addAttribute("result", "error");
            model.addAttribute("msg", lan.getTemplateName() + " is already exist");
            return "jsonTemplate";
        }

        deviceRouterLANService.save(DTOUtil.map(lan, HFDevRouterLanTmp.class));

        model.addAttribute("result", "success");
        return "jsonTemplate";
    }

    @RequestMapping(value = "show", method = GET)
    public String show(@RequestParam(value = "search", required = false) String search,
                       PageBean pb, Model model) {

        Page<HFDevRouterLanTmp> lans = null;

        if(search != null && search.length() != 0) {
            lans = deviceRouterLANService.findAllByTemplateName(pb, search);
        }
        else {
            lans = deviceRouterLANService.findAll(pb);
        }

        model.addAttribute("result", "success");
        model.addAttribute("total", lans.getTotalElements());
        model.addAttribute("rows", lans.getContent());

        return "jsonTemplate";
    }

    @RequestMapping(value = "edit", method = POST)
    public String edit(Model model, TemplateLanBean lan) {

        HFDevRouterLanTmp drl;
        HFDevRouterLanTmp tmp;

        if(lan.getId() <= 0) {
            model.addAttribute("result", "error");
            model.addAttribute("msg", "id :" + lan.getId() + " is invalid");
            return "jsonTemplate";
        }

        drl = deviceRouterLANService.findOne(lan.getId());
        if(drl == null) {
            model.addAttribute("result", "error");
            model.addAttribute("msg", "id :" + lan.getId() + " is invalid");
            return "jsonTemplate";
        }
        else {
            if(lan.getTemplateName().equals(drl.getTemplateName()) == false) {
                tmp = deviceRouterLANService.findOneByTemplateName(lan.getTemplateName());
                if(tmp != null) {
                    model.addAttribute("result", "error");
                    model.addAttribute("msg", "template name :" + lan.getTemplateName()
                            + " is already exist");
                    return "jsonTemplate";
                }
            }
        }

        DTOUtil.mapTo(lan, drl);
        deviceRouterLANService.save(drl);
        model.addAttribute("result", "success");

        return "jsonTemplate";
    }

    @RequestMapping(value = "delete", method = POST)
    public String delete(Model model, @RequestParam(name="id", required = true) Long id) {

        if(id <= 0) {
            model.addAttribute("result", "error");
            model.addAttribute("msg", "id :" + id + " is invalid");
        }
        else {
            deviceRouterLANService.delete(id);
            model.addAttribute("result", "success");
        }

        return "jsonTemplate";
    }
}

