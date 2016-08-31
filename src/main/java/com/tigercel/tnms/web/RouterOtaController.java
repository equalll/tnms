package com.tigercel.tnms.web;

import com.tigercel.tnms.dto.RouterVersionBean;
import com.tigercel.tnms.model.router.HFDevRouterGroup;
import com.tigercel.tnms.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by somebody on 2016/8/12.
 */
@RestController
@RequestMapping("ota")
public class RouterOtaController {

    @Autowired
    private VersionService versionService;

    /**
     *    DeviceType: #{ota.getProductModel()}
          FwVerison: #{ota.getFirmwareVersion()}
          FwName: #{ota.getFirmwareFileName()}
          FwMd5: #{ota.getFirmwareMd5()}
          #{url}
     * @param vb
     * @param request
     * @return
     */

    @RequestMapping(value = "router", method = GET)
    public String otaVersion(RouterVersionBean vb, HttpServletRequest request) {
        HFDevRouterGroup ota = null;
        String result;

        if(!versionService.checkMD5(vb.getC(), vb.getB())) {
            result = "Invalid md5";
        }
        else {
            ota = versionService.getGroupRecordByMac(vb.getA());
            result = "DeviceType: " + ota.getProductModel() + "\n" +
                     "FwVerison: " + ota.getFirmwareVersion() + "\n" +
                     "FwName: " + ota.getFirmwareFileName() + "\n" +
                     "FwMd5: " + ota.getFirmwareMd5() + "\n" +
                    versionService.getRequestURL(request, ota.getFirmwareUrl());
        }

        return result;
    }
}
