package com.gene.IM.mapper;

import com.gene.IM.DTO.OrderIndex;
import com.gene.IM.DTO.TodayProgress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderInfoMapper {

    public List<OrderIndex> getTodayTask();

    public List<TodayProgress> getCompleteRate();

    public int getDoneNum();
    public int getHistorySum();


}
