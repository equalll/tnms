package com.tigercel.tnms.service.support;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tigercel.tnms.model.router.HFDevRouter;
import com.tigercel.tnms.model.router.HFDevRouterGroup;
import com.tigercel.tnms.model.router.HFDevRouterStatus;
import com.tigercel.tnms.model.router.setup.HFDevRouterLanSetup;
import com.tigercel.tnms.model.router.setup.HFDevRouterOtaSetup;
import com.tigercel.tnms.model.router.setup.HFDevRouterWanSetup;
import com.tigercel.tnms.model.router.setup.HFDevRouterWiFiSetup;
import com.tigercel.tnms.service.DevRouterGroupNodeService;
import com.tigercel.tnms.service.DevRouterService;
import com.tigercel.tnms.utils.DataValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by freedom on 2016/5/26.
 */
@Service
@Transactional
public class MqttMsgReceiver {

    @Autowired
    private DevRouterService deviceRouterService;

    @Autowired
    private DevRouterGroupNodeService deviceRouterGroupNodeService;

    private static Logger logger = LoggerFactory.getLogger(MqttMsgReceiver.class);

    public void NMSMsgSingleAPStatus(Message<?> message) {
        //NMSMsgHeaderProcessor(message.getHeaders());

        JsonNode            root;
        ObjectMapper        mapper;
        String              id;
        String              scope;
        HFDevRouter         router;
        HFDevRouterGroup    group;

        logger.info("====== Body: " + message.getPayload().toString());
        try {
            mapper = new ObjectMapper();
            root = mapper.readTree(message.getPayload().toString());

            id = root.path("id").asText();
            scope = root.path("scope").asText();

            if(id.length() < 17) {
                logger.info("====== invalid id: " + id);
                return;
            }

            group = deviceRouterGroupNodeService.findGroupByMAC(id);
            if(group == null) {
                logger.info("====== id: " + id + " does not belong to any group");
                return;
            }

            router = deviceRouterService.findOneByMac(id);
            if(router == null) {
                logger.info("====== id: " + id + " not exist yet");
                router = new HFDevRouter();
                HFDevRouterStatus status = new HFDevRouterStatus();
                router.setUdid(id);
                //router.setOnline(true);
                router.setGroup(group);
                router.setStatus(status);

                if(scope.equals("all")) {
                    NMSMsgAllStatusSave(root, scope, status, router, mapper);
                }
                else {
                    NMSMsgSingleStatusOnlySave(root, scope, status);
                }

                deviceRouterService.save(router);
            }
            else {
                logger.info("====== id: " + id + " found");
                //router.setOnline(true);
                HFDevRouterStatus status = router.getStatus();

                if(scope.equals("all")) {
                    NMSMsgAllStatusOnlySave(root, scope, status);
                }
                else {
                    NMSMsgSingleStatusOnlySave(root, scope, status);
                }
                deviceRouterService.save(router);
            }
        }
        catch(IOException e) {
            logger.error("====== Exception: " + e.getMessage());
            return;
        }
    }

    /**
     *
     * 			{
     "clientid": "blues",
     "username": "undefined",
     "ipaddress": "192.168.8.146",
     "session": false,
     "protocol": 3,
     "connack": 0,
     "ts": 1463036001
     }
     * @param message
     */
    public void NMSMsgSysConnected(Message<?> message) {
        //NMSMsgHeaderProcessor(message.getHeaders());
        JsonNode            root;
        ObjectMapper        mapper;
        String              client;
        String              id;
        HFDevRouter        router;
        HFDevRouterGroup   group;
        //String[]            clientId;

        try {
            mapper = new ObjectMapper();
            root = mapper.readTree(message.getPayload().toString());
            client = root.path("clientid").asText();
            if(client == null || client.length() < 17) {
                return;
            }

            id = client;
            if(DataValidationUtil.isMACValid(id) == false) {
                return;
            }

            group = deviceRouterGroupNodeService.findGroupByMAC(id);
            if(group == null) {
                logger.info("====== id: " + id + " does not belong to any group");
                return;
            }

            router = deviceRouterService.findOneByMac(id);
            if(router == null) {
                return;
            }

            router.setOnline(true);
            router.setIp(root.path("ipaddress").asText());
            deviceRouterService.save(router);

        }
        catch (Exception e) {
            return;
        }
    }

