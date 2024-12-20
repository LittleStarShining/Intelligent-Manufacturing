package com.gene.IM.api;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gene.IM.JWT.annotation.NotNeedJWT;
import com.gene.IM.entity.CommonResult;
import com.gene.IM.mapper.DeviceMapper;
import com.gene.IM.sendclient.Send_Client1;
import com.gene.IM.service.DeviceService;
import com.gene.IM.service.MqttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.gene.IM.receiveclient.MqttAcceptCallback.macSet;


// 之前任务需要，后续可更改
@RestController
@CrossOrigin
@RequestMapping("/mqtt")
public class MqttApi {

//    public static BlockingQueue<JSONObject> macQueue = new ArrayBlockingQueue<JSONObject>(30);

    //设备是否连接
    public static boolean connect=false;

    public static int line1OrderNum;
    public static int line2OrderNum;
    public static int line3OrderNum;
    //是否火灾
    public static int isFire;
    public static int WaterState;

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
    private DeviceMapper deviceMapper;

    @Autowired
    private DeviceService deviceService;


    /**
     * 获取所有设备信息
     *
     * @return 包含所有设备信息的 JSONObject 对象
     */
    @NotNeedJWT
    @GetMapping("/getAllDevices")
    public JSONObject getAllDevices() {

        JSONObject allDevices = JSONUtil.createObj();
//        while (!macSet.isEmpty()){
//            allDevices.append("设备",macSet);
//        }
        for (String macElement : macSet) {
            JSONObject json = new JSONObject(macElement);
            if(json.getInt("Mac")!=0) {
                json.set("line", (json.getInt("Mac")+4-1)/ 4 );
            }
            allDevices.append("Devices",json);
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
    /**
     * 连接设备
     *
     * @param devices 包含要连接的设备信息的 JSON 数组
     * @return 包含连接结果的通用结果对象，包含连接成功的设备列表
     */
    @NotNeedJWT
    @PostMapping("/connectDevices")
    public CommonResult<List<String>> connectDevices(@RequestBody JSONArray devices) {
        connect=true;
        System.out.println(devices);
        line1OrderNum = deviceMapper.getLineOrderDetail(1).getOrderNum();
        line2OrderNum = deviceMapper.getLineOrderDetail(2).getOrderNum();
        line3OrderNum = deviceMapper.getLineOrderDetail(3).getOrderNum();
        return new CommonResult<List<String>>(deviceService.connectDevices(devices));

    }
    /**
     * 断开设备连接
     *
     * @return 包含断开连接结果的通用结果对象，提示等待下次连接
     */
    @NotNeedJWT
    @GetMapping("/disconnectDevices")
    public CommonResult<String> disconnectDevices() {
        connect=false;
        //清空消息队列(事实上应该在/getAllDevices已经清空了），等待硬件再次发布mac信息，用户访问/connectDevices再次连接
        macSet.clear();

        return new CommonResult<String>("已断开连接，等待下次连接");

    }

    /**
     * 获取湿度、温度和气体信息
     *
     * @return 包含湿度、温度和气体信息的通用结果对象
     */
    @NotNeedJWT
    @GetMapping("/getHumTempGas")
    public CommonResult<JSONObject> getHumTempGas() {

        return new CommonResult<JSONObject>(deviceService.getHumTempGas());


    }

    /**
     * 获取通过消毒节点的率
     * @return 消毒通过率
     */
    @NotNeedJWT
    @GetMapping("/getPass")
    public CommonResult<JSONObject> getPass() {

        return new CommonResult<JSONObject>(deviceService.getPass());


    }

    /**
     * 获取入库商品个数
     * @return
     */
    @NotNeedJWT
    @GetMapping("/getPassOutNum")
    public CommonResult<JSONArray> getPassOutNum() {

        return new CommonResult<JSONArray>(deviceService.getPassOutNum());


    }

    @NotNeedJWT
    @GetMapping("/getPassInNum")
    public CommonResult<JSONArray> getPassInNum() {

        return new CommonResult<JSONArray>(deviceService.getPassInNum(),"消毒节点通过数量");


    }

    @NotNeedJWT
    @GetMapping("/getFire")
    public CommonResult<JSONObject> getFire() {
        JSONObject fireJSON = new JSONObject().set("isFire",isFire);
        return new CommonResult<JSONObject>(fireJSON);

    }
    @NotNeedJWT
    @GetMapping("/getLineInfo")
    public CommonResult<JSONObject> getLineInfo(@RequestParam Integer lineId) {
        return new CommonResult<JSONObject>(deviceService.getLineInfo(lineId));

    }

    @NotNeedJWT
    @GetMapping("/getWaterInfo")
    public CommonResult<JSONObject> getWaterInfo(@RequestParam Integer lineId) {

        return new CommonResult<JSONObject>(deviceService.getWaterInfo(lineId));

    }


    @NotNeedJWT
    @GetMapping(value = "/sendmsg")
    public Object publishTopic(String sendMessage) {
        sendMessage= "{uname:lz,age:18}";
        client1.publish(false ,"receiveTopic",sendMessage);
        return null;
    }

//
//    @NotNeedJWT
//    @GetMapping(value = "/statistics")
//    public JSONObject publishTopic() throws SQLException {
//        Map result = mqttService.getTemp();
//        if(result!=null) {
//            JSONObject json1 = JSONUtil.createObj()
//                    .put("舒适",result.get("avg_temp_on"))
//                    .put("燥热",result.get("avg_temp_off"))
//                    .put("status","ok");
//            return json1;
//        }else {
//            JSONObject json1 = JSONUtil.createObj()
//                    .put("status","error");
//            return json1;
//        }
//
//    }
//
//    @PostMapping(value = "/windctl")
//    public JSONObject windctl(@RequestBody JSONObject sendMessage) {
//        String msg = sendMessage.getStr("switch");
//        JSONObject json1 = JSONUtil.createObj();
//        if(client1.publish(false ,"TopicC",msg)==1) {
//            json1.put("code", 1)
//                    .put("desc", "修改成功")
//                    .put("status", msg);
//        }else {
//            json1.put("code", 0)
//                    .put("desc", "修改失败");
//        }
//
//        return json1;    }
//
//
//    @PostMapping(value = "/waterctl")
//    public JSONObject waterctl(@RequestBody JSONObject sendMessage) {
//        String msg = sendMessage.getStr("switch");
////        return client2.publish(false ,"TopicD",msg);
//        client2.publish(false ,"TopicD",msg);
//        return sendMessage;
//    }
}
