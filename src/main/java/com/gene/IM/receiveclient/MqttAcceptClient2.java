package com.gene.IM.receiveclient;

import cn.hutool.core.date.DateUtil;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.gene.IM.util.MqttProperties;

import java.util.Date;

@Component
public class MqttAcceptClient2 {

    private static final Logger logger = LoggerFactory.getLogger(MqttAcceptClient2.class);

    @Autowired
    private MqttAcceptCallback2 mqttAcceptCallback;

    @Autowired
    private MqttProperties mqttProperties;
    
    @Autowired
    private JdbcTemplate jdbc;

    public static MqttClient client;

    private static MqttClient getClient() {
        return client;
    }

    private static void setClient(MqttClient client) {
        MqttAcceptClient2.client = client;
    }

    /**
     * 客户端连接
     */
    public void connect() {
        MqttClient client;
//        Date date3 = DateUtil.date(System.currentTimeMillis());
//        //时间
//        String formatDateTime = DateUtil.formatDateTime(date3);

        try {
            //client = new MqttClient("tcp://127.0.0.1:1883",System.currentTimeMillis()+"zhan2-c-002", new MemoryPersistence());
        	client = new MqttClient(mqttProperties.getHostUrl(),System.currentTimeMillis()+"zhan2-c-002", new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(mqttProperties.getUsername());
            options.setPassword(mqttProperties.getPassword().toCharArray());
            options.setConnectionTimeout(mqttProperties.getTimeout());
            options.setKeepAliveInterval(mqttProperties.getKeepAlive());
            options.setAutomaticReconnect(mqttProperties.getReconnect());
            options.setCleanSession(mqttProperties.getCleanSession());
            MqttAcceptClient2.setClient(client);
            try {
                // 设置回调
                client.setCallback(mqttAcceptCallback);
                client.connect(options);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重新连接
     */
    public void reconnection() {
        try {
            client.connect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 订阅某个主题
     *
     * @param topic 主题
     * @param qos   连接方式
     */
    public void subscribe(String topic, int qos) {
        logger.info("==============开始订阅主题==============" + topic);
        try {
            client.subscribe(topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消订阅某个主题
     *
     * @param topic
     */
    public void unsubscribe(String topic) {
        logger.info("==============开始取消订阅主题==============" + topic);
        try {
            client.unsubscribe(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}

