package com.gene.IM.mapper;

import com.gene.IM.DTO.HistoryReportGraph;
import com.gene.IM.DTO.OrderIndex;
import com.gene.IM.DTO.TodayProgress;
import com.gene.IM.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderInfoMapper {

    public List<OrderIndex> getTodayTask();
    public List<OrderInfo> getWaitingTask();
    public List<OrderInfo> getDoingTask();
    public List<OrderInfo> getDoneTask();

    public List<TodayProgress> getCompleteRate();
    public int getDoneNum();
    public int getHistorySum();
    public OrderInfo getOrderDetail(@Param("orderID") int id);

    public List<OrderInfo> WaitingOrderByMoney();
    public List<OrderInfo> WaitingOrderByDdl();
    public List<OrderInfo> DoingOrderByMoney();
    public List<OrderInfo> DoingOrderByDdl();

    // 模糊搜索
    public List<OrderInfo> vagueSelect(@Param("source")String source, @Param("key") String key);

    public void setPriority(@Param("order")int orderId,@Param("priority") double priority);

    public HistoryReportGraph genGraph(@Param("month")int month, @Param("lineID")int lineID);





}
