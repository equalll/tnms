package com.tigercel.tnms.mqtt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by freedom on 2016/5/24.
 */
@Configuration
@IntegrationComponentScan
@Data
public class MqttPublishConfig {
    private DefaultMqttPahoClientFactory    factory = null;
    private MqttPahoMessageHandler          mpmh = null;


    @Value("${spring.mqtt.server-uri}")
    private String serverURI;

    @Value("${spring.mqtt.username}")
    private String username;

    @Value("${spring.mqtt.password}")
    private String password;

    @Value("${spring.mqtt.clientid-pub}")
    private String clientid;

    @Bean
    public MqttPahoClientFactory mqttOutBoundClientFactory() {

        factory = new DefaultMqttPahoClientFactory();
        factory.setServerURIs(serverURI);
        factory.setUserName(username);
        factory.setPassword(password);

        return factory;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {

        mpmh = new MqttPahoMessageHandler(clientid, mqttOutBoundClientFactory());
        mpmh.setAsync(true);
        mpmh.setDefaultTopic("hello");
        mpmh.setAsyncEvents(true);

        return mpmh;
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Component
    @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
    public interface PubGateway {

        @Async
        void sendToMqtt(String data);
    }

}