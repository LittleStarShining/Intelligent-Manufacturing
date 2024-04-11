package com.gene.IM.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.gene.IM.entity.Device;
import com.gene.IM.mapper.DeviceMapper;
import com.gene.IM.sendclient.Send_Client1;
import com.gene.IM.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.gene.IM.api.MqttApi.*;
import static com.gene.IM.receiveclient.MqttAcceptCallback.*;


@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private Send_Client1 send_client1;
    @Override
    public List<String> connectDevices(JSONArray devices) {
        List<String> connect_mac = new ArrayList<String>();
        System.out.println(devices);
        Device device = new Device();
        for(int i = 0; i < devices.size(); i++){

            String mac = devices.getJSONObject(i).getStr("mac");
            device.setMac(mac);
            device.setStatus("已连接");
            //如果数据库没有该设备，则插入新设备
            if(deviceMapper.selectDeviceByMac(mac) == null){
                device.setLine((Integer) devices.getJSONObject(i).get("line"));
                device.setDescription(devices.getJSONObject(i).getStr("description"));
                deviceMapper.insertDevice(device);
            }
            //数据库已有设备，直接更新
            else {
                deviceMapper.updateDevice(device);
            }
            connect_mac.add(mac);
            //通知硬件要连接的mac地址
            send_client1.publish(false ,"subtopic","{mac:"+mac+"}");
        }

//        for(JSONObject device:devices){
//
//        }
//deviceMapper.updateDevice(new Device().setMac(devices);)
        System.out.println("Mac:"+ connect_mac);


        return connect_mac;
    }


    @Override
    public JSONObject addDevices(JSONObject device) {
        return null;
    }

    @Override
    public JSONObject getHumTempGas() {
        Double temperatureSum = 0.0;
        Double humiditySum = 0.0;
        Double flameSum = 0.0;
        int count = 0;
        JSONObject json = new JSONObject();
        while(count == 0) {
            Iterator<JSONObject> iterator = massageQueue.iterator();
            while (iterator.hasNext()) {
                JSONObject message = iterator.next();
                if (message.getStr("Mac").equals("0")) {
                    humiditySum += message.getDouble("Hum");
                    temperatureSum += message.getDouble("Temp");
                    flameSum += message.getDouble("Gas");
                    count++;
                }
            }

            if (count > 0) {
                Double averageTemperature = temperatureSum / count;
                Double averageHumidity = humiditySum / count;
                Double averageFlame = flameSum / count;
                json.set("hum", averageHumidity).set("temp", averageTemperature).set("gas",averageFlame ).set("count", count);
            }
        }

        return json;
    }

    @Override
    public JSONObject getPass() {
        JSONArray jsonArray = new JSONArray();
//        jsonArray.add(new JSONObject().set("line", 1).set("pass", line1_num * 1.0 / line1OrderNum));
//        jsonArray.add(new JSONObject().set("line", 2).set("pass", line1_num * 1.0 / line2OrderNum));
//        jsonArray.add(new JSONObject().set("line", 3).set("pass", line1_num * 1.0 / line3OrderNum));
        return new JSONObject().set("流水线通过率", jsonArray);
    }
    @Override
    public JSONArray getPassInNum() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(new JSONObject().set("line", 1).set("num",line1_num+"/"+line1OrderNum));
        jsonArray.add(new JSONObject().set("line", 2).set("line2",line2_num+"/"+line2OrderNum));
        jsonArray.add(new JSONObject().set("line", 3).set("line3",line3_num+"/"+line3OrderNum));
        return jsonArray;
    }
}
