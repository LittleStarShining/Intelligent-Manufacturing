package com.gene.IM.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.gene.IM.receiveclient.MqttAcceptClient;
import com.gene.IM.receiveclient.MqttAcceptClient2;

@Configuration
public class MqttConfig {

    @Autowired
    private MqttAcceptClient mqttAcceptClient;
    
    @Autowired
    private MqttAcceptClient2 mqttAcceptClient2;
    /**
     * 订阅mqtt
     *
     * @return
     */
    @Conditional(MqttCondition.class)
    @Bean
    public MqttAcceptClient getMqttPushClient() {
        mqttAcceptClient.connect();
        return mqttAcceptClient;
    }
    
    @Conditional(MqttCondition.class)
    @Bean
    public MqttAcceptClient2 getMqttPushClient2() {
        mqttAcceptClient2.connect();
        return mqttAcceptClient2;
    }




}

