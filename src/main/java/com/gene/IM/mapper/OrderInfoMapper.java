package com.gene.IM.mapper;

import com.gene.IM.DTO.HistoryReportGraph;
import com.gene.IM.DTO.OrderIndex;
import com.gene.IM.DTO.TodayProgress;
import com.gene.IM.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface OrderInfoMapper {

    /**
     * 获取今日任务订单
     * @return 今日任务订单
     *       {
     *         "orderID":订单ID,
     *         "typeName":商品名称,
     *         "orderMoney":订单金额,
     *         "lineID":流水线ID,
     *         "ddl":交付日期
     *       }
     *
     */
    public List<OrderIndex> getTodayTask();

    /**
     * 获取等待队列订单
     * @return 等待队列订单
     * {
     *         "orderID":订单ID,
     *         "typeName":商品名称,
     *         "orderMoney":订单金额,
     *         "orderNum":订单数量,
     *         "ddl":交付日期
     * }
     */
    public List<OrderInfo> getWaitingTask();

    /**
     * 获取已分配队列
     * @return
     * {
     *         "orderID":订单ID,
     *         "typeName":商品名称,
     *         "orderMoney":订单金额,
     *         "lineID":订单,
     *         "ddl":
     * }
     */
    public List<OrderInfo> getDoingTask();

    /**
     * 获取已完成队列
     * @return
     *       {
     *         "orderID": 订单ID,
     *         "typeName": 商品名称,
     *         "orderMoney": 订单金额,
     *         "lineID": 所属流水线ID,
     *         "ddl": 交付日期
     *       },
     */
    public List<OrderInfo> getDoneTask();

    /**
     * 获取今日订单完成率
     * @return 今日订单（3个）完成率
     */
    public List<TodayProgress> getCompleteRate();

    /**
     * 获取已完成订单数
     * @return 已完成订单数
     */
    public int getDoneNum();

    /**
     * 获取订单总数
     * @return 订单总数
     */
    public int getHistorySum();

    /**
     *
     * @param id 订单ID
     * @return 获取订单详情
     */
    public OrderInfo getOrderDetail(@Param("orderID") int id);

    /**
     * 获取
     * @return
     */
    public List<OrderInfo> WaitingOrderByMoney();

    /**
     *
     * @return
     */
    public List<OrderInfo> WaitingOrderByDdl();

    /**
     *
     * @return
     */
    public List<OrderInfo> DoingOrderByMoney();

    /**
     *
     * @return
     */
    public List<OrderInfo> DoingOrderByDdl();

    /**
     *
     * @param source
     * @param key
     * @return
     */
    public List<OrderInfo> vagueSelect(@Param("source")String source, @Param("key") String key);

    /**
     *
     * @param orderId
     * @param priority
     */
    public void setPriority(@Param("orderID")int orderId,@Param("priority") double priority);

    /**
     *
     * @param month
     * @param lineID
     * @return
     */
    public HistoryReportGraph genGraph(@Param("month")int month, @Param("lineID")int lineID);

    /**
     *
     * @param order
     */

    @Options(useGeneratedKeys = true, keyProperty = "order.id")
    public int addOrder(OrderInfo order);


    /**
     *
     * @param lineID
     */
    public void changeStatusByLineID(@Param("lineID") int lineID);

    /**
     *
     * @param orderID
     * @param status
     */
    public void changeStatusByOrderID(@Param("orderID")int orderID,@Param("status") String status);

    /**
     *
     * @param lineID
     * @param orderID
     */
    public void changeLineWorkingOrder(@Param("lineID")int lineID,@Param("orderID") int orderID);

    /**
     *
     * @param lineID
     * @param orderID
     */
    public void addBelongLineOrder(@Param("lineID")int lineID, @Param("orderID")int orderID);

    public void updatePredictStart(@Param("orderID") int orderID, @Param("predictStart") LocalDate start);
    public void updatePredictFinish(@Param("orderID") int orderID, @Param("predictFinish") LocalDate finish);


}
