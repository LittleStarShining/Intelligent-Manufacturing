package com.gene.IM.service.impl;

import com.gene.IM.entity.OrderInfo;
import com.gene.IM.mapper.OrderInfoMapper;
import com.gene.IM.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service("OrderService")
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Override
    public Map<String, Object> showWaitingList() {
        Map<String, Object> res = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        try {
            data.put("waitingTask", orderInfoMapper.getWaitingTask());
            res.put("data", data);

        } catch (Exception e) {
            res.put("code", 0);
            res.put("desc", "查询失败");
            return res;
        }
        res.put("code", 1);
        res.put("desc", "查询成功");

        return res;
    }

    @Override
    public Map<String, Object> showDoingList() {
        Map<String, Object> res = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        try {
            data.put("doingTask", orderInfoMapper.getDoingTask());
            res.put("data", data);

        } catch (Exception e) {
            res.put("code", 0);
            res.put("desc", "查询失败");
            return res;
        }
        res.put("code", 1);
        res.put("desc", "查询成功");

        return res;
    }

    @Override
    public Map<String, Object> showDoneList() {
        Map<String, Object> res = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        try {
            data.put("doneTask", orderInfoMapper.getDoneTask());
            res.put("data", data);

        } catch (Exception e) {
            res.put("code", 0);
            res.put("desc", "查询失败");
            return res;
        }
        res.put("code", 1);
        res.put("desc", "查询成功");

        return res;
    }

    @Override
    public Map<String, Object> showOrderDetail(int id) {
        Map<String, Object> res = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        try {
            data.put("orderDetail", orderInfoMapper.getOrderDetail(id));
            res.put("data", data);

        } catch (Exception e) {
            res.put("code", 0);
            res.put("desc", "查询失败");
            return res;
        }
        res.put("code", 1);
        res.put("desc", "查询成功");

        return res;
    }

    @Override
    public Map<String, Object> orderBySituation(int situation, String sort) {
        Map<String, Object> res = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        try {
            if (sort.equals("按金额")) {
                if (situation == 0) {
                    data.put("task", orderInfoMapper.WaitingOrderByMoney());
                } else if (situation == 1) {
                    data.put("task", orderInfoMapper.DoingOrderByMoney());
                }

            } else if (sort.equals("按交付日期")) {
                if (situation == 0) {
                    data.put("task", orderInfoMapper.WaitingOrderByDdl());
                } else if (situation == 1) {
                    data.put("task", orderInfoMapper.DoingOrderByDdl());
                }
            }

            res.put("data", data);

        } catch (Exception e) {
            res.put("code", 0);
            res.put("desc", "查询失败");
            return res;
        }
        res.put("code", 1);
        res.put("desc", "查询成功");

        return res;
    }

    @Override
    public Map<String, Object> vagueSelect(String source, String key) {
        Map<String, Object> res = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        try {
            data.put("task", orderInfoMapper.vagueSelect(source, key));
            res.put("data", data);
            System.out.println(orderInfoMapper.vagueSelect(source, key).get(0).getOrderMoney());
        } catch (Exception e) {
            res.put("code", 0);
            res.put("desc", "查询失败");
            return res;
        }
        res.put("code", 1);
        res.put("desc", "查询成功");

        return res;
    }

    /**
     * 计算订单的优先级（响应比）
     *
     * @param orderId
     * @return 订单的优先级
     */
    @Override
    public double getPriority(int orderId) {
        // 获得等待时间;
        LocalDate orderTime = orderInfoMapper.getOrderDetail(orderId).getOrderTime();
        LocalDate today = LocalDate.now();
        Period period = Period.between(orderTime, today);
        int waitTime = period.getDays();
        // 获得订单预计完成时间 默认产能5000瓶一天
        double predictedTime = Math.ceil((double) orderInfoMapper.getOrderDetail(orderId).getOrderNum() / 5000);
        // 计算订单的优先级
        return (waitTime + predictedTime) / (predictedTime + 1);
    }

    @Override
    public void autoSetPriority(int orderId) {
        double priority = getPriority(orderId);
        orderInfoMapper.setPriority(orderId, priority);
    }


    @Override
    public List<List<OrderInfo>> greedyAssign() {
        PriorityQueue<OrderInfo> priorityQueue = getPriorityQueue();
        List<OrderInfo> orderInfos = new ArrayList<>();
        while (!priorityQueue.isEmpty()) {
            OrderInfo orderInfo = priorityQueue.poll();
            orderInfos.add(orderInfo);
        }
        int lineNum = 3;
        List<List<OrderInfo>> lines = new ArrayList<>();
        for (int i = 0; i < lineNum; i++) {
            lines.add(new ArrayList<>());
        }
        for (OrderInfo orderInfo : orderInfos) {
            int minIndex = 0;
            for (int i = 1; i < lineNum; i++) {
                if (lines.get(i).size() < lines.get(minIndex).size()) {
                    minIndex = i;
                }
            }
            lines.get(minIndex).add(orderInfo);
        }
        return lines;
    }

    @Override
    public PriorityQueue<OrderInfo> getPriorityQueue() {
        Comparator<OrderInfo> comparator = (o1, o2) -> Double.compare(o2.getPriority(), o1.getPriority());
        PriorityQueue<OrderInfo> priorityQueue = new PriorityQueue<>(comparator);
        List<OrderInfo> orderInfos = orderInfoMapper.getWaitingTask();
        for (OrderInfo orderInfo : orderInfos) {
            double priority = getPriority(orderInfo.getOrderID());
            orderInfo.setPriority(priority);
            priorityQueue.add(orderInfo);
        }
        return priorityQueue;
    }

    @Override
    public OrderInfo getHighestPriorityOrder() {
        PriorityQueue<OrderInfo> priorityQueue = getPriorityQueue();
        return priorityQueue.poll();
    }
}
