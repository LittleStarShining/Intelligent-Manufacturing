package com.gene.IM.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.gene.IM.entity.CommonResult;
import com.gene.IM.entity.Device;
import com.gene.IM.mapper.DeviceMapper;
import com.gene.IM.sendclient.Send_Client1;
import com.gene.IM.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.gene.IM.receiveclient.MqttAcceptCallback.massageQueue;


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

            String mac =devices.getJSONObject(i).getStr("mac");
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
            send_client1.publish(false ,"subtopic","{\""+mac+"\"}");
        }

//        for(JSONObject device:devices){
//
//        }
//deviceMapper.updateDevice(new Device().setMac(devices);)
        System.out.println("mac:"+ connect_mac);


        return connect_mac;
    }


    @Override
    public JSONObject addDevices(JSONObject device) {
        return null;
    }

    @Override
    public JSONObject getHumTempFlame() {
        Double temperatureSum = 0.0;
        Double humiditySum = 0.0;
        Double flameSum = 0.0;
        int count = 0;
        JSONObject json = new JSONObject();
        while(count == 0) {
            Iterator<JSONObject> iterator = massageQueue.iterator();
            while (iterator.hasNext()) {
                JSONObject message = iterator.next();
                if (message.getStr("mac").equals("000")) {
                    humiditySum += message.getDouble("hum");
                    temperatureSum += message.getDouble("temp");
                    flameSum += message.getDouble("flame");
                    count++;
                }
            }

            if (count > 0) {
                Double averageTemperature = temperatureSum / count;
                Double averageHumidity = humiditySum / count;
                Double averageFlame = flameSum / count;
                json.set("hum", averageHumidity).set("temp", averageTemperature).set("flame",averageFlame ).set("count", count);
            }
        }

        return json;
    }

    @Override
    public JSONObject getPass() {
        Iterator<JSONObject> iterator = massageQueue.iterator();
        int num = 0;
        int line =0;
        while (iterator.hasNext()) {
            JSONObject message = iterator.next();
            if (message.getStr("mac").equals("001")) {
                num = message.getInt("mac");
                line = message.getInt("line");

            }
        }
        return null;
    }
}
