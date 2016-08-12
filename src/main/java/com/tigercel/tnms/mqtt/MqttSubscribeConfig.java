package com.tigercel.tnms.mqtt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * Created by freedom on 2016/5/24.
 */
@Configuration
@IntegrationComponentScan
@Data
public class MqttSubscribeConfig {

    private DefaultMqttPahoClientFactory        factory = null;
    private MqttPahoMessageDrivenChannelAdapter adapter = null;
    private MessageHandler                      mh = null;

    @Value("${spring.mqtt.server-uri}")
    private String serverURI;

    @Value("${spring.mqtt.username}")
    private String username;

    @Value("${spring.mqtt.password}")
    private String password;

    @Value("${spring.mqtt.clientid-sub}")
    private String clientid;

    @Value("${spring.mqtt.topic.sub.deviceStatus}")
    public String topicSubDeviceStatus;

    @Value("${spring.mqtt.topic.sub.SYS.disconnected}")
    public String topicSubSYSDisconnected;

    @Value("${spring.mqtt.topic.sub.SYS.connected}")
    public String topicSubSYSConnected;

    @Bean
    public MqttPahoClientFactory mqttInBoundClientFactory() {

        factory = new DefaultMqttPahoClientFactory();
        factory.setServerURIs(serverURI);
        factory.setUserName(username);
        factory.setPassword(password);

        return factory;
    }


    /* subscribe topic - device/ap/status/id/# */
    @Bean
    public MessageChannel mqttStatusInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer statusInbound() {

        adapter = new MqttPahoMessageDrivenChannelAdapter(clientid,
                mqttInBoundClientFactory(), topicSubDeviceStatus);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttStatusInputChannel());

        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttStatusInputChannel")
    public MessageHandler statusHandler() {

        mh = new MqttSubStatusMessageHandler();

        return mh;
    }
    /* end */


    /* subscribe topic - $SYS/brokers/emqttd@127.0.0.1/clients/+/disconnected */
    @Bean
    public MessageChannel mqttSysInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer sysInbound() {

        adapter = new MqttPahoMessageDrivenChannelAdapter(clientid + "-sys-disc", mqttInBoundClientFactory(),
                topicSubSYSDisconnected);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttSysInputChannel());

        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttSysInputChannel")
    public MessageHandler sysTopicHandler() {

        mh = new MqttSubSYSDisconnectedHandler();

        return mh;
    }
    /* end */


    /* subscribe topic - $SYS/brokers/emqttd@127.0.0.1/clients/+/connected */
    @Bean
    public MessageChannel mqttSysConnInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer sysConnInbound() {

        adapter = new MqttPahoMessageDrivenChannelAdapter(clientid + "-sys-conn", mqttInBoundClientFactory(),
                topicSubSYSConnected);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttSysConnInputChannel());

        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttSysConnInputChannel")
    public MessageHandler sysConnTopicHandler() {

        mh = new MqttSubSYSConnectedHandler();

        return mh;
    }
    /* end */
}