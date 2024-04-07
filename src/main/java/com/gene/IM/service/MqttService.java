package com.gene.IM.service;

import org.springframework.stereotype.Service;

import cn.hutool.db.Entity;
import cn.hutool.json.JSONObject;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

// 之前任务需要，后续可更改
@Service
public interface MqttService {

    public Map<String, Object> selectLastData();

    public Map<String, Object> selectFiveDate();
    
    public Map getTemp()  throws SQLException;


}
