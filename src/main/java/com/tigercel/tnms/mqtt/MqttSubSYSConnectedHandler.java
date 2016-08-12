package com.tigercel.tnms.mqtt;

import com.tigercel.tnms.service.support.MqttMsgReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.scheduling.annotation.Async;

/**
 * Created by freedom on 2016/5/26.
 */
public class MqttSubSYSConnectedHandler implements MessageHandler {
    @Autowired
    private MqttMsgReceiver mqttMsgReceiver;

    private static Logger logger = LoggerFactory.getLogger(MqttSubSYSConnectedHandler.class);

    @Async
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        logger.debug("====== Connected =====\n" +
                "====== header: " + message.getHeaders().toString() +
                "====== body: " + message.getPayload().toString() +
                "====== connected end =======");
        mqttMsgReceiver.NMSMsgSysConnected(message);
    }
}
