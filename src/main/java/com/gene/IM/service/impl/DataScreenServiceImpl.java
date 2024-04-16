package com.gene.IM.service.impl;

import com.gene.IM.DTO.DoingOrder_ds;
import com.gene.IM.DTO.OrderIndex;
import com.gene.IM.entity.OrderInfo;
import com.gene.IM.mapper.OrderInfoMapper;
import com.gene.IM.service.DataScreenService;
import com.gene.IM.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gene.IM.receiveclient.MqttAcceptCallback.*;

@Service("DataScreenService")
public class DataScreenServiceImpl implements DataScreenService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Override
    public Map<String, Object> production() {
        Map<String,Object> res = new HashMap<>();
        int dailyProgress = 0;
        int productionTargets = 0;
        int actualProduction = 0;

        List<OrderIndex> doingTask = orderInfoMapper.getTodayTask();
        for (OrderIndex o:doingTask){
            productionTargets+=o.getOrderNum();
        }
        actualProduction+=line1_pass_num;
        actualProduction+=line2_pass_num;
        actualProduction+=line3_pass_num;

        res.put("productionTargets",productionTargets);
        res.put("actualProduction",actualProduction);
        dailyProgress = (int)(actualProduction*100/productionTargets);
        res.put("dailyProgress",dailyProgress);
        return res;
    }

    @Override
    public Map<String, Object> lineProduction() {
        Map<String,Object> res = new HashMap<>();
        List<DoingOrder_ds> lineProductDetail= orderInfoMapper.lineProductDetail();
        lineProductDetail.get(0).setDoneSum(1222);
        lineProductDetail.get(1).setDoneSum(2222);
        lineProductDetail.get(2).setDoneSum(3222);
        res.put("lineDetail",lineProductDetail);
        return res;
    }

    @Override
    public Map<String, Object> allOrderList() {
        Map<String,Object> res = new HashMap<>();
        List<OrderInfo> orderList = new ArrayList<>();
        List<OrderInfo> doingOrder = orderInfoMapper.getDoingTask();
        List<OrderInfo> doneOrder =  orderInfoMapper.getWaitingTask();
        orderList.addAll(doingOrder);
        orderList.addAll(doneOrder);

        res.put("orderList",orderList);

        return res;
    }


}
