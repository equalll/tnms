package com.tigercel.tnms.web.template;

import com.tigercel.tnms.dto.PageBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by freedom on 2016/5/23.
 */
@Controller
@RequestMapping("device/ap/template/qos")
public class DevRouterQoSController {
    @RequestMapping(value = "", method = GET)
    public String index(Model model) {


        model.addAttribute("menu", "menuTemplateQoS");
        return "device/ap/template/qos";
    }

    @RequestMapping(value = "", method = POST)
    public String add(Model model) {


        model.addAttribute("result", "success");
        return "jsonTemplate";
    }

    @RequestMapping(value = "show", method = GET)
    public String show(@RequestParam(value = "search", required = false) String search,
                       PageBean pb, Model model) {


        return "jsonTemplate";
    }

    @RequestMapping(value = "edit", method = POST)
    public String edit(Model model) {


        model.addAttribute("result", "success");
        return "jsonTemplate";
    }

    @RequestMapping(value = "delete", method = POST)
    public String delete(Model model) {

        model.addAttribute("result", "success");
        return "jsonTemplate";
    }
}