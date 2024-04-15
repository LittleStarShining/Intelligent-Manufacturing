package com.gene.IM.service.impl;

import com.gene.IM.DTO.TodayProgress;
import com.gene.IM.mapper.OrderInfoMapper;
import com.gene.IM.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("IndexService")
public class IndexServiceImpl implements IndexService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Override
    public Map<String, Object> getIndexInfo() {
        Map<String,Object> res = new HashMap<>();
        Map<String,Object> data = new HashMap<>();
        try{
            data.put("todayTask",orderInfoMapper.getTodayTask());

            int totalOrderNum = orderInfoMapper.getHistorySum();
            int doneOrderNum = orderInfoMapper.getDoneNum();
            int totalProgress = doneOrderNum*100/totalOrderNum;
            int todayTotalNum = 0;
            float todayDoneNum = 0;
            List<TodayProgress> todayProgressList = orderInfoMapper.getCompleteRate();
            for (TodayProgress i : todayProgressList){
                todayTotalNum+=i.getOrderSum();
                todayDoneNum+= (i.getProgress()*i.getOrderSum());
            }
            int completionRate = (int) todayDoneNum*100/todayTotalNum;
            data.put("totalProgress",totalProgress);
            data.put("completionRate",completionRate);
            res.put("data",data);

        }catch (Exception e) {
            res.put("code", 0);
            res.put("desc", "查询失败");
            return res;
        }
        res.put("code",1);
        res.put("desc","查询成功");

        return res;
    }
}
