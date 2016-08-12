package com.tigercel.tnms.service.support;


import com.tigercel.tnms.mqtt.MqttPublishConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.stereotype.Component;

/**
 * Created by freedom on 2016/5/25.
 */
@Component
public class MqttMsgSender {

    @Autowired
    private MqttPublishConfig mqttPublishConfig;

    @Autowired
    private MqttPublishConfig.PubGateway gateway;


    @Value("${spring.mqtt.topic.pub.deviceSetup}")
    private String topicPubDeviceSetup;

    @Value("${spring.mqtt.topic.pub.groupSetup}")
    private String topicPubGroupSetup;

    /* single */
    public void MqttMsgRouterReset(String id) {
        MqttPahoMessageHandler mpmh = mqttPublishConfig.getMpmh();
        mpmh.setDefaultTopic(topicPubDeviceSetup + id);
        gateway.sendToMqtt("{\"scope\": \"reset\", \"reset\": \"true\"}");
    }

    public void MqttMsgRouterReboot(String id) {
        MqttPahoMessageHandler mpmh = mqttPublishConfig.getMpmh();
        mpmh.setDefaultTopic(topicPubDeviceSetup + id);
        gateway.sendToMqtt("{\"scope\": \"reboot\", \"reboot\": \"true\"}");
    }


    public void MqttMsgRouterSingleSetup(String id, String settings) {
        MqttPahoMessageHandler mpmh = mqttPublishConfig.getMpmh();
        mpmh.setDefaultTopic(topicPubDeviceSetup + id);
        gateway.sendToMqtt(settings);
    }

    /* group */
    public void MqttMsgRouterGroupReset(String groupId) {
        MqttPahoMessageHandler mpmh = mqttPublishConfig.getMpmh();
        mpmh.setDefaultTopic(topicPubGroupSetup + groupId);
        gateway.sendToMqtt("{\"scope\": \"reset\", \"reset\": \"true\"}");
    }

    public void MqttMsgRouterGroupReboot(String groupId) {
        MqttPahoMessageHandler mpmh = mqttPublishConfig.getMpmh();
        mpmh.setDefaultTopic(topicPubGroupSetup + groupId);
        gateway.sendToMqtt("{\"scope\": \"reboot\", \"reboot\": \"true\"}");
    }

    public void MqttMsgRouterGroupSetup(String groupId, String settings) {
        MqttPahoMessageHandler mpmh = mqttPublishConfig.getMpmh();
        mpmh.setDefaultTopic(topicPubGroupSetup + groupId);
        gateway.sendToMqtt(settings);
    }
}