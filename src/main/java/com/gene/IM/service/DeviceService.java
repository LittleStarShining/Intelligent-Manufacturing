package com.gene.IM.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

import java.util.List;

public interface DeviceService {


    /***
     * 连接设备
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
     * @return
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

    /**
     * 获取消息队列中湿度、温度和气体值的平均数
     *
     * @return 包含平均湿度、温度和气体值的 JSONObject 对象
     */
    public JSONObject getHumTempGas();

    /**
     * 分别获取流水线上订单完成率
     * @return 三条流水线上订单完成率
     */
    public JSONObject getPass();

    /**
     * 获取走出最后一个入库节点的订单的商品个数
     * @return
     */
    public JSONArray getPassOutNum();

    /**
     * 获取进入消毒节点的订单的商品个数
     * @return
     */
    public JSONArray getPassInNum();

    /**
     * 获取指定生产线的线路信息
     *
     * @param lineId 生产线的唯一标识符
     * @return 包含指定生产线线路信息的 JSONObject 对象
     */
    public JSONObject getLineInfo(Integer lineId);
    /**
     * 获取指定生产线的水质信息
     *
     * @param lineId 生产线的唯一标识符
     * @return 包含指定生产线水质信息的 JSONObject 对象
     */
    public JSONObject getWaterInfo(Integer lineId);
}
