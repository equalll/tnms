package com.tigercel.tnms.web.template;

import com.tigercel.tnms.dto.PageBean;
import com.tigercel.tnms.dto.TemplateWanBean;
import com.tigercel.tnms.model.router.template.HFDevRouterWanTmp;
import com.tigercel.tnms.service.template.DevRouterWANService;
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
@RequestMapping("device/ap/template/wan")
public class DevRouterWANController {

    @Autowired
    private DevRouterWANService deviceRouterWANService;

    @RequestMapping(value = "", method = GET)
    public String index(Model model) {


        model.addAttribute("menu", "menuTemplateWAN");
        return "device/ap/template/wan";
    }


    @RequestMapping(value = "", method = POST)
    public String add(Model model, TemplateWanBean wan) {

        HFDevRouterWanTmp drw;

        if(deviceRouterWANService.count() >= 10) {
            model.addAttribute("result", "error");
            model.addAttribute("msg", "template table is full");
            return "jsonTemplate";
        }

        if(wan == null || wan.getTemplateName() == null) {
            model.addAttribute("result", "error");
            model.addAttribute("msg", "invalid parameters");
            return "jsonTemplate";
        }

        drw = deviceRouterWANService.findOneByTemplateName(wan.getTemplateName());
        if(drw != null) {
            model.addAttribute("result", "error");
            model.addAttribute("msg", wan.getTemplateName() + " is already exist");
            return "jsonTemplate";
        }

        deviceRouterWANService.save(DTOUtil.map(wan, HFDevRouterWanTmp.class));

        model.addAttribute("result", "success");
        return "jsonTemplate";
    }

    /**
     * get records
     * @param search is optional, search record by templateName
     * @param pb
     * @param model
     * @return
     */
    @RequestMapping(value = "show", method = GET)
    public String show(@RequestParam(value = "search", required = false) String search,
                       PageBean pb, Model model) {

        Page<HFDevRouterWanTmp> wans = null;

        if(search != null && search.length() != 0) {
            wans = deviceRouterWANService.findAllByTemplateName(pb, search);
        }
        else {
            wans = deviceRouterWANService.findAll(pb);
        }

        model.addAttribute("result", "success");
        model.addAttribute("total", wans.getTotalElements());
        model.addAttribute("rows", wans.getContent());

        return "jsonTemplate";
    }

    /**
     *
     * @param model
     * @param wan
     * @return
     */
    @RequestMapping(value = "edit", method = POST)
    public String edit(Model model, TemplateWanBean wan) {
        HFDevRouterWanTmp drw;
        HFDevRouterWanTmp tmp;

        if(wan.getId() <= 0) {
            model.addAttribute("result", "error");
            model.addAttribute("msg", "id :" + wan.getId() + " is invalid");
            return "jsonTemplate";
        }

        drw = deviceRouterWANService.findOne(wan.getId());
        if(drw == null) {
            model.addAttribute("result", "error");
            model.addAttribute("msg", "id :" + wan.getId() + " is invalid");
            return "jsonTemplate";
        }
        else {
            if(wan.getTemplateName().equals(drw.getTemplateName()) == false) {
                tmp = deviceRouterWANService.findOneByTemplateName(wan.getTemplateName());
                if(tmp != null) {
                    model.addAttribute("result", "error");
                    model.addAttribute("msg", "template name :" + wan.getTemplateName()
                            + " is already exist");
                    return "jsonTemplate";
                }
            }
        }

        DTOUtil.mapTo(wan, drw);
        deviceRouterWANService.save(drw);
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
            deviceRouterWANService.delete(id);
            model.addAttribute("result", "success");
        }

        return "jsonTemplate";
    }
}


