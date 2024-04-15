package com.gene.IM.service.impl;

import com.gene.IM.DTO.HistoryReportGraph;
import com.gene.IM.entity.OrderInfo;
import com.gene.IM.event.newOrder;
import com.gene.IM.mapper.MaterialMapper;
import com.gene.IM.mapper.OrderInfoMapper;
import com.gene.IM.service.MaterialService;
import com.gene.IM.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service("OrderService")
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private MaterialService materialService;

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
        // 获得三条流水线当前工作订单的预计完成时间
        // 获得三条流水线当前工作订单的预计完成时间
        List<OrderInfo> workingorders = orderInfoMapper.getDoingTask();
        LocalDate[] lastFinishTimes = new LocalDate[lineNum];
        lastFinishTimes[0] = getPredictFinish(workingorders.get(0).getOrderID());
        lastFinishTimes[1] = getPredictFinish(workingorders.get(1).getOrderID());
        lastFinishTimes[2] = getPredictFinish(workingorders.get(2).getOrderID());

        for (OrderInfo orderInfo : orderInfos) {
            int minIndex = 0;
            for (int i = 1; i < lineNum; i++) {
                if (lines.get(i).size() < lines.get(minIndex).size()) {
                    minIndex = i;
                }
            }
            lines.get(minIndex).add(orderInfo);
            // 设置预计开始时间为对应流水线的最后一个任务的预计完成时间
            orderInfo.setPredictStart(lastFinishTimes[minIndex]);
            // 更新对应流水线的最后一个任务的预计完成时间
            lastFinishTimes[minIndex] = getPredictFinish(orderInfo.getOrderID());
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
            orderInfoMapper.setPriority(orderInfo.getOrderID(),priority);
            priorityQueue.add(orderInfo);
        }
        return priorityQueue;
    }

    @Override
    public OrderInfo getHighestPriorityOrder() {
        PriorityQueue<OrderInfo> priorityQueue = getPriorityQueue();
        return priorityQueue.poll();
    }

    @Override
    public List<HistoryReportGraph> getGraphData() {
        List<HistoryReportGraph> graphData = new ArrayList<>();
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 获取当前月份
        Month currentMonth = currentDate.getMonth();
        // 获取当前月份的整数值
        int monthValue = currentMonth.getValue();
        for (int i = monthValue; i >= monthValue-3; i--){
            for (int j = 1; j <= 3; j++){
                System.out.println(i);
                System.out.println(j);
                graphData.add(orderInfoMapper.genGraph(i,j));
            }
        }
        return graphData;
    }

    @Override
    public Map<String, Object> showHistoryReport() {
        Map<String,Object> res = new HashMap<>();
        Map<String,Object> data = new HashMap<>();
        try{
            int totalOrderNum = orderInfoMapper.getDoneNum();
            float totalOrderMoney = 0;
            int totalNum = 0;
            List<OrderInfo> doneOrder = orderInfoMapper.getDoneTask();
            for (OrderInfo r:doneOrder){
                totalOrderMoney+=r.getOrderMoney();
                totalNum+=r.getOrderNum();
            }

            float avgNum = (float) totalNum / 3;
            data.put("totalOrderNum",totalOrderNum);
            data.put("totalOrderMoney",totalOrderMoney);
            data.put("totalNum",totalNum);
            data.put("avgNum",avgNum);
            List<HistoryReportGraph> list = this.getGraphData();
            System.out.println(list.get(0).getLineID());
            data.put("graphData",list);
            res.put("data",data);
        }catch (Exception e){
            res.put("code",0);
            res.put("desc","生成失败");
            return res;
        }
        res.put("code",1);
        res.put("desc","生成成功");

        return res;
    }

    @Override
    public Map<String, Object> addOrder(OrderInfo request) {
        Map<String, Object> res = new HashMap<>();
        try {
            // 计算sumTime
            int sumTime = (int) ChronoUnit.DAYS.between(request.getOrderTime(), request.getDdl());

            // 计算remainTime
            int remainTime = (int) ChronoUnit.DAYS.between(LocalDate.now(), request.getDdl());

            // 设置progress和status
            double progress = 0.0;
            String status = "等候";

            // 构造订单对象
            OrderInfo order = new OrderInfo();
            order.setTypeName(request.getTypeName());
            order.setOrderTime(request.getOrderTime());
            order.setDdl(request.getDdl());
            order.setOrderNum(request.getOrderNum());
            order.setOrderMoney(request.getOrderMoney());
            order.setSumTime(sumTime);
            order.setRemainTime(remainTime);
            order.setProgress(progress);
            order.setStatus(status);

            // 调用mapper添加订单，并获取自增id
            int orderId = orderInfoMapper.addOrder(order);
            // 修改Need
            materialMapper.updateMaterialNeed();
            // 重分配优先级
            //this.getPriorityQueue();
            this.greedyAssign();
            publisher.publishEvent(new newOrder(this, orderId,order.getTypeName(),order.getOrderNum(),order.getPredictStart(),order.getPredictFinish()));
            res.put("code", 1);
            res.put("desc", "添加订单成功");
        } catch (Exception e) {
            res.put("code", 0);
            res.put("desc", "添加订单失败");
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Map<String, Object> getScheduleList() {
        return null;
    }

    public LocalDate getPredictFinish(int OrderId) {
        // 获得剩余工作量
        double remainNum = orderInfoMapper.getOrderDetail(OrderId).getOrderNum() * orderInfoMapper.getOrderDetail(OrderId).getProgress();
        int remainTime = (int) Math.ceil(remainNum / 5000);
        return LocalDate.now().plusDays(remainTime);
    }


}
