package com.gene.IM.service.impl;

import com.gene.IM.mapper.OrderInfoMapper;
import com.gene.IM.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("OrderService")
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Override
    public Map<String, Object> showWaitingList() {
        Map<String,Object> res = new HashMap<>();
        Map<String,Object> data = new HashMap<>();
        try{
            data.put("waitingTask",orderInfoMapper.getWaitingTask());
            res.put("data",data);

        }catch (Exception e){
            res.put("code",0);
            res.put("desc","查询失败");
            return res;
        }
        res.put("code",1);
        res.put("desc","查询成功");

        return res;
    }

    @Override
    public Map<String, Object> showDoingList() {
        Map<String,Object> res = new HashMap<>();
        Map<String,Object> data = new HashMap<>();
        try{
            data.put("doingTask",orderInfoMapper.getDoingTask());
            res.put("data",data);

        }catch (Exception e){
            res.put("code",0);
            res.put("desc","查询失败");
            return res;
        }
        res.put("code",1);
        res.put("desc","查询成功");

        return res;
    }

    @Override
    public Map<String, Object> showDoneList() {
        Map<String,Object> res = new HashMap<>();
        Map<String,Object> data = new HashMap<>();
        try{
            data.put("doneTask",orderInfoMapper.getDoneTask());
            res.put("data",data);

        }catch (Exception e){
            res.put("code",0);
            res.put("desc","查询失败");
            return res;
        }
        res.put("code",1);
        res.put("desc","查询成功");

        return res;
    }

    @Override
    public Map<String, Object> showOrderDetail(int id) {
        Map<String,Object> res = new HashMap<>();
        Map<String,Object> data = new HashMap<>();
        try{
            data.put("orderDetail",orderInfoMapper.getOrderDetail(id));
            res.put("data",data);

        }catch (Exception e){
            res.put("code",0);
            res.put("desc","查询失败");
            return res;
        }
        res.put("code",1);
        res.put("desc","查询成功");

        return res;
    }

    @Override
    public Map<String, Object> orderBySituation(int situation, String sort) {
        Map<String,Object> res = new HashMap<>();
        Map<String,Object> data = new HashMap<>();
        try{
            if(sort.equals("按金额")){
                if(situation==0){
                    data.put("task",orderInfoMapper.WaitingOrderByMoney());
                } else if (situation==1) {
                    data.put("task",orderInfoMapper.DoingOrderByMoney());
                }

            }
            else if(sort.equals("按交付日期")){
                if(situation==0){
                    data.put("task",orderInfoMapper.WaitingOrderByDdl());
                } else if (situation==1) {
                    data.put("task",orderInfoMapper.DoingOrderByDdl());
                }
            }

            res.put("data",data);

        }catch (Exception e){
            res.put("code",0);
            res.put("desc","查询失败");
            return res;
        }
        res.put("code",1);
        res.put("desc","查询成功");

        return res;
    }

    @Override
    public Map<String, Object> vagueSelect(String source,String key) {
        Map<String,Object> res = new HashMap<>();
        Map<String,Object> data = new HashMap<>();
        try{
            data.put("task",orderInfoMapper.vagueSelect(source,key));
            res.put("data",data);
            System.out.println(orderInfoMapper.vagueSelect(source,key).get(0).getOrderMoney());
        }catch (Exception e){
            res.put("code",0);
            res.put("desc","查询失败");
            return res;
        }
        res.put("code",1);
        res.put("desc","查询成功");

        return res;
    }


}
