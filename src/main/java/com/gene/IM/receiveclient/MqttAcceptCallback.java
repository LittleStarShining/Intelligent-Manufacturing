package com.gene.IM.receiveclient;

import cn.hutool.db.Db;
import cn.hutool.json.JSONObject;

import com.gene.IM.DTO.MaterialDTO;
import com.gene.IM.entity.OrderInfo;
import com.gene.IM.mapper.MaterialMapper;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.gene.IM.sendclient.Send_Client1;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static com.gene.IM.api.MqttApi.connect;

// 之前任务需要，后续可更改
@Component
public class MqttAcceptCallback implements MqttCallbackExtended {

    private static final Logger logger = LoggerFactory.getLogger(MqttAcceptCallback.class);

    public static BlockingQueue<JSONObject> massageQueue = new ArrayBlockingQueue<>(20);
    public static HashSet<String> macSet = new HashSet<>(); // 存储已经接收过的字符串

    public static int line1_num = 0;
    public static int line2_num = 0;
    public static int line3_num = 0;
    private JSONObject mac = new JSONObject();
    @Autowired
    private MqttAcceptClient mqttAcceptClient;
    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    @Qualifier("send_Client1")
    private Send_Client1  client8;
    
    
    @Autowired
    private JdbcTemplate jdbc;

    /**
     * 客户端断开后触发
     *
     * @param throwable
     */
    @Override
    public void connectionLost(Throwable throwable) {
        logger.info("连接断开，可以做重连");
        if (MqttAcceptClient.client == null || !MqttAcceptClient.client.isConnected()) {
            logger.info("emqx重新连接....................................................");
   
        }
    }

    /**
     * 客户端收到消息触发
     *
     * @param topic       主题
     * @param mqttMessage 消息
     */
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception, SQLException {
    	String s =  new String(mqttMessage.getPayload());
        logger.info("接收消息主题 : " + topic);
        logger.info("接收消息Qos : " + mqttMessage.getQos());
        logger.info("接收的消息内容 : " + s);
//        Date date3 = DateUtil.date(System.currentTimeMillis());
//        //时间
//        String formatDateTime = DateUtil.formatDateTime(date3);

     // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 格式化日期为 yyyy-MM-dd
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 获取当前时间
        LocalTime currentTime = LocalTime.now();
        // 格式化时间为 HH:mm:ss
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));




        String payload = mqttMessage.toString();
        if(topic.equals("TopicA")) {
        	System.out.println("TTTTTTT");
            JSONObject jsonObject = new JSONObject(mqttMessage.toString());
            logger.info("接收的消息内容: " + jsonObject);

//            Db.use().execute("INSERT INTO task1table(temp,wet,status)VALUES(?,?,?)", temp, wet, status);
            System.out.println((int)jsonObject.get("temp")+(int)jsonObject.get("wet")+jsonObject.getStr("status"));
            int a = jdbc.update("insert into task1table(temp,wet,status,date,time) values (?, ?,?, CURRENT_DATE, CURRENT_TIME)",  (int)jsonObject.get("temp"), (int)jsonObject.get("wet"),jsonObject.getStr("status"));

            if((int)jsonObject.get("temp")>=30) {
            	System.out.println("temp");

        	System.out.println(client8.publish(false ,"TopicC","on"));
        	
        }
            
            
        } else if (topic.equals("topicB")) {
//            try {
//                massageQueue.put(payload);
//                if (massageQueue.size() == 2) {
//                    int averageTemp = 0;
//                    int averageWet = 0;
//                    String status = null;
//                    // 处理队列中的数据
//                    for (String data : massageQueue) {
//                        System.out.println("Processing: " + data);
//                        JSONObject jsonObject = new JSONObject(data);
//                        int temp = (int) jsonObject.get("temp");
//                        int wet = (int) jsonObject.get("wet");
//                        status = (String) jsonObject.get("status");
//                        averageTemp+=temp;
//                        averageWet+=wet;
//                    }
//                    averageTemp/=2;
//                    averageWet/=2;
//
//                    Db.use().execute("INSERT INTO average(av_temp,av_wet,status,date,time) VALUES(?,?,?,?,?)", averageTemp, averageWet,status,formattedDate,formattedTime);
//
//                    // 清空队列
//                    massageQueue.clear();
//
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        //获取收到的硬件设备信息
        else if(topic.equals("publish")) {
//            mac.get()
            if(!macSet.contains(mqttMessage.toString())){
                macSet.add(mqttMessage.toString());
            }
            System.out.println(macSet);
            //如果已经连接
            if(connect){
                JSONObject json = new JSONObject(mqttMessage.toString());
                //获取通过瓶数，并更新material表的数量
                if (json.getStr("Mac").equals("001")) {
                    int num_temp1 = line1_num;
                    int num_temp2 = line2_num;
                    int num_temp3 = line3_num;
                    if(json.getInt("line")==1){
                        line1_num = json.getInt("Num");
                        MaterialDTO m = materialMapper.getLineOrderMaterial(1);
                        materialMapper.decreaseMaterial(m.getMaterialId(),m.getQuantity()*(line1_num-num_temp1));
                        num_temp1 = line1_num;
                    }
                    else if(json.getInt("line")==2){
                        line2_num = json.getInt("Num");
                        MaterialDTO m = materialMapper.getLineOrderMaterial(2);
                        materialMapper.decreaseMaterial(m.getMaterialId(),m.getQuantity()*(line2_num-num_temp2));
                        num_temp2 = line2_num;
                    }
                    else if(json.getInt("line")==3){
                        line3_num = json.getInt("Num");
                        MaterialDTO m = materialMapper.getLineOrderMaterial(3);
                        materialMapper.decreaseMaterial(m.getMaterialId(),m.getQuantity()*(line3_num-num_temp3));
                        num_temp3 = line3_num;
                    }

                }
                if (!massageQueue.offer(json)) {
                    // 队列已满，弹出头元素
                    JSONObject oldMessage = massageQueue.poll();
                    // 输出弹出的消息，您可以根据需求处理该消息
                    System.out.println("队列已满，弹出的消息为：" + oldMessage.toString());
                    massageQueue.offer(json);

                }
            }
        	 
        }
    }

    /**
     * 发布消息成功
     *
     * @param token token
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        String[] topics = token.getTopics();
        for (String topic : topics) {
            logger.info("向主题：" + topic + "发送消息成功！");
        }
        try {
            MqttMessage message = token.getMessage();
            byte[] payload = message.getPayload();
            String s = new String(payload, "UTF-8");
            logger.info("消息的内容是：" + s);
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接emq服务器后触发
     *
     * @param b
     * @param s
     */
    @Override
    public void connectComplete(boolean b, String s) {
        logger.info("--------------------ClientId:"
                + MqttAcceptClient.client.getClientId() + "客户端连接成功！--------------------");
        // 以/#结尾表示订阅所有以test开头的主题
        // 订阅所有机构主题
//        mqttAcceptClient.subscribe("testtopic/#", 0);
        mqttAcceptClient.subscribe("publish", 0);
    }
}
