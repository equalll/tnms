package com.tigercel.tnms.service.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tigercel.tnms.model.router.HFDevRouter;
import com.tigercel.tnms.model.router.HFDevRouterGroup;
import com.tigercel.tnms.model.router.setup.HFDevRouterLanSetup;
import com.tigercel.tnms.model.router.setup.HFDevRouterOtaSetup;
import com.tigercel.tnms.model.router.setup.HFDevRouterWanSetup;
import com.tigercel.tnms.model.router.setup.HFDevRouterWiFiSetup;
import com.tigercel.tnms.utils.DTOUtil;

/**
 * Created by freedom on 2016/5/24.
 */
public class TNMSJsonBodyBuilder {





    /**
     *
     * @param wan
     * @return
     */
    public static ObjectNode WANJsonBuilder(HFDevRouterWanSetup wan) {
        ObjectNode      node;

        try {
            ObjectMapper    mapper = new ObjectMapper();
            node = mapper.createObjectNode();


            node.put("protocol", wan.getProtocol());
            if(wan.getProtocol().equals("dhcp")) {
                /* pass through */
            }
            else if(wan.getProtocol().equals("static")) {
                node.put("address", wan.getAddress());
                node.put("netmask", wan.getNetmask());
                node.put("gateway", wan.getGateway());
            }
            else if(wan.getProtocol().equals("pppoe")) {
                node.put("username", wan.getUsername());
                node.put("password", wan.getPassword());
            }
            else if(wan.getProtocol().equals("3g")) {
                node.put("modemDevice", wan.getModemDevice());
                node.put("serviceType", wan.getServiceType());
                node.put("APN", wan.getAPN());
                node.put("dialNumber", wan.getDialNumber());
            }
            else {
                return null;
            }
        }
        catch (Exception e) {
            return null;
        }

        return node;
    }

    /**
     *
     * @param lan
     * @return
     */
    public static ObjectNode LANJsonBuilder(HFDevRouterLanSetup lan) {
        ObjectNode      node;

        try {
            ObjectMapper    mapper = new ObjectMapper();
            node = mapper.createObjectNode();

            //root.put("scope", "lan");

            node.put("lanAddress", lan.getLanAddress());
            node.put("lanNetmask", lan.getLanNetmask());
            node.put("dhcp", lan.getDhcp());
            node.put("start", lan.getStart());
            node.put("limit", lan.getLimit());
            node.put("leaseTime", lan.getLeaseTime());

        }
        catch (Exception e) {
            return null;
        }

        return node;
    }

    /**
     *
     * @param wifi
     * @return
     */
    public static ObjectNode WiFiJsonBuilder(HFDevRouterWiFiSetup wifi) {
        ObjectNode      node;

        try {
            ObjectMapper    mapper = new ObjectMapper();
            node = mapper.createObjectNode();

            node = mapper.createObjectNode();
            node.put("wifi", node);
            //root.put("scope", "wifi");

            node.put("wifi", wifi.getWifi());
            node.put("ssid", wifi.getSsid());
            node.put("channel", wifi.getChannel());
            node.put("maxStation", wifi.getMaxStation());
            node.put("encryption", wifi.getEncryption());

            if(wifi.getEncryption().equals("none")) {
                /* pass through */
            }
            else if(wifi.getEncryption().equals("open")){
                node.put("key", wifi.getKey());
            }
            else if(wifi.getEncryption().equals("psk")){
                node.put("cipher", wifi.getCipher());
                node.put("key", wifi.getKey());
            }
            else if(wifi.getEncryption().equals("psk2")){
                node.put("cipher", wifi.getCipher());
                node.put("key", wifi.getKey());
            }
            else if(wifi.getEncryption().equals("psk-mixed")){
                node.put("cipher", wifi.getCipher());
                node.put("key", wifi.getKey());
            }

        }
        catch (Exception e) {
            return null;
        }

        return node;
    }

