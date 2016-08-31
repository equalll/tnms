package com.tigercel.tnms.web;

import com.tigercel.tnms.config.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by somebody on 2016/8/24.
 */
@Controller
@RequestMapping("upload")
public class UploadController {

    @RequestMapping(value = "router", method = GET)
    public String router(Model model, HttpServletRequest request) {
        ServletContext sc = request.getSession().getServletContext();
        String uploadFilePath = sc.getRealPath(Constants.UPLOAD_PATH_ROUTER);
        File files[] = new File(uploadFilePath).listFiles();

        model.addAttribute("title", "router");
        model.addAttribute("files", files);

        return "dirlist";
    }

    @RequestMapping(value = "app", method = GET)
    public String app(Model model, HttpServletRequest request) {
        ServletContext sc = request.getSession().getServletContext();
        String uploadFilePath = sc.getRealPath(Constants.UPLOAD_PATH_APP);
        File files[] = new File(uploadFilePath).listFiles();

        model.addAttribute("title", "app");
        model.addAttribute("files", files);

        return "dirlist";
    }
}
