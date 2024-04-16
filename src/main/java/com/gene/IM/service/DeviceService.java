package com.gene.IM.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

import java.util.List;

public interface DeviceService {


    /***
     * 每次连接一个设备
     * @param devices
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
     * @return 连接个数
     */
    List<String> connectDevices(JSONArray devices);
    /***
     * 每次添加一个设备
     * @param device                     {
     *                     "mac": "02",
     *                     "describe": "风扇"
     *                     }
     * @return 添加的设备
     */
    public JSONObject addDevices(JSONObject device);
    public JSONObject getHumTempGas();

    public JSONObject getPass();

    public JSONArray getPassOutNum();

    public JSONArray getPassInNum();

    public JSONObject getLineInfo(Integer lineId);

    public JSONObject getWaterInfo(Integer lineId);
}
