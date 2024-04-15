package com.gene.IM.event;

import com.gene.IM.mapper.InformationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Async
@Component
public class NotificationService {
    @Autowired
    private InformationMapper informationMapper;
    @EventListener
    public void handleNewOrderEvent(newOrder event) {
      String msg;
        msg = "收到新订单" + event.getOrderID() + ", 饮料类型：" + event.getTypeName() + ", 下单数量： " + event.getOrderNum() + ", 预计开始生产时间" + event.getPredictStart() + ", 预计生产结束时间" + event.getPredictFinish();
      informationMapper.addInformation(msg);
    }

    @EventListener
    public void handleCheckMaterial(checkMaterial event) {
      int temp = event.getNeed() - (int)event.getNum();
      String msg;
      msg = "材料" + event.getId() + "库存不足, 当前库存为" + event.getNum() + ", 预计需求" + event.getNeed() + ", 预计补货量:" + temp;
      informationMapper.addInformation(msg);
    }


}
