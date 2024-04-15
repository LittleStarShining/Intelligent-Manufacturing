package com.gene.IM.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.gene.IM.sendclient.Send_Client1;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
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
//    @Autowired
//    public static Send_Client1 send_client1;

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

//    @Conditional(MqttCondition.class)
//    @Bean
//    public Send_Client1 SwitchOnAllDevices() throws MqttException {
////        //消毒
////        JSONObject sterilization = JSONUtil.createObj().putOnce("Mac","")
////                .putOnce("status","on")
////                .putOpt("num","900");
////        //罐装
////        JSONObject wrap = JSONUtil.createObj().putOnce("Mac","")
////                .putOnce("status","on")
////                .putOpt("level","0.8");
////        //贴条
////        JSONObject barcode = JSONUtil.createObj().putOnce("Mac","")
////                .putOnce("status","on");
////
////        //入库
////        JSONObject end = JSONUtil.createObj().putOnce("Mac","")
////                .putOnce("status","on")
////                .putOpt("num","900");
////
////        JSONArray array = JSONUtil.createArray().put(sterilization)
////                .put(wrap)
////                .put(barcode)
////                .put(end);
//
//        send_client1.publish(false ,"subtopic","on");
//        return send_client1;
//    }



}

