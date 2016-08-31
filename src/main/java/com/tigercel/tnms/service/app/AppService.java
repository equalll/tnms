package com.tigercel.tnms.service.app;

import com.tigercel.tnms.config.Constants;
import com.tigercel.tnms.dto.AppVersionBean;
import com.tigercel.tnms.model.app.HFApp;
import com.tigercel.tnms.repository.app.AppRepository;
import com.tigercel.tnms.utils.ClutterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by somebody on 2016/8/12.
 */
@Service
public class AppService {


    @Autowired
    private AppRepository appRepository;


    public List<HFApp> findAll() {
        return appRepository.findAll();
    }

    public HFApp findOne() {
        List<HFApp> apps = findAll();

        if(apps.isEmpty()) {
            return null;
        }
        else {
            return apps.get(0);
        }
    }


    public HFApp findOne(Long id) {
        return appRepository.findOne(id);
    }


    public HFApp findFirst() {
        Page<HFApp> apps = appRepository.findAll(new PageRequest(0, 1));

        if(apps == null || apps.hasContent() == false) {
            return null;
        }
        else {
            return apps.getContent().get(0);
        }
    }


    public HFApp save(HFApp app) {
        return appRepository.save(app);
    }



    public void createApp(HFApp app, AppVersionBean version, MultipartFile file, HttpServletRequest request) {
        byte[]          uploadBytes = null;
        String 			md5;
        String          path;
        File            target ;
        //HFApp           app = new HFApp();


        app.setAppName(version.getAppName());
        app.setVersion(version.getVersion());
        app.setFilename(file.getOriginalFilename());

        try {
            uploadBytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        md5 = ClutterUtils.MD5(uploadBytes);
        if(md5 == null) {
            return;
        }
        app.setMd5(md5);
        app.setUrl(request.getContextPath() + Constants.UPLOAD_PATH_APP + app.getFilename());

        path = request.getSession().getServletContext().getRealPath(Constants.UPLOAD_PATH_APP);
        target = new File(path);
        if(!target.exists()) {
            target.mkdirs();
        }

        try {
            file.transferTo(new File(path, app.getFilename()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        save(app);
    }



    public void deleteFile(HFApp app, HttpServletRequest request) {
        if(app.getFilename() != null) {
            String path = request.getSession().getServletContext().getRealPath(Constants.UPLOAD_PATH_APP);
            File file = new File(path + "/" + app.getFilename());
            if (file.isFile() && file.exists()) {
                file.delete();
            }
        }
    }


    public void deleteApp(Long id) {
        appRepository.delete(id);
    }


}
