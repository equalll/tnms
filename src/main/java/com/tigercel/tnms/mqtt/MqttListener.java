package com.tigercel.tnms.mqtt;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.integration.mqtt.event.MqttMessageDeliveredEvent;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by freedom on 2016/5/5.
 */
@Component
public class MqttListener implements SmartApplicationListener {

    @Override
    public boolean supportsEventType(final Class<? extends ApplicationEvent> eventType) {

        return eventType == MqttMessageDeliveredEvent.class;
    }

    @Override
    public boolean supportsSourceType(final Class<?> sourceType) {

        return sourceType == MqttPahoMessageHandler.class;
    }

    @Async
    @Override
    public void onApplicationEvent(final ApplicationEvent event) {
        //System.out.println("====== MqttListener : " + (MqttMessageDeliveredEvent) event);
    }

    @Override
    public int getOrder() {

        return 1;
    }
}