    /**
     *
     * @param ota
     * @return
     */
    public static ObjectNode OTAJsonBuilder(HFDevRouterOtaSetup ota) {
        ObjectNode      node;

        try {
            ObjectMapper    mapper = new ObjectMapper();
            node = mapper.createObjectNode();

            node = mapper.createObjectNode();
            node.put("ota", node);
            //root.put("scope", "ota");

            node.put("mode", ota.getMode());
            node.put("server", ota.getServer());
            node.put("pridDelay", ota.getPridDelay());
            node.put("restoreFlag", ota.getRestoreFlag());

            if(ota.getMode() == 2) {
                node.put("windowStart", ota.getWindowStart());
                node.put("windowSize", ota.getWindowSize());
            }
        }
        catch (Exception e) {
            return null;
        }

        return node;
    }

/*
    public static ObjectNode AllJsonBuilder(HFDevRouter router) {
        ObjectNode      root = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode node;

            root = mapper.createObjectNode();

            node = WANJsonBuilder(router.getWan());
            root.put("wan", node.get("wan"));

            node = LANJsonBuilder(router.getLan());
            root.put("lan", node.get("lan"));

            node = WiFiJsonBuilder(router.getWifi());
            root.put("wifi", node.get("wifi"));

            node = OTAJsonBuilder(router.getOta());
            root.put("ota", node.get("ota"));

            //root.put("scope", "all");
        }
        catch (Exception e) {
            return null;
        }

        return root;
    }


    public static ObjectNode AllJsonBuilder(HFDevRouterGroup group) {
        ObjectNode      root = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode node;

            root = mapper.createObjectNode();

            node = WANJsonBuilder(DTOUtil.map(group.getWan(), HFDevRouterWanSetup.class));
            root.put("wan", node.get("wan"));

            node = LANJsonBuilder(DTOUtil.map(group.getLan(), HFDevRouterLanSetup.class));
            root.put("lan", node.get("lan"));

            node = WiFiJsonBuilder(DTOUtil.map(group.getWifi(), HFDevRouterWiFiSetup.class));
            root.put("wifi", node.get("wifi"));

            node = OTAJsonBuilder(DTOUtil.map(group.getOta(), HFDevRouterOtaSetup.class));
            root.put("ota", node.get("ota"));

            //root.put("scope", "all");
        }
        catch (Exception e) {
            return null;
        }

        return root;
    }


    public static ObjectNode AllJsonBuilder(String[] scope, HFDevRouter router) {
        ObjectNode      root = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode node;

            root = mapper.createObjectNode();

            if(contains(scope, "wan") >= 0) {
                node = WANJsonBuilder(router.getWan());
                root.put("wan", node.get("wan"));
            }

            if(contains(scope, "lan") >= 0) {
                node = LANJsonBuilder(router.getLan());
                root.put("lan", node.get("lan"));
            }

            if(contains(scope, "wifi") >= 0) {
                node = WiFiJsonBuilder(router.getWifi());
                root.put("wifi", node.get("wifi"));
            }

            if(contains(scope, "ota") >= 0) {
                node = OTAJsonBuilder(router.getOta());
                root.put("ota", node.get("ota"));
            }

            //root.put("scope", "all");
        }
        catch (Exception e) {
            return null;
        }

        return root;
    }


    public static ObjectNode AllJsonBuilder(String[] scope, HFDevRouterGroup group) {
        ObjectNode      root = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode node;

            root = mapper.createObjectNode();

            if(contains(scope, "wan") >= 0) {
                node = WANJsonBuilder(DTOUtil.map(group.getWan(), HFDevRouterWanSetup.class));
                root.put("wan", node.get("wan"));
            }

            if(contains(scope, "lan") >= 0) {
                node = LANJsonBuilder(DTOUtil.map(group.getLan(), HFDevRouterLanSetup.class));
                root.put("lan", node.get("lan"));
            }

            if(contains(scope, "wifi") >= 0) {
                node = WiFiJsonBuilder(DTOUtil.map(group.getWifi(), HFDevRouterWiFiSetup.class));
                root.put("wifi", node.get("wifi"));
            }

            if(contains(scope, "ota") >= 0) {
                node = OTAJsonBuilder(DTOUtil.map(group.getOta(), HFDevRouterOtaSetup.class));
                root.put("ota", node.get("ota"));
            }

            //root.put("scope", "all");
        }
        catch (Exception e) {
            return null;
        }

        return root;
    }


    public static int contains(String[] arry, String str) {
        for(int i = 0; i < arry.length; i++) {
            if(arry[i].equalsIgnoreCase(str)) {
                return i;
            }
        }
        return -1;
    }
    */
}