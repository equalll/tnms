package com.tigercel.tnms.service.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tigercel.tnms.model.router.HFDevRouter;
import com.tigercel.tnms.model.router.HFDevRouterGroup;
import com.tigercel.tnms.model.router.setup.HFDevRouterLanSetup;
import com.tigercel.tnms.model.router.setup.HFDevRouterOtaSetup;
import com.tigercel.tnms.model.router.setup.HFDevRouterWanSetup;
import com.tigercel.tnms.model.router.setup.HFDevRouterWiFiSetup;
import com.tigercel.tnms.utils.ClutterUtils;
import com.tigercel.tnms.utils.DTOUtil;
import org.springframework.stereotype.Component;

/**
 * Created by somebody on 2016/8/15.
 */
@Component
public class TNMSJsonBuilder <T> {

    private void JsonHeader(ObjectNode node, String src, String dst, String type) {
        node.put("srcid", src);
        node.put("desid", dst);
        node.put("type", type);
    }


    private ObjectNode JsonBodyBuilder(ObjectNode body, T t, String scope) {
        ObjectNode      node;
        String[]        sc = scope.split(",");

        if(t instanceof HFDevRouterWanSetup) {
            node = TNMSJsonBodyBuilder.WANJsonBuilder((HFDevRouterWanSetup)t);
            body.set("wan", node);
        }
        else if(t instanceof HFDevRouterLanSetup) {
            node = TNMSJsonBodyBuilder.LANJsonBuilder((HFDevRouterLanSetup)t);
            body.set("lan", node);
        }
        else if(t instanceof HFDevRouterWiFiSetup) {
            node = TNMSJsonBodyBuilder.WiFiJsonBuilder((HFDevRouterWiFiSetup)t);
            body.set("wifi", node);
        }
        else if(t instanceof HFDevRouterOtaSetup) {
            node = TNMSJsonBodyBuilder.OTAJsonBuilder((HFDevRouterOtaSetup)t);
            body.set("ota", node);
        }
        else if(t instanceof HFDevRouter) {
            if(ClutterUtils.contains(sc, "all") >= 0) {
                node = TNMSJsonBodyBuilder.WANJsonBuilder(((HFDevRouter)t).getWan());
                body.set("wan", node);
                node = TNMSJsonBodyBuilder.LANJsonBuilder(((HFDevRouter)t).getLan());
                body.set("lan", node);
                node = TNMSJsonBodyBuilder.WiFiJsonBuilder(((HFDevRouter)t).getWifi());
                body.set("wifi", node);
                node = TNMSJsonBodyBuilder.OTAJsonBuilder(((HFDevRouter)t).getOta());
                body.set("ota", node);
            }
            else {
                if(ClutterUtils.contains(sc, "wan") >= 0) {
                    node = TNMSJsonBodyBuilder.WANJsonBuilder(((HFDevRouter)t).getWan());
                    body.set("wan", node);
                }

                if(ClutterUtils.contains(sc, "lan") >= 0) {
                    node = TNMSJsonBodyBuilder.LANJsonBuilder(((HFDevRouter)t).getLan());
                    body.set("lan", node);
                }

                if(ClutterUtils.contains(sc, "wifi") >= 0) {
                    node = TNMSJsonBodyBuilder.WiFiJsonBuilder(((HFDevRouter)t).getWifi());
                    body.set("wifi", node);
                }

                if(ClutterUtils.contains(sc, "ota") >= 0) {
                    node = TNMSJsonBodyBuilder.OTAJsonBuilder(((HFDevRouter)t).getOta());
                    body.set("ota", node);
                }
            }

        }
        else if(t instanceof HFDevRouterGroup) {
            HFDevRouterGroup group = (HFDevRouterGroup) t;

            if(ClutterUtils.contains(sc, "all") >= 0) {
                node = TNMSJsonBodyBuilder.WANJsonBuilder(DTOUtil.map(group.getWan(), HFDevRouterWanSetup.class));
                body.set("wan", node);

                node = TNMSJsonBodyBuilder.LANJsonBuilder(DTOUtil.map(group.getLan(), HFDevRouterLanSetup.class));
                body.set("lan", node);

                node = TNMSJsonBodyBuilder.WiFiJsonBuilder(DTOUtil.map(group.getWifi(), HFDevRouterWiFiSetup.class));
                body.set("wifi", node);

                node = TNMSJsonBodyBuilder.OTAJsonBuilder(DTOUtil.map(group.getOta(), HFDevRouterOtaSetup.class));
                body.set("ota", node);
            }
            else {
                if(ClutterUtils.contains(sc, "wan") >= 0) {
                    node = TNMSJsonBodyBuilder.WANJsonBuilder(DTOUtil.map(group.getWan(), HFDevRouterWanSetup.class));
                    body.set("wan", node);
                }

                if(ClutterUtils.contains(sc, "lan") >= 0) {
                    node = TNMSJsonBodyBuilder.LANJsonBuilder(DTOUtil.map(group.getLan(), HFDevRouterLanSetup.class));
                    body.set("lan", node);
                }

                if(ClutterUtils.contains(sc, "wifi") >= 0) {
                    node = TNMSJsonBodyBuilder.WiFiJsonBuilder(DTOUtil.map(group.getWifi(), HFDevRouterWiFiSetup.class));
                    body.set("wifi", node);
                }

                if(ClutterUtils.contains(sc, "ota") >= 0) {
                    node = TNMSJsonBodyBuilder.OTAJsonBuilder(DTOUtil.map(group.getOta(), HFDevRouterOtaSetup.class));
                    body.set("ota", node);
                }
            }

        }
        else {
            return null;
        }

        return body;
    }


    public ObjectNode JsonDataBuilder(T t, String src, String dst, String type, String scope) {

        ObjectNode      root;
        ObjectNode      head;
        ObjectNode      body;


        try {
            ObjectMapper mapper = new ObjectMapper();

            root = mapper.createObjectNode();
            head = mapper.createObjectNode();
            body = mapper.createObjectNode();

            JsonHeader(head, src, dst, type);
            JsonBodyBuilder(body, t, scope);

            root.set("head", head);
            root.set("body", body);
        }
        catch (Exception e) {
            return null;
        }

        return root;
    }



}
