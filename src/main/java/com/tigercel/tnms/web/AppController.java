package com.tigercel.tnms.web;

import com.tigercel.tnms.dto.AppVersionBean;
import com.tigercel.tnms.model.app.HFApp;
import com.tigercel.tnms.service.app.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by somebody on 2016/8/12.
 */
@Controller
@RequestMapping("app")
public class AppController {

    @Autowired
    private AppService appService;

    /*
    *  显示 app 信息
    * */
    @RequestMapping(value = "index", method = GET)
    public String index(Model model) {

        model.addAttribute("menu", "menuApp");

        return "app/index";
    }

    @RequestMapping(value = "show", method = GET)
    public String show(Model model) {
        HFApp app = appService.findFirst();

        if(app != null) {
            model.addAttribute("result", "success");
            model.addAttribute("app", app);
        }
        else {
            model.addAttribute("result", "error");
        }

        return "jsonTemplate";
    }

    @RequestMapping(value = "version", method = POST)
    public String uploadApp(AppVersionBean version,
                            @RequestParam(value = "file", required = false) MultipartFile file,
                            HttpServletRequest request, Model model) {

        if(file != null) {
            HFApp app = appService.findFirst();
            if(app == null) {
                app = new HFApp();
            }
            else {
                appService.deleteFile(app, request);
            }
            appService.createApp(app, version, file, request);
            model.addAttribute("result", "success");
        }
        else {
            model.addAttribute("result", "error");
        }


        return "jsonTemplate";
    }


    @RequestMapping(value = "delete", method = GET)
    public String deleteApp(AppVersionBean version, HttpServletRequest request, Model model) {
        HFApp app = appService.findFirst();

        appService.deleteFile(app, request);
        appService.deleteApp(app.getId());

        model.addAttribute("result", "success");

        return "jsonTemplate";
    }
}
