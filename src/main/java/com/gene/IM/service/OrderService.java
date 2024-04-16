package com.gene.IM.service;

import com.gene.IM.DTO.HistoryReportGraph;
import com.gene.IM.entity.OrderInfo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public interface OrderService {
    /**
     *
     * @return
     */
    public Map<String,Object> showWaitingList();

    /**
     *
     * @return
     */
    public Map<String,Object> showDoingList();

    /**
     *
     * @return
     */
    public Map<String,Object> showDoneList();

    /**
     *
     * @param id
     * @return
     */
    public Map<String,Object> showOrderDetail(int id);

    /**
     *
     * @param situation
     * @param sort
     * @return
     */
    public Map<String,Object> orderBySituation(int situation, String sort);

    /**
     *
     * @param source
     * @param key
     * @return
     */
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

    /**
     *
     * @return
     */
    public List<HistoryReportGraph> getGraphData();

    /**
     *
     * @return
     */
    public Map<String,Object> showHistoryReport();

    /**
     *
     * @param request
     * @return
     */
    public Map<String, Object> addOrder(OrderInfo request);

    /**
     *
     * @return
     */
    public Map<String,Object> getScheduleList();

    public double getPassRate(int lineID);


    // 获得预计完成时间
    public LocalDate getPredictFinish(int orderID);

}
