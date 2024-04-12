package com.gene.IM.service;

import com.gene.IM.DTO.HistoryReportGraph;
import com.gene.IM.entity.OrderInfo;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public interface OrderService {
    public Map<String,Object> showWaitingList();
    public Map<String,Object> showDoingList();
    public Map<String,Object> showDoneList();
    public Map<String,Object> showOrderDetail(int id);

    public Map<String,Object> orderBySituation(int situation, String sort);

    public Map<String,Object> vagueSelect(String source,String key);


    // 计算订单的优先级（响应比）
    public double getPriority(int orderId);
    // 存储订单优先级
    public void autoSetPriority(int orderId);
    // 贪心算法分配订单到流水线
    public List<List<OrderInfo>> greedyAssign();
    // 获得订单优先级队列
    public PriorityQueue<OrderInfo> getPriorityQueue();
    // 获得最优先订单
    public OrderInfo getHighestPriorityOrder();

    public List<HistoryReportGraph> getGraphData();
    public Map<String,Object> showHistoryReport();
    public Map<String, Object> addOrder(OrderInfo request);


}
