package com.gene.IM.api;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gene.IM.sendclient.Send_Client1;
import com.gene.IM.service.MqttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

@RestController
@RequestMapping("")
public class MqttApi {

    /**
     *  http:/localhost:9091/sendmsg
     */
    @Autowired
    private Send_Client1 client1;

    @Autowired
    private Send_Client1  client2;


    @Autowired
    private MqttService mqttService;

    @GetMapping("/getLast")
    public Map getLast() {
        return mqttService.selectLastData();
    }
    @GetMapping("/getLastFive")
    public Map getLastFive() {
        return mqttService.selectFiveDate();
    }


    @GetMapping(value = "/sendmsg")
    public Object publishTopic(String sendMessage) {
        sendMessage= "{uname:lz,age:18}";
        client1.publish(false ,"receiveTopic",sendMessage);
        return null;
    }


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
