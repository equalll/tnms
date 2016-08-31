package com.tigercel.tnms.service;

import com.tigercel.tnms.model.router.HFDevRouterGroup;
import com.tigercel.tnms.model.router.HFDevRouterNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by freedom on 2016/3/23.
 */
@Service
public class VersionService {

    @Autowired
    private DevRouterGroupNodeService devRouterGroupNodeService;

    private static final Logger logger = LoggerFactory.getLogger(VersionService.class);

    private final String seed = "shanghaicz";

    public HFDevRouterGroup getGroupRecordByMac(String mac) {
        return devRouterGroupNodeService.findGroupByMAC(mac);
    }

    public boolean checkMD5(String md5s, String rnd) {
        MessageDigest   md5         = null;
        byte[] 			digest      ;
        String 			hashString  ;

        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        digest = md5.digest((rnd + seed).getBytes());
        hashString = new BigInteger(1, digest).toString(16);

        logger.debug("====== hash: " + hashString + ", md5: " + md5s);
        return hashString.equalsIgnoreCase(md5s);
    }

    public String getRequestURL(HttpServletRequest request, String uri) {
        String url = request.getRequestURL().toString();
        int end = 0;

        //http:// or https://
        end = url.indexOf('/', 8);

        return (url.substring(0, end) + uri);
    }
}
