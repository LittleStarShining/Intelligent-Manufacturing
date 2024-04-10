package com.gene.IM.api;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gene.IM.JWT.annotation.NotNeedJWT;
import com.gene.IM.entity.CommonResult;
import com.gene.IM.entity.Material;
import com.gene.IM.sendclient.Send_Client1;
import com.gene.IM.service.DeviceService;
import com.gene.IM.service.MqttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static com.gene.IM.receiveclient.MqttAcceptCallback.massageQueue;


// 之前任务需要，后续可更改
@RestController
@CrossOrigin
@RequestMapping("/mqtt")
public class MqttApi {

    public static BlockingQueue<JSONObject> macQueue = new ArrayBlockingQueue<JSONObject>(30);

    //设备是否连接
    public static boolean connect=false;
    /**
     *  http:/localhost:9091/sendmsg
     */
    @Autowired
    @Qualifier("send_Client1")
    private Send_Client1 client1;

    @Autowired
    @Qualifier("send_Client1")
    private Send_Client1  client2;


    @Autowired
    private MqttService mqttService;

    @Autowired
    private DeviceService deviceService;
    @NotNeedJWT
    @GetMapping("/getLast")
    public Map getLast() {
        return mqttService.selectLastData();
    }
    @GetMapping("/getLastFive")
    public Map getLastFive() {
        return mqttService.selectFiveDate();
    }
    @NotNeedJWT
    @GetMapping("/getAllDevices")
    public JSONObject getAllDevices() {

        JSONObject allDevices = JSONUtil.createObj();
        while (!macQueue.isEmpty()){
            allDevices.append("设备",macQueue.poll());
        }

        return allDevices;
        /*
        {
            "设备": [
                {
                    "mac": "02",
                    "describe": "实时"
                },
                {
                    "mac": "01",
                    "describe": "风扇"
                }
            ]
        }
         */
    }

    @NotNeedJWT
    @PostMapping("/connectDevices")
    public CommonResult<List<String>> connectDevices(@RequestBody JSONArray devices) {
connect=true;
System.out.println(devices);
        return new CommonResult<List<String>>(deviceService.connectDevices(devices));

    }

    @NotNeedJWT
    @GetMapping("/disconnectDevices")
    public CommonResult<String> disconnectDevices() {
        connect=false;
        //清空消息队列(事实上应该在/getAllDevices已经清空了），等待硬件再次发布mac信息，用户访问/connectDevices再次连接
        macQueue.clear();

        return new CommonResult<String>("已断开连接，等待下次连接");

    }

    @NotNeedJWT
    @GetMapping("/getHumTempFlame")
    public CommonResult<JSONObject> getHumTempFlame() {

        return new CommonResult<JSONObject>(deviceService.getHumTempFlame());


    }

    @NotNeedJWT
    @GetMapping("/getPass")
    public CommonResult<JSONObject> getPass() {

        return new CommonResult<JSONObject>(deviceService.getPass());


    }

    @NotNeedJWT
    @GetMapping(value = "/sendmsg")
    public Object publishTopic(String sendMessage) {
        sendMessage= "{uname:lz,age:18}";
        client1.publish(false ,"receiveTopic",sendMessage);
        return null;
    }


    @NotNeedJWT
    @GetMapping(value = "/statistics")
    public JSONObject publishTopic() throws SQLException {
        Map result = mqttService.getTemp();
        if(result!=null) {
            JSONObject json1 = JSONUtil.createObj()
                    .put("舒适",result.get("avg_temp_on"))
                    .put("燥热",result.get("avg_temp_off"))
                    .put("status","ok");
            return json1;
        }else {
            JSONObject json1 = JSONUtil.createObj()
                    .put("status","error");
            return json1;
        }

    }

    @PostMapping(value = "/windctl")
    public JSONObject windctl(@RequestBody JSONObject sendMessage) {
        String msg = sendMessage.getStr("switch");
        JSONObject json1 = JSONUtil.createObj();
        if(client1.publish(false ,"TopicC",msg)==1) {
            json1.put("code", 1)
                    .put("desc", "修改成功")
                    .put("status", msg);
        }else {
            json1.put("code", 0)
                    .put("desc", "修改失败");
        }

        return json1;    }


    @PostMapping(value = "/waterctl")
    public JSONObject waterctl(@RequestBody JSONObject sendMessage) {
        String msg = sendMessage.getStr("switch");
//        return client2.publish(false ,"TopicD",msg);
        client2.publish(false ,"TopicD",msg);
        return sendMessage;
    }
}
