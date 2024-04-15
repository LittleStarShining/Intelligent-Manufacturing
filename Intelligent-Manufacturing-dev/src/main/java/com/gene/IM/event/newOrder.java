package com.gene.IM.event;

import org.springframework.context.ApplicationEvent;

import java.time.LocalDate;

public class newOrder extends ApplicationEvent {
    private int orderID;
    private String typeName;
    private int orderNum;

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public LocalDate getPredictStart() {
        return predictStart;
    }

    public void setPredictStart(LocalDate predictStart) {
        this.predictStart = predictStart;
    }

    public LocalDate getPredictFinish() {
        return predictFinish;
    }

    public void setPredictFinish(LocalDate predictFinish) {
        this.predictFinish = predictFinish;
    }

    private LocalDate predictStart;
    private LocalDate predictFinish;
    public newOrder(Object source,int orderID,String typeName,int orderNum,LocalDate predictStart,LocalDate predictFinish) {
        super(source);
        this.orderID = orderID;
        this.typeName = typeName;
        this.orderNum = orderNum;
        this.predictStart = predictStart;
        this.predictFinish = predictFinish;
    }



}
