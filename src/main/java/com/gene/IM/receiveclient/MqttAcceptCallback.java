package com.gene.IM.receiveclient;

import cn.hutool.db.Db;
import cn.hutool.json.JSONObject;

import cn.hutool.json.JSONUtil;
import com.gene.IM.DTO.MaterialDTO;
import com.gene.IM.entity.OrderInfo;
import com.gene.IM.mapper.DeviceMapper;
import com.gene.IM.mapper.MaterialMapper;
import com.gene.IM.mapper.OrderInfoMapper;
import com.gene.IM.service.OrderService;
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
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static com.gene.IM.api.MqttApi.*;

// 之前任务需要，后续可更改
@Component
public class MqttAcceptCallback implements MqttCallbackExtended {

    private static final Logger logger = LoggerFactory.getLogger(MqttAcceptCallback.class);

    public static BlockingQueue<JSONObject> massageQueue = new ArrayBlockingQueue<>(20);
    public static HashSet<String> macSet = new HashSet<>(); // 存储已经接收过的字符串
    //进入流水线的订单商品个数（小于等于订单商品个数），第一个节点检测个数
    public static int line1_num = 0;
    public static int line2_num = 0;
    public static int line3_num = 0;
    //离开流水线的订单商品个数（小于等于订单个数），最后一个节点检测个数
    public static int line1_pass_num = 0;
    public static int line2_pass_num = 0;
    public static int line3_pass_num = 0;
    //流水线累积通过商品个数
    public static int line1_cumulation_num=0;
    public static int line2_cumulation_num=0;
    public static int line3_cumulation_num=0;
    private JSONObject mac = new JSONObject();
    @Autowired
    private MqttAcceptClient mqttAcceptClient;
    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    @Qualifier("send_Client1")
    private Send_Client1  client8;

