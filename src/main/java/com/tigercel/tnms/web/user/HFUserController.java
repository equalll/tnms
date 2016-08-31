package com.tigercel.tnms.web.user;

import com.tigercel.tnms.dto.PageBean;
import com.tigercel.tnms.model.HFUser;
import com.tigercel.tnms.service.user.HFUserService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by somebody on 2016/8/30.
 */

@Controller
@RequestMapping("hfuser")
public class HFUserController {

    @Autowired
    private HFUserService hfUserService;

    @RequestMapping(value = "index", method = GET)
    public String index(Model model) {

        model.addAttribute("menu", "menuHFUser");

        return "hfuser/index";
    }


    @RequestMapping(value = "users", method = GET)
    public String users(Model model,
                        @RequestParam(value = "search", required = false) String search,
                        PageBean pb) {
        Page<HFUser> users;

        if(StringUtils.isEmpty(search) == false) {
            users = hfUserService.findAllByName(pb, search);
        }
        else {
            users = hfUserService.findAll(pb);
        }

        model.addAttribute("result", "success");
        model.addAttribute("total", users.getTotalElements());
        model.addAttribute("rows", users.getContent());

        return "jsonTemplate";
    }


    @RequestMapping(value = "user/{name}/index", method = GET)
    public String userIndex(@PathVariable(value = "name") String name, Model model) {

        model.addAttribute("menu", "menuHFUser");
        model.addAttribute("userName", name);

        return "hfuser/user";
    }

    @RequestMapping(value = "user/{name}/info", method = GET)
    public String userInfo(@PathVariable(value = "name") String name, Model model) {

        HFUser user = hfUserService.findOneByName(name);

        if(user == null) {
            model.addAttribute("result", "error");
            model.addAttribute("msg", "invalid name");
        }
        else {
            Hibernate.initialize(user.getRouters());

            model.addAttribute("result", "success");
            model.addAttribute("routers", user.getRouters());
            model.addAttribute("hfUser", user);
        }

        return "jsonTemplate";
    }
}