    /**
     * 			{
     "clientid": "blues",
     "reason": "closed",
     "ts": 1463036015
     }
     * clientid :   11:22:33:44:55:66-sub-0
     *              11:22:33:44:55:66-sub-1
     *              11:22:33:44:55:66-pub-0
     * @param message
     */
    public void NMSMsgSysDisconnected(Message<?> message) {
        //NMSMsgHeaderProcessor(message.getHeaders());

        JsonNode            root;
        ObjectMapper        mapper;
        String              client;
        String              id;
        HFDevRouter        router;
        HFDevRouterGroup   group;

        try {
            mapper = new ObjectMapper();
            root = mapper.readTree(message.getPayload().toString());
            client = root.path("clientid").asText();
            if(client == null || client.length() < 17) {
                return;
            }

            id = client;
            if(DataValidationUtil.isMACValid(id) == false) {
                return;
            }

            group = deviceRouterGroupNodeService.findGroupByMAC(id);
            if(group == null) {
                logger.info("====== id: " + id + " does not belong to any group");
                return;
            }

            router = deviceRouterService.findOneByMac(id);
            if(router == null) {
                return;
            }

            router.setOnline(false);
            deviceRouterService.save(router);

        }
        catch (Exception e) {
            return;
        }
    }


    private void NMSMsgHeaderProcessor(MessageHeaders headers) {

        /*
        Iterator<Map.Entry<String, Object>> entries = headers.entrySet().iterator();
        while (entries.hasNext()) {

            Map.Entry<String, Object> entry = entries.next();

            System.out.println("=== Key: " + entry.getKey() + ", Value: " + entry.getValue());

        }
        */
    }

    /**
     *  only save single status field
     * @param root
     * @param scope
     * @param status
     */
    private void NMSMsgSingleStatusOnlySave(JsonNode root, String scope, HFDevRouterStatus status) {
        JsonNode node;

        node = root.path(scope);
        if(scope.equals("wan")) {
            status.setWan(node.toString());
        }
        else if(scope.equals("lan")) {
            status.setLan(node.toString());
        }
        else if(scope.equals("wifi")) {
            status.setWifi(node.toString());
        }
        else if(scope.equals("version")) {
            status.setVersion(node.toString());
        }
        else if(scope.equals("system")) {
            status.setSystem(node.toString());
        }
        else if(scope.equals("ota")) {
            status.setOta(node.toString());
        }
        else if(scope.equals("client")) {
            status.setClient(node.toString());
        }
    }

    /**
     * only save all status fields
     * @param root
     * @param scope
     * @param status
     */
    private void NMSMsgAllStatusOnlySave(JsonNode root, String scope, HFDevRouterStatus status) {
        JsonNode node;

        node = root.path("wan");
        if(node != null) {
            status.setWan(node.toString());
        }

        node = root.path("lan");
        if(node != null) {
            status.setLan(node.toString());
        }

        node = root.path("wifi");
        if(node != null) {
            status.setWifi(node.toString());
        }

        node = root.path("version");
        if(node != null) {
            status.setVersion(node.toString());
        }

        node = root.path("system");
        if(node != null) {
            status.setSystem(node.toString());
        }

        node = root.path("ota");
        if(node != null) {
            status.setOta(node.toString());
        }

        node = root.path("client");
        if(node != null) {
            status.setClient(node.toString());
        }
    }

    /**
     * save all status and settings
     * @param root
     * @param scope
     * @param status
     * @param router
     * @param mapper
     */
    private void NMSMsgAllStatusSave(JsonNode root, String scope, HFDevRouterStatus status,
                                     HFDevRouter router, ObjectMapper mapper) {
        JsonNode node;

        try {
            node = root.path("wan");
            if(node != null) {
                router.setWan(mapper.readValue(node.toString(), HFDevRouterWanSetup.class));
                status.setWan(node.toString());
            }

            node = root.path("lan");
            if(node != null) {
                router.setLan(mapper.readValue(node.toString(), HFDevRouterLanSetup.class));
                status.setLan(node.toString());
            }

            node = root.path("wifi");
            if(node != null) {
                router.setWifi(mapper.readValue(node.toString(), HFDevRouterWiFiSetup.class));
                status.setWifi(node.toString());
            }

            node = root.path("ota");
            if(node != null) {
                router.setOta(mapper.readValue(node.toString(), HFDevRouterOtaSetup.class));
                status.setOta(node.toString());
            }

            node = root.path("version");
            if(node != null) {
                status.setVersion(node.toString());
            }

            node = root.path("system");
            if(node != null) {
                status.setSystem(node.toString());
            }

            node = root.path("client");
            if(node != null) {
                status.setClient(node.toString());
            }
        }
        catch (Exception e) {
            return;
        }
    }
}