    @Autowired
    private OrderService orderService;


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
            //若未连接
            if(!connect) {
                if (!macSet.contains(mqttMessage.toString())) {
                    macSet.add(mqttMessage.toString());
                }
                System.out.println("未连接");
                System.out.println(macSet);
            }
            //如果已经连接,将消息转为json存到messageQueue中
            else{
                int num_temp1 = line1_num;
                int num_temp2 = line2_num;
                int num_temp3 = line3_num;
                int pass_num_temp1 = line1_pass_num;
                int pass_num_temp2 = line2_pass_num;
                int pass_num_temp3 = line3_pass_num;
                System.out.println("---------已连接--------");
                JSONObject json = JSONUtil.parseObj(mqttMessage.toString());
                System.out.println("---------已连接--------接收到的json："+json);
//                massageQueue.add(json);
                int mac = json.getInt("Mac");
                System.out.println("mac:" + mac);
                System.out.println("mac/3:"+mac/3);
                //获取火灾
                if (mac==0) {
                    isFire = json.getInt("Fire");
                    System.out.println("isFire:"+isFire);

                }
                //获取通过第一个节点的瓶数，并更新material表的数量
                else if (mac %4==1) {
                    System.out.println("mac%3=1:");

                    if((mac+4-1)/4==1){
                        //减原料。更新原料表
                        System.out.println("mac/4=0:");
                        line1_num = json.getInt("Num")-line1_cumulation_num;
                        System.out.println("line1_num:"+line1_num);
                        List<MaterialDTO> materials = materialMapper.getLineOrderMaterial(1);
                        System.out.println(materials);
                        for(MaterialDTO m:materials){
                            System.out.println("减的个数:"+m.getQuantity()*(line1_num-num_temp1));
                            materialMapper.decreaseMaterial(m.getMaterialId(),m.getQuantity()*(line1_num-num_temp1));

                        }
                        num_temp1 = line1_num;
                    }
                    else if((mac+4-1)/4==2){
                        line2_num = json.getInt("Num")-line2_cumulation_num;

                        List<MaterialDTO> materials = materialMapper.getLineOrderMaterial(2);
                        for(MaterialDTO m:materials) {
                            materialMapper.decreaseMaterial(m.getMaterialId(), m.getQuantity() * (line2_num - num_temp2));

                        }
                        num_temp2 = line2_num;
                    }

                    else if((mac+4-1)/4==3){
                        line3_num = json.getInt("Num")-line3_cumulation_num;
                        List<MaterialDTO> materials = materialMapper.getLineOrderMaterial(3);

                        for(MaterialDTO m:materials) {
                            materialMapper.decreaseMaterial(m.getMaterialId(), m.getQuantity() * (line3_num - num_temp3));

                        }
                        num_temp3 = line3_num;

                    }

                }
                //获取水位,mac:2
                else if (mac%4==2) {
                    WaterState = json.getInt("Water_State");

                }

                //获取灌装个数,mac:4
                else if (mac%4==0) {

                    if((mac+4-1)/4==1) {//更新流水线1
                        line1_pass_num = json.getInt("Num")-line1_cumulation_num;
                        if(line1_pass_num==line1OrderNum){
                            //设置当前订单状态为已完成
                            orderInfoMapper.changeStatusByLineID(1);
                            //更新原料need
                            materialMapper.updateMaterialNeed();
                            //获取贪心分配
                            List<List<OrderInfo>> nowList = orderService.greedyAssign();
                            //设置下一个任务为进行中
                            OrderInfo nextTask = nowList.get(2).get(0);
                            orderInfoMapper.changeStatusByOrderID(nextTask.getOrderID(),"处理中");
                            //改变当前流水线处理订单的信息
                            orderInfoMapper.changeLineWorkingOrder(1,nextTask.getOrderID());
                            //记录流水线处理历史
                            orderInfoMapper.addBelongLineOrder(1,nextTask.getOrderID());
                            //更新当前流水线订单商品总数
                            line1OrderNum = deviceMapper.getLineOrderDetail(1).getOrderNum();
                            //更新流水线累积通过量
                            line1_cumulation_num+=line1OrderNum;
                        }
                        pass_num_temp1 = line1_pass_num;
                    }
                    else if((mac+4-1)/4==2){//更新流水线2
                        line2_pass_num = json.getInt("Num")-line2_cumulation_num;
                        if(line2_pass_num==line2OrderNum){
                            //设置当前订单状态为已完成
                            orderInfoMapper.changeStatusByLineID(2);
                            //更新原料need
                            materialMapper.updateMaterialNeed();
                            //获取贪心分配
                            List<List<OrderInfo>> nowList = orderService.greedyAssign();
                            //设置下一个任务为进行中
                            OrderInfo nextTask = nowList.get(2).get(0);
                            orderInfoMapper.changeStatusByOrderID(nextTask.getOrderID(),"处理中");
                            //改变当前流水线处理订单的信息
                            orderInfoMapper.changeLineWorkingOrder(2,nextTask.getOrderID());
                            //记录流水线处理历史
                            orderInfoMapper.addBelongLineOrder(2,nextTask.getOrderID());
                            //更新当前流水线订单商品总数
                            line2OrderNum = deviceMapper.getLineOrderDetail(2).getOrderNum();
                            //更新流水线累积通过量
                            line2_cumulation_num+=line2OrderNum;
                        }
                        pass_num_temp2 = line2_pass_num;
                    }
                    else if((mac+4-1)/4==3){//更新流水线3
                        line3_pass_num = json.getInt("Num")-line3_cumulation_num;
                        if(line3_pass_num==line3OrderNum){
                            //设置当前订单状态为已完成
                            orderInfoMapper.changeStatusByLineID(3);
                            //更新原料need
                            materialMapper.updateMaterialNeed();
                            //获取贪心分配
                            List<List<OrderInfo>> nowList = orderService.greedyAssign();
                            //设置下一个任务为进行中
                            OrderInfo nextTask = nowList.get(2).get(0);
                            orderInfoMapper.changeStatusByOrderID(nextTask.getOrderID(),"处理中");
                            //改变当前流水线处理订单的信息
                            orderInfoMapper.changeLineWorkingOrder(3,nextTask.getOrderID());
                            //记录流水线处理历史
                            orderInfoMapper.addBelongLineOrder(3,nextTask.getOrderID());
                            //更新当前流水线订单商品总数
                            line3OrderNum = deviceMapper.getLineOrderDetail(3).getOrderNum();
                            line2_cumulation_num+=line2OrderNum;
                        }
                        pass_num_temp3 = line3_pass_num;
                    }


                }


                if (!massageQueue.offer(json)) {
                    // 队列已满，弹出头元素
                    JSONObject oldMessage = massageQueue.poll();
                    // 输出弹出的消息，您可以根据需求处理该消息
                    System.out.println("队列已满，弹出的消息为：" + oldMessage.toString());
                    massageQueue.offer(json);

                }
                System.out.println("massageQueue" + massageQueue);

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
