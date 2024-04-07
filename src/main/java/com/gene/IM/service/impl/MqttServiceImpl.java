package com.gene.IM.service.impl;

import com.gene.IM.mapper.RecordMapper;
import com.gene.IM.entity.Record;
import com.gene.IM.service.MqttService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MqttServiceImpl implements MqttService {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public Map<String, Object> selectLastData() {
        Map res = new HashMap<>();
        List<Record> result = new ArrayList<>();
        try {
            System.out.println("1");
            result = jdbc.query("SELECT * FROM task1table ORDER BY record_id DESC LIMIT 1;", new RecordMapper());

        }catch (Exception e){
            res.put("code",0);
            res.put("desc","查询失败");
        }
        res.put("result",result);
        res.put("code",1);
        res.put("desc","查询成功");

        return res;
    }

    @Override
    public Map<String, Object> selectFiveDate() {
        Map res = new HashMap<>();
        List<Record> result = new ArrayList<>();
        try {
            System.out.println("1");
            result = jdbc.query("SELECT * FROM task1table ORDER BY record_id DESC LIMIT 5;", new RecordMapper());

        }catch (Exception e){
            res.put("code",0);
            res.put("desc","查询失败");
        }
        System.out.println(result);
        res.put("result",result);
        res.put("code",1);
        res.put("desc","查询成功");

        return res;
    }

	@Override
	public Map getTemp() throws SQLException {
		// TODO Auto-generated method stub
		Map result = jdbc.queryForMap("SELECT AVG(CASE WHEN status = 'on' THEN temp END) AS avg_temp_on,\r\n" + 
				"       AVG(CASE WHEN status = 'off' THEN temp END) AS avg_temp_off\r\n" + 
				"FROM task1table;");
		


		
		return result;
	}
}
